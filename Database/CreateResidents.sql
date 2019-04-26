
CREATE TYPE RoleType AS ENUM('Resident', 'Guest');
CREATE TABLE Residents(
    residentid SERIAL PRIMARY KEY,
    name text,
    email text,
    role RoleType,
    administrator boolean
);

