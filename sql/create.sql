-- MySQL Workbench Forward Engineering

SET @OLD_UNIQUE_CHECKS = @@UNIQUE_CHECKS, UNIQUE_CHECKS = 0;
SET @OLD_FOREIGN_KEY_CHECKS = @@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS = 0;
SET @OLD_SQL_MODE = @@SQL_MODE, SQL_MODE = 'TRADITIONAL,ALLOW_INVALID_DATES';

-- -----------------------------------------------------
-- Schema hospital
-- -----------------------------------------------------
DROP SCHEMA IF EXISTS `hospital`;

-- -----------------------------------------------------
-- Schema hospital
-- -----------------------------------------------------
CREATE SCHEMA IF NOT EXISTS `hospital`
  DEFAULT CHARACTER SET utf8;
USE `hospital`;

-- -----------------------------------------------------
-- Table `hospital`.`role`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `hospital`.`role`;

CREATE TABLE IF NOT EXISTS `hospital`.`role` (
  `id_role`   INT(11)     NOT NULL AUTO_INCREMENT,
  `role_name` VARCHAR(45) NOT NULL,
  PRIMARY KEY (`id_role`)
)
  ENGINE = InnoDB;

-- -----------------------------------------------------
-- Table `hospital`.`chamber_type`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `hospital`.`chamber_type`;

CREATE TABLE IF NOT EXISTS `hospital`.`chamber_type` (
  `id_chamber`   INT(11)     NOT NULL,
  `chamber_name` VARCHAR(45) NOT NULL,
  PRIMARY KEY (`id_chamber`)
)
  ENGINE = InnoDB;

-- -----------------------------------------------------
-- Table `hospital`.`chamber`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `hospital`.`chamber`;

CREATE TABLE IF NOT EXISTS `hospital`.`chamber` (
  `id_chamber` INT(11) NOT NULL AUTO_INCREMENT,
  `max_count`  INT(11) NOT NULL,
  `number`     INT(11) NOT NULL,
  `fk_ch_type` INT(11) NOT NULL,
  PRIMARY KEY (`id_chamber`),
  INDEX `fk_chamber_chamber_type1_idx` (`fk_ch_type` ASC),
  CONSTRAINT `fk_chamber_chamber_type1`
  FOREIGN KEY (`fk_ch_type`)
  REFERENCES `hospital`.`chamber_type` (`id_chamber`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION
)
  ENGINE = InnoDB;

-- -----------------------------------------------------
-- Table `hospital`.`person`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `hospital`.`person`;

CREATE TABLE IF NOT EXISTS `hospital`.`person` (
  `id_person`  INT(11)     NOT NULL AUTO_INCREMENT,
  `name`       VARCHAR(90) NOT NULL,
  `surname`    VARCHAR(90) NOT NULL,
  `birthday`   DATE        NOT NULL,
  `phone`      VARCHAR(45) NULL,
  `email`      VARCHAR(45) NOT NULL,
  `password`   VARCHAR(90) NOT NULL,
  `fk_role`    INT(11)     NOT NULL,
  `fk_chamber` INT(11)     NULL,
  PRIMARY KEY (`id_person`),
  INDEX `fk_person_role_idx` (`fk_role` ASC),
  UNIQUE INDEX `email_UNIQUE` (`email` ASC),
  INDEX `fk_person_chamber1_idx` (`fk_chamber` ASC),
  UNIQUE INDEX `phone_UNIQUE` (`phone` ASC),
  CONSTRAINT `fk_person_role`
  FOREIGN KEY (`fk_role`)
  REFERENCES `hospital`.`role` (`id_role`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_person_chamber1`
  FOREIGN KEY (`fk_chamber`)
  REFERENCES `hospital`.`chamber` (`id_chamber`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION
)
  ENGINE = InnoDB;

-- -----------------------------------------------------
-- Table `hospital`.`diagnosis`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `hospital`.`diagnosis`;

CREATE TABLE IF NOT EXISTS `hospital`.`diagnosis` (
  `id_diagnosis`   INT(11)      NOT NULL AUTO_INCREMENT,
  `diagnosis_name` VARCHAR(45)  NOT NULL,
  `description`    VARCHAR(255) NOT NULL,
  PRIMARY KEY (`id_diagnosis`)
)
  ENGINE = InnoDB;

-- -----------------------------------------------------
-- Table `hospital`.`prescription`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `hospital`.`prescription`;

CREATE TABLE IF NOT EXISTS `hospital`.`prescription` (
  `id_prescription` INT(11)      NOT NULL AUTO_INCREMENT,
  `drugs`           VARCHAR(128) NULL,
  `procedure`       VARCHAR(128) NULL,
  `operation`       VARCHAR(128) NULL,
  PRIMARY KEY (`id_prescription`)
)
  ENGINE = InnoDB;

-- -----------------------------------------------------
-- Table `hospital`.`person_diagnosis`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `hospital`.`person_diagnosis`;

CREATE TABLE IF NOT EXISTS `hospital`.`person_diagnosis` (
  `id_patient`      INT(11)  NOT NULL,
  `id_staff`        INT(11)  NOT NULL,
  `id_diagnosis`    INT(11)  NOT NULL,
  `id_prescription` INT(11)  NOT NULL,
  `date`            DATETIME NOT NULL,
  `discharge_date`  DATETIME NULL,
  PRIMARY KEY (`id_patient`, `id_staff`, `id_diagnosis`, `id_prescription`),
  INDEX `fk_person_diagnosis_diagnosis1_idx` (`id_diagnosis` ASC),
  INDEX `fk_person_diagnosis_person2_idx` (`id_staff` ASC),
  INDEX `fk_person_diagnosis_prescription1_idx` (`id_prescription` ASC),
  CONSTRAINT `fk_person_diagnosis_diagnosis1`
  FOREIGN KEY (`id_diagnosis`)
  REFERENCES `hospital`.`diagnosis` (`id_diagnosis`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_person_diagnosis_person1`
  FOREIGN KEY (`id_patient`)
  REFERENCES `hospital`.`person` (`id_person`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_person_diagnosis_person2`
  FOREIGN KEY (`id_staff`)
  REFERENCES `hospital`.`person` (`id_person`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_person_diagnosis_prescription1`
  FOREIGN KEY (`id_prescription`)
  REFERENCES `hospital`.`prescription` (`id_prescription`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION
)
  ENGINE = InnoDB;


SET SQL_MODE = @OLD_SQL_MODE;
SET FOREIGN_KEY_CHECKS = @OLD_FOREIGN_KEY_CHECKS;
SET UNIQUE_CHECKS = @OLD_UNIQUE_CHECKS;
