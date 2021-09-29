-- create farms table: contains info about the farm
CREATE TABLE IF NOT EXISTS farms (
    "id" SERIAL PRIMARY KEY,
    "name" VARCHAR(20) NOT NULL,
    "created_on" TIMESTAMPTZ DEFAULT CURRENT_TIMESTAMP
);

-- add farm_id column
ALTER TABLE users ADD COLUMN "farm_id" INTEGER REFERENCES farms("id");

ALTER TABLE cows ADD COLUMN "farm_id" INTEGER REFERENCES farms("id");

ALTER TABLE shops ADD COLUMN "farm_id" INTEGER REFERENCES farms("id");