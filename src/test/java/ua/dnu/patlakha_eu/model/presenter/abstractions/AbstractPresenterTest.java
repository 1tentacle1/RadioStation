package test.java.ua.dnu.patlakha_eu.model.presenter.abstractions;

import main.java.ua.dnu.patlakha_eu.model.broadcast.Broadcast;
import main.java.ua.dnu.patlakha_eu.model.presenter.RegularPresenter;
import main.java.ua.dnu.patlakha_eu.model.presenter.abstractions.AbstractPresenter;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertTrue;

public class AbstractPresenterTest {
    private AbstractPresenter presenter;

    @BeforeEach
    public void setUp() {
        presenter = new RegularPresenter(1, "Іван", 5);
    }

    @Test
    void testAddSomeConductedBroadcast() {
        Broadcast broadcast1 = new Broadcast(1,60);
        Broadcast broadcast2 = new Broadcast(2,100);

        presenter.addConductedBroadcast(broadcast1);
        presenter.addConductedBroadcast(broadcast2);

        assertTrue(presenter.getConductedBroadcasts().contains(broadcast1));
        assertTrue(presenter.getConductedBroadcasts().contains(broadcast2));
    }
}
