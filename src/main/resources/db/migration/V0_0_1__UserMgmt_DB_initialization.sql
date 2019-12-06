CREATE DATABASE IF NOT EXISTS security;

/*
 * - Every table has UUID along with its primary key.
 *    We shall not share ID(primary key) in API response, instead we should share UUID for particular entity
 *
 * - Every table has history table for auditing purpose. Master table and mapping tables have no history table
 *   History table will store extra columns like status, and performed_by
 *
 * - No UUID in mapping table
 *
 * - `status` is Enum
 * - `performed_by` (id of user whoever inserting/updating/deleting record in table)
 * - `ts` is TIMESTAMPTZ column which holds UTC timestamp
 *
 * - No foreign key constraints in history table
 */

/*
 * For every DML(insert,update,delete) action,
 * We will perform DML (One Insert in History and DML on original table)
 *  - insert into history table with latest status and current timestamp
 *  - then we will perform similar DML on original table
 * --------------------------------------------------------------------------------
 * Case 1: insert into user (insert bruce user)
 *
 *
 * user_history
 * id   uuid              name          status    performed_by    ts
 * 1    aasad-asas-asas   bruce         CREATED   10000           2019-10-10 10:10:10
 *
 * user
 * id   uuid              name          status    performed_by    ts
 * 1    aasad-asas-asas   bruce         CREATED   10000           2019-10-10 10:10:10
 * ---------------------------------------------------------------------------------
 * Case 2: update user (Update name of chandra)
 *
 * user_history
 * id   uuid              name          status    performed_by    ts
 * 1    aasad-asas-asas   bruce         CREATED   10000           2019-10-10 10:10:10
 * 1    aasad-asas-asas   batman        UPDATED   10001           2019-11-11 10:10:10
 *
 * user
 * id   uuid              name          status    performed_by    ts
 * 1    aasad-asas-asas   batman        UPDATED   10001           2019-11-11 10:10:10
 */

CREATE TABLE IF NOT EXISTS app_user (
  id BIGINT AUTO_INCREMENT,
  uuid CHAR(36) NOT NULL,
  email VARCHAR(50) NOT NULL,
  f_name VARCHAR(50) NOT NULL,
  l_name VARCHAR(50) NOT NULL,
  status ENUM('CREATED', 'UPDATED', 'DELETED', 'DEACTIVATED', 'REACTIVATED') NOT NULL,
  performed_by BIGINT NOT NULL,
  ts TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY(id),
  CONSTRAINT uk_app_user_uuid UNIQUE(uuid),
  CONSTRAINT uk_app_user_email UNIQUE(email)
);
CREATE TABLE IF NOT EXISTS app_user_history (
  id BIGINT AUTO_INCREMENT,
  uuid CHAR(36) NOT NULL,
  email VARCHAR(50) NOT NULL,
  f_name VARCHAR(50) NOT NULL,
  l_name VARCHAR(50) NOT NULL,
  status ENUM('CREATED', 'UPDATED', 'DELETED', 'DEACTIVATED', 'REACTIVATED') NOT NULL,
  performed_by BIGINT NOT NULL,
  ts TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY(id)
);
DELIMITER ;;
CREATE TRIGGER before_insert_app_user
BEFORE INSERT ON app_user
FOR EACH ROW
BEGIN
  IF new.uuid IS NULL THEN
    SET new.uuid = uuid();
  END IF;
END ;;

CREATE TABLE IF NOT EXISTS role (
  id BIGINT AUTO_INCREMENT,
  uuid CHAR(36) NOT NULL,
  name VARCHAR(50) NOT NULL,
  status ENUM('CREATED', 'UPDATED', 'DELETED', 'DEACTIVATED', 'REACTIVATED') NOT NULL,
  performed_by BIGINT NOT NULL,
  ts TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY(id)
);
CREATE TABLE IF NOT EXISTS role_history (
  id BIGINT AUTO_INCREMENT,
  uuid CHAR(36) NOT NULL,
  name VARCHAR(50) NOT NULL,
  status ENUM('CREATED', 'UPDATED', 'DELETED', 'DEACTIVATED', 'REACTIVATED') NOT NULL,
  performed_by BIGINT NOT NULL,
  ts TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY(id)
);
DELIMITER ;;
CREATE TRIGGER before_insert_role
BEFORE INSERT ON role
FOR EACH ROW
BEGIN
  IF new.uuid IS NULL THEN
    SET new.uuid = uuid();
  END IF;
END ;;

CREATE TABLE IF NOT EXISTS claim (
  id BIGINT AUTO_INCREMENT,
  uuid CHAR(36) NOT NULL,
  resource_name VARCHAR(50) NOT NULL,
  resource_http_method VARCHAR(50) NOT NULL,
  resource_endpoint VARCHAR(50) NOT NULL,
  status ENUM('CREATED', 'UPDATED', 'DELETED', 'DEACTIVATED', 'REACTIVATED') NOT NULL,
  performed_by BIGINT NOT NULL,
  ts TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY(id),
  CONSTRAINT uk_claim_uuid UNIQUE(uuid),
  CONSTRAINT uk_claim_name UNIQUE(resource_name)
);

CREATE TABLE IF NOT EXISTS role_claim (
  fk_role_id BIGINT NOT NULL,
  fk_claim_id BIGINT NOT NULL,
  PRIMARY KEY (fk_role_id, fk_claim_id),
  CONSTRAINT fk_role_claim_role FOREIGN KEY(fk_role_id) REFERENCES role(id),
  CONSTRAINT fk_role_claim_claim FOREIGN KEY(fk_claim_id) REFERENCES claim(id)
);

CREATE TABLE IF NOT EXISTS user_role (
  fk_user_id BIGINT NOT NULL,
  fk_role_id BIGINT NOT NULL,
  PRIMARY KEY (fk_user_id, fk_role_id),
  CONSTRAINT fk_user_role_app_user FOREIGN KEY(fk_user_id) REFERENCES app_user(id),
  CONSTRAINT fk_user_role_role FOREIGN KEY(fk_role_id) REFERENCES role(id)
);