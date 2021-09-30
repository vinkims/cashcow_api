-- cow_categories: holds data about various categories of cows
CREATE TABLE IF NOT EXISTS cow_categories (
    "id" INTEGER PRIMARY KEY,
    "name" VARCHAR(40),
    "description" VARCHAR(80)
);

-- create statuses table: hold info about all statuses
CREATE TABLE If NOT EXISTS statuses (
    "id" SERIAL PRIMARY KEY,
    "name" VARCHAR(20) NOT NULL UNIQUE,
    "description" VARCHAR(80) NOT NULL
);

-- Crate farms table: holds data about the farm
CREATE TABLE IF NOT EXISTS farms (
    "id" SERIAL PRIMARY KEY,
    "name" VARCHAR(20) NOT NULL,
    "created_on" TIMESTAMPTZ DEFAULT CURRENT_TIMESTAMP
);

-- Create cows table: holds primary information about cows
CREATE TABLE IF NOT EXISTS cows (
    "id" SERIAL PRIMARY KEY,
    "name" VARCHAR(40),
    "created_on" TIMESTAMPTZ DEFAULT CURRENT_TIMESTAMP,
    "parent_id" INTEGER REFERENCES cows("id") ON DELETE SET NULL,
    "farm_id" INTEGER REFERENCES farms("id"),
    "status_id" INTEGER REFERENCES statuses("id") ON DELETE SET NULL,
    "cow_category_id" INTEGER REFERENCES cow_categories("id") ON DELETE SET NULL
);

-- Create cow profiles table: Additional data about cows
CREATE TABLE IF NOT EXISTS cow_profiles (
    "cow_id" INTEGER PRIMARY KEY REFERENCES cows("id") ON DELETE CASCADE,
    "date_of_birth" DATE,
    "date_of_purchase" DATE,
    "date_of_death" DATE,
    "date_of_sale" DATE,
    "breed" VARCHAR(20),
    "color" VARCHAR(20),
    "purchase_amount" NUMERIC(11,4),
    "sale_amount" NUMERIC(11,4),
    "location_bought" VARCHAR(40)
);

-- Create shops table
CREATE TABLE IF NOT EXISTS shops (
    "id" SERIAL PRIMARY KEY,
    "name" VARCHAR(20) NULL,
    "location" VARCHAR(100),
    "created_on" TIMESTAMPTZ DEFAULT CURRENT_TIMESTAMP,
    "farm_id" INTEGER REFERENCES farms("id")
);

-- Create roles table
CREATE TABLE IF NOT EXISTS roles (
    "id" INTEGER PRIMARY KEY,
    "name" VARCHAR(20) NOT NULL,
    "description" VARCHAR(80) NOT NULL
);

-- Create users table
CREATE TABLE IF NOT EXISTS users (
    "id" SERIAL PRIMARY KEY,
    "first_name" VARCHAR(15) NOT NULL,
    "middle_name" VARCHAR(15),
    "last_name" VARCHAR(15),
    "created_on" TIMESTAMPTZ DEFAULT CURRENT_TIMESTAMP,
    "role_id" INTEGER REFERENCES roles("id") ON DELETE SET NULL,
    "shop_id" INTEGER REFERENCES shops("id") ON DELETE SET NULL,
    "farm_id" INTEGER REFERENCES farms("id") ON DELETE SET NULL,
    "status_id" INTEGER REFERENCES statuses("id") ON DELETE SET NULL
);

--Create user profiles table
CREATE TABLE IF NOT EXISTS user_profiles (
    "user_id" INTEGER PRIMARY KEY REFERENCES users("id") ON DELETE CASCADE,
    "passcode" VARCHAR(150) NULL
);

-- contact types: contains contact metadata
CREATE TABLE IF NOT EXISTS contact_types (
    "id" INTEGER PRIMARY KEY,
    "name" VARCHAR(20) NOT NULL,
    "description" VARCHAR(80)
);

--Create contacts table
CREATE TABLE IF NOT EXISTS contacts (
    "user_id" INTEGER NOT NULL REFERENCES users("id") ON DELETE CASCADE,
    "value" VARCHAR(30) PRIMARY KEY NOT NULL UNIQUE,
    "contact_type_id" INTEGER REFERENCES contact_types("id") ON DELETE SET NULL
);

-- Create sales table
CREATE TABLE IF NOT EXISTS milk_sales (
    "id" SERIAL PRIMARY KEY,
    "shop_id" INTEGER REFERENCES shops("id") ON DELETE SET NULL,
    "created_on" TIMESTAMPTZ DEFAULT CURRENT_TIMESTAMP,
    "quantity" NUMERIC(11,3),
    "customer_id" INTEGER REFERENCES users("id") ON DELETE SET NULL,
    "amount" NUMERIC(11,4),
    "status_id" INTEGER REFERENCES statuses("id") ON DELETE SET NULL
);

-- Create table transaction types: contains transactions metadata
CREATE TABLE IF NOT EXISTS transaction_types (
    "id" INTEGER PRIMARY KEY,
    "name" VARCHAR(20),
    "description" VARCHAR(80)
);

-- payment_channels: Defines payment channels (M-pesa or cash)
CREATE TABLE IF NOT EXISTS payment_channels (
    "id" INTEGER PRIMARY KEY,
    "name" VARCHAR(20),
    "description" VARCHAR(80)
);

-- transactions: holds transactions information
CREATE TABLE IF NOT EXISTS transactions (
    "id" SERIAL PRIMARY KEY,
    "created_on" TIMESTAMPTZ DEFAULT CURRENT_TIMESTAMP,
    "amount" NUMERIC(11,4),
    "transaction_type_id" INTEGER REFERENCES transaction_types("id") ON DELETE SET NULL,
    "payment_channel_id" INTEGER REFERENCES payment_channels("id") ON DELETE CASCADE,
    "shop_id" INTEGER REFERENCES shops("id") ON DELETE SET NULL,
    "attendant_id" INTEGER REFERENCES users("id") ON DELETE SET NULL,
    "customer_id" INTEGER REFERENCES users("id") ON DELETE SET NULL,
    "status_id" INTEGER REFERENCES statuses("id") ON DELETE SET NULL
);

-- cow_services: Services offered to the cows
CREATE TABLE IF NOT EXISTS cow_services (
    "id" SERIAL PRIMARY KEY,
    "cow_id" INTEGER REFERENCES cows("id"),
    "amount" NUMERIC(11,4),
    "name" VARCHAR(20),
    "results" VARCHAR(80),
    "created_on" TIMESTAMPTZ DEFAULT CURRENT_TIMESTAMP,
    "user_id" INTEGER REFERENCES users("id")
);

-- purchases: Holds information about products purchased e.g feeds
CREATE TABLE IF NOT EXISTS purchases (
    "id" SERIAL PRIMARY KEY,
    "name" VARCHAR(20),
    "supplier_id" INTEGER REFERENCES users("id"),
    "created_on" TIMESTAMPTZ DEFAULT CURRENT_TIMESTAMP,
    "unit_price" NUMERIC(11,4),
    "quantity" NUMERIC(11,4),
    "transport_cost" NUMERIC(11,4)
);

-- milking sessions: Hold milking session metadata
CREATE TABLE IF NOT EXISTS milking_sessions (
    "id" SERIAL PRIMARY KEY,
    "name" VARCHAR(40) NOT NULL,
    "description" VARCHAR(80)
);

-- milk production: Hold milk production information
CREATE TABLE IF NOT EXISTS milk_productions (
    "id" SERIAL PRIMARY KEY,
    "created_on" TIMESTAMPTZ DEFAULT CURRENT_TIMESTAMP,
    "cow_id" INTEGER REFERENCES cows("id"),
    "session_id" INTEGER REFERENCES milking_sessions("id"),
    "quantity" NUMERIC(11,4),
    "user_id" INTEGER REFERENCES users("id")
);

-- create blacklist tokens: contains deactivated tokens
CREATE TABLE blacklist_tokens (
    "id" SERIAL PRIMARY KEY,
    "user_id" INTEGER REFERENCES users("id") ON DELETE SET NULL,
    "token_hash" BIGINT NOT NULL UNIQUE,
    "created_on" TIMESTAMPTZ DEFAULT CURRENT_TIMESTAMP
);
