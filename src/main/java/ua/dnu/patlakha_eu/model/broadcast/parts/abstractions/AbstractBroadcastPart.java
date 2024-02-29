package main.java.ua.dnu.patlakha_eu.model.broadcast.parts.abstractions;

import java.math.BigDecimal;

public abstract class AbstractBroadcastPart {
    private final int id;
    private final int duration;
    private final BigDecimal cost;

    protected AbstractBroadcastPart(int id, int duration, int cost) {
        this.id = id;
        this.duration = duration;
        this.cost = new BigDecimal(cost);
    }

    public int getDuration() {
        return duration;
    }
    public int getCost() {
        return cost.intValue();
    }
    public int getId() {
        return id;
    }
}
