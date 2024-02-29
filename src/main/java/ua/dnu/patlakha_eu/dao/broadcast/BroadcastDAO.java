package main.java.ua.dnu.patlakha_eu.dao.broadcast;

import main.java.ua.dnu.patlakha_eu.dao.ConsDB;
import main.java.ua.dnu.patlakha_eu.dao.DatabaseConnection;
import main.java.ua.dnu.patlakha_eu.model.broadcast.Broadcast;
import main.java.ua.dnu.patlakha_eu.model.broadcast.parts.Advertising;
import main.java.ua.dnu.patlakha_eu.model.broadcast.parts.Interview;
import main.java.ua.dnu.patlakha_eu.model.broadcast.parts.Song;
import main.java.ua.dnu.patlakha_eu.model.broadcast.parts.abstractions.AbstractBroadcastPart;

import java.sql.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class BroadcastDAO {
    public int addBroadcast(int duration) {
        int broadcastId  = -1;
        String sql = "INSERT INTO " + ConsDB.BROADCASTS_TABLE + " (broadcast_duration) VALUES (?)";

        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            preparedStatement.setInt(1, duration);
            preparedStatement.executeUpdate();

            try (ResultSet resultSet = preparedStatement.getGeneratedKeys()){
                if (resultSet.next()) {
                    broadcastId  = resultSet.getInt(1);
                } else {
                    throw new SQLException("Помилка! Трансляція не була додана");
                }
            } catch (SQLException exception) {
                exception.printStackTrace();
            }
        } catch (SQLException exception) {
            exception.printStackTrace();
        }
        return broadcastId ;
    }

    public void addPartToBroadcast(int broadcastId, AbstractBroadcastPart part) {
        String sql = "";

        try (Connection connection = DatabaseConnection.getConnection()) {
            if (part instanceof Interview) {
                sql = "INSERT INTO " + ConsDB.BROADCAST_INTERVIEW_TABLE + " (broadcast_id, interview_id) VALUES (?, ?)";
            }
            else if (part instanceof Advertising) {
                sql = "INSERT INTO " + ConsDB.BROADCAST_ADVERTISING_TABLE + " (broadcast_id, advertising_id) VALUES (?, ?)";
            }
            else if (part instanceof Song) {
                sql = "INSERT INTO " + ConsDB.BROADCAST_SONG_TABLE + " (broadcast_id, song_id) VALUES (?, ?)";
            }

            try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
                preparedStatement.setInt(1, broadcastId);
                preparedStatement.setInt(2, part.getId());
                preparedStatement.executeUpdate();
            } catch (SQLException exception) {
                exception.printStackTrace();
            }
        } catch (SQLException exception) {
            exception.printStackTrace();
        }
    }

    public List<Broadcast> getBroadcasts() {
        List<Broadcast> broadcasts = new ArrayList<>();
        String sql = "SELECT * FROM " + ConsDB.BROADCASTS_TABLE;

        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(sql);
             ResultSet resultSet = preparedStatement.executeQuery()) {

            while (resultSet.next()) {
                int broadcastId = resultSet.getInt("broadcast_id");
                int broadcastDuration = resultSet.getInt("broadcast_duration");

                Broadcast broadcast = new Broadcast(broadcastId, broadcastDuration);

                Map<AbstractBroadcastPart, LocalDateTime> parts = new HashMap<>();
                List<AbstractBroadcastPart> sortedParts = getSortedParts(parts, broadcastId);

                for (AbstractBroadcastPart part : sortedParts) {
                    broadcast.addPart(part);
                }

                broadcasts.add(broadcast);
            }
        } catch (SQLException exception) {
            exception.printStackTrace();
        }
        return broadcasts;
    }

    public List<AbstractBroadcastPart> getSortedParts(Map<AbstractBroadcastPart, LocalDateTime> parts, int broadcastId) {
        addInterviewsToParts(broadcastId, parts);
        addAdvertisementsToParts(broadcastId, parts);
        addSongsToParts(broadcastId, parts);

        return parts.entrySet()
                .stream()
                .sorted(Map.Entry.comparingByValue())
                .map(Map.Entry::getKey)
                .toList();
    }

    private void addInterviewsToParts(int broadcastId, Map<AbstractBroadcastPart, LocalDateTime> parts) {
        String sql = "SELECT i.*, bi.broadcast_adding_time FROM " + ConsDB.INTERVIEWS_TABLE + " i " +
                "JOIN " + ConsDB.BROADCAST_INTERVIEW_TABLE + " bi ON i.interview_id = bi.interview_id " +
                "WHERE bi.broadcast_id = ?";

        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(sql)) {

            preparedStatement.setInt(1, broadcastId);

            try(ResultSet resultSet = preparedStatement.executeQuery()) {
                while (resultSet.next()) {
                    Interview interview = createInterviewFromResultSet(resultSet);
                    Timestamp timestamp = resultSet.getTimestamp("broadcast_adding_time");
                    LocalDateTime time = timestamp.toLocalDateTime();

                    parts.put(interview, time);
                }
            } catch (SQLException exception) {
                exception.printStackTrace();
            }
        } catch (SQLException exception) {
            exception.printStackTrace();
        }
    }

    private void addAdvertisementsToParts(int broadcastId, Map<AbstractBroadcastPart, LocalDateTime> parts) {
        String sql = "SELECT a.*, ba.broadcast_adding_time FROM " + ConsDB.ADVERTISEMENTS_TABLE + " a " +
                "JOIN " + ConsDB.BROADCAST_ADVERTISING_TABLE + " ba ON a.advertising_id = ba.advertising_id " +
                "WHERE ba.broadcast_id = ?";

        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(sql)) {

            preparedStatement.setInt(1, broadcastId);

            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                while (resultSet.next()) {
                    Advertising advertising = createAdvertisingFromResultSet(resultSet);
                    Timestamp timestamp = resultSet.getTimestamp("broadcast_adding_time");
                    LocalDateTime time = timestamp.toLocalDateTime();

                    parts.put(advertising, time);
                }
            } catch (SQLException exception) {
                exception.printStackTrace();
            }
        } catch (SQLException exception) {
            exception.printStackTrace();
        }
    }

    private void addSongsToParts(int broadcastId, Map<AbstractBroadcastPart, LocalDateTime> parts) {
        String sql = "SELECT s.*, bs.broadcast_adding_time FROM " + ConsDB.SONGS_TABLE + " s " +
                "JOIN " + ConsDB.BROADCAST_SONG_TABLE + " bs ON s.song_id = bs.song_id " +
                "WHERE bs.broadcast_id = ?";

        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(sql)) {

            preparedStatement.setInt(1, broadcastId);

            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                while (resultSet.next()) {
                    Song song = createSongFromResultSet(resultSet);
                    Timestamp timestamp = resultSet.getTimestamp("broadcast_adding_time");
                    LocalDateTime time = timestamp.toLocalDateTime();

                    parts.put(song, time);
                }
            } catch (SQLException exception) {
                exception.printStackTrace();
            }
        } catch (SQLException exception) {
            exception.printStackTrace();
        }
    }

    private Interview createInterviewFromResultSet(ResultSet resultSet) throws SQLException {
        int interviewId = resultSet.getInt("interview_id");
        String interviewee = resultSet.getString("interviewee");
        int interviewDuration = resultSet.getInt("interview_duration");

        return new Interview(interviewId, interviewee, interviewDuration);
    }

    private Advertising createAdvertisingFromResultSet(ResultSet resultSet) throws SQLException {
        int advertisingId = resultSet.getInt("advertising_id");
        String productName = resultSet.getString("product_name");
        int advertisingDuration = resultSet.getInt("advertising_duration");

        return new Advertising(advertisingId, productName, advertisingDuration);
    }

    private Song createSongFromResultSet(ResultSet resultSet) throws SQLException {
        int songId = resultSet.getInt("song_id");
        String artistName = resultSet.getString("artist_name");
        String songName = resultSet.getString("song_name");
        int songDuration = resultSet.getInt("song_duration");

        return new Song(songId, artistName, songName, songDuration);
    }

    public List<Interview> getInterviews() {
        List<Interview> interviews = new ArrayList<>();
        String sql = "SELECT * FROM " + ConsDB.INTERVIEWS_TABLE;

        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(sql);
             ResultSet resultSet = preparedStatement.executeQuery()) {

            while (resultSet.next()) {
                Interview interview = createInterviewFromResultSet(resultSet);
                interviews.add(interview);
            }
        } catch (SQLException exception) {
            exception.printStackTrace();
        }
        return interviews;
    }

    public List<Advertising> getAdvertisements() {
        List<Advertising> advertisements = new ArrayList<>();
        String sql = "SELECT * FROM " + ConsDB.ADVERTISEMENTS_TABLE;

        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(sql);
             ResultSet resultSet = preparedStatement.executeQuery()) {

            while (resultSet.next()) {
                Advertising advertising = createAdvertisingFromResultSet(resultSet);
                advertisements.add(advertising);
            }
        } catch (SQLException exception) {
            exception.printStackTrace();
        }
        return advertisements;
    }

    public List<Song> getSongs() {
        List<Song> songs = new ArrayList<>();
        String sql = "SELECT * FROM " + ConsDB.SONGS_TABLE;

        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(sql);
             ResultSet resultSet = preparedStatement.executeQuery()) {

            while (resultSet.next()) {
                Song song = createSongFromResultSet(resultSet);
                songs.add(song);
            }
        } catch (SQLException exception) {
            exception.printStackTrace();
        }
        return songs;
    }
}