MERGE INTO funkos (id, nombre, precio, cantidad, categoria) KEY(id) VALUES
    (1, 'Mickey Mouse', 15.99, 10, 'DISNEY'),
    (2, 'Stitch',       17.00,  8, 'DISNEY'),
    (3, 'Spider-Man',   19.99,  5, 'MARVEL'),
    (4, 'Iron Man',     21.50,  2, 'MARVEL'),
    (5, 'Goku',         22.50,  3, 'ANIME'),
    (6, 'Naruto',       18.75,  0, 'ANIME');

ALTER TABLE funkos ALTER COLUMN id RESTART WITH 7;