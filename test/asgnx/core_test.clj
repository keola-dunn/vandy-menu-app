(ns asgnx.core-test
  (:require [clojure.test :refer :all]
            [clojure.core.async :refer [<!!]]
            [clojure.spec.alpha :as s]
            [clojure.spec.test.alpha :as stest]
            [clojure.test.check.generators :as gen]
            [asgnx.core :refer :all]
            [asgnx.kvstore :as kvstore :refer [put! get!]]))



(deftest words-test
  (testing "that sentences can be split into their constituent words"
    (is (= ["a" "b" "c"] (words "a b c")))
    (is (= [] (words "   ")))
    (is (= [] (words nil)))
    (is (= ["a"] (words "a")))
    (is (= ["a"] (words "a ")))
    (is (= ["a" "b"] (words "a b")))))


(deftest cmd-test
  (testing "that commands can be parsed from text messages"
    (is (= "foo" (cmd "foo")))
    (is (= "foo" (cmd "foo x y")))
    (is (= nil   (cmd nil)))
    (is (= ""    (cmd "")))))


(deftest args-test
  (testing "that arguments can be parsed from text messages"
    (is (= ["x" "y"] (args "foo x y")))
    (is (= ["x"] (args "foo x")))
    (is (= [] (args "foo")))
    (is (= [] (args nil)))))


(deftest parsed-msg-test
  (testing "that text messages can be parsed into cmd/args data structures"
    (is (= {:cmd "foo"
            :args ["x" "y"]}
           (parsed-msg "foo x y")))
    (is (= {:cmd "foo"
            :args ["x"]}
           (parsed-msg "foo x")))
    (is (= {:cmd "foo"
            :args []}
           (parsed-msg "foo")))
    (is (= {:cmd "foo"
            :args ["x" "y" "z" "somereallylongthing"]}
           (parsed-msg "foo x y z somereallylongthing")))))

(deftest is-open-test
  (testing "testing is-open method to see if a restaurant is open"
    (is (= true (is-open {:cmd "x" :args ["ebi" "monday"]})))
    (is (= false (is-open {:cmd "x" :args ["towers" "friday"]})))
    (is (= true (is-open {:cmd "choose" :args ["rand" "saturday"]})))))

(deftest get-open-test
  (testing "testing get-open to retrieve the open time for a restaurant"
    (is (= "11am" (get-open ["pub" "monday"])))
    (is (= "7am" (get-open ["2301" "tuesday"])))
    (is (= "5:30pm" (get-open ["kissam" "sunday"])))))

(deftest get-closed-test
  (testing "check to see that retrieval of closing time is valid"
    (is (= "9pm" (get-closed ["pub" "monday"])))
    (is (= "10pm" (get-closed ["highland" "thursday"])))))

(deftest choose-dining-test
  (testing "check to see that the implementation of choose-dining is valid"
    (is (= "there is no such restaurant on Vanderbilt's campus"
           (choose-dining '{:cmd "choose" :args ["applebees" "friday"]})))
    (is (= (str "pub is open from 11am until 3pm. Menu: " menu)
           (choose-dining '{:cmd "choose" :args ["pub" "friday"]})))))


(deftest create-router-test
  (testing "correct creation of a function to lookup a handler for a parsed message"
    (let [router (create-router {"hello" #(str (:cmd %) " " "test")
                                 "argc"  #(count (:args %))
                                 "echo"  identity
                                 "default" (fn [& a] "No!")})
          msg1   {:cmd "hello"}
          msg2   {:cmd "argc" :args [1 2 3]}
          msg3   {:cmd "echo" :args ["a" "z"]}
          msg4   {:cmd "echo2" :args ["a" "z"]}]
      (is (= "hello test" ((router msg1) msg1)))
      (is (= "No!" ((router msg4) msg4)))
      (is (= 3 ((router msg2) msg2)))
      (is (= msg3 ((router msg3) msg3))))))
