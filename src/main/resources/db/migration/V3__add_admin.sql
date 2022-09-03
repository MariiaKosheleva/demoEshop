INSERT INTO users (id, email, name, password, role)
VALUES (1, 'admin@mail.com', 'admin', '$2a$10$QRWAbXveneW1d.EKZPnxV.D7hjqPfw9Ex4nJuJpLgiFCDFopflogC', 'ADMIN');

ALTER SEQUENCE user_seq RESTART WITH 2;

