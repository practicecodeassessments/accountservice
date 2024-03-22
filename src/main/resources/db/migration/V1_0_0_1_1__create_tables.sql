CREATE TABLE account
(
    id                         bigint IDENTITY (1, 1) NOT NULL,
    created_on                 datetime,
    last_modified_on           datetime,
    customer_id                bigint,
    account_number             varchar(255),
    type                       varchar(255),
    status                     varchar(255),
    available_balance          decimal(18, 0),
    balance                    decimal(18, 0),
    last_balance_update_status varchar(255),
    CONSTRAINT pk_account PRIMARY KEY (id)
);
    GO

CREATE TABLE customer
(
    id               bigint IDENTITY (1, 1) NOT NULL,
    created_on       datetime,
    last_modified_on datetime,
    first_name       varchar(255),
    last_name        varchar(255),
    email            varchar(255),
    address          varchar(255),
    phone            varchar(255),
    customer_id      bigint,
    CONSTRAINT pk_customer PRIMARY KEY (id)
);
    GO

ALTER TABLE account
    ADD CONSTRAINT FK_ACCOUNT_ON_CUSTOMER FOREIGN KEY (customer_id) REFERENCES customer (id)
    GO