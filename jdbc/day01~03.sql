show databases;
use nhn_academy_104;

-- day01-07, day01-08-01, day03
CREATE TABLE jdbc_student (
  id varchar(50) NOT NULL COMMENT '학생-아이디',
  name varchar(50) NOT NULL COMMENT '학생-이름',
  gender varchar(1) NOT NULL COMMENT '성별 (M | F)',
  age int NOT NULL,
  created_at datetime DEFAULT CURRENT_TIMESTAMP,
  
  PRIMARY KEY (id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci COMMENT='학생';

DROP TABLE jdbc_student;
SELECT * FROM jdbc_student;
DELETE FROM jdbc_student;


-- day01-08-02, day02-02
CREATE TABLE jdbc_user (
  user_id varchar(50) NOT NULL,
  user_name varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL,
  user_password varchar(200) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci DEFAULT NULL,
  
  PRIMARY KEY (user_id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

DROP TABLE jdbc_user;
SELECT * FROM jdbc_user;
DELETE FROM jdbc_user;


-- day-02-04
CREATE TABLE jdbc_account (
  account_number bigint unsigned NOT NULL COMMENT '계좌번호',
  name varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '고객_이름',
  balance bigint DEFAULT '0' COMMENT '잔고',
  
  PRIMARY KEY (account_number)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

DROP TABLE jdbc_account;
SELECT * FROM jdbc_account;
DELETE FROM jdbc_account;


-- day03
CREATE TABLE jdbc_club (
  club_id varchar(50) NOT NULL,
  club_name varchar(200) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL,
  club_created_at datetime DEFAULT CURRENT_TIMESTAMP,
  
  PRIMARY KEY (club_id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

CREATE TABLE jdbc_club_registrations (
  student_id varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci DEFAULT NULL COMMENT '학생_아이디',
  club_id varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci DEFAULT NULL COMMENT 'club_아이디',
  
  KEY club_registrations_pk (student_id,club_id),
  KEY fk_club_id (club_id),
  CONSTRAINT fk_club_id FOREIGN KEY (club_id) REFERENCES jdbc_club (club_id) ON DELETE RESTRICT ON UPDATE RESTRICT,
  CONSTRAINT fk_student_id FOREIGN KEY (student_id) REFERENCES jdbc_student (id) ON DELETE RESTRICT ON UPDATE RESTRICT
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

DROP TABLE jdbc_club;
SELECT * FROM jdbc_club;
DELETE FROM jdbc_club;

DROP TABLE jdbc_club_registrations;
SELECT * FROM jdbc_club_registrations;
DELETE FROM jdbc_club_registrations;