-- phpMyAdmin SQL Dump
-- version 5.1.0
-- https://www.phpmyadmin.net/
--
-- Host: 127.0.0.1
-- Creato il: Giu 12, 2021 alle 17:02
-- Versione del server: 10.4.18-MariaDB
-- Versione PHP: 8.0.3

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Database: `ecommerce`
--

-- --------------------------------------------------------

--
-- Struttura della tabella `cliente`
--

CREATE TABLE `cliente` (
  `id` bigint(20) NOT NULL,
  `user_extra_id` bigint(20) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Dump dei dati per la tabella `cliente`
--

INSERT INTO `cliente` (`id`, `user_extra_id`) VALUES
(1, 1),
(2, 4),
(3, 9),
(5, 11);

-- --------------------------------------------------------

--
-- Struttura della tabella `databasechangelog`
--

CREATE TABLE `databasechangelog` (
  `ID` varchar(255) NOT NULL,
  `AUTHOR` varchar(255) NOT NULL,
  `FILENAME` varchar(255) NOT NULL,
  `DATEEXECUTED` datetime NOT NULL,
  `ORDEREXECUTED` int(11) NOT NULL,
  `EXECTYPE` varchar(10) NOT NULL,
  `MD5SUM` varchar(35) DEFAULT NULL,
  `DESCRIPTION` varchar(255) DEFAULT NULL,
  `COMMENTS` varchar(255) DEFAULT NULL,
  `TAG` varchar(255) DEFAULT NULL,
  `LIQUIBASE` varchar(20) DEFAULT NULL,
  `CONTEXTS` varchar(255) DEFAULT NULL,
  `LABELS` varchar(255) DEFAULT NULL,
  `DEPLOYMENT_ID` varchar(10) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Dump dei dati per la tabella `databasechangelog`
--

INSERT INTO `databasechangelog` (`ID`, `AUTHOR`, `FILENAME`, `DATEEXECUTED`, `ORDEREXECUTED`, `EXECTYPE`, `MD5SUM`, `DESCRIPTION`, `COMMENTS`, `TAG`, `LIQUIBASE`, `CONTEXTS`, `LABELS`, `DEPLOYMENT_ID`) VALUES
('00000000000001', 'jhipster', 'config/liquibase/changelog/00000000000000_initial_schema.xml', '2021-05-10 17:33:57', 1, 'EXECUTED', '8:74594b464969f7d7128e994fd6b9d518', 'createTable tableName=jhi_user; createTable tableName=jhi_authority; createTable tableName=jhi_user_authority; addPrimaryKey tableName=jhi_user_authority; addForeignKeyConstraint baseTableName=jhi_user_authority, constraintName=fk_authority_name, ...', '', NULL, '4.3.2', NULL, NULL, '0660836735'),
('20210510152732-1', 'jhipster', 'config/liquibase/changelog/20210510152732_added_entity_UserExtra.xml', '2021-05-10 17:33:57', 2, 'EXECUTED', '8:ac45c3ef5d31b4df27e1698963b2c041', 'createTable tableName=user_extra', '', NULL, '4.3.2', NULL, NULL, '0660836735'),
('20210510152732-1-data', 'jhipster', 'config/liquibase/changelog/20210510152732_added_entity_UserExtra.xml', '2021-05-10 17:33:57', 3, 'EXECUTED', '8:223865b72b8be76ddf7653f3bb452255', 'loadData tableName=user_extra', '', NULL, '4.3.2', 'faker', NULL, '0660836735'),
('20210510152733-1', 'jhipster', 'config/liquibase/changelog/20210510152733_added_entity_Cliente.xml', '2021-05-10 17:33:57', 4, 'EXECUTED', '8:cea065c6a64a03383f055f8eb1301d2d', 'createTable tableName=cliente', '', NULL, '4.3.2', NULL, NULL, '0660836735'),
('20210510152733-1-data', 'jhipster', 'config/liquibase/changelog/20210510152733_added_entity_Cliente.xml', '2021-05-10 17:33:57', 5, 'EXECUTED', '8:4df2cf2d93cad16182a805d13c24dc06', 'loadData tableName=cliente', '', NULL, '4.3.2', 'faker', NULL, '0660836735'),
('20210510152734-1', 'jhipster', 'config/liquibase/changelog/20210510152734_added_entity_Venditore.xml', '2021-05-10 17:33:57', 6, 'EXECUTED', '8:4cda042c77d2802d300fa117ee897307', 'createTable tableName=venditore', '', NULL, '4.3.2', NULL, NULL, '0660836735'),
('20210510152734-1-data', 'jhipster', 'config/liquibase/changelog/20210510152734_added_entity_Venditore.xml', '2021-05-10 17:33:57', 7, 'EXECUTED', '8:2e6b57427c95acae5c5819589fc8e126', 'loadData tableName=venditore', '', NULL, '4.3.2', 'faker', NULL, '0660836735'),
('20210510152735-1', 'jhipster', 'config/liquibase/changelog/20210510152735_added_entity_Prodotto.xml', '2021-05-10 17:33:57', 8, 'EXECUTED', '8:b52120308cda20f596601b0e01fc0fc3', 'createTable tableName=prodotto', '', NULL, '4.3.2', NULL, NULL, '0660836735'),
('20210510152735-1-data', 'jhipster', 'config/liquibase/changelog/20210510152735_added_entity_Prodotto.xml', '2021-05-10 17:33:57', 9, 'EXECUTED', '8:409387c7e240f1f76eb90c42c32bba33', 'loadData tableName=prodotto', '', NULL, '4.3.2', 'faker', NULL, '0660836735'),
('20210510152736-1', 'jhipster', 'config/liquibase/changelog/20210510152736_added_entity_Ordine.xml', '2021-05-10 17:33:57', 10, 'EXECUTED', '8:cf83b1382661269d33aa8bc6b8469dd2', 'createTable tableName=ordine', '', NULL, '4.3.2', NULL, NULL, '0660836735'),
('20210510152736-1-data', 'jhipster', 'config/liquibase/changelog/20210510152736_added_entity_Ordine.xml', '2021-05-10 17:33:57', 11, 'EXECUTED', '8:5cc9fdbae8a52cb22a7c3a9d5e006797', 'loadData tableName=ordine', '', NULL, '4.3.2', 'faker', NULL, '0660836735'),
('20210510152732-2', 'jhipster', 'config/liquibase/changelog/20210510152732_added_entity_constraints_UserExtra.xml', '2021-05-10 17:33:57', 12, 'EXECUTED', '8:3da0464fd1bbddcf988dd35823013628', 'addForeignKeyConstraint baseTableName=user_extra, constraintName=fk_user_extra__user_id, referencedTableName=jhi_user', '', NULL, '4.3.2', NULL, NULL, '0660836735'),
('20210510152733-2', 'jhipster', 'config/liquibase/changelog/20210510152733_added_entity_constraints_Cliente.xml', '2021-05-10 17:33:58', 13, 'EXECUTED', '8:5cf5ada64279d45be9d70afcf9f137be', 'addForeignKeyConstraint baseTableName=cliente, constraintName=fk_cliente__user_extra_id, referencedTableName=user_extra', '', NULL, '4.3.2', NULL, NULL, '0660836735'),
('20210510152734-2', 'jhipster', 'config/liquibase/changelog/20210510152734_added_entity_constraints_Venditore.xml', '2021-05-10 17:33:58', 14, 'EXECUTED', '8:ac3e0b1c7fcd075a822f73eb419fbb89', 'addForeignKeyConstraint baseTableName=venditore, constraintName=fk_venditore__user_extra_id, referencedTableName=user_extra', '', NULL, '4.3.2', NULL, NULL, '0660836735'),
('20210510152735-2', 'jhipster', 'config/liquibase/changelog/20210510152735_added_entity_constraints_Prodotto.xml', '2021-05-10 17:33:58', 15, 'EXECUTED', '8:b3e081ebbd668b85c92c21e40f75a81a', 'addForeignKeyConstraint baseTableName=prodotto, constraintName=fk_prodotto__venditore_id, referencedTableName=venditore', '', NULL, '4.3.2', NULL, NULL, '0660836735'),
('20210510152736-2', 'jhipster', 'config/liquibase/changelog/20210510152736_added_entity_constraints_Ordine.xml', '2021-05-10 17:33:58', 16, 'EXECUTED', '8:3048a69b37961cb810351c5e57226558', 'addForeignKeyConstraint baseTableName=ordine, constraintName=fk_ordine__cliente_id, referencedTableName=cliente; addForeignKeyConstraint baseTableName=ordine, constraintName=fk_ordine__prodotto_id, referencedTableName=prodotto; addForeignKeyConstr...', '', NULL, '4.3.2', NULL, NULL, '0660836735');

-- --------------------------------------------------------

--
-- Struttura della tabella `databasechangeloglock`
--

CREATE TABLE `databasechangeloglock` (
  `ID` int(11) NOT NULL,
  `LOCKED` bit(1) NOT NULL,
  `LOCKGRANTED` datetime DEFAULT NULL,
  `LOCKEDBY` varchar(255) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Dump dei dati per la tabella `databasechangeloglock`
--

INSERT INTO `databasechangeloglock` (`ID`, `LOCKED`, `LOCKGRANTED`, `LOCKEDBY`) VALUES
(1, b'1', '2021-05-15 21:30:50', 'zolla94 (192.168.1.213)');

-- --------------------------------------------------------

--
-- Struttura della tabella `jhi_authority`
--

CREATE TABLE `jhi_authority` (
  `name` varchar(50) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Dump dei dati per la tabella `jhi_authority`
--

INSERT INTO `jhi_authority` (`name`) VALUES
('ROLE_ADMIN'),
('ROLE_CUSTOMER'),
('ROLE_SELLER'),
('ROLE_USER');

-- --------------------------------------------------------

--
-- Struttura della tabella `jhi_user`
--

CREATE TABLE `jhi_user` (
  `id` bigint(20) NOT NULL,
  `login` varchar(50) NOT NULL,
  `password_hash` varchar(60) NOT NULL,
  `first_name` varchar(50) DEFAULT NULL,
  `last_name` varchar(50) DEFAULT NULL,
  `email` varchar(191) DEFAULT NULL,
  `image_url` varchar(256) DEFAULT NULL,
  `activated` bit(1) NOT NULL,
  `lang_key` varchar(10) DEFAULT NULL,
  `activation_key` varchar(20) DEFAULT NULL,
  `reset_key` varchar(20) DEFAULT NULL,
  `created_by` varchar(50) NOT NULL,
  `created_date` timestamp NULL,
  `reset_date` timestamp NULL DEFAULT NULL,
  `last_modified_by` varchar(50) DEFAULT NULL,
  `last_modified_date` timestamp NULL DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Dump dei dati per la tabella `jhi_user`
--

INSERT INTO `jhi_user` (`id`, `login`, `password_hash`, `first_name`, `last_name`, `email`, `image_url`, `activated`, `lang_key`, `activation_key`, `reset_key`, `created_by`, `created_date`, `reset_date`, `last_modified_by`, `last_modified_date`) VALUES
(1, 'zolla94', '$2a$10$gSAhZrxMllrbgj/kkK9UceBPpChGWJA7SYIb1Mqo.n5aNLq1/oRrC', 'Salvatore', 'Zollino', 'admin@localhost', '', b'1', 'it', NULL, NULL, 'system', NULL, NULL, 'zolla94', '2021-05-31 07:05:17'),
(2, 'user', '$2a$10$VEjxo0jq2YG9Rbk2HmX9S.k1uZBGYUHdUcid3g/vfiEl7lwWgOH/K', 'User', 'User', 'user@localhost', '', b'1', 'it', NULL, NULL, 'system', NULL, NULL, 'zolla94', '2021-05-11 06:01:44'),
(3, 'customer', '$2a$10$d2JRvlkkhdT5nD7SCyzrK.nCuHEBRobOhRC3qoWZ332RvD2DepF5u', 'Rebecca', 'Ghergo', 'murphiloveyou@gg.com', NULL, b'1', 'it', '7L1krgrpM7SqffU4S1jr', NULL, 'anonymousUser', '2021-05-11 05:51:49', NULL, 'customer', '2021-05-31 07:04:42'),
(4, 'seller', '$2a$10$kMlASIj1BqyytoAOgd.4ce4VO4GUq0zg.pL.rzPvoD3Ookc.xuN0a', 'Tommaso', 'Angeli', 'tommangeli@gg.com', NULL, b'1', 'it', 'xMUDF8Z4kfVlAJzQX0XA', NULL, 'anonymousUser', '2021-05-11 05:53:42', NULL, 'seller', '2021-05-31 06:55:46'),
(5, 'aukey', '$2a$10$HM4WdEF0DBBNg73aLMjed.tTw0iXOyjAN8dWaji2fGky471MMbpA2', 'Aukey', 'Aukey', 'aukey@gmail.com', NULL, b'1', 'it', 'PJbxvYzuNNRinMiwkESI', NULL, 'anonymousUser', '2021-05-11 07:24:16', NULL, 'aukey', '2021-05-25 13:29:04'),
(6, 'botteon', '$2a$10$x6uxC5tTbD.4o./bkC5dXeZDhjmV3Au6oBNI0e6qHwXkO39k4XQDe', 'Davide', 'Botteon', 'bottiamo@g.com', NULL, b'1', 'it', 'p1NkSxPFxeC53r3grdUJ', NULL, 'anonymousUser', '2021-05-11 07:27:58', NULL, 'zolla94', '2021-05-11 16:46:17'),
(10, 'decathlon', '$2a$10$tV.RyKGllQWaSCl94wXh8.2HeJTEY79dopJOp1B0XApuBGrfDVFVS', 'Decathlon', 'Decathlon', 'deca@g.com', NULL, b'1', 'it', '9EM45OFoktHkgR1jpDuV', NULL, 'anonymousUser', '2021-05-14 08:21:57', NULL, 'decathlon', '2021-05-25 11:31:12'),
(11, 'sara', '$2a$10$szG0QcTZiMuiM9E5Qpy1E.NTY/rcqO3VlegZnf1PedgAAzgmQVMPC', 'Sara', 'Pirelli', 'sara@g', NULL, b'1', 'it', 'SCgTzE28AhIChagGlKT6', NULL, 'anonymousUser', '2021-05-20 05:06:21', NULL, 'sara', '2021-05-20 05:08:39'),
(13, 'isabella', '$2a$10$iqReL2yb03FvHqtGFLdjTOp7SkYf2ChbVBnyS6HkTQaemUey.2LYi', 'Isabella', 'Caloro', 'isa@ca', NULL, b'1', 'it', '6ERoHDmnMPfAqXLUAYpX', NULL, 'anonymousUser', '2021-05-25 12:44:38', NULL, 'anonymousUser', '2021-05-25 12:44:38');

-- --------------------------------------------------------

--
-- Struttura della tabella `jhi_user_authority`
--

CREATE TABLE `jhi_user_authority` (
  `user_id` bigint(20) NOT NULL,
  `authority_name` varchar(50) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Dump dei dati per la tabella `jhi_user_authority`
--

INSERT INTO `jhi_user_authority` (`user_id`, `authority_name`) VALUES
(1, 'ROLE_ADMIN'),
(2, 'ROLE_USER'),
(3, 'ROLE_CUSTOMER'),
(4, 'ROLE_SELLER'),
(5, 'ROLE_SELLER'),
(6, 'ROLE_CUSTOMER'),
(10, 'ROLE_SELLER'),
(11, 'ROLE_CUSTOMER'),
(13, 'ROLE_CUSTOMER');

-- --------------------------------------------------------

--
-- Struttura della tabella `ordine`
--

CREATE TABLE `ordine` (
  `id` bigint(20) NOT NULL,
  `acquistato` bit(1) NOT NULL,
  `spedito` bit(1) DEFAULT NULL,
  `quantita` int(11) NOT NULL,
  `cliente_id` bigint(20) DEFAULT NULL,
  `prodotto_id` bigint(20) DEFAULT NULL,
  `venditore_id` bigint(20) DEFAULT NULL,
  `totale` double NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Dump dei dati per la tabella `ordine`
--

INSERT INTO `ordine` (`id`, `acquistato`, `spedito`, `quantita`, `cliente_id`, `prodotto_id`, `venditore_id`, `totale`) VALUES
(1, b'1', b'0', 4, 1, 3, 1, 57.57599999999999),
(2, b'1', b'0', 5, 1, 2, 2, 150),
(3, b'1', b'0', 1, 1, 3, 1, 14.393999999999998),
(4, b'1', b'0', 5, 1, 4, 1, 17.97),
(5, b'1', b'0', 5, 1, 5, 1, 29.97),
(6, b'1', b'0', 1, 1, 1, 2, 58.5),
(7, b'1', b'0', 1, 1, 2, 2, 30),
(8, b'1', b'0', 1, 1, 3, 1, 14.393999999999998);

-- --------------------------------------------------------

--
-- Struttura della tabella `prodotto`
--

CREATE TABLE `prodotto` (
  `id` bigint(20) NOT NULL,
  `nome` varchar(255) NOT NULL,
  `descrizione` varchar(255) NOT NULL,
  `prezzo` double NOT NULL,
  `disponibilita` int(11) DEFAULT NULL,
  `categoria` varchar(255) NOT NULL,
  `image_url` varchar(255) DEFAULT NULL,
  `venditore_id` bigint(20) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Dump dei dati per la tabella `prodotto`
--

INSERT INTO `prodotto` (`id`, `nome`, `descrizione`, `prezzo`, `disponibilita`, `categoria`, `image_url`, `venditore_id`) VALUES
(1, 'Lara Croft', 'Con basetta in legno', 58.5, 73, 'ACTIONFIGURE', NULL, 2),
(2, 'Charizard', 'Dipinto a mano', 30, 79, 'ACTIONFIGURE', NULL, 2),
(3, 'Orologio Star Wars', 'Stampato in 3D', 23.99, 79, 'ARREDAMENTO', NULL, 1),
(4, 'Cornice Friends', 'Direttamente dalla serie originale', 5.99, 80, 'ARREDAMENTO', NULL, 1),
(5, 'Bulbasaur portapianta', '3d printed', 9.99, 80, 'ARREDAMENTO', NULL, 1),
(6, 'Oddish portapianta', 'Stampato in 3D e dipinto a mano', 15, 85, 'ARREDAMENTO', NULL, 2),
(7, 'Scarpe da running personalizzate', 'Colorate con colori da tessuto e impermeabili', 45.69, 85, 'ALTRO', NULL, 6),
(8, 'Magliette personalizzate', 'Personalizzala con il tuo nome e numero', 14, 85, 'ALTRO', NULL, 6);

-- --------------------------------------------------------

--
-- Struttura della tabella `sconto`
--

CREATE TABLE `sconto` (
  `id` int(11) NOT NULL,
  `nome` varchar(50) NOT NULL,
  `giorni` varchar(500) NOT NULL,
  `valore` int(11) NOT NULL,
  `cat` varchar(50) NOT NULL,
  `attivo` tinyint(1) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Dump dei dati per la tabella `sconto`
--

INSERT INTO `sconto` (`id`, `nome`, `giorni`, `valore`, `cat`, `attivo`) VALUES
(1, 'SuperWeekend', 'Giovedì', 50, 'ACTIONFIGURE', 0);

-- --------------------------------------------------------

--
-- Struttura della tabella `user_extra`
--

CREATE TABLE `user_extra` (
  `id` bigint(20) NOT NULL,
  `indirizzo` varchar(255) DEFAULT NULL,
  `telefono` varchar(255) DEFAULT NULL,
  `ruolo` varchar(255) DEFAULT NULL,
  `user_id` bigint(20) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Dump dei dati per la tabella `user_extra`
--

INSERT INTO `user_extra` (`id`, `indirizzo`, `telefono`, `ruolo`, `user_id`) VALUES
(1, 'Via Domodossola, 81', '3485442206', 'CLIENTE', 3),
(2, 'Corso Montegrappa, 45', '3695844121', 'VENDITORE', 4),
(3, 'Via Aukey, 5', '3541145877', 'VENDITORE', 5),
(4, 'Via Vittorio Veneto, 71', '3289954783', 'CLIENTE', 6),
(8, 'Torino Centro, 4', '3588966981', 'VENDITORE', 10),
(9, 'Via Tripolitania, 211, Edificio F, interno 31', '3294191824', 'CLIENTE', 11),
(11, 'Corso Apulia, 60', '3467866780', 'CLIENTE', 13);

-- --------------------------------------------------------

--
-- Struttura della tabella `venditore`
--

CREATE TABLE `venditore` (
  `id` bigint(20) NOT NULL,
  `user_extra_id` bigint(20) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Dump dei dati per la tabella `venditore`
--

INSERT INTO `venditore` (`id`, `user_extra_id`) VALUES
(1, 2),
(2, 3),
(6, 8);

--
-- Indici per le tabelle scaricate
--

--
-- Indici per le tabelle `cliente`
--
ALTER TABLE `cliente`
  ADD PRIMARY KEY (`id`),
  ADD UNIQUE KEY `ux_cliente__user_extra_id` (`user_extra_id`);

--
-- Indici per le tabelle `databasechangeloglock`
--
ALTER TABLE `databasechangeloglock`
  ADD PRIMARY KEY (`ID`);

--
-- Indici per le tabelle `jhi_authority`
--
ALTER TABLE `jhi_authority`
  ADD PRIMARY KEY (`name`);

--
-- Indici per le tabelle `jhi_user`
--
ALTER TABLE `jhi_user`
  ADD PRIMARY KEY (`id`),
  ADD UNIQUE KEY `ux_user_login` (`login`),
  ADD UNIQUE KEY `ux_user_email` (`email`);

--
-- Indici per le tabelle `jhi_user_authority`
--
ALTER TABLE `jhi_user_authority`
  ADD PRIMARY KEY (`user_id`,`authority_name`),
  ADD KEY `fk_authority_name` (`authority_name`);

--
-- Indici per le tabelle `ordine`
--
ALTER TABLE `ordine`
  ADD PRIMARY KEY (`id`),
  ADD KEY `fk_ordine__cliente_id` (`cliente_id`),
  ADD KEY `fk_ordine__prodotto_id` (`prodotto_id`),
  ADD KEY `fk_ordine__venditore_id` (`venditore_id`);

--
-- Indici per le tabelle `prodotto`
--
ALTER TABLE `prodotto`
  ADD PRIMARY KEY (`id`),
  ADD KEY `fk_prodotto__venditore_id` (`venditore_id`);

--
-- Indici per le tabelle `sconto`
--
ALTER TABLE `sconto`
  ADD PRIMARY KEY (`id`);

--
-- Indici per le tabelle `user_extra`
--
ALTER TABLE `user_extra`
  ADD PRIMARY KEY (`id`),
  ADD UNIQUE KEY `ux_user_extra__user_id` (`user_id`);

--
-- Indici per le tabelle `venditore`
--
ALTER TABLE `venditore`
  ADD PRIMARY KEY (`id`),
  ADD UNIQUE KEY `ux_venditore__user_extra_id` (`user_extra_id`);

--
-- AUTO_INCREMENT per le tabelle scaricate
--

--
-- AUTO_INCREMENT per la tabella `cliente`
--
ALTER TABLE `cliente`
  MODIFY `id` bigint(20) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=12;

--
-- AUTO_INCREMENT per la tabella `jhi_user`
--
ALTER TABLE `jhi_user`
  MODIFY `id` bigint(20) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=20;

--
-- AUTO_INCREMENT per la tabella `ordine`
--
ALTER TABLE `ordine`
  MODIFY `id` bigint(20) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=9;

--
-- AUTO_INCREMENT per la tabella `prodotto`
--
ALTER TABLE `prodotto`
  MODIFY `id` bigint(20) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=9;

--
-- AUTO_INCREMENT per la tabella `sconto`
--
ALTER TABLE `sconto`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=2;

--
-- AUTO_INCREMENT per la tabella `user_extra`
--
ALTER TABLE `user_extra`
  MODIFY `id` bigint(20) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=18;

--
-- AUTO_INCREMENT per la tabella `venditore`
--
ALTER TABLE `venditore`
  MODIFY `id` bigint(20) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=7;

--
-- Limiti per le tabelle scaricate
--

--
-- Limiti per la tabella `cliente`
--
ALTER TABLE `cliente`
  ADD CONSTRAINT `fk_cliente__user_extra_id` FOREIGN KEY (`user_extra_id`) REFERENCES `user_extra` (`id`);

--
-- Limiti per la tabella `jhi_user_authority`
--
ALTER TABLE `jhi_user_authority`
  ADD CONSTRAINT `fk_authority_name` FOREIGN KEY (`authority_name`) REFERENCES `jhi_authority` (`name`),
  ADD CONSTRAINT `fk_user_id` FOREIGN KEY (`user_id`) REFERENCES `jhi_user` (`id`);

--
-- Limiti per la tabella `ordine`
--
ALTER TABLE `ordine`
  ADD CONSTRAINT `fk_ordine__cliente_id` FOREIGN KEY (`cliente_id`) REFERENCES `cliente` (`id`),
  ADD CONSTRAINT `fk_ordine__prodotto_id` FOREIGN KEY (`prodotto_id`) REFERENCES `prodotto` (`id`),
  ADD CONSTRAINT `fk_ordine__venditore_id` FOREIGN KEY (`venditore_id`) REFERENCES `venditore` (`id`);

--
-- Limiti per la tabella `prodotto`
--
ALTER TABLE `prodotto`
  ADD CONSTRAINT `fk_prodotto__venditore_id` FOREIGN KEY (`venditore_id`) REFERENCES `venditore` (`id`);

--
-- Limiti per la tabella `user_extra`
--
ALTER TABLE `user_extra`
  ADD CONSTRAINT `fk_user_extra__user_id` FOREIGN KEY (`user_id`) REFERENCES `jhi_user` (`id`);

--
-- Limiti per la tabella `venditore`
--
ALTER TABLE `venditore`
  ADD CONSTRAINT `fk_venditore__user_extra_id` FOREIGN KEY (`user_extra_id`) REFERENCES `user_extra` (`id`);
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
