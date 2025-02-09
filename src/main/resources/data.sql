INSERT INTO member (id, username, password, name, nickname, phone_number)
VALUES (1, 'test', '{noop}otrtest', 'test', 'test', '010-0000-0000');

INSERT INTO role (id, authority, member_id)
VALUES (1, 'GUEST', 1);

INSERT INTO role (id, authority, member_id)
VALUES (2, 'HOST', 1);

INSERT INTO role (id, authority, member_id)
VALUES (3, 'ADMIN', 1);