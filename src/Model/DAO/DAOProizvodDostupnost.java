package Model.DAO;

import java.sql.*;
import java.util.List;


import ConnectionPool.ConnectionPool;
import Model.Domen.Stavka;

public class DAOProizvodDostupnost {

    public static void dodajProizvod(List<Stavka> stavke, Integer storeId) {
        Connection connection = null;
        CallableStatement statement = null;
        Statement st = null;

        try {
            connection = ConnectionPool.getInstance().checkOut();

            for (Stavka s : stavke) {
                statement = connection.prepareCall("{call `shopmuzikeopreme`.`insert_into_proizvod_dustupnost`(?,?,?)}");
                statement.setString(1, s.getBarkod());
                statement.setInt(2, storeId);
                statement.setInt(3, s.getKolicina());
                statement.executeQuery();
            }
        } catch (SQLException exception) {
            exception.printStackTrace();
        } finally {
            if (connection != null) {
                ConnectionPool.getInstance().checkIn(connection);
            }
            if (statement != null) {
                try {
                    statement.close();
                } catch (SQLException exception) {
                    exception.printStackTrace();
                }
            }
        }
    }

    public static Boolean reduceProizvod(List<Stavka> stavke, Integer storeId) {
        Connection connection = null;
        CallableStatement statement = null;
        Boolean productsAvailable = false;

        try {
            connection = ConnectionPool.getInstance().checkOut();

            for (Stavka s : stavke) {
                statement = connection.prepareCall("{call azuriraj_proizvod_dostupnost(?,?,?,?)}");
                statement.setString(1, s.getBarkod());
                statement.setInt(2, storeId);
                statement.setInt(3, s.getKolicina());
                statement.registerOutParameter(4, Types.BOOLEAN);
                statement.executeQuery();
                productsAvailable = statement.getBoolean(4);
                System.out.println(productsAvailable);
            }
        } catch (SQLException exception) {
           exception.printStackTrace();
        } finally {
            if (connection != null) {
                ConnectionPool.getInstance().checkIn(connection);
            }
            if (statement != null) {
                try {
                    statement.close();
                } catch (SQLException exception) {
                    exception.printStackTrace();
                }
            }
        }
        return productsAvailable;
    }

}

