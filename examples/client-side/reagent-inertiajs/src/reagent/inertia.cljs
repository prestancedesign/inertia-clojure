(ns reagent.inertia
  (:require ["@inertiajs/inertia-react" :refer [createInertiaApp InertiaLink]]
            ["@inertiajs/inertia" :refer [Inertia]]
            [reagent.core :as r]
            [reagent.dom :as d]
            [applied-science.js-interop :as j]))

(defn layout [& children]
  (into
   [:<>
    [:p "Navigate pages without reloading: "]
    [:ul
     [:li [:> InertiaLink {:href "/"} "Home"]]
     [:li [:> InertiaLink {:href "/demo"} "Demo"]]]]
   children))

(defn index [{:keys [title]}]
  [:h1 title])

(defn demo [{:keys [title date]}]
  (r/with-let [counter (r/atom 0)]
    [:<>
     [:h1 title]
     [:p "Local state Reagent Atom or React useState can be preserved when reloading page/component."]
     [:p "This is very useful for form submission to keep information filled in while handling errors."]
     [:p "Try the counter and click the refresh link. You can see the updated seconds however the counter value stay in place."]
     [:p (str "Counter: " @counter)]
     [:p (str "Date: " date)]
     [:button {:on-click #(swap! counter inc)} "+"]
     [:> InertiaLink {:href "/demo" :preserve-state true
                      :as "button"} "Refresh date"]]))

;; Add new page component to this list
(def pages {"index" index
            "demo" demo})

(defn init! []
  (createInertiaApp
   #js {:resolve (fn [name]
                   (let [^js comp (r/reactify-component (get pages name))]
                     (set! (.-layout comp) (fn [page] (r/as-element [layout page])))
                     comp))
        :setup (j/fn [^:js {:keys [el App props]}]
                 (d/render (r/as-element [:f> App props]) el))}))

(defn ^:dev/after-load reload []
  (.reload Inertia))
