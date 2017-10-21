CREATE TABLE Subject(
code VARCHAR(10) NOT NULL,
name VARCHAR(100) NOT NULL,
duration double NOT NULL,
numStudents int NOT NULL,
PRIMARY KEY (code),
UNIQUE code_UNIQUE (code ASC)
);