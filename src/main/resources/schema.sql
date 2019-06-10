
DROP TABLE IF EXISTS `user_wallet`;
DROP TABLE IF EXISTS `USER`;

CREATE TABLE `user` (
                        `USER_ID` int(11) NOT NULL AUTO_INCREMENT,
                        `FULL_NAME` varchar(45) NOT NULL,
                        PRIMARY KEY (`USER_ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;



CREATE TABLE `user_wallet` (
    `WALLET_ID` int(11) NOT NULL AUTO_INCREMENT,
    `USER_ID` int(11) NOT NULL,
    `balance` decimal(10,0) NOT NULL,
    `currency_code` varchar(5) NOT NULL,
    PRIMARY KEY (`WALLET_ID`),
    KEY `UserID_FK_idx` (`USER_ID`),
    CONSTRAINT `UserID_FK` FOREIGN KEY (`USER_ID`) REFERENCES `user` (`USER_ID`) ON DELETE CASCADE ON UPDATE CASCADE
    ) ENGINE=InnoDB DEFAULT CHARSET=utf8;




