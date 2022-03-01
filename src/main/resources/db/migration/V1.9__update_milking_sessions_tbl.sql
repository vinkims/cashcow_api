-- Update milking_sessions table
UPDATE milking_sessions SET "name" = 'early_morning' WHERE id = 1;
UPDATE milking_sessions SET "name" = 'midday' WHERE id = 2;
UPDATE milking_sessions SET "name" = 'afternoon' WHERE id = 3;

-- create consumption_categories table
CREATE TABLE IF NOT EXISTS consumption_categories(
    "id" SERIAL PRIMARY KEY,
    "name" VARCHAR(30) NOT NULL UNIQUE,
    "description" VARCHAR(80)
);

-- create milk_consumption table
CREATE TABLE IF NOT EXISTS milk_consumptions (
    "id" SERIAL PRIMARY KEY,
    "created_on" TIMESTAMPTZ DEFAULT CURRENT_TIMESTAMP,
    "category_id" INTEGER REFERENCES consumption_categories("id") ON DELETE SET NULL,
    "user_id" INTEGER REFERENCES users("id") ON DELETE SET NULL,
    "quantity" NUMERIC(11,4),
    "price_per_litre" NUMERIC(11,4),
    "session_id" INTEGER REFERENCES milking_sessions("id")
);