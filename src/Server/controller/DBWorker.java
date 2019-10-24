package Server.controller;

import model.Users;

import java.io.IOException;
import java.sql.*;

public class DBWorker extends DBSettings {
    Connection connection;

    public DBWorker() throws IOException {
    }

    public Connection getDbConnection() throws ClassNotFoundException, SQLException {

        String URL = "jdbc:mysql://" + dbHost + ":" + dbPort + "/" + dbName +
                "?verifyServerCertificate=false&useSSL=false&requireSSL=false&useLegacyDatetimeCode=false&amp&serverTimezone=UTC";
        Class.forName("com.mysql.cj.jdbc.Driver");

        connection = DriverManager.getConnection(URL, dbUser, dbPass);

        return connection;
    }

    public String AddOne(Users user) throws SQLException, ClassNotFoundException { // новый пользователь регистрируется только как user
        Connection connection = getDbConnection();
        PreparedStatement statement = connection.prepareStatement("SELECT * FROM users WHERE Login = ? ");
        statement.setString(1,user.getLogin());
        ResultSet resultSet = statement.executeQuery();
        String result = "";
            if (resultSet.next())
                result = "error";
            else result = "good";
        PreparedStatement st = connection.prepareStatement("SELECT * FROM users WHERE ClientID = ? ");
        st.setInt(1,user.getClientID());
        ResultSet rs = st.executeQuery();
            if (rs.next())
                result = "error";
            else result = "good";
            if (result.equals("good")) {
                String insert = "INSERT INTO " + DBConstant.User_Table + "(" + DBConstant.User_Login + ","
                        + DBConstant.User_Password + "," + DBConstant.User_Role + "," + DBConstant.Clients_ID + ")" + "VALUES(?,?,?,?)";

                try {
                    PreparedStatement pRst = getDbConnection().prepareStatement(insert);
                    pRst.setString(1, user.getLogin());
                    pRst.setString(2, user.getPassword());
                    pRst.setString(3, "user");
                    pRst.setInt(4, user.getClientID());
                    pRst.executeUpdate();
                } catch (SQLException e) {
                    e.printStackTrace();
                } catch (ClassNotFoundException e) {
                    e.printStackTrace();
                }
            }
        return result;
    }

    public String checkuser(Users user) throws SQLException, ClassNotFoundException {
        Connection connection = getDbConnection();
        Statement statement = connection.createStatement();
        ResultSet resultSet = statement.executeQuery("Select * FROM users");

        while (resultSet.next()) {
            System.out.println(resultSet.getString(1) +" /"+ resultSet.getString(2));
            if(user.getLogin().equals(resultSet.getString(1))&& user.getPassword().equals(resultSet.getString(2)))
                if(resultSet.getString(3).equals("user"))
                {
                    return "User";
                }
                else
                    return "Admin";
        }
        return "False";
    }

}