-- Add a maximum number of open requests.
-- this is limit may not be changed by the user.
ALTER TABLE customer add requestLimit int;
