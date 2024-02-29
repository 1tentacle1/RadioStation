package main.java.ua.dnu.patlakha_eu.model.broadcast;

import main.java.ua.dnu.patlakha_eu.model.broadcast.parts.abstractions.AbstractBroadcastPart;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

public class Broadcast {
    private int id;
    private final int broadcastDuration;
    private final List<AbstractBroadcastPart> broadcastParts;
    private BigDecimal income;

    private int partsDuration;
    private int paidContentDuration;

    public Broadcast(int id, int broadcastDuration) {
        this.id = id;
        this.broadcastDuration = broadcastDuration;

        income = new BigDecimal(0);
        broadcastParts = new ArrayList<>();

        partsDuration = 0;
        paidContentDuration = 0;
    }

    public void addPart(AbstractBroadcastPart part) {
        int partDuration = part.getDuration();
        int partCost = part.getCost();

        if (isPartDurationNotExceeded(partDuration)) {
            if (partCost > 0) {
                if (isPaidContentHalfDurationNotExceeded(partDuration)) {
                    paidContentDuration += partDuration;

                    income = income.add(new BigDecimal(partCost));
                } else {
                    throw new IllegalArgumentException("Тривалість платного контенту не може перевищувати 50% від загальної " +
                            "тривалості трансляцій");
                }
            }
            partsDuration += partDuration;

            broadcastParts.add(part);
        }
        else {
            throw new IllegalArgumentException("Неприпустимо додавання додаткової частини до трансляцій, якщо загальна кількість " +
                    "хвилин передачі даних більше, ніж тривалість всієї трансляції");
        }
    }

    private boolean isPartDurationNotExceeded(int partDuration){
        return partDuration + partsDuration <= broadcastDuration;
    }

    private boolean isPaidContentHalfDurationNotExceeded(int partDuration) {
        return partDuration + paidContentDuration <= broadcastDuration / 2;
    }

    public int getRemainingPartsMinutes() {
        return broadcastDuration - partsDuration;
    }

    public int getRemainingPaidContentMinutes() {
        return Math.min(broadcastDuration / 2 - paidContentDuration, getRemainingPartsMinutes());
    }

    public List<AbstractBroadcastPart> getBroadcastParts() {
        return new ArrayList<>(broadcastParts);
    }

    public int getId() {
        return id;
    }
    public int getBroadcastDuration() {
        return broadcastDuration;
    }

    public int getIncome() {
        return income.intValue();
    }
}