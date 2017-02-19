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

(defn make-pattern [search-text]
  (. java.util.regex.Pattern compile (str "(?i)" search-text)))

(defn walk [dirpath pattern]
;  (let [p (. java.util.regex.Pattern compile (str "(?i)" pattern))]
  (let [p (make-pattern pattern)]
    (doall (filter #(re-find p (.getName %))
                   (file-seq (file dirpath))))))

(defn find-file [name ignore-path]
  "find file in all drives, except in ignore-path"
  (let [n (count (drive-names))]
    (map walk (drive-names) (repeat n name))))

;test for sub-folder of drive-root : if start from certain drive-root, try to access all drive
(defn test-find [name]
    (map walk (list "c:/__devroot") (list name)))

;my objective
;function that get root-dir, search-text and filter predicates
;return file seq
(defn find-all [root-dir search-text & preds]
  (let [search-pred #(re-find (make-pattern search-text) (.getName %1))]
    (filter (apply every-pred search-pred preds) (file-seq (file root-dir)))))

(defn get-extension [path]
  (.substring path (inc (.lastIndexOf path "."))))

(defn is-video [file]
  (let [path (.getPath file)]
    (= (get-extension path) "wmv")))

(defn is-video2 [file]
  (let [extension (get-extension(.getPath file))]
    (cond (= extension "avi") true
              (= extension "wmv") true
              (= extension "mp4") true
              (= extension "mkv") true)))

(defn is-video-extension [file-name]
  (let [ext (get-extension file-name)]
    (or (= ext "avi")
          (= ext "wmv")
          (= ext "mp4")
          (= ext "mkv"))))

;test syntex
(defn sample-fun
  ([] (println "no args"))
  ([name] (println "got name" name)))
