package main.java.ua.dnu.patlakha_eu.dao.presenter;

import main.java.ua.dnu.patlakha_eu.dao.ConsDB;
import main.java.ua.dnu.patlakha_eu.dao.DatabaseConnection;
import main.java.ua.dnu.patlakha_eu.dao.broadcast.BroadcastDAO;
import main.java.ua.dnu.patlakha_eu.model.broadcast.Broadcast;
import main.java.ua.dnu.patlakha_eu.model.broadcast.parts.abstractions.AbstractBroadcastPart;
import main.java.ua.dnu.patlakha_eu.model.presenter.GuestPresenter;

import java.sql.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class GuestPresenterDAO {
    public int addGuestPresenter(String name, String resume) {
        int id = -1;
        String sql = "INSERT INTO " + ConsDB.GUEST_PRESENTERS_TABLE + " (guest_presenter_name, resume) VALUES (?, ?)";

        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            preparedStatement.setString(1, name);
            preparedStatement.setString(2, resume);
            preparedStatement.executeUpdate();

            try (ResultSet resultSet = preparedStatement.getGeneratedKeys()){
                if (resultSet.next()) {
                    id = resultSet.getInt(1);
                } else {
                    throw new SQLException("Помилка! Ведучий не був доданий");
                }
            } catch (SQLException exception) {
                exception.printStackTrace();
            }
        } catch (SQLException exception) {
            exception.printStackTrace();
        }
        return id;
    }

    public void addBroadcastToGuestPresenter(int regularPresenterId, int broadcastId) {
        String sql = "INSERT INTO " + ConsDB.GUEST_PRESENTER_BROADCAST + " (guest_presenter_id, broadcast_id) VALUES (?, ?)";

        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(sql)) {

            preparedStatement.setInt(1, regularPresenterId);
            preparedStatement.setInt(2, broadcastId);
            preparedStatement.executeUpdate();
        } catch (SQLException exception) {
            exception.printStackTrace();
        }
    }

    public List<GuestPresenter> getGuestPresenters() {
        List<GuestPresenter> guestPresenters = new ArrayList<>();
        String sql = "SELECT * FROM " + ConsDB.GUEST_PRESENTERS_TABLE;

        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(sql);
             ResultSet resultSet = preparedStatement.executeQuery()) {

            while (resultSet.next()) {
                int guestPresenterId = resultSet.getInt("guest_presenter_id");
                String guestPresenterName = resultSet.getString("guest_presenter_name");
                String guestPresenterResume = resultSet.getString("resume");

                GuestPresenter guestPresenter = new GuestPresenter(guestPresenterId, guestPresenterName, guestPresenterResume);

                List<Broadcast> broadcasts = new ArrayList<>();

                addBroadcastsToGuestPresenter(guestPresenterId, broadcasts);

                guestPresenter.setConductedBroadcasts(broadcasts);
                guestPresenters.add(guestPresenter);
            }
        } catch (SQLException exception) {
            exception.printStackTrace();
        }
        return guestPresenters;
    }

    private void addBroadcastsToGuestPresenter(int guestPresenterId, List<Broadcast> broadcasts) {
        String sql = "SELECT b.* FROM " + ConsDB.BROADCASTS_TABLE + " b " +
                "JOIN " + ConsDB.GUEST_PRESENTER_BROADCAST + " rpb ON b.broadcast_id = rpb.broadcast_id " +
                "WHERE rpb.guest_presenter_id = ?";

        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(sql)) {

            preparedStatement.setInt(1, guestPresenterId);

            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                BroadcastDAO broadcastDAO = new BroadcastDAO();

                while (resultSet.next()) {
                    Broadcast broadcast = createBroadcastFromResultSet(resultSet);

                    Map<AbstractBroadcastPart, LocalDateTime> parts = new HashMap<>();
                    List<AbstractBroadcastPart> sortedParts = broadcastDAO.getSortedParts(parts, broadcast.getId());

                    for (AbstractBroadcastPart part : sortedParts) {
                        broadcast.addPart(part);
                    }

                    broadcasts.add(broadcast);
                }
            } catch (SQLException exception) {
                exception.printStackTrace();
            }
        } catch (SQLException exception) {
            exception.printStackTrace();
        }
    }

    private Broadcast createBroadcastFromResultSet(ResultSet resultSet) throws SQLException {
        int broadcastId = resultSet.getInt("broadcast_id");
        int broadcastDuration = resultSet.getInt("broadcast_duration");

        return new Broadcast(broadcastId, broadcastDuration);
    }
}