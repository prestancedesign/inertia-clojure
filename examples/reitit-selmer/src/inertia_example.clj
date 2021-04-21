(ns inertia-example
  (:require [muuntaja.core :as m]
            [inertia.middleware :as inertia]
            [reitit.ring :as ring]
            [reitit.ring.middleware.muuntaja :as muuntaja]
            [ring.adapter.jetty :as http]
            [selmer.parser :as html]))

(def asset-version "1")

(defn template [data-page]
  (html/render-file "templates/base.html" {:page data-page}))

(def app
  (ring/ring-handler
   (ring/router
    [["/" (inertia/render "index"
                          {:name "Superman"})]
     ["/demo" (inertia/render "demo"
                              {:name "Batman"})]]
    {:data {:muuntaja   m/instance
            :middleware [muuntaja/format-middleware
                         [inertia/wrap-inertia template asset-version]]}})
   (ring/create-resource-handler {:path "/"
                                  :root "public"})))

(defn -main []
  (http/run-jetty #'app {:port 3000 :join? false}))
