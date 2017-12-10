INSERT INTO "poolmate"."user" ("first_name", "last_name", "email", "hashed_password")
VALUES
  ('Test', 'Test', 'test@test',
   (SELECT crypt('secret', gen_salt('bf'))));

INSERT INTO "poolmate"."session" ("user_id", "date")
VALUES
  ((SELECT "id"
    FROM "poolmate"."user"
    WHERE email = 'test@test'), '2016-05-01'),
  ((SELECT "id"
    FROM "poolmate"."user"
    WHERE email = 'test@test'), '2016-05-10'),
  ((SELECT "id"
    FROM "poolmate"."user"
    WHERE email = 'test@test'), '2016-05-12');

INSERT INTO "poolmate"."session_set" ("session_id", "number", "swimming_time", "rest_time", "laps", "average_strokes", "speed", "efficiency_index")
VALUES
  ((SELECT "id"
    FROM "poolmate"."session"
    WHERE "date" = '2016-05-01'), 0, 120e9, 60e9, 2, 25, 110, 40),
  ((SELECT "id"
    FROM "poolmate"."session"
    WHERE "date" = '2016-05-01'), 1, 240e9, 60e9, 4, 25, 105, 35),
  ((SELECT "id"
    FROM "poolmate"."session"
    WHERE "date" = '2016-05-10'), 0, 260e9, 60e9, 4, 25, 115, 45),
  ((SELECT "id"
    FROM "poolmate"."session"
    WHERE "date" = '2016-05-10'), 1, 480e9, 60e9, 6, 25, 95, 40),
  ((SELECT "id"
    FROM "poolmate"."session"
    WHERE "date" = '2016-05-12'), 0, 480e9, 60e9, 6, 27, 95, 40),
  ((SELECT "id"
    FROM "poolmate"."session"
    WHERE "date" = '2016-05-12'), 1, 260e9, 60e9, 4, 23, 85, 45),
  ((SELECT "id"
    FROM "poolmate"."session"
    WHERE "date" = '2016-05-12'), 2, 120e9, 60e9, 2, 29, 75, 50);
