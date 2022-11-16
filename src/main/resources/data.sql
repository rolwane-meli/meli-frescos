-- ============================= RESET TABLES ============================= --
DROP TABLE IF EXISTS `product_purchase_orders`;
DROP TABLE IF EXISTS `purchase_orders`;
DROP TABLE IF EXISTS `buyers`;
DROP TABLE IF EXISTS `batches`;
DROP TABLE IF EXISTS `products`;
DROP TABLE IF EXISTS `sellers`;
DROP TABLE IF EXISTS `inbound_orders`;
DROP TABLE IF EXISTS `sectors`;
DROP TABLE IF EXISTS `warehouses`;
DROP TABLE IF EXISTS `representatives`;


-- ============================= BUYERS ============================= --
CREATE TABLE `buyers` (
    `id` bigint NOT NULL AUTO_INCREMENT,
    `cpf` varchar(14) NOT NULL UNIQUE,
    `email` varchar(45) NOT NULL UNIQUE,
    `name` varchar(45) NOT NULL,
    PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

INSERT INTO `buyers` VALUES (1,'45678912300','augusto.liberato@mercadolivre.com','Augusto Liberato'),
                            (2,'32184758305','marcos@gmail.com','Marcos Silva'),
                            (3,'04883618401','antonia@gmail.com','Antonia Bastos');


-- ============================= SELLERS ============================= --
CREATE TABLE `sellers` (
    `id` bigint NOT NULL AUTO_INCREMENT,
    `cnpj` varchar(20) NOT NULL UNIQUE,
    `email` varchar(45) NOT NULL UNIQUE,
    `name` varchar(45) NOT NULL,
    `phone_number` varchar(11) NOT NULL,
    PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

INSERT INTO `sellers` VALUES (1,'40005821000135','maria.lopes@gmail.com','Maria Lopes Lima','11552390949'),
                             (2,'16625752000188','joaorp@hotmail.com','João Ribeiro Pereira','81780553026'),
                             (3,'88574702000120','lucas.castro@gmail.com','Lucas Sousa Castro','61600261170'),
                             (4,'35246916000100','pratriciafmoraes@yahoo.com','Patricia Fernandes Moraes','88262858046');


-- ============================= REPRESENTATIVES ============================= --
CREATE TABLE `representatives` (
    `id` bigint NOT NULL AUTO_INCREMENT,
    `cpf` varchar(14) NOT NULL UNIQUE,
    `email` varchar(45) NOT NULL UNIQUE,
    `name` varchar(45) NOT NULL,
    PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=6 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

INSERT INTO `representatives` VALUES (1,'90142253790','ana.reis@hotmail.com','Ana Oliveira Reis'),
                                     (2,'00305142909','jugoncalves@gmail.com','Juliana Gonçalves Freitas'),
                                     (3,'71103257374','luizmlima@gmail.com','Luiz Miranda Lima'),
                                     (4,'72134466278','alinefnunes@yahoo.com','Aline Freitas Nunes'),
                                     (5,'10341176010','antonioosouza@yahoo.com','Antonio Oliveira Souza');


-- ============================= PRODUCTS ============================= --
CREATE TABLE `products` (
    `id` bigint NOT NULL AUTO_INCREMENT,
    `name` varchar(100) NOT NULL,
    `type` varchar(255) NOT NULL,
    `id_seller` bigint DEFAULT NULL,
    PRIMARY KEY (`id`),
    FOREIGN KEY (`id_seller`) REFERENCES `sellers` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=11 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

INSERT INTO `products` VALUES (1,'Leite','REFRIGERATED',1),
                              (2,'Margarina','REFRIGERATED',1),
                              (3,'Iogurte','REFRIGERATED',2),
                              (4,'Queijo','REFRIGERATED',2),
                              (5,'Carne de Hambúrguer','FROZEN',3),
                              (6,'Sorvete','FROZEN',3),
                              (7,'Picanha','FROZEN',4),
                              (8,'Pão de Queijo','FROZEN',4),
                              (9,'Batata Congelada','FROZEN',2),
                              (10,'Ovo de codorna','REFRIGERATED',1);


-- ============================= WAREHOUSES ============================= --
CREATE TABLE `warehouses` (
    `id` bigint NOT NULL AUTO_INCREMENT,
    `name` varchar(45) NOT NULL,
    `id_representative` bigint NOT NULL,
    PRIMARY KEY (`id`),
    FOREIGN KEY (`id_representative`) REFERENCES `representatives` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=6 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

INSERT INTO `warehouses` VALUES (1,'meli-sp1',1),
                                (2,'meli-sp2',2),
                                (3,'meli-rj1',4),
                                (4,'meli-mg1',5),
                                (5,'meli-ce1',3);


-- ============================= SECTORS ============================= --
CREATE TABLE `sectors` (
    `id` bigint NOT NULL AUTO_INCREMENT,
    `capacity` double NOT NULL,
    `name` varchar(45) NOT NULL,
    `type` varchar(255) NOT NULL,
    `id_warehouse` bigint NOT NULL,
    PRIMARY KEY (`id`),
    FOREIGN KEY (`id_warehouse`) REFERENCES `warehouses` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=14 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

INSERT INTO `sectors` VALUES (1,10,'ST-01','FROZEN',1),
                             (2,25.5,'ST-02','FROZEN',1),
                             (3,15,'ST-03','FROZEN',2),
                             (4,32.5,'ST-04','REFRIGERATED',3),
                             (5,8,'ST-05','REFRIGERATED',2),
                             (6,75,'ST-06','REFRIGERATED',4),
                             (7,50,'ST-07','REFRIGERATED',4),
                             (8,45.5,'ST-08','FROZEN',4),
                             (9,18.5,'ST-09','FROZEN',5),
                             (10,78.5,'ST-10','FROZEN',5),
                             (11,36,'ST-11','REFRIGERATED',5),
                             (12,64,'ST-12','FROZEN',3),
                             (13,80,'ST-13','REFRIGERATED',3);


-- ============================= INBOUND ORDER ============================= --
CREATE TABLE `inbound_orders` (
    `id` bigint NOT NULL AUTO_INCREMENT,
    `order_date` datetime(6) NOT NULL,
    `id_sector` bigint NOT NULL,
    PRIMARY KEY (`id`),
    FOREIGN KEY (`id_sector`) REFERENCES `sectors` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=11 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

INSERT INTO `inbound_orders` VALUES (1,'2022-04-22 14:30:02.123000',1),
                                    (2,'2022-05-10 09:12:10.123000',1),
                                    (3,'2022-05-29 15:00:30.123000',2),
                                    (4,'2022-06-04 10:25:04.123000',3),
                                    (5,'2022-07-04 09:30:04.123000',4),
                                    (6,'2022-07-15 17:01:05.123000',4),
                                    (7,'2022-07-28 18:10:09.123000',5),
                                    (8,'2022-08-02 14:00:03.123000',6),
                                    (9,'2022-09-10 14:30:18.123000',11),
                                    (10,'2022-09-26 10:00:22.123000',10);


-- ============================= BATCHES ============================= --
CREATE TABLE `batches` (
    `id` bigint NOT NULL AUTO_INCREMENT,
    `current_temperature` double NOT NULL,
    `due_date` datetime(6) NOT NULL,
    `manufacturing_date` date NOT NULL,
    `manufacturing_time` time NOT NULL,
    `price` decimal(19,2) NOT NULL,
    `product_quantity` int NOT NULL,
    `volume` double NOT NULL,
    `id_inbound_order` bigint DEFAULT NULL,
    `id_product` bigint NOT NULL,
    PRIMARY KEY (`id`),
    FOREIGN KEY (`id_inbound_order`) REFERENCES `inbound_orders` (`id`),
    FOREIGN KEY (`id_product`) REFERENCES `products` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=11 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

INSERT INTO `batches` VALUES (1,-18,'2023-01-31 00:00:00.000000','2022-08-30','18:00:00',200.00,75,1,1,5),
                             (2,-18,'2023-02-22 15:30:00.000000','2022-08-30','10:45:00',1250.00,285,2,2,6),
                             (3,-18,'2023-03-22 10:00:00.000000','2022-09-15','12:00:00',18000.00,350,5,3,7),
                             (4,-18,'2023-04-15 12:30:10.000000','2022-10-15','12:00:00',2000.00,500,7,4,8),
                             (5,10,'2023-03-20 12:00:00.000000','2022-11-28','17:15:00',1750.00,250,5,5,1),
                             (6,10,'2023-04-06 13:00:10.000000','2022-11-28','10:00:20',2250.00,150,10,6,4),
                             (7,10,'2023-01-06 13:00:00.000000','2022-12-28','08:30:15',2345.50,330,8,7,3),
                             (8,10,'2023-01-10 10:30:13.000000','2022-12-10','08:30:15',1500.00,250,5,8,2),
                             (9,10,'2023-03-01 14:30:15.000000','2022-11-25','19:30:15',600.00,300,5,9,10),
                             (10,-18,'2023-01-20 08:00:00.000000','2022-09-11','17:15:00',3500.00,486,12,10,9);