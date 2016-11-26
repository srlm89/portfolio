
SET search_path TO bogus;       -- equivalent to 'use bogus'

CREATE TABLE EMPLOYEE (
    id serial NOT NULL PRIMARY KEY,
    job_description text NOT NULL UNIQUE,
    name text NOT NULL
);

