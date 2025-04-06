INSERT INTO member (id, username, password, name, nickname, phone_number, picture, created_at, modified_at)
VALUES (1, 'test', '{noop}otrtest', 'test', 'test', '010-0000-0000', 'https://d3j0mzt56d6iv2.cloudfront.net/images/o/test/logo-symbol.jpg', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);

INSERT INTO member_roles (member_id, roles)
VALUES (1, 'GUEST');

INSERT INTO member_roles (member_id, roles)
VALUES (1, 'HOST');

INSERT INTO member_roles (member_id, roles)
VALUES (1, 'ADMIN');
