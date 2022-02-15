CREATE DATABASE IF NOT EXISTS `LIGUES` DEFAULT CHARACTER SET utf8 COLLATE utf8_general_ci;
USE `LIGUES`;

CREATE TABLE `EMPLOYÉ` (
  `id_employe` INT NOT NULL AUTO_INCREMENT,
  `date-entrée` VARCHAR(42),
  `date_sortie` VARCHAR(42),
  `nom_employé` VARCHAR(42),
  `prénom_employé` VARCHAR(42),
  `email` VARCHAR(42),
  `password_employé` VARCHAR(42),
  `type` VARCHAR(42),
  `id_ligue` INT(42),
  PRIMARY KEY (`id_employe`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

CREATE TABLE `LIGUE` (
  `id_ligue` INT NOT NULL AUTO_INCREMENT, 
  `nom` VARCHAR(42),
  PRIMARY KEY (`id_ligue`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

ALTER TABLE `EMPLOYÉ` ADD FOREIGN KEY (`id_ligue`) REFERENCES `LIGUE` (`id_ligue`);