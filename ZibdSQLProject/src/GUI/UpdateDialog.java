package GUI;

import Inside.ConnectionToDatabase;
import Inside.StringCorrectness;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;

public class UpdateDialog {
    private static JDialog d;
    UpdateDialog(ResultSet rs, String tableName, ConnectionToDatabase CTD,int row, int col,JTable table) {
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
        d = new JDialog(frame,"Update Dialog",true);


        d.setLayout(new BoxLayout(d.getContentPane(),BoxLayout.Y_AXIS));

        JButton insertButton = new JButton("Update database");
        int finalColumnCount = columnCount;
        ResultSetMetaData finalMetaData = metaData;
        insertButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                //tutaj wsadz zapytando sqlowe wysylajace inserta\
                String sql ="UPDATE " + tableName+" SET ";
                try{
                    //pobieranie nazw wartosci wpisywanych
                    for(int column = 1; column<= finalColumnCount; column++){
                        if(d.getContentPane().getComponent(column-1) instanceof InsertPair){
                            String t=((InsertPair) d.getContentPane().getComponent(column-1)).getText();

                            sql+=finalMetaData.getColumnName(column) +"="+t;
                            if(column==finalColumnCount) continue;
                            sql+=",";
                    }
                    }
                    sql+=" WHERE ";
                    for(int column = 1;column<=finalColumnCount;column++){
                        String temp=table.getValueAt(row,column-1).toString();
                        if(StringCorrectness.isInt(temp)||StringCorrectness.isFloat(temp)||StringCorrectness.isDouble(temp)){
                            sql+=finalMetaData.getColumnName(column) +"="+temp;
                        }
                        else{
                            sql+=finalMetaData.getColumnName(column) +"="+"'"+temp+"'";
                        }

                        if(column==finalColumnCount) continue;
                        sql+=" AND ";
                    }

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

                //System.out.println(toString());
                String str =table.getValueAt(row,column-1).toString();
                d.add(new InsertPair(metaData.getColumnName(column),str,true));
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        d.add(insertButton);
        d.setSize(300,(columnCount*50)+20);
        d.setVisible(true);
    }
    }


