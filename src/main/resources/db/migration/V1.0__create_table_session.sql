CREATE TABLE "poolmate"."session" (
  "id"          UUID    NOT NULL,
  "version"     BIGINT  NOT NULL DEFAULT 0,
  "deleted"     BOOLEAN NOT NULL DEFAULT FALSE,
  "user_id"     UUID    NOT NULL,
  "date"        DATE    NOT NULL,
  "pool_length" INT     NOT NULL DEFAULT 50,
  "calories"    INT     NOT NULL DEFAULT 0,
  "sets"        JSONB   NOT NULL DEFAULT '[]',
  "comments"    JSONB   NOT NULL DEFAULT '[]',

  PRIMARY KEY ("id")
);

CREATE INDEX ON "poolmate"."session" ("user_id");
