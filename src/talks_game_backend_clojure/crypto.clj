(ns talks-game-backend-clojure.crypto
  "All about passwords and hashes"
  (:import (java.util UUID)
           (de.mkammerer.argon2 Argon2Factory)))

(def argon2 (Argon2Factory/create))

(defn uuid-random-string
  "Generate a random UUID string for using as a session token"
  []
  (-> (UUID/randomUUID)
      .toString))

;; Use this for generating new hashed passwords
(defn argon2-hash
  "Hash string with Argon2id"
  [plain-str]
  (.hash argon2 10 65536 1 plain-str))

(defn argon2-verify
  "Verify argon2-hash(plaintext_str) == hashed_str"
  [hashed-str plain-str]
  (.verify argon2 hashed-str plain-str))