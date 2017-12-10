CREATE EXTENSION pgcrypto;

CREATE TABLE "poolmate"."user" (
  "id"              BIGSERIAL    NOT NULL,
  "version"         BIGINT       NOT NULL DEFAULT 0,
  "deleted"         BOOLEAN      NOT NULL DEFAULT FALSE,
  "first_name"      VARCHAR(255) NOT NULL,
  "last_name"       VARCHAR(255) NOT NULL,
  "email"           VARCHAR(255) NOT NULL UNIQUE,
  "hashed_password" TEXT         NOT NULL,
  "administrator"   BOOLEAN      NOT NULL DEFAULT FALSE,

  PRIMARY KEY ("id")
);

CREATE TABLE "poolmate"."session" (
  "id"          BIGSERIAL NOT NULL,
  "version"     BIGINT    NOT NULL DEFAULT 0,
  "deleted"     BOOLEAN   NOT NULL DEFAULT FALSE,
  "user_id"     BIGINT    NOT NULL,
  "date"        DATE      NOT NULL,
  "pool_length" INT       NOT NULL DEFAULT 50,
  "calories"    INT       NOT NULL DEFAULT 0,

  PRIMARY KEY ("id"),
  FOREIGN KEY ("user_id") REFERENCES "poolmate"."user" ("id")
);

CREATE TABLE "poolmate"."session_set" (
  "id"               BIGSERIAL NOT NULL,
  "version"          BIGINT    NOT NULL DEFAULT 0,
  "deleted"          BOOLEAN   NOT NULL DEFAULT FALSE,
  "session_id"       BIGINT    NOT NULL,
  "number"           INT       NOT NULL,
  "swimming_time"    BIGINT    NOT NULL DEFAULT 0,
  "rest_time"        BIGINT    NOT NULL DEFAULT 0,
  "laps"             INT       NOT NULL DEFAULT 0,
  "average_strokes"  INT       NOT NULL DEFAULT 0,
  "speed"            INT       NOT NULL DEFAULT 0,
  "efficiency_index" INT       NOT NULL DEFAULT 0,

  PRIMARY KEY ("id"),
  FOREIGN KEY ("session_id") REFERENCES "poolmate"."session" ("id")
);
