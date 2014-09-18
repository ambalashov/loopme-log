(ns loopme.log-test
  (:import [org.apache.logging.log4j ThreadContext Logger LogManager]
           [java.util UUID])
  (:require [loopme.log :refer :all]))

(def ^{:private true} log (get-log *ns*))

(set! *warn-on-reflection* true)

(defn some-function []
  (ThreadContext/push "some-function")
  (TRACE log "in fun")
  (ThreadContext/pop))

(defn fun1 []
  (ThreadContext/push "fun1")
  (CONTEXT-PUT {:id (.toString (UUID/randomUUID))})
  ;(ThreadContext/put "id" (.toString (UUID/randomUUID)))
  (let [start (System/nanoTime)]
    (dotimes [_ 10]
      (some-function)
      (DEBUG log "main starting...")
      (.debug ^Logger log "debuf str"))
    (INFO log (str (* (- (System/nanoTime) start) 0.000000001))))
  (INFO log "main stop...")
  (CONTEXT-CLEAR)
  (ThreadContext/pop))

(defn fun2 []
  (ThreadContext/push "fun2")
  (CONTEXT-PUT {:key (.toString (UUID/randomUUID))})
  ;(ThreadContext/put "id" (.toString (UUID/randomUUID)))
  (let [start (System/nanoTime)]
    (dotimes [_ 10]
      (some-function)
      (INFO log "main starting..."))
    (INFO log (str (* (- (System/nanoTime) start) 0.000000001))))
  (INFO log "main stop...")
  (CONTEXT-CLEAR)
  (ThreadContext/pop))

(defn fun3
  []
  (ThreadContext/push "fun3")
  (DEBUG log "another ns")
  (ThreadContext/pop))

(defnd fun4
       [msg]
       (DEBUG log (str "fun4 " msg)))

(defn run []
  (ThreadContext/push "main")
  (INFO log "main starting...")

  (fun1)
  (deref (future-call fun2))
  (fun3)
  (fun4 " oops")
  (ThreadContext/pop))

(comment
  (run)
)