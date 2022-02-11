(ns reagent.ssr
  (:require
   [reagent.dom.server :as d]
   [reagent.core :as r]
   [reagent.inertia :refer [pages layout]]
   ["@inertiajs/inertia-react" :refer [createInertiaApp]]
   [applied-science.js-interop :as j]
   ["@inertiajs/server" :default create-server]))

(defn main []
  (create-server
   (fn [page]
     (createInertiaApp
      (clj->js
       {:page page
        :render d/render-to-string
        :resolve (fn [name]
                   (let [^js comp (r/reactify-component (get pages name))]
                     (set! (.-layout comp) (fn [page] (r/as-element [layout page])))
                     comp))
        :setup (j/fn [^:js {:keys [App props]}]
                 (r/as-element [:f> App props]))})))))
