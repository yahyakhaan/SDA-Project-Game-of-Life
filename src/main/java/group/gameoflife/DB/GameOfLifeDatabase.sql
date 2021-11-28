create database GameofLifeDB;
use GameOfLifeDB;

#Table containing all relevant information for a state, including the maximum row and column number of saved grid.

CREATE TABLE GameTB
(
GameName varchar(30),
RowNo int,
ColNo int,
isAlive bit,
MaxRowSize int,
MaxColSize int,
PRIMARY KEY (GameName,RowNo,ColNo)
);

#Stored Procedures for DB

DELIMITER $$
use GameOfLifeDB $$
CREATE PROCEDURE saveState(IN GameNameIN varchar(30), IN RowNoIN int, IN ColNoIN int, IN isAliveIN bit, IN MaxRowSizeIN int, IN MaxColSizeIN int)
begin
	replace into GameTB(GameName,RowNo,ColNo,isAlive,MaxRowSize,MaxColSize)
    VALUES
    (GameNameIN,RowNoIN,ColNoIN,isAliveIN,MaxRowSizeIN,MaxColSizeIN);
end $$


DELIMITER $$
use GameOfLifeDB $$
CREATE PROCEDURE deleteState(IN GameNameIN varchar(30))
begin
	if exists( select* from GameTB
				where GameTB.GameName=GameNameIN)
	then
		delete from GameTB
        where GameTB.GameName=GameNameIN;
	end if;
end $$


DELIMITER $$
use GameOfLifeDB $$
CREATE PROCEDURE viewState()
begin
	select GameTB.GameName from GameTB
    group by GameTB.GameName;
end $$


DELIMITER $$
use GameOfLifeDB $$
CREATE PROCEDURE loadState(IN GameNameIN varchar(30))
begin
	if exists( select* from GameTB
				where GameTB.GameName=GameNameIN)
	then
		select GameTB.RowNo,GameTB.ColNo,GameTB.isAlive,GameTB.MaxRowSize,GameTB.MaxColSize from GameTB
        where GameTB.GameName=GameNameIN;
	end if;
end $$