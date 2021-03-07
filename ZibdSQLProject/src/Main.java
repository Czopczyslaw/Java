import java.sql.*;


public class Main {
    public void main(String [] args) throws SQLException {
       // ConnectionWork();
    }
    public void ConnectionWork() throws SQLException {
        String Url = "jdbc:oracle:thin:@155.158.112.45:1521:oltpstud";
        String User = "Ziibd5";
        String Password = "haslo1";
        Connection myConnection = null;
        Statement myStatement = null;
        ResultSet rs = null;
        try {
            Class.forName("oracle.jdbc.driver.OracleDriver");       //wczytanie klasy sterownika
            myConnection = DriverManager.getConnection(Url, User, Password);
            System.out.println("Polaczono z baza danych :)");
        } catch (ClassNotFoundException e) {
            System.out.println("Jakis problem z klasa sterownika");
            e.printStackTrace();
        } catch (SQLException e) {
            System.out.println("UPS, jakis problemos");
            e.printStackTrace();
        }
        try {
            myStatement = myConnection.createStatement(); //tutaj kontynuuj
        } catch (SQLException e) {
            e.printStackTrace();
        }
        try{
            rs = myStatement.executeQuery("select * from employees");
        } catch (SQLException e) {
            e.printStackTrace();
        }
        try {
            while (rs.next()) {
            String last_name = rs.getString("last_name");
                System.out.println(last_name);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        rs.close();


    }


}

