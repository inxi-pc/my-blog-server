CREATE TABLE user (
  user_id         INTEGER PRIMARY KEY  AUTO_INCREMENT,
  user_username   VARCHAR(30),
  user_telephone  CHAR(20),
  user_email      CHAR(30),
  user_password   VARCHAR(30) NOT NULL,
  user_created_at DATETIME    NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP
);

CREATE TABLE post (
  post_id         INTEGER PRIMARY KEY AUTO_INCREMENT,
  post_content    TEXT,
  user_id         INTEGER  NOT NULL,
  post_created_at DATETIME NOT NULL   DEFAULT CURRENT_TIMESTAMP,
  post_published  BOOLEAN  NOT NULL   DEFAULT FALSE,
  post_updated_at DATETIME NOT NULL   DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  FOREIGN KEY (user_id) REFERENCES user(user_id)
)