Views :
create or replace view vTotalLivre as
select li.idCommande, c.idUser, l.nomLivre, l.prixLivre, li.quantiteLigneCommande,
       (l.prixLivre * li.quantiteLigneCommande) as totalLivre
from livre l
         inner join ligneCommande li
                    on l.idLivre = li.idLivre
         inner join commande c
                    on c.idCommande = li.idCommande;


create or replace view vTotalCommandeEnAttente as
select c.idUser, sum(l.prixLivre * li.quantiteLigneCommande) as totalCommande
from commande c
         inner join ligneCommande li
                    on c.idCommande = li.idCommande
         inner join livre l
                    on li.idLivre = l.idLivre
where c.statutCommande = 'en attente'
group by c.idUser;


create or replace view vTotalCommandeExpediee as
select c.idUser, sum(l.prixLivre * li.quantiteLigneCommande) as totalCommande
from commande c
         inner join ligneCommande li
                    on c.idCommande = li.idCommande
         inner join livre l
                    on li.idLivre = l.idLivre
where c.statutCommande = 'expédiée'
group by c.idUser;


create or replace view vTotalLivreEnAttente as
select li.idLivre, li.idCommande, li.idLigneCommande, c.idUser, l.nomLivre, l.prixLivre, li.quantiteLigneCommande,
       (l.prixLivre * li.quantiteLigneCommande) as totalLivre
from livre l
         inner join ligneCommande li
                    on l.idLivre = li.idLivre
         inner join commande c
                    on c.idCommande = li.idCommande
where c.statutCommande = 'en attente';


create or replace view vTotalLivreExpediee as
select li.idCommande, c.idUser, l.idLivre, l.nomLivre, l.prixLivre, li.quantiteLigneCommande,
       (l.prixLivre * li.quantiteLigneCommande) as totalLivre
from livre l
         inner join ligneCommande li
                    on l.idLivre = li.idLivre
         inner join commande c
                    on c.idCommande = li.idCommande
where c.statutCommande = 'expédiée';


create or replace view vCommandesEnAttente as
select count(c.idCommande) as nbCommandeEnAttente
from commande c
where c.statutCommande = 'en attente';


create or replace view vMeilleuresVentes as
select l.idLivre, l.nomLivre,
       sum(li.quantiteLigneCommande) as totalVendu
from ligneCommande li
         inner join livre l
                    on li.idLivre = l.idLivre
group by l.idLivre, l.nomLivre
order by totalVendu desc;


create or replace view vLivresEnStock as
select l.idLivre, l.nomLivre, l.prixLivre, l.exemplaireLivre
from livre l
where l.exemplaireLivre <= 5;


create or replace view vMeilleursAvis as
select l.idLivre, l.nomLivre, avg(a.noteAvis) as moyenneNote
from avis a
         inner join livre l
                    on a.idLivre = l.idLivre
group by l.idLivre, l.nomLivre
order by moyenneNote desc;


create or replace view vNbLivreAcheteUser as
select u.emailUser, sum(l.quantiteLigneCommande) as nbLivreAchete
from ligneCommande l
         inner join commande c
                    on l.idCommande = c.idCommande
         inner join user u
                    on c.idUser = u.idUser
where c.statutCommande = 'expédiée'
group by u.emailUser;


create or replace view vLivresMieuxNotes as
select idLivre, max(nomLivre) as nomLivre, round(avg(noteAvis), 2) as noteMoyenne
from avis
group by idLivre
order by noteMoyenne desc;



Triggers :
create trigger tExemplaireLivreLigneCommande
before insert on ligneCommande
for each row
begin
    declare existingQuantity int;
select lc.quantiteLigneCommande
into existingQuantity
from ligneCommande lc
         inner join commande c on lc.idCommande = c.idCommande
where lc.idLivre = NEW.idLivre
  and c.idUser = (select idUser from commande where idCommande = NEW.idCommande LIMIT 1)
  and c.statutCommande = 'en attente'
    LIMIT 1;
if existingQuantity is not null then
        SIGNAL SQLSTATE "45000"
        SET MESSAGE_TEXT = "Erreur : Livre déjà dans le panier de cet utilisateur. Veuillez modifier le nombre d'exemplaires.";
end if;
end


create trigger tStockNull
before insert on ligneCommande
for each row
begin
declare exemplaireLivreDisponible int;
select exemplaireLivre
into exemplaireLivreDisponible
from livre
where idLivre = NEW.idLivre;
if exemplaireLivreDisponible < NEW.quantiteLigneCommande then
    SIGNAL SQLSTATE '45000'
    set MESSAGE_TEXT = 'Erreur : Stock insuffisant pour le livre.';
end IF;
end


create trigger tUpdateStockCommandeExpediee
after update on commande
for each row
begin
    declare exemplaireLivreDisponible int;
    select exemplaireLivre
    into exemplaireLivreDisponible
    from livre
    where idLivre = NEW.idLivre;
    if exemplaireLivreDisponible < NEW.quantiteLigneCommande then
    SIGNAL SQLSTATE '45000'
    set MESSAGE_TEXT = 'Erreur : Stock insuffisant pour le livre.';
end IF;
end