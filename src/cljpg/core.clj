(ns cljpg.core)

(require
 '[cryptohash-clj.api :as ch]
 '[clojure.string :as str]
 '[clojure.java.io :as io])

(def modes (hash-map 3200 "bcrypt"))

(defn verifyNSave [mode pair pf]
  (if (ch/verify-with (keyword (get modes mode)) (second pair) (first pair))
    (spit pf (str/join (vector (first pair) ":" (second pair) "\n")) :append true)
    ()))

(defn run [mode ll wl pf]
  (doseq
   [pair (zipmap
           (str/split (str/replace (slurp ll) "\r" "")  #"\n")
           (str/split (str/replace (slurp wl) "\r" "")  #"\n"))]
   (verifyNSave mode pair pf)))
 
(run 3200 "D:\\cljpg\\cljpg\\resources\\ll" "D:\\cljpg\\cljpg\\resources\\wl" "D:\\cljpg\\cljpg\\resources\\pf")

(defn -main [mode ll wl pf]
  (run mode ll wl pf))
