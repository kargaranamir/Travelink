
CREATE TABLE User (
                Token VARCHAR NOT NULL,
                First_Name VARCHAR NOT NULL,
                phone_number VARCHAR NOT NULL,
                Last_Name VARCHAR NOT NULL,
                Profile_Image_address_ VARCHAR NOT NULL,
                PRIMARY KEY (Token)
);


CREATE TABLE Help_Center (
                Ticket_ID VARCHAR NOT NULL,
                Token VARCHAR NOT NULL,
                Subject VARCHAR NOT NULL,
                text LONGNVARCHAR NOT NULL,
                Status VARCHAR NOT NULL,
                Answer VARCHAR NOT NULL,
                PRIMARY KEY (Ticket_ID, Token)
);


CREATE TABLE Traveling_History (
                Token VARCHAR NOT NULL,
                Path NVARCHAR NOT NULL,
                Date DATE NOT NULL,
                PRIMARY KEY (Token)
);


CREATE TABLE Score_Page (
                Token VARCHAR NOT NULL,
                Link VARCHAR NOT NULL,
                Score NUMERIC NOT NULL,
                PRIMARY KEY (Token)
);


CREATE TABLE Ewallet (
                Ewallet_ID VARCHAR NOT NULL,
                Token VARCHAR NOT NULL,
                balance NUMERIC NOT NULL,
                PRIMARY KEY (Ewallet_ID)
);


CREATE TABLE Transaction_logs_ (
                Ewallet_ID VARCHAR NOT NULL,
                Date DATE NOT NULL,
                amount NUMERIC NOT NULL,
                PRIMARY KEY (Ewallet_ID)
);


ALTER TABLE Ewallet ADD CONSTRAINT user_ewallet_fk
FOREIGN KEY (Token)
REFERENCES User (Token)
ON DELETE NO ACTION
ON UPDATE NO ACTION;

ALTER TABLE Score_Page ADD CONSTRAINT user_score_page_fk
FOREIGN KEY (Token)
REFERENCES User (Token)
ON DELETE NO ACTION
ON UPDATE NO ACTION;

ALTER TABLE Traveling_History ADD CONSTRAINT user_traveling_history_fk
FOREIGN KEY (Token)
REFERENCES User (Token)
ON DELETE NO ACTION
ON UPDATE NO ACTION;

ALTER TABLE Help_Center ADD CONSTRAINT user_help_center_fk
FOREIGN KEY (Token)
REFERENCES User (Token)
ON DELETE NO ACTION
ON UPDATE NO ACTION;

ALTER TABLE Transaction_logs_ ADD CONSTRAINT ewallet_transaction_logs__fk
FOREIGN KEY (Ewallet_ID)
REFERENCES Ewallet (Ewallet_ID)
ON DELETE NO ACTION
ON UPDATE NO ACTION;