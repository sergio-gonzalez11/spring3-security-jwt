INSERT INTO ROLE (ID_ROLE, NAME, DESCRIPTION) VALUES (1, 'Basic', 'User with default roles');
INSERT INTO ROLE (ID_ROLE, NAME, DESCRIPTION) VALUES (2, 'Admin', 'User with all roles');

INSERT INTO `USER` (ID_USER, FIRST_NAME, LAST_NAME, EMAIL, USERNAME, PASSWORD, BIRTHDATE, IS_ENABLED, ID_ROLE) VALUES (1, 'Admin', 'Admin', 'admin100@gmail.com', 'admin100', '$argon2id$v=19$m=65536,t=10,p=1$Awm/6iXn7+F5AxJI3MgHxw$GcDRIlM7T5dvNTj+qpDzL9P+AwRCBRK1GtoZfliVMbQ', now(), true, 2);
INSERT INTO `USER` (ID_USER, FIRST_NAME, LAST_NAME, EMAIL, USERNAME, PASSWORD, BIRTHDATE, IS_ENABLED, ID_ROLE) VALUES (2, 'Username', 'Username', 'username100@gmail.com', 'username100', '$argon2id$v=19$m=65536,t=10,p=1$Awm/6iXn7+F5AxJI3MgHxw$GcDRIlM7T5dvNTj+qpDzL9P+AwRCBRK1GtoZfliVMbQ', now(), true, 1);