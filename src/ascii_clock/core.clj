(ns ascii-clock.core
  (:gen-class))

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

(defn segment-to-coords [segment]
  { :row (if (<= segment 6) (+ 1 segment) (- 13 segment))
    :position (if (< segment 6) 2 1)})

(defn update-row [row position segment]
  (if (= position 1)
    (clojure.string/replace-first row #"o" segment)
    (clojure.string/reverse (clojure.string/replace-first row #"o" segment))))

(defn- update-clock-for-segment [clock {row :row position :position} segment]
  (update-in clock [(- row 1)] update-row position segment))

(defn format-clock [{hours :hours minutes :minutes}]
  (let [minute-hand-coords (segment-to-coords (map-minute minutes))
        hour-hand-coords (segment-to-coords hours)]
        (if (= minute-hand-coords hour-hand-coords)
          (update-clock-for-segment raw-clock minute-hand-coords "x")
          (update-clock-for-segment
            (update-clock-for-segment raw-clock hour-hand-coords "h") minute-hand-coords "m"))))

(defn -main [& args]
  (let [time-spec (first args)]
    (println (str "Showing ascii clock for " time-spec))
    (dorun (map println (format-clock (parse-time time-spec))))))
