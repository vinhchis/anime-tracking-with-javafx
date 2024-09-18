CREATE DATABASE TrackingAnimeDB
GO

Use TrackingAnimeDB

-- 1. Account table
CREATE TABLE Account(
	account_id INT IDENTITY(1,1),
	username VARCHAR(50) UNIQUE NOT NULL,
	password VARCHAR(32) NOT NULL,
	email VARCHAR(255) UNIQUE NOT NULL,
	nickname NVARCHAR(50) UNIQUE NOT NULL,
	last_open DATETIME NOT NULL,
	PRIMARY KEY (account_id)
)

-- 2. Genre table
CREATE TABLE Genre (
	genre_id INT IDENTITY(1,1),
	name VARCHAR(50) NOT NULL UNIQUE,
	description TEXT,
	PRIMARY KEY (genre_id)
);

-- 3. Anime table
CREATE TABLE Anime(
	anime_id INT IDENTITY(1,1),
	account_id INT NOT NULL,
	title VARCHAR(50) NOT NULL,
	poster VARCHAR(100), /* link poster*/
	status TINYINT, /* 1 - Airing, 2 - Finished, 3 - Cancelled, 4 - Upcoming*/
	aried DATETIME, /* Release time */
	episodes INT, 
	new_episode INT, /* new episode */
	studio NVARCHAR(20),
	type TINYINT, /* 1 - Series, 2 - Movies, 3 - OVA */
	introduction NVARCHAR(200), /* Description */
	season TINYINT, /* 1 - Spring, 2 - Summer, 3 - Autumn, 4 - Winter*/
	nation TINYINT DEFAULT 1, /* 1 - Japan, 2 - China, 3 - Others*/
	score FLOAT DEFAULT 0,
	rankded INT DEFAULT -1,
	
	
	PRIMARY KEY (anime_id),
	FOREIGN KEY(account_id) REFERENCES Account(account_id),
)

-- 4. Schedule table
CREATE TABLE Schedule (
	schedule_id INT IDENTITY(1,1),
	anime_id INT NOT NULL,
	day TINYINT NOT NULL, /* 2 - Monday, 3 - Tueday, ... , 8 - Sunday*/
	time TIME,

	PRIMARY KEY (schedule_id),
	FOREIGN KEY (anime_id) REFERENCES Anime(anime_id)
);

-- 5. Genre with Anime table
CREATE TABLE GenreWithAnime(
	anime_id INT NOT NULL,
	genre_id INT NOT NULL,

	FOREIGN KEY (anime_id) REFERENCES Anime(anime_id),
	FOREIGN KEY (genre_id) REFERENCES Genre(genre_id),
	CONSTRAINT PK_ID PRIMARY KEY (anime_id, genre_id)
)

-- 6. Tracking List table
CREATE TABLE TrackingList(
	tl_id INT IDENTITY(1,1),
	account_id INT NOT NULL,
	created_day DATE NOT NULL,
	number_of_anime INT DEFAULT 0,
	mode BIT DEFAULT 1, /* 0 - Once a day, 1 - time of tracking anime */
	last_updated DATETIME,

	PRIMARY KEY (tl_id),
	FOREIGN KEY(account_id) REFERENCES Account(account_id)
)

-- 7. Tracking Anime
CREATE table TrackingAnime(
	tl_id INT NOT NULL,
	anime_id INT NOT NULL,
	status TINYINT NOT NULL,
	/*
		1. Plan to Watch
		2. Watching
		3. Completed
		4. On Hold
		5. Dropped
	*/
	last_watched_episode INT,
	isFavorite BIT DEFAULT 0,

	FOREIGN KEY(tl_id) REFERENCES TrackingList(tl_id),
	FOREIGN KEY(anime_id) REFERENCES Anime(anime_id),
	CONSTRAINT PK_TL_ID PRIMARY KEY (tl_id, anime_id)
)

-- 8. Notification
CREATE TABLE Notification(
	id INT IDENTITY(1,1),
	ref_tl_id INT NOT NULL,
	ref_anime_id INT NOT NULL,
	time DATETIME NOT NULL,
	isChecked BIT DEFAULT 0, /* 1 - Notification checked, 0 - not checked */

	PRIMARY KEY (id),
	FOREIGN KEY (ref_tl_id, ref_anime_id) REFERENCES TrackingAnime(tl_id, anime_id),
)

--DROP DATABASE TrackingAnimeDB 