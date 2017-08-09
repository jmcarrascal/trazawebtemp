# -*- coding: utf-8; -*-
import sys
import commands
import os


path="/tmp"
schema = sys.argv[1]
file_conf= open(path + os.sep + "insert_combos.sql", "w+")

script = '''
USE {schema}; 

DELETE FROM `impuestos` WHERE 1;
INSERT INTO `impuestos` (`Nr`,`Descripcion`,`Alicuota`,`SobreAlicuota`,`Percepcion`,`Uso`,`Minimo`) VALUES (1,'Iva Inscripto 21%',21.00,10.50,5.00,0,-999999999.9900);
INSERT INTO `impuestos` (`Nr`,`Descripcion`,`Alicuota`,`SobreAlicuota`,`Percepcion`,`Uso`,`Minimo`) VALUES (2,'Iva Inscripto 27%',27.00,0.00,0.00,2,-999999999.9900);
INSERT INTO `impuestos` (`Nr`,`Descripcion`,`Alicuota`,`SobreAlicuota`,`Percepcion`,`Uso`,`Minimo`) VALUES (3,'Iva Exento',0.00,0.00,0.00,0,-999999999.9900);
INSERT INTO `impuestos` (`Nr`,`Descripcion`,`Alicuota`,`SobreAlicuota`,`Percepcion`,`Uso`,`Minimo`) VALUES (4,'Iva Inscripto 10.5%',10.50,0.00,0.00,0,-999999999.9900);
INSERT INTO `impuestos` (`Nr`,`Descripcion`,`Alicuota`,`SobreAlicuota`,`Percepcion`,`Uso`,`Minimo`) VALUES (5,'(Iva Libre)',0.00,0.00,0.00,0,-999999999.9900);
INSERT INTO `impuestos` (`Nr`,`Descripcion`,`Alicuota`,`SobreAlicuota`,`Percepcion`,`Uso`,`Minimo`) VALUES (6,'(libre)',0.00,0.00,0.00,0,-999999999.9900);
INSERT INTO `impuestos` (`Nr`,`Descripcion`,`Alicuota`,`SobreAlicuota`,`Percepcion`,`Uso`,`Minimo`) VALUES (7,'(libre)',0.00,0.00,0.00,0,-999999999.9900);
INSERT INTO `impuestos` (`Nr`,`Descripcion`,`Alicuota`,`SobreAlicuota`,`Percepcion`,`Uso`,`Minimo`) VALUES (8,'Ret IngBrutos',0.00,0.00,0.00,0,-999999999.9900);
INSERT INTO `impuestos` (`Nr`,`Descripcion`,`Alicuota`,`SobreAlicuota`,`Percepcion`,`Uso`,`Minimo`) VALUES (9,'Ret O.Social',0.00,0.00,0.00,0,-999999999.9900);
INSERT INTO `impuestos` (`Nr`,`Descripcion`,`Alicuota`,`SobreAlicuota`,`Percepcion`,`Uso`,`Minimo`) VALUES (10,'Internos 1.01%',0.00,0.00,0.00,0,-999999999.9900);
INSERT INTO `impuestos` (`Nr`,`Descripcion`,`Alicuota`,`SobreAlicuota`,`Percepcion`,`Uso`,`Minimo`) VALUES (11,'Internos 7%',7.00,0.00,0.00,0,-999999999.9900);
INSERT INTO `impuestos` (`Nr`,`Descripcion`,`Alicuota`,`SobreAlicuota`,`Percepcion`,`Uso`,`Minimo`) VALUES (12,'Internos Exento',0.00,0.00,0.00,0,-999999999.9900);
INSERT INTO `impuestos` (`Nr`,`Descripcion`,`Alicuota`,`SobreAlicuota`,`Percepcion`,`Uso`,`Minimo`) VALUES (13,'(Libre)',0.00,0.00,0.00,0,999999999.0000);
INSERT INTO `impuestos` (`Nr`,`Descripcion`,`Alicuota`,`SobreAlicuota`,`Percepcion`,`Uso`,`Minimo`) VALUES (14,'(Libre)',0.00,0.00,0.00,0,999999999.0000);
INSERT INTO `impuestos` (`Nr`,`Descripcion`,`Alicuota`,`SobreAlicuota`,`Percepcion`,`Uso`,`Minimo`) VALUES (15,'(Libre)',0.00,0.00,0.00,0,999999999.0000);
INSERT INTO `impuestos` (`Nr`,`Descripcion`,`Alicuota`,`SobreAlicuota`,`Percepcion`,`Uso`,`Minimo`) VALUES (16,'(Libre)',0.00,0.00,0.00,0,999999999.0000);
INSERT INTO `impuestos` (`Nr`,`Descripcion`,`Alicuota`,`SobreAlicuota`,`Percepcion`,`Uso`,`Minimo`) VALUES (17,'(Libre)',0.00,0.00,0.00,0,999999999.0000);
INSERT INTO `impuestos` (`Nr`,`Descripcion`,`Alicuota`,`SobreAlicuota`,`Percepcion`,`Uso`,`Minimo`) VALUES (18,'Otros Imp 1',1.00,0.00,0.00,0,999999999.0000);
INSERT INTO `impuestos` (`Nr`,`Descripcion`,`Alicuota`,`SobreAlicuota`,`Percepcion`,`Uso`,`Minimo`) VALUES (19,'Otros Imp 2',2.00,0.00,0.00,0,999999999.0000);
INSERT INTO `impuestos` (`Nr`,`Descripcion`,`Alicuota`,`SobreAlicuota`,`Percepcion`,`Uso`,`Minimo`) VALUES (20,'Otros Imp 3',3.00,0.00,0.00,0,999999999.0000);

DELETE FROM `relacion` WHERE 1;
INSERT INTO `relacion` (`nr`,`descrip`) VALUES (1,'Cliente');
INSERT INTO `relacion` (`nr`,`descrip`) VALUES (2,'Proveedor');
INSERT INTO `relacion` (`nr`,`descrip`) VALUES (3,'Amigo');
INSERT INTO `relacion` (`nr`,`descrip`) VALUES (4,'Profesionales');
INSERT INTO `relacion` (`nr`,`descrip`) VALUES (5,'Parientes');
INSERT INTO `relacion` (`nr`,`descrip`) VALUES (6,'Proveedor de Servicios');
INSERT INTO `relacion` (`nr`,`descrip`) VALUES (7,'Expensas');
INSERT INTO `relacion` (`nr`,`descrip`) VALUES (8,'Proveedor del Exterior');
INSERT INTO `relacion` (`nr`,`descrip`) VALUES (9,'Cliente y Proveedor');

DELETE FROM `vendedor` WHERE 1;
INSERT INTO `vendedor` (`nr`,`descrip`) VALUES (1,'Administración de Ventas');

DELETE FROM `zona` WHERE 1;
INSERT INTO `zona` (`nr`,`descrip`) VALUES (1,'Capital Federal');
INSERT INTO `zona` (`nr`,`descrip`) VALUES (2,'Gran Buenos Aries Sur');
INSERT INTO `zona` (`nr`,`descrip`) VALUES (3,'Gran Buenos Aries Norte');
INSERT INTO `zona` (`nr`,`descrip`) VALUES (4,'Gran Buenos Aries Oeste');
INSERT INTO `zona` (`nr`,`descrip`) VALUES (5,'Buenos Aires');
INSERT INTO `zona` (`nr`,`descrip`) VALUES (6,'Santa Fe');
INSERT INTO `zona` (`nr`,`descrip`) VALUES (7,'Cordoba');
INSERT INTO `zona` (`nr`,`descrip`) VALUES (8,'Misiones');
INSERT INTO `zona` (`nr`,`descrip`) VALUES (9,'Corrientes');
INSERT INTO `zona` (`nr`,`descrip`) VALUES (10,'Chaco');
INSERT INTO `zona` (`nr`,`descrip`) VALUES (11,'Santiago del Estero');
INSERT INTO `zona` (`nr`,`descrip`) VALUES (12,'Tucuman');
INSERT INTO `zona` (`nr`,`descrip`) VALUES (13,'Salta');
INSERT INTO `zona` (`nr`,`descrip`) VALUES (14,'La Pampa');
INSERT INTO `zona` (`nr`,`descrip`) VALUES (15,'Tierra del Fuego');
INSERT INTO `zona` (`nr`,`descrip`) VALUES (16,'Santa Cruz');
INSERT INTO `zona` (`nr`,`descrip`) VALUES (17,'Chubut');
INSERT INTO `zona` (`nr`,`descrip`) VALUES (18,'Neuquen');
INSERT INTO `zona` (`nr`,`descrip`) VALUES (19,'Mendoza');
INSERT INTO `zona` (`nr`,`descrip`) VALUES (20,'San Juan');
INSERT INTO `zona` (`nr`,`descrip`) VALUES (21,'San Luis');
INSERT INTO `zona` (`nr`,`descrip`) VALUES (22,'Catamarca');
INSERT INTO `zona` (`nr`,`descrip`) VALUES (23,'La Rioja');
INSERT INTO `zona` (`nr`,`descrip`) VALUES (24,'Jujuy');
INSERT INTO `zona` (`nr`,`descrip`) VALUES (25,'Formosa');
INSERT INTO `zona` (`nr`,`descrip`) VALUES (26,'Entre Rios');
INSERT INTO `zona` (`nr`,`descrip`) VALUES (27,'Rio Negro');

DELETE FROM `ivacod` WHERE 1;
INSERT INTO `ivacod` (`Nr`,`Descrip`,`Letra`,`Obser`,`NrAlicuota`) VALUES (1,'IVA Resp.  Inscripto','A','1',1);
INSERT INTO `ivacod` (`Nr`,`Descrip`,`Letra`,`Obser`,`NrAlicuota`) VALUES (2,'Consumidor Final','B','5',1);
INSERT INTO `ivacod` (`Nr`,`Descrip`,`Letra`,`Obser`,`NrAlicuota`) VALUES (3,'Exento','B','4',3);
INSERT INTO `ivacod` (`Nr`,`Descrip`,`Letra`,`Obser`,`NrAlicuota`) VALUES (4,'Responsable No Inscripto','A','2',1);
INSERT INTO `ivacod` (`Nr`,`Descrip`,`Letra`,`Obser`,`NrAlicuota`) VALUES (5,'No Responsable','B','3',1);
INSERT INTO `ivacod` (`Nr`,`Descrip`,`Letra`,`Obser`,`NrAlicuota`) VALUES (6,'IVA Resp.Insc','A','4',3);
INSERT INTO `ivacod` (`Nr`,`Descrip`,`Letra`,`Obser`,`NrAlicuota`) VALUES (7,'Exportacion','E','4',3);
INSERT INTO `ivacod` (`Nr`,`Descrip`,`Letra`,`Obser`,`NrAlicuota`) VALUES (8,'Responsable Inscripto','A','1',1);
INSERT INTO `ivacod` (`Nr`,`Descrip`,`Letra`,`Obser`,`NrAlicuota`) VALUES (9,'IVA Responsable No Inscripto','C','2',3);
INSERT INTO `ivacod` (`Nr`,`Descrip`,`Letra`,`Obser`,`NrAlicuota`) VALUES (10,'Exento','C','4',3);
INSERT INTO `ivacod` (`Nr`,`Descrip`,`Letra`,`Obser`,`NrAlicuota`) VALUES (11,'Monotributo Compras','C','6',3);
INSERT INTO `ivacod` (`Nr`,`Descrip`,`Letra`,`Obser`,`NrAlicuota`) VALUES (12,'Sujeto No Categorizado','B','7',1);
INSERT INTO `ivacod` (`Nr`,`Descrip`,`Letra`,`Obser`,`NrAlicuota`) VALUES (13,'MonoTributo Ventas','B','4',3);
INSERT INTO `ivacod` (`Nr`,`Descrip`,`Letra`,`Obser`,`NrAlicuota`) VALUES (14,'Responsable Inscripto','Z','1',3);
INSERT INTO `ivacod` (`Nr`,`Descrip`,`Letra`,`Obser`,`NrAlicuota`) VALUES (15,'Responsable Inscripto','A','1',4);
INSERT INTO `ivacod` (`Nr`,`Descrip`,`Letra`,`Obser`,`NrAlicuota`) VALUES (16,'Responsable Inscripto','A','1',2);
INSERT INTO `ivacod` (`Nr`,`Descrip`,`Letra`,`Obser`,`NrAlicuota`) VALUES (17,'.','A','Ventas',1);

DELETE FROM `condi` WHERE 1;
INSERT INTO `condi` (`nr`,`descrip`) VALUES (1,'Cheque c/e a 30 dias Fecha fac');
INSERT INTO `condi` (`nr`,`descrip`) VALUES (2,'Contado efectivo');
INSERT INTO `condi` (`nr`,`descrip`) VALUES (3,'Pagare a 45 dias');
INSERT INTO `condi` (`nr`,`descrip`) VALUES (4,'Cta. Cte. A 45 Días');
INSERT INTO `condi` (`nr`,`descrip`) VALUES (5,'Cheque a 60 dias F.F. C/Entr.');
INSERT INTO `condi` (`nr`,`descrip`) VALUES (6,'Cheque a 90 dias Fecha fact');
INSERT INTO `condi` (`nr`,`descrip`) VALUES (7,'Cta. Cte. A 30 Días');
INSERT INTO `condi` (`nr`,`descrip`) VALUES (8,'Cta. Cte. A 60 Días');
INSERT INTO `condi` (`nr`,`descrip`) VALUES (9,'Cheque a 1 dias Fecha fact');
INSERT INTO `condi` (`nr`,`descrip`) VALUES (10,'Cheque a 50 dias Fecha fact');
INSERT INTO `condi` (`nr`,`descrip`) VALUES (11,'Cta. Cte. A 7 Días Cheque A 45');
INSERT INTO `condi` (`nr`,`descrip`) VALUES (12,'Cheque C/entrega a 10 Días f.f');
INSERT INTO `condi` (`nr`,`descrip`) VALUES (13,'CHEQUE C/ENTREGA A 7 DIAS');
INSERT INTO `condi` (`nr`,`descrip`) VALUES (14,'CUENTA CORRIENTE A 15 DIAS');
INSERT INTO `condi` (`nr`,`descrip`) VALUES (15,'CHEQUE A 15 DIAS C/ENTREGA');
INSERT INTO `condi` (`nr`,`descrip`) VALUES (16,'CHEQUE PROPIO A 7 DIAS C/ENTRE');
INSERT INTO `condi` (`nr`,`descrip`) VALUES (17,'CUENTA CORRIENTE A 21 DIAS');
INSERT INTO `condi` (`nr`,`descrip`) VALUES (18,'CTA. CTE. A 7 DIAS CHEQUE A 60');
INSERT INTO `condi` (`nr`,`descrip`) VALUES (19,'CHEQUE AL DIA C/ENTREGA');
INSERT INTO `condi` (`nr`,`descrip`) VALUES (20,'CHEQUE C/ENTREGA A 45 DIAS');
INSERT INTO `condi` (`nr`,`descrip`) VALUES (21,'CHEQUE C/ENTREGA A 90 DIAS');
INSERT INTO `condi` (`nr`,`descrip`) VALUES (22,'cheque c/entrega a 7 dias');
INSERT INTO `condi` (`nr`,`descrip`) VALUES (23,'Cheque a 120 dias Fecha fact.');
INSERT INTO `condi` (`nr`,`descrip`) VALUES (24,'Cta. Cte. 30 días Cheque 60');
INSERT INTO `condi` (`nr`,`descrip`) VALUES (25,'Cta. Cte. 15 Días');
INSERT INTO `condi` (`nr`,`descrip`) VALUES (26,'C/Entrega a 30 Días + 5 %');
INSERT INTO `condi` (`nr`,`descrip`) VALUES (27,'CHEQUE C/ENTREGA A 20 DIAS');
INSERT INTO `condi` (`nr`,`descrip`) VALUES (28,'CUENTA CORRIENTE');
INSERT INTO `condi` (`nr`,`descrip`) VALUES (29,'Cheque c/ entrega a 48 hs.');
INSERT INTO `condi` (`nr`,`descrip`) VALUES (30,'Cheque C/E a 7 Días + 3%');
INSERT INTO `condi` (`nr`,`descrip`) VALUES (31,'CUENTA CORRIENTE A 7 DIAS');
INSERT INTO `condi` (`nr`,`descrip`) VALUES (32,'CHEQUE C/ENTREGA A 21 DIAS');
INSERT INTO `condi` (`nr`,`descrip`) VALUES (33,'Entrega C/Acred. Transf. Banc.');
INSERT INTO `condi` (`nr`,`descrip`) VALUES (34,'Res.Sem. Cheque a 30 FechaFact');
INSERT INTO `condi` (`nr`,`descrip`) VALUES (35,'Cheque a 7 Dias y 15 Dias c/en');
INSERT INTO `condi` (`nr`,`descrip`) VALUES (36,'Contado anticipado o acreditad');
INSERT INTO `condi` (`nr`,`descrip`) VALUES (37,'Cheques PROPIOS a 30 dias f.f.');
INSERT INTO `condi` (`nr`,`descrip`) VALUES (38,'Cheque c/entrega a 1 Dia f.f.');
INSERT INTO `condi` (`nr`,`descrip`) VALUES (39,'Cheque c/entrega a 35 dias f.f');
INSERT INTO `condi` (`nr`,`descrip`) VALUES (40,'Cheque c/entrega a 40 dias f.f');
INSERT INTO `condi` (`nr`,`descrip`) VALUES (41,'CTA. CTE. A 210 DIAS FF');
INSERT INTO `condi` (`nr`,`descrip`) VALUES (42,'COMPENSACION');
INSERT INTO `condi` (`nr`,`descrip`) VALUES (43,'Contado Efect. Contra Entrega');
INSERT INTO `condi` (`nr`,`descrip`) VALUES (44,'Cheque 60 días F.F. c/e 30 d.');
INSERT INTO `condi` (`nr`,`descrip`) VALUES (45,'CHEQUE C/ENTREGA A 30 Y A 32 D');
INSERT INTO `condi` (`nr`,`descrip`) VALUES (46,'CONSIGNACION');
INSERT INTO `condi` (`nr`,`descrip`) VALUES (47,'Cheq.  50% a 1 D. 50% a 2 D.');
INSERT INTO `condi` (`nr`,`descrip`) VALUES (48,'Cta. Cte. A 7 D. ch. A 90 D FF');
INSERT INTO `condi` (`nr`,`descrip`) VALUES (49,'Cta. Cte. A 75 dias');
INSERT INTO `condi` (`nr`,`descrip`) VALUES (50,'Cta.Cte 15 días / Ch. 45 d.f.f');
INSERT INTO `condi` (`nr`,`descrip`) VALUES (51,'Cta. Cte. A 7 Días Cheque A 30');
INSERT INTO `condi` (`nr`,`descrip`) VALUES (52,'Res.Sem. Cheque a 45 FechaFact');
INSERT INTO `condi` (`nr`,`descrip`) VALUES (53,'CHEQ. C/ENTREGA AL 26/12/2005');
INSERT INTO `condi` (`nr`,`descrip`) VALUES (54,'Cheque al día Certificado');
INSERT INTO `condi` (`nr`,`descrip`) VALUES (55,'30 Dias f.p.f.');
INSERT INTO `condi` (`nr`,`descrip`) VALUES (56,'Cta.Cte.30d.cheque.a 90 f.fact');
INSERT INTO `condi` (`nr`,`descrip`) VALUES (57,'Cta. Cte. 10 dias');
INSERT INTO `condi` (`nr`,`descrip`) VALUES (58,'Cta. Cte. 10 dias Cheque a 30');
INSERT INTO `condi` (`nr`,`descrip`) VALUES (59,'CHEQ. C/ENTREGA A 50 DIAS');
INSERT INTO `condi` (`nr`,`descrip`) VALUES (60,'Cta. Cte. 10 dias Cheque a 40');
INSERT INTO `condi` (`nr`,`descrip`) VALUES (61,'Cta. Cte. 2 Días Cheque A 30');
INSERT INTO `condi` (`nr`,`descrip`) VALUES (62,'Cta.Cte. 72hs / Cheque a 7 d.');
INSERT INTO `condi` (`nr`,`descrip`) VALUES (63,'Cta.Cte.30 dias/ Cheque 45 dff');
INSERT INTO `condi` (`nr`,`descrip`) VALUES (64,'Cta.Cte. 72hs / Cheque a 30 d.');
INSERT INTO `condi` (`nr`,`descrip`) VALUES (65,'Cta.Cte.60 dias/ Cheque 90 dff');
INSERT INTO `condi` (`nr`,`descrip`) VALUES (66,'Cta.Cte. 45 dias');
INSERT INTO `condi` (`nr`,`descrip`) VALUES (67,'Cta.Cte. 15 dìas/ Cheque 30dff');
INSERT INTO `condi` (`nr`,`descrip`) VALUES (68,'Cheque c/entrega a 20 días');
INSERT INTO `condi` (`nr`,`descrip`) VALUES (69,'Cta Cte 15 dias Cheque a 60 FF');
INSERT INTO `condi` (`nr`,`descrip`) VALUES (70,'Cta. Cte.  2 Días');
INSERT INTO `condi` (`nr`,`descrip`) VALUES (71,'CH C/ENTREGA A 60 DIAS F FACT');
INSERT INTO `condi` (`nr`,`descrip`) VALUES (72,'Cheque c/entrega a 10 d.f.f.');
INSERT INTO `condi` (`nr`,`descrip`) VALUES (73,'Cta. Cte. A 27 dias F.F.');
INSERT INTO `condi` (`nr`,`descrip`) VALUES (74,'Cta. Cte. A 30 FFP');
INSERT INTO `condi` (`nr`,`descrip`) VALUES (75,'Cta Cte 7 dias CH/E a 37 dias');
INSERT INTO `condi` (`nr`,`descrip`) VALUES (76,'DEPOSITO EN CUENTA');
INSERT INTO `condi` (`nr`,`descrip`) VALUES (77,'Cheque C/E a 30 y 60 dias');
INSERT INTO `condi` (`nr`,`descrip`) VALUES (78,'Cta. Cte. a 60 dias ch. a 30');
INSERT INTO `condi` (`nr`,`descrip`) VALUES (79,'Cta. Cte. 10 dias Ch. a 50 dff');
INSERT INTO `condi` (`nr`,`descrip`) VALUES (80,'Cta Cte a 15 dias Ch a 60 dias');
INSERT INTO `condi` (`nr`,`descrip`) VALUES (81,'Cheque c/ent 75 días fec. Fact');
INSERT INTO `condi` (`nr`,`descrip`) VALUES (82,'CH C/E 30 DÍAS F. ENTREGA');
INSERT INTO `condi` (`nr`,`descrip`) VALUES (83,'Cta. Cte. 60 días Fecha Fact.');
INSERT INTO `condi` (`nr`,`descrip`) VALUES (84,'CTA. CTE. 90 DÍAS FECHA FACT.');
INSERT INTO `condi` (`nr`,`descrip`) VALUES (85,'Cta.Cte. 50 días F.F.');
INSERT INTO `condi` (`nr`,`descrip`) VALUES (86,'Ch. c/ent a 60 días F.Entrega');
INSERT INTO `condi` (`nr`,`descrip`) VALUES (87,'Ch. c/ent a 30 días F.Entrega');
INSERT INTO `condi` (`nr`,`descrip`) VALUES (88,'Cta.Cte.A 10 dias/Cheque a 60');
INSERT INTO `condi` (`nr`,`descrip`) VALUES (89,'Cta Cte 50% a 30 d 50% a 60 d');
INSERT INTO `condi` (`nr`,`descrip`) VALUES (90,'Pago diferido de 30 a 45 días.');
INSERT INTO `condi` (`nr`,`descrip`) VALUES (91,'Prepago - Canc. Anticipada');
INSERT INTO `condi` (`nr`,`descrip`) VALUES (92,'Cta.Cte. 30 días Chq. 60 D.F.F');
INSERT INTO `condi` (`nr`,`descrip`) VALUES (93,'Transf. ant. 50% Ch 20 c/e 50%');
INSERT INTO `condi` (`nr`,`descrip`) VALUES (94,'PAGO DIFERIDO A 90 DIAS');
INSERT INTO `condi` (`nr`,`descrip`) VALUES (95,'COMISION LIQUIDO PRODUCTO');
INSERT INTO `condi` (`nr`,`descrip`) VALUES (96,'Depósito en Cuenta');
INSERT INTO `condi` (`nr`,`descrip`) VALUES (97,'50 % ch a 7 d. y 50% ch. a 30');
INSERT INTO `condi` (`nr`,`descrip`) VALUES (98,'75 % cont y 25 % Ch/ent  a  45');
INSERT INTO `condi` (`nr`,`descrip`) VALUES (99,'ch/entr 7 y CH/E a 37 dias');

DELETE FROM `fam` WHERE 1;
INSERT INTO `fam` (`nrfam`,`desfam`) VALUES (0,'ALIMENTOS');
INSERT INTO `fam` (`nrfam`,`desfam`) VALUES (1,'BEBIDAS');

DELETE FROM `subfam` WHERE 1;
INSERT INTO `subfam` (`nrsubfam`,`desubfa`,`famaso`,`foto`,`leyenda`) VALUES (0,'ARCOR',0,NULL,NULL);
INSERT INTO `subfam` (`nrsubfam`,`desubfa`,`famaso`,`foto`,`leyenda`) VALUES (1,'ALFA PAMPA',0,NULL,NULL);
INSERT INTO `subfam` (`nrsubfam`,`desubfa`,`famaso`,`foto`,`leyenda`) VALUES (2,'SNACKS',0,NULL,NULL);
INSERT INTO `subfam` (`nrsubfam`,`desubfa`,`famaso`,`foto`,`leyenda`) VALUES (3,'LUIGI BOSCA',1,NULL,NULL);
INSERT INTO `subfam` (`nrsubfam`,`desubfa`,`famaso`,`foto`,`leyenda`) VALUES (4,'NIETO SENETINER',1,NULL,NULL);
INSERT INTO `subfam` (`nrsubfam`,`desubfa`,`famaso`,`foto`,`leyenda`) VALUES (5,'CHANDON',1,NULL,NULL);
INSERT INTO `subfam` (`nrsubfam`,`desubfa`,`famaso`,`foto`,`leyenda`) VALUES (6,'LAS PERDICES',1,NULL,NULL);
INSERT INTO `subfam` (`nrsubfam`,`desubfa`,`famaso`,`foto`,`leyenda`) VALUES (7,'CATENA ZAPATA',1,NULL,NULL);
INSERT INTO `subfam` (`nrsubfam`,`desubfa`,`famaso`,`foto`,`leyenda`) VALUES (8,'LA RURAL',1,NULL,NULL);
INSERT INTO `subfam` (`nrsubfam`,`desubfa`,`famaso`,`foto`,`leyenda`) VALUES (9,'CASARENA',1,NULL,NULL);
INSERT INTO `subfam` (`nrsubfam`,`desubfa`,`famaso`,`foto`,`leyenda`) VALUES (10,'CHAKANA',1,NULL,NULL);
INSERT INTO `subfam` (`nrsubfam`,`desubfa`,`famaso`,`foto`,`leyenda`) VALUES (11,'CALLIA',1,NULL,NULL);
INSERT INTO `subfam` (`nrsubfam`,`desubfa`,`famaso`,`foto`,`leyenda`) VALUES (12,'ESPIRITUOSAS',1,NULL,NULL);


DELETE FROM `prov` WHERE 1;
INSERT INTO prov (`nr`,`descrip`) VALUES(1,'BUENOS AIRES');
INSERT INTO prov (`nr`,`descrip`) VALUES(2,'CATAMARCA');
INSERT INTO prov (`nr`,`descrip`) VALUES(3,'CHACO');
INSERT INTO prov (`nr`,`descrip`) VALUES(4,'CHUBUT');
INSERT INTO prov (`nr`,`descrip`) VALUES(5,'CORDOBA');
INSERT INTO prov (`nr`,`descrip`) VALUES(6,'CORRIENTES');
INSERT INTO prov (`nr`,`descrip`) VALUES(7,'ENTRE RIOS');
INSERT INTO prov (`nr`,`descrip`) VALUES(8,'FORMOSA');
INSERT INTO prov (`nr`,`descrip`) VALUES(9,'JUJUY');
INSERT INTO prov (`nr`,`descrip`) VALUES(10,'LA PAMPA');
INSERT INTO prov (`nr`,`descrip`) VALUES(11,'LA RIOJA');
INSERT INTO prov (`nr`,`descrip`) VALUES(12,'MENDOZA');
INSERT INTO prov (`nr`,`descrip`) VALUES(13,'MISIONES');
INSERT INTO prov (`nr`,`descrip`) VALUES(14,'NEUQUEN');
INSERT INTO prov (`nr`,`descrip`) VALUES(15,'RIO NEGRO');
INSERT INTO prov (`nr`,`descrip`) VALUES(16,'SALTA');
INSERT INTO prov (`nr`,`descrip`) VALUES(17,'SAN JUAN');
INSERT INTO prov (`nr`,`descrip`) VALUES(18,'SAN LUIS');
INSERT INTO prov (`nr`,`descrip`) VALUES(19,'SANTA CRUZ');
INSERT INTO prov (`nr`,`descrip`) VALUES(20,'SANTA FE');
INSERT INTO prov (`nr`,`descrip`) VALUES(21,'SANTIAGO DEL ESTERO');
INSERT INTO prov (`nr`,`descrip`) VALUES(22,'TIERRA DEL FUEGO');
INSERT INTO prov (`nr`,`descrip`) VALUES(23,'TUCUMAN');


DELETE FROM `artmadre` WHERE 1;
INSERT INTO `artmadre` (`CodArtMad`,`DescArtMad`) VALUES ('XXX','No definido');


DELETE FROM `plan` WHERE 1;
INSERT INTO `plan` (`nr`,`descrip`,`cuenta`) VALUES (1,'Ventas','1101010000');
INSERT INTO `plan` (`nr`,`descrip`,`cuenta`) VALUES (2,'Compras',NULL);
INSERT INTO `plan` (`nr`,`descrip`,`cuenta`) VALUES (3,'Cobranzas de Deudores',NULL);
INSERT INTO `plan` (`nr`,`descrip`,`cuenta`) VALUES (4,'Pago a Proveedores',NULL);
INSERT INTO `plan` (`nr`,`descrip`,`cuenta`) VALUES (5,'Depositos en Bancos',NULL);
INSERT INTO `plan` (`nr`,`descrip`,`cuenta`) VALUES (6,'Gastos Bancarios',NULL);
INSERT INTO `plan` (`nr`,`descrip`,`cuenta`) VALUES (7,'Operaciones con TarjCred',NULL);
INSERT INTO `plan` (`nr`,`descrip`,`cuenta`) VALUES (8,'Cheques Rechazados',NULL);
INSERT INTO `plan` (`nr`,`descrip`,`cuenta`) VALUES (9,'Gastos de Librería',NULL);
INSERT INTO `plan` (`nr`,`descrip`,`cuenta`) VALUES (10,'Gastos de Viaticos',NULL);
INSERT INTO `plan` (`nr`,`descrip`,`cuenta`) VALUES (11,'Gastos de Alimentacion',NULL);
INSERT INTO `plan` (`nr`,`descrip`,`cuenta`) VALUES (12,'Gastos Varios',NULL);
INSERT INTO `plan` (`nr`,`descrip`,`cuenta`) VALUES (13,'Gastos de Envio',NULL);
INSERT INTO `plan` (`nr`,`descrip`,`cuenta`) VALUES (14,'Gastos de Refaccion',NULL);
INSERT INTO `plan` (`nr`,`descrip`,`cuenta`) VALUES (15,'Gastos Servicios Varios',NULL);
INSERT INTO `plan` (`nr`,`descrip`,`cuenta`) VALUES (16,'Fletes y acarreos',NULL);
INSERT INTO `plan` (`nr`,`descrip`,`cuenta`) VALUES (17,'Direccion Tecnica',NULL);
INSERT INTO `plan` (`nr`,`descrip`,`cuenta`) VALUES (18,'Honorarios Profesionales',NULL);
INSERT INTO `plan` (`nr`,`descrip`,`cuenta`) VALUES (19,'Mantenimiento Rodados',NULL);
INSERT INTO `plan` (`nr`,`descrip`,`cuenta`) VALUES (20,'Honorarios Notariales',NULL);
INSERT INTO `plan` (`nr`,`descrip`,`cuenta`) VALUES (21,'Refrigeración para cadenas de frío',NULL);
INSERT INTO `plan` (`nr`,`descrip`,`cuenta`) VALUES (22,'Internet',NULL);
INSERT INTO `plan` (`nr`,`descrip`,`cuenta`) VALUES (23,'Honorarios comercialización',NULL);
INSERT INTO `plan` (`nr`,`descrip`,`cuenta`) VALUES (24,'Luz, gas, teléfono y agua',NULL);
INSERT INTO `plan` (`nr`,`descrip`,`cuenta`) VALUES (25,'Gastos de mantenimiento',NULL);
INSERT INTO `plan` (`nr`,`descrip`,`cuenta`) VALUES (26,'Rodados',NULL);
INSERT INTO `plan` (`nr`,`descrip`,`cuenta`) VALUES (27,'Muebles y útiles',NULL);
INSERT INTO `plan` (`nr`,`descrip`,`cuenta`) VALUES (28,'Seguridad',NULL);
INSERT INTO `plan` (`nr`,`descrip`,`cuenta`) VALUES (29,'Estacionamiemto',NULL);
INSERT INTO `plan` (`nr`,`descrip`,`cuenta`) VALUES (30,'Canje de cheques',NULL);
INSERT INTO `plan` (`nr`,`descrip`,`cuenta`) VALUES (31,'S.U.S.S.',NULL);
INSERT INTO `plan` (`nr`,`descrip`,`cuenta`) VALUES (32,'Expensas Cons. Juan B. Justo 2117',NULL);
INSERT INTO `plan` (`nr`,`descrip`,`cuenta`) VALUES (33,'Expensas Cons.Camargo 1118',NULL);
INSERT INTO `plan` (`nr`,`descrip`,`cuenta`) VALUES (34,'Pago a cuenta Retencion Ganancias',NULL);
INSERT INTO `plan` (`nr`,`descrip`,`cuenta`) VALUES (35,'Servicio de Telefonia Movil',NULL);
INSERT INTO `plan` (`nr`,`descrip`,`cuenta`) VALUES (36,'COM.OPER.DESCUENTO DOCUM.',NULL);
INSERT INTO `plan` (`nr`,`descrip`,`cuenta`) VALUES (37,'INT.OPER.DESCUENTO DOCUM.',NULL);
INSERT INTO `plan` (`nr`,`descrip`,`cuenta`) VALUES (38,'IVA DEB FISCAL 10 5%',NULL);
INSERT INTO `plan` (`nr`,`descrip`,`cuenta`) VALUES (39,'CRED.OPER.DESCUENTO DOCUM',NULL);
INSERT INTO `plan` (`nr`,`descrip`,`cuenta`) VALUES (40,'Pago Bienes Personales Acciones o Participaciones',NULL);
INSERT INTO `plan` (`nr`,`descrip`,`cuenta`) VALUES (41,'DEB.OPER.DESCUENTO DOCUM',NULL);
INSERT INTO `plan` (`nr`,`descrip`,`cuenta`) VALUES (42,'EQUIPOS Y SISTEMAS',NULL);
INSERT INTO `plan` (`nr`,`descrip`,`cuenta`) VALUES (43,'Tasa IGJ',NULL);
INSERT INTO `plan` (`nr`,`descrip`,`cuenta`) VALUES (44,'Gastos de Limpieza y Refrigerio',NULL);
INSERT INTO `plan` (`nr`,`descrip`,`cuenta`) VALUES (45,'Seguros Varios',NULL);
INSERT INTO `plan` (`nr`,`descrip`,`cuenta`) VALUES (46,'Ingesos brutos Convenio Multilateral',NULL);
INSERT INTO `plan` (`nr`,`descrip`,`cuenta`) VALUES (47,'F.A.T.S.A.',NULL);
INSERT INTO `plan` (`nr`,`descrip`,`cuenta`) VALUES (48,'Ganancia minima Presunta',NULL);
INSERT INTO `plan` (`nr`,`descrip`,`cuenta`) VALUES (49,'Pago Tarjete de Credito',NULL);
INSERT INTO `plan` (`nr`,`descrip`,`cuenta`) VALUES (50,'Publicidad y Promocion',NULL);
INSERT INTO `plan` (`nr`,`descrip`,`cuenta`) VALUES (51,'Otros Egesos',NULL);
INSERT INTO `plan` (`nr`,`descrip`,`cuenta`) VALUES (52,'COMPRAS COMPUTABLES',NULL);
INSERT INTO `plan` (`nr`,`descrip`,`cuenta`) VALUES (53,'Gastos de Alojamiento',NULL);
INSERT INTO `plan` (`nr`,`descrip`,`cuenta`) VALUES (54,'OPERACIÓN DE DESCUENTO',NULL);
INSERT INTO `plan` (`nr`,`descrip`,`cuenta`) VALUES (55,'GASTO POR GESTION DE COBRANZA',NULL);
INSERT INTO `plan` (`nr`,`descrip`,`cuenta`) VALUES (56,'VIATICOS AL EXTERIOR',NULL);
INSERT INTO `plan` (`nr`,`descrip`,`cuenta`) VALUES (57,'GASTOS EMPRESARIALES',NULL);
INSERT INTO `plan` (`nr`,`descrip`,`cuenta`) VALUES (58,'REFRIGERIOS',NULL);
INSERT INTO `plan` (`nr`,`descrip`,`cuenta`) VALUES (59,'GASTOS DE REPRESENTACION',NULL);
INSERT INTO `plan` (`nr`,`descrip`,`cuenta`) VALUES (60,'COMBUSTIBLE',NULL);

DELETE FROM `trans` WHERE 1;
INSERT INTO `trans` (`nr`,`descrip`,`domi`,`cuit`,`tel`,`obser`,`Area1`,`area2`,`area3`,`ObserEntrega`) VALUES (1,'Transporte Propio','.','.','.','.',NULL,NULL,NULL,NULL);
'''.format(schema=schema)

file_conf.write(script)
file_conf.close()

cmd = '''mysql --user root --password=root < /tmp/insert_combos.sql'''

tb = commands.getoutput(cmd)
print tb

if len(sys.argv) < 2:
    u = "Usage: insert_combos <schema>\n"
    sys.stderr.write(u)
    sys.exit(1)

