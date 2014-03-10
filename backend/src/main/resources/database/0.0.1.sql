

CREATE TABLE db_version (
    version         CHAR(16)        NOT NULL,
    log_date        TIMESTAMP       NOT NULL,
    log_message     VARCHAR(1000)   NOT NULL,
    PRIMARY KEY(version)
);

CREATE TABLE tusers (
    id              BIGINT          NOT NULL AUTO_INCREMENT,
    username        VARCHAR(128)    NOT NULL,
    PRIMARY KEY(id),
    CONSTRAINT tusers_username_uq UNIQUE(username)
);

INSERT INTO db_version VALUES ('0.0.1', '2014-03-10', 'Initial setup');
