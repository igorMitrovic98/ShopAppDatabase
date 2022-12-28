package Model.DAO;

import java.sql.*;

import ConnectionPool.ConnectionPool;
import Model.Domen.UlaznaFaktura;

public class DAOUlaznaFaktura {

    public static int dodajFakturu(UlaznaFaktura ulaznaFaktura) {
        Connection connection = null;
        PreparedStatement statement = null;
        int lastId = -1;
        try {
            connection = ConnectionPool.getInstance().checkOut();
            statement = connection.prepareStatement("{call insert_into_ulaznafaktura(?,?,?,?,?,?,?)}");
            statement.setDate(1, new java.sql.Date(ulaznaFaktura.getDatumIzdavanja().getTime()));
            statement.setString(2, ulaznaFaktura.getUkupnaCijena());
            statement.setString(3, ulaznaFaktura.getMjestoIzdavanja());
            statement.setInt(4, ulaznaFaktura.getJib_d());
            statement.setInt(5, ulaznaFaktura.getOrganizacionaJedinicaID());
            statement.setString(6, ulaznaFaktura.getJmbg());
            statement.setString(7, ulaznaFaktura.getBrojRacuna());
            statement.executeQuery();
            ResultSet rs = statement.executeQuery("select last_insert_id() as last_id");
            rs.next();
            lastId = rs.getInt("last_id");

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
        return lastId;
    }
}
