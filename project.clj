(defproject loopme/log "0.1.4"
  :description "Naive clojure wrapper for Log4j2"
  :url "http://loopme.biz"
  :license {:name "Eclipse Public License"
            :url "http://www.eclipse.org/legal/epl-v10.html"}
  :dependencies [[org.clojure/clojure "1.6.0"]
                 [org.apache.logging.log4j/log4j-api "2.0.2"]
                 [org.apache.logging.log4j/log4j-core "2.0.2"]]
  :plugins [[s3-wagon-private "1.1.2"]]
  :repositories [["loopme" {:url           "s3p://lm-artifacts/releases/"
                            :username :env :passphrase :env
                            :sign-releases false}]]
  :profiles {:dev {:dependencies [[org.clojure/tools.trace "0.7.8"]]}})
