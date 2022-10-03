-- Clean UP Previous Database Tables

USE `full-stack-ecommerce`;

SET FOREIGN_KEY_CHECKS=0;

TRUNCATE customer;
TRUNCATE orders;
TRUNCATE order_item;
TRUNCATE address;

SET FOREIGN_KEY_CHECKS=1;

-- Make the Email Addres UNIQUE
ALTER TABLE customer ADD UNIQUE (email);