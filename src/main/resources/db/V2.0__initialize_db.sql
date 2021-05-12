
    CREATE TABLE account (
        acc_number varchar(255) not null,
        balance int not null,
        name varchar(255),
        pin varchar(255),
        CONSTRAINT pk_account PRIMARY KEY (acc_number)
    );

    CREATE TABLE transaction (
        transaction_id int not null,
        account_number varchar(255),
        amount varchar(255),
        balance int not null,
        desc_account_number varchar(255),
        transaction_date date,
        transaction_type varchar(255),
        ref_no varchar(255),
        CONSTRAINT pk_transaction PRIMARY KEY (transaction_id)
    );

