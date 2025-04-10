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