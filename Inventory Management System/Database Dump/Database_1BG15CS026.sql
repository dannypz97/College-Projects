--------------------------------------------------------
--  File created - Thursday-November-23-2017   
--------------------------------------------------------
--------------------------------------------------------
--  DDL for Table DEPARTMENT
--------------------------------------------------------

  CREATE TABLE "DPZ"."DEPARTMENT" 
   (	"DEPARTMENT_NAME" VARCHAR2(45 BYTE), 
	"DEPARTMENT_CODE" VARCHAR2(3 BYTE), 
	"LOCATION" VARCHAR2(45 BYTE), 
	"NUMBER_OF_ITEMS" NUMBER(*,0) DEFAULT 0
   ) SEGMENT CREATION IMMEDIATE 
  PCTFREE 10 PCTUSED 40 INITRANS 1 MAXTRANS 255 NOCOMPRESS LOGGING
  STORAGE(INITIAL 65536 NEXT 1048576 MINEXTENTS 1 MAXEXTENTS 2147483645
  PCTINCREASE 0 FREELISTS 1 FREELIST GROUPS 1 BUFFER_POOL DEFAULT FLASH_CACHE DEFAULT CELL_FLASH_CACHE DEFAULT)
  TABLESPACE "SYSTEM" ;
--------------------------------------------------------
--  DDL for Table IMS_USER
--------------------------------------------------------

  CREATE TABLE "DPZ"."IMS_USER" 
   (	"UNAME" VARCHAR2(20 BYTE), 
	"PASSWORD" VARCHAR2(15 BYTE), 
	"SYSTEM_ID" VARCHAR2(20 BYTE), 
	"CLEARANCE_LEVEL" NUMBER(*,0)
   ) SEGMENT CREATION IMMEDIATE 
  PCTFREE 10 PCTUSED 40 INITRANS 1 MAXTRANS 255 NOCOMPRESS LOGGING
  STORAGE(INITIAL 65536 NEXT 1048576 MINEXTENTS 1 MAXEXTENTS 2147483645
  PCTINCREASE 0 FREELISTS 1 FREELIST GROUPS 1 BUFFER_POOL DEFAULT FLASH_CACHE DEFAULT CELL_FLASH_CACHE DEFAULT)
  TABLESPACE "SYSTEM" ;
--------------------------------------------------------
--  DDL for Table ORDERS
--------------------------------------------------------

  CREATE TABLE "DPZ"."ORDERS" 
   (	"ORDER_ID" VARCHAR2(45 BYTE), 
	"TIME_OF_ORDER" DATE, 
	"VENDOR_ID" VARCHAR2(45 BYTE), 
	"STATUS" VARCHAR2(10 BYTE)
   ) SEGMENT CREATION IMMEDIATE 
  PCTFREE 10 PCTUSED 40 INITRANS 1 MAXTRANS 255 NOCOMPRESS LOGGING
  STORAGE(INITIAL 65536 NEXT 1048576 MINEXTENTS 1 MAXEXTENTS 2147483645
  PCTINCREASE 0 FREELISTS 1 FREELIST GROUPS 1 BUFFER_POOL DEFAULT FLASH_CACHE DEFAULT CELL_FLASH_CACHE DEFAULT)
  TABLESPACE "SYSTEM" ;
--------------------------------------------------------
--  DDL for Table ORDER_ITEMS
--------------------------------------------------------

  CREATE TABLE "DPZ"."ORDER_ITEMS" 
   (	"PRODUCT_NAME" VARCHAR2(75 BYTE), 
	"QUANTITY" NUMBER(10,0), 
	"UNITS" VARCHAR2(6 BYTE) DEFAULT NULL, 
	"ORDER_ID" VARCHAR2(45 BYTE)
   ) SEGMENT CREATION IMMEDIATE 
  PCTFREE 10 PCTUSED 40 INITRANS 1 MAXTRANS 255 NOCOMPRESS LOGGING
  STORAGE(INITIAL 65536 NEXT 1048576 MINEXTENTS 1 MAXEXTENTS 2147483645
  PCTINCREASE 0 FREELISTS 1 FREELIST GROUPS 1 BUFFER_POOL DEFAULT FLASH_CACHE DEFAULT CELL_FLASH_CACHE DEFAULT)
  TABLESPACE "SYSTEM" ;
--------------------------------------------------------
--  DDL for Table STOCK_ITEMS
--------------------------------------------------------

  CREATE TABLE "DPZ"."STOCK_ITEMS" 
   (	"SKU" VARCHAR2(120 BYTE), 
	"DELIVERY_REF_ID" VARCHAR2(45 BYTE), 
	"QUANTITY" NUMBER(10,0), 
	"DEPT_CODE" VARCHAR2(3 BYTE)
   ) SEGMENT CREATION IMMEDIATE 
  PCTFREE 10 PCTUSED 40 INITRANS 1 MAXTRANS 255 NOCOMPRESS LOGGING
  STORAGE(INITIAL 65536 NEXT 1048576 MINEXTENTS 1 MAXEXTENTS 2147483645
  PCTINCREASE 0 FREELISTS 1 FREELIST GROUPS 1 BUFFER_POOL DEFAULT FLASH_CACHE DEFAULT CELL_FLASH_CACHE DEFAULT)
  TABLESPACE "SYSTEM" ;
--------------------------------------------------------
--  DDL for Table VENDOR
--------------------------------------------------------

  CREATE TABLE "DPZ"."VENDOR" 
   (	"VENDOR_NAME" VARCHAR2(45 BYTE), 
	"VENDOR_ID" VARCHAR2(45 BYTE), 
	"ADDRESS" VARCHAR2(130 BYTE), 
	"CONTACT_NUMBER" NUMBER(19,0) DEFAULT NULL
   ) SEGMENT CREATION IMMEDIATE 
  PCTFREE 10 PCTUSED 40 INITRANS 1 MAXTRANS 255 NOCOMPRESS LOGGING
  STORAGE(INITIAL 65536 NEXT 1048576 MINEXTENTS 1 MAXEXTENTS 2147483645
  PCTINCREASE 0 FREELISTS 1 FREELIST GROUPS 1 BUFFER_POOL DEFAULT FLASH_CACHE DEFAULT CELL_FLASH_CACHE DEFAULT)
  TABLESPACE "SYSTEM" ;
--------------------------------------------------------
--  DDL for Table WAREHOUSE_DELIVERY
--------------------------------------------------------

  CREATE TABLE "DPZ"."WAREHOUSE_DELIVERY" 
   (	"DELIVERY_ID" VARCHAR2(45 BYTE), 
	"TIME_OF_DELIVERY" DATE, 
	"ORDER_REF_ID" VARCHAR2(45 BYTE), 
	"STATUS" VARCHAR2(20 BYTE)
   ) SEGMENT CREATION IMMEDIATE 
  PCTFREE 10 PCTUSED 40 INITRANS 1 MAXTRANS 255 NOCOMPRESS LOGGING
  STORAGE(INITIAL 65536 NEXT 1048576 MINEXTENTS 1 MAXEXTENTS 2147483645
  PCTINCREASE 0 FREELISTS 1 FREELIST GROUPS 1 BUFFER_POOL DEFAULT FLASH_CACHE DEFAULT CELL_FLASH_CACHE DEFAULT)
  TABLESPACE "SYSTEM" ;
--------------------------------------------------------
--  DDL for Table WAREHOUSE_DELIVERY_ITEMS
--------------------------------------------------------

  CREATE TABLE "DPZ"."WAREHOUSE_DELIVERY_ITEMS" 
   (	"ITEM_NAME" VARCHAR2(45 BYTE), 
	"QUANTITY" NUMBER(10,0), 
	"UNITS" VARCHAR2(6 BYTE), 
	"BATCH_PRICE" NUMBER(10,2), 
	"DELIVERY_ID" VARCHAR2(45 BYTE), 
	"PRODUCT_TYPE_CODE" VARCHAR2(5 BYTE)
   ) SEGMENT CREATION IMMEDIATE 
  PCTFREE 10 PCTUSED 40 INITRANS 1 MAXTRANS 255 NOCOMPRESS LOGGING
  STORAGE(INITIAL 65536 NEXT 1048576 MINEXTENTS 1 MAXEXTENTS 2147483645
  PCTINCREASE 0 FREELISTS 1 FREELIST GROUPS 1 BUFFER_POOL DEFAULT FLASH_CACHE DEFAULT CELL_FLASH_CACHE DEFAULT)
  TABLESPACE "SYSTEM" ;
--------------------------------------------------------
--  DDL for Sequence DELIVERYSEQ
--------------------------------------------------------

   CREATE SEQUENCE  "DPZ"."DELIVERYSEQ"  MINVALUE 1 MAXVALUE 9999999999999999999999999999 INCREMENT BY 1 START WITH 41 CACHE 20 NOORDER  NOCYCLE ;
--------------------------------------------------------
--  DDL for Sequence ORDERSEQ
--------------------------------------------------------

   CREATE SEQUENCE  "DPZ"."ORDERSEQ"  MINVALUE 1 MAXVALUE 9999999999999999999999999999 INCREMENT BY 1 START WITH 47 CACHE 20 NOORDER  NOCYCLE ;
REM INSERTING into DPZ.DEPARTMENT
SET DEFINE OFF;
Insert into DPZ.DEPARTMENT (DEPARTMENT_NAME,DEPARTMENT_CODE,LOCATION,NUMBER_OF_ITEMS) values ('Apparel_L','apl','a1',1);
Insert into DPZ.DEPARTMENT (DEPARTMENT_NAME,DEPARTMENT_CODE,LOCATION,NUMBER_OF_ITEMS) values ('Apparel_U','apu','a2',1);
Insert into DPZ.DEPARTMENT (DEPARTMENT_NAME,DEPARTMENT_CODE,LOCATION,NUMBER_OF_ITEMS) values ('Electronics_S','ecs','b1',2);
Insert into DPZ.DEPARTMENT (DEPARTMENT_NAME,DEPARTMENT_CODE,LOCATION,NUMBER_OF_ITEMS) values ('Electronics_L','ecl','b2',1);
Insert into DPZ.DEPARTMENT (DEPARTMENT_NAME,DEPARTMENT_CODE,LOCATION,NUMBER_OF_ITEMS) values ('new','n','x4',5);
REM INSERTING into DPZ.IMS_USER
SET DEFINE OFF;
Insert into DPZ.IMS_USER (UNAME,PASSWORD,SYSTEM_ID,CLEARANCE_LEVEL) values ('admin','admin','#SYS034',1);
Insert into DPZ.IMS_USER (UNAME,PASSWORD,SYSTEM_ID,CLEARANCE_LEVEL) values ('root','root','#SYS010',2);
Insert into DPZ.IMS_USER (UNAME,PASSWORD,SYSTEM_ID,CLEARANCE_LEVEL) values ('sys','sys','#SYS005',2);
REM INSERTING into DPZ.ORDERS
SET DEFINE OFF;
Insert into DPZ.ORDERS (ORDER_ID,TIME_OF_ORDER,VENDOR_ID,STATUS) values ('#OR0001',to_date('02-OCT-17 03:02:00','DD-MON-RR HH24:MI:SS'),'#VI0004','Completed');
Insert into DPZ.ORDERS (ORDER_ID,TIME_OF_ORDER,VENDOR_ID,STATUS) values ('#OR0006',to_date('07-DEC-17 01:45:00','DD-MON-RR HH24:MI:SS'),'#VI0007','Pending');
Insert into DPZ.ORDERS (ORDER_ID,TIME_OF_ORDER,VENDOR_ID,STATUS) values ('#OR0002',to_date('02-OCT-17 06:45:00','DD-MON-RR HH24:MI:SS'),'#VI0002','Completed');
Insert into DPZ.ORDERS (ORDER_ID,TIME_OF_ORDER,VENDOR_ID,STATUS) values ('#OR0003',to_date('03-OCT-17 02:15:00','DD-MON-RR HH24:MI:SS'),'#VI0004','Pending');
Insert into DPZ.ORDERS (ORDER_ID,TIME_OF_ORDER,VENDOR_ID,STATUS) values ('#OR0004',to_date('03-OCT-17 05:30:00','DD-MON-RR HH24:MI:SS'),'#VI0007','Completed');
Insert into DPZ.ORDERS (ORDER_ID,TIME_OF_ORDER,VENDOR_ID,STATUS) values ('#OR0005',to_date('07-DEC-17 04:45:00','DD-MON-RR HH24:MI:SS'),'#VI0006','Pending');
Insert into DPZ.ORDERS (ORDER_ID,TIME_OF_ORDER,VENDOR_ID,STATUS) values ('#OR0027',to_date('16-NOV-17 09:03:00','DD-MON-RR HH24:MI:SS'),'#VI0002','Pending');
REM INSERTING into DPZ.ORDER_ITEMS
SET DEFINE OFF;
Insert into DPZ.ORDER_ITEMS (PRODUCT_NAME,QUANTITY,UNITS,ORDER_ID) values ('Inspiron 15 3552 Laptop Black',7,'pcs','#OR0002');
Insert into DPZ.ORDER_ITEMS (PRODUCT_NAME,QUANTITY,UNITS,ORDER_ID) values ('Inspiron 3252 Small Desktop MetalGrey ',5,'pcs','#OR0002');
Insert into DPZ.ORDER_ITEMS (PRODUCT_NAME,QUANTITY,UNITS,ORDER_ID) values ('New Inspiron 24 3464 All-in-One Red',2,'pcs','#OR0002');
Insert into DPZ.ORDER_ITEMS (PRODUCT_NAME,QUANTITY,UNITS,ORDER_ID) values ('Panasonic rp-ht211 headphones Maroon',5,'pcs','#OR0003');
Insert into DPZ.ORDER_ITEMS (PRODUCT_NAME,QUANTITY,UNITS,ORDER_ID) values ('Pansonic bt-50gcs earbuds Blue',3,'pcs','#OR0003');
Insert into DPZ.ORDER_ITEMS (PRODUCT_NAME,QUANTITY,UNITS,ORDER_ID) values ('Samsung Galaxy On5 Gold',10,'pcs','#OR0004');
Insert into DPZ.ORDER_ITEMS (PRODUCT_NAME,QUANTITY,UNITS,ORDER_ID) values ('HP 240 G6 Notebook Black',5,'pcs','#OR0005');
Insert into DPZ.ORDER_ITEMS (PRODUCT_NAME,QUANTITY,UNITS,ORDER_ID) values ('HP 24es 60.45 cm(23.8) Display MetalGrey',5,'pcs','#OR0005');
Insert into DPZ.ORDER_ITEMS (PRODUCT_NAME,QUANTITY,UNITS,ORDER_ID) values ('Mens Colorblock Cotton Pique Red',10,'pcs','#OR0001');
Insert into DPZ.ORDER_ITEMS (PRODUCT_NAME,QUANTITY,UNITS,ORDER_ID) values ('Mens Slim Fit Polo Shirt Blue',15,'pcs','#OR0001');
Insert into DPZ.ORDER_ITEMS (PRODUCT_NAME,QUANTITY,UNITS,ORDER_ID) values ('Mens Adidas Adi Classic Backpack Black',25,'pcs','#OR0006');
Insert into DPZ.ORDER_ITEMS (PRODUCT_NAME,QUANTITY,UNITS,ORDER_ID) values ('Unisex Adidas Blackcat Cap Black',25,'pcs','#OR0006');
Insert into DPZ.ORDER_ITEMS (PRODUCT_NAME,QUANTITY,UNITS,ORDER_ID) values ('Unisex Adidas Running Ankle Socks Blue',20,'pairs','#OR0006');
Insert into DPZ.ORDER_ITEMS (PRODUCT_NAME,QUANTITY,UNITS,ORDER_ID) values ('Dell Latitude 12 Black',15,'pcs','#OR0027');
REM INSERTING into DPZ.STOCK_ITEMS
SET DEFINE OFF;
Insert into DPZ.STOCK_ITEMS (SKU,DELIVERY_REF_ID,QUANTITY,DEPT_CODE) values ('Panasonic/#OR0001/Mens Slim Fit Polo Shirt Blue/15/''14-Nov-17 19:51''','#DV0001',14,'apu');
Insert into DPZ.STOCK_ITEMS (SKU,DELIVERY_REF_ID,QUANTITY,DEPT_CODE) values ('Dell/#OR0002/Inspiron 15 3552 Laptop Black/7/''14-Nov-17 19:51''','#DV0002',7,'ecl');
Insert into DPZ.STOCK_ITEMS (SKU,DELIVERY_REF_ID,QUANTITY,DEPT_CODE) values ('Samsung/#OR0004/Samsung Galaxy On5 Gold/10/''14-Nov-17 19:52''','#DV0006',10,'ecs');
Insert into DPZ.STOCK_ITEMS (SKU,DELIVERY_REF_ID,QUANTITY,DEPT_CODE) values ('Samsung/#OR0004/Samsung Galaxy Note 8 (sm-910c) White/45/''14-Nov-17 19:52''','#DV0006',15,'ecs');
REM INSERTING into DPZ.VENDOR
SET DEFINE OFF;
Insert into DPZ.VENDOR (VENDOR_NAME,VENDOR_ID,ADDRESS,CONTACT_NUMBER) values ('Dell','#VI0002','No.15, Ground Floor, Wood Street, Richmond Road, Bangalore - 560025',8557546712);
Insert into DPZ.VENDOR (VENDOR_NAME,VENDOR_ID,ADDRESS,CONTACT_NUMBER) values ('Adidas','#VI0003','No 10/A, Chandra Kiran Building, Kasturba Road, Bangalore - 560001',8444319661);
Insert into DPZ.VENDOR (VENDOR_NAME,VENDOR_ID,ADDRESS,CONTACT_NUMBER) values ('Panasonic','#VI0004','No. 124, Silver Jubilee Park Road, S.P. Road, Bangalore - 560002',9731611987);
Insert into DPZ.VENDOR (VENDOR_NAME,VENDOR_ID,ADDRESS,CONTACT_NUMBER) values ('Reebok','#VI0005','690, Next To-Sony World, 80 Feet Rd, Koramangala 4th Block, Koramangala, Bengaluru, Karnataka 560095',8786879000);
Insert into DPZ.VENDOR (VENDOR_NAME,VENDOR_ID,ADDRESS,CONTACT_NUMBER) values ('HPE','#VI0006','2 FL, No.24, Salarpuria Arena Building, Hosur Main Road, Adugodi, Chikku Lakshmaiah Layout, Adugodi, Bengaluru, Karnataka 560030',8898112332);
Insert into DPZ.VENDOR (VENDOR_NAME,VENDOR_ID,ADDRESS,CONTACT_NUMBER) values ('Samsung','#VI0007','# 1000 Sri vari Plaza, 80 Feet Road, 7th main Jakasandra, Koramangala, Bangalore - 560034',8861768798);
Insert into DPZ.VENDOR (VENDOR_NAME,VENDOR_ID,ADDRESS,CONTACT_NUMBER) values ('Lacoste','#VI0001','No 8/A, 22nd Main, 3rd Cross, Srinagar, Banashankari 1st Stage, Bangalore - 56005',9898009765);
REM INSERTING into DPZ.WAREHOUSE_DELIVERY
SET DEFINE OFF;
Insert into DPZ.WAREHOUSE_DELIVERY (DELIVERY_ID,TIME_OF_DELIVERY,ORDER_REF_ID,STATUS) values ('#DV0002',to_date('14-NOV-17 19:44:00','DD-MON-RR HH24:MI:SS'),'#OR0002','Stocked');
Insert into DPZ.WAREHOUSE_DELIVERY (DELIVERY_ID,TIME_OF_DELIVERY,ORDER_REF_ID,STATUS) values ('#DV0003',to_date('14-NOV-17 19:44:00','DD-MON-RR HH24:MI:SS'),'#OR0003','Arrived');
Insert into DPZ.WAREHOUSE_DELIVERY (DELIVERY_ID,TIME_OF_DELIVERY,ORDER_REF_ID,STATUS) values ('#DV0004',to_date('14-NOV-17 19:44:00','DD-MON-RR HH24:MI:SS'),'#OR0005','Arrived');
Insert into DPZ.WAREHOUSE_DELIVERY (DELIVERY_ID,TIME_OF_DELIVERY,ORDER_REF_ID,STATUS) values ('#DV0005',to_date('14-NOV-17 19:44:00','DD-MON-RR HH24:MI:SS'),'#OR0006','Arrived');
Insert into DPZ.WAREHOUSE_DELIVERY (DELIVERY_ID,TIME_OF_DELIVERY,ORDER_REF_ID,STATUS) values ('#DV0001',to_date('14-NOV-17 19:44:00','DD-MON-RR HH24:MI:SS'),'#OR0001','Stocked');
Insert into DPZ.WAREHOUSE_DELIVERY (DELIVERY_ID,TIME_OF_DELIVERY,ORDER_REF_ID,STATUS) values ('#DV0006',to_date('14-NOV-17 19:44:00','DD-MON-RR HH24:MI:SS'),'#OR0004','Stocked');
Insert into DPZ.WAREHOUSE_DELIVERY (DELIVERY_ID,TIME_OF_DELIVERY,ORDER_REF_ID,STATUS) values ('#DV0021',to_date('16-NOV-17 09:33:00','DD-MON-RR HH24:MI:SS'),'#OR0027','Arrived');
REM INSERTING into DPZ.WAREHOUSE_DELIVERY_ITEMS
SET DEFINE OFF;
Insert into DPZ.WAREHOUSE_DELIVERY_ITEMS (ITEM_NAME,QUANTITY,UNITS,BATCH_PRICE,DELIVERY_ID,PRODUCT_TYPE_CODE) values ('Mens Slim Fit Polo Shirt Blue',15,'pcs',9500,'#DV0001','apu');
Insert into DPZ.WAREHOUSE_DELIVERY_ITEMS (ITEM_NAME,QUANTITY,UNITS,BATCH_PRICE,DELIVERY_ID,PRODUCT_TYPE_CODE) values ('Inspiron 15 3552 Laptop Black',7,'pcs',525000,'#DV0002','ecl');
Insert into DPZ.WAREHOUSE_DELIVERY_ITEMS (ITEM_NAME,QUANTITY,UNITS,BATCH_PRICE,DELIVERY_ID,PRODUCT_TYPE_CODE) values ('Inspiron 3252 Small Desktop MetalGrey ',5,'pcs',600000,'#DV0002','ecl');
Insert into DPZ.WAREHOUSE_DELIVERY_ITEMS (ITEM_NAME,QUANTITY,UNITS,BATCH_PRICE,DELIVERY_ID,PRODUCT_TYPE_CODE) values ('! New Inspiron 24 3464 All-in-One Red',2,'pcs',25000,'#DV0002','ecl');
Insert into DPZ.WAREHOUSE_DELIVERY_ITEMS (ITEM_NAME,QUANTITY,UNITS,BATCH_PRICE,DELIVERY_ID,PRODUCT_TYPE_CODE) values ('Panasonic rp-ht211 headphones Maroon',5,'pcs',0,'#DV0003','x');
Insert into DPZ.WAREHOUSE_DELIVERY_ITEMS (ITEM_NAME,QUANTITY,UNITS,BATCH_PRICE,DELIVERY_ID,PRODUCT_TYPE_CODE) values ('Pansonic bt-50gcs earbuds Blue',3,'pcs',0,'#DV0003','x');
Insert into DPZ.WAREHOUSE_DELIVERY_ITEMS (ITEM_NAME,QUANTITY,UNITS,BATCH_PRICE,DELIVERY_ID,PRODUCT_TYPE_CODE) values ('HP 240 G6 Notebook Black',5,'pcs',0,'#DV0004','x');
Insert into DPZ.WAREHOUSE_DELIVERY_ITEMS (ITEM_NAME,QUANTITY,UNITS,BATCH_PRICE,DELIVERY_ID,PRODUCT_TYPE_CODE) values ('Mens Colorblock Cotton Pique Red',10,'pcs',1800,'#DV0001','apu');
Insert into DPZ.WAREHOUSE_DELIVERY_ITEMS (ITEM_NAME,QUANTITY,UNITS,BATCH_PRICE,DELIVERY_ID,PRODUCT_TYPE_CODE) values ('HP 24es 60.45 cm(23.8) Display MetalGrey',5,'pcs',0,'#DV0004','x');
Insert into DPZ.WAREHOUSE_DELIVERY_ITEMS (ITEM_NAME,QUANTITY,UNITS,BATCH_PRICE,DELIVERY_ID,PRODUCT_TYPE_CODE) values ('Mens Adidas Adi Classic Backpack Black',25,'pcs',0,'#DV0005','x');
Insert into DPZ.WAREHOUSE_DELIVERY_ITEMS (ITEM_NAME,QUANTITY,UNITS,BATCH_PRICE,DELIVERY_ID,PRODUCT_TYPE_CODE) values ('Unisex Adidas Blackcat Cap Black',25,'pcs',0,'#DV0005','x');
Insert into DPZ.WAREHOUSE_DELIVERY_ITEMS (ITEM_NAME,QUANTITY,UNITS,BATCH_PRICE,DELIVERY_ID,PRODUCT_TYPE_CODE) values ('Unisex Adidas Running Ankle Socks Blue',20,'pairs',0,'#DV0005','x');
Insert into DPZ.WAREHOUSE_DELIVERY_ITEMS (ITEM_NAME,QUANTITY,UNITS,BATCH_PRICE,DELIVERY_ID,PRODUCT_TYPE_CODE) values ('Samsung Galaxy Note 8 (sm-910c) White',45,'pcs',3600000,'#DV0006','ecs');
Insert into DPZ.WAREHOUSE_DELIVERY_ITEMS (ITEM_NAME,QUANTITY,UNITS,BATCH_PRICE,DELIVERY_ID,PRODUCT_TYPE_CODE) values ('Samsung Galaxy On5 Gold',10,'pcs',100000,'#DV0006','ecs');
Insert into DPZ.WAREHOUSE_DELIVERY_ITEMS (ITEM_NAME,QUANTITY,UNITS,BATCH_PRICE,DELIVERY_ID,PRODUCT_TYPE_CODE) values ('Dell Latitude 12 Black',15,'pcs',0,'#DV0021','x');
--------------------------------------------------------
--  DDL for Index WAREHOUSE_DELIVERY_ITEMS_UK1
--------------------------------------------------------

  CREATE UNIQUE INDEX "DPZ"."WAREHOUSE_DELIVERY_ITEMS_UK1" ON "DPZ"."WAREHOUSE_DELIVERY_ITEMS" ("ITEM_NAME") 
  PCTFREE 10 INITRANS 2 MAXTRANS 255 COMPUTE STATISTICS 
  STORAGE(INITIAL 65536 NEXT 1048576 MINEXTENTS 1 MAXEXTENTS 2147483645
  PCTINCREASE 0 FREELISTS 1 FREELIST GROUPS 1 BUFFER_POOL DEFAULT FLASH_CACHE DEFAULT CELL_FLASH_CACHE DEFAULT)
  TABLESPACE "SYSTEM" ;
--------------------------------------------------------
--  DDL for Index DEPT_UNIQUE
--------------------------------------------------------

  CREATE UNIQUE INDEX "DPZ"."DEPT_UNIQUE" ON "DPZ"."DEPARTMENT" ("DEPARTMENT_NAME", "LOCATION") 
  PCTFREE 10 INITRANS 2 MAXTRANS 255 COMPUTE STATISTICS 
  STORAGE(INITIAL 65536 NEXT 1048576 MINEXTENTS 1 MAXEXTENTS 2147483645
  PCTINCREASE 0 FREELISTS 1 FREELIST GROUPS 1 BUFFER_POOL DEFAULT FLASH_CACHE DEFAULT CELL_FLASH_CACHE DEFAULT)
  TABLESPACE "SYSTEM" ;
--------------------------------------------------------
--  DDL for Index SYS_C007000
--------------------------------------------------------

  CREATE UNIQUE INDEX "DPZ"."SYS_C007000" ON "DPZ"."VENDOR" ("VENDOR_ID") 
  PCTFREE 10 INITRANS 2 MAXTRANS 255 COMPUTE STATISTICS 
  STORAGE(INITIAL 65536 NEXT 1048576 MINEXTENTS 1 MAXEXTENTS 2147483645
  PCTINCREASE 0 FREELISTS 1 FREELIST GROUPS 1 BUFFER_POOL DEFAULT FLASH_CACHE DEFAULT CELL_FLASH_CACHE DEFAULT)
  TABLESPACE "SYSTEM" ;
--------------------------------------------------------
--  DDL for Index SYS_C007003
--------------------------------------------------------

  CREATE UNIQUE INDEX "DPZ"."SYS_C007003" ON "DPZ"."IMS_USER" ("UNAME", "SYSTEM_ID") 
  PCTFREE 10 INITRANS 2 MAXTRANS 255 COMPUTE STATISTICS 
  STORAGE(INITIAL 65536 NEXT 1048576 MINEXTENTS 1 MAXEXTENTS 2147483645
  PCTINCREASE 0 FREELISTS 1 FREELIST GROUPS 1 BUFFER_POOL DEFAULT FLASH_CACHE DEFAULT CELL_FLASH_CACHE DEFAULT)
  TABLESPACE "SYSTEM" ;
--------------------------------------------------------
--  DDL for Trigger GENERATENEWDELIVERYID
--------------------------------------------------------

  CREATE OR REPLACE TRIGGER "DPZ"."GENERATENEWDELIVERYID" 
before insert on warehouse_delivery
for each row
begin
  SELECT '#DV' || TO_CHAR(DELIVERYSEQ.NEXTVAL, 'FM0000') INTO :NEW.DELIVERY_ID FROM DUAL;
end;



/
ALTER TRIGGER "DPZ"."GENERATENEWDELIVERYID" ENABLE;
--------------------------------------------------------
--  DDL for Trigger GENERATENEWORDERID
--------------------------------------------------------

  CREATE OR REPLACE TRIGGER "DPZ"."GENERATENEWORDERID" 
before insert on orders
for each row

begin
     SELECT '#OR' || TO_CHAR(ORDERSEQ.NEXTVAL, 'FM0000') INTO :NEW.ORDER_ID FROM DUAL;
end;



/
ALTER TRIGGER "DPZ"."GENERATENEWORDERID" ENABLE;
--------------------------------------------------------
--  DDL for Trigger UPDATE_QTY
--------------------------------------------------------

  CREATE OR REPLACE TRIGGER "DPZ"."UPDATE_QTY" 
before insert on order_items
for each row
DECLARE 
   oldQty integer:=0;   
   product_name varchar2(45);
   pragma autonomous_transaction;
   CURSOR itemRows is
    select trim(upper(product_name)),quantity from order_items where trim(upper(product_name)) = trim(upper(:NEW.product_name))
    and order_id = :NEW.order_id;
begin
 open itemRows;

    fetch itemRows into product_name, oldQty;    

    if itemRows%rowcount = 1 then
         update order_items set quantity = oldQty + :NEW.quantity where trim(upper(PRODUCT_NAME)) = trim(upper(:NEW.product_name));
         commit;
         Raise_Application_Error (-20100, 'Item with same name already exists. Quantity of original item will be updated...');
    end if;

end;



/
ALTER TRIGGER "DPZ"."UPDATE_QTY" ENABLE;
--------------------------------------------------------
--  DDL for Procedure NUMOFITEMS
--------------------------------------------------------
set define off;

  CREATE OR REPLACE PROCEDURE "DPZ"."NUMOFITEMS" as 
num number;
dept_code varchar2(20);
CURSOR counts IS 
    select count(*), dept_code from stock_items 
    group by dept_code;
BEGIN
 open counts;
 loop
    fetch counts into num, dept_code;
    exit when counts%notfound;

    if counts%rowcount = 0 then
    update department set NUMBER_OF_ITEMS = 0;
    exit;
    end if;

    DBMS_OUTPUT.PUT_LINE(dept_code||' '||num);
    update department set NUMBER_OF_ITEMS = num where DEPARTMENT_CODE = dept_code; 
end loop;
end numofitems;




/
--------------------------------------------------------
--  Constraints for Table WAREHOUSE_DELIVERY
--------------------------------------------------------

  ALTER TABLE "DPZ"."WAREHOUSE_DELIVERY" MODIFY ("DELIVERY_ID" NOT NULL ENABLE);
  ALTER TABLE "DPZ"."WAREHOUSE_DELIVERY" MODIFY ("ORDER_REF_ID" NOT NULL ENABLE);
  ALTER TABLE "DPZ"."WAREHOUSE_DELIVERY" MODIFY ("TIME_OF_DELIVERY" NOT NULL ENABLE);
  ALTER TABLE "DPZ"."WAREHOUSE_DELIVERY" ADD PRIMARY KEY ("DELIVERY_ID")
  USING INDEX PCTFREE 10 INITRANS 2 MAXTRANS 255 COMPUTE STATISTICS 
  STORAGE(INITIAL 65536 NEXT 1048576 MINEXTENTS 1 MAXEXTENTS 2147483645
  PCTINCREASE 0 FREELISTS 1 FREELIST GROUPS 1 BUFFER_POOL DEFAULT FLASH_CACHE DEFAULT CELL_FLASH_CACHE DEFAULT)
  TABLESPACE "SYSTEM"  ENABLE;
  ALTER TABLE "DPZ"."WAREHOUSE_DELIVERY" MODIFY ("STATUS" NOT NULL ENABLE);
--------------------------------------------------------
--  Constraints for Table ORDERS
--------------------------------------------------------

  ALTER TABLE "DPZ"."ORDERS" MODIFY ("ORDER_ID" NOT NULL ENABLE);
  ALTER TABLE "DPZ"."ORDERS" MODIFY ("TIME_OF_ORDER" NOT NULL ENABLE);
  ALTER TABLE "DPZ"."ORDERS" MODIFY ("VENDOR_ID" NOT NULL ENABLE);
  ALTER TABLE "DPZ"."ORDERS" MODIFY ("STATUS" NOT NULL ENABLE);
  ALTER TABLE "DPZ"."ORDERS" ADD PRIMARY KEY ("ORDER_ID")
  USING INDEX PCTFREE 10 INITRANS 2 MAXTRANS 255 COMPUTE STATISTICS 
  STORAGE(INITIAL 65536 NEXT 1048576 MINEXTENTS 1 MAXEXTENTS 2147483645
  PCTINCREASE 0 FREELISTS 1 FREELIST GROUPS 1 BUFFER_POOL DEFAULT FLASH_CACHE DEFAULT CELL_FLASH_CACHE DEFAULT)
  TABLESPACE "SYSTEM"  ENABLE;
--------------------------------------------------------
--  Constraints for Table IMS_USER
--------------------------------------------------------

  ALTER TABLE "DPZ"."IMS_USER" MODIFY ("UNAME" NOT NULL ENABLE);
  ALTER TABLE "DPZ"."IMS_USER" MODIFY ("PASSWORD" NOT NULL ENABLE);
  ALTER TABLE "DPZ"."IMS_USER" MODIFY ("CLEARANCE_LEVEL" NOT NULL ENABLE);
  ALTER TABLE "DPZ"."IMS_USER" MODIFY ("SYSTEM_ID" NOT NULL ENABLE);
  ALTER TABLE "DPZ"."IMS_USER" ADD CONSTRAINT "SYS_C007003" PRIMARY KEY ("UNAME", "SYSTEM_ID")
  USING INDEX PCTFREE 10 INITRANS 2 MAXTRANS 255 COMPUTE STATISTICS 
  STORAGE(INITIAL 65536 NEXT 1048576 MINEXTENTS 1 MAXEXTENTS 2147483645
  PCTINCREASE 0 FREELISTS 1 FREELIST GROUPS 1 BUFFER_POOL DEFAULT FLASH_CACHE DEFAULT CELL_FLASH_CACHE DEFAULT)
  TABLESPACE "SYSTEM"  ENABLE;
--------------------------------------------------------
--  Constraints for Table VENDOR
--------------------------------------------------------

  ALTER TABLE "DPZ"."VENDOR" MODIFY ("VENDOR_NAME" NOT NULL ENABLE);
  ALTER TABLE "DPZ"."VENDOR" MODIFY ("VENDOR_ID" NOT NULL ENABLE);
  ALTER TABLE "DPZ"."VENDOR" MODIFY ("ADDRESS" NOT NULL ENABLE);
  ALTER TABLE "DPZ"."VENDOR" ADD CONSTRAINT "SYS_C007000" PRIMARY KEY ("VENDOR_ID")
  USING INDEX PCTFREE 10 INITRANS 2 MAXTRANS 255 COMPUTE STATISTICS 
  STORAGE(INITIAL 65536 NEXT 1048576 MINEXTENTS 1 MAXEXTENTS 2147483645
  PCTINCREASE 0 FREELISTS 1 FREELIST GROUPS 1 BUFFER_POOL DEFAULT FLASH_CACHE DEFAULT CELL_FLASH_CACHE DEFAULT)
  TABLESPACE "SYSTEM"  ENABLE;
--------------------------------------------------------
--  Constraints for Table WAREHOUSE_DELIVERY_ITEMS
--------------------------------------------------------

  ALTER TABLE "DPZ"."WAREHOUSE_DELIVERY_ITEMS" MODIFY ("ITEM_NAME" NOT NULL ENABLE);
  ALTER TABLE "DPZ"."WAREHOUSE_DELIVERY_ITEMS" MODIFY ("QUANTITY" NOT NULL ENABLE);
  ALTER TABLE "DPZ"."WAREHOUSE_DELIVERY_ITEMS" MODIFY ("UNITS" NOT NULL ENABLE);
  ALTER TABLE "DPZ"."WAREHOUSE_DELIVERY_ITEMS" MODIFY ("BATCH_PRICE" NOT NULL ENABLE);
  ALTER TABLE "DPZ"."WAREHOUSE_DELIVERY_ITEMS" MODIFY ("DELIVERY_ID" NOT NULL ENABLE);
  ALTER TABLE "DPZ"."WAREHOUSE_DELIVERY_ITEMS" MODIFY ("PRODUCT_TYPE_CODE" NOT NULL ENABLE);
  ALTER TABLE "DPZ"."WAREHOUSE_DELIVERY_ITEMS" ADD CONSTRAINT "WAREHOUSE_DELIVERY_ITEMS_PK" PRIMARY KEY ("ITEM_NAME")
  USING INDEX PCTFREE 10 INITRANS 2 MAXTRANS 255 COMPUTE STATISTICS 
  STORAGE(INITIAL 65536 NEXT 1048576 MINEXTENTS 1 MAXEXTENTS 2147483645
  PCTINCREASE 0 FREELISTS 1 FREELIST GROUPS 1 BUFFER_POOL DEFAULT FLASH_CACHE DEFAULT CELL_FLASH_CACHE DEFAULT)
  TABLESPACE "SYSTEM"  ENABLE;
--------------------------------------------------------
--  Constraints for Table ORDER_ITEMS
--------------------------------------------------------

  ALTER TABLE "DPZ"."ORDER_ITEMS" MODIFY ("PRODUCT_NAME" NOT NULL ENABLE);
  ALTER TABLE "DPZ"."ORDER_ITEMS" MODIFY ("QUANTITY" NOT NULL ENABLE);
  ALTER TABLE "DPZ"."ORDER_ITEMS" MODIFY ("ORDER_ID" NOT NULL ENABLE);
--------------------------------------------------------
--  Constraints for Table DEPARTMENT
--------------------------------------------------------

  ALTER TABLE "DPZ"."DEPARTMENT" MODIFY ("DEPARTMENT_NAME" NOT NULL ENABLE);
  ALTER TABLE "DPZ"."DEPARTMENT" MODIFY ("DEPARTMENT_CODE" NOT NULL ENABLE);
  ALTER TABLE "DPZ"."DEPARTMENT" MODIFY ("LOCATION" NOT NULL ENABLE);
  ALTER TABLE "DPZ"."DEPARTMENT" ADD PRIMARY KEY ("DEPARTMENT_CODE")
  USING INDEX PCTFREE 10 INITRANS 2 MAXTRANS 255 COMPUTE STATISTICS 
  STORAGE(INITIAL 65536 NEXT 1048576 MINEXTENTS 1 MAXEXTENTS 2147483645
  PCTINCREASE 0 FREELISTS 1 FREELIST GROUPS 1 BUFFER_POOL DEFAULT FLASH_CACHE DEFAULT CELL_FLASH_CACHE DEFAULT)
  TABLESPACE "SYSTEM"  ENABLE;
  ALTER TABLE "DPZ"."DEPARTMENT" ADD CONSTRAINT "DEPT_UNIQUE" UNIQUE ("DEPARTMENT_NAME", "LOCATION")
  USING INDEX PCTFREE 10 INITRANS 2 MAXTRANS 255 COMPUTE STATISTICS 
  STORAGE(INITIAL 65536 NEXT 1048576 MINEXTENTS 1 MAXEXTENTS 2147483645
  PCTINCREASE 0 FREELISTS 1 FREELIST GROUPS 1 BUFFER_POOL DEFAULT FLASH_CACHE DEFAULT CELL_FLASH_CACHE DEFAULT)
  TABLESPACE "SYSTEM"  ENABLE;
--------------------------------------------------------
--  Constraints for Table STOCK_ITEMS
--------------------------------------------------------

  ALTER TABLE "DPZ"."STOCK_ITEMS" MODIFY ("SKU" NOT NULL ENABLE);
  ALTER TABLE "DPZ"."STOCK_ITEMS" MODIFY ("DELIVERY_REF_ID" NOT NULL ENABLE);
  ALTER TABLE "DPZ"."STOCK_ITEMS" MODIFY ("QUANTITY" NOT NULL ENABLE);
  ALTER TABLE "DPZ"."STOCK_ITEMS" MODIFY ("DEPT_CODE" NOT NULL ENABLE);
  ALTER TABLE "DPZ"."STOCK_ITEMS" ADD PRIMARY KEY ("SKU")
  USING INDEX PCTFREE 10 INITRANS 2 MAXTRANS 255 COMPUTE STATISTICS 
  STORAGE(INITIAL 65536 NEXT 1048576 MINEXTENTS 1 MAXEXTENTS 2147483645
  PCTINCREASE 0 FREELISTS 1 FREELIST GROUPS 1 BUFFER_POOL DEFAULT FLASH_CACHE DEFAULT CELL_FLASH_CACHE DEFAULT)
  TABLESPACE "SYSTEM"  ENABLE;
--------------------------------------------------------
--  Ref Constraints for Table ORDER_ITEMS
--------------------------------------------------------

  ALTER TABLE "DPZ"."ORDER_ITEMS" ADD CONSTRAINT "ORDER_ID_FK" FOREIGN KEY ("ORDER_ID")
	  REFERENCES "DPZ"."ORDERS" ("ORDER_ID") ON DELETE CASCADE ENABLE;
--------------------------------------------------------
--  Ref Constraints for Table STOCK_ITEMS
--------------------------------------------------------

  ALTER TABLE "DPZ"."STOCK_ITEMS" ADD CONSTRAINT "DELIVERY_ID_REF_FK" FOREIGN KEY ("DELIVERY_REF_ID")
	  REFERENCES "DPZ"."WAREHOUSE_DELIVERY" ("DELIVERY_ID") ENABLE;
  ALTER TABLE "DPZ"."STOCK_ITEMS" ADD CONSTRAINT "DEPT_FK" FOREIGN KEY ("DEPT_CODE")
	  REFERENCES "DPZ"."DEPARTMENT" ("DEPARTMENT_CODE") ENABLE;
--------------------------------------------------------
--  Ref Constraints for Table WAREHOUSE_DELIVERY
--------------------------------------------------------

  ALTER TABLE "DPZ"."WAREHOUSE_DELIVERY" ADD CONSTRAINT "ORDER_REF_ID_FK" FOREIGN KEY ("ORDER_REF_ID")
	  REFERENCES "DPZ"."ORDERS" ("ORDER_ID") ENABLE;
--------------------------------------------------------
--  Ref Constraints for Table WAREHOUSE_DELIVERY_ITEMS
--------------------------------------------------------

  ALTER TABLE "DPZ"."WAREHOUSE_DELIVERY_ITEMS" ADD CONSTRAINT "DELIVERY_ID_FK" FOREIGN KEY ("DELIVERY_ID")
	  REFERENCES "DPZ"."WAREHOUSE_DELIVERY" ("DELIVERY_ID") ON DELETE CASCADE ENABLE;
