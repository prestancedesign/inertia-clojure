(ns inertia-example
  (:require [compojure.core :refer [defroutes GET]]
            [compojure.route :as route]
            [hiccup.page :as page]
            [inertia.middleware :as inertia]
            [ring.adapter.jetty :as http]
            [ring.middleware.json :refer [wrap-json-response]]))

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
  (GET "/" [] (inertia/render "index" {:name "World!"}))
  (GET "/demo" [] (inertia/render "demo" {:name "Clojure + Inertia"}))
  (route/resources "/"))

(def app
  (-> routes
      (inertia/wrap-inertia template asset-version)
      wrap-json-response))

(defn -main []
  (http/run-jetty #'app {:port 3000 :join? false}))
