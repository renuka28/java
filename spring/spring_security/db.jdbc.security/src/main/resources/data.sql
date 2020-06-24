INSERT INTO  users (username, password, enabled) values ('renuka', 'renuka', true);
INSERT INTO  users (username, password, enabled) values ('gubol', 'gubol', true);
INSERT into authorities (username, authority) values ('renuka', 'ROLE_ADMIN');
INSERT into authorities (username, authority) values ('gubol', 'ROLE_USER');