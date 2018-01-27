DROP TABLE PAGAMENTO CASCADE CONSTRAINTS;

DROP SEQUENCE SEQ_PAGAMENTO;
CREATE SEQUENCE SEQ_PAGAMENTO;

CREATE TABLE PAGAMENTO
(ID               NUMBER(5)
   CONSTRAINT PAGAMENTO_ID_PK
   PRIMARY KEY,
 TIPO_PGTO        CHAR(3),
 DATA_PGTO        DATE,
 VALOR            NUMBER(9,2),
 BANCO            VARCHAR2(10),
 AGENCIA          VARCHAR2(10),
 CONTA            VARCHAR2(10),
 NUMERO           VARCHAR2(10),
 MES_EXP          CHAR(02),
 ANO_EXP          CHAR(04));


