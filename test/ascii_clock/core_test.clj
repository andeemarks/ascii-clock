(ns ascii-clock.core-test
  (:use midje.sweet)
  (:use [ascii-clock.core]))

(facts "about time parsing"
  (fact "it should identify a valid hour"
    (:hours (parse-time "21:35")) => 21
    (:hours (parse-time "04:35")) => 4
    (:hours (parse-time "24:35")) => nil)
  (fact "it should identify the minutes"
    (:minutes (parse-time "21:35")) => 35
    (:minutes (parse-time "21:05")) => 5
    (:minutes (parse-time "21:65")) => nil)
  )

(facts "about minute rounding"
  (fact "it should round down to the nearest 5 minutes"
    (round-minutes 1) => 0
    (round-minutes 46) => 45
    (round-minutes 35) => 35))

(facts "about minute mapping"
  (fact "it should map minutes to clock face positions"
    (map-minute 0) => 0
    (map-minute 5) => 1
    (map-minute 10) => 2
    (map-minute 17) => 3
    (map-minute 45) => 9))
