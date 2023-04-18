-- update farms 
ALTER TABLE farms ADD COLUMN updated_on TIMESTAMPTZ;
ALTER TABLE farms ADD COLUMN status_id SMALLINT REFERENCES statuses("id") ON DELETE SET NULL;

-- cow_breeds
CREATE TABLE IF NOT EXISTS cow_breeds (
    "id" SMALLSERIAL PRIMARY KEY,
    "name" VARCHAR(40),
    "description" VARCHAR(100)
);

-- cows
ALTER TABLE cows ADD COLUMN updated_on TIMESTAMPTZ;
ALTER TABLE cows ADD COLUMN cow_breed_id SMALLINT REFENCES cow_breeds("id") ON DELETE SET NULL;
ALTER TABLE cows ADD COLUMN gender VARCHAR(10) NOT NULL;
ALTER TABLE cows ADD COLUMN date_of_birth DATE;
ALTER TABLE cows ADD COLUMN color VARCHAR(40);
ALTER TABLE cows ADD COLUMN other_details VARCHAR;

-- cow_purchases
CREATE TABLE IF NOT EXISTS cow_purchases (
    "id" SERIAL PRIMARY KEY,
    "created_on" TIMESTAMPTZ DEFAULT CURRENT_TIMESTAMP,
    "updated_on" TIMESTAMPTZ,
    "cow_id" INTEGER REFENCES cows("id") ON DELETE SET NULL,
    "purchase_amount" NUMERIC(11,4),
    "purchase_location" VARCHAR(100),
    "status_id" SMALLINT REFENCES statuses("id") ON DELETE SET NULL
);

-- alter cow_service_types
ALTER TABLE cow_service_types ADD COLUMN farm_id SMALLINT REFERENCES farms("id") ON DELETE CASCADE;
ALTER TABLE cow_service_types ADD COLUMN status_id SMALLINT REFERENCES statuses("id") ON DELETE SET NULL;

-- alter cow_services
ALTER TABLE cow_services ADD COLUMN updated_on TIMESTAMPTZ;
ALTER TABLE cow_services RENAME COLUMN amount TO cost;
ALTER TABLE cow_services RENAME COLUMN results TO remarks;
ALTER TABLE cow_services ADD COLUMN status_id SMALLINT REFENCES statuses("id") ON DELETE SET NULL;

-- alter weight_progressions table
ALTER TABLE weight_progressions ADD COLUMN updated_on TIMESTAMPTZ;
ALTER TABLE weight_progressions ADD COLUMN status_id SMALLINT REFERENCES statuses("id") ON DELETE SET NULL;
ALTER TABLE weight_progressions RENAME TO cow_weights;

-- feed_items
CREATE TABLE IF NOT EXISTS feed_items (
    "id" SERIAL PRIMARY KEY,
    "created_on" TIMESTAMPTZ DEFAULT CURRENT_TIMESTAMP,
    "updated_on" TIMESTAMPTZ,
    "name" VARCHAR(50),
    "farm_id" SMALLINT REFENCES farms("id") ON DELETE CASCADE,
    "stock" NUMERIC(11,4),
    "measurement_unit" VARCHAR(50),
    "status_id" SMALLINT REFERENCES statuses("id") ON DELETE SET NULL
);

-- cow_feedings
CREATE TABLE IF NOT EXISTS cow_feedings (
    "id" SERIAL PRIMARY KEY,
    "created_on" TIMESTAMPTZ DEFAULT CURRENT_TIMESTAMP,
    "updated_on" TIMESTAMPTZ,
    "farm_id" SMALLINT REFERENCES farms("id") ON DELETE CASCADE,
    "user_id" INTEGER REFERENCES users("id") ON DELETE SET NULL,
    "status_id" SMALLINT REFERENCES statuses("id") ON DELETE SET NULL
);

-- cow_feeding_feed_items
CREATE TABLE IF NOT EXISTS cow_feeding_feed_items (
    "cow_feeding_id" INTEGER REFERENCES cow_feedings("id") ON DELETE SET NULL,
    "feed_item_id" INTEGER REFERENCES feed_items("id") ON DELETE SET NULL,
    "quantity" NUMERIC(11,4),
    PRIMARY KEY("cow_feeding_id", "feed_item_id")
);

-- cow_deaths
CREATE TABLE IF NOT EXISTS cow_deaths (
    "id" SERIAL PRIMARY KEY,
    "created_on" TIMESTAMPTZ DEFAULT CURRENT_TIMESTAMP,
    "updated_on" TIMESTAMPTZ,
    "cow_id" INTEGER REFERENCES cows("id") ON DELETE SET NULL,
    "description" VARCHAR(200),
    "status_id" SMALLINT REFERENCES statuses("id") ON DELETE SET NULL
);

-- cow_sales
CREATE TABLE IF NOT EXISTS cow_sales (
    "id" SERIAL PRIMARY KEY,
    "created_on" TIMESTAMPTZ DEFAULT CURRENT_TIMESTAMP,
    "updated_on" TIMESTAMPTZ,
    "cow_id" INTEGER REFERENCES cows("id") ON DELETE SET NULL,
    "sale_amount" NUMERIC(11,4),
    "buyer_id" INTEGER REFERENCES users("id") ON DELETE SET NULL,
    "status_id" SMALLINT REFERENCES statuses("id") ON DELETE SET NULL
);

-- breeding_types
CREATE TABLE IF NOT EXISTS breeding_types (
    "id" SMALLSERIAL PRIMARY KEY,
    "name" VARCHAR(40),
    "description" VARCHAR(100)
);

-- cow_breedings
CREATE TABLE IF NO EXISTS cow_breedings (
    "id" SERIAL PRIMARY KEY,
    "created_on" TIMESTAMPTZ DEFAULT CURRENT_TIMESTAMP,
    "updated_on" TIMESTAMPTZ,
    "breeding_type_id" SMALLINT REFERENCES breeding_types("id") ON DELETE SET NULL,
    "cow_id" INTEGER REFERENCES cows("id") ON DELETE SET NULL,
    "bull_id" INTEGER REFERENCES cows("id") ON DELETE SET NULL,
    "description" VARCHAR(200),
    "status_id" SMALLINT REFERENCES statuses("id") ON DELETE SET NULL
);