CREATE DATABASE test;

USE test;

DROP TABLE IF EXISTS post CASCADE;
DROP TABLE IF EXISTS user CASCADE;
DROP TABLE IF EXISTS category CASCADE;

CREATE TABLE user (
  user_id         INTEGER PRIMARY KEY NOT NULL AUTO_INCREMENT,
  user_name       VARCHAR(30) NOT NULL UNIQUE,
  user_telephone  CHAR(20) UNIQUE,
  user_email      CHAR(30) UNIQUE,
  user_password   VARCHAR(200) NOT NULL,
  user_created_at DATETIME    NOT NULL DEFAULT CURRENT_TIMESTAMP,
  user_updated_at DATETIME    NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  user_enabled    BOOLEAN     NOT NULL DEFAULT FALSE
);

CREATE TABLE category (
  category_id         INTEGER PRIMARY KEY NOT NULL AUTO_INCREMENT,
  category_parent_id  INTEGER NOT NULL,
  category_root_id    INTEGER NOT NULL,
  category_name_en    VARCHAR(100),
  category_name_cn    VARCHAR(100),
  category_level      TINYINT NOT NULL,
  category_created_at DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP,
  category_updated_at DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  category_enabled    BOOLEAN  NOT NULL DEFAULT FALSE,
  FOREIGN KEY (category_parent_id) REFERENCES category(category_id),
  FOREIGN KEY (category_root_id) REFERENCES category(category_id)
);

DROP TRIGGER IF EXISTS set_category_parent_id_on_insert;

DELIMITER $
CREATE TRIGGER set_category_parent_id_on_insert
BEFORE INSERT ON category
FOR EACH ROW
  BEGIN
    DECLARE self_id INT;
    DECLARE parent_level TINYINT;
    DECLARE root_id INT;

    SELECT AUTO_INCREMENT INTO self_id
    FROM information_schema.TABLES
    WHERE TABLE_NAME = 'category' AND TABLE_SCHEMA = database();

    IF NEW.category_parent_id IS NULL THEN
      SET NEW.category_parent_id = self_id;
      SET NEW.category_root_id = self_id;
      SET NEW.category_level = 1;
    ELSE IF NEW.category_parent_id > 0 THEN
      SET parent_level = (
        SELECT category_level
        FROM category
        WHERE category_id = NEW.category_parent_id
      );

      SET root_id = (
        SELECT category_root_id
        FROM category
        WHERE category_id = NEW.category_parent_id
      );

      SET NEW.category_level = parent_level + 1;
      SET NEW.category_root_id = root_id;
    ELSE
      SIGNAL SQLSTATE '45000' SET MESSAGE_TEXT = 'Cannot set category_parent_id = 0, constraint by trigger `set_category_parent_id_on_insert`';
    END IF ;
    END IF;
  END$
DELIMITER ;

DROP TRIGGER IF EXISTS disable_update_category_inherit_structure;

DELIMITER $
CREATE TRIGGER disable_update_category_inherit_structure
BEFORE UPDATE ON category
FOR EACH ROW
  BEGIN
    IF NEW.category_parent_id != OLD.category_parent_id THEN
      SIGNAL SQLSTATE '45000' SET MESSAGE_TEXT = 'Cannot update the category parent id, constraint by trigger `disable_update_category_inherit_structure`';
    END IF;

    IF NEW.category_root_id != OLD.category_root_id THEN
      SIGNAL SQLSTATE '45000' SET MESSAGE_TEXT = 'Cannot update the category root id, constraint by trigger `disable_update_category_inherit_structure`';
    END IF;

    IF NEW.category_level != OLD.category_level THEN
      SIGNAL SQLSTATE '45000' SET MESSAGE_TEXT = 'Cannot update the category level, constraint by trigger `disable_update_category_inherit_structure`';
    END IF;
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