use SuperHeroes;


SELECT * from Hero h
JOIN sighting s ON s.hero_id = h.hero_id 
WHERE s.dateT = 2015/11/15;

SELECT * from Location
join sighting s on s.location_id = location.location_id
WHERE s.dateT = 2015/11/15;



SELECT * FROM  Hero h , Location l, Sighting s
WHERE s.hero_id = h.hero_id
AND s.location_id = l.location_id 
AND s.dateT = '2015/11/15';


SELECT *
FROM (((Hero h, Location l) 
JOIN Sighting s ON s.hero_id = h.hero_id)
JOIN Sighting s2 ON s2.location_id = l.location_id )
WHERE s.dateT = 2015/11/15;


SELECT location_name,heroName
FROM sighting s
join hero h ON s.hero_id=h.hero_id join location l ON s.location_id=l.location_id;

SELECT h.* FROM Hero h 
JOIN sighting s ON s.hero_id = h.hero_id 
WHERE s.location_id = 1;



