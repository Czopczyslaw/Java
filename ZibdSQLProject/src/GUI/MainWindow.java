package GUI;

import Inside.ConnectionToDatabase;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.event.*;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.Vector;

public class MainWindow {
    public static ConnectionToDatabase CTD;
    private JButton executeButton;
    private JPanel mainPanel;
    private JTable resultsTable;
    private JTextField queryTextField;
    private JButton resetButton;
    private JButton employeesButton;
    private JButton departmentsButton;
    private JButton countriesButton;
    private JButton job_gradesButton;
    private JButton job_historyButton;
    private JButton jobsButton;
    private JButton locationsButton;
    private JButton regionsButton;
    private JPanel Selectables;
    private JButton insertButton;
    private JPanel insertPanel;
    private ResultSet rs;
    private String shownTableName;


    public MainWindow() {
        CTD = new ConnectionToDatabase(this);

        resultsTable.addMouseListener(new java.awt.event.MouseAdapter(){
            @Override
            public void mouseClicked(java.awt.event.MouseEvent evt){
                int row = resultsTable.rowAtPoint(evt.getPoint());
                int col = resultsTable.columnAtPoint(evt.getPoint());
                if(evt.getModifiers()==MouseEvent.BUTTON1_MASK)
                    new UpdateDialog(rs,shownTableName,CTD,row,col,resultsTable);
                else if(evt.getModifiers()==MouseEvent.BUTTON3_MASK){
                   new DeleteDialog(rs,shownTableName,CTD,row,col,resultsTable);
                }

            }
        });
        insertButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new InsertDialog(rs,shownTableName,CTD);
            }
        });
        employeesButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                rs = CTD.sendQuery("select * from employees");
                shownTableName="Employees";
                InsertIntoTable(rs);
            }
        });
        departmentsButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                rs = CTD.sendQuery("select * from departments");
                shownTableName="Departments";
                InsertIntoTable(rs);
            }
        });
        countriesButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                rs = CTD.sendQuery("select * from countries");
                shownTableName="Countries";
                InsertIntoTable(rs);
            }
        });
        job_gradesButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                rs = CTD.sendQuery("select * from job_grades");
                shownTableName="Job_Grades";
                InsertIntoTable(rs);
            }
        });
        job_historyButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                rs = CTD.sendQuery("select * from job_history");
                shownTableName="Job_history";
                InsertIntoTable(rs);
            }
        });
        jobsButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                rs = CTD.sendQuery("select * from jobs");
                shownTableName="Jobs";
                InsertIntoTable(rs);
            }
        });
        locationsButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                rs = CTD.sendQuery("select * from locations");
                shownTableName="Locations";
                InsertIntoTable(rs);
            }
        });
        regionsButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                rs = CTD.sendQuery("select * from regions");
                shownTableName="Regions";
                InsertIntoTable(rs);
            }
        });
        resetButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String resetQuery = "SELECT\n" + "  table_name\n" + "FROM\n" + "  all_tables\n" + "WHERE\n owner ='ZIIBD1' or owner='ZIIBD5'" + "ORDER BY\n" + "  owner, table_name";
                rs = CTD.sendQuery(resetQuery);
                shownTableName="";
                InsertIntoTable(rs);
            }
        });
        executeButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String query = queryTextField.getText();
                rs = CTD.sendQuery(query);
                if (query == null) {
                    JOptionPane.showMessageDialog(null, "Blednie wpisane zapytanie");
                } else {
                    InsertIntoTable(rs);
                }

            }
        });


    }

    //Transforms data from query into JTable model
    private static DefaultTableModel buildTableModel(ResultSet resultSet) throws SQLException {
        ResultSetMetaData metaData = resultSet.getMetaData();


        //names of columns
        Vector<String> columnNames = new Vector<>();
        int columnCount = metaData.getColumnCount();

        for (int column = 1; column <= columnCount; column++) {
            columnNames.add(metaData.getColumnName(column));
        }
        //data of the table
        Vector<Vector<Object>> data = new Vector<>();



        while (resultSet.next()) {
            Vector<Object> vector = new Vector<>();
            for (int columnIndex = 1; columnIndex <= columnCount; columnIndex++) {
                vector.add(resultSet.getObject(columnIndex));
            }
            data.add(vector);
        }

        return new DefaultTableModel(data, columnNames);

    }

    public static void main(String[] args) {
        try {
            UIManager.setLookAndFeel("com.sun.java.swing.plaf.windows.WindowsLookAndFeel"); //Windows Look and feel
        } catch (ClassNotFoundException | InstantiationException | IllegalAccessException | UnsupportedLookAndFeelException e) {
            e.printStackTrace();
        }
        JFrame frame = new JFrame("MainWindow");

        frame.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                CTD.closeConnection();
                System.exit(0);
            }
        });
        frame.setContentPane(new MainWindow().mainPanel);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        frame.setVisible(true);
        frame.setLocationRelativeTo(null);
    }

    public void InsertIntoTable(ResultSet resultSet) {
        //To remove previously added rows
        while (resultsTable.getRowCount() > 0) {
            ((DefaultTableModel) resultsTable.getModel()).removeRow(0);
        }
        //updates the table
        try {
            resultsTable.setModel(buildTableModel(resultSet));


        } catch (SQLException e) {
            e.printStackTrace();

        }

    }


    private void buildInsertablePanel(ResultSet resultSet) throws SQLException {
        insertPanel.removeAll();
        ResultSetMetaData metaData = resultSet.getMetaData();

        //names of columns
        Vector<String> columnNames = new Vector<>();
        int columnCount = metaData.getColumnCount();
        JLabel[] labels = new JLabel[columnCount];
        for (int column = 1; column <= columnCount; column++) {
            columnNames.add(metaData.getColumnName(column));
        }

        for(int col = 1; col<= columnCount;col++){
            labels[col-1]=new JLabel(metaData.getColumnName(col));
            labels[col-1].setSize(80,80);
            insertPanel.add(labels[col-1]);
        }

        insertPanel.revalidate();
        insertPanel.repaint();
    }

}
