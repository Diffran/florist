-- MySQL Workbench Forward Engineering

SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0;
SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0;
SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='ONLY_FULL_GROUP_BY,STRICT_TRANS_TABLES,NO_ZERO_IN_DATE,NO_ZERO_DATE,ERROR_FOR_DIVISION_BY_ZERO,NO_ENGINE_SUBSTITUTION';

-- -----------------------------------------------------
-- Schema florist
-- -----------------------------------------------------

-- -----------------------------------------------------
-- Schema florist
-- -----------------------------------------------------
CREATE SCHEMA IF NOT EXISTS `florist` DEFAULT CHARACTER SET utf8 ;
USE `florist` ;

-- -----------------------------------------------------
-- Table `florist`.`PRODUCT`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `florist`.`PRODUCT` (
  `idPRODUCT` INT NOT NULL AUTO_INCREMENT,
  `price` DOUBLE NOT NULL,
  `name` VARCHAR(45) NOT NULL,
  `type` ENUM('tree', 'flower', 'decoration') NOT NULL,
  `color` VARCHAR(45) NULL,
  `height` DOUBLE NULL,
  `materialType` ENUM('wood', 'plastic') NULL,
  PRIMARY KEY (`idPRODUCT`))
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `florist`.`FLORIST`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `florist`.`FLORIST` (
  `idFLORIST` INT NOT NULL AUTO_INCREMENT,
  `name` VARCHAR(45) NOT NULL,
  `totalStockValue` DOUBLE NOT NULL DEFAULT 0,
  PRIMARY KEY (`idFLORIST`))
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `florist`.`TICKET`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `florist`.`TICKET` (
  `idTICKET` INT NOT NULL AUTO_INCREMENT,
  `date` DATETIME NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  `idFLORIST` INT NOT NULL,
  `totalPrice` DOUBLE NOT NULL DEFAULT 0,
  PRIMARY KEY (`idTICKET`),
  INDEX `fk_TICKET_FLORIST1_idx` (`idFLORIST` ASC) VISIBLE,
  CONSTRAINT `fk_TICKET_FLORIST1`
    FOREIGN KEY (`idFLORIST`)
    REFERENCES `florist`.`FLORIST` (`idFLORIST`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `florist`.`STOCK`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `florist`.`STOCK` (
  `idSTOCK` INT NOT NULL AUTO_INCREMENT,
  `idFLORIST` INT NOT NULL,
  PRIMARY KEY (`idSTOCK`),
  INDEX `fk_STOCK_FLORIST1_idx` (`idFLORIST` ASC) VISIBLE,
  CONSTRAINT `fk_STOCK_FLORIST1`
    FOREIGN KEY (`idFLORIST`)
    REFERENCES `florist`.`FLORIST` (`idFLORIST`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `florist`.`STOCK_has_PRODUCT`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `florist`.`STOCK_has_PRODUCT` (
  `idSTOCK` INT NOT NULL AUTO_INCREMENT,
  `idPRODUCT` INT NOT NULL,
  `quantity` INT NOT NULL DEFAULT 0,
  INDEX `fk_STOCK_has_PRODUCT_PRODUCT1_idx` (`idPRODUCT` ASC) VISIBLE,
  INDEX `fk_STOCK_has_PRODUCT_STOCK1_idx` (`idSTOCK` ASC) VISIBLE,
  CONSTRAINT `fk_STOCK_has_PRODUCT_STOCK1`
    FOREIGN KEY (`idSTOCK`)
    REFERENCES `florist`.`STOCK` (`idSTOCK`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_STOCK_has_PRODUCT_PRODUCT1`
    FOREIGN KEY (`idPRODUCT`)
    REFERENCES `florist`.`PRODUCT` (`idPRODUCT`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `florist`.`PRODUCT_has_TICKET`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `florist`.`PRODUCT_has_TICKET` (
  `idPRODUCT` INT NOT NULL,
  `idTICKET` INT NOT NULL,
  `quantity` INT NOT NULL DEFAULT 0,
  INDEX `fk_PRODUCT_has_TICKET_TICKET1_idx` (`idTICKET` ASC) VISIBLE,
  INDEX `fk_PRODUCT_has_TICKET_PRODUCT1_idx` (`idPRODUCT` ASC) VISIBLE,
  CONSTRAINT `fk_PRODUCT_has_TICKET_PRODUCT1`
    FOREIGN KEY (`idPRODUCT`)
    REFERENCES `florist`.`PRODUCT` (`idPRODUCT`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_PRODUCT_has_TICKET_TICKET1`
    FOREIGN KEY (`idTICKET`)
    REFERENCES `florist`.`TICKET` (`idTICKET`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


SET SQL_MODE=@OLD_SQL_MODE;
SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS;
SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS;

ALTER TABLE STOCK
ADD CONSTRAINT fk_stock_florist
FOREIGN KEY (idFLORIST) REFERENCES FLORIST(idFLORIST) ON DELETE CASCADE;

ALTER TABLE STOCK_has_PRODUCT
ADD CONSTRAINT fk_stock_has_product_stock
FOREIGN KEY (idSTOCK) REFERENCES STOCK(idSTOCK) ON DELETE CASCADE,
ADD CONSTRAINT fk_stock_has_product_product
FOREIGN KEY (idPRODUCT) REFERENCES PRODUCT(idPRODUCT) ON DELETE CASCADE;

ALTER TABLE TICKET
ADD CONSTRAINT fk_ticket_florist
FOREIGN KEY (idFLORIST) REFERENCES FLORIST(idFLORIST) ON DELETE CASCADE;

ALTER TABLE PRODUCT_has_TICKET
ADD CONSTRAINT fk_product_has_ticket_product
FOREIGN KEY (idPRODUCT) REFERENCES PRODUCT(idPRODUCT) ON DELETE CASCADE,
ADD CONSTRAINT fk_product_has_ticket_ticket
FOREIGN KEY (idTICKET) REFERENCES TICKET(idTICKET) ON DELETE CASCADE;

