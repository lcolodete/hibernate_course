DROP USER HIBERNATE21 CASCADE;
CREATE USER HIBERNATE21 IDENTIFIED BY HIBERNATE21;
GRANT CONNECT, RESOURCE TO HIBERNATE21;

CONNECT HIBERNATE21/HIBERNATE21

CREATE SEQUENCE SEQ_LANCE;
CREATE SEQUENCE SEQ_PRODUTO;
CREATE SEQUENCE SEQ_CATEGORIA;

CREATE TABLE PRODUTO
(ID             NUMBER(5)
   CONSTRAINT PRODUTO_ID_PK
   PRIMARY KEY,
 NOME           VARCHAR2(30) 
   CONSTRAINT PRODUTO_NOME_NN
   NOT NULL,
 DESCRICAO      VARCHAR2(40),
 LANCE_MINIMO  NUMBER(8,2)
   CONSTRAINT PRODUTO_LANCE_MINIMO_NN
   NOT NULL,
 DATA_CADASTRO  DATE
   CONSTRAINT PRODUTO_DATA_CADASTRO_NN
   NOT NULL,
 DATA_VENDA     DATE,
 LANCE_VENCEDOR_ID NUMBER(5));

CREATE TABLE LANCE
(ID             NUMBER(5)
   CONSTRAINT LANCE_ID_PK
   PRIMARY KEY,
 VALOR          NUMBER(8,2)
   CONSTRAINT LANCE_VALOR_NN
   NOT NULL,
 DATA_CRIACAO   DATE
   CONSTRAINT LANCE_DATA_CRIACAO_NN
   NOT NULL,
 PRODUTO_ID     NUMBER(5)
   CONSTRAINT LANCE_PRODUTO_FK
   REFERENCES PRODUTO(ID)
   CONSTRAINT LANCE_PRODUTO_ID_NN
   NOT NULL);

ALTER  TABLE  PRODUTO
ADD  CONSTRAINT  PRODUTO_LANCE_FK
FOREIGN  KEY  (LANCE_VENCEDOR_ID)
REFERENCES LANCE(ID);

CREATE TABLE CATEGORIA
(ID                NUMBER(5)
   CONSTRAINT CATEGORIA_ID_PK
   PRIMARY KEY,
 NOME              VARCHAR2(30)
   CONSTRAINT CATEGORIA_NOME_NN
   NOT NULL,
 ID_CATEGORIA_PAI  NUMBER(5)
   CONSTRAINT CATEGORIA_CATEGORIA_FK
   REFERENCES CATEGORIA(ID));

CREATE TABLE PRODUTO_CATEGORIA
(ID_CATEGORIA        NUMBER(5)
    CONSTRAINT CATEGORIA_PRODUTO_CATEGORIA_FK
    REFERENCES CATEGORIA(ID),
 ID_PRODUTO          NUMBER(5)
    CONSTRAINT CATEGORIA_PRODUTO_PRODUTO_FK
    REFERENCES PRODUTO(ID),
CONSTRAINT CATEGORIA_PRODUTO_PK
PRIMARY KEY(ID_CATEGORIA, ID_PRODUTO));

INSERT INTO PRODUTO(ID, NOME, DESCRICAO, LANCE_MINIMO, DATA_CADASTRO)
VALUES(SEQ_PRODUTO.NEXTVAL, 'TV SAMSUNG 20 POL', 'TV SAMSUNG 20 POL TELA PLANA', 2000, SYSDATE);

INSERT INTO LANCE(ID, VALOR, DATA_CRIACAO, PRODUTO_ID)
VALUES(SEQ_LANCE.NEXTVAL, 2100, SYSDATE, SEQ_PRODUTO.CURRVAL);


INSERT INTO LANCE(ID, VALOR, DATA_CRIACAO, PRODUTO_ID)
VALUES(SEQ_LANCE.NEXTVAL, 2200, SYSDATE, SEQ_PRODUTO.CURRVAL);

INSERT INTO PRODUTO(ID, NOME, DESCRICAO, LANCE_MINIMO, DATA_CADASTRO)
VALUES(SEQ_PRODUTO.NEXTVAL, 'TV SAMSUNG 22 POL', 'TV SAMSUNG 22 POL TELA PLANA', 2500, SYSDATE);

INSERT INTO LANCE(ID, VALOR, DATA_CRIACAO, PRODUTO_ID)
VALUES(SEQ_LANCE.NEXTVAL, 2600, SYSDATE, SEQ_PRODUTO.CURRVAL);

INSERT INTO LANCE(ID, VALOR, DATA_CRIACAO, PRODUTO_ID)
VALUES(SEQ_LANCE.NEXTVAL, 2700, SYSDATE, SEQ_PRODUTO.CURRVAL);


INSERT INTO CATEGORIA(ID, NOME, ID_CATEGORIA_PAI)
VALUES(SEQ_CATEGORIA.NEXTVAL, 'ELETRODOMESTICO', NULL);

INSERT INTO CATEGORIA(ID, NOME, ID_CATEGORIA_PAI)
VALUES(SEQ_CATEGORIA.NEXTVAL, 'COMPUTADOR', NULL);

INSERT INTO CATEGORIA(ID, NOME, ID_CATEGORIA_PAI)
VALUES(SEQ_CATEGORIA.NEXTVAL, 'TELEVISOR', 1);

COMMIT;


Para exibir os dados cadastrados:
================================

alter session set nls_date_format='dd/MM/yyyy hh24:mi:ss';
