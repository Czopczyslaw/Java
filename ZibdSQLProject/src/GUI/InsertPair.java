package GUI;

import javax.swing.*;

import static Inside.StringCorrectness.*;

public class InsertPair extends JPanel{

    private  JLabel label;
    private  JTextField textField;
    InsertPair(String text,String content,boolean v){
        label= new JLabel(text);
        textField = new JTextField(text.length());
        textField.setText(content);
        this.setLayout(new BoxLayout(this,BoxLayout.Y_AXIS));
        this.add(label);
        this.add(textField);
        this.setVisible(v);
    }
    public void setTextField(String str){
        this.textField.setText(str);
    }
    public String getText(){
        String temp=textField.getText();
        if(isInt(temp) || isDouble(temp) || isFloat(temp)) return temp;
        else return "'"+temp+"'";
    }


}
