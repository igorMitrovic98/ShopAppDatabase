package Model.DAO;

import ConnectionPool.ConnectionPool;
import Model.Domen.Klavijatura;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

public class DAOKlavijatura {

    public static void addKlavijatura(Klavijatura klavijatura) {
        Connection connection = null;
        CallableStatement statement = null;
        try {
            connection = ConnectionPool.getInstance().checkOut();
            statement = connection.prepareCall("{call insert_into_klavijatura(?,?,?,?,?,?,?,?)}");
            statement.setString(1, klavijatura.getNaziv());
            statement.setString(2, klavijatura.getCijena());
            statement.setInt(3, klavijatura.getJib_p());
            statement.setInt(4, klavijatura.getJib_d());
            statement.setString(5, klavijatura.getVrstaMaterijala());
            statement.setString(6, klavijatura.getVrsta());
            statement.setString(7, klavijatura.getBarkod());
            statement.setString(8, klavijatura.getTipKonfiguracije());
            statement.executeQuery();
        } catch (SQLException ex) {
            Logger.getLogger(DAOStavka.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            if (connection != null) {
                ConnectionPool.getInstance().checkIn(connection);
            }
            if (statement != null) {
                try {
                    statement.close();
                } catch (SQLException ex) {
                    Logger.getLogger(DAOStavka.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        }
    }

    public static void updateKlavijatura(Klavijatura klavijatura) {
        Connection connection = null;
        CallableStatement statement = null;

        try {
            connection = ConnectionPool.getInstance().checkOut();
            statement = connection.prepareCall("{call azuriraj_klavijatura(?,?,?,?,?,?,?,?)}");
            statement.setString(1, klavijatura.getNaziv());
            statement.setString(2, klavijatura.getCijena());
            statement.setInt(3, klavijatura.getJib_p());
            statement.setInt(4, klavijatura.getJib_d());
            statement.setString(5, klavijatura.getVrstaMaterijala());
            statement.setString(6, klavijatura.getTipKonfiguracije());
            statement.setString(7, klavijatura.getVrsta());
            statement.setString(8, klavijatura.getBarkod());
            statement.executeQuery();
        } catch (SQLException ex) {
            Logger.getLogger(DAOStavka.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            if (connection != null) {
                ConnectionPool.getInstance().checkIn(connection);
            }
            if (statement != null) {
                try {
                    statement.close();
                } catch (SQLException ex) {
                    Logger.getLogger(DAOStavka.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        }
    }
}
