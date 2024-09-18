/* Import Data to Tracking Anime Database*/
USE TrackingAnimeDB
-- 1. Insert statements (1 user) for Account table
INSERT INTO Account (username, password, email, nickname, last_open)
VALUES ('user1', '123456', 'user1@gmail.com',N'Hinata', '2024-5-21 16:00:00');

--select * from Account

-- 2. Insert statements (10 genres) for Genre table
INSERT INTO Genre (name, description)
VALUES
  ('Action', 'Anime in the action genre features characters engaged in physical feats and combat.'),
  ('Adventure', 'Adventure anime often involve exploration, travel, and the discovery of new and exciting places.'),
  ('Comedy', 'Comedy anime focuses on humor and lightheartedness, aiming to make viewers laugh.'),
  ('Drama', 'Drama anime explore serious themes and emotional situations, often evoking feelings in the viewer.'),
  ('Fantasy', 'Fantasy anime features elements of magic, supernatural abilities, and mythical creatures.'),
  ('Mecha', 'Mecha anime prominently feature giant robots or machines piloted by humans.'),
  ('Mystery', 'Mystery anime involve solving puzzles, uncovering secrets, and uncovering the truth behind a crime or event.'),
  ('Romance', 'Romance anime focus on love stories and the development of relationships between characters.'),
  ('Sci-Fi', 'Sci-Fi anime explore themes of science fiction, technology, space exploration, and futuristic settings.'),
  ('Slice of Life', 'Slice of Life anime depict everyday experiences and situations in a realistic or lighthearted way.');

  -- 3. Insert statements (7 Anime) for Anime table
 INSERT INTO Anime (account_id, title, poster, status, aried, episodes, new_episode, studio, type, introduction, season, nation)
VALUES 
		(1, 'One Piece', 'https://cdn.myanimelist.net/images/anime/1244/138851.jpg', 1, '1999-07-20', NULL, 1102, 'Toei Animation', 1, 'Monkey D. Luffy recruits crewmates to find the legendary One Piece...', NULL, 1),
		(1, 'Solo Leveling', 'https://cdn.myanimelist.net/images/anime/1926/140799.jpg', 2, '2024-07-01', 12, 12, 'D&C Media', 1, 'A low-ranked hunter gets a chance to change his fate...', 4, 1),
		(1, 'Kaiju No. 8', 'https://cdn.myanimelist.net/images/anime/1370/140362.jpg', 1, '2024-04-13', 12, 3, 'SHUEISHA', 1, 'Defense Force member Kafka Hibino dreams of joining...', 1, 1),
		(1, 'Black Clover', 'https://cdn.myanimelist.net/images/anime/2/88336.jpg', 3, '2017-03-10', 170, 170, 'Pierrot', 1, 'Asta, a magicless boy in a world of magic, dreams of becoming...', 3, 1),
		(1, 'Your Name', 'https://cdn.myanimelist.net/images/anime/5/87048.jpg', 1, '2016-08-26', 1, 1, 'CoMix Wave Films', 2, 'Two teenagers discover they are linked and can swap bodies...', NULL, 1),
		(1, 'Fights Break Sphere', 'https://cdn.myanimelist.net/images/anime/1206/142245.jpg', 1, '2022-10-22', 104, 95, 'Ufotable', 1, 'After defeating the Nine-Colored Heavenly Thunder Beast and becoming a Dou Zun, Xiao Yan travels to the Central Plains to seek out the Three Great Ancient Factions', 2, 2),
		(1, 'Oshi no Ko (Season 2)', 'https://cdn.myanimelist.net/images/anime/NONE/142710.jpg', 4, NULL, NULL, 0, 'Doga Kobo', 1, 'Second season of "Oshi no Ko".', 3, 1)


--SELECT * FROM Anime

-- 4. Insert statements for Schedule
INSERT INTO Schedule(anime_id, day, time)
VALUES
	(1, 7, '12:00:00'),
	(1, 4, '18:45:00'),
	(3, 7, '07:00:00'),           
	(6, 3, '23:30:00')

--select * from Schedule
delete from Schedule

-- 5. Insert statement for GenreWithAnime
INSERT INTO GenreWithAnime(anime_id, genre_id)
VALUES
	(1, 1),
	(1, 2),
	(2, 1),
	(2, 5),
	(3, 1),
	(3, 5),
	(4, 5),
	(4, 7),
	(5, 8),
	(6, 1),
	(6, 10),
	(7, 4)

--select * from GenreWithAnime

-- 6. Inser statements for TrackingList table
INSERT INTO TrackingList(account_id , created_day, number_of_anime, mode, last_updated)
VALUES
	(1, '2024-5-1', 3, 2, '2024-5-6')

--SELECT * FROM TrackingList

-- 7. Insert statements for TrackingAnime Table 
INSERT INTO TrackingAnime(tl_id, anime_id, status, last_watched_episode, isFavorite)
VALUES
	(1, 1, 2, 1102, 1),
	(1, 4, 3, 500, 0),
	(1, 7, 1, NULL, 1)

--select * from TrackingAnime

-- 8. Insert statements for Notification table
INSERT INTO Notification(ref_tl_id, ref_anime_id, time, isChecked)
VALUES
	(1, 4, '2024-5-2 18:45:00', 1),
	(1, 4, '2024-5-5 12:00:00', 0)
