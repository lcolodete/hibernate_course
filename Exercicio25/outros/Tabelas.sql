CONNECT HIBERNATE25/HIBERNATE25

CREATE SEQUENCE SEQ_PAGAMENTO;

CREATE TABLE CONTA
(ID               NUMBER(5)
   CONSTRAINT CONTA_ID_PK
   PRIMARY KEY,
 VALOR            NUMBER(9,2),
 DATA_PGTO        DATE,
 BANCO            VARCHAR2(10),
 AGENCIA          VARCHAR2(10),
 CONTA            VARCHAR2(10));

CREATE TABLE CARTAO
(ID               NUMBER(5)
   CONSTRAINT CARTAO_ID_PK
   PRIMARY KEY,
 VALOR            NUMBER(9,2),
 DATA_PGTO        DATE,
 NUMERO           VARCHAR2(10),
 MES_EXP          CHAR(02),
 ANO_EXP          CHAR(04));

