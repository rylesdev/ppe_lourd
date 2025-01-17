CREATE TABLE user (
    idUser int NOT NULL,
    nomUser varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci DEFAULT NULL,
    prenomUser varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci DEFAULT NULL,
    emailUser varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci DEFAULT NULL,
    mdpUser varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci DEFAULT NULL,
    adresseUser varchar(50) NOT NULL,
    roleUser enum('admin','client') CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci DEFAULT NULL,
    dateInscriptionUser date DEFAULT NULL,
    primary key user (idUser)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

CREATE TABLE livre (
    idLivre int NOT NULL,
    nomLivre varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL,
    categorieLivre varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci DEFAULT NULL,
    auteurLivre varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci DEFAULT NULL,
    imageLivre varchar(50) NOT NULL,
    exemplaireLivre int DEFAULT NULL,
    prixLivre float(10,2) NOT NULL,
    primary key livre (idLivre)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

CREATE TABLE commande (
    idCommande int NOT NULL,
    dateCommande date DEFAULT NULL,
    statutCommande enum('en attente','annulée','expédiée','arrivée'),
    dateLivraisonCommande date DEFAULT NULL,
    idUser int DEFAULT NULL,
    primary key commande (idCommande),
    foreign key (idUser) references user (idUser)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

CREATE TABLE ligneCommande (
    idCommande int NOT NULL,
    idLivre int NOT NULL,
    quantiteLigneCommande int NOT NULL DEFAULT '1',
    primary key ligneCommande (idCommande)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;