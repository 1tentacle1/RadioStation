package main.java.ua.dnu.patlakha_eu.model.broadcast.parts.visitor.interfaces;

import main.java.ua.dnu.patlakha_eu.model.broadcast.parts.Advertising;
import main.java.ua.dnu.patlakha_eu.model.broadcast.parts.Interview;
import main.java.ua.dnu.patlakha_eu.model.broadcast.parts.Song;

public interface Visitor {
    void visit(Interview interview);
    void visit(Advertising advertising);
    void visit(Song song);
    String getResult();
}
