# Simple Inertia example with Reitit and Selmer

## Usage

### Build the front end

Build the Reagent app in the folder examples/client-side/reagent-inertiajs:

```clojure
$ npx shadow-cljs release app
```

or for hot reloading and the REPL

```clojure
$ npx shadow-cljs watch app
```

This will create the file `/js/app.js` that will be included in the html page served by the backend.
This entry point is shared among several examples, so it live in the [examples/shared-resources](examples/shared-resources).

### Run the back end

Then run the server:

```clojure
$ clj -M:run
```

Now acces the app at: http://localhost:3000/ and then experiment!
