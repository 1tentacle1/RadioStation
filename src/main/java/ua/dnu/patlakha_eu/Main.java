package main.java.ua.dnu.patlakha_eu;

import main.java.ua.dnu.patlakha_eu.dao.broadcast.BroadcastDAO;
import main.java.ua.dnu.patlakha_eu.dao.presenter.GuestPresenterDAO;
import main.java.ua.dnu.patlakha_eu.dao.presenter.RegularPresenterDAO;
import main.java.ua.dnu.patlakha_eu.model.broadcast.Broadcast;
import main.java.ua.dnu.patlakha_eu.model.broadcast.parts.Advertising;
import main.java.ua.dnu.patlakha_eu.model.broadcast.parts.Interview;
import main.java.ua.dnu.patlakha_eu.model.broadcast.parts.Song;
import main.java.ua.dnu.patlakha_eu.model.broadcast.parts.abstractions.AbstractBroadcastPart;
import main.java.ua.dnu.patlakha_eu.model.broadcast.parts.visitor.ConcreteVisitor;
import main.java.ua.dnu.patlakha_eu.model.broadcast.parts.visitor.interfaces.Visitable;
import main.java.ua.dnu.patlakha_eu.model.broadcast.parts.visitor.interfaces.Visitor;
import main.java.ua.dnu.patlakha_eu.model.presenter.GuestPresenter;
import main.java.ua.dnu.patlakha_eu.model.presenter.RegularPresenter;
import main.java.ua.dnu.patlakha_eu.model.presenter.abstractions.AbstractPresenter;

import java.util.*;

public class Main {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        BroadcastDAO broadcastDAO = new BroadcastDAO();
        RegularPresenterDAO regularPresenterDAO = new RegularPresenterDAO();
        GuestPresenterDAO guestPresenterDAO = new GuestPresenterDAO();

        while (true) {
            System.out.println("""
                    Оберіть сутність:
                    1 - Трансляції
                    2 - Ведучі
                    0 - Завершити програму\s""");

            String inputValue1 = sc.nextLine();

            if (inputValue1.equals("0")) {
                break;
            }

            if (inputValue1.equals("1")) {
                while (true) {
                    System.out.println("""
                            Оберіть дію:
                            1 - Додати нову транцсляцію
                            2 - Переглянути існуючі трансляції
                            0 - Назад\s""");

                    String inputValue2 = sc.nextLine();

                    if (inputValue2.equals("0")) {
                        break;
                    }

                    if (inputValue2.equals("1")) {
                        System.out.println("Оберіть тривалість трансляції:");

                        String broadcastDurationString = sc.nextLine();

                        int broadcastDuration;
                        if (tryParseStringToInteger(broadcastDurationString)) {
                            broadcastDuration = Integer.parseInt(broadcastDurationString);
                        } else {
                            System.out.println("Невірне значення!");
                            continue;
                        }

                        int broadcastId = broadcastDAO.addBroadcast(broadcastDuration);
                        Broadcast broadcast = new Broadcast(broadcastId, broadcastDuration);

                        while (true) {
                            System.out.println("""
                                    Оберіть частину, яку хочете додати:
                                    1 - Додати інтерв'ю
                                    2 - Додати рекламу
                                    3 - Додати пісню
                                    0 - Назад\s""");

                            String inputValue3 = sc.nextLine();

                            if (inputValue3.equals("0")) {
                                break;
                            }

                            int counter = 1;

                            switch (inputValue3) {
                                case "1" -> {
                                    List<Interview> interviews = broadcastDAO.getInterviews();

                                    System.out.println("Оберіть інтерв'ю:");

                                    for (Interview interview : interviews) {
                                        System.out.println(counter++ + " - Інтерв'ю " + interview.getId() +
                                                ": Тривалість: " + interview.getDuration() +
                                                " Ціна: " + interview.getCost() +
                                                " Інтерв'юйований: " + interview.getInterviewee());
                                    }

                                    String inputValue4 = sc.nextLine();

                                    if (tryParseStringToInteger(inputValue4)) {
                                        int inputValueInteger = Integer.parseInt(inputValue4);

                                        System.out.println(addBroadcastPart(inputValueInteger, broadcast, broadcastDAO, interviews));
                                    } else {
                                        System.out.println("Невірне значення!");
                                    }
                                }
                                case "2" -> {
                                    List<Advertising> advertisements = broadcastDAO.getAdvertisements();

                                    System.out.println("Оберіть рекламу:");

                                    for (Advertising advertising : advertisements) {
                                        System.out.println(counter++ + " - Реклама " + advertising.getId() +
                                                ": Тривалість: " + advertising.getDuration() +
                                                " Ціна: " + advertising.getCost() +
                                                " Рекламний продукт: " + advertising.getProductName());
                                    }

                                    String inputValue4 = sc.nextLine();

                                    if (tryParseStringToInteger(inputValue4)) {
                                        int inputValueInteger = Integer.parseInt(inputValue4);

                                        System.out.println(addBroadcastPart(inputValueInteger, broadcast, broadcastDAO, advertisements));
                                    } else {
                                        System.out.println("Невірне значення!");
                                    }
                                }
                                case "3" -> {
                                    List<Song> songs = broadcastDAO.getSongs();

                                    System.out.println("Оберіть пісню:");

                                    for (Song song : songs) {
                                        System.out.println(counter++ + " - Пісня " + song.getId() +
                                                ": Тривалість: " + song.getDuration() +
                                                " Ціна: " + song.getCost() +
                                                " Виконавець: " + song.getArtistName() +
                                                " Назва пісні: " + song.getSongName());
                                    }

                                    String inputValue4 = sc.nextLine();

                                    if (tryParseStringToInteger(inputValue4)) {
                                        int inputValueInteger = Integer.parseInt(inputValue4);

                                        System.out.println(addBroadcastPart(inputValueInteger, broadcast, broadcastDAO, songs));
                                    } else {
                                        System.out.println("Невірне значення!");
                                    }
                                }
                                default -> System.out.println("Невірне значення!");
                            }
                        }
                    }
                    else if (inputValue2.equals("2")) {
                        System.out.print(getBroadcastsInfoString(broadcastDAO.getBroadcasts(), false));
                    }
                    else {
                        System.out.println("Невірне значення!");
                    }
                }
            }
            else if (inputValue1.equals("2")) {
                while (true) {
                    System.out.println("""
                            Оберіть дію:
                            1 - Додати нового ведучого
                            2 - Переглянути існуючих ведучих
                            0 - Назад\s""");

                    String inputValue2 = sc.nextLine();

                    if (inputValue2.equals("0")) {
                        break;
                    }

                    if (inputValue2.equals("1")) {
                        System.out.println("Введіть ім'я ведучого");

                        String presenterName = sc.nextLine();

                        System.out.println("""
                                Оберіть тип ведучого:
                                1 - Регулярний ведучий
                                2 - Запрошений ведучий\s""");

                        String inputValue3 = sc.nextLine();

                        AbstractPresenter presenter;

                        if (inputValue3.equals("1")) {
                            System.out.println("Введіть стаж роботи");

                            String workExperienceString = sc.nextLine();

                            int workExperience;
                            if (tryParseStringToInteger(workExperienceString)) {
                                workExperience = Integer.parseInt(workExperienceString);
                            } else {
                                System.out.println("Невірне значення!");
                                continue;
                            }

                            int regularPresenterId = regularPresenterDAO.addRegularPresenter(presenterName, workExperience);
                            presenter = new RegularPresenter(regularPresenterId, presenterName, workExperience);
                        }
                        else if (inputValue3.equals("2")) {
                            System.out.println("Введіть резюме");

                            String resume = sc.nextLine();

                            int guestPresenterId = guestPresenterDAO.addGuestPresenter(presenterName, resume);
                            presenter = new GuestPresenter(guestPresenterId, presenterName, resume);

                        }
                        else {
                            System.out.println("Невірне значення!");
                            continue;
                        }

                        while (true) {
                            System.out.println("""
                                    Оберіть дію:
                                    1 - Додати проведену трансляцію
                                    0 - Назад\s""");

                            String inputValue4 = sc.nextLine();

                            if (inputValue4.equals("0")) {
                                break;
                            }

                            if (inputValue4.equals("1")) {
                                List<Broadcast> broadcasts = broadcastDAO.getBroadcasts();

                                System.out.println("Оберіть трансляцію:");
                                System.out.print(getBroadcastsInfoString(broadcasts, true));

                                String selectedBroadcastString = sc.nextLine();

                                int selectedBroadcast;
                                if(tryParseStringToInteger(selectedBroadcastString)) {
                                    selectedBroadcast = Integer.parseInt(selectedBroadcastString);
                                } else {
                                    System.out.println("Невірне значення!");
                                    continue;
                                }

                                if (selectedBroadcast > 0 && selectedBroadcast <= broadcasts.size()) {
                                    presenter.addConductedBroadcast(broadcasts.get(selectedBroadcast - 1));

                                    if (presenter instanceof RegularPresenter) {
                                        regularPresenterDAO.addBroadcastToRegularPresenter(presenter.getId(),
                                                broadcasts.get(selectedBroadcast - 1).getId());
                                    }
                                    else {
                                        guestPresenterDAO.addBroadcastToGuestPresenter(presenter.getId(),
                                                broadcasts.get(selectedBroadcast - 1).getId());
                                    }

                                    System.out.println("Трансляція додана!");
                                } else {
                                    System.out.println("Невірне значення!");
                                }
                            }
                            else {
                                System.out.println("Невірне значення!");
                            }
                        }
                    }
                    else if (inputValue2.equals("2")) {
                        List<RegularPresenter> regularPresenters = regularPresenterDAO.getRegularPresenters();
                        List<GuestPresenter> guestPresenters = guestPresenterDAO.getGuestPresenters();

                        System.out.println("Регулярні ведучі:");
                        System.out.print(getPresentersInfoString(regularPresenters));

                        System.out.println("Запрошені ведучі:");
                        System.out.print(getPresentersInfoString(guestPresenters));
                    }
                    else {
                        System.out.println("Невірне значення!");
                    }
                }
            }
            else {
                System.out.println("Невірне значення!");
            }
        }
    }

    private static String addBroadcastPart(int inputValue, Broadcast broadcast, BroadcastDAO broadcastDAO, List<? extends AbstractBroadcastPart> parts) {
        if (inputValue > 0 && inputValue <= parts.size()) {
            try {
                broadcast.addPart(parts.get(inputValue - 1));
                broadcastDAO.addPartToBroadcast(broadcast.getId(), parts.get(inputValue - 1));

                return "Частина додана!\nЗалишилось " + broadcast.getRemainingPartsMinutes() + " хвилин для частин та " +
                        broadcast.getRemainingPaidContentMinutes() + " хвилин для платного контенту";

            } catch (IllegalArgumentException exception) {
                return exception.getMessage();
            }
        }
        return "Невірне значення!";
    }

    private static String getBroadcastsInfoString(List<Broadcast> broadcasts, boolean withNumbers) {
        StringBuilder result = new StringBuilder();

        for (Broadcast broadcast : broadcasts) {
            Visitor visitor = new ConcreteVisitor();

            for (AbstractBroadcastPart part : broadcast.getBroadcastParts()) {
                if (part instanceof Visitable) {
                    ((Visitable)part).accept(visitor);
                }
            }

            if (withNumbers) {
                result.append(broadcasts.indexOf(broadcast) + 1).append(" - ");
            }

            result.append("Трансляція ").append(broadcast.getId()).append(": ")
                    .append("\n\tТривалість: ").append(broadcast.getBroadcastDuration())
                    .append("\n\tВільних хвилин для частин: ").append(broadcast.getRemainingPartsMinutes())
                    .append("\n\tВільних хвилин для платного контенту: ").append(broadcast.getRemainingPaidContentMinutes())
                    .append("\n\tПрибуток: ").append(broadcast.getIncome())
                    .append("\n\tЧастини: \n").append(visitor.getResult()).append("\n");
        }
        return result.toString();
    }

    private static String getPresentersInfoString(List<? extends AbstractPresenter> presenters) {
        StringBuilder result = new StringBuilder();

        for (AbstractPresenter presenter : presenters) {
            result.append("\tВедучий ").append(presenter.getId()).append(": ")
                    .append("\n\t\tІм'я: ").append(presenter.getName());

            result.append("\n\t\tПроведені трансляції: ");

            for (Broadcast broadcast : presenter.getConductedBroadcasts()) {
                 result.append("\n\t\t\tТрансляція ").append(broadcast.getId());
            }

            if (presenter instanceof RegularPresenter regularPresenter) {
                result.append("\n\t\tСтаж роботи: ").append(regularPresenter.getWorkExperience()).append("\n\n");
            }
            if (presenter instanceof GuestPresenter guestPresenter) {
                result.append("\n\t\tРезюме: ").append(guestPresenter.getResume()).append("\n\n");
            }
        }
        return result.toString();
    }

    private static boolean tryParseStringToInteger(String value) {
        try {
            Integer.parseInt(value);
            return true;
        } catch (NumberFormatException exception) {
            return false;
        }
    }
}