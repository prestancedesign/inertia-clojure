(ns reagent.inertia
  (:require ["@inertiajs/inertia-react" :refer [App InertiaLink]]
            [reagent.core :as r]
            [reagent.dom :as d]))

(def el (.getElementById js/document "app"))

(defn index [props]
  [:<>
   [:h1 (str "Hello " (:name props))]
   [:> InertiaLink {:href "/demo"} "Demo"]])

(defn demo [props]
  [:<>
   [:h1 (str "Hello " (:name props))]
   [:> InertiaLink {:href "/"} "Home"]])

(defn load-module
  "Helper to dynamic load component"
  [name]
  (let [fn-var ((ns-publics 'reagent.inertia) (symbol name))]
    fn-var))

(defn app []
  [:> App {:initial-page (.parse js/JSON (.. el -dataset -page))
           :resolve-component (fn [name] (r/reactify-component (load-module name)))}])

(defn mount-root []
  (d/render [app] el))

(defn init! []
  (mount-root))
