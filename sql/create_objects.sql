CREATE TABLE IF NOT EXISTS `server_server` (
  `id` VARCHAR(64) NOT NULL,
  `alias` VARCHAR(45) NULL,
  `ip` VARCHAR(45) NOT NULL,
  `mac` VARCHAR(45) NULL,
  `netmask` VARCHAR(45) NULL,
  `rack` INT(45) NULL,
  `size` INT(45) NULL,
  `monitor_type` TINYINT NOT NULL,
  `properties` TEXT NULL,
  PRIMARY KEY (`id`),
  UNIQUE INDEX `id_UNIQUE` (`id` ASC),
  UNIQUE INDEX `ip_UNIQUE` (`ip` ASC))
ENGINE = InnoDB;

CREATE TABLE IF NOT EXISTS `server_ssh` (
  `server_id` VARCHAR(64) NOT NULL,
  `host` VARCHAR(45) NULL,
  `port` INT NULL,
  `username` VARCHAR(45) NULL,
  `password` VARCHAR(45) NULL,
  INDEX `server_ssh_fk_server_id_idx` (`server_id` ASC),
  CONSTRAINT `server_ssh_fk_server_id`
    FOREIGN KEY (`server_id`)
    REFERENCES `server_server` (`id`)
    ON DELETE CASCADE
    ON UPDATE CASCADE)
ENGINE = InnoDB;

CREATE TABLE IF NOT EXISTS `server_asset` (
  `server_id` VARCHAR(64) NOT NULL,
  `code` VARCHAR(45) NULL,
  `manufacturer` VARCHAR(45) NULL,
  `modal` VARCHAR(45) NULL,
  `serialsno` VARCHAR(45) NULL,
  `contacter` VARCHAR(45) NULL,
  `telephone` VARCHAR(45) NULL,
  `note` VARCHAR(256) NULL,
  INDEX `server_asset_fk_server_id_idx` (`server_id` ASC),
  CONSTRAINT `server_asset_fk_server_id`
    FOREIGN KEY (`server_id`)
    REFERENCES `server_server` (`id`)
    ON DELETE CASCADE
    ON UPDATE CASCADE)
ENGINE = InnoDB;

CREATE TABLE IF NOT EXISTS `server_ipmi` (
  `server_id` VARCHAR(64) NOT NULL,
  `host` VARCHAR(45) NULL,
  `username` VARCHAR(45) NULL,
  `password` VARCHAR(45) NULL,
  UNIQUE INDEX `host_UNIQUE` (`host` ASC),
  INDEX `server_ipmi_fk_server_id_idx` (`server_id` ASC),
  CONSTRAINT `server_ipmi_fk_server_id`
    FOREIGN KEY (`server_id`)
    REFERENCES `server_server` (`id`)
    ON DELETE CASCADE
    ON UPDATE CASCADE)
ENGINE = InnoDB;

CREATE VIEW `server_server_view` AS
	select a.*, b.code, b.manufacturer, b.modal, b.serialsno, b.contacter, b.telephone, b.note, 
    c.host ssh_host, c.port ssh_port, c.username ssh_username, c.password ssh_password,
    d.host ipmi_host, d.username ipmi_username, d.password ipmi_password
    from server_server a left join server_asset b on a.id = b.server_id
    left join server_ssh c on a.id = c.server_id
    left join server_ipmi d on a.id = d.server_id