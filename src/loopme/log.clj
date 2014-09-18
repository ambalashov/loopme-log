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

;grep '*'    foo.log    Find all errors in log.
;grep '!'    foo.log    Find all warnings in log.
;grep '*\|!' foo.log    Find all warnings AND errors in log!  The pipe is
;the OR operator in grep, but it needs backslash protection.

(defn FATAL ([^Logger log ^String msg] (.fatal log msg)))

(defn ERROR ([^Logger log ^String msg] (.error log msg)))

(defn WARN ([^Logger log ^String msg] (.warn log msg)))

(defn INFO ([^Logger log ^String msg] (.info log msg)))

(defn DEBUG ([^Logger log ^String msg] (.debug log msg)))

(defn TRACE ([^Logger log ^String msg] (.trace log msg)))

(defn CONTEXT-PUT
  "Put additional info in Thread Context Map.
  Usage: %X , %X{key}"
  [m]
  (doseq [e m]
    (ThreadContext/put (name (key e)) (val e))))

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
