# Inertia Clojure

Clojure Middleware adapter for Inertia.js https://inertiajs.com/

The latest versions on Clojar

[![Clojars Project](https://clojars.org/com.github.prestancedesign/inertia-clojure/latest-version.svg)](https://clojars.org/com.github.prestancedesign/inertia-clojure)

## Introduction

Inertia is a new approach to building classic server-driven web apps. From their own web page:

> Inertia allows you to create fully client-side rendered, single-page apps, without much of the complexity that comes with modern SPAs. It does this by leveraging existing server-side frameworks.

Inertia requires an adapter for each backend framework.

For Clojure there is no de facto web framework, so I went with a middleware who shape up the Ring request/response to meet the Inertia.js [protocol](https://inertiajs.com/the-protocol)

## Example

```clojure
(require '[inertia.middleware :as inertia])

(def asset-version "1")

;; Create html template with library of your choice, here Hiccup
(defn template [data-page]
  (page/html5
   [:head
    [:meta {:charset "utf-8"}]
    [:title "Inertia + Clojure example"]]
   [:body
    [:div {:id "app"
           :data-page data-page}] ; The Inertia JSON page object
    (page/include-js "/js/app.js")])) ; Include your Reagent, React, Vue or Svelte SPA

(defroutes routes
  (GET "/" [] (inertia/render "index" {:title "World!"}))
  (GET "/demo" [] (inertia/render "demo" {:title "Clojure + Inertia"}))
  (route/resources "/"))

(def app
  (-> routes
      (inertia/wrap-inertia template asset-version)
      wrap-json-response))

(http/run-server #'app {:port 3000})
```

## More examples

### Server side

* [Reitit / Selmer](examples/server-side/reitit-selmer) : An example with Reitit routing and Selmer template lib
* [Compojure / Hiccup](examples/server-side/compojure-hiccup) : An example with Compojure routing and Hiccup for the template

### Client side

* [Reagent / Shadow-CLJS + Inertia.js](examples/client-side/reagent-inertiajs)

## Install

For `deps.edn`:

```clojure
com.github.prestancedesign/inertia-clojure {:mvn/version "0.1.0"}
```

For `project.clj`:

```clojure
[com.github.prestancedesign/inertia-clojure "0.1.0"]
```

## Usage


## Features

Almost all features of the official server-side adapters are present. The rest is coming:

- [x] Shared data
- [x] Partial reloads
- [X] Assets versionning
- [ ] Lazy loaded props
- [ ] Root template data

## License

Copyright © 2021 Michaël Salihi / Prestance

Distributed under the Eclipse Public License version 1.0.
