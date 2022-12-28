package Model.DAO;

import java.sql.*;
import java.util.logging.Level;
import java.util.logging.Logger;

import ConnectionPool.ConnectionPool;


public class DAOProizvod {

    public static void obrisiProizvod(String barkod){
        Connection connection=null;
        Statement statement=null;
        try {
            connection = ConnectionPool.getInstance().checkOut();
            statement = connection.createStatement();
            statement.execute("DELETE proizvod from proizvod where Barkod = " + barkod);
        } catch (SQLException exception) {
           exception.printStackTrace();
        }finally {
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
    public static String getNazivProizvodjaca(String JIB_P) {
        Connection connection = null;
        PreparedStatement statement = null;
        String naziv = "";
        try {
            connection = ConnectionPool.getInstance().checkOut();
            String query = "select Naziv from proizvodjac where jib_p=?";
            statement = connection.prepareStatement(query);
            statement.setString(1, JIB_P);
            ResultSet rs = statement.executeQuery();
            rs.next();
            naziv = rs.getString("Naziv");
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
        return naziv;
    }
    }
