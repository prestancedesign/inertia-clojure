(ns reagent.inertia
  (:require ["@inertiajs/inertia-react" :refer [App InertiaLink]]
            [reagent.core :as r]
            [reagent.dom :as d]))

(def el (.getElementById js/document "app"))

(defn menu []
  [:<>
   [:p "Navigate pages without reloading: "]
   [:ul
    [:li [:> InertiaLink {:href "/"} "Home"]]
    [:li [:> InertiaLink {:href "/demo"} "Demo"]]]])

(defn index [{:keys [title]}]
  [:<>
   [menu]
   [:h1 title]])

(defn demo [{:keys [title date]}]
  (r/with-let [counter (r/atom 0)]
    [:<>
     [menu]
     [:h1 title]
     [:p "Local state Reagent Atom or React useState can be preserved when reloading page/component."]
     [:p "This is very useful for form submission to keep information filled in while handling errors."]
     [:p "Try the counter and click the refresh link. You can see the updated seconds however the counter value stay in place."]
     [:p (str "Counter: " @counter)]
     [:button {:on-click #(swap! counter inc)} "+"]
     [:p (str "Date: " date)]
     [:> InertiaLink {:href "/demo" :preserve-state true} "Refresh date"]]))

(defn app []
  [:> App {:initial-page (.parse js/JSON (.. el -dataset -page))
           :resolve-component (fn [name] (r/reactify-component (case name
                                                                 "index" index
                                                                 "demo" demo)))}])

(defn mount-root []
  (d/render [app] el))

(defn init! []
  (mount-root))
