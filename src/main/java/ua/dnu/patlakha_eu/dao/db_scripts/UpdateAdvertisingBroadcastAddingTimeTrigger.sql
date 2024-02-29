CREATE TRIGGER TRG_UpdateBroadcastAdvertisingAddingTime
ON BroadcastAdvertising
AFTER INSERT
AS
BEGIN
    UPDATE BroadcastAdvertising
    SET broadcast_adding_time = CURRENT_TIMESTAMP
    WHERE broadcast_advertising_id IN (SELECT DISTINCT broadcast_advertising_id FROM Inserted)
END;