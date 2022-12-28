package Model.DAO;

import java.sql.*;


import ConnectionPool.ConnectionPool;
import Model.Domen.Zaposleni;


public class DAOZaposleni {

    public static void dodajZaposlenog(Zaposleni zaposleni,String selektovaniZaposleni,Integer selektovanaOJID){
        Connection connection=null;
        CallableStatement statement=null;
        try {
            connection=ConnectionPool.getInstance().checkOut();
            switch (selektovaniZaposleni) {
                case "Trgovac":
                    statement = connection.prepareCall("{call insert_into_trgovac(?,?,?,?,?,?,?,?,?)}");
                    break;
                case "Magacioner":
                    statement = connection.prepareCall("{call insert_into_magacioner(?,?,?,?,?,?,?,?,?)}");
                    break;
                case "Administrativni radnik":
                    statement = connection.prepareCall("{call insert_into_administrativni_radnik(?,?,?,?,?,?,?,?,?)}");
                    break;

            }
            statement.setString(1, zaposleni.getJMBG());
            statement.setString(2, zaposleni.getIme());
            statement.setString(3, zaposleni.getPrezime());
            statement.setDate(4, new java.sql.Date(zaposleni.getDatumRodjenja().getTime()));
            statement.setString(5, zaposleni.getAdresa());
            statement.setInt(6, zaposleni.getMjestoID());
            statement.setString(7, zaposleni.getIznosPlate());
            statement.setString(8, zaposleni.getKorisnickoIme());
            statement.setString(9, zaposleni.getLozinka());
            statement.setInt(10, selektovanaOJID);
        }catch (SQLException exception){
            exception.printStackTrace();
        }
        finally {
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
    public  static void izbrisiZaposlenog(Zaposleni zaposleni){
        Connection connection = null;
        PreparedStatement statement = null;
        try {
            connection = ConnectionPool.getInstance().checkOut();
            String query = "UPDATE zaposleni set Aktivan = false where JMB = ?";
            PreparedStatement preparedStmt = connection.prepareStatement(query);
            preparedStmt.setString(1, zaposleni.getJMBG());
            preparedStmt.execute();
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



