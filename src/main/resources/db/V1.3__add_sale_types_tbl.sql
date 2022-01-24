-- Create sale types table
CREATE TABLE IF NOT EXISTS sale_types (
    "id" INTEGER PRIMARY KEY,
    "name" VARCHAR(20) NOT NULL,
    "description" VARCHAR(80)
);

-- Seed sale_types table
INSERT INTO sale_types ("id", "name", "description")
VALUES
    (1, 'cash', 'milk cash sale'),
    (2, 'mpesa', 'mpesa milk sale'),
    (3, 'credit', 'credit milk sale');

-- Add sale_type_id column to sales table
ALTER TABLE milk_sales ADD COLUMN sale_type_id INTEGER REFERENCES sale_types("id") ON DELETE SET NULL;

-- Seed additional transaction types
INSERT INTO transaction_types("id", "name", "description")
VALUES 
    (7, 'staff_salary', 'Staff salary payment'),
    (8, 'staff_advance', 'Staff advance on salary'),
    (9, 'transport', 'Transport expense');

-- Create expense types table
CREATE TABLE IF NOT EXISTS expense_types (
    "id" SERIAL PRIMARY KEY,
    "name" VARCHAR(20) NOT NULL,
    "description" VARCHAR(80)
);

-- Create expenses table
CREATE TABLE IF NOT EXISTS expenses (
    "id" SERIAL PRIMARY KEY,
    "created_on" TIMESTAMPTZ DEFAULT CURRENT_TIMESTAMP,
    "amount" NUMERIC(11,4),
    "description" VARCHAR(50),
    "expense_type_id" INTEGER REFERENCES expense_types("id") ON DELETE SET NULL,
    "cow_id" INTEGER REFERENCES cows("id") ON DELETE SET NULL,
    "user_id" INTEGER REFERENCES users("id") ON DELETE SET NULL,
    "status_id" INTEGER REFERENCES statuses("id") ON DELETE SET NULL
);

-- Create weight progression table
CREATE TABLE IF NOT EXISTS weight_progression (
    "id" SERIAL PRIMARY KEY,
    "created_on" TIMESTAMPTZ DEFAULT CURRENT_TIMESTAMP,
    "weight" NUMERIC(6,4),
    "cow_id" INTEGER REFERENCES cows("id") ON DELETE SET NULL
);