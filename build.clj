(ns build
  (:require [clojure.tools.build.api :as b]
            [org.corfield.build :as bb]))

(def lib 'com.github.prestancedesign/inertia-clojure)

(def version "0.2.5")

(defn jar [opts]
  (-> opts
      (assoc :lib lib :version version)
      (bb/jar)))

(defn install [opts]
  (-> opts
      (assoc :lib lib :version version)
      (bb/install)))

(defn deploy [opts]
  (-> opts
      (assoc :lib lib :version version)
      (bb/deploy)))
