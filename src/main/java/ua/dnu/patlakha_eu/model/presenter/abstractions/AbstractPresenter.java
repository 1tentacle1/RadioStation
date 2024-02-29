package main.java.ua.dnu.patlakha_eu.model.presenter.abstractions;

import main.java.ua.dnu.patlakha_eu.model.broadcast.Broadcast;

import java.util.ArrayList;
import java.util.List;

public abstract class AbstractPresenter {
    private final int id;
    private final String name;
    private List<Broadcast> conductedBroadcasts;

    protected AbstractPresenter(int id, String name) {
        this.id = id;
        this.name = name;

        conductedBroadcasts = new ArrayList<>();
    }

    public void addConductedBroadcast(Broadcast broadcast) {
        conductedBroadcasts.add(broadcast);
    }

    public String getName() {
        return name;
    }

    public List<Broadcast> getConductedBroadcasts() {
        return new ArrayList<>(conductedBroadcasts);
    }

    public int getId() {
        return id;
    }

    public void setConductedBroadcasts(List<Broadcast> conductedBroadcasts) {
        this.conductedBroadcasts = conductedBroadcasts;
    }
}
