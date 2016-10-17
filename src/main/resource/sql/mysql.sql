CREATE DATABASE test;

USE test;

DROP TABLE IF EXISTS post CASCADE;
DROP TABLE IF EXISTS user CASCADE;
DROP TABLE IF EXISTS category CASCADE;

CREATE TABLE user (
  user_id         INTEGER PRIMARY KEY NOT NULL AUTO_INCREMENT,
  user_username   VARCHAR(30),
  user_telephone  CHAR(20),
  user_email      CHAR(30),
  user_password   VARCHAR(30) NOT NULL,
  user_created_at DATETIME    NOT NULL DEFAULT CURRENT_TIMESTAMP,
  user_updated_at DATETIME    NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  user_enabled    BOOLEAN     NOT NULL DEFAULT FALSE
);

CREATE TABLE category (
  category_id         INTEGER PRIMARY KEY NOT NULL AUTO_INCREMENT,
  category_parent_id  INTEGER,
  category_root_id    INTEGER,
  category_name_en    VARCHAR(100),
  category_name_cn    VARCHAR(100),
  category_level      TINYINT NOT NULL DEFAULT 1,
  category_created_at DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP,
  category_updated_at DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  category_enabled    BOOLEAN  NOT NULL DEFAULT FALSE,
  FOREIGN KEY (category_parent_id) REFERENCES category(category_id),
  FOREIGN KEY (category_parent_id) REFERENCES category(category_id)
);

DELIMITER $
CREATE TRIGGER set_category_parent_id
BEFORE INSERT ON category
FOR EACH ROW
  BEGIN

  END$
DELIMITER ;


CREATE TABLE post (
  post_id         INTEGER PRIMARY KEY NOT NULL AUTO_INCREMENT,
  category_id     INTEGER NOT NULL,
  post_title      VARCHAR(100),
  post_content    TEXT,
  user_id         INTEGER  NOT NULL,
  post_published  BOOLEAN  NOT NULL  DEFAULT FALSE,
  post_created_at DATETIME NOT NULL  DEFAULT CURRENT_TIMESTAMP,
  post_updated_at DATETIME NOT NULL  DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  post_enabled    BOOLEAN  NOT NULL  DEFAULT FALSE,
  FOREIGN KEY (user_id) REFERENCES user(user_id),
  FOREIGN KEY (category_id) REFERENCES category(category_id)
);