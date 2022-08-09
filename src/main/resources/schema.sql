CREATE DATABASE IF NOT EXISTS `restfull_service_db`;
USE `restfull_service_db`;

CREATE TABLE IF NOT EXISTS `users`
(
    `user_id`  bigint(5) NOT NULL AUTO_INCREMENT,
    `username` varchar(200) NOT NULL,
    `password` varchar(100) NOT NULL,
    PRIMARY KEY (`user_id`)
) ENGINE = InnoDB;

CREATE TABLE IF NOT EXISTS `animals`
(
    `animal_id` bigint(5) NOT NULL AUTO_INCREMENT,
    `type`      enum ('Cat','Dog','Fox','Wolf','Fish') NOT NULL,
    `gender`    enum ('Male', 'Female') NOT NULL,
    `nickname`  varchar(200) NOT NULL,
    `username` varchar(200) DEFAULT NULL,
    `birthday` datetime NOT NULL,
    PRIMARY KEY (`animal_id`)
) ENGINE = InnoDB;

CREATE TABLE IF NOT EXISTS `user_roles`
(
    `user_id` bigint(5) NOT NULL,
    `role_id` bigint(5) NOT NULL
) ENGINE = InnoDB;

CREATE TABLE IF NOT EXISTS `roles`
(
    `role_id` bigint(5) NOT NULL AUTO_INCREMENT,
    `name`enum ('ROLE_USER', 'ROLE_ADMIN', 'ROLE_MODERATOR') NOT NULL,
     PRIMARY KEY (`role_id`)
) ENGINE = InnoDB;

INSERT INTO roles (name)
VALUES ('ROLE_USER');

INSERT INTO roles (name)
VALUES ('ROLE_ADMIN');

INSERT INTO roles (name)
VALUES ('ROLE_MODERATOR');