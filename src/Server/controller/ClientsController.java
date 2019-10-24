package Server.controller;

import model.Clients;

import java.io.IOException;
import java.sql.*;
import java.util.ArrayList;

public class ClientsController extends DBWorker {

    public ClientsController() throws IOException {
    }

    public ArrayList<String> GetClientsSurname () throws SQLException, ClassNotFoundException {
        Connection connection = getDbConnection();
        Statement statement = connection.createStatement();
        ResultSet resultSet = statement.executeQuery("SELECT ClientSurname FROM clients");
        ArrayList<String> result = new ArrayList<>();
        while (resultSet.next())
            result.add(resultSet.getString(1));
        return result;
    }

    public int GetIDBySurname (Clients cl) throws SQLException, ClassNotFoundException {
        Connection connection = getDbConnection();
        Statement statement = connection.createStatement();
        ResultSet resultSet = statement.executeQuery("SELECT * FROM clients WHERE ClientSurname = '" + cl.getClientSurname() + "'");
        int result = 0;
        while (resultSet.next())
            result = resultSet.getInt(1);
        return result;
    }

    public ArrayList<Clients> ShowClients () throws SQLException, ClassNotFoundException {
        Connection connection = getDbConnection();
        Statement statement = connection.createStatement();
        ResultSet resultSet = statement.executeQuery("SELECT * FROM clients");
        ArrayList<Clients> result = new ArrayList<>();
        Clients foradd;
        while (resultSet.next()) {
            foradd = new Clients();
            foradd.setClientID(resultSet.getInt(1));
            foradd.setClientSurname(resultSet.getString(2));
            foradd.setClientName(resultSet.getString(3));
            foradd.setClientLastname(resultSet.getString(4));
            foradd.setClientBirthday(resultSet.getDate(5));
            foradd.setClientCity(resultSet.getString(6));
            foradd.setClientStreet(resultSet.getString(7));
            foradd.setClientHouse(resultSet.getInt(8));
            foradd.setClientFlat(resultSet.getInt(9));
            foradd.setClientSalary(resultSet.getFloat(10));
            foradd.setCurrencyID(resultSet.getString(11));
            result.add(foradd);
        }
        return result;
    }

    public String AddOne(Clients cl) throws SQLException, ClassNotFoundException {
        Connection connection = getDbConnection();
        PreparedStatement statement = connection.prepareStatement("SELECT * FROM clients WHERE ClientID = ? ");
        statement.setInt(1,cl.getClientID());
        ResultSet resultSet = statement.executeQuery();
        String result = "";
        if (resultSet.next())
            result = "error";
        else result = "good";
        if (result.equals("good")) {
            String insert = "INSERT INTO " + DBConstant.Clients_Table + "(" + DBConstant.Clients_ID + ","
                    + DBConstant.Clients_Surname + "," + DBConstant.Clients_Name + "," + DBConstant.Clients_Lastname
            + "," + DBConstant.Clients_Birthday + "," + DBConstant.Clients_City + "," + DBConstant.Clients_Street
            + "," + DBConstant.Clients_House + "," + DBConstant.Clients_Flat + "," + DBConstant.Clients_Salary
            + "," + DBConstant.Clients_Currency + ")" + "VALUES(?,?,?,?,?,?,?,?,?,?,?)";

            try {
                PreparedStatement pRst = getDbConnection().prepareStatement(insert);
                pRst.setInt(1, cl.getClientID());
                pRst.setString(2, cl.getClientSurname());
                pRst.setString(3, cl.getClientName());
                pRst.setString(4, cl.getClientLastname());
                pRst.setDate(5, cl.getClientBirthday());
                pRst.setString(6, cl.getClientCity());
                pRst.setString(7, cl.getClientStreet());
                pRst.setInt(8, cl.getClientHouse());
                pRst.setInt(9, cl.getClientFlat());
                pRst.setFloat(10, cl.getClientSalary());
                pRst.setString(11, cl.getCurrencyID());
                pRst.executeUpdate();
            } catch (SQLException e) {
                e.printStackTrace();
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            }
        }
        return result;
    }

    public String DeleteClient(Clients cc) throws SQLException, ClassNotFoundException {
        String result = "good";
        Connection connection = getDbConnection();
        Statement statement = connection.createStatement();
        ResultSet resultSet = statement.executeQuery("SELECT * FROM clientscredits WHERE ClientID = '" + cc.getClientID() + "'");
        if (resultSet.next())
            result = "cannotdeleteclient";
        else {
            statement.executeUpdate("DELETE FROM clients WHERE ClientID = '" + cc.getClientID() + "'");
            statement.executeUpdate("DELETE FROM users WHERE ClientID = '" + cc.getClientID() + "'");
        }

        return result;
    }

    public ArrayList<Clients> SearchSurname (Clients c) throws SQLException, ClassNotFoundException {
        Connection connection = getDbConnection();
        Statement statement = connection.createStatement();
        ResultSet resultSet = statement.executeQuery("SELECT * FROM clients WHERE (ClientSurname = '"+ c.getClientSurname() +
        "') AND (ClientSalary >= '" + c.getClientSalary() + "') AND (CurrencyID = '" + c.getCurrencyID() + "')");
        ArrayList<Clients> result = new ArrayList<>();
        Clients foradd;
        while (resultSet.next()) {
            foradd = new Clients();
            foradd.setClientID(resultSet.getInt(1));
            foradd.setClientSurname(resultSet.getString(2));
            foradd.setClientName(resultSet.getString(3));
            foradd.setClientLastname(resultSet.getString(4));
            foradd.setClientBirthday(resultSet.getDate(5));
            foradd.setClientCity(resultSet.getString(6));
            foradd.setClientStreet(resultSet.getString(7));
            foradd.setClientHouse(resultSet.getInt(8));
            foradd.setClientFlat(resultSet.getInt(9));
            foradd.setClientSalary(resultSet.getFloat(10));
            foradd.setCurrencyID(resultSet.getString(11));
            result.add(foradd);
        }
        return result;
    }

    public ArrayList<Clients> SearchCity (Clients c) throws SQLException, ClassNotFoundException {
        Connection connection = getDbConnection();
        Statement statement = connection.createStatement();
        ResultSet resultSet = statement.executeQuery("SELECT * FROM clients WHERE (ClientCity = '"+ c.getClientCity() +
                "') AND (ClientSalary >= '" + c.getClientSalary() + "') AND (CurrencyID = '" + c.getCurrencyID() + "')");
        ArrayList<Clients> result = new ArrayList<>();
        Clients foradd;
        while (resultSet.next()) {
            foradd = new Clients();
            foradd.setClientID(resultSet.getInt(1));
            foradd.setClientSurname(resultSet.getString(2));
            foradd.setClientName(resultSet.getString(3));
            foradd.setClientLastname(resultSet.getString(4));
            foradd.setClientBirthday(resultSet.getDate(5));
            foradd.setClientCity(resultSet.getString(6));
            foradd.setClientStreet(resultSet.getString(7));
            foradd.setClientHouse(resultSet.getInt(8));
            foradd.setClientFlat(resultSet.getInt(9));
            foradd.setClientSalary(resultSet.getFloat(10));
            foradd.setCurrencyID(resultSet.getString(11));
            result.add(foradd);
        }
        return result;
    }

    public String EditClient(Clients cl) throws SQLException, ClassNotFoundException {
        Connection connection = getDbConnection();
        PreparedStatement st = connection.prepareStatement("SELECT * FROM clientscredits WHERE ClientID = ? ");
        st.setInt(1,cl.getClientID());
        ResultSet resultSet = st.executeQuery();
        String result = "";
        if (resultSet.next())
            result = "error";
        else result = "good";
        if (result.equals("good")) {
            Statement statement = connection.createStatement();
            String insert = "UPDATE " + DBConstant.Clients_Table + " SET " + DBConstant.Clients_Surname + " = '" + cl.getClientSurname() + "' WHERE " + DBConstant.Clients_ID + " = '" + cl.getClientID() + "'";
            statement.executeUpdate(insert);
            insert = "UPDATE " + DBConstant.Clients_Table + " SET " + DBConstant.Clients_Name + " = '" + cl.getClientName() + "' WHERE " + DBConstant.Clients_ID + " = '" + cl.getClientID() + "'";
            statement.executeUpdate(insert);
            insert = "UPDATE " + DBConstant.Clients_Table + " SET " + DBConstant.Clients_Lastname + " = '" + cl.getClientLastname() + "' WHERE " + DBConstant.Clients_ID + " = '" + cl.getClientID() + "'";
            statement.executeUpdate(insert);
            insert = "UPDATE " + DBConstant.Clients_Table + " SET " + DBConstant.Clients_Birthday + " = '" + cl.getClientBirthday() + "' WHERE " + DBConstant.Clients_ID + " = '" + cl.getClientID() + "'";
            statement.executeUpdate(insert);
            insert = "UPDATE " + DBConstant.Clients_Table + " SET " + DBConstant.Clients_City + " = '" + cl.getClientCity() + "' WHERE " + DBConstant.Clients_ID + " = '" + cl.getClientID() + "'";
            statement.executeUpdate(insert);
            insert = "UPDATE " + DBConstant.Clients_Table + " SET " + DBConstant.Clients_Street + " = '" + cl.getClientStreet() + "' WHERE " + DBConstant.Clients_ID + " = '" + cl.getClientID() + "'";
            statement.executeUpdate(insert);
            insert = "UPDATE " + DBConstant.Clients_Table + " SET " + DBConstant.Clients_House + " = '" + cl.getClientHouse() + "' WHERE " + DBConstant.Clients_ID + " = '" + cl.getClientID() + "'";
            statement.executeUpdate(insert);
            insert = "UPDATE " + DBConstant.Clients_Table + " SET " + DBConstant.Clients_Flat + " = '" + cl.getClientFlat() + "' WHERE " + DBConstant.Clients_ID + " = '" + cl.getClientID() + "'";
            statement.executeUpdate(insert);
            insert = "UPDATE " + DBConstant.Clients_Table + " SET " + DBConstant.Clients_Salary + " = '" + cl.getClientSalary() + "' WHERE " + DBConstant.Clients_ID + " = '" + cl.getClientID() + "'";
            statement.executeUpdate(insert);
            insert = "UPDATE " + DBConstant.Clients_Table + " SET " + DBConstant.Clients_Currency + " = '" + cl.getCurrencyID() + "' WHERE " + DBConstant.Clients_ID + " = '" + cl.getClientID() + "'";
            statement.executeUpdate(insert);
        }
        return result;
    }
}
