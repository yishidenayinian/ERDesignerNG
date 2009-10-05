CREATE TABLE TABLE1 (
    TB2_1 VARCHAR2(20) NOT NULL,
    TB2_2 VARCHAR2(100) DEFAULT 'A',
    TB2_3 NUMBER(20,5) NOT NULL,
    TB2_4 NUMBER(9,0)
);
CREATE UNIQUE INDEX TABL11_IDX1 ON TABLE1 (TB2_2);
CREATE INDEX TABL11_IDX2 ON TABLE1 (TB2_3);
CREATE INDEX TABL11_IDX3 ON TABLE1 (LOWER("TB2_2"));
CREATE TABLE TABLE2 (
    TB3_1 VARCHAR2(20) NOT NULL,
    TB3_2 VARCHAR2(100) DEFAULT 'A',
    TB3_3 NUMBER(20,5) NOT NULL
);
ALTER TABLE TABLE2 ADD CONSTRAINT PK2 PRIMARY KEY(TB3_1);
CREATE VIEW VIEW1 AS SELECT "TB2_1","TB2_2","TB2_3","TB2_4" from Table1;
ALTER TABLE TABLE1 ADD CONSTRAINT FK1 FOREIGN KEY (TB2_1) REFERENCES TABLE2(TB3_1);