CREATE TABLE bag_discipline
(
  id BIGINT PRIMARY KEY NOT NULL,
  name VARCHAR(100) NOT NULL,
  description VARCHAR(255)
);
CREATE UNIQUE INDEX "DISCIPLINE_NAME_uindex" ON bag_discipline (name);

CREATE SEQUENCE BAG_SEQUENCE INCREMENT by 1 MINVALUE 1000;