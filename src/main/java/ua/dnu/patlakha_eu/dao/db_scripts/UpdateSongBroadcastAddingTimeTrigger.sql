CREATE TRIGGER TRG_UpdateBroadcastSongAddingTime
ON BroadcastSong
AFTER INSERT
AS
BEGIN
    UPDATE BroadcastSong
    SET broadcast_adding_time = CURRENT_TIMESTAMP
    WHERE broadcast_song_id IN (SELECT DISTINCT broadcast_song_id FROM Inserted)
END;