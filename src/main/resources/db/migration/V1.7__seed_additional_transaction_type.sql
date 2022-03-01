-- Update transactions tbl
UPDATE transaction_types
SET "name" = 'purchase_transport', "description" = 'Transport cost for products purchased'
WHERE id=9;

-- Add milk transport transaction type
INSERT INTO transaction_types ("id", "name", "description")
VALUES
    (10, 'milk_transport', 'Transport cost for milk delivery');