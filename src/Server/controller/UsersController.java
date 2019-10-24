package Server.controller;

import model.Users;

import java.io.IOException;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

public class UsersController extends DBWorker {

    public UsersController() throws IOException {
    }

    public ArrayList<Users> ShowUsers () throws SQLException, ClassNotFoundException {
        Connection connection = getDbConnection();
        Statement statement = connection.createStatement();
        ResultSet resultSet = statement.executeQuery("SELECT * FROM users");
        ArrayList<Users> result = new ArrayList<>();
        Users foradd;
        while (resultSet.next()) {
            foradd = new Users();
            foradd.setLogin(resultSet.getString(1));
            foradd.setPassword(resultSet.getString(2));
            foradd.setRole(resultSet.getString(3));
            foradd.setClientID(resultSet.getInt(4));
            result.add(foradd);
        }
        return result;
    }

    public String DeleteUser(Users uc) throws SQLException, ClassNotFoundException {
        String result = null;
        Connection connection = getDbConnection();
        Statement statement = connection.createStatement();
        statement.executeUpdate("DELETE FROM users WHERE Login = '" + uc.getLogin() + "'");
        result = "good";
        return result;
    }

}
