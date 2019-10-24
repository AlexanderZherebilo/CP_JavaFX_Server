package Server.controller;

import model.Currency;

import java.io.IOException;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import static javax.swing.UIManager.getString;

public class CurrencyController extends DBWorker {

    public CurrencyController() throws IOException {
    }

    public ArrayList<String> GetCurrencyID () throws SQLException, ClassNotFoundException {
        Connection connection = getDbConnection();
        Statement statement = connection.createStatement();
        ResultSet resultSet = statement.executeQuery("SELECT CurrencyID FROM currency");
        ArrayList<String> result = new ArrayList<>();
        while (resultSet.next())
            result.add(resultSet.getString(1));
        return result;
    }

    public ArrayList<Currency> ShowCurrency () throws SQLException, ClassNotFoundException {
        Connection connection = getDbConnection();
        Statement statement = connection.createStatement();
        ResultSet resultSet = statement.executeQuery("SELECT * FROM currency");
        ArrayList<Currency> result = new ArrayList<>();
        Currency foradd;
        while (resultSet.next()) {
            foradd = new Currency();
            foradd.setCurrencyID(resultSet.getString(1));
            foradd.setCurrencyName(resultSet.getString(2));
            foradd.setRoundingStep(resultSet.getDouble(3));
            foradd.setCurrencyRate(resultSet.getFloat(4));
            result.add(foradd);
        }
        return result;
    }

    public void EditCurrency(Currency cur) throws SQLException, ClassNotFoundException {
        Connection connection = getDbConnection();
        Statement statement = connection.createStatement();
            String insert = "UPDATE " + DBConstant.Currency_Table + " SET " + DBConstant.Currency_RoundingStep + " = '" + cur.getRoundingStep() + "' WHERE " + DBConstant.Currency_ID + " = '" + cur.getCurrencyID() + "'";
            statement.executeUpdate(insert);
            insert = "UPDATE " + DBConstant.Currency_Table + " SET " + DBConstant.Currency_Rate + " = '" + cur.getCurrencyRate() + "' WHERE " + DBConstant.Currency_ID + " = '" + cur.getCurrencyID() + "'";
            statement.executeUpdate(insert);
    }

}
