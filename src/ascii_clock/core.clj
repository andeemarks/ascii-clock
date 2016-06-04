(ns ascii-clock.core
  (:gen-class)
  (use [clojure.string :only [replace-first]]))

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

(defn map-minute [minutes]
  (/ (round-minutes minutes) 5))

(defn segment-to-location [segment]
  { :row (if (<= segment 6) (+ 1 segment) (- 13 segment))
    :position (if (< segment 6) 2 1)})

(defn update-row [row position token]
  (if (= position 1)
    (clojure.string/replace-first row #"o" token)
    (clojure.string/reverse (clojure.string/replace-first row #"o" token))))

(defn update-clock-for-segment [clock {row :row position :position} segment]
  (update-in clock [(- row 1)] update-row position segment))

(defn update-clock-for-minutes [clock coords]
  (update-clock-for-segment clock coords "m"))

(defn update-clock-for-hours [clock coords]
  (update-clock-for-segment clock coords "h"))

(defn format-clock [{hours :hours minutes :minutes}]
  (let [minute-update-coords (segment-to-location (map-minute minutes))
        hour-update-coords (segment-to-location hours)]
        (if (= minute-update-coords hour-update-coords)
          (update-clock-for-segment raw-clock minute-update-coords "x")
          (update-clock-for-minutes
            (update-clock-for-hours raw-clock hour-update-coords) minute-update-coords))))

(defn -main [& args]
  (let [time-spec (first args)]
    (println (str "Showing ascii clock for " time-spec))
    (dorun (map println (format-clock (parse-time time-spec))))))
