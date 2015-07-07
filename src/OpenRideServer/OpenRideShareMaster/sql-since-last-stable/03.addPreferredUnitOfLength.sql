-- Add a maximum number of matches to be displayed.
-- this is limit may be changed by the user in a range of 1...matchLimitMax.
ALTER TABLE customer add  preferredUnitOfLength  int;
UPDATE customer set preferredUnitOfLength = 2  where preferredunitoflength is NULL ; 
