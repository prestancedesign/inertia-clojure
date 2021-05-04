(ns inertia-example
  (:require [inertia.middleware :as inertia]
            [reitit.ring :as ring]
            [ring.adapter.jetty :as http]
            [selmer.parser :as html]))

(def asset-version "1")

(defn template [data-page]
  (html/render-file "templates/base.html" {:page data-page}))

(def app
  (ring/ring-handler
   (ring/router
    [["/" (fn [_] (inertia/render "index" {:title "Hello World!"}))]
     ["/demo" (fn [_] (inertia/render "demo"
                                     {:title "Clojure + Inertia"
                                      :date (str (java.util.Date.))}))]])
   (ring/create-resource-handler {:path "/"})
   {:middleware [[inertia/wrap-inertia template asset-version]]}))

(defn -main []
  (http/run-jetty #'app {:port 3000 :join? false}))
