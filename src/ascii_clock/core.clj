(ns ascii-clock.core
  (:gen-class))

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

(defn format-clock [{hours :hours minutes :minutes}]
  ["   o   "
    "  o o  "
    " o   o "
    "m     h"
    " o   o "
    "  o o  "
    "   o   "])

(defn -main []
  (dorun (map println (format-clock {:hours 12 :minutes 19}))))
