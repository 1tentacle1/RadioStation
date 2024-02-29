package main.java.ua.dnu.patlakha_eu.model.presenter;

import main.java.ua.dnu.patlakha_eu.model.presenter.abstractions.AbstractPresenter;

public class RegularPresenter extends AbstractPresenter {
    private int workExperience;

    public RegularPresenter(int id, String name, int workExperience) {
        super(id, name);

        this.workExperience = workExperience;
    }

    public int getWorkExperience() {
        return workExperience;
    }

    public void setWorkExperience(int workExperience) {
        this.workExperience = workExperience;
    }
}
