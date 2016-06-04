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

(facts "about hand location"
  (fact "each hour should map to a row and position within row"
    (hand-location 1) => {:row 2 :position 2}
    (hand-location 2) => {:row 3 :position 2}
    (hand-location 3) => {:row 4 :position 2}
    (hand-location 4) => {:row 5 :position 2}
    (hand-location 5) => {:row 6 :position 2}
    (hand-location 6) => {:row 7 :position 1}
    (hand-location 7) => {:row 6 :position 1}
    (hand-location 8) => {:row 5 :position 1}
    (hand-location 9) => {:row 4 :position 1}
    (hand-location 10) => {:row 3 :position 1}
    (hand-location 11) => {:row 2 :position 1}
    (hand-location 12) => {:row 1 :position 1}
    ))

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
