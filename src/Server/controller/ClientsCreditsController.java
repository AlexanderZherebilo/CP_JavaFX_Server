package Server.controller;

import model.ClientsCredits;
import model.Credits;

import java.io.IOException;
import java.sql.*;
import java.util.ArrayList;

public class ClientsCreditsController extends DBWorker {

    public ClientsCreditsController() throws IOException {
    }

    public ArrayList<ClientsCredits> ShowClientsCredits () throws SQLException, ClassNotFoundException {
        Connection connection = getDbConnection();
        Statement statement = connection.createStatement();
        ResultSet resultSet = statement.executeQuery("SELECT * FROM clientscredits");
        ArrayList<ClientsCredits> result = new ArrayList<>();
        ClientsCredits foradd;
        while (resultSet.next()) {
            foradd = new ClientsCredits();
            foradd.setClientID(resultSet.getInt(1));
            foradd.setCreditID(resultSet.getInt(2));
            foradd.setClientSurname(resultSet.getString(3));
            foradd.setCreditName(resultSet.getString(4));
            foradd.setCreditNominalRate(resultSet.getFloat(5));
            foradd.setCreditGiveDate(resultSet.getDate(6));
            foradd.setCreditReturnDate(resultSet.getDate(7));
            foradd.setCreditFrequency(resultSet.getInt(8));
            foradd.setCreditSumm(resultSet.getInt(9));
            foradd.setCreditEffectiveRate(foradd.CalculateEffectiveRate(foradd.getCreditNominalRate(), foradd.getCreditFrequency()));
            foradd.setCreditTotalPayments(foradd.CalculateTotalPayments(foradd.getCreditNominalRate(), foradd.getCreditFrequency(), foradd.getCreditSumm()));
            foradd.setCreditPeicePayment(foradd.CalculatePeicePayments(foradd.getCreditTotalPayments(), foradd.getCreditFrequency()));
            result.add(foradd);
        }
        return result;
    }

    public String AddOne(ClientsCredits cc) throws SQLException, ClassNotFoundException {
        Connection connection = getDbConnection();
        PreparedStatement statement = connection.prepareStatement("SELECT * FROM clientscredits WHERE ClientID = ? ");
        statement.setInt(1,cc.getClientID());
        ResultSet resultSet = statement.executeQuery();
        String result = "good";
        if (resultSet.next())
            result = "hasCredit";
        else {
            Statement st = connection.createStatement();
            ResultSet rs = st.executeQuery("SELECT * FROM credits WHERE CreditID = '" + cc.getCreditID() + "'");
            while (rs.next())
                if (cc.getCreditSumm() > (rs.getInt(3)))
                    result = "TooMuch";
        }
        if (result.equals("good")) {
            String insert = "INSERT INTO " + DBConstant.ClientsCredits_Table + "(" + DBConstant.ClientsCredits_ClientID + ","
                    + DBConstant.ClientsCredits_CreditID + "," + DBConstant.ClientsCredits_ClientSurname + ","
                    + DBConstant.ClientsCredits_CreditName + "," + DBConstant.ClientsCredits_CreditNominalRate + ","
                    + DBConstant.ClientsCredits_CreditGiven + "," + DBConstant.ClientsCredits_CreditReturned + ","
                    + DBConstant.ClientsCredits_CreditFrequency + "," + DBConstant.ClientsCredits_CreditSumm + ")" + "VALUES(?,?,?,?,?,?,?,?,?)";

            try {
                PreparedStatement pRst = getDbConnection().prepareStatement(insert);
                pRst.setInt(1, cc.getClientID());
                pRst.setInt(2, cc.getCreditID());
                pRst.setString(3, cc.getClientSurname());
                pRst.setString(4, cc.getCreditName());
                pRst.setFloat(5, cc.getCreditNominalRate());
                pRst.setDate(6, cc.getCreditGiveDate());
                pRst.setDate(7, cc.getCreditReturnDate());
                pRst.setInt(8, cc.getCreditFrequency());
                pRst.setInt(9, cc.getCreditSumm());
                pRst.executeUpdate();
            } catch (SQLException e) {
                e.printStackTrace();
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            }
        }
        return result;
    }

    public String ReturnCredit(ClientsCredits cc) throws SQLException, ClassNotFoundException {
        String result = null;
        System.out.println(cc.toString());
        Connection connection = getDbConnection();
        Statement statement = connection.createStatement();
        statement.executeUpdate("DELETE FROM clientscredits WHERE (ClientID = '" + cc.getClientID() + "') AND (CreditID = '" + cc.getCreditID() + "')");
        result = "good";
        return result;
    }

}
