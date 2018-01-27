CONNECT HIBERNATE06/HIBERNATE06

DROP SEQUENCE SEQ_CLIENTE;
CREATE SEQUENCE SEQ_CLIENTE;

DROP TABLE CLIENTE CASCADE CONSTRAINTS;

CREATE TABLE CLIENTE
(NUMERO       NUMBER(5)
   CONSTRAINT CLIENTES_NUMERO_PK
   PRIMARY KEY,
 NOME         VARCHAR2(50),
 SALARIO      NUMBER(9,2),
 VERSAO       NUMBER(10));   

INSERT INTO CLIENTE (NUMERO, NOME, SALARIO, VERSAO)
VALUES (SEQ_CLIENTE.NEXTVAL, 'JULIO CESAR', 2500.00, 0);

INSERT INTO CLIENTE (NUMERO, NOME, SALARIO, VERSAO)
VALUES (SEQ_CLIENTE.NEXTVAL, 'JULIANA SILVA', 1800.00, 0);

INSERT INTO CLIENTE (NUMERO, NOME, SALARIO, VERSAO)
VALUES (SEQ_CLIENTE.NEXTVAL, 'SERGIO ARANTES', 3800.00, 0);

COMMIT;
