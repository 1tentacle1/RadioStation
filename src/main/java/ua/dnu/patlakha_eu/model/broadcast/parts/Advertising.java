package main.java.ua.dnu.patlakha_eu.model.broadcast.parts;

import main.java.ua.dnu.patlakha_eu.model.broadcast.parts.abstractions.AbstractBroadcastPart;
import main.java.ua.dnu.patlakha_eu.model.broadcast.parts.visitor.interfaces.Visitable;
import main.java.ua.dnu.patlakha_eu.model.broadcast.parts.visitor.interfaces.Visitor;

public class Advertising extends AbstractBroadcastPart implements Visitable {
    private final String productName;

    public Advertising(int id, String productName, int duration) {
        super(id, duration, duration * 5 * 60);

        this.productName = productName;
    }

    public String getProductName() {
        return productName;
    }

    @Override
    public void accept(Visitor visitor) {
        visitor.visit(this);
    }
}