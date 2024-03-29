# Change Log

## 0.2.5 - 2022-07-25
### Changed
- Switch to tools.build
- Upgrade to Clojure 1.11.1
- Upgrade to Ring 1.9.5
- Upgrade to Jsonista 0.3.6

## 0.2.4 - 2022-07-25
### Feature
- Keep http status code from response (fixes https://github.com/prestancedesign/inertia-clojure/issues/4)

## 0.2.3 - 2021-05-25
### Fixed
- Explicit text/html mime type for HTTP response

## 0.2.2 - 2021-05-25
### Changed
- Move Inertia shared props to ":inertia-share" key in Ring request map

## 0.2.1 - 2021-05-14
### Changed
- Append query-string to URL props to allowing browser History.pushState
- Multi-arity Inertia render function

## 0.2.0 - 2021-04-23
### Changed
- Replace JSON lib
- Renaming namespace

### Fixed
- Does not return body content for 409 HTTP status
- Condition to apply transformation only on collection type response

## 0.1.0 - 2021-04-20
- First release
