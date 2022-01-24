--Add milk selling price to user_profiles 
ALTER TABLE user_profiles 
ADD COLUMN "milk_price" NUMERIC(5,2),
ADD COLUMN "price_valid_on" DATE,
ADD COLUMN "price_expires_on" DATE;