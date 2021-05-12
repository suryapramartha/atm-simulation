--Account Data
    INSERT INTO Account (acc_number, pin, name, balance) VALUES ('112233',120108, 'John Doe', 60);
    INSERT INTO Account (acc_number, pin, name, balance) VALUES ('112244',932012, 'Jane Doe', 60);
    INSERT INTO Account (acc_number, pin, name, balance) VALUES ('112211',345234, 'Jacky Chan', 40);
    INSERT INTO Account (acc_number, pin, name, balance) VALUES ('112222',907834, 'Jessie Jane', 90);
    INSERT INTO Account (acc_number, pin, name, balance) VALUES ('112255',453421, 'Jack Sparrow', 30);
    INSERT INTO Account (acc_number, pin, name, balance) VALUES ('112266',889898, 'Anna', 100);
    INSERT INTO Account (acc_number, pin, name, balance) VALUES ('112277',332323, 'Tania', 200);
    INSERT INTO Account (acc_number, pin, name, balance) VALUES ('112278',932012, 'Alita', 30);
    INSERT INTO Account (acc_number, pin, name, balance) VALUES ('112279',120108, 'Michael Burry', 60);
    INSERT INTO Account (acc_number, pin, name, balance) VALUES ('112280',932012, 'John Cena', 60);
    INSERT INTO Account (acc_number, pin, name, balance) VALUES ('112281',120108, 'Brock Lesnar', 70);
    INSERT INTO Account (acc_number, pin, name, balance) VALUES ('112282',932012, 'Jeff Musk', 30);
    INSERT INTO Account (acc_number, pin, name, balance) VALUES ('112283',120108, 'Elon Mark', 90);
    INSERT INTO Account (acc_number, pin, name, balance) VALUES ('112284',932012, 'Bob Marley', 50);
    INSERT INTO Account (acc_number, pin, name, balance) VALUES ('112285',120108, 'Rob Barley', 20);
    INSERT INTO Account (acc_number, pin, name, balance) VALUES ('112286',932012, 'David Beck', 120);
    INSERT INTO Account (acc_number, pin, name, balance) VALUES ('112287',120108, 'George Bash', 80);
    INSERT INTO Account (acc_number, pin, name, balance) VALUES ('112288',932012, 'Neil Unstrong', 110);
    INSERT INTO Account (acc_number, pin, name, balance) VALUES ('112289',120108, 'Diana', 50);
    INSERT INTO Account (acc_number, pin, name, balance) VALUES ('112290',932012, 'Saitama', 90);

--Transaction Data
    INSERT INTO Transaction (transaction_id, account_number, amount, balance, transaction_date,transaction_type, ref_no) VALUES ('1',120108, '10', 80, '2021-01-01','WITHDRAW', '999999');
    INSERT INTO Transaction (transaction_id, account_number, amount, balance, transaction_date,transaction_type, ref_no) VALUES ('2',112233, '20', 80, '2021-01-01','WITHDRAW', '999999');
    INSERT INTO Transaction (transaction_id, account_number, amount, balance, transaction_date,transaction_type, ref_no) VALUES ('3',100000, '30', 80, '2021-01-01','WITHDRAW', '999999');
    INSERT INTO Transaction (transaction_id, account_number, amount, balance, transaction_date,transaction_type, ref_no) VALUES ('4',100002, '40', 80, '2021-01-01','WITHDRAW', '999999');
    INSERT INTO Transaction (transaction_id, account_number, amount, balance, transaction_date,transaction_type, ref_no) VALUES ('5',100002, '50', 80, '2021-01-01','WITHDRAW', '999999');
    INSERT INTO Transaction (transaction_id, account_number, amount, balance, transaction_date,transaction_type, ref_no) VALUES ('6',100022, '60', 80, '2021-01-01','WITHDRAW', '999999');
    INSERT INTO Transaction (transaction_id, account_number, amount, balance, transaction_date,transaction_type, ref_no) VALUES ('7',100009, '70', 80, '2021-01-01','WITHDRAW', '999999');
    INSERT INTO Transaction (transaction_id, account_number, amount, balance, transaction_date,transaction_type, ref_no) VALUES ('8',100004, '80', 80, '2021-01-01','WITHDRAW', '999999');
    INSERT INTO Transaction (transaction_id, account_number, amount, balance, transaction_date,transaction_type, ref_no) VALUES ('9',100003, '90', 80, '2021-01-01','WITHDRAW', '999999');
    INSERT INTO Transaction (transaction_id, account_number, amount, balance, transaction_date,transaction_type, ref_no) VALUES ('10',100004, '010', 80, '2021-01-01','WITHDRAW', '999999');
