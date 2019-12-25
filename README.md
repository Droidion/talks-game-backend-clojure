# Talks Game: Clojure Backend

Experimental implementation of Talks Game. Backend part, using Clojure and GraphQL.

## Installation

Have JVM, Clojure (Leiningen) and Redis  installed. Tested with JVM 13.

Create `config/config.edn` file with env variable for setting Redis uri, e.g. for localhost
```
{:redis "redis://127.0.0.1:6379"}
```

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