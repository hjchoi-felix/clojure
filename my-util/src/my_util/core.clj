(ns my-util.core
  (:gen-class))

(defn -main
  "I don't do a whole lot ... yet."
  [& args]
  (println "Hello, World!"))

(defn my-test []
  (println "my test function in core.clj"))

(require '[org.tobereplaced.nio.file :refer [create-symbolic-link! ] :as nio])

(defn create-simlink [org-path target-dir]
  (let [name (.getName (java.io.File. org-path))]
	(try
	  (nio/create-symbolic-link!  (str  target-dir "/" name)  org-path  )
	  (catch Exception e (prn "error " e)))))

(defn drive-names []
  (map (memfn getPath) (java.io.File/listRoots)))

(use `[clojure.java.io])

(defn walk [dirpath pattern]
  (let [p (. java.util.regex.Pattern compile (str "(?i)" pattern))]
    (doall (filter #(re-find p (.getName %))
                   (file-seq (file dirpath))))))

(defn find-file [name ignore-path]
  "find file in all drives, except in ignore-path"
  (let [n (count (drive-names))]
    (map walk (drive-names) (repeat n name))))

;test for sub-folder of drive-root : if start from certain drive-root, try to access all drive
(defn test-find [name]
    (map walk (list "c:/__devroot") (list name)))
