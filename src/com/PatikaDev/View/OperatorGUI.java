package com.PatikaDev.View;

import com.PatikaDev.Hepler.Config;
import com.PatikaDev.Hepler.Helper;
import com.PatikaDev.Model.Operator;
import com.PatikaDev.Model.User;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class OperatorGUI extends JFrame {
    private JPanel wrapper;

    private final Operator operator;
    private JTabbedPane tabbedPane1;
    private JPanel tab_operator;
    private JLabel lbl_welcome;
    private JButton btn_logOut;
    private JScrollPane scrl_userList;
    private JTable tbl_userList;
    private JPanel pnl_userForm;
    private JTextField fld_userName;
    private JTextField fld_userUName;
    private JTextField fld_userPass;
    private JComboBox cmb_userType;
    private JButton btn_userAdd;
    private DefaultTableModel mdl_userList;
    private Object[] row_userList;

    public OperatorGUI(Operator operator){
        this.operator=operator;
        add(wrapper);
        setSize(400,400);
        int x = Helper.screenCenterPoint(getSize())[0];
        int y = Helper.screenCenterPoint(getSize())[1];
        setLocation(x,y);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setTitle(Config.PROJECT_TITLE);
        setVisible(true);


        lbl_welcome.setText("Hosgeldiniz "+ operator.getName());

        mdl_userList= new DefaultTableModel();
        Object[] col_userList = {"ID","Ad Soyad", "Kullanıcı Adı", "Şifre" , "Üyelik Tipi"};
        mdl_userList.setColumnIdentifiers(col_userList);

        for (User obj : User.getList()){
            Object[] row = new Object[col_userList.length];
            row[0] = obj.getId();
            row[1] = obj.getName();
            row[2] = obj.getUname();
            row[3] = obj.getPass();
            row[4] = obj.getType();
            mdl_userList.addRow(row);
        }

        tbl_userList.setModel(mdl_userList);
        tbl_userList.getTableHeader().setReorderingAllowed(false);
        cmb_userType.setModel(new DefaultComboBoxModel(Helper.getEnumValuesFromDatabase().toArray()));

        btn_userAdd.addActionListener(e -> {
            if(Helper.isFieldEmpty(fld_userName) || Helper.isFieldEmpty(fld_userPass) || Helper.isFieldEmpty(fld_userUName)){
                Helper.showMassage("fill");
            }
            else{
                String name=fld_userName.getText();
                String uname=fld_userUName.getText();
                String pass=fld_userPass.getText();
                String type = cmb_userType.getSelectedItem().toString();
                if(User.add(name,uname,pass,type)){
                    // Add the new user to the table model
                    DefaultTableModel tableModel = (DefaultTableModel) tbl_userList.getModel();
                    Object[] row = {mdl_userList.getValueAt(mdl_userList.getRowCount() - 1, 0),name, uname, pass, type }; // Assuming User.getLastId() gets the ID of the last added user
                    tableModel.addRow(row);  // Add the new row to the model

                    // Notify the table about the data change
                    tableModel.fireTableDataChanged();

//                    // Optional: Revalidate and repaint if necessary
//                    tbl_userList.revalidate();
//                    tbl_userList.repaint();

                    Helper.showMassage("done");
                }
                else{
                    Helper.showMassage("error");
                }
            }
        });
    }

    public void loadUserModel(){

        DefaultTableModel clearModel = (DefaultTableModel) tbl_userList.getModel();


    }

    public static void main(String[] args) {

        Operator op = new Operator();
        Helper.setLayout();
        op.setId(1);
        op.setName("NoMore");
        op.setPass("12345");
        op.setType("operator");
        op.setUname("Arch");

        OperatorGUI opGUI = new OperatorGUI(op);
    }




}
