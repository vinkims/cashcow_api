-- seed income_types
INSERT INTO income_types ("id", "name", "description", "status_id")
VALUES 
    (1, 'milk_sale', 'income from the sale of milk', 1),
    (2, 'cow_sale', 'income from the sale of cows', 1),
    (3, 'manure_sale', 'income from the sale of manure', 1);

alter sequence IF EXISTS income_types_id_seq restart with 4;

-- seed expense_types
INSERT INTO expense_types ("id", "name", "description", "status_id")
VALUES
    (1, 'cow_purchase', 'purchase of cows, calves or bulls', 1),
    (2, 'transport', 'transport of all products related to the farm', 1),
    (3, 'product_purchase', 'purchase of products such as feeds, medication', 1),
    (4, 'staff_salary', 'farm staff salary', 1),
    (5, 'staff_advance', 'farm staff salary advance', 1),
    (6, 'utilities', 'utility expenses such as electricity, water', 1),
    (7, 'milk_losses', 'loss of milk', 1),
    (8, 'service_expense', 'expenses due to  services offered', 1);

alter sequence IF EXISTS expense_types_id_seq restart with 9;

-- seed transaction_types
INSERT INTO transaction_types ("id", "name", "description")
VALUES 
    (13, 'income', 'income transaction type'),
    (14, 'expense', 'expense transaction type');

alter sequence IF EXISTS transaction_types_id_seq restart with 15;