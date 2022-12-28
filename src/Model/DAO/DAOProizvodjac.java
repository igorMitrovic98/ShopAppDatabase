package Model.DAO;

import java.sql.*;

import ConnectionPool.ConnectionPool;
import Model.Domen.Proizvodjac;

public class DAOProizvodjac {

    public static void dodajProizvodjaca(Proizvodjac proizvodjac) {
        Connection connection = null;
        CallableStatement statement = null;
        try {
            connection = ConnectionPool.getInstance().checkOut();
            statement = connection.prepareCall("{call insert_into_proizvodjac(?,?,?,?)}");
            statement.setInt(1, proizvodjac.getJib_p());
            statement.setString(2, proizvodjac.getNaziv());
            statement.setString(3, proizvodjac.getTelefon());
            statement.setInt(4, proizvodjac.getMjestoId());

            statement.executeQuery();
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
    public static void obrisiProizvodjaca(Integer JIB_P) {
        Connection connection = null;
        Statement statement = null;
        try {
            connection = ConnectionPool.getInstance().checkOut();
            statement = connection.createStatement();
            statement.execute("DELETE proizvodjac from proizvodjac where JIB_P = " + JIB_P);

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

}
