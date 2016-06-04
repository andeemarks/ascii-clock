(ns ascii-clock.core
  (:gen-class)
  (use [clojure.string :only [replace-first reverse]]))

;;; This is an incorrect implementation, such as might be written by
;;; someone who was used to a Lisp in which an empty list is equal to
;;; nil.

(defn parse-time [time]
  (def matcher (re-matcher #"\d+" time))
  (let [hours (Integer/parseInt (re-find matcher))
        minutes (Integer/parseInt (re-find matcher))]
        {:hours hours :minutes minutes}))

(defn round-minutes [minutes]
  (- minutes (mod minutes 5)))

(defn map-minute [minutes]
  (/ (round-minutes minutes) 5))

(defn hand-location [hour]
  { :row (if (<= hour 6) (+ 1 hour) (- 13 hour)) 
    :position (if (< hour 6) 2 1)})

(defn update-row [row position token]
  (if (= position 1)
    (replace-first row #"o" marker)
    (reverse (replace-first row #"o" marker))))

(defn format-clock [{hours :hours minutes :minutes}]
  [ "    o    "
    "  o   o  "
    " o     o "
    "m       h"
    " o     o "
    "  o   o  "
    "    o    "])

(defn -main []
  (dorun (map println (format-clock {:hours 12 :minutes 19}))))
