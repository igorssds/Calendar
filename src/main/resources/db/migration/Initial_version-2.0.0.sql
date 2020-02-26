-- RODAR EM UAT
--DROP TABLE HOLLIDAY_DATE;
--DROP TABLE HOLLIDAY;
--DROP TABLE CITY;
--DROP TABLE STATE;
--DROP TABLE COUNTRY;

CREATE TABLE "HOLIDAY" 
(
	"ID" NUMBER(10,0) NOT NULL,
	"NAME" VARCHAR2(200) NOT NULL,
	"NAME_PLAIN_TEXT" VARCHAR2(200) NOT NULL,
	"HOLIDAY_DATE" DATE NOT NULL,
	"CITY_NAME" VARCHAR2(100) NULL,
	"CITY_NAME_PLAIN_TEXT" VARCHAR2(100) NULL, 
	"STATE_NAME" VARCHAR2(100) NULL,
	"STATE_CODE" VARCHAR(2) NULL,
	"COUNTRY_NAME" VARCHAR(100) NULL,
	"COUNTRY_CODE" VARCHAR(3) NULL,  
	"ACTIVE" NUMBER(1,0) NOT NULL,
	"CREATION_DATE" TIMESTAMP NOT NULL,
	"UPDATE_DATE" TIMESTAMP NOT NULL,
	CONSTRAINT "PK_HOLIDAY" PRIMARY KEY ("ID")
);

CREATE SEQUENCE "HOLIDAY_SEQUENCE" START WITH 1 INCREMENT BY 1;
