(ns asgnx.core
  (:require [clojure.string :as string]
            [clojure.core.async :as async :refer [go chan <! >!]]
            [asgnx.kvstore :as kvstore
             :refer [put! get! list! remove!]]))

(def resturants (list "pub" "2301" "rand" "commons" "towers" "highland" "kissam" "ebi"))


(def menu "https://campusdining.vanderbilt.edu/menus/")

(def pub-hours {"sunday" {:open "3pm"
                          :close "9pm"}
                "monday" {:open "11am"
                          :close "9pm"}
                "tuesday" {:open "11am"
                           :close "9pm"}
                "wednesday" {:open "11am"
                             :close "9pm"}
                "thursday" {:open "11am"
                            :close "9pm"}
                "friday" {:open "11am"
                          :close "3pm"}
                "saturday" {:closed true}})

(def a2301-hours {"sunday" {:open "4:30pm"
                            :close "8pm"}
                  "monday" {:open "7am"
                            :close "8pm"}
                  "tuesday" {:open "7am"
                             :close "8pm"}
                  "wednesday" {:open "7am"
                               :close "8pm"}
                  "thursday" {:open "7am"
                              :close "8pm"}
                  "friday" {:open "7am"
                            :close "3pm"}
                  "saturday" {:closed true}})

(def rand-hours {"sunday" {:open "10am"
                           :close "8pm"}
                 "monday" {:open "7am"
                           :close "8pm"}
                 "tuesday" {:open "7am"
                            :close "8pm"}
                 "wednesday" {:open "7am"
                              :close "8pm"}
                 "thursday" {:open "7am"
                             :close "8pm"}
                 "friday" {:open "7am"
                           :close "8pm"}
                 "saturday" {:open "10am"
                             :close "8pm"}})

(def commons-hours {"sunday" {:open "10am"
                              :close "8pm"}
                    "monday" {:open "7am"
                              :close "8pm"}
                    "tuesday" {:open "7am"
                               :close "8pm"}
                    "wednesday" {:open "7am"
                                 :close "8pm"}
                    "thursday" {:open "7am"
                                :close "8pm"}
                    "friday" {:open "7am"
                              :close "8pm"}
                    "saturday" {:open "10am"
                                :close "8pm"}})

(def rocket-subs-hrs {"sunday" {:open "11am"
                                       :close "10pm"}
                             "monday" {:open "11am"
                                       :close "10pm"}
                             "tuesday" {:open "11am"
                                        :close "10pm"}
                             "wednesday" {:open "11am"
                                          :close "10pm"}
                             "thursday" {:open "11am"
                                         :close "10pm"}
                             "friday" {:closed true}
                             "saturday" {:closed true}})

(def CH-hours {"sunday" {:open "5:30pm"
                             :close "7:30pm"}
                   "monday" {:open "7:30am"
                             :close "7:30pm"}
                   "tuesday" {:open "7:30am"
                              :close "7:30pm"}
                   "wednesday" {:open "7:30am"
                                :close "7:30pm"}
                   "thursday" {:open "7:30am"
                               :close "7:30pm"}
                   "friday" {:open "7:30am"
                             :close "2pm"}
                   "saturday" {:closed true}})


(def resturant-hours {"pub" pub-hours
                      "2301" a2301-hours
                      "rand" rand-hours
                      "commons" commons-hours
                      "towers" rocket-subs-hrs
                      "highland" rocket-subs-hrs
                      "kissam" CH-hours
                      "ebi" CH-hours})



;; This is a helper function that you might want to use to implement
;; `cmd` and `args`.
(defn words [msg]
  (if msg
      (string/split msg #" ")
      []))

;; Asgn 1.
;;
;; @Todo: Fill in this function to return the first word in a text
;; message.
;;
;; Example: (cmd "foo bar") => "foo"
;;
;; See the cmd-test in test/asgnx/core_test.clj for the
;; complete specification.
;;
(defn cmd [msg]
  (if (= [] (words msg))
    nil
    (get (words msg) 0)))


;; Asgn 1.
;;
;; @Todo: Fill in this function to return the list of words following
;; the command in a text message.
;;
;; Example: (args "foo bar baz") => ("bar" "baz")
;;
;; See the args-test in test/asgnx/core_test.clj for the
;; complete specification.
;;
(defn args [msg]
  (if (= [] (words msg))
    []
    (rest (words msg)))) ;;this isnt done yet

;; Asgn 1.
;;
;; @Todo: Fill in this function to return a map with keys for the
;; :cmd and :args parsed from the msg.
;;
;; Example:
;;
;; (parsed-msg "foo bar baz") => {:cmd "foo" :args ["bar" "baz"]}
;;
;; See the parsed-msg-test in test/asgnx/core_test.clj for the
;; complete specification.
;;
(defn parsed-msg [msg]
  {:cmd (cmd msg) :args (args msg)})

;; Asgn 1.
;;
;; @Todo: Fill in this function to prefix the first of the args
;; in a parsed message with "Welcome " and return the result.
;;
;; Example:
;;
;; (welcome {:cmd "welcome" :args ["foo"]}) => "Welcome foo"
;;
;; See the welcome-test in test/asgnx/core_test.clj for the
;; complete specification.
;;
(defn welcome [pmsg]
  (str "Welcome " (first (get pmsg :args))))


;; Asgn 2.
;;
;; @Todo: Create a function called action-send-msg that takes
;; a destination for the msg in a parameter called `to`
;; and the message in a parameter called `msg` and returns
;; a map with the keys :to and :msg bound to each parameter.
;; The map should also have the key :action bound to the value
;; :send.
;;
(defn action-send-msg [to msg]
  ;;needs to handle multiple "to" people, and return them in a list
  (hash-map :action :send, :to to, :msg msg))


;; Asgn 2.
;;
;; @Todo: Create a function called action-send-msgs that takes
;; takes a list of people to receive a message in a `people`
;; parameter and a message to send them in a `msg` parmaeter
;; and returns a list produced by invoking the above `action-send-msg`
;; function on each person in the people list.
;;
;; java-like pseudo code:
;;
;; output = new list
;; for person in people:
;;   output.add( action-send-msg(person, msg) )
;; return output
;;
(defn action-send-msgs [people msg]
  (map #(action-send-msg % msg) people))

;; Asgn 2.
;;
;; @Todo: Create a function called action-insert that takes
;; a list of keys in a `ks` parameter, a value to bind to that
;; key path to in a `v` parameter, and returns a map with
;; the key :ks bound to the `ks` parameter value and the key :v
;; vound to the `v` parameter value.)
;; The map should also have the key :action bound to the value
;; :assoc-in.
;;
(defn action-insert [ks v]
  {:action :assoc-in, :ks ks, :v v})

;; Asgn 2.
;;
;; @Todo: Create a function called action-inserts that takes:
;; 1. a key prefix (e.g., [:a :b])
;; 2. a list of suffixes for the key (e.g., [:c :d])
;; 3. a value to bind
;;
;; and calls (action-insert combined-key value) for each possible
;; combined-key that can be produced by appending one of the suffixes
;; to the prefix.
;;
;; In other words, this invocation:
;;
;; (action-inserts [:foo :bar] [:a :b :c] 32)
;;
;; would be equivalent to this:
;;
;; [(action-insert [:foo :bar :a] 32)
;;  (action-insert [:foo :bar :b] 32)
;;  (action-insert [:foo :bar :c] 32)]
;;
(defn action-inserts [prefix ks v]
  (map #(action-insert % v) (map #(conj prefix %) ks)))

;; Asgn 2.
;;
;; @Todo: Create a function called action-remove that takes
;; a list of keys in a `ks` parameter and returns a map with
;; the key :ks bound to the `ks` parameter value.
;; The map should also have the key :action bound to the value
;; :dissoc-in.
;;
(defn action-remove [ks]
  {:action :dissoc-in, :ks ks})

;; Asgn 3.
;;
;; @Todo: Create a function called "experts-register"
;; that takes the current application `state`, a `topic`
;; the expert's `id` (e.g., unique name), and information
;; about the expert (`info`) and registers them as an expert on
;; the specified topic. Look at the associated test to see the
;; expected function signature.
;;
;; Your function should NOT directly change the application state
;; to register them but should instead return a list of the
;; appropriate side-effects (above) to make the registration
;; happen (hint: action-insert).
;;
;; See the integration test in See handle-message-test for the
;; expectations on how your code operates
;;

;;experts is the state
(defn experts-register [experts topic id info]
  [(action-insert [:expert topic id] info)])


;; Asgn 3.
;;
;; @Todo: Create a function called "experts-unregister"
;; that takes the current application `state`, a `topic`
;; and the expert's `id` (e.g., unique name) and then
;; removes the expert from the list of experts on that topic.
;; Look at the associated test to see the expected function signature.
;;
;; Your function should NOT directly change the application state
;; to unregister them but should instead return a list of the
;; appropriate side-effects (above) to make the registration
;; happen (hint: action-remove).
;;
;; See the integration test in See handle-message-test for the
;; expectations on how your code operates
;;
(defn experts-unregister [experts topic id]
  [{:action :action-remove, :ks [:expert topic id]}])

(defn experts-question-msg [experts question-words]
  (str "Asking " (count experts) " expert(s) for an answer to: \""
       (string/join " " question-words) "\""))

;; Asgn 3.
;;
;; @Todo: Create a function called "ask-experts"
;; that takes two parameters:
;;
;; 1. the list of experts on the topic
;; 2. a parsed message with the format:
;;    {:cmd "ask"
;;     :user-id "phone number that sent the message"
;;     :args [topic question-word1 question-word2 ... question-wordN]}
;;
;; The sender of the message will be identified by their phone number
;; in the user-id parameter. This is the phone number that you will need
;; to forward answers to the question to.
;;
;; The parsed message is generated by breaking up the words in the ask
;; text message. For example, if someone sent the message:
;;
;; "ask food what is the best pizza in nashville"
;;
;; The parsed message would be:
;;
;; {:cmd "ask"
;;  :user-id "+15555555555"
;;  :args ["food" "what" "is" "the" "best" "pizza" "in" "nashville"]}
;;
;; This function needs to return a list with two elements:
;; [[actions...] "response to asker"]
;;
;; The actions in the list are the *side effects* that need to take place
;; to ask the question (e.g., sending messages to the experts). The string
;; is the response that is going to be sent back to the person that asked
;; the question (e.g. "Asking 2 expert(s) for an answer to ....").
;;
;; The correct string response to a valid question should be produced with
;; the `experts-question-msg` function above.
;;
;; Think about how you are going to figure out where to route messages
;; when an expert answers (see the conversations query) and make sure you
;; handle the needed side effect for storing the conversation state.
;;
;; If there are no registered experts on a topic, you should return an
;; empty list of actions and "There are no experts on that topic."
;;
;; If there isn't a question, you should return "You must ask a valid question."
;;
;; Why this strange architecture? By returning a list of the actions to take,
;; rather than directly taking that action, we can keep this function pure.
;; Pure functions are WAY easier to test / maintain. Also, we can isolate our
;; messy impure action handling at the "edges" of the application, where it is
;; easier to track and reason about.
;;
;; You should look at `handle-message` to get an idea of the way that this
;; function is going to be used, its expected signature, and how the actions
;; and output are going to work.
;;
;; See the integration test in See handle-message-test for the
;; expectations on how your code operates
;;
(defn ask-experts [experts {:keys [args user-id]}]
  (if (<= (count args) 1)
    (list [] "You must ask a valid question.")
    (if (= true (empty? experts))
      (list [] "There are no experts on that topic.")
      (if (= (count experts) 1)
        (list [(action-send-msg (first experts) (clojure.string/join " " (rest args)))
               (action-insert [:conversations (first experts)] {:last-question (rest args) , :asker user-id})]
              (experts-question-msg experts (rest args)))
        (list
              (concat (action-send-msgs experts (clojure.string/join " " (rest args)))
                      (action-inserts [:conversations] [experts] [{:last-question (rest args) , :asker user-id}]))
              (experts-question-msg experts (rest args)))))))


;; Asgn 3.
;;
;; @Todo: Create a function called "answer-question"
;; that takes two parameters:
;;
;; 1. the last conversation describing the last question that was routed
;;    to the expert
;; 2. a parsed message with the format:
;;    {:cmd "ask"
;;     :user-id "+15555555555"
;;     :args [topic answer-word1 answer-word2 ... answer-wordN]}
;;
;; The parsed message is generated by breaking up the words in the ask
;; text message. For example, if someone sent the message:
;;
;; "answer joey's house of pizza"
;;
;; The conversation will be data that you store as a side-effect in
;; ask-experts. You probably want this data to be information about the
;; last question asked to each expert. See the "think about" comment above.
;;
;; The parsed message would be:
;;
;; {:cmd "answer"
;;  :user-id "+15555555555"
;;  :args ["joey's" "house" "of" "pizza"]}
;;
;; This function needs to return a list with two elements:
;; [[actions...] "response to expert answering"]
;;
;; The actions in the list are the *side effects* that need to take place
;; to send the answer to the original question asker. The string
;; is the response that is going to be sent back to the expert answering
;; the question.
;;
;; Think about how you are going to figure out where to route messages
;; when an expert answers (see the conversations query) and make sure you
;; handle the needed side effect for storing the conversation state.
;;
;; Why this strange architecture? By returning a list of the actions to take,
;; rather than directly taking that action, we can keep this function pure.
;; Pure functions are WAY easier to test / maintain. Also, we can isolate our
;; messy impure action handling at the "edges" of the application, where it is
;; easier to track and reason about.
;;
;; You should look at `handle-message` to get an idea of the way that this
;; function is going to be used, its expected signature, and how the actions
;; and output are going to work.
;;
;; See the integration test in See handle-message-test for the
;; expectations on how your code operates
;;
(defn answer-question [conversation {:keys [args]}]
  (if (empty? args)
    (list [] "You did not provide an answer.")
    (if (contains? conversation :last-question)
      (list [(action-send-msg (get conversation :asker) (clojure.string/join " " args))] "Your answer was sent.")
      (list [] "You haven't been asked a question."))))



;; Asgn 3.
;;
;; @Todo: Create a function called "add-expert"
;; that takes two parameters:
;;
;; 1. the current list of experts on the topic
;; 2. a parsed message with the format:
;;    {:cmd "expert"
;;     :user-id "+15555555555"
;;     :args [topic]
;;
;;
;; The parsed message is generated by breaking up the words in the expert
;; text message. For example, if someone sent the message:
;;
;; "expert food"
;;
;; The parsed message would be:
;;
;; {:cmd "expert"
;;  :user-id "+15555555555"))
;;  :args ["food"]}
;;
;; This function needs to add "sara" to the list of experts on "food" and
;; associate her phone number with her ID.
;;
;; This function needs to return a list with two elements:
;; [[actions...] "response to the person adding themselves as an expert"]
;;
;; The actions in the list are the *side effects* that need to take place
;; to add the person as an expert on the topic (hint: result of calling experts-register). The string
;; is the response that is going to be sent back to the person adding themselves
;; as an expert.
;;
;; You should look at `handle-message` to get an idea of the way that this
;; function is going to be used, its expected signature, and how the actions
;; and output are going to work.
;;
;; See the integration test in See handle-message-test for the
;; expectations on how your code operates
(defn add-expert [experts {:keys [args user-id]}]
  (list (experts-register experts (first args) user-id {})
        (str user-id " is now an expert on " (first args) ".")))

(defn is-open
  [args]
  (if (contains? (get (get resturant-hours (first args)) (second args)) :closed)
    false
    true))

(defn get-open
  [args]
  (get (get (get resturant-hours (first args)) (second args)) :open))

(defn get-closed
  [args]
  (get (get (get resturant-hours (first args)) (second args)) :close))


(defn choose-dining [{:keys [args cmd]}]
  (if (some #{(first args)} resturants)
    (if (is-open args)
      (str (first args) " is open from "
           (get-open args) " until "
           (get-closed args)". Menu: " menu)
      (str (first args) " is closed on " (second args)))
    (str "there is no such resturant on Vanderbilt's campus")))


(defn show-dining-options
  "End goal - this will return a string of all the resturants currently
  open. Currently, this presents the commands the user can text the
  application."
  [_]
  (str "rand, commons, 2301, pub, towers (rocket subs), highland (rocket subs),
ebi, kissam"))


;; Don't edit!
(defn stateless [f]
  (fn [_ & args]
    [[] (apply f args)]))


(def routes {"default"  (stateless (fn [& args] "Unknown command."))
             "ask"      ask-experts
             "answer"   answer-question
             "expert"   add-expert
             "choose"   (stateless choose-dining)
             "what"     (stateless show-dining-options)})

;; Asgn 3.
;;
;; @Todo: Add mappings of the cmds "expert", "ask", and "answer" to
;; to the `routes` map so that the functions that you
;; created will be invoked when the corresponding text message
;; commands are received.
;;})


;; Don't edit!
(defn experts-on-topic-query [state-mgr pmsg]
  (let [[topic]  (:args pmsg)]
    (list! state-mgr [:expert topic])))


;; Don't edit!
(defn conversations-for-user-query [state-mgr pmsg]
  (let [user-id (:user-id pmsg)]
    (get! state-mgr [:conversations user-id])))


;; Don't edit!
(def queries
  {"expert" experts-on-topic-query
   "ask"    experts-on-topic-query
   "answer" conversations-for-user-query})


;; Don't edit!
(defn read-state [state-mgr pmsg]
  (go
    (if-let [qfn (get queries (:cmd pmsg))]
      (<! (qfn state-mgr pmsg))
      {})))


;; Asgn 1.
;;
;; @Todo: This function should return a function (<== pay attention to the
;; return type) that takes a parsed message as input and returns the
;; function in the `routes` map that is associated with a key matching
;; the `:cmd` in the parsed message. The returned function would return
;; `welcome` if invoked with `{:cmd "welcome"}`.
;;
;; Example:
;;
;; (let [msg {:cmd "welcome" :args ["bob"]}]
;;   (((create-router {"welcome" welcome}) msg) msg) => "Welcome bob"
;;
;; If there isn't a function in the routes map that is mapped to a
;; corresponding key for the command, you should return the function
;; mapped to the key "default".
;;
;; See the create-router-test in test/asgnx/core_test.clj for the
;; complete specification.
;;
(defn create-router [routes]
  (fn [in]
    (if (contains? routes (get in :cmd))
      (get routes (get in :cmd))
      (get routes "default"))))



;; Don't edit!
(defn output [o]
  (second o))


;; Don't edit!
(defn actions [o]
  (first o))


;; Don't edit!
(defn invoke [{:keys [effect-handlers] :as system} e]
  (go
    (println "    Invoke:" e)
    (if-let [action (get effect-handlers (:action e))]
      (do
        (println "    Invoking:" action "with" e)
        (<! (action system e))))))


;; Don't edit!
(defn process-actions [system actions]
  (go
    (println "  Processing actions:" actions)
    (let [results (atom [])]
      (doseq [action actions]
        (let [result (<! (invoke system action))]
          (swap! results conj result)))
      @results)))


;; Don't edit!
(defn handle-message
  "
    This function orchestrates the processing of incoming messages
    and glues all of the pieces of the processing pipeline together.

    The basic flow to handle a message is as follows:

    1. Create the router that will be used later to find the
       function to handle the message
    2. Parse the message
    3. Load any saved state that is going to be needed to process
       the message (e.g., querying the list of experts, etc.)
    4. Find the function that can handle the message
    5. Call the handler function with the state from #3 and
       the message
    6. Run the different actions that the handler returned...these actions
       will be bound to different implementations depending on the environemnt
       (e.g., in test, the actions aren't going to send real text messages)
    7. Return the string response to the message

  "
  [{:keys [state-mgr] :as system} src msg]
  (go
    (println "=========================================")
    (println "  Processing:\"" msg "\" from" src)
    (let [rtr    (create-router routes)
          _      (println "  Router:" rtr)
          pmsg   (assoc (parsed-msg msg) :user-id src)
          _      (println "  Parsed msg:" pmsg)
          state  (<! (read-state state-mgr pmsg))
          _      (println "  Read state:" state)
          hdlr   (rtr pmsg)
          _      (println "  Hdlr:" hdlr)
          [as o] (hdlr state pmsg)
          _      (println "  Hdlr result:" [as o])
          arslt  (<! (process-actions system as))
          _      (println "  Action results:" arslt)]
      (println "=========================================")
      o)))
