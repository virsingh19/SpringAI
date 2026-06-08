DROP TABLE IF EXISTS PLAYERS;

-- Create a table from the csv
CREATE TABLE HOUSES AS SELECT * FROM CSVREAD('ny_real_estate.csv');