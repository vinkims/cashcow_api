-- update farms 
ALTER TABLE farms 
ADD COLUMN updated_on TIMESTAMPTZ,
ADD COLUMN status_id SMALLINT REFERENCES statuses("id") ON DELETE SET NULL;

-- add cow_breeds
CREATE TABLE IF NOT EXISTS cow_breeds (
    "id" SMALLSERIAL PRIMARY KEY,
    "created_on" TIMESTAMPTZ DEFAULT CURRENT_TIMESTAMP,
    "name" VARCHAR(40),
    "description" VARCHAR(100),
    "status_id" SMALLINT REFERENCES statuses("id") ON DELETE SET NULL
);

-- alter cows
ALTER TABLE cows 
ADD COLUMN updated_on TIMESTAMPTZ,
ADD COLUMN cow_breed_id SMALLINT REFERENCES cow_breeds("id") ON DELETE SET NULL,
ADD COLUMN gender VARCHAR(10) NOT NULL,
ADD COLUMN date_of_birth DATE,
ADD COLUMN color VARCHAR(40),
ADD COLUMN other_details VARCHAR,
ADD COLUMN cow_image_id INTEGER REFERENCES cow_images("id") ON DELETE SET NULL;

-- add cow_purchases
CREATE TABLE IF NOT EXISTS cow_purchases (
    "id" SERIAL PRIMARY KEY,
    "created_on" TIMESTAMPTZ DEFAULT CURRENT_TIMESTAMP,
    "updated_on" TIMESTAMPTZ,
    "cow_id" INTEGER REFERENCES cows("id") ON DELETE SET NULL,
    "purchase_amount" NUMERIC(11,4),
    "purchase_location" VARCHAR(100),
    "status_id" SMALLINT REFERENCES statuses("id") ON DELETE SET NULL
);

-- alter cow_service_types
ALTER TABLE cow_service_types 
ADD COLUMN farm_id SMALLINT REFERENCES farms("id") ON DELETE CASCADE,
ADD COLUMN status_id SMALLINT REFERENCES statuses("id") ON DELETE SET NULL;

-- alter cow_services
ALTER TABLE cow_services 
ADD COLUMN updated_on TIMESTAMPTZ,
RENAME COLUMN amount TO cost,
RENAME COLUMN results TO remarks,
ADD COLUMN status_id SMALLINT REFERENCES statuses("id") ON DELETE SET NULL;

-- alter weight_progressions table
ALTER TABLE weight_progressions 
ADD COLUMN updated_on TIMESTAMPTZ,
ADD COLUMN status_id SMALLINT REFERENCES statuses("id") ON DELETE SET NULL,
ALTER TABLE weight_progressions RENAME TO cow_weights;

-- add feed_items
CREATE TABLE IF NOT EXISTS feed_items (
    "id" SERIAL PRIMARY KEY,
    "created_on" TIMESTAMPTZ DEFAULT CURRENT_TIMESTAMP,
    "updated_on" TIMESTAMPTZ,
    "name" VARCHAR(50),
    "farm_id" SMALLINT REFERENCES farms("id") ON DELETE CASCADE,
    "stock" NUMERIC(11,4),
    "measurement_unit" VARCHAR(50),
    "status_id" SMALLINT REFERENCES statuses("id") ON DELETE SET NULL
);

-- add cow_feedings
CREATE TABLE IF NOT EXISTS cow_feedings (
    "id" SERIAL PRIMARY KEY,
    "created_on" TIMESTAMPTZ DEFAULT CURRENT_TIMESTAMP,
    "updated_on" TIMESTAMPTZ,
    "farm_id" SMALLINT REFERENCES farms("id") ON DELETE CASCADE,
    "user_id" INTEGER REFERENCES users("id") ON DELETE SET NULL,
    "status_id" SMALLINT REFERENCES statuses("id") ON DELETE SET NULL
);

-- add cow_feeding_feed_items
CREATE TABLE IF NOT EXISTS cow_feeding_feed_items (
    "cow_feeding_id" INTEGER REFERENCES cow_feedings("id") ON DELETE SET NULL,
    "feed_item_id" INTEGER REFERENCES feed_items("id") ON DELETE SET NULL,
    "quantity" NUMERIC(11,4),
    PRIMARY KEY("cow_feeding_id", "feed_item_id")
);

-- add cow_deaths
CREATE TABLE IF NOT EXISTS cow_deaths (
    "id" SERIAL PRIMARY KEY,
    "created_on" TIMESTAMPTZ DEFAULT CURRENT_TIMESTAMP,
    "updated_on" TIMESTAMPTZ,
    "cow_id" INTEGER REFERENCES cows("id") ON DELETE SET NULL,
    "description" VARCHAR(200),
    "status_id" SMALLINT REFERENCES statuses("id") ON DELETE SET NULL
);

-- add cow_sales
CREATE TABLE IF NOT EXISTS cow_sales (
    "id" SERIAL PRIMARY KEY,
    "created_on" TIMESTAMPTZ DEFAULT CURRENT_TIMESTAMP,
    "updated_on" TIMESTAMPTZ,
    "cow_id" INTEGER REFERENCES cows("id") ON DELETE SET NULL,
    "sale_amount" NUMERIC(11,4),
    "buyer_id" INTEGER REFERENCES users("id") ON DELETE SET NULL,
    "status_id" SMALLINT REFERENCES statuses("id") ON DELETE SET NULL
);

-- add breeding_types
CREATE TABLE IF NOT EXISTS breeding_types (
    "id" SMALLSERIAL PRIMARY KEY,
    "name" VARCHAR(40),
    "description" VARCHAR(100)
);

-- add cow_breedings
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

-- alter milking_sessions
ALTER TABLE milking_sessions 
ADD COLUMN created_on TIMESTAMPTZ DEFAULT CURRENT_TIMESTAMP,
ADD COLUMN updated_on TIMESTAMPTZ,
ADD COLUMN farm_id SMALLINT REFERENCES farms("id") ON DELETE CASCADE,
ADD COLUMN status_id SMALLINT REFERENCES statuses("id") ON DELETE SET NULL;

-- alter milk_productions
ALTER TABLE milk_productions 
ADD COLUMN updated_on TIMESTAMPTZ,
ADD COLUMN status_id SMALLINT REFERENCES statuses("id") ON DELETE SET NULL;

-- alter consumption_categories
ALTER TABLE consumption_categories 
ADD COLUMN farm_id SMALLINT REFERENCES farms("id") ON DELETE CASCADE,
ADD COLUMN created_on TIMESTAMPTZ DEFAULT CURRENT_TIMESTAMP,
ADD COLUMN updated_on TIMESTAMPTZ,
ADD COLUMN status_id SMALLINT REFERENCES statuses("id") ON DELETE SET NULL,
RENAME TO milk_consumption_categories;

-- alter milk_consumptions
ALTER TABLE milk_consumptions 
ADD COLUMN updated_on TIMESTAMPTZ,
ADD COLUMN farm_id SMALLINT REFERENCES farms("id") ON DELETE CASCADE,
RENAME COLUMN price_per_litre TO unit_cost,
ADD COLUMN status_id SMALLINT REFERENCES statuses("id") ON DELETE SET NULL;

-- alter milk_delivery 
ALTER TABLE milk_delivery
ADD COLUMN updated_on TIMESTAMPTZ,
ADD COLUMN status_id SMALLINT REFERENCES statuses("id") ON DELETE SET NULL,
RENAME TO milk_shop_deliveries;

-- alter milk_sales
ALTER TABLE milk_sales ADD COLUMN updated_on TIMESTAMPTZ;

-- alter shops
ALTER TABLE shops
ADD COLUMN updated_on TIMESTAMPTZ,
ADD COLUMN status_id SMALLINT REFERENCES statuses("id") ON DELETE SET NULL;

-- add shop_users 
CREATE TABLE IF NOT EXISTS shop_users (
    "shop_id" SMALLINT REFERENCES shops("id") ON DELETE CASCADE,
    "user_id" INTEGER REFERENCES users("id") ON DELETE CASCADE,
    PRIMARY KEY("shop_id", "user_id")
);

-- alter users
ALTER TABLE users
ADD COLUMN updated_on TIMESTAMPTZ,
ADD COLUMN passcode VARCHAR;

-- alter contact_types
ALTER TABLE contact_types ADD COLUMN regex_value VARCHAR(100);

-- alter contacts
ALTER TABLE contacts
ADD COLUMN created_on TIMESTAMPTZ DEFAULT CURRENT_TIMESTAMP;

-- add user_profiles
ALTER TABLE user_profiles
DROP COLUMN passcode,
ADD COLUMN status_id SMALLINT REFERENCES statuses("id") ON DELETE SET NULL,
RENAME TO user_milk_prices;

-- alter expense_types
ALTER TABLE expense_types
ADD COLUMN created_on TIMESTAMPTZ DEFAULT CURRENT_TIMESTAMP,
ADD COLUMN updated_on TIMESTAMPTZ,
ADD COLUMN farm_id SMALLINT REFERENCES farms("id") ON DELETE SET NULL,
ADD COLUMN status_id SMALLINT REFERENCES statuses("id") ON DELETE SET NULL;

-- alter expenses
ALTER TABLE expenses
ADD COLUMN updated_on TIMESTAMPTZ,
ADD COLUMN farm_id SMALLINT REFERENCES farms("id") ON DELETE SET NULL,
DROP COLUMN cow_id,
DROP COLUMN user_id;

-- add cow_expenses
CREATE TABLE IF NOT EXISTS cow_expenses (
    "cow_id" INTEGER REFERENCES cows("id") ON DELETE SET NULL,
    "expense_id" INTEGER REFERENCES expenses("id") ON DELETE SET NULL,
    PRIMARY KEY("cow_id", "expense_id")
);

-- add user_expenses
CREATE TABLE IF NOT EXISTS user_expenses (
    "user_id" INTEGER REFERENCES users("id") ON DELETE SET NULL,
    "expense_id" INTEGER REFERENCES expenses("id") ON DELETE SET NULL,
    PRIMARY KEY("user_id", "expense_id")
);

-- alter purchases
ALTER TABLE purchases
ADD COLUMN updated_on TIMESTAMPTZ,
RENAME COLUMN unit_price TO unit_cost,
DROP COLUMN transport_cost,
ADD COLUMN farm_id SMALLINT REFERENCES farms("id") ON DELETE SET NULL,
ADD COLUMN status_id SMALLINT REFERENCES statuses("id") ON DELETE SET NULL;

-- add income_types
CREATE TABLE IF NOT EXISTS income_types (
    "id" SMALLSERIAL PRIMARY KEY,
    "created_on" TIMESTAMPTZ DEFAULT CURRENT_TIMESTAMP,
    "updated_on" TIMESTAMPTZ,
    "farm_id" SMALLINT REFERENCES farms("id") ON DELETE CASCADE,
    "name" VARCHAR(40),
    "description" VARCHAR(200),
    "status_id" SMALLINT REFERENCES statuses("id") ON DELETE SET NULL
);

-- add incomes 
CREATE TABLE IF NOT EXISTS incomes (
    "id" SERIAL PRIMARY KEY,
    "created_on" TIMESTAMPTZ DEFAULT CURRENT_TIMESTAMP,
    "updated_on" TIMESTAMPTZ,
    "income_type_id" SMALLINT REFERENCES income_types("id") ON DELETE SET NULL,
    "farm_id" SMALLINT REFERENCES farms("id") ON DELETE SET NULL,
    "amount" NUMERIC(11,4),
    "reference" VARCHAR(200),
    "status_id" SMALLINT REFERENCES statuses("id") ON DELETE SET NULL
);

-- add audit_event_types
CREATE TABLE IF NOT EXISTS audit_event_types (
    "id" SMALLSERIAL PRIMARY KEY,
    "name" VARCHAR(40)
);

-- add audit_events
CREATE TABLE IF NOT EXISTS audit_events (
    "id" SERIAL PRIMARY KEY,
    "created_on" TIMESTAMPTZ DEFAULT CURRENT_TIMESTAMP,
    "farm_id" SMALLINT REFERENCES farms("id") ON DELETE SET NULL,
    "event_type_id" SMALLINT REFERENCES audit_event_types("id") ON DELETE SET NULL,
    "principal" VARCHAR(100),
    "status_id" SMALLINT REFERENCES statuses("id") ON DELETE SET NULL
);

-- add audit_event_data
CREATE TABLE IF NOT EXISTS audit_event_data (
    "audit_event_id" INTEGER PRIMARY KEY REFERENCES audit_events("id") ON DELETE CASCADE,
    "remote_address" VARCHAR(40),
    "session_id" VARCHAR(40),
    "data_type" VARCHAR(200),
    "event_message" VARCHAR
);

-- alter transactions
ALTER TABLE transactions 
ADD COLUMN updated_on TIMESTAMPTZ,
DROP COLUMN shop_id,
DROP COLUMN customer_id,
RENAME COLUMN attendant_id TO user_id,
ADD COLUMN farm_id SMALLINT REFERENCES farms("id") ON DELETE SET NULL;

-- drop cow_profiles table
DROP TABLE IF EXISTS cow_profiles;