DROP TABLE IF EXISTS manager_auth;
DROP TABLE IF EXISTS foods;
DROP TABLE IF EXISTS manager;
DROP SEQUENCE IF EXISTS global_sequence;

CREATE SEQUENCE global_sequence START WITH 100000;

CREATE TABLE managers
(
    id               INTEGER PRIMARY KEY DEFAULT nextval('global_sequence'),
    name             VARCHAR                           NOT NULL,
    email            VARCHAR                           NOT NULL,
    password         VARCHAR                           NOT NULL,
    registered       TIMESTAMP           DEFAULT now() NOT NULL,
    enabled          BOOL                DEFAULT TRUE  NOT NULL,
    calories_per_day INTEGER             DEFAULT 2000  NOT NULL
);
CREATE UNIQUE INDEX managers_unique_email_idx ON managers (email);

CREATE TABLE manager_auth
(
    manager_id INTEGER NOT NULL,
    role       VARCHAR,
    CONSTRAINT user_roles_idx UNIQUE (manager_id, role),
    FOREIGN KEY (manager_id) REFERENCES managers (id) ON DELETE CASCADE
);

CREATE TABLE foods
(
    id          INTEGER PRIMARY KEY DEFAULT nextval('global_sequence'),
    manager_id  INTEGER   NOT NULL,
    date_time   TIMESTAMP NOT NULL,
    description TEXT      NOT NULL,
    calories    INT       NOT NULL,
    FOREIGN KEY (manager_id) REFERENCES managers (id) ON DELETE CASCADE
);
CREATE UNIQUE INDEX foods_unique_manager_datetime_idx ON foods (manager_id, date_time);