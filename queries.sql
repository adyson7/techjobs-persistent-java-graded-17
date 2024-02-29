-- Part 1:
-- Columns and data types in the job table:
CREATE TABLE job (
    id INT DEFAULT NEXTVAL('job_seq') PRIMARY KEY,
    name VARCHAR(255) NOT NULL,
    employer VARCHAR(255) NOT NULL,
    location VARCHAR(255) NOT NULL,
    position_type VARCHAR(255) NOT NULL,
    core_competency VARCHAR(255) NOT NULL
);


--Part 2
-- SQL query to select names of employers in St. Louis City
SELECT name
FROM employer
WHERE location = 'St. Louis City';


--Part 3
DROP TABLE job;


--Part 4
--select distinct skill names from the "skill" table
-- join the "skill" table with the "job skill" table
-- ensures only skills associated with the job are included
--alphabetical list of names

SELECT DISTINCT s.name
FROM Skill s
JOIN Job_Skill js ON s.id = js.skill_id
JOIN Job j ON js.job_id = j.id
ORDER BY s.name ASC;

