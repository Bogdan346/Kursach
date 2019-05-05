package sample;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.DriverManager;
import java.sql.*;


public class DataBaseConnector {

    public Connection dbConnection;

    /// Подключение к БД
    public Connection getDbConnection()
            throws ClassNotFoundException, SQLException {
        String dbName = "root";
        String dbPass = "57002488";
        String connectionUrl = "jdbc:mysql://127.0.0.1:3306/mydatabase?serverTimezone=UTC&useSSL=false";
        Class.forName("com.mysql.cj.jdbc.Driver");
        dbConnection = DriverManager.getConnection(connectionUrl, dbName, dbPass);
        return dbConnection;

    }

    ///  метод добавления инф. в БД
    public void addElementToDB(String name, String link ) {


        String sql = "INSERT INTO " + Const.MYTABLE + "(" + Const.MYTABLE_NAME + "," + Const.MYTABLE_LINK + ")" + "VALUES (?,?)";//SQL

        //заполнение полей данными.В определённые поля опред. данн
        try {
            PreparedStatement preparedStatement = getDbConnection().prepareStatement(sql);
            preparedStatement.setString(1, name);
            preparedStatement.setString(2, link);
            preparedStatement.execute();
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }
}











