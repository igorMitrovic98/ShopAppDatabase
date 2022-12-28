package Model.DAO;

import ConnectionPool.ConnectionPool;
import Model.Domen.Bubanj;


import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

public class DAOBubanj {

    public static void addBubanj(Bubanj bubanj) {
        Connection connection = null;
        CallableStatement statement = null;
        try {
            connection = ConnectionPool.getInstance().checkOut();
            statement = connection.prepareCall("{call insert_into_bubanj(?,?,?,?,?,?,?)}");
            statement.setString(1, bubanj.getNaziv());
            statement.setString(2, bubanj.getCijena());
            statement.setInt(3, bubanj.getJib_p());
            statement.setInt(4, bubanj.getJib_d());
            statement.setString(5, bubanj.getVrstaMaterijala());
            statement.setString(6, bubanj.getVrsta());
            statement.setString(7, bubanj.getBarkod());
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

    public static void updateBubanj(Bubanj bubanj) {
        Connection connection = null;
        CallableStatement statement = null;

        try {
            connection = ConnectionPool.getInstance().checkOut();
            statement = connection.prepareCall("{call azuriraj_bubanj(?,?,?,?,?,?,?)}");
            statement.setString(1, bubanj.getNaziv());
            statement.setString(2, bubanj.getCijena());
            statement.setInt(3, bubanj.getJib_p());
            statement.setInt(4, bubanj.getJib_d());
            statement.setString(5, bubanj.getVrstaMaterijala());
            statement.setString(6, bubanj.getVrsta());
            statement.setString(7, bubanj.getBarkod());
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
