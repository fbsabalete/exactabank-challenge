CREATE TABLE account
(
    id      BIGINT AUTO_INCREMENT NOT NULL,
    balance DECIMAL NOT NULL,
    CONSTRAINT pk_account PRIMARY KEY (id)
);

CREATE TABLE pix_key
(
    id         BIGINT AUTO_INCREMENT NOT NULL,
    key_type   VARCHAR(255) NOT NULL,
    pix_key    VARCHAR(255) NOT NULL,
    account_fk BIGINT NOT NULL,
    CONSTRAINT pk_pixkey PRIMARY KEY (id)
);

CREATE TABLE transaction
(
    id                    BIGINT AUTO_INCREMENT NOT NULL,
    account_fk            BIGINT NULL,
    target_account_fk     BIGINT NULL,
    transaction_type      VARCHAR(255) NULL,
    amount                DECIMAL NULL,
    agency_number         INT NULL,
    transaction_date_time datetime NULL,
    CONSTRAINT pk_transaction PRIMARY KEY (id)
);

ALTER TABLE pix_key
    ADD CONSTRAINT uc_b97b0753e8a17c8624c65a244 UNIQUE (pix_key, key_type);

ALTER TABLE pix_key
    ADD CONSTRAINT FK_PIXKEY_ON_ACCOUNT_FK FOREIGN KEY (account_fk) REFERENCES account (id);

ALTER TABLE transaction
    ADD CONSTRAINT FK_TRANSACTION_ON_ACCOUNT_FK FOREIGN KEY (account_fk) REFERENCES account (id);

ALTER TABLE transaction
    ADD CONSTRAINT FK_TRANSACTION_ON_TARGET_ACCOUNT_FK FOREIGN KEY (target_account_fk) REFERENCES account (id);