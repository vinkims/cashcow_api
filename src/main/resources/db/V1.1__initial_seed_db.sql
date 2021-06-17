INSERT INTO roles ("id", "name", "description")
VALUES 
    (1, 'system-admin', 'Cash cow system administrator'),
    (2, 'admin', 'Cash cow administrator'),
    (3, 'shop-attendant', 'Milk shop attendant'),
    (4, 'customer', 'Milk customer'),
    (5, 'supplier', 'Products e.g feeds supplier'),
    (6, 'vet', 'Veterinary doctor/person offering services');

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