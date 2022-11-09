(ns cljpg.core)

(require '[cryptohash-clj.api :as ch])
(require '[clojure.string :as str])
(require '[clojure.java.io :as io])


(def ll (io/resource "ll"))
(def wl (io/resource "wl"))
(def pf (io/resource "pf"))

(defn verify [pair]
  (if (ch/verify-with :bcrypt (second pair) (first pair))
      (spit pf (str/join (vector (first pair) ":" (second pair) "\n")) :append true)
      ()))

(defn run []
  (map
   verify 
   (zipmap 
    (str/split (str/replace (slurp ll) "\r" "")  #"\n")
    (str/split (str/replace (slurp wl) "\r" "")  #"\n"))))
 
(run)
