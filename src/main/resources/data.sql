CREATE TABLE Product

(

   id        INT PRIMARY KEY,

   nom       VARCHAR(255) NOT NULL,

   prix      INT         NOT NULL,

   prix_achat INT         NOT NULL

);


INSERT INTO Product VALUES(1, 'Ordinateur portable' , 350, 120);

 

INSERT INTO Product VALUES(2, 'Aspirateur Robot' , 500, 200);

 

INSERT INTO Product VALUES(3, 'Table de Ping Pong' , 750, 400);