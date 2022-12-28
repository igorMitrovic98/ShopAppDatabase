package Model.DAO;

import ConnectionPool.ConnectionPool;
import Model.Domen.Gitara;
import Model.Domen.Stavka;

import java.sql.*;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class DAOStavka {

    public static void addStavke(List<Stavka> stavke, int lastID){
        Connection connection =null;
        CallableStatement statement = null;

        try {
            connection=ConnectionPool.getInstance().checkOut();

            for(Stavka s : stavke){
                statement=connection.prepareCall("{call `shopmuzikeopreme`.`insert_into_stavka`(?,?,?,?)}");
                statement.setString(1, s.getBarkod());
                statement.setInt(2,lastID);
                statement.setInt(3,s.getKolicina());
                statement.setString(4,s.getCijena());
            }
        }catch (SQLException exception){
            Logger.getLogger(DAOGitara.class.getName()).log(Level.SEVERE, null, exception);
        }
        finally {
            if(connection!=null)
                ConnectionPool.getInstance().checkIn(connection);
            if(statement!=null){
                try {
                    statement.close();
                }catch (SQLException exception){
                    Logger.getLogger(DAOGitara.class.getName()).log(Level.SEVERE, null, exception);
                }
            }
        }
    }

    public static String getNazivProizvoda(String barkod){
        Connection connection = null;
        Statement statement = null;
        String productName = "";

        try {
            connection = ConnectionPool.getInstance().checkOut();
            statement = connection.createStatement();
            ResultSet rs = statement.executeQuery("SELECT Naziv from proizvod where Barkod=" + barkod);
            rs.next();
            productName = rs.getString("Naziv");

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
        return productName;
    }
    }

