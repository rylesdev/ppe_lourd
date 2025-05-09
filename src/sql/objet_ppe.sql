VIEWS :
create or replace view vLivresMieuxNotes as
select a.idLivre, max(l.nomLivre) as nomLivre, round(avg(a.noteAvis), 2) as noteMoyenne
from avis a
inner join livre l on a.idLivre=l.idLivre
group by idLivre
order by noteMoyenne desc;


create or replace view vTotalLivre AS
SELECT li.idCommande, c.idUser, l.nomLivre, l.prixLivre, li.quantiteLigneCommande,
       (l.prixLivre * li.quantiteLigneCommande) AS totalLivre
FROM livre l
         inner JOIN ligneCommande li
                    ON l.idLivre = li.idLivre
         inner join commande c
                    on c.idCommande=li.idCommande;


create or replace view vTotalCommandeEnAttente as
select c.idUser, sum(l.prixLivre * li.quantiteLigneCommande) as totalCommande
from commande c
         inner join ligneCommande li
                    on c.idCommande=li.idCommande
         inner join livre l
                    on li.idLivre=l.idLivre
where c.statutCommande = 'en attente'
group by c.idUser;


create or replace view vTotalCommandeExpediee as
select c.idUser, sum(l.prixLivre * li.quantiteLigneCommande) as totalCommande
from commande c
         inner join ligneCommande li
                    on c.idCommande=li.idCommande
         inner join livre l
                    on li.idLivre=l.idLivre
where c.statutCommande = 'expédiée'
group by c.idUser;


CREATE OR REPLACE VIEW vTotalLivreEnAttente AS
SELECT li.idLivre, li.idCommande, li.idLigneCommande, c.idUser, l.nomLivre, l.prixLivre, li.quantiteLigneCommande, (l.prixLivre * li.quantiteLigneCommande) AS totalLivre
FROM livre l
         INNER JOIN ligneCommande li
                    ON l.idLivre = li.idLivre
         INNER JOIN commande c
                    ON c.idCommande = li.idCommande
WHERE
    c.statutCommande = 'en attente';


CREATE OR REPLACE VIEW vTotalLivreExpediee AS
SELECT li.idCommande, c.idUser, l.idLivre, l.nomLivre, l.prixLivre, li.quantiteLigneCommande, (l.prixLivre * li.quantiteLigneCommande) AS totalLivre
FROM livre l
         INNER JOIN ligneCommande li
                    ON l.idLivre = li.idLivre
         INNER JOIN commande c ON c.idCommande = li.idCommande
WHERE c.statutCommande = 'expédiée';


create or replace view vCommandesEnAttente as
select count(c.idCommande) as nbCommandeEnAttente
from commande c
where c.statutCommande = 'en attente';


create or replace view vMeilleuresVentes as
select l.idLivre,l.nomLivre,
       sum(li.quantiteLigneCommande) as totalVendu
from ligneCommande li
         inner join livre l on li.idLivre = l.idLivre
group by l.idLivre, l.nomLivre
order by totalVendu desc;


create or replace view vLivresEnStock as
select l.idLivre, l.nomLivre, l.prixLivre, l.exemplaireLivre
from livre l
where l.exemplaireLivre <= 5;


create view vMeilleursAvis as
select l.idLivre, l.nomLivre, avg(a.noteAvis) as moyenneNote
from avis a
         inner join  livre l
                     on a.idLivre = l.idLivre
group by l.idLivre, l.nomLivre
order by moyenneNote desc;


CREATE VIEW vNbLivreAcheteUser AS
SELECT u.emailUser, SUM(l.quantiteLigneCommande) AS nbLivreAchete
FROM ligneCommande l
         INNER JOIN commande c ON l.idCommande = c.idCommande
         INNER JOIN user u ON c.idUser = u.idUser
WHERE c.statutCommande = 'expédiée'
GROUP BY u.emailUser;



TRIGGERS :
// Trigger qui sert à vérifier si la quantité de livre d''une ligneCommande est supérieur à la quantité d''exemplaire de ce livre.
DELIMITER $$
CREATE TRIGGER tStockLivre
    BEFORE update ON ligneCommande
    FOR EACH ROW
BEGIN
    DECLARE t_totalQuantite INT;
    DECLARE t_exemplaireLivre INT;
    DECLARE t_idUser INT;

    SELECT idUser
    INTO t_idUser
    FROM commande
    WHERE idCommande = NEW.idCommande;

    SELECT SUM(lc.quantiteLigneCommande)
    INTO t_totalQuantite
    FROM ligneCommande lc
             INNER JOIN commande c ON lc.idCommande = c.idCommande
    WHERE lc.idLivre = NEW.idLivre
      AND c.idUser = t_idUser
      AND c.statutCommande = 'en attente'
      AND lc.idLigneCommande != OLD.idLigneCommande;

    SET t_totalQuantite = IFNULL(t_totalQuantite, 0) + NEW.quantiteLigneCommande;

    SELECT exemplaireLivre
    INTO t_exemplaireLivre
    FROM livre
    WHERE idLivre = NEW.idLivre;

    IF t_totalQuantite > t_exemplaireLivre THEN
        SIGNAL SQLSTATE '45000'
		SET MESSAGE_TEXT = 'La quantite totale depasse le nombre exemplaires disponibles pour ce livre';
END IF;
END$$
DELIMITER ;


// Trigger qui sert à mettre à jour la quantité d''exemplaire de livre après une commande.
DELIMITER //
CREATE TRIGGER tUpdateStockCommande
AFTER UPDATE ON commande
FOR EACH ROW
BEGIN
IF NEW.statutCommande = 'expédiée' THEN
UPDATE livre l
JOIN ligneCommande lc ON l.idLivre = lc.idLivre
SET l.exemplaireLivre = l.exemplaireLivre - lc.quantiteLigneCommande
WHERE lc.idCommande = NEW.idCommande;
END IF;
END//
DELIMITER ;


// Trigger qui sert à insérer les champs commande et ligneCommande dans les tables archiveCommande et archiveLigneCommande après la suppression d''un user
DELIMITER //
CREATE TRIGGER tInsertArchive
    before DELETE ON user
    FOR EACH ROW
BEGIN
    INSERT INTO archiveCommande
    SELECT c.idCommande, c.dateCommande, c.statutCommande, c.dateLivraisonCommande, c.idUser, NOW()
    FROM commande c
    WHERE c.idUser = OLD.idUser;

    INSERT INTO archiveLigneCommande
    SELECT lc.idLigneCommande, lc.idCommande, lc.idLivre, lc.quantiteLigneCommande, NOW()
    FROM ligneCommande lc
             JOIN commande c ON lc.idCommande = c.idCommande
    WHERE c.idUser = OLD.idUser;

    DELETE FROM ligneCommande
    WHERE idCommande IN (SELECT idCommande FROM commande WHERE idUser = OLD.idUser);

    DELETE FROM commande
    WHERE idUser = OLD.idUser;
END//
DELIMITER ;



EVENT :
// Event qui sert à mettre à jour le statutCommande en fonction de la dateLivraisonCommande et de la date actuelle.
DELIMITER //
CREATE EVENT IF NOT EXISTS eUpdateStatutCommande
ON SCHEDULE EVERY 1 DAY
STARTS CURRENT_DATE + INTERVAL 1 DAY
DO
BEGIN
UPDATE commande
SET statutCommande = 'arrivée'
WHERE statutCommande = 'expédiée'
  AND dateLivraisonCommande < CURDATE();
END//
DELIMITER ;