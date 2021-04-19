(ns prestancedesign.inertia-clojure
  (:require [clojure.data.json :as json]
            [ring.util.response :as rr]))

(defn render
  [component props]
  (fn [request]
    (assoc request :inertia {:component component
                             :props props})))

(defn wrap-inertia
  ([handler template asset-version]
   (wrap-inertia handler template asset-version {}))
  ([handler template asset-version share-props]
   (fn [request]
     (let [response (handler request)
           inertia-header (rr/get-header request "x-inertia")
           inertia-data (-> response
                            :inertia
                            (update :props merge share-props))
           url (:uri request)
           data-page (assoc inertia-data :url url :version asset-version)]
       (cond (= 302 (:status response)) response
             inertia-header {:status 200
                             :headers {"x-inertia" "true"
                                       "x-inertia-Version" asset-version}
                             :body data-page}
             :else (rr/response (template (json/write-str data-page))))))))
