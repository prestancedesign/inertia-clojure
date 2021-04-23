(ns inertia-example
  (:require [compojure.core :refer [defroutes GET]]
            [compojure.route :as route]
            [hiccup.page :as page]
            [inertia.middleware :as inertia]
            [ring.adapter.jetty :as http]))

(def asset-version "1")

(defn template [data-page]
  (page/html5
   [:head
    [:meta {:charset "utf-8"}]
    [:title "Inertia + Clojure example"]]
   [:body
    [:div {:id "app"
           :data-page data-page}]
    (page/include-js "/js/app.js")]))

(defroutes routes
  (GET "/" [] (inertia/render "index" {:title "Hello World!"}))
  (GET "/demo" [] (inertia/render "demo" {:title "Clojure + Inertia"
                                          :date (str (java.util.Date.))}))
  (route/resources "/"))

(def app (inertia/wrap-inertia routes template asset-version))

(defn -main []
  (http/run-jetty #'app {:port 3000 :join? false}))
