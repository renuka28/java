INSERT INTO  custom_users_table (username, password, enabled) values ('renuka', 'renuka', true);
INSERT INTO  custom_users_table (username, password, enabled) values ('gubol', 'gubol', true);
INSERT into custom_authorities_table (username, authority) values ('renuka', 'ROLE_ADMIN');
INSERT into custom_authorities_table (username, authority) values ('gubol', 'ROLE_USER');