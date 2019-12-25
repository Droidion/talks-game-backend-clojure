(ns talks-game-backend-clojure.graphql.auth
  (:require
    [com.walmartlabs.lacinia.resolve :as resolve]
    [talks-game-backend-clojure.crypto :as crypto]
    [talks-game-backend-clojure.user-profiles :as profiles]))

(defn auth-response-ok
  "Send ok response with generated session token"
  [{:keys [team-number team-type]}]
  (let [session {:token       (crypto/uuid-random-string)
                 :team_number team-number
                 :team_type   team-type
                 :controller  false}]
    (profiles/add-session-to-redis session)
    (resolve/resolve-as session)))

(defn auth-response-wrong-password
  "Send wrong password error with basic profile metadata"
  [{:keys [team-number team-type]}]
  (resolve/resolve-as {:token       nil
                       :team_number team-number
                       :team_type   team-type
                       :controller  nil}
                      {:message "Incorrect password"}))

(defn auth-response-not-found
  "Send profile (wrong login) error with empty profile metadata"
  []
  (resolve/resolve-as {:token       nil
                       :team_number nil
                       :team_type   nil
                       :controller  nil}
                      {:message "Profile not found"}))

(defn resolve-auth
  "Process auth Graphql request"
  [_ args _]
  (if-let [found-profiles
           (seq (profiles/find-profile-by-login
                  (:login args)
                  @profiles/profiles))]
    (if (crypto/argon2-verify
          (:password (first found-profiles))
          (:password args))
      (auth-response-ok (first found-profiles))
      (auth-response-wrong-password (first found-profiles)))
    (auth-response-not-found))
  )
