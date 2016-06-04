(ns ascii-clock.core
  (:gen-class)
  (use [clojure.string :only [replace-first]]))

;;; This is an incorrect implementation, such as might be written by
;;; someone who was used to a Lisp in which an empty list is equal to
;;; nil.

(def raw-clock
[ "    o    "
  "  o   o  "
  " o     o "
  "o       o"
  " o     o "
  "  o   o  "
  "    o    "])

(defn parse-time [time]
  (def matcher (re-matcher #"\d+" time))
  (let [hours (Integer/parseInt (re-find matcher))
        minutes (Integer/parseInt (re-find matcher))]
        {:hours hours :minutes minutes}))

(defn round-minutes [minutes]
  (- minutes (mod minutes 5)))

(defn map-minute [minutes]
  (/ (round-minutes minutes) 5))

(defn hour-to-location [hour]
  { :row (if (<= hour 6) (+ 1 hour) (- 13 hour))
    :position (if (< hour 6) 2 1)})

(defn update-row [row position token]
  (if (= position 1)
    (replace-first row #"o" token)
    (clojure.string/reverse (replace-first row #"o" token))))

(defn update-clock-for-minutes [clock {row :row position :position}]
  (update-in clock [(- row 1)] update-row position "m"))

(defn update-clock-for-hours [clock {row :row position :position}]
  (update-in clock [(- row 1)] update-row position "h"))

(defn format-clock [{hours :hours minutes :minutes}]
  (let [minute-update-coords (hour-to-location (map-minute (round-minutes minutes)))
        hour-update-coords (hour-to-location hours)]
        (update-clock-for-minutes
          (update-clock-for-hours raw-clock hour-update-coords) minute-update-coords)))

(defn -main [& args]
  (dorun (map println (format-clock {:hours 12 :minutes 19}))))
