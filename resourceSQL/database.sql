-- MySQL Workbench Forward Engineering


-- -----------------------------------------------------
-- Schema florist
-- -----------------------------------------------------
DROP SCHEMA IF EXISTS `florist` ;

-- -----------------------------------------------------
-- Schema florist
-- -----------------------------------------------------
CREATE SCHEMA IF NOT EXISTS `florist` DEFAULT CHARACTER SET utf8 ;
USE `florist` ;

-- -----------------------------------------------------
-- Table `florist`.`florist`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `florist`.`florist` (
  `id_florist` INT NOT NULL AUTO_INCREMENT,
  `name` VARCHAR(45) NOT NULL,
  `total_stock_value` DOUBLE NOT NULL,
  PRIMARY KEY (`id_florist`))
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `florist`.`stock`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `florist`.`stock` (
  `id_stock` INT NOT NULL AUTO_INCREMENT,
  `florist_id_florist` INT NOT NULL,
  PRIMARY KEY (`id_stock`),
  INDEX `fk_stock_florist_idx` (`florist_id_florist` ASC) VISIBLE,
  CONSTRAINT `fk_stock_florist`
    FOREIGN KEY (`florist_id_florist`)
    REFERENCES `florist`.`florist` (`id_florist`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `florist`.`product`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `florist`.`product` (
  `id_product` INT NOT NULL AUTO_INCREMENT,
  `price` DOUBLE NOT NULL,
  `name` VARCHAR(45) NOT NULL,
  `type` ENUM("tree", "flower", "decoration") NULL,
  `color` VARCHAR(45) NULL,
  `height` DOUBLE NULL,
  `material_type` ENUM("wood", "plastic") NULL,
  PRIMARY KEY (`id_product`))
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `florist`.`ticket`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `florist`.`ticket` (
  `id_ticket` INT NOT NULL AUTO_INCREMENT,
  `date` DATETIME NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  `total_price` DOUBLE NOT NULL,
  `florist_id_florist` INT NOT NULL,
  PRIMARY KEY (`id_ticket`),
  INDEX `fk_ticket_florist1_idx` (`florist_id_florist` ASC) VISIBLE,
  CONSTRAINT `fk_ticket_florist1`
    FOREIGN KEY (`florist_id_florist`)
    REFERENCES `florist`.`florist` (`id_florist`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `florist`.`stock_has_product`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `florist`.`stock_has_product` (
  `quantity` INT NOT NULL,
  `stock_id_stock` INT NOT NULL,
  `product_id_product` INT NOT NULL,
  INDEX `fk_stock_has_product_stock1_idx` (`stock_id_stock` ASC) VISIBLE,
  INDEX `fk_stock_has_product_product1_idx` (`product_id_product` ASC) VISIBLE,
  CONSTRAINT `fk_stock_has_product_stock1`
    FOREIGN KEY (`stock_id_stock`)
    REFERENCES `florist`.`stock` (`id_stock`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_stock_has_product_product1`
    FOREIGN KEY (`product_id_product`)
    REFERENCES `florist`.`product` (`id_product`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `florist`.`product_has_ticket`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `florist`.`product_has_ticket` (
  `quantity` INT NOT NULL,
  `ticket_id_ticket` INT NOT NULL,
  `product_id_product` INT NOT NULL,
  INDEX `fk_product_has_ticket_ticket1_idx` (`ticket_id_ticket` ASC) VISIBLE,
  INDEX `fk_product_has_ticket_product1_idx` (`product_id_product` ASC) VISIBLE,
  CONSTRAINT `fk_product_has_ticket_ticket1`
    FOREIGN KEY (`ticket_id_ticket`)
    REFERENCES `florist`.`ticket` (`id_ticket`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_product_has_ticket_product1`
    FOREIGN KEY (`product_id_product`)
    REFERENCES `florist`.`product` (`id_product`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;



