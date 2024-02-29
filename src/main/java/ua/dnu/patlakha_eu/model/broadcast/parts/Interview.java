package main.java.ua.dnu.patlakha_eu.model.broadcast.parts;

import main.java.ua.dnu.patlakha_eu.model.broadcast.parts.abstractions.AbstractBroadcastPart;
import main.java.ua.dnu.patlakha_eu.model.broadcast.parts.visitor.interfaces.Visitable;
import main.java.ua.dnu.patlakha_eu.model.broadcast.parts.visitor.interfaces.Visitor;

public class Interview extends AbstractBroadcastPart implements Visitable {
    private final String interviewee;

    public Interview(int id, String interviewee, int duration) {
        super(id, duration, duration * 30);

        this.interviewee = interviewee;
    }

    public String getInterviewee() {
        return interviewee;
    }

    @Override
    public void accept(Visitor visitor) {
        visitor.visit(this);
    }
}