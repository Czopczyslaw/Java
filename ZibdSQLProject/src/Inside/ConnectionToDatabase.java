package Inside;//155.158.112.45
//port : 1521
//sid: oltpstud
//user: Ziibd5
//pass: haslo1

import GUI.MainWindow;

import java.sql.*;

//this class connects to the sql database
public class ConnectionToDatabase {
    MainWindow mainWindow = null;
    String Url = "jdbc:oracle:thin:@155.158.112.45:1521:oltpstud";
    String User = "Ziibd5";
    String Password = "haslo1";
    Connection myConnection = null;
    Statement myStatement = null;
    ResultSet resultSet = null;
    static private Boolean close = false;
    public ConnectionToDatabase(MainWindow mainWindow) {
        this.mainWindow=mainWindow;
        try {
            Class.forName("oracle.jdbc.driver.OracleDriver");       //wczytanie klasy sterownika
            myConnection = DriverManager.getConnection(Url, User, Password);
            System.out.println("Polaczono z baza danych :)");
            myStatement=myConnection.createStatement();
            resultSet=myStatement.executeQuery("SELECT\n" +
                    "  table_name\n" +
                    "FROM\n" +
                    "  all_tables\n" +
                    "WHERE\n owner ='ZIIBD1' or owner='ZIIBD5'"+
                    "ORDER BY\n" +
                    "  owner, table_name");
            mainWindow.InsertIntoTable(resultSet);//wrzucenie wyniku w do tabeli apki
            } catch (ClassNotFoundException e) {
            e.printStackTrace();

        } catch (SQLException e) {
            e.printStackTrace();
        } finally{
            try{
            //resultSet.close();
            //myStatement.close();
            //myConnection.close();
        }catch(Exception e){}
        }

    }
    public ResultSet sendQuery(String query){   //wyslanie zapytania
        try{
        resultSet=myStatement.executeQuery(query);
        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("bledne zapytanie");
            resultSet=null;
        }
        return resultSet;
    }
    public boolean closeConnection(){   //zamkniecie polaczenia przy zamknieciu apki
        try{
            resultSet.close();
            myStatement.close();
            myConnection.close();
        }catch (SQLException e){
            e.printStackTrace();
            return false;
        }
        System.out.println("polączenie skutecznie rozłączone");
        return true;
    }
    }
