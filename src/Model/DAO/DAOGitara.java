package Model.DAO;

import ConnectionPool.ConnectionPool;
import Model.Domen.Gitara;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

public class DAOGitara {

    public static void addGitara(Gitara gitara) {
        Connection connection = null;
        CallableStatement statement = null;
        try {
            connection = ConnectionPool.getInstance().checkOut();
            statement = connection.prepareCall("{call insert_into_gitara(?,?,?,?,?,?,?,?)}");
            statement.setString(1, gitara.getNaziv());
            statement.setString(2, gitara.getCijena());
            statement.setInt(3, gitara.getJib_p());
            statement.setInt(4, gitara.getJib_d());
            statement.setString(5, gitara.getVrstaMaterijala());
            statement.setString(6, gitara.getVrsta());
            statement.setString(7, gitara.getBarkod());
            statement.setInt(8, gitara.getBrojZica());
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

    public static void updateGitara(Gitara gitara) {
        Connection connection = null;
        CallableStatement statement = null;

        try {
            connection = ConnectionPool.getInstance().checkOut();
            statement = connection.prepareCall("{call azuriraj_gitara(?,?,?,?,?,?,?,?)}");
            statement.setString(1, gitara.getNaziv());
            statement.setString(2, gitara.getCijena());
            statement.setInt(3, gitara.getJib_p());
            statement.setInt(4, gitara.getJib_d());
            statement.setString(5, gitara.getVrstaMaterijala());
            statement.setInt(6, gitara.getBrojZica());
            statement.setString(7, gitara.getVrsta());
            statement.setString(8, gitara.getBarkod());
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
