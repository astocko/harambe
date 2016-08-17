(use '[leiningen.exec :only (deps)])
(deps '[[me.raynes/fs "1.4.6"]
        [camel-snake-kebab "0.4.0"]
        [cheshire "5.6.3"]])

(require '[clojure.java.io :as io])
(require '[camel-snake-kebab.core :as keb])
(require '[cheshire.core :as json])
(require '[me.raynes.fs :as fs])

(use '[clojure.java.shell :only [sh]])
(use '[clojure.string :only [ends-with? split join]])
(use '[clojure.pprint :only [pprint]])

(def tmp-dir (str (fs/tmpdir) (gensym "/harambe-")))

(defn create-dir []
  (fs/mkdir tmp-dir))

(defn clean-up []
  (println "Cleaning up.")
  (fs/delete-dir tmp-dir))

(defn get-docs []
  (println "Cloning slack-api-docs")
  (let [target (str tmp-dir "/slack-api-docs")]
    (sh "git" "clone" "https://github.com/slackhq/slack-api-docs" target)
    target))

(defn get-files [dir]
  (sort (fs/list-dir (str dir "/methods"))))

(defn get-json [files]
  (filter #(ends-with? % ".json") files))

(defn rename-method [file]
  (let [parts (split (.getName file) #"\.")]
    {:module (first parts)
     :api-name (join "." (drop-last parts))
     :fn-name (keb/->kebab-case (join "-" (drop 1 (drop-last parts))))
     }))

(defn parse-method [file]
  (let [f (slurp file)]
    (let [method (rename-method file)]
      {:module (:module method)
       :api-name (:api-name method)
       :method (:fn-name method)
       :json (json/parse-string f true)})))

(defn required-arg? [[key val]]
  (= true (val :required)))

(defn sym-arg [[key val]]
  (symbol (name key)))

(defn get-args [args]
  {:req (map sym-arg (sort (filter #(required-arg? %) args)))
   :unreq (map sym-arg (sort (filter #(not (required-arg? %)) args)))})

(defn gen-args [args]
  (into []
        (concat
         (:req args)
         (list `& `{:keys ~(into [] (:unreq args))}))))

(defn args->map [args]
  (let [args (concat (:req args) (:unreq args))]
    (into {} (map #(into [] (list (keyword %) %)) args))))

(defn gen-method [method]
  (let [args (get-args (:args (method :json)))]
    `(~'defn
       ~(symbol (method :method))
       ~(:desc (method :json))
       ~(gen-args args)
      (~'let [~'query (~'into {}
                       (~'concat
                        [[:token ~'slack-api-token]]
                        (~'remove (~'comp ~'nil? ~'second) ~(args->map args))))]
       (~'let [~'res (:body (client/post
        ~(str "https://slack.com/api/"
              (method :api-name)) {:form-params ~'query}))]
         ~'(json/parse-string res true))))))

(defn gen-ns [module]
  `(~'ns ~(symbol (str "harambe.slack.web." module))
    (:gen-class)
    (:require
     [cheshire.core :as ~'json]
     [clj-http.client :as ~'client]
     [harambe.slack.state :refer [~'slack-api-token]])))

(defn generate []
  (println "Generating API from docs")
  (let [files (-> (get-docs) (get-files) (get-json))]
    (let [methods (map parse-method files)]
      (doseq [keyval (group-by :module methods)]
        (with-open [wr (io/writer (str "src/harambe/slack/web/" (key keyval) ".clj"))]
          (pprint (gen-ns (key keyval)) wr)
          (doseq [mkv (val keyval)]
            (.write wr "\n")
            (pprint (gen-method mkv) wr)))))))

(do
  (create-dir)
  (generate)
  (clean-up))
