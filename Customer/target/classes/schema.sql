DROP TABLE IF EXISTS "customer";
DROP SEQUENCE IF EXISTS customer_id_sequence CASCADE;
CREATE SEQUENCE customer_id_sequence INCREMENT BY 1 MINVALUE 1 MAXVALUE 9223372036854775807 CACHE 1;

CREATE TABLE "customer"(
    "id" BIGINT DEFAULT nextval('customer_id_sequence') NOT NULL,
    "emailid" text NOT NULL UNIQUE,
    "username" text NOT NULL UNIQUE,
    "phone" text NOT NULL UNIQUE,
    "password" VARCHAR(25) NOT NULL,
    CONSTRAINT "customer_primary_key" PRIMARY KEY ("id"),
    CONSTRAINT password_length_check CHECK (char_length(password) >= 8 AND char_length(password) <= 25)
);