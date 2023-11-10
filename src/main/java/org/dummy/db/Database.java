package org.dummy.db;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class Database {

    private final Logger log = LoggerFactory.getLogger(Database.class);

    private final String connectionUrl;

    public Database(String url) {
        this.connectionUrl = url;
    }

    public synchronized void initDatabase() throws Exception {
        try (Connection connection = DriverManager.getConnection(connectionUrl + ";create=true")) {
            Statement statement = connection.createStatement();
            statement.executeUpdate("create table susers(user_id INT, user_guid varchar(10), user_name varchar(255))");
            log.info("Database setup successful");
        } catch (SQLException ex) {
            log.error("Unable to setup database", ex);
            throw new Exception(ex);
        }
    }

    public synchronized void stopDatabase() throws Exception {
        // drop table
        try (Connection connection = DriverManager.getConnection(connectionUrl)) {
            Statement statement = connection.createStatement();
            statement.execute("drop table susers");
        } catch (SQLException ex) {
            throw new Exception(ex);
        }

        // shutdown db
        try {
            DriverManager.getConnection("jdbc:derby:;shutdown=true");
        } catch (SQLException ex) {
            if (((ex.getErrorCode() == 50000) && ("XJ015".equals(ex.getSQLState())))) { // I googled this
                log.info("DB shutdown successful");
            } else {
                throw new Exception(ex);
            }
        }
    }


    public synchronized int addUser(Integer id, String guid, String name) throws Exception {
        try (Connection connection = DriverManager.getConnection(connectionUrl)) {
            PreparedStatement preparedStatement = connection.prepareStatement("insert into susers(user_id, user_guid, user_name) values (?, ?, ?)");
            preparedStatement.setInt(1, id);
            preparedStatement.setString(2, guid);
            preparedStatement.setString(3, name);
            return preparedStatement.executeUpdate();

        } catch (SQLException e) {
            throw new Exception(e);
        }
    }


    public synchronized int deleteAllUsers() throws Exception {
        try (Connection connection = DriverManager.getConnection(connectionUrl)) {
            Statement statement = connection.createStatement();
            return statement.executeUpdate("delete from susers");
        } catch (SQLException e) {
            throw new Exception(e);
        }
    }


    public synchronized List<String[]> findAllUsers() throws Exception {
        try (Connection connection = DriverManager.getConnection(connectionUrl)) {
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery("select * from susers order by user_name");

            List<String[]> users = new ArrayList<>();
            while (resultSet.next()) {
                int id = resultSet.getInt(1);
                String guid = resultSet.getString(2);
                String name = resultSet.getString(3);
                String[] user = new String[] {Integer.toString(id), guid, name};
                users.add(user);
            }
            return users;

        } catch (SQLException e) {
            throw new Exception(e);
        }
    }

}
