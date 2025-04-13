-- phpMyAdmin SQL Dump
-- version 5.2.1
-- https://www.phpmyadmin.net/
--
-- Hôte : localhost:8889
-- Généré le : dim. 13 avr. 2025 à 11:26
-- Version du serveur : 8.0.35
-- Version de PHP : 8.3.9

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Base de données : `ppe_lourd`
--

-- --------------------------------------------------------

--
-- Structure de la table `abonnement`
--

CREATE TABLE `abonnement` (
  `idAbonnement` int NOT NULL,
  `idUser` int NOT NULL,
  `dateDebutAbonnement` date NOT NULL,
  `dateFinAbonnement` date DEFAULT NULL,
  `pointAbonnement` int NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

--
-- Déchargement des données de la table `abonnement`
--

INSERT INTO `abonnement` (`idAbonnement`, `idUser`, `dateDebutAbonnement`, `dateFinAbonnement`, `pointAbonnement`) VALUES
(1, 2, '2025-01-01', '2025-12-31', 0),
(3, 23, '2025-01-25', '2025-04-25', 0),
(4, 15, '2025-01-26', '2025-02-28', 80);

-- --------------------------------------------------------

--
-- Structure de la table `admin`
--

CREATE TABLE `admin` (
  `idAdmin` int NOT NULL,
  `idUser` int DEFAULT NULL,
  `niveauAdmin` enum('principal','junior','superadmin') NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

--
-- Déchargement des données de la table `admin`
--

INSERT INTO `admin` (`idAdmin`, `idUser`, `niveauAdmin`) VALUES
(1, 1, 'principal');

-- --------------------------------------------------------

--
-- Structure de la table `avis`
--

CREATE TABLE `avis` (
  `idAvis` int NOT NULL,
  `idLivre` int NOT NULL,
  `nomLivre` varchar(50) NOT NULL,
  `idUser` int NOT NULL,
  `commentaireAvis` text NOT NULL,
  `noteAvis` tinyint NOT NULL,
  `dateAvis` date NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

--
-- Déchargement des données de la table `avis`
--

INSERT INTO `avis` (`idAvis`, `idLivre`, `nomLivre`, `idUser`, `commentaireAvis`, `noteAvis`, `dateAvis`) VALUES
(1, 1, 'Alcools', 15, 'Poésie complexe mais fascinante', 4, '2025-02-01'),
(2, 1, 'Alcools', 16, 'Difficile d\'accès', 2, '2025-02-02'),
(3, 1, 'Alcools', 17, 'Un must de la poésie moderne', 5, '2025-02-03'),
(4, 2, 'Crime et Chatiment', 18, 'Profondeur psychologique remarquable', 3, '2025-02-04'),
(5, 2, 'Crime et Chatiment', 19, 'Trop long et lent', 1, '2025-02-05'),
(6, 2, 'Crime et Chatiment', 20, 'Une œuvre magistrale', 4, '2025-02-06'),
(7, 3, 'L`Etranger', 21, 'Absurde et génial', 2, '2025-02-07'),
(8, 3, 'L`Etranger', 22, 'Déprimant mais nécessaire', 5, '2025-02-08'),
(9, 3, 'L`Etranger', 23, 'Une réflexion sur l\'existence', 3, '2025-02-09'),
(10, 4, 'L`Odyssée', 24, 'Fondateur de la littérature occidentale', 1, '2025-02-10'),
(11, 4, 'L`Odyssée', 25, 'Version ancienne peu accessible', 4, '2025-02-11'),
(12, 4, 'L`Odyssée', 26, 'Indispensable classique', 2, '2025-02-12'),
(13, 5, 'Les Fleurs du Mal', 27, 'Beauté noire envoûtante', 5, '2025-02-13'),
(14, 5, 'Les Fleurs du Mal', 28, 'Trop sombre à mon goût', 3, '2025-02-14'),
(15, 5, 'Les Fleurs du Mal', 29, 'Baudelaire au sommet', 1, '2025-02-15'),
(16, 6, 'PHP et MySQL pour les nuls', 30, 'Parfait pour débuter', 4, '2025-02-16'),
(17, 6, 'PHP et MySQL pour les nuls', 31, 'Exemples concrets utiles', 2, '2025-02-17'),
(18, 6, 'PHP et MySQL pour les nuls', 32, 'Un peu trop basique', 5, '2025-02-18'),
(19, 7, 'Programmer en Java', 33, 'Guide complet mais dense', 3, '2025-02-19'),
(20, 7, 'Programmer en Java', 34, 'Approche pédagogique claire', 1, '2025-02-20'),
(21, 7, 'Programmer en Java', 35, 'Exercices pratiques manquants', 4, '2025-02-21'),
(22, 8, 'SPQR', 36, 'Histoire romaine captivante', 2, '2025-02-22'),
(23, 8, 'SPQR', 37, 'Trop académique', 5, '2025-02-23'),
(24, 8, 'SPQR', 38, 'Une nouvelle perspective', 3, '2025-02-24'),
(25, 9, 'À la recherche du temps perdu', 39, 'Chef-d\'œuvre de la littérature', 1, '2025-02-25'),
(26, 9, 'À la recherche du temps perdu', 40, 'Style trop alambiqué', 4, '2025-02-26'),
(27, 9, 'À la recherche du temps perdu', 41, 'Expérience immersive', 2, '2025-02-27'),
(28, 10, 'Les Misérables', 42, 'Roman social puissant', 5, '2025-02-28'),
(29, 10, 'Les Misérables', 43, 'Long mais enrichissant', 3, '2025-03-01'),
(30, 10, 'Les Misérables', 44, 'Personnages mémorables', 1, '2025-03-02'),
(31, 11, '1984', 45, 'Vision terrifiante du futur', 4, '2025-03-03'),
(32, 11, '1984', 46, 'Toujours d\'actualité', 2, '2025-03-04'),
(33, 11, '1984', 47, 'Un peu angoissant', 5, '2025-03-05'),
(34, 12, 'L`Art d\'aimer', 48, 'Approche philosophique', 3, '2025-03-06'),
(35, 12, 'L`Art d\'aimer', 49, 'Intéressant mais daté', 1, '2025-03-07'),
(36, 12, 'L`Art d\'aimer', 50, 'Une lecture surprenante', 4, '2025-03-08'),
(37, 13, 'La Peste', 51, 'Allégorie brillante', 2, '2025-03-09'),
(38, 13, 'La Peste', 52, 'Pertinent en période d\'épidémie', 5, '2025-03-10'),
(39, 13, 'La Peste', 53, 'Nécessite une relecture', 3, '2025-03-11'),
(40, 14, 'Les Mémoires d\'Hadrien', 54, 'Écriture sublime', 1, '2025-03-12'),
(41, 14, 'Les Mémoires d\'Hadrien', 55, 'Rythme trop lent', 4, '2025-03-13'),
(42, 14, 'Les Mémoires d\'Hadrien', 56, 'Portrait historique vivant', 2, '2025-03-14'),
(43, 15, 'La Condition humaine', 57, 'Roman profondément humain', 5, '2025-03-15'),
(44, 15, 'La Condition humaine', 58, 'Passages confus', 3, '2025-03-16'),
(45, 15, 'La Condition humaine', 59, 'Une réflexion sur l\'engagement', 1, '2025-03-17'),
(46, 16, 'Le Comte de Monte-Cristo', 60, 'Intrigue palpitante', 4, '2025-03-18'),
(47, 16, 'Le Comte de Monte-Cristo', 61, 'Trop de rebondissements', 2, '2025-03-19'),
(48, 16, 'Le Comte de Monte-Cristo', 62, 'Un classique du roman-feuilleton', 5, '2025-03-20'),
(49, 17, 'Orgueil et Préjugés', 63, 'Ironie sociale délicieuse', 3, '2025-03-21'),
(50, 17, 'Orgueil et Préjugés', 64, 'Personnages féminins forts', 1, '2025-03-22'),
(51, 17, 'Orgueil et Préjugés', 65, 'Un peu trop romantique', 4, '2025-03-23'),
(52, 18, 'Shining', 66, 'Horreur psychologique maîtrisée', 2, '2025-03-24'),
(53, 18, 'Shining', 67, 'Plus angoissant que le film', 5, '2025-03-25'),
(54, 18, 'Shining', 68, 'Déception après le film', 3, '2025-03-26'),
(55, 19, 'Bel-Ami', 69, 'Satire sociale mordante', 1, '2025-03-27'),
(56, 19, 'Bel-Ami', 70, 'Personnage principal détestable', 4, '2025-03-28'),
(57, 19, 'Bel-Ami', 71, 'Un Maupassant incontournable', 2, '2025-03-29'),
(58, 20, 'Fahrenheit 451', 72, 'Vision prophétique', 5, '2025-03-30'),
(59, 20, 'Fahrenheit 451', 73, 'Surcoté selon moi', 3, '2025-03-31'),
(60, 20, 'Fahrenheit 451', 74, 'Un avertissement nécessaire', 1, '2025-04-01');

-- --------------------------------------------------------

--
-- Structure de la table `categorie`
--

CREATE TABLE `categorie` (
  `idCategorie` int NOT NULL,
  `nomCategorie` varchar(50) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

--
-- Déchargement des données de la table `categorie`
--

INSERT INTO `categorie` (`idCategorie`, `nomCategorie`) VALUES
(1, 'Roman'),
(2, 'Histoire'),
(3, 'Recueil de Poèmes'),
(4, 'Programmation');

-- --------------------------------------------------------

--
-- Structure de la table `commande`
--

CREATE TABLE `commande` (
  `idCommande` int NOT NULL,
  `dateCommande` date DEFAULT NULL,
  `statutCommande` enum('en attente','annulée','expédiée','arrivée') CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci DEFAULT NULL,
  `dateLivraisonCommande` date DEFAULT NULL,
  `idUser` int DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

--
-- Déchargement des données de la table `commande`
--

INSERT INTO `commande` (`idCommande`, `dateCommande`, `statutCommande`, `dateLivraisonCommande`, `idUser`) VALUES
(301, '2025-01-24', 'expédiée', '2025-01-31', 2),
(302, '2025-01-24', 'en attente', '2025-01-31', 2),
(303, '2025-01-24', 'en attente', '2025-01-31', 2),
(304, '2025-01-24', 'en attente', '2025-01-31', 2),
(305, '2025-01-24', 'en attente', '2025-01-31', 2),
(306, '2025-01-24', 'en attente', '2025-01-31', 2),
(307, '2025-01-24', 'en attente', '2025-01-31', 2),
(308, '2025-01-24', 'en attente', '2025-01-31', 2),
(309, '2025-01-24', 'en attente', '2025-01-31', 2),
(310, '2025-01-24', 'en attente', '2025-01-31', 2),
(311, '2025-01-24', 'en attente', '2025-01-31', 2),
(312, '2025-01-24', 'en attente', '2025-01-31', 2),
(313, '2025-01-24', 'en attente', '2025-01-31', 2),
(314, '2025-01-24', 'en attente', '2025-01-31', 2),
(315, '2025-01-24', 'en attente', '2025-01-31', 2),
(316, '2025-01-24', 'en attente', '2025-01-31', 2),
(317, '2025-01-24', 'en attente', '2025-01-31', 2),
(318, '2025-01-24', 'en attente', '2025-01-31', 2),
(319, '2025-01-24', 'en attente', '2025-01-31', 2),
(320, '2025-01-24', 'en attente', '2025-01-31', 2),
(321, '2025-01-24', 'en attente', '2025-01-31', 2),
(322, '2025-01-24', 'en attente', '2025-01-31', 2),
(323, '2025-01-24', 'en attente', '2025-01-31', 2),
(324, '2025-01-24', 'en attente', '2025-01-31', 2),
(325, '2025-01-24', 'en attente', '2025-01-31', 2),
(326, '2025-01-24', 'en attente', '2025-01-31', 2),
(327, '2025-01-24', 'en attente', '2025-01-31', 2),
(328, '2025-01-24', 'en attente', '2025-01-31', 2),
(329, '2025-01-24', 'en attente', '2025-01-31', 2),
(330, '2025-01-24', 'en attente', '2025-01-31', 2),
(331, '2025-01-24', 'en attente', '2025-01-31', 2),
(332, '2025-01-24', 'en attente', '2025-01-31', 2),
(333, '2025-01-24', 'en attente', NULL, 2),
(337, '2025-01-27', 'expédiée', '2025-02-03', 15),
(338, '2025-01-27', 'expédiée', '2025-02-03', 2),
(339, '2025-01-28', 'expédiée', '2025-02-04', 15),
(340, '2025-01-28', 'expédiée', '2025-02-04', 15),
(341, '2025-01-28', 'expédiée', '2025-02-04', 2),
(342, '2025-01-29', 'expédiée', '2025-02-05', 2),
(343, '2025-01-29', 'expédiée', '2025-02-05', 2),
(344, '2025-01-29', 'expédiée', '2025-02-05', 2),
(345, '2025-01-29', 'expédiée', '2025-02-05', 2),
(346, '2025-01-29', 'expédiée', '2025-02-05', 2),
(347, '2025-01-29', 'expédiée', '2025-02-05', 2),
(348, '2025-01-29', 'expédiée', '2025-02-05', 2),
(349, '2025-01-29', 'expédiée', '2025-02-05', 2),
(350, '2025-01-29', 'expédiée', '2025-02-05', 2),
(351, '2025-01-29', 'expédiée', '2025-02-05', 2),
(352, '2025-01-29', 'expédiée', '2025-02-05', 2),
(353, '2025-01-29', 'expédiée', '2025-02-05', 2),
(354, '2025-01-29', 'expédiée', '2025-02-05', 2),
(355, '2025-01-29', 'expédiée', '2025-02-05', 2),
(356, '2025-01-29', 'expédiée', '2025-02-05', 2),
(357, '2025-01-29', 'expédiée', '2025-02-05', 2),
(358, '2025-01-29', 'expédiée', '2025-02-05', 2),
(359, '2025-01-29', 'expédiée', '2025-02-05', 2),
(360, '2025-01-29', 'expédiée', '2025-02-05', 2),
(361, '2025-01-29', 'expédiée', '2025-02-05', 2),
(362, '2025-01-29', 'expédiée', '2025-02-05', 2),
(363, '2025-01-29', 'expédiée', '2025-02-05', 2),
(364, '2025-01-29', 'expédiée', '2025-02-05', 15),
(365, '2025-01-29', 'expédiée', '2025-02-05', 15),
(366, '2025-01-29', 'expédiée', '2025-02-05', 15),
(367, '2025-01-29', 'expédiée', '2025-02-05', 15),
(368, '2025-01-29', 'expédiée', '2025-02-05', 15),
(369, '2025-01-29', 'expédiée', '2025-02-05', 15),
(370, '2025-01-29', 'expédiée', '2025-02-05', 15),
(371, '2025-01-29', 'expédiée', '2025-02-05', 15),
(372, '2025-01-29', 'expédiée', '2025-02-05', 2),
(373, '2025-01-29', 'expédiée', '2025-02-05', 2),
(374, '2025-01-29', 'expédiée', '2025-02-05', 2),
(375, '2025-01-29', 'expédiée', '2025-02-05', 15),
(376, '2025-01-29', 'expédiée', '2025-02-05', 15),
(377, '2025-01-29', 'expédiée', '2025-02-05', 15),
(378, '2025-01-29', 'expédiée', '2025-02-05', 15),
(379, '2025-01-29', 'expédiée', '2025-02-05', 15),
(380, '2025-01-29', 'expédiée', '2025-02-05', 15),
(381, '2025-01-29', 'expédiée', '2025-02-05', 15),
(382, '2025-01-29', 'expédiée', '2025-02-05', 15),
(383, '2025-01-29', 'expédiée', '2025-02-05', 24),
(384, '2025-01-29', 'expédiée', '2025-02-05', 24),
(385, '2025-01-29', 'expédiée', '2025-02-05', 24),
(386, '2025-01-29', 'expédiée', '2025-02-05', 24),
(387, '2025-01-29', 'expédiée', '2025-02-05', 24),
(388, '2025-01-29', 'expédiée', '2025-02-05', 24),
(389, '2025-01-29', 'expédiée', '2025-02-05', 24),
(390, '2025-01-29', 'expédiée', '2025-02-05', 24),
(391, '2025-01-29', 'expédiée', '2025-02-05', 24),
(392, '2025-01-29', 'expédiée', '2025-02-05', 24),
(393, '2025-01-29', 'expédiée', '2025-02-05', 24),
(395, '2025-01-29', 'expédiée', '2025-02-05', 24),
(396, '2025-01-29', 'expédiée', '2025-02-05', 24),
(397, '2025-01-29', 'expédiée', '2025-02-05', 24),
(398, '2025-01-29', 'expédiée', '2025-02-05', 24),
(399, '2025-01-29', 'expédiée', '2025-02-05', 24),
(400, '2025-01-29', 'expédiée', '2025-02-05', 24),
(401, '2025-01-29', 'expédiée', '2025-02-05', 24),
(402, '2025-01-29', 'expédiée', '2025-02-05', 24),
(403, '2025-01-29', 'expédiée', '2025-02-05', 24),
(404, '2025-01-29', 'expédiée', '2025-02-05', 24),
(405, '2025-01-29', 'expédiée', '2025-02-05', 24),
(406, '2025-01-30', 'expédiée', '2025-02-06', 2),
(407, '2025-01-30', 'expédiée', '2025-02-06', 2),
(408, '2025-01-30', 'expédiée', '2025-02-06', 2),
(409, '2025-01-30', 'expédiée', '2025-02-06', 15),
(410, '2025-01-30', 'expédiée', '2025-02-06', 15),
(411, '2025-01-30', 'expédiée', '2025-02-06', 15),
(412, '2025-01-30', 'expédiée', '2025-02-06', 15),
(413, '2025-01-30', 'expédiée', '2025-02-06', 15),
(414, '2025-01-30', 'expédiée', '2025-02-06', 15),
(415, '2025-01-30', 'expédiée', '2025-02-06', 15),
(417, '2025-02-02', 'expédiée', '2025-02-09', 15),
(418, '2025-02-02', 'expédiée', '2025-02-09', 15),
(419, '2025-02-02', 'expédiée', '2025-02-09', 15),
(420, '2025-02-02', 'expédiée', '2025-02-09', 15),
(421, '2025-02-02', 'expédiée', '2025-02-09', 15),
(422, '2025-02-02', 'expédiée', '2025-02-09', 15),
(423, '2025-02-02', 'expédiée', '2025-02-09', 15),
(424, '2025-02-02', 'expédiée', '2025-02-09', 15),
(425, '2025-02-02', 'expédiée', '2025-02-09', 24),
(426, '2025-02-02', 'expédiée', '2025-02-09', 24),
(427, '2025-02-02', 'expédiée', '2025-02-09', 2),
(428, '2025-02-02', 'expédiée', '2025-02-09', 15),
(429, '2025-02-02', 'expédiée', '2025-02-09', 15),
(430, '2025-02-02', 'expédiée', '2025-02-09', 15),
(431, '2025-02-02', 'expédiée', '2025-02-09', 15),
(432, '2025-02-02', 'expédiée', '2025-02-09', 15),
(433, '2025-02-02', 'expédiée', '2025-02-09', 15),
(434, '2025-02-02', 'expédiée', '2025-02-09', 15),
(435, '2025-02-02', 'expédiée', '2025-02-09', 15),
(436, '2025-02-02', 'expédiée', '2025-02-09', 15),
(437, '2025-02-02', 'expédiée', '2025-02-09', 15),
(438, '2025-02-02', 'expédiée', '2025-02-09', 15),
(439, '2025-02-02', 'expédiée', '2025-02-09', 15),
(440, '2025-02-02', 'expédiée', '2025-02-09', 15),
(441, '2025-02-02', 'expédiée', '2025-02-09', 15),
(442, '2025-02-02', 'expédiée', '2025-02-09', 15),
(443, '2025-02-02', 'expédiée', '2025-02-09', 15),
(444, '2025-02-02', 'expédiée', '2025-02-09', 15),
(445, '2025-02-02', 'expédiée', '2025-02-09', 15),
(446, '2025-02-02', 'expédiée', '2025-02-09', 15),
(447, '2025-02-02', 'expédiée', '2025-02-09', 15),
(448, '2025-02-02', 'expédiée', '2025-02-09', 15),
(449, '2025-02-02', 'expédiée', '2025-02-09', 15),
(450, '2025-02-02', 'expédiée', '2025-02-09', 15),
(452, '2025-02-03', 'expédiée', '2025-02-10', 15),
(453, '2025-02-03', 'expédiée', '2025-02-10', 15),
(454, '2025-02-03', 'expédiée', '2025-02-10', 15),
(455, '2025-02-03', 'expédiée', '2025-02-10', 15),
(456, '2025-04-10', 'en attente', '2025-04-17', 3),
(459, '2000-12-12', 'en attente', '2000-12-20', 3);

--
-- Déclencheurs `commande`
--
DELIMITER $$
CREATE TRIGGER `tUpdateStockCommandeExpediee` AFTER UPDATE ON `commande` FOR EACH ROW begin
    if OLD.statutCommande = 'en attente' and NEW.statutCommande = 'expédiée' then
        update livre
        set exemplaireLivre = exemplaireLivre - (
            select sum(quantiteLigneCommande)
            from ligneCommande
            where idCommande = NEW.idCommande
            and ligneCommande.idLivre = livre.idLivre
        )
        where idLivre in (select idLivre from ligneCommande where idCommande = NEW.idCommande);
    end if;
end
$$
DELIMITER ;

-- --------------------------------------------------------

--
-- Structure de la table `entreprise`
--

CREATE TABLE `entreprise` (
  `idUser` int NOT NULL,
  `siretUser` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci DEFAULT NULL,
  `raisonSocialeUser` varchar(255) DEFAULT NULL,
  `capitalSocialUser` float(15,2) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

--
-- Déchargement des données de la table `entreprise`
--

INSERT INTO `entreprise` (`idUser`, `siretUser`, `raisonSocialeUser`, `capitalSocialUser`) VALUES
(26, '123456789', 'Entreprise SARL', 10000.00),
(27, '123456789', 'Entreprise SARL', 10000.00),
(30, '123123123', 'yasser', 123123120.00),
(32, '123', '123', 123124.00),
(33, '123123', '123123', 123123.00),
(34, '987987', '987987', 987987.00),
(52, 'zaopdkazd', 'pokdzad', 21312.31);

-- --------------------------------------------------------

--
-- Structure de la table `ligneCommande`
--

CREATE TABLE `ligneCommande` (
  `idLigneCommande` int NOT NULL,
  `idCommande` int NOT NULL,
  `idLivre` int NOT NULL,
  `quantiteLigneCommande` int NOT NULL DEFAULT '1'
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

--
-- Déchargement des données de la table `ligneCommande`
--

INSERT INTO `ligneCommande` (`idLigneCommande`, `idCommande`, `idLivre`, `quantiteLigneCommande`) VALUES
(521, 289, 1, 33),
(533, 290, 1, 3),
(534, 291, 1, 5),
(535, 292, 1, 4),
(536, 293, 1, 2),
(537, 294, 1, 2),
(538, 295, 1, 3),
(539, 296, 1, 3),
(540, 297, 1, 3),
(543, 298, 1, 3),
(544, 299, 1, 6),
(545, 300, 1, 6),
(546, 301, 1, 3),
(547, 302, 2, 3),
(548, 303, 3, 3),
(549, 304, 3, 3),
(550, 305, 3, 3),
(551, 306, 1, 3),
(552, 307, 2, 3),
(553, 308, 2, 3),
(554, 309, 1, 2),
(555, 310, 2, 2),
(556, 311, 3, 2),
(557, 312, 1, 3),
(558, 313, 1, 2),
(559, 314, 1, 2),
(560, 315, 1, 3),
(561, 316, 1, 3),
(562, 317, 1, 3),
(563, 318, 1, 3),
(564, 319, 1, 3),
(565, 320, 1, 3),
(566, 321, 1, 3),
(567, 322, 1, 3),
(568, 323, 1, 3),
(569, 324, 1, 3),
(570, 325, 1, 3),
(571, 330, 1, 3),
(572, 331, 1, 3),
(573, 332, 1, 3),
(574, 153, 1, 3),
(575, 153, 1, 3),
(576, 153, 1, 3),
(577, 153, 1, 3),
(578, 153, 1, 5),
(579, 159, 1, 5),
(607, 271, 1, 5),
(608, 271, 5, 5),
(609, 274, 8, 3),
(610, 219, 4, 1),
(615, 337, 2, 10),
(616, 338, 6, 1),
(617, 337, 3, 10),
(618, 339, 8, 1),
(619, 340, 6, 1),
(620, 364, 2, 2),
(621, 365, 6, 1),
(622, 366, 2, 2),
(623, 367, 6, 1),
(624, 368, 2, 1),
(625, 369, 6, 1),
(626, 370, 2, 1),
(627, 371, 2, 1),
(628, 372, 6, 1),
(629, 373, 6, 1),
(630, 374, 6, 1),
(631, 375, 6, 1),
(632, 376, 2, 1),
(633, 377, 6, 1),
(634, 378, 2, 1),
(635, 379, 6, 1),
(636, 380, 2, 1),
(637, 381, 2, 10),
(638, 382, 2, 10),
(639, 383, 2, 10),
(640, 384, 2, 10),
(641, 385, 2, 5),
(642, 386, 6, 1),
(643, 387, 3, 10),
(644, 388, 3, 10),
(645, 389, 4, 10),
(646, 390, 2, 4),
(647, 391, 6, 1),
(648, 392, 2, 10),
(649, 393, 6, 1),
(651, 395, 2, 1),
(652, 396, 2, 1),
(653, 397, 2, 1),
(654, 398, 2, 1),
(655, 399, 6, 1),
(656, 400, 2, 1),
(657, 401, 2, 1),
(658, 402, 2, 1),
(659, 403, 6, 1),
(660, 404, 2, 1),
(661, 405, 6, 1),
(662, 406, 6, 1),
(663, 407, 6, 1),
(664, 408, 6, 1),
(665, 409, 3, 1),
(666, 409, 9, 1),
(667, 410, 6, 1),
(668, 411, 2, 1),
(669, 412, 3, 1),
(670, 413, 3, 1),
(671, 414, 3, 1),
(672, 415, 9, 1),
(674, 417, 2, 1),
(675, 418, 12, 1),
(676, 419, 2, 1),
(677, 420, 12, 1),
(678, 421, 2, 1),
(679, 422, 9, 1),
(680, 423, 3, 1),
(681, 424, 12, 1),
(682, 425, 2, 1),
(683, 426, 3, 1),
(684, 427, 10, 1),
(685, 428, 2, 1),
(686, 429, 11, 1),
(687, 430, 2, 1),
(688, 431, 2, 1),
(689, 432, 11, 1),
(690, 433, 2, 1),
(691, 434, 11, 1),
(692, 435, 2, 1),
(693, 436, 12, 1),
(694, 437, 2, 1),
(695, 438, 12, 1),
(696, 439, 2, 1),
(697, 440, 11, 1),
(698, 441, 2, 1),
(699, 442, 12, 1),
(700, 443, 2, 1),
(701, 444, 12, 1),
(702, 445, 2, 1),
(703, 446, 12, 1),
(704, 447, 2, 1),
(705, 448, 9, 1),
(706, 449, 2, 1),
(707, 450, 11, 1),
(712, 452, 3, 1),
(713, 452, 2, 2),
(714, 452, 14, 5),
(715, 453, 2, 5),
(716, 454, 2, 1),
(717, 454, 3, 1),
(718, 454, 13, 1),
(720, 455, 2, 1),
(721, 0, 1, 20),
(722, 459, 1, 20);

--
-- Déclencheurs `ligneCommande`
--
DELIMITER $$
CREATE TRIGGER `tExemplaireLivreLigneCommande` BEFORE INSERT ON `ligneCommande` FOR EACH ROW begin
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
$$
DELIMITER ;
DELIMITER $$
CREATE TRIGGER `tStockNull` BEFORE INSERT ON `ligneCommande` FOR EACH ROW begin
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
$$
DELIMITER ;

-- --------------------------------------------------------

--
-- Structure de la table `livre`
--

CREATE TABLE `livre` (
  `idLivre` int NOT NULL,
  `nomLivre` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL,
  `auteurLivre` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci DEFAULT NULL,
  `imageLivre` varchar(50) NOT NULL,
  `exemplaireLivre` int DEFAULT NULL,
  `prixLivre` float(10,2) NOT NULL,
  `idCategorie` int DEFAULT NULL,
  `idMaisonEdition` int DEFAULT NULL,
  `idPromotion` int DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

--
-- Déchargement des données de la table `livre`
--

INSERT INTO `livre` (`idLivre`, `nomLivre`, `auteurLivre`, `imageLivre`, `exemplaireLivre`, `prixLivre`, `idCategorie`, `idMaisonEdition`, `idPromotion`) VALUES
(1, 'Alcools', 'Apollinaire', 'alcools.png', 96, 12.50, 3, 1, NULL),
(2, 'Crime et Chatiment', 'Dostoïevski', 'crime_et_chatiment.png', 91, 15.00, 1, 2, NULL),
(3, 'L`Etranger', 'Camus', 'l_etranger.png', 56, 10.00, 1, 3, NULL),
(4, 'L`Odyssée', 'Homère', 'l_odyssee.png', 89, 13.50, 2, 4, NULL),
(5, 'Les Fleurs du Mal', 'Baudelaire', 'les_fleurs_du_mal.png', 100, 14.00, 3, 5, NULL),
(6, 'PHP et MySQL pour les nuls', 'Valade', 'php_et_mysql_pour_les_nuls.png', 79, 22.00, 4, 6, NULL),
(7, 'Programmer en Java', 'Delannoy', 'programmer_en_java.png', 100, 25.00, 4, 7, NULL),
(8, 'SPQR', 'Beard', 'spqr.png', 99, 18.00, 2, 8, NULL),
(9, 'À la recherche du temps perdu', 'Proust', 'a_la_recherche_du_temps_perdu.png', 96, 0.00, 1, 1, NULL),
(10, 'Les Misérables', 'Hugo', 'les_miserables_I.png', 98, 0.00, 1, 2, 10),
(11, '1984', 'Orwell', '1984.png', 95, 0.00, 1, 3, NULL),
(12, 'L`Art d\'aimer', 'Ovide', 'l_art_d_aimer', 92, 0.00, 1, 4, NULL),
(13, 'La Peste', 'Camus', 'la_peste.png', 98, 15.99, 1, 1, NULL),
(14, 'Les Mémoires d\'Hadrien', 'Yourcenar', 'les_memoires_d_hadrien.png', 95, 12.99, 1, 1, NULL),
(15, 'La Condition humaine', 'Malraux', 'la_condition_humaine.png', 100, 14.99, 1, 1, NULL),
(16, 'Le Comte de Monte-Cristo', 'Dumas', 'le_comte_de_monte_cristo.png', 100, 9.99, 1, 2, NULL),
(17, 'Orgueil et Préjugés', 'Austen', 'orgueil_et_prejuges.png', 100, 8.99, 1, 2, NULL),
(18, 'Shining', 'King', 'shining.png', 100, 10.99, 1, 2, NULL),
(19, 'Bel-Ami', 'Maupassant', 'bel_ami.png', 100, 11.99, 1, 3, NULL),
(20, 'Fahrenheit 451', 'Bradbury', 'fahrenheit_451.png', 100, 9.99, 1, 3, NULL),
(21, 'La Nuit des temps', 'Barjavel', 'la_nuit_des_temps.png', 100, 12.99, 1, 3, NULL),
(22, 'L`Énéide', 'Virgile', 'l_eneide.png', 100, 19.99, 3, 4, NULL),
(23, 'Les Pensées', 'Aurèle', 'les_pensees.png', 100, 18.99, 3, 4, NULL),
(24, 'Les Métamorphoses', 'Ovide', 'les_metamorphoses.png', 100, 20.99, 3, 4, NULL),
(25, 'Le Petit Livre des citations latines', 'Delamaire', 'le_petit_livre_des_citations_latines.png', 100, 7.99, 3, 6, NULL),
(43, 'Le Petit Livre des grandes coïncidences', 'Chiflet', 'le_petit_livre_des_grandes_coincidences.png', 100, 7.99, 3, 6, NULL),
(44, 'Le Petit Livre des gros mensonges', 'Chiflet', 'le_petit_livre_des_gros_mensonges.png', 100, 7.99, 3, 6, NULL),
(45, 'L`Art de la guerre', 'Sun', 'l_art_de_la_guerre.png', 100, 12.99, 2, 7, NULL),
(46, 'Apprendre à dessiner', 'Edwards', 'apprendre_a_dessiner.png', 100, 14.99, 4, 7, NULL),
(47, 'Le Lean Startup', 'Ries', 'le_lean_startup.png', 100, 16.99, 4, 7, NULL),
(48, 'Les Templiers', 'Demurger', 'les_templiers.png', 100, 18.99, 2, 8, NULL),
(49, 'La Seconde Guerre mondiale', 'Beevor', 'la_seconde_guerre_mondiale.png', 100, 19.99, 2, 8, NULL),
(50, 'Napoléon : Une ambition française', 'Tulard', 'napoleon_une_ambition_francaise.png', 100, 20.99, 2, 8, NULL),
(51, 'dimanche', 'dimanche', 'dimanche.png', 180, 7.20, 3, 3, NULL),
(52, 'cate', 'cate', 'cate.png', 12, 12.00, 3, 3, NULL),
(53, 'po', 'po', 'po', 1, 1.00, 1, 3, 1),
(55, 'ok', 'no', 'no', 9, 9.00, 2, 3, 4);

-- --------------------------------------------------------

--
-- Structure de la table `maisonEdition`
--

CREATE TABLE `maisonEdition` (
  `idMaisonEdition` int NOT NULL,
  `nomMaisonEdition` varchar(50) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

--
-- Déchargement des données de la table `maisonEdition`
--

INSERT INTO `maisonEdition` (`idMaisonEdition`, `nomMaisonEdition`) VALUES
(1, 'Gallimard'),
(2, 'Livre de Poche'),
(3, 'Folio'),
(4, 'Les Belles Lettres'),
(5, 'Le Livre de Poche'),
(6, 'First Interactive'),
(7, 'Eyrolles'),
(8, 'Perrin');

-- --------------------------------------------------------

--
-- Structure de la table `particulier`
--

CREATE TABLE `particulier` (
  `idUser` int NOT NULL,
  `nomUser` varchar(255) DEFAULT NULL,
  `prenomUser` varchar(255) DEFAULT NULL,
  `dateNaissanceUser` date DEFAULT NULL,
  `sexeUser` enum('M','F') DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

--
-- Déchargement des données de la table `particulier`
--

INSERT INTO `particulier` (`idUser`, `nomUser`, `prenomUser`, `dateNaissanceUser`, `sexeUser`) VALUES
(28, 'Arch', 'Michael', '2001-12-12', 'M'),
(29, 'ryles', 'ryles', '2022-12-12', 'M'),
(35, 'yasser', 'yasser', '2010-12-21', 'M'),
(36, 'yasser', 'yasser', '2010-12-21', 'M'),
(37, 'part', 'part', '2009-12-12', 'M'),
(38, 'uy', 'uy', '2003-03-12', 'M'),
(41, 'final', 'final', '2025-12-12', 'F'),
(50, 'mlm', 'lmlml', '2999-12-12', 'M');

-- --------------------------------------------------------

--
-- Structure de la table `promotion`
--

CREATE TABLE `promotion` (
  `idPromotion` int NOT NULL,
  `nomPromotion` varchar(50) NOT NULL,
  `dateDebutPromotion` date NOT NULL,
  `dateFinPromotion` date NOT NULL,
  `reductionPromotion` int NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

--
-- Déchargement des données de la table `promotion`
--

INSERT INTO `promotion` (`idPromotion`, `nomPromotion`, `dateDebutPromotion`, `dateFinPromotion`, `reductionPromotion`) VALUES
(1, '10%', '2025-01-05', '2025-01-20', 10),
(2, '20%', '2025-02-01', '2025-02-15', 20),
(3, '30%', '2025-03-01', '2025-03-10', 30),
(4, '40%', '2025-03-26', '2026-12-12', 40),
(5, '50%', '2025-03-26', '2026-12-12', 50),
(6, '60%', '2025-02-02', '2025-02-10', 60),
(7, '70%', '2025-03-26', '2026-12-12', 70),
(8, '80%', '2025-03-26', '2026-12-12', 80),
(9, '90%', '2025-04-11', '2025-12-31', 90),
(10, 'Aucune Promotion', '2025-04-11', '2025-12-31', 0);

-- --------------------------------------------------------

--
-- Structure de la table `user`
--

CREATE TABLE `user` (
  `idUser` int NOT NULL,
  `emailUser` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci DEFAULT NULL,
  `mdpUser` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci DEFAULT NULL,
  `adresseUser` varchar(50) NOT NULL,
  `roleUser` enum('admin','client','gestionnaire') CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

--
-- Déchargement des données de la table `user`
--

INSERT INTO `user` (`idUser`, `emailUser`, `mdpUser`, `adresseUser`, `roleUser`) VALUES
(1, 'ryles@gmail.com', '123', '123 Rue des Lilas', 'admin'),
(2, 'jean@gmail.com', '123', '45 Avenue de la République', 'client'),
(12, 'm', '123', '78 Boulevard Haussmann', 'client'),
(13, 'klza', '123', '9 Place de la Liberté', 'client'),
(14, 'poi', '123', '56 Rue Victor Hugo', 'client'),
(15, 'i', '123', '34 Avenue Montaigne', 'client'),
(23, 'chouaki@gmail.com', '123', '101 Rue Lafayette', 'client'),
(24, 'bo@gmail.com', '123', '202 Avenue des Champs-Élysées', 'client'),
(26, 'entreprise@gmail.com', '123', '404 Rue de Rivoli', 'client'),
(27, 'entreprise@gmail.com', '123', '505 Quai de la Tournelle', 'client'),
(28, 'michael@gmail.com', '123', '89 Impasse des Cerisiers', 'client'),
(29, 'ryles', '123', '102 Rue du Moulin', 'client'),
(30, 'yasser@gmail.com', '123', '76 Avenue des Rosiers', 'client'),
(32, 'test@gmail.com', '123', '21 Boulevard de la Liberté', 'client'),
(33, 'yass@gmail.com', '123', '34 Rue des Érables', 'client'),
(34, '987987@gmail.com', '123', '47 Allée des Chênes', 'client'),
(35, 'yasser@gmail.com', '123', '15 Route du Soleil', 'client'),
(36, 'yasser@gmail.com', '123', '63 Place du Marché', 'client'),
(37, 'part@gmail.com', '123', 'part', 'client'),
(38, 'uy@gmail.com', '123', 'uy', 'client'),
(39, 'gest@gmail.com', '123', '16 rue des Lilas', 'gestionnaire'),
(40, 'test', '123', 'test', 'client'),
(41, 'final@gmail.com', '123', 'final', 'gestionnaire'),
(50, 'mlml', 'd43affcc277ee52980fc4ecea523730f28d6405b', 'mlml', 'client'),
(52, 'azdazpdok', 'd43affcc277ee52980fc4ecea523730f28d6405b', 'pokazd', 'client');

-- --------------------------------------------------------

--
-- Doublure de structure pour la vue `vcommandesenattente`
-- (Voir ci-dessous la vue réelle)
--
CREATE TABLE `vcommandesenattente` (
`nbCommandeEnAttente` bigint
);

-- --------------------------------------------------------

--
-- Doublure de structure pour la vue `vlivresenstock`
-- (Voir ci-dessous la vue réelle)
--
CREATE TABLE `vlivresenstock` (
`exemplaireLivre` int
,`idLivre` int
,`nomLivre` varchar(50)
,`prixLivre` float(10,2)
);

-- --------------------------------------------------------

--
-- Doublure de structure pour la vue `vlivresmieuxnotes`
-- (Voir ci-dessous la vue réelle)
--
CREATE TABLE `vlivresmieuxnotes` (
`idLivre` int
,`nomLivre` varchar(50)
,`noteMoyenne` decimal(6,2)
);

-- --------------------------------------------------------

--
-- Doublure de structure pour la vue `vmeilleuresventes`
-- (Voir ci-dessous la vue réelle)
--
CREATE TABLE `vmeilleuresventes` (
`idLivre` int
,`nomLivre` varchar(50)
,`totalVendu` decimal(32,0)
);

-- --------------------------------------------------------

--
-- Doublure de structure pour la vue `vmeilleursavis`
-- (Voir ci-dessous la vue réelle)
--
CREATE TABLE `vmeilleursavis` (
`idLivre` int
,`moyenneNote` decimal(7,4)
,`nomLivre` varchar(50)
);

-- --------------------------------------------------------

--
-- Doublure de structure pour la vue `vnblivreacheteuser`
-- (Voir ci-dessous la vue réelle)
--
CREATE TABLE `vnblivreacheteuser` (
`emailUser` varchar(50)
,`nbLivreAchete` decimal(32,0)
);

-- --------------------------------------------------------

--
-- Doublure de structure pour la vue `vtotalcommandeenattente`
-- (Voir ci-dessous la vue réelle)
--
CREATE TABLE `vtotalcommandeenattente` (
`idUser` int
,`totalCommande` double(19,2)
);

-- --------------------------------------------------------

--
-- Doublure de structure pour la vue `vtotalcommandeexpediee`
-- (Voir ci-dessous la vue réelle)
--
CREATE TABLE `vtotalcommandeexpediee` (
`idUser` int
,`totalCommande` double(19,2)
);

-- --------------------------------------------------------

--
-- Doublure de structure pour la vue `vtotallivre`
-- (Voir ci-dessous la vue réelle)
--
CREATE TABLE `vtotallivre` (
`idCommande` int
,`idUser` int
,`nomLivre` varchar(50)
,`prixLivre` float(10,2)
,`quantiteLigneCommande` int
,`totalLivre` double(22,2)
);

-- --------------------------------------------------------

--
-- Doublure de structure pour la vue `vtotallivreenattente`
-- (Voir ci-dessous la vue réelle)
--
CREATE TABLE `vtotallivreenattente` (
`idCommande` int
,`idLigneCommande` int
,`idLivre` int
,`idUser` int
,`nomLivre` varchar(50)
,`prixLivre` float(10,2)
,`quantiteLigneCommande` int
,`totalLivre` double(22,2)
);

-- --------------------------------------------------------

--
-- Doublure de structure pour la vue `vtotallivreexpediee`
-- (Voir ci-dessous la vue réelle)
--
CREATE TABLE `vtotallivreexpediee` (
`idCommande` int
,`idLivre` int
,`idUser` int
,`nomLivre` varchar(50)
,`prixLivre` float(10,2)
,`quantiteLigneCommande` int
,`totalLivre` double(22,2)
);

-- --------------------------------------------------------

--
-- Structure de la vue `vcommandesenattente`
--
DROP TABLE IF EXISTS `vcommandesenattente`;

CREATE ALGORITHM=UNDEFINED DEFINER=`root`@`localhost` SQL SECURITY DEFINER VIEW `vcommandesenattente`  AS SELECT count(`c`.`idCommande`) AS `nbCommandeEnAttente` FROM `commande` AS `c` WHERE (`c`.`statutCommande` = 'en attente') ;

-- --------------------------------------------------------

--
-- Structure de la vue `vlivresenstock`
--
DROP TABLE IF EXISTS `vlivresenstock`;

CREATE ALGORITHM=UNDEFINED DEFINER=`root`@`localhost` SQL SECURITY DEFINER VIEW `vlivresenstock`  AS SELECT `l`.`idLivre` AS `idLivre`, `l`.`nomLivre` AS `nomLivre`, `l`.`prixLivre` AS `prixLivre`, `l`.`exemplaireLivre` AS `exemplaireLivre` FROM `livre` AS `l` WHERE (`l`.`exemplaireLivre` <= 5) ;

-- --------------------------------------------------------

--
-- Structure de la vue `vlivresmieuxnotes`
--
DROP TABLE IF EXISTS `vlivresmieuxnotes`;

CREATE ALGORITHM=UNDEFINED DEFINER=`root`@`localhost` SQL SECURITY DEFINER VIEW `vlivresmieuxnotes`  AS SELECT `avis`.`idLivre` AS `idLivre`, max(`avis`.`nomLivre`) AS `nomLivre`, round(avg(`avis`.`noteAvis`),2) AS `noteMoyenne` FROM `avis` GROUP BY `avis`.`idLivre` ORDER BY `noteMoyenne` DESC ;

-- --------------------------------------------------------

--
-- Structure de la vue `vmeilleuresventes`
--
DROP TABLE IF EXISTS `vmeilleuresventes`;

CREATE ALGORITHM=UNDEFINED DEFINER=`root`@`localhost` SQL SECURITY DEFINER VIEW `vmeilleuresventes`  AS SELECT `l`.`idLivre` AS `idLivre`, `l`.`nomLivre` AS `nomLivre`, sum(`li`.`quantiteLigneCommande`) AS `totalVendu` FROM (`lignecommande` `li` join `livre` `l` on((`li`.`idLivre` = `l`.`idLivre`))) GROUP BY `l`.`idLivre`, `l`.`nomLivre` ORDER BY `totalVendu` DESC ;

-- --------------------------------------------------------

--
-- Structure de la vue `vmeilleursavis`
--
DROP TABLE IF EXISTS `vmeilleursavis`;

CREATE ALGORITHM=UNDEFINED DEFINER=`root`@`localhost` SQL SECURITY DEFINER VIEW `vmeilleursavis`  AS SELECT `l`.`idLivre` AS `idLivre`, `l`.`nomLivre` AS `nomLivre`, avg(`a`.`noteAvis`) AS `moyenneNote` FROM (`avis` `a` join `livre` `l` on((`a`.`idLivre` = `l`.`idLivre`))) GROUP BY `l`.`idLivre`, `l`.`nomLivre` ORDER BY `moyenneNote` DESC ;

-- --------------------------------------------------------

--
-- Structure de la vue `vnblivreacheteuser`
--
DROP TABLE IF EXISTS `vnblivreacheteuser`;

CREATE ALGORITHM=UNDEFINED DEFINER=`root`@`localhost` SQL SECURITY DEFINER VIEW `vnblivreacheteuser`  AS SELECT `u`.`emailUser` AS `emailUser`, sum(`l`.`quantiteLigneCommande`) AS `nbLivreAchete` FROM ((`lignecommande` `l` join `commande` `c` on((`l`.`idCommande` = `c`.`idCommande`))) join `user` `u` on((`c`.`idUser` = `u`.`idUser`))) WHERE (`c`.`statutCommande` = 'expédiée') GROUP BY `u`.`emailUser` ;

-- --------------------------------------------------------

--
-- Structure de la vue `vtotalcommandeenattente`
--
DROP TABLE IF EXISTS `vtotalcommandeenattente`;

CREATE ALGORITHM=UNDEFINED DEFINER=`root`@`localhost` SQL SECURITY DEFINER VIEW `vtotalcommandeenattente`  AS SELECT `c`.`idUser` AS `idUser`, sum((`l`.`prixLivre` * `li`.`quantiteLigneCommande`)) AS `totalCommande` FROM ((`commande` `c` join `lignecommande` `li` on((`c`.`idCommande` = `li`.`idCommande`))) join `livre` `l` on((`li`.`idLivre` = `l`.`idLivre`))) WHERE (`c`.`statutCommande` = 'en attente') GROUP BY `c`.`idUser` ;

-- --------------------------------------------------------

--
-- Structure de la vue `vtotalcommandeexpediee`
--
DROP TABLE IF EXISTS `vtotalcommandeexpediee`;

CREATE ALGORITHM=UNDEFINED DEFINER=`root`@`localhost` SQL SECURITY DEFINER VIEW `vtotalcommandeexpediee`  AS SELECT `c`.`idUser` AS `idUser`, sum((`l`.`prixLivre` * `li`.`quantiteLigneCommande`)) AS `totalCommande` FROM ((`commande` `c` join `lignecommande` `li` on((`c`.`idCommande` = `li`.`idCommande`))) join `livre` `l` on((`li`.`idLivre` = `l`.`idLivre`))) WHERE (`c`.`statutCommande` = 'expédiée') GROUP BY `c`.`idUser` ;

-- --------------------------------------------------------

--
-- Structure de la vue `vtotallivre`
--
DROP TABLE IF EXISTS `vtotallivre`;

CREATE ALGORITHM=UNDEFINED DEFINER=`root`@`localhost` SQL SECURITY DEFINER VIEW `vtotallivre`  AS SELECT `li`.`idCommande` AS `idCommande`, `c`.`idUser` AS `idUser`, `l`.`nomLivre` AS `nomLivre`, `l`.`prixLivre` AS `prixLivre`, `li`.`quantiteLigneCommande` AS `quantiteLigneCommande`, (`l`.`prixLivre` * `li`.`quantiteLigneCommande`) AS `totalLivre` FROM ((`livre` `l` join `lignecommande` `li` on((`l`.`idLivre` = `li`.`idLivre`))) join `commande` `c` on((`c`.`idCommande` = `li`.`idCommande`))) ;

-- --------------------------------------------------------

--
-- Structure de la vue `vtotallivreenattente`
--
DROP TABLE IF EXISTS `vtotallivreenattente`;

CREATE ALGORITHM=UNDEFINED DEFINER=`root`@`localhost` SQL SECURITY DEFINER VIEW `vtotallivreenattente`  AS SELECT `li`.`idLivre` AS `idLivre`, `li`.`idCommande` AS `idCommande`, `li`.`idLigneCommande` AS `idLigneCommande`, `c`.`idUser` AS `idUser`, `l`.`nomLivre` AS `nomLivre`, `l`.`prixLivre` AS `prixLivre`, `li`.`quantiteLigneCommande` AS `quantiteLigneCommande`, (`l`.`prixLivre` * `li`.`quantiteLigneCommande`) AS `totalLivre` FROM ((`livre` `l` join `lignecommande` `li` on((`l`.`idLivre` = `li`.`idLivre`))) join `commande` `c` on((`c`.`idCommande` = `li`.`idCommande`))) WHERE (`c`.`statutCommande` = 'en attente') ;

-- --------------------------------------------------------

--
-- Structure de la vue `vtotallivreexpediee`
--
DROP TABLE IF EXISTS `vtotallivreexpediee`;

CREATE ALGORITHM=UNDEFINED DEFINER=`root`@`localhost` SQL SECURITY DEFINER VIEW `vtotallivreexpediee`  AS SELECT `li`.`idCommande` AS `idCommande`, `c`.`idUser` AS `idUser`, `l`.`idLivre` AS `idLivre`, `l`.`nomLivre` AS `nomLivre`, `l`.`prixLivre` AS `prixLivre`, `li`.`quantiteLigneCommande` AS `quantiteLigneCommande`, (`l`.`prixLivre` * `li`.`quantiteLigneCommande`) AS `totalLivre` FROM ((`livre` `l` join `lignecommande` `li` on((`l`.`idLivre` = `li`.`idLivre`))) join `commande` `c` on((`c`.`idCommande` = `li`.`idCommande`))) WHERE (`c`.`statutCommande` = 'expédiée') ;

--
-- Index pour les tables déchargées
--

--
-- Index pour la table `abonnement`
--
ALTER TABLE `abonnement`
  ADD PRIMARY KEY (`idAbonnement`),
  ADD KEY `fk_user` (`idUser`);

--
-- Index pour la table `admin`
--
ALTER TABLE `admin`
  ADD PRIMARY KEY (`idAdmin`),
  ADD KEY `idUser` (`idUser`);

--
-- Index pour la table `avis`
--
ALTER TABLE `avis`
  ADD PRIMARY KEY (`idAvis`),
  ADD KEY `idUser` (`idUser`),
  ADD KEY `avis_ibfk_1` (`idLivre`);

--
-- Index pour la table `categorie`
--
ALTER TABLE `categorie`
  ADD PRIMARY KEY (`idCategorie`);

--
-- Index pour la table `commande`
--
ALTER TABLE `commande`
  ADD PRIMARY KEY (`idCommande`),
  ADD KEY `idUser` (`idUser`);

--
-- Index pour la table `entreprise`
--
ALTER TABLE `entreprise`
  ADD PRIMARY KEY (`idUser`);

--
-- Index pour la table `ligneCommande`
--
ALTER TABLE `ligneCommande`
  ADD PRIMARY KEY (`idLigneCommande`),
  ADD KEY `idCommande` (`idCommande`),
  ADD KEY `idLivre` (`idLivre`);

--
-- Index pour la table `livre`
--
ALTER TABLE `livre`
  ADD PRIMARY KEY (`idLivre`),
  ADD KEY `fk_c` (`idCategorie`),
  ADD KEY `fk_m` (`idMaisonEdition`),
  ADD KEY `fk_livre_promotion` (`idPromotion`);

--
-- Index pour la table `maisonEdition`
--
ALTER TABLE `maisonEdition`
  ADD PRIMARY KEY (`idMaisonEdition`);

--
-- Index pour la table `particulier`
--
ALTER TABLE `particulier`
  ADD PRIMARY KEY (`idUser`);

--
-- Index pour la table `promotion`
--
ALTER TABLE `promotion`
  ADD PRIMARY KEY (`idPromotion`);

--
-- Index pour la table `user`
--
ALTER TABLE `user`
  ADD PRIMARY KEY (`idUser`);

--
-- AUTO_INCREMENT pour les tables déchargées
--

--
-- AUTO_INCREMENT pour la table `abonnement`
--
ALTER TABLE `abonnement`
  MODIFY `idAbonnement` int NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=7;

--
-- AUTO_INCREMENT pour la table `commande`
--
ALTER TABLE `commande`
  MODIFY `idCommande` int NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=460;

--
-- AUTO_INCREMENT pour la table `ligneCommande`
--
ALTER TABLE `ligneCommande`
  MODIFY `idLigneCommande` int NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=723;

--
-- AUTO_INCREMENT pour la table `livre`
--
ALTER TABLE `livre`
  MODIFY `idLivre` int NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=57;

--
-- AUTO_INCREMENT pour la table `particulier`
--
ALTER TABLE `particulier`
  MODIFY `idUser` int NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=52;

--
-- AUTO_INCREMENT pour la table `user`
--
ALTER TABLE `user`
  MODIFY `idUser` int NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=53;

--
-- Contraintes pour les tables déchargées
--

--
-- Contraintes pour la table `livre`
--
ALTER TABLE `livre`
  ADD CONSTRAINT `fk_c` FOREIGN KEY (`idCategorie`) REFERENCES `categorie` (`idCategorie`),
  ADD CONSTRAINT `fk_livre_promotion` FOREIGN KEY (`idPromotion`) REFERENCES `promotion` (`idPromotion`),
  ADD CONSTRAINT `fk_m` FOREIGN KEY (`idMaisonEdition`) REFERENCES `maisonEdition` (`idMaisonEdition`);
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
