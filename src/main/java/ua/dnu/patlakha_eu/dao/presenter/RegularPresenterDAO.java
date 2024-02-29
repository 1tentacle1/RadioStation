package main.java.ua.dnu.patlakha_eu.dao.presenter;

import main.java.ua.dnu.patlakha_eu.dao.ConsDB;
import main.java.ua.dnu.patlakha_eu.dao.DatabaseConnection;
import main.java.ua.dnu.patlakha_eu.dao.broadcast.BroadcastDAO;
import main.java.ua.dnu.patlakha_eu.model.broadcast.Broadcast;
import main.java.ua.dnu.patlakha_eu.model.broadcast.parts.abstractions.AbstractBroadcastPart;
import main.java.ua.dnu.patlakha_eu.model.presenter.RegularPresenter;

import java.sql.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class RegularPresenterDAO{
    public int addRegularPresenter(String name, int workExperience) {
        int id = -1;
        String sql = "INSERT INTO " + ConsDB.REGULAR_PRESENTERS_TABLE + " (regular_presenter_name, work_experience) VALUES (?, ?)";

        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            preparedStatement.setString(1, name);
            preparedStatement.setInt(2, workExperience);
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

    public void addBroadcastToRegularPresenter(int regularPresenterId, int broadcastId) {
        String sql = "INSERT INTO " + ConsDB.REGULAR_PRESENTER_BROADCAST + " (regular_presenter_id, broadcast_id) VALUES (?, ?)";

        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(sql)) {

            preparedStatement.setInt(1, regularPresenterId);
            preparedStatement.setInt(2, broadcastId);
            preparedStatement.executeUpdate();
        } catch (SQLException exception) {
            exception.printStackTrace();
        }
    }

    public List<RegularPresenter> getRegularPresenters() {
        List<RegularPresenter> regularPresenters = new ArrayList<>();
        String sql = "SELECT * FROM " + ConsDB.REGULAR_PRESENTERS_TABLE;

        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(sql);
             ResultSet resultSet = preparedStatement.executeQuery()) {

            while (resultSet.next()) {
                int regularPresenterId = resultSet.getInt("regular_presenter_id");
                String regularPresenterName = resultSet.getString("regular_presenter_name");
                int regularPresenterWorkExperience = resultSet.getInt("work_experience");

                RegularPresenter regularPresenter = new RegularPresenter(regularPresenterId, regularPresenterName, regularPresenterWorkExperience);

                List<Broadcast> broadcasts = new ArrayList<>();

                addBroadcastsToRegularPresenter(regularPresenterId, broadcasts);

                regularPresenter.setConductedBroadcasts(broadcasts);
                regularPresenters.add(regularPresenter);
            }
        } catch (SQLException exception) {
            exception.printStackTrace();
        }
        return regularPresenters;
    }

    private void addBroadcastsToRegularPresenter(int regularPresenterId, List<Broadcast> broadcasts) {
        String sql = "SELECT b.* FROM " + ConsDB.BROADCASTS_TABLE + " b " +
                "JOIN " + ConsDB.REGULAR_PRESENTER_BROADCAST + " rpb ON b.broadcast_id = rpb.broadcast_id " +
                "WHERE rpb.regular_presenter_id = ?";

        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(sql)) {

            preparedStatement.setInt(1, regularPresenterId);

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