package Model.DAO;

import java.sql.*;
import java.util.logging.Level;
import java.util.logging.Logger;

import ConnectionPool.ConnectionPool;
import Model.Domen.IzlaznaFaktura;

public class DAOIzlaznaFaktura {

    public static int dodajFakturu(IzlaznaFaktura izlaznaFaktura) {
        Connection connection = null;
        PreparedStatement statement = null;
        int lastId = -1;
        try {
            connection = ConnectionPool.getInstance().checkOut();
            statement = connection.prepareStatement("{call insert_into_izlaznafaktura(?,?,?,?,?,?)}");
            statement.setDate(1, new java.sql.Date(izlaznaFaktura.getDatumIzdavanja().getTime()));
            statement.setString(2, izlaznaFaktura.getUkupnaCijena());
            statement.setString(3, izlaznaFaktura.getMjestoIzdavanja());
            statement.setInt(4, izlaznaFaktura.getKupacID());
            statement.setInt(5, izlaznaFaktura.getOrganizacionaJedinicaID());
            statement.setString(6, izlaznaFaktura.getJmbg());
            statement.executeQuery();
            ResultSet rs = statement.executeQuery("select last_insert_id() as last_id");
            rs.next();
            lastId = rs.getInt("last_id");

        } catch (SQLException exception) {
            Logger.getLogger(DAOStavka.class.getName()).log(Level.SEVERE, null, exception);
        } finally {
            if (connection != null) {
                ConnectionPool.getInstance().checkIn(connection);
            }
            if (statement != null) {
                try {
                    statement.close();
                } catch (SQLException exception) {
                    Logger.getLogger(DAOStavka.class.getName()).log(Level.SEVERE, null, exception);
                }
            }
        }
        return lastId;
    }
}
