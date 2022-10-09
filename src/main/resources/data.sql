INSERT INTO user (id, created, updated, name, email, password)
VALUES (
        '3ee5ca42-b5f2-4f61-8d14-1a4840f741ce',
        NOW(),
        NOW(),
        'Kshtiij Dhakal',
        'dhakalkshitij@gmail.com',
        '{bcrypt}$2a$10$G7lmJ7BvlB8dzDpuDaYc/OTLauC3.D7t0DwE0R8Hr/WlPxgmD88mK'
    );
INSERT INTO user_roles (User_id, roles)
VALUES ('3ee5ca42-b5f2-4f61-8d14-1a4840f741ce', 0);