CREATE TRIGGER TRG_UpdateBroadcastInterviewAddingTime
ON BroadcastInterview
AFTER INSERT
AS
BEGIN
    UPDATE BroadcastInterview
    SET broadcast_adding_time = CURRENT_TIMESTAMP
    WHERE broadcast_interview_id IN (SELECT DISTINCT broadcast_interview_id FROM Inserted)
END;