package com.PatikaDev.Hepler;

import javax.swing.*;
import java.awt.*;
import java.sql.*;
import java.util.List;
import java.util.ArrayList;

public class Helper {

    public static int[] screenCenterPoint(Dimension size){

        int x= (Toolkit.getDefaultToolkit().getScreenSize().width-size.width)/2;
        int y= (Toolkit.getDefaultToolkit().getScreenSize().height-size.height)/2;

        return new int[]{x,y};
    }

    public static void setLayout(){
        for(UIManager.LookAndFeelInfo info : UIManager.getInstalledLookAndFeels()){
            if("Nimbus".equals(info.getName())){
                try {
                    UIManager.setLookAndFeel(info.getClassName());
                } catch (ClassNotFoundException e) {
                    throw new RuntimeException(e);
                } catch (InstantiationException e) {
                    throw new RuntimeException(e);
                } catch (IllegalAccessException e) {
                    throw new RuntimeException(e);
                } catch (UnsupportedLookAndFeelException e) {
                    throw new RuntimeException(e);
                }
                break;
            }
        }
    }
    public static List<String> getEnumValuesFromDatabase() {
        List<String> enumValues = new ArrayList<>();
        String sql = "SELECT\n" +
                "   COLUMN_TYPE\n" +
                "FROM\n" +
                "   INFORMATION_SCHEMA.COLUMNS\n" +
                "WHERE\n" +
                "   TABLE_SCHEMA = \"patika\" AND TABLE_NAME = 'user' AND COLUMN_NAME = 'type';";

        try {
            Statement st = DBConnecter.getInstance().createStatement();
            ResultSet rs = st.executeQuery(sql);

            if (rs.next()) {
                // Extract enum values from the result
                String columnType = rs.getString("COLUMN_TYPE");
                // The result will be something like "enum('operator','educator','student')"
                columnType = columnType.substring(columnType.indexOf("(") + 1, columnType.indexOf(")"));
                String[] values = columnType.replace("'", "").split(",");
                for (String value : values) {
                    enumValues.add(value.trim());
                }
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return enumValues;
    }

    public static boolean isFieldEmpty(JTextField field){
        return field.getText().trim().isEmpty();
    }

    public static void showMassage(String str){
        optionPageTR();
        String msg;
        String title;
        switch(str){
            case "fill" :
                msg = "Lütfen tüm alanları doldurun. Yoksa ...";
                title = "Hata";
                break;
            case "done":
                msg="İşlem Başarılı.";
                title="Sonuç";
            default:
                msg=str;
                title="Masaj";
        }

        JOptionPane.showMessageDialog(null,msg,title,JOptionPane.INFORMATION_MESSAGE);
    }

    public static void optionPageTR(){
        UIManager.put("OptionPane.okButtonText", "Tamam");
    }
}
