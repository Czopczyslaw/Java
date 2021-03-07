package GUI;
import Inside.ConnectionToDatabase;
import sun.plugin2.main.server.ResultHandler;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Vector;
//dialog panel that gives you an option to insert new data
public class InsertDialog {
    private static JDialog d;
    private static JPanel labels;
    private static JPanel textFields;
    private static JScrollPane s;

    InsertDialog(ResultSet rs, String tableName, ConnectionToDatabase CTD){
        //pobranie metadata z rs zeby wygenerowac pola do wpisywania
        ResultSetMetaData metaData = null;

        try {
            metaData = rs.getMetaData();

        } catch (SQLException e) {
            e.printStackTrace();
        }
        int columnCount = 0;
        try {
            columnCount = metaData.getColumnCount();
        } catch (SQLException e) {
            e.printStackTrace();
        }


        JFrame frame = new JFrame();
        d = new JDialog(frame,"Insert Dialog",true);
        s = new JScrollPane();

        d.setLayout(new BoxLayout(d.getContentPane(),BoxLayout.Y_AXIS));
        //s.add(d);

        JButton insertButton = new JButton("Insert into database");
        int finalColumnCount = columnCount;
        ResultSetMetaData finalMetaData = metaData;
        insertButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                //tutaj wsadz zapytando sqlowe wysylajace inserta\
                String sql ="INSERT INTO " + tableName + "(";
                try{
                    //pobieranie nazw wartosci wpisywanych
                    for(int column = 1; column<= finalColumnCount; column++){
                        sql+=finalMetaData.getColumnName(column);
                        if(column==finalColumnCount) continue;
                        sql+=",";
                    }
                    sql+=") VALUES(";
                    //wpisane wartosci
                    for(int column = 1; column<= finalColumnCount; column++){
                       if(d.getContentPane().getComponent(column-1) instanceof InsertPair){
                            String t=((InsertPair) d.getContentPane().getComponent(column-1)).getText();
                           sql+= t;
                           if(column==finalColumnCount) continue;
                           sql+=",";
                        }
                    }

                    sql+=")";
                System.out.println(sql);

                CTD.sendQuery(sql);


                d.dispose();


            }
            catch(SQLException ex){
                ex.printStackTrace();
            }}
        });

        for (int column = 1; column <= columnCount; column++) {
            try {
                d.add(new InsertPair(metaData.getColumnName(column),"",true));
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        d.add(insertButton);
        d.setSize(300,(columnCount*50)+20);
        d.setVisible(true);
    }

}
