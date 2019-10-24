package Server.controller;

import model.ClientsCredits;
import model.Credits;

import java.io.IOException;
import java.sql.*;
import java.util.ArrayList;

public class CreditsController extends DBWorker {

    public CreditsController() throws IOException {
    }

    public ArrayList<String> GetCreditsName () throws SQLException, ClassNotFoundException {
        Connection connection = getDbConnection();
        Statement statement = connection.createStatement();
        ResultSet resultSet = statement.executeQuery("SELECT CreditName FROM credits");
        ArrayList<String> result = new ArrayList<>();
        while (resultSet.next())
            result.add(resultSet.getString(1));
        return result;
    }

    public Credits GetIDByCredit (Credits cr) throws SQLException, ClassNotFoundException {
        Connection connection = getDbConnection();
        Statement statement = connection.createStatement();
        ResultSet resultSet = statement.executeQuery("SELECT * FROM credits WHERE CreditName = '" + cr.getCreditName() + "'");
        Credits result = new Credits();
        while (resultSet.next()) {
            result.setCreditID(resultSet.getInt(1));
            result.setCreditName(resultSet.getString(2));
            result.setCreditMaxSumm(resultSet.getInt(3));
            result.setCurrencyID(resultSet.getString(4));
            result.setCreditNominalRate(resultSet.getFloat(5));
        }
        return result;
    }

    public ArrayList<Credits> ShowCredits () throws SQLException, ClassNotFoundException {
        Connection connection = getDbConnection();
        Statement statement = connection.createStatement();
        ResultSet resultSet = statement.executeQuery("SELECT * FROM credits");
        ArrayList<Credits> result = new ArrayList<>();
        Credits foradd;
        while (resultSet.next()) {
            foradd = new Credits();
            foradd.setCreditID(resultSet.getInt(1));
            foradd.setCreditName(resultSet.getString(2));
            foradd.setCreditMaxSumm(resultSet.getInt(3));
            foradd.setCurrencyID(resultSet.getString(4));
            foradd.setCreditNominalRate(resultSet.getFloat(5));
            result.add(foradd);
        }
        return result;
    }

    public String AddOne(Credits cr) throws SQLException, ClassNotFoundException {
        Connection connection = getDbConnection();
        PreparedStatement statement = connection.prepareStatement("SELECT * FROM credits WHERE CreditID = ? ");
        statement.setInt(1,cr.getCreditID());
        ResultSet resultSet = statement.executeQuery();
        String result = "";
        if (resultSet.next())
            result = "error";
        else result = "good";
        if (result.equals("good")) {
            String insert = "INSERT INTO " + DBConstant.Credits_Table + "(" + DBConstant.Credits_ID + ","
                    + DBConstant.Credits_Name + "," + DBConstant.Credits_MaxSumm + "," + DBConstant.Credits_Currency
                    + "," + DBConstant.Credits_NominalRate + ")" + "VALUES(?,?,?,?,?)";

            try {
                PreparedStatement pRst = getDbConnection().prepareStatement(insert);
                pRst.setInt(1, cr.getCreditID());
                pRst.setString(2, cr.getCreditName());
                pRst.setInt(3, cr.getCreditMaxSumm());
                pRst.setString(4, cr.getCurrencyID());
                pRst.setFloat(5, cr.getCreditNominalRate());
                pRst.executeUpdate();
            } catch (SQLException e) {
                e.printStackTrace();
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            }
        }
        return result;
    }

    public String DeleteCredit(Credits cc) throws SQLException, ClassNotFoundException {
        String result = "good";
        Connection connection = getDbConnection();
        Statement statement = connection.createStatement();
        ResultSet resultSet = statement.executeQuery("SELECT * FROM clientscredits WHERE CreditID = '" + cc.getCreditID() + "'");
        if (resultSet.next())
            result = "cannotdeletecredit";
        else
            statement.executeUpdate("DELETE FROM credits WHERE CreditID = '" + cc.getCreditID() + "'");
        return result;
    }

    public ArrayList<Credits> SearchSumm (int min, int max, String curr) throws SQLException, ClassNotFoundException {
        Connection connection = getDbConnection();
        Statement statement = connection.createStatement();
        ResultSet resultSet = statement.executeQuery("SELECT * FROM credits WHERE (CreditMaxSumm >= '"+ min +
                "') AND (CreditMaxSumm <= '" + max + "') AND (CurrencyID = '" + curr + "')");
        ArrayList<Credits> result = new ArrayList<>();
        Credits foradd;
        while (resultSet.next()) {
            foradd = new Credits();
            foradd.setCreditID(resultSet.getInt(1));
            foradd.setCreditName(resultSet.getString(2));
            foradd.setCreditMaxSumm(resultSet.getInt(3));
            foradd.setCurrencyID(resultSet.getString(4));
            foradd.setCreditNominalRate(resultSet.getFloat(5));
            result.add(foradd);
        }
        return result;
    }

    public ArrayList<Credits> SearchRate (int min, int max, String curr) throws SQLException, ClassNotFoundException {
        Connection connection = getDbConnection();
        Statement statement = connection.createStatement();
        ResultSet resultSet = statement.executeQuery("SELECT * FROM credits WHERE (CreditNominalRate >= '"+ min +
                "') AND (CreditNominalRate <= '" + max + "') AND (CurrencyID = '" + curr + "')");
        ArrayList<Credits> result = new ArrayList<>();
        Credits foradd;
        while (resultSet.next()) {
            foradd = new Credits();
            foradd.setCreditID(resultSet.getInt(1));
            foradd.setCreditName(resultSet.getString(2));
            foradd.setCreditMaxSumm(resultSet.getInt(3));
            foradd.setCurrencyID(resultSet.getString(4));
            foradd.setCreditNominalRate(resultSet.getFloat(5));
            result.add(foradd);
        }
        return result;
    }

    public String EditCredit(Credits cr) throws SQLException, ClassNotFoundException {
        Connection connection = getDbConnection();
        PreparedStatement st = connection.prepareStatement("SELECT * FROM clientscredits WHERE CreditID = ? ");
        st.setInt(1,cr.getCreditID());
        ResultSet resultSet = st.executeQuery();
        String result = "";
        if (resultSet.next())
            result = "error";
        else result = "good";
        if (result.equals("good")) {
            Statement statement = connection.createStatement();
            String insert = "UPDATE " + DBConstant.Credits_Table + " SET " + DBConstant.Credits_Name + " = '" + cr.getCreditName() + "' WHERE " + DBConstant.Credits_ID + " = '" + cr.getCreditID() + "'";
            statement.executeUpdate(insert);
            insert = "UPDATE " + DBConstant.Credits_Table + " SET " + DBConstant.Credits_MaxSumm + " = '" + cr.getCreditMaxSumm() + "' WHERE " + DBConstant.Credits_ID + " = '" + cr.getCreditID() + "'";
            statement.executeUpdate(insert);
            insert = "UPDATE " + DBConstant.Credits_Table + " SET " + DBConstant.Credits_Currency + " = '" + cr.getCurrencyID() + "' WHERE " + DBConstant.Credits_ID + " = '" + cr.getCreditID() + "'";
            statement.executeUpdate(insert);
            insert = "UPDATE " + DBConstant.Credits_Table + " SET " + DBConstant.Credits_NominalRate + " = '" + cr.getCreditNominalRate() + "' WHERE " + DBConstant.Credits_ID + " = '" + cr.getCreditID() + "'";
            statement.executeUpdate(insert);
        }
        return result;
    }

}
