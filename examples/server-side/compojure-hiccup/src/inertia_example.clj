(ns inertia-example
  (:require [compojure.core :refer [defroutes GET]]
            [compojure.route :as route]
            [hiccup.page :as page]
            [hiccup.util :as h]
            [inertia.middleware :as inertia]
            [org.httpkit.client :as http]
            [org.httpkit.server :as server]
            [jsonista.core :as json]))

(def asset-version "1")

(defn template [data-page]
  (let [render @(http/post "http://127.0.0.1:13714/render" {:body data-page
                                                            :headers {"Content-Type" "application/json"}})
        {:keys [body head]} (json/read-value (:body render) json/keyword-keys-object-mapper)]
    (page/html5
     [:head
      [:meta {:charset "utf-8"}]
      [:title "Inertia + Clojure example"]
      (for [h head]
        (h/as-str h))]
     [:body
      (if (seq body)
        body
        [:div {:id "app"
               :data-page data-page}])
      (page/include-js "/js/app.js")])))

(defroutes routes
  (GET "/" [] (inertia/render "index" {:title "Hello World!"}))
  (GET "/demo" [] (inertia/render "demo" {:title "Clojure + Inertia"
                                          :date (str (java.util.Date.))}))
  (route/resources "/"))

(def app (inertia/wrap-inertia routes template asset-version))

(defn -main []
  (server/run-server #'app {:port 3000}))
