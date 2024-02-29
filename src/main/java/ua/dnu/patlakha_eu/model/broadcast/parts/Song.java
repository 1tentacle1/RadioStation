package main.java.ua.dnu.patlakha_eu.model.broadcast.parts;

import main.java.ua.dnu.patlakha_eu.model.broadcast.parts.abstractions.AbstractBroadcastPart;
import main.java.ua.dnu.patlakha_eu.model.broadcast.parts.visitor.interfaces.Visitable;
import main.java.ua.dnu.patlakha_eu.model.broadcast.parts.visitor.interfaces.Visitor;

public class Song extends AbstractBroadcastPart implements Visitable {
    private final String artistName;
    private final String songName;

    public Song(int id, String artistName, String songName, int duration) {
        super(id, duration, 0);

        this.artistName = artistName;
        this.songName = songName;
    }

    public String getArtistName() {
        return artistName;
    }

    public String getSongName() {
        return songName;
    }

    @Override
    public void accept(Visitor visitor) {
        visitor.visit(this);
    }
}