USE RadioStation;

INSERT INTO Broadcasts (broadcast_duration) VALUES
(60),
(45),
(20);

INSERT INTO Interviews (interviewee, interview_duration) VALUES
('Beyoncé', 2),
('Justin Bieber', 1),
('Taylor Swift', 3),
('Drake', 4),
('Ariana Grande', 5),
('Billie Eilish', 2),
('Cardi B', 1),
('Post Malone', 3),
('Travis Scott', 1),
('Kendrick Lamar', 5);

INSERT INTO Advertisements (product_name, advertising_duration) VALUES
('Branded Pens', 2),
('Promo T-Shirts', 3),
('Advertising Mugs', 4),
('Branded USB Sticks', 5),
('Custom Keychains', 3),
('Logo Water Bottles', 1),
('Promotional Tote Bags', 2),
('Custom Calendars', 5),
('Logo Caps', 3),
('Promotional Notebooks', 1);

INSERT INTO Songs (artist_name, song_name, song_duration) VALUES
('Beyoncé', 'Halo', 3),
('Justin Bieber', 'Sorry', 1),
('Taylor Swift', 'Love Story', 2),
('Drake', 'Gods Plan', 5),
('Ariana Grande', 'Thank U, Next', 4),
('Billie Eilish', 'Bad Guy', 3),
('Cardi B', 'I Like It', 2),
('Post Malone', 'Circles', 3),
('Travis Scott', 'Sicko Mode', 5),
('Kendrick Lamar', 'HUMBLE.', 2);

INSERT INTO BroadcastInterview (broadcast_id, interview_id) VALUES
(1, 3),
(1, 7),
(2, 10),
(3, 5);

INSERT INTO BroadcastAdvertising (broadcast_id, advertising_id) VALUES
(1, 2),
(2, 6),
(2, 10),
(3, 4);

INSERT INTO BroadcastSong (broadcast_id, song_id) VALUES
(1, 8),
(2, 9),
(3, 4),
(3, 1);

INSERT INTO RegularPresenters (regular_presenter_name, work_experience) VALUES
('Іван', 5),
('Анатолій', 3),
('Олексій', 9),
('Олена', 8),
('Оксана', 2);

INSERT INTO GuestPresenters (guest_presenter_name, resume) VALUES
('Євгеній', 'Резюме Євгенія'),
('Марія', 'Резюме Марії');

INSERT INTO RegularPresenterBroadcast (regular_presenter_id, broadcast_id) VALUES
(1, 3),
(1, 2),
(2, 1),
(2, 3),
(3, 1),
(4, 2),
(5, 1),
(5, 2),
(5, 3);

INSERT INTO GuestPresenterBroadcast (guest_presenter_id, broadcast_id) VALUES
(1, 1),
(1, 2),
(2, 2),
(2, 3);