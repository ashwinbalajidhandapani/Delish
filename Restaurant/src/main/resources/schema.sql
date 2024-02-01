DROP TABLE IF EXISTS "restaurant";

CREATE TABLE "restaurant"(
                           "id" BIGINT NOT NULL,
                           "name" text NOT NULL UNIQUE,
                           "address" text NOT NULL,
                           "isopen" BOOLEAN NOT NULL,
                           "menuid" TEXT UNIQUE,
                           CONSTRAINT "restaurant_primary_key" PRIMARY KEY ("id")
);