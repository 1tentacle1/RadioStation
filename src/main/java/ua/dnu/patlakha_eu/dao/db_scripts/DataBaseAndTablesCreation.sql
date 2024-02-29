CREATE DATABASE RadioStation;

USE RadioStation;

CREATE TABLE Broadcasts (
    broadcast_id INT IDENTITY(1,1),
    broadcast_duration INT NOT NULL,
	CONSTRAINT PK_Broadcast PRIMARY KEY (broadcast_id)
);

CREATE TABLE Interviews (
	interview_id INT IDENTITY(1,1),
	interviewee VARCHAR(100) NOT NULL,
	interview_duration INT NOT NULL,
	CONSTRAINT PK_Interview PRIMARY KEY (interview_id)
);

CREATE TABLE Advertisements (
	advertising_id INT IDENTITY(1,1),
	product_name VARCHAR(100) NOT NULL,
	advertising_duration INT NOT NULL,
	CONSTRAINT PK_Advertising PRIMARY KEY (advertising_id)
);

CREATE TABLE Songs (
	song_id INT IDENTITY(1,1),
	artist_name VARCHAR(100) NOT NULL,
	song_name VARCHAR(100) NOT NULL,
	song_duration INT NOT NULL,
	CONSTRAINT PK_Song PRIMARY KEY (song_id)
);

CREATE TABLE BroadcastInterview (
	broadcast_interview_id INT IDENTITY(1, 1),
	broadcast_id INT,
	interview_id INT,
	broadcast_adding_time DATETIME,
	CONSTRAINT PK_BroadcastInterview PRIMARY KEY (broadcast_interview_id),
	CONSTRAINT FK_BroadcastInterview_Broadcast FOREIGN KEY (broadcast_id) REFERENCES Broadcasts (broadcast_id),
	CONSTRAINT FK_BroadcastInterview_Interview FOREIGN KEY (interview_id) REFERENCES Interviews (interview_id)
);

CREATE TABLE BroadcastAdvertising (
	broadcast_advertising_id INT IDENTITY(1, 1),
	broadcast_id INT,
	advertising_id INT,
	broadcast_adding_time DATETIME,
	CONSTRAINT PK_BroadcastAdvertising PRIMARY KEY (broadcast_advertising_id),
	CONSTRAINT FK_BroadcastAdvertising_Broadcast FOREIGN KEY (broadcast_id) REFERENCES Broadcasts (broadcast_id),
	CONSTRAINT FK_BroadcastAdvertising_Advertising FOREIGN KEY (advertising_id) REFERENCES Advertisements (advertising_id)
);

CREATE TABLE BroadcastSong (
	broadcast_song_id INT IDENTITY(1, 1),
	broadcast_id INT,
	song_id INT,
	broadcast_adding_time DATETIME,
	CONSTRAINT PK_BroadcastSong PRIMARY KEY (broadcast_song_id),
	CONSTRAINT FK_BroadcastSong_Broadcast FOREIGN KEY (broadcast_id) REFERENCES Broadcasts (broadcast_id),
	CONSTRAINT FK_BroadcastSong_Song FOREIGN KEY (song_id) REFERENCES Songs (song_id)
);

CREATE TABLE RegularPresenters (
	regular_presenter_id INT IDENTITY(1,1),
	regular_presenter_name VARCHAR(100) NOT NULL,
	work_experience INT NOT NULL,
	CONSTRAINT PK_RegularPresenter PRIMARY KEY (regular_presenter_id)
);

CREATE TABLE GuestPresenters (
	guest_presenter_id INT IDENTITY(1,1),
	guest_presenter_name VARCHAR(100) NOT NULL,
	resume VARCHAR(100) NOT NULL,
	CONSTRAINT PK_GuestPresenter PRIMARY KEY (guest_presenter_id)
);

CREATE TABLE RegularPresenterBroadcast (
	regular_presenter_broadcast_id INT IDENTITY(1, 1),
	regular_presenter_id INT,
	broadcast_id INT,

	CONSTRAINT PK_RegularPresenterBroadcast PRIMARY KEY (regular_presenter_broadcast_id),
	CONSTRAINT FK_RegularPresenterBroadcast_RegularPresenter FOREIGN KEY (regular_presenter_id) REFERENCES RegularPresenters (regular_presenter_id),
	CONSTRAINT FK_RegularPresenterBroadcast_Broadcast FOREIGN KEY (broadcast_id) REFERENCES Broadcasts (broadcast_id)
);

CREATE TABLE GuestPresenterBroadcast (
	guest_presenter_broadcast_id INT IDENTITY(1, 1),
	guest_presenter_id INT,
	broadcast_id INT,

	CONSTRAINT PK_GuestPresenterBroadcast PRIMARY KEY (guest_presenter_broadcast_id),
	CONSTRAINT FK_GuestPresenterBroadcast_GuestPresenter FOREIGN KEY (guest_presenter_id) REFERENCES GuestPresenters (guest_presenter_id),
	CONSTRAINT FK_GuestPresenterBroadcast_Broadcast FOREIGN KEY (broadcast_id) REFERENCES Broadcasts (broadcast_id)
);
