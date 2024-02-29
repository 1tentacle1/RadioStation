package main.java.ua.dnu.patlakha_eu.model.presenter;

import main.java.ua.dnu.patlakha_eu.model.presenter.abstractions.AbstractPresenter;

public class GuestPresenter extends AbstractPresenter {
    private String resume;

    public GuestPresenter(int id, String name, String resume) {
        super(id, name);

        this.resume = resume;
    }

    public String getResume() {
        return resume;
    }

    public void setResume(String resume) {
        this.resume = resume;
    }
}
