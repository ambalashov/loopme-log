(ns loopme.log
  (:import [org.apache.logging.log4j Logger LogManager ThreadContext]))

(set! *warn-on-reflection* true)

(defn ^Logger get-log
  ^{:tag Logger}
  [name]
  (LogManager/getLogger (str name)))

;Log levels
;OFF 	  0
;FATAL 	100
;ERROR 	200
;WARN 	300
;INFO 	400
;DEBUG 	500
;TRACE 	600
;ALL 	Integer.MAX_VALUE
;%level{FATAL=F, ERROR=E, WARN=W, INFO=I, DEBUG=D, TRACE=T}

(defn FATAL
  ([^Logger log ^String msg]
   (.fatal log msg))
  ([^Logger log ^String msg ^Throwable e]
   (.fatal log msg e)))

(defn ERROR
  ([^Logger log ^String msg]
   (.error log msg))
  ([^Logger log ^String msg ^Throwable e]
   (.error log msg e)))

(defn WARN
  ([^Logger log ^String msg]
   (.warn log msg))
  ([^Logger log ^String msg ^Throwable e]
   (.warn log msg e)))

(defn INFO
  ([^Logger log ^String msg]
   (.info log msg))
  ([^Logger log ^String msg ^Throwable e]
   (.info log msg e)))

(defn DEBUG
  ([^Logger log ^String msg]
   (.debug log msg))
  ([^Logger log ^String msg ^Throwable e]
   (.debug log msg e)))

(defn TRACE
  ([^Logger log ^String msg]
   (.trace log msg))
  ([^Logger log ^String msg ^Throwable e]
   (.trace log msg e)))

(defn CONTEXT-PUT
  "Put additional info in Thread Context Map.
  Usage: %X , %X{key}"
  [m]
  (doseq [e m]
    (ThreadContext/put (name (key e)) (str (val e)))))

(defn CONTEXT-CLEAR
  []
  (ThreadContext/clearMap))

(defmacro defnd [name doc args & body]
  "Push function name into Thread Context Stack.
  May be usefull for function call stack. Pattern %x"
  `(try
     (ThreadContext/push (name '~name))
     (defn ~name ~doc [~args] ~@body)
     (finally
       (ThreadContext/pop))))
