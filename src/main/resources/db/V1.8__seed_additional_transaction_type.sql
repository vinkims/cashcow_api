-- Add utility payment transaction type
INSERT INTO transaction_types ("id", "name", "description")
VALUES
    (11, 'utility_payment', 'Payment for utilities e.g water, electricity');