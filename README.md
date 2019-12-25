# Talks Game: Clojure Backend

Experimental implementation of Talks Game. Backend part, using Clojure and GraphQL.

## Installation

Have JVM and Clojure (Leiningen) installed. Tested with JVM 13.

## Usage

Run web server

```
$ lein run
```

Or make and run distributable JAR file

```
$ lein uberjar
$ java -jar target/uberjar/talks-game-backend-clojure-0.1.0-SNAPSHOT-standalone.jar
```

Run GraphQL queries using POST requests to `http://localhost:8888/graphql`.

Use the following GraphQL schema

```
query($login: String, $password: String) {
  auth(login: $login, password: $password) {
    token, team_number, team_type, controller
  }
}
```