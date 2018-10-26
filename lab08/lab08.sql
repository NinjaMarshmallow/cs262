-- Lab8 Queries
-- Jason Klaassen

-- 8.1
Use Monopoly
Go

-- a.
Select * From Game
Order By time DESC

-- b.
Select * From Game
Where time > DATEADD(DAY, -7, GETDATE())

-- c.
Select * From Player
Where name IS NOT NULL

-- d.
Select PlayerID From PlayerGame
Where score > 2000

-- e.
Select * From Player
Where emailAddress LIKE '%@gmail.%' -- Should probably be '%gmail.com', but the actual data has @gmail.edu

-- 8.2

-- a.
Select score
From PlayerGame pg
Where pg.playerID = (Select ID From Player Where name = 'The King')

-- b.
Select name 
From Player
Where ID = (Select TOP(1) playerID From PlayerGame pg
JOIN Game ga On pg.gameID = ga.ID
Where time = '2006-06-28 13:20:00'
Order By score DESC)

-- c.
-- A self join will allow you to compare data within a table without sorting using an order by, 
-- but using order by and TOP(1) instead of a self join is more intutive

-- d.
-- If database objects fit more than one role, a self join can be used to link them. Ex. 
-- Employees who each have a manager and managers who are employees, have employees under them, and have a manager.


