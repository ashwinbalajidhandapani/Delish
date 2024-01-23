DROP TABLE IF EXISTS "restaurant";
DROP SEQUENCE IF EXISTS restaurant_id_sequence CASCADE;
CREATE SEQUENCE restaurant_id_sequence INCREMENT BY 1 MINVALUE 1 MAXVALUE 9223372036854775807 CACHE 1;

CREATE TABLE "restaurant"(
                           "id" BIGINT DEFAULT nextval('restaurant_id_sequence') NOT NULL,
                           "name" text NOT NULL UNIQUE,
                           "address" text NOT NULL,
                           "isopen" BOOLEAN NOT NULL UNIQUE,
                           "menuid" TEXT UNIQUE,
                           CONSTRAINT "restaurant_primary_key" PRIMARY KEY ("id")
);