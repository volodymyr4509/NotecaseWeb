CREATE TABLE Product
(
  ProductId           INT(11)      NOT NULL AUTO_INCREMENT PRIMARY KEY,
  Id                  INT(11)      NOT NULL,
  UserId              INT(11)      NOT NULL,
  CategoryId          INT(11)      NOT NULL,
  Name                VARCHAR(256) NOT NULL,
  Price               DOUBLE       NOT NULL,
  Created    TIMESTAMP    NOT NULL,
  LastUpdateTimestamp TIMESTAMP    NOT NULL DEFAULT NOW(),
  Enabled             TINYINT      NOT NULL DEFAULT '1'
);

CREATE TABLE Category
(
  CategoryId          INT(11)      NOT NULL AUTO_INCREMENT PRIMARY KEY,
  Id                  INT          NOT NULL,
  Name                VARCHAR(256) NOT NULL,
  Color               INT          NOT NULL,
  Image               INT          NOT NULL,
  LastUpdateTimestamp TIMESTAMP    NOT NULL DEFAULT NOW(),
  Enabled             TINYINT      NOT NULL DEFAULT '1'
);

CREATE TABLE User
(
  Id                  INT(11)      NOT NULL AUTO_INCREMENT PRIMARY KEY,
  UserName            VARCHAR(256) NOT NULL,
  UserPassword        VARCHAR(256) NOT NULL,
  Email               VARCHAR(256) NOT NULL,
  LastUpdateTimestamp TIMESTAMP    NOT NULL DEFAULT NOW(),
  Enabled             TINYINT      NOT NULL DEFAULT '1'
);
