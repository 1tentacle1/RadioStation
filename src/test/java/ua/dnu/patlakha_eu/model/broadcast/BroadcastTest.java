package test.java.ua.dnu.patlakha_eu.model.broadcast;

import main.java.ua.dnu.patlakha_eu.model.broadcast.Broadcast;
import main.java.ua.dnu.patlakha_eu.model.broadcast.parts.Advertising;
import main.java.ua.dnu.patlakha_eu.model.broadcast.parts.Interview;
import main.java.ua.dnu.patlakha_eu.model.broadcast.parts.Song;
import main.java.ua.dnu.patlakha_eu.model.broadcast.parts.abstractions.AbstractBroadcastPart;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class BroadcastTest {
    private Broadcast broadcast;

    @BeforeEach
    public void setUp() {
        broadcast = new Broadcast(1,60);
    }

    @Test
    public void testAddSong() {
        AbstractBroadcastPart part = new Song(1, "The Beatles", "Let It Be", 2);

        broadcast.addPart(part);

        assertTrue(broadcast.getBroadcastParts().contains(part));

        assertEquals(58, broadcast.getRemainingPartsMinutes());
        assertEquals(30, broadcast.getRemainingPaidContentMinutes());

        assertEquals(0, broadcast.getIncome());
    }

    @Test
    public void testAddAdvertising() {
        AbstractBroadcastPart part = new Advertising(1, "SomeCarModel", 3);

        broadcast.addPart(part);

        assertTrue(broadcast.getBroadcastParts().contains(part));

        assertEquals(57, broadcast.getRemainingPartsMinutes());
        assertEquals(27, broadcast.getRemainingPaidContentMinutes());

        assertEquals(900, broadcast.getIncome());
    }

    @Test
    public void testAddInterview() {
        AbstractBroadcastPart part = new Interview(1, "Snoop Dogg", 15);

        broadcast.addPart(part);

        assertTrue(broadcast.getBroadcastParts().contains(part));

        assertEquals(45, broadcast.getRemainingPartsMinutes());
        assertEquals(15, broadcast.getRemainingPaidContentMinutes());

        assertEquals(450, broadcast.getIncome());
    }

    @Test
    public void testAddSomeParts() {
        AbstractBroadcastPart part1 = new Song(1,"3", "4", 30);
        AbstractBroadcastPart part2 = new Song(2,"8", "8", 5);

        AbstractBroadcastPart part3 = new Interview(3, "1", 15);
        AbstractBroadcastPart part4 = new Interview(4, "8", 5);

        AbstractBroadcastPart part5 = new Advertising(5, "2", 3);
        AbstractBroadcastPart part6 = new Advertising(6, "7", 2);

        broadcast.addPart(part1);
        broadcast.addPart(part2);
        broadcast.addPart(part3);
        broadcast.addPart(part4);
        broadcast.addPart(part5);
        broadcast.addPart(part6);

        assertTrue(broadcast.getBroadcastParts().contains(part1));
        assertTrue(broadcast.getBroadcastParts().contains(part2));
        assertTrue(broadcast.getBroadcastParts().contains(part3));
        assertTrue(broadcast.getBroadcastParts().contains(part4));
        assertTrue(broadcast.getBroadcastParts().contains(part5));
        assertTrue(broadcast.getBroadcastParts().contains(part6));

        assertEquals(0, broadcast.getRemainingPartsMinutes());
        assertEquals(0, broadcast.getRemainingPaidContentMinutes());

        assertEquals(2100, broadcast.getIncome());
    }

    @Test
    public void testAddPartExceedsDurationTrowsIllegalArgumentException() {
        AbstractBroadcastPart part = new Interview(1, "Babyface", 61);

        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> broadcast.addPart(part));

        assertEquals("Неприпустимо додавання додаткової частини до трансляцій, якщо загальна кількість " +
                "хвилин передачі даних більше, ніж тривалість всієї трансляції", exception.getMessage());
    }

    @Test
    public void testAddPaidContentExceedsHalfDurationTrowsIllegalArgumentException() {
        AbstractBroadcastPart part = new Interview(2, "Kanye West", 31);

        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> broadcast.addPart(part));

        assertEquals("Тривалість платного контенту не може перевищувати 50% від " +
                "загальної тривалості трансляцій", exception.getMessage());
    }
}