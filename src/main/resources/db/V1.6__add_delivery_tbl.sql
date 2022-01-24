-- Create milk delivery table
CREATE TABLE IF NOT EXISTS milk_delivery (
    "id" SERIAL PRIMARY KEY,
    "created_on" TIMESTAMPTZ DEFAULT CURRENT_TIMESTAMP,
    "quantity" NUMERIC(11,4),
    "user_id" INTEGER REFERENCES users("id") ON DELETE CASCADE,
    "shop_id" INTEGER REFERENCES shops("id") ON DELETE SET NULL
);