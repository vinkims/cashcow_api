-- Add transaction code column to transactions table
ALTER TABLE transactions ADD COLUMN transaction_code VARCHAR(50) NULL;

-- Add calving & observation dates to cow_services table
ALTER TABLE cow_services
ADD COLUMN calving_date DATE,
ADD COLUMN observation_date DATE,
ADD COLUMN bull_id INTEGER REFERENCES cows("id") ON DELETE SET NULL;

-- Seed cow_categories table
INSERT INTO cow_categories ("id", "name", "description")
VALUES (4, 'calf', 'Cow that has been born');

-- Create images table
CREATE TABLE IF NOT EXISTS cow_images (
    "id" SERIAL PRIMARY KEY,
    "img" BYTEA,
    "created_on" TIMESTAMPTZ DEFAULT CURRENT_TIMESTAMP
);

-- Add gender value to cow_profiles
ALTER TABLE cow_profiles 
ADD COLUMN gender VARCHAR(5) NULL,
ADD COLUMN cow_image_id INTEGER REFERENCES cow_images("id") ON DELETE SET NULL;
