--
-- This SQL script builds a monopoly database, deleting any pre-existing version.
--
-- @author kvlinden
-- @version Summer, 2015
--
-- Students: Jason Klaassen & Alex Cho

-- Drop previous versions of the tables if they they exist, in reverse order of foreign keys.
DROP TABLE IF EXISTS PlayerGame CASCADE;
DROP TABLE IF EXISTS Game CASCADE;
DROP TABLE IF EXISTS Player CASCADE;
DROP TABLE IF EXISTS Property CASCADE;

-- Create the schema.
CREATE TABLE Game (
	ID integer PRIMARY KEY, 
	time timestamp
	);

CREATE TABLE Player (
	ID integer PRIMARY KEY, 
	emailAddress varchar(50) NOT NULL,
	name varchar(50)
	);

CREATE TABLE PlayerGame (
	gameID integer REFERENCES Game(ID) NOT NULL, 
	playerID integer REFERENCES Player(ID) NOT NULL,
	score integer,
	boardLocation integer DEFAULT 1 Check (boardLocation BETWEEN 1 AND 40)
	
	);
CREATE TABLE Property (
	propertyID integer NOT NULL CHECK (propertyID BETWEEN 1 AND 28),
	gameID integer REFERENCES Game(ID) NOT NULL,
	playerID integer REFERENCES Player(ID) NULL,
	numberOfHouses integer DEFAULT 0 Check (NumberOfHouses BETWEEN 0 AND 5),
	numberOfHotels integer DEFAULT 0 Check (NumberOfHotels BETWEEN 0 AND 3)
	);

-- Allow users to select data from the tables.
GRANT SELECT ON Game TO PUBLIC;
GRANT SELECT ON Player TO PUBLIC;
GRANT SELECT ON PlayerGame TO PUBLIC;

-- Add sample records.
INSERT INTO Game VALUES (1, '2006-06-27 08:00:00');
INSERT INTO Game VALUES (2, '2006-06-28 13:20:00');
INSERT INTO Game VALUES (3, '2006-06-29 18:41:00');

INSERT INTO Player(ID, emailAddress) VALUES (1, 'me@calvin.edu');
INSERT INTO Player VALUES (2, 'king@gmail.edu', 'The King');
INSERT INTO Player VALUES (3, 'dog@gmail.edu', 'Dogbreath');

INSERT INTO PlayerGame VALUES (1, 1, 0.00, 4);
INSERT INTO PlayerGame VALUES (1, 2, 0.00, 12);
INSERT INTO PlayerGame VALUES (1, 3, 2350.00, 28);
INSERT INTO PlayerGame VALUES (2, 1, 1000.00, 1);
INSERT INTO PlayerGame VALUES (2, 2, 0.00, 30);
INSERT INTO PlayerGame VALUES (2, 3, 500.00, 40);
INSERT INTO PlayerGame VALUES (3, 2, 0.00, 19);
INSERT INTO PlayerGame VALUES (3, 3, 5500.00, 5);

INSERT INTO Property VALUES (1, 1, 2, 0, 1);
INSERT INTO Property VALUES (2, 3, 2, 2, 0);
INSERT INTO Property VALUES (3, 2, 3, 5, 0);
INSERT INTO Property VALUES (4, 1, 1, 0, 2);

SELECT * From PlayerGame;