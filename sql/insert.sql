-- Insert into chamber_type table two types:
INSERT INTO hospital.chamber_type (chamber_name) VALUES ('ward');
INSERT INTO hospital.chamber_type (chamber_name) VALUES ('cabinet');

-- Insert into chamber table seven chamber:
INSERT INTO hospital.chamber (max_count, number, fk_ch_type) VALUES (20, 1, 2);
INSERT INTO hospital.chamber (max_count, number, fk_ch_type) VALUES (5, 2, 2);
INSERT INTO hospital.chamber (max_count, number, fk_ch_type) VALUES (3, 3, 1);
INSERT INTO hospital.chamber (max_count, number, fk_ch_type) VALUES (5, 4, 1);
INSERT INTO hospital.chamber (max_count, number, fk_ch_type) VALUES (7, 5, 1);
INSERT INTO hospital.chamber (max_count, number, fk_ch_type) VALUES (18, 6, 1);
INSERT INTO hospital.chamber (max_count, number, fk_ch_type) VALUES (8, 7, 1);
INSERT INTO hospital.chamber (max_count, number, fk_ch_type) VALUES (1, 8, 2);

-- Insert into role table:
INSERT INTO hospital.role (role_name) VALUES ('patient');
INSERT INTO hospital.role (role_name) VALUES ('admin');
INSERT INTO hospital.role (role_name) VALUES ('doctor');
INSERT INTO hospital.role (role_name) VALUES ('nurse');

-- Insert into person table

-- admin (staff registration with basic password '1111'
INSERT INTO hospital.person (name, surname, birthday, phone, email, password, fk_role, fk_chamber)
VALUES ('Andrew', 'Maslov', '1985-03-11', '0507841205', 'admin.hospital@gmail.com', 'adminpass', 2, 8);

-- doctors (patient registration with basic password '1111'
INSERT INTO hospital.person (name, surname, birthday, phone, email, password, fk_role, fk_chamber)
VALUES ('Михаил', 'Жильцов', '1972-05-23', '0632551239', 'michael.zh@gmail.com', 'michael555', 3, 1);

INSERT INTO hospital.person (name, surname, birthday, phone, email, password, fk_role, fk_chamber)
VALUES ('Юрий', 'Петрулько', '1964-05-23', '0999912426', 'yuriyp@gmail.com', 'yuriy123321', 3, 1);

INSERT INTO hospital.person (name, surname, birthday, phone, email, password, fk_role, fk_chamber)
VALUES ('Johny', 'Anderson', '1998-11-05', '0504852333', 'johny.and@gmail.com', 'johnyanderson', 3, 1);

-- nurses
INSERT INTO hospital.person (name, surname, birthday, phone, email, password, fk_role, fk_chamber)
VALUES ('Валентина', 'Петрулько', '1963-04-29', '0670442385', 'valyap@gmail.com', 'valyapetr111', 4, 1);

INSERT INTO hospital.person (name, surname, birthday, phone, email, password, fk_role, fk_chamber)
VALUES ('Александра', 'Хорошая', '1999-01-01', '0630574513', 'alex.horoshaya@gmail.com', 'sanya111', 4, 1);
