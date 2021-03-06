(ns ascii-clock.core-test
  (:use midje.sweet)
  (:use [ascii-clock.core]))

(facts "about time parsing"
  (fact "it should identify a valid hour"
    (:hours (parse-time "21:35")) => 21
    (:hours (parse-time "04:35")) => 4)
  (future-fact "it should refuse invalid hours"
    (:hours (parse-time "24:35")) => nil)
  (fact "it should identify the minutes"
    (:minutes (parse-time "21:35")) => 35
    (:minutes (parse-time "21:05")) => 5)
  (future-fact "it should refuse invalid minutes"
    (:minutes (parse-time "21:65")) => nil))

(facts "about minute mapping"
  (fact "it should map minutes to clock face positions"
    (map-minute 0) => 0
    (map-minute 1) => 0
    (map-minute 5) => 1
    (map-minute 10) => 2
    (map-minute 17) => 3
    (map-minute 45) => 9
    (map-minute 49) => 9
    (map-minute 46) => 9))

(facts "about mapping segments to hand locations"
  (fact "each segment should map to a row and position within row"
    (segment-to-coords 1) => {:row 1 :position 2}
    (segment-to-coords 2) => {:row 2 :position 2}
    (segment-to-coords 3) => {:row 3 :position 2}
    (segment-to-coords 4) => {:row 4 :position 2}
    (segment-to-coords 5) => {:row 5 :position 2}
    (segment-to-coords 6) => {:row 6 :position 1}
    (segment-to-coords 7) => {:row 5 :position 1}
    (segment-to-coords 8) => {:row 4 :position 1}
    (segment-to-coords 9) => {:row 3 :position 1}
    (segment-to-coords 10) => {:row 2 :position 1}
    (segment-to-coords 11) => {:row 1 :position 1}
    (segment-to-coords 12) => {:row 0 :position 1}))

(facts "about update-row"
  (fact "it can update both positions in rows with the supplied token"
    (update-row " o   o " 1 "h") => " h   o "
    (update-row " o   o " 2 "m") => " o   m "))

(facts "about format clock"
  (fact "it should show the hours and minutes correctly when different"
    (format-clock {:hours 3 :minutes 50}) => ["    o    "
                                              "  o   o  "
                                              " m     o "
                                              "o       h"
                                              " o     o "
                                              "  o   o  "
                                              "    o    "])
  (fact "it should show the hours and minutes correctly when same"
    (format-clock {:hours 3 :minutes 15}) => ["    o    "
                                              "  o   o  "
                                              " o     o "
                                              "o       x"
                                              " o     o "
                                              "  o   o  "
                                              "    o    "]))
