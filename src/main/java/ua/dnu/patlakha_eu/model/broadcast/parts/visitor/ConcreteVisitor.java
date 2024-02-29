package main.java.ua.dnu.patlakha_eu.model.broadcast.parts.visitor;

import main.java.ua.dnu.patlakha_eu.model.broadcast.parts.Advertising;
import main.java.ua.dnu.patlakha_eu.model.broadcast.parts.Interview;
import main.java.ua.dnu.patlakha_eu.model.broadcast.parts.Song;
import main.java.ua.dnu.patlakha_eu.model.broadcast.parts.visitor.interfaces.Visitor;

public class ConcreteVisitor implements Visitor {
    private final StringBuilder result = new StringBuilder();
    @Override
    public void visit(Interview interview) {
        result.append("\t\tІнтерв'ю ").append(interview.getId()).append(": ")
                .append("Тривалість: ").append(interview.getDuration()).append("; ")
                .append("Ціна: ").append(interview.getCost()).append("; ")
                .append("Інтерв'юйований: ").append(interview.getInterviewee()).append("\n");
    }

    @Override
    public void visit(Advertising advertising) {
        result.append("\t\tРеклама ").append(advertising.getId()).append(": ")
                .append("Тривалість: ").append(advertising.getDuration()).append("; ")
                .append("Ціна: ").append(advertising.getCost()).append("; ")
                .append("Рекламний продукт: ").append(advertising.getProductName()).append("\n");
    }

    @Override
    public  void visit(Song song) {
        result.append("\t\tПісня ").append(song.getId()).append(": ")
                .append("Тривалість: ").append(song.getDuration()).append("; ")
                .append("Ціна: ").append(song.getCost()).append("; ")
                .append("Виконавець: ").append(song.getArtistName()).append(" Назва пісні: ").append(song.getSongName()).append("\n");
    }

    @Override
    public String getResult() {
        return result.toString();
    }
}
