# Inertia Clojure

Clojure Middleware adapter for [Inertia.js](https://inertiajs.com/).

The latest versions on Clojars

[![Clojars Project](https://clojars.org/com.github.prestancedesign/inertia-clojure/latest-version.svg)](https://clojars.org/com.github.prestancedesign/inertia-clojure)

## Introduction

Inertia is a new approach to building classic server-driven web apps. From their own web page:

> Inertia allows you to create fully client-side rendered, single-page apps, without much of the complexity that comes with modern SPAs. It does this by leveraging existing server-side frameworks.

Inertia requires an adapter for each backend framework.

For Clojure there is no de facto web framework, so I went with a [Ring](https://github.com/ring-clojure/ring) middleware who shape up the [`request`](https://github.com/ring-clojure/ring/wiki/Concepts#requests) and [`response`](https://github.com/ring-clojure/ring/wiki/Concepts#responses) to meet the Inertia.js [protocol](https://inertiajs.com/the-protocol).

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

Inertia-clojure contains a standard Ring middleware `wrap-inertia` that you can use with routing libraries like [Reitit](https://github.com/metosin/reitit), [Compojure](https://github.com/weavejester/compojure), etc.
The middleware must be the last item in your web middleware chain.

It expects 3 arguments:

* the app handler
* template: a function that recieves data-page arg - a correctly encoded string of the Inertia page object. The function should return an HTML string, so the template lib is up to you.
* version: your current asset version https://inertiajs.com/asset-versioning

Note: In your HTML template function make sure to always include the `data-page` string in the attribute of the HTML node you want to render your JavaScript app in.
For more information on how Inertia works read the protocol on the Inertia website https://inertiajs.com/the-protocol.

## Example

```clojure
(require '[inertia.middleware :as inertia])
;; ... Another required lib (compojure, httpkit, etc)

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
  (GET "/" [] (inertia/render "index" {:title "Hello World!"}))
  (GET "/demo" [] (inertia/render "demo" {:title "Clojure + Inertia"}))
  (route/resources "/"))

(def app (inertia/wrap-inertia routes template asset-version)) ; Wrap your handler with the Inertia middleware

(http/run-server app {:port 3000})
```

## More examples

### Server side

* [Reitit / Selmer](examples/server-side/reitit-selmer) : An example with Reitit routing and Selmer template lib
* [Compojure / Hiccup](examples/server-side/compojure-hiccup) : An example with Compojure routing and Hiccup for the template

### Client side

* [Reagent / Shadow-CLJS + Inertia.js](examples/client-side/reagent-inertiajs)

## Features

Features of the official server-side adapters are still in progress.

### Todo:

- [x] Shared data
- [x] Partial reloads
- [X] Assets versionning
- [ ] Lazy loaded props
- [ ] Root template data

## License

Copyright © 2021 Michaël Salihi / Prestance

Distributed under the Eclipse Public License version 1.0.
