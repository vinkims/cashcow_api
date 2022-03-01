INSERT INTO roles ("id", "name", "description")
VALUES 
    (1, 'system-admin', 'Cash cow system administrator'),
    (2, 'admin', 'Cash cow administrator'),
    (3, 'shop-attendant', 'Milk shop attendant'),
    (4, 'customer', 'Milk customer'),
    (5, 'supplier', 'Products e.g feeds supplier'),
    (6, 'vet', 'Veterinary doctor/person offering services'),
    (7, 'farm-attendant', 'Farm and cows attendant'),
    (8, 'credit-customer', 'Credit milk customer; Pays after a given period');

INSERT INTO cow_categories ("id", "name", "description")
VALUES 
    (1, 'heifer', 'Cow that is yet to give birth'),
    (2, 'cow', 'Cow that has given birth'),
    (3, 'bull', 'Male cow');

INSERT INTO contact_types ("id", "name", "description")
VALUES 
    (1, 'mobile', 'mobile number'),
    (2, 'email', 'email address');

INSERT INTO payment_channels ("id", "name", "description")
VALUES 
    (1, 'cash', 'cash payment'),
    (2, 'm-pesa', 'm-pesa payments');

INSERT INTO milking_sessions ("id", "name", "description")
VALUES 
    (1, 'morning', 'first milking session'),
    (2, 'afternoon', 'second milking session'),
    (3, 'evening', 'third milking session');

INSERT INTO statuses ("id", "name", "description")
VALUES 
    (1, 'active', 'resource enabled for all corresponding functionality'),
    (2, 'inactive', 'reources locked for some/all functionslity'),
    (3, 'pending', 'resource awaiting further action'),
    (4, 'complete', 'resource action completed successfully'),
    (5, 'failed', 'transaction unsuccessful'),
    (6, 'dry', 'cow that has stopped giving milk'),
    (7, 'pregnant', 'cow that is pregnant'),
    (8, 'lactating', 'cow that is producing milk'),
    (9, 'pre-calving', 'cow that is yet to give birth'),
    (10, 'dead', 'cow that is dead'),
    (11, 'sold', 'cow that has been sold');

INSERT INTO transaction_types ("id", "name", "description")
VALUES
    (1, 'milk_payment', 'Payment for milk sold'),
    (2, 'credit_transaction', 'Credit milk transaction'),
    (3, 'service_payment', 'Payment for services offered'),
    (4, 'cow_purchase', 'Payment for cow purchased'),
    (5, 'cow_sale', 'Payment received for cow sold'),
    (6, 'products_payment', 'Payment for products purchased');