# Basic Inertia example with Compojure and Hiccup

## Usage

### Build the front end

First build the Reagent app in the folder [examples/client-side/reagent-inertiajs](../../client-side/reagent-inertiajs):

    $ npx shadow-cljs release app

or for development with hot reloading and REPL:

    $ npx shadow-cljs watch app

This will create the file `/js/app.js` that will be included in the html page served by the backend.
This entry point is shared among several examples, so it live in the [examples/shared-resources](../../shared-resources).

### Run the back end

Then run the server:

    $ clj -M:run

Now acces the app at: http://localhost:3000/ and then experiment!
