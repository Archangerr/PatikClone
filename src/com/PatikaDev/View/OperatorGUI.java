package com.PatikaDev.View;

import com.PatikaDev.Hepler.Config;
import com.PatikaDev.Hepler.Helper;
import com.PatikaDev.Model.Operator;
import com.PatikaDev.Model.Patika;
import com.PatikaDev.Model.User;

import javax.swing.*;
import javax.swing.event.TableModelEvent;
import javax.swing.table.DefaultTableModel;
import java.awt.event.*;

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
    private JButton btn_remove;
    private JPanel tab_patika;
    private JScrollPane scrl_patika;
    private JTable tbl_patikaList;
    private JTextField fld_patikaName;
    private JButton btn_patikaAdd;
    private JPanel pnl_patikaForm;
    private JButton btn_patikRemove;
    private JPanel tab_Course;
    private JTable tbl_course;
    private JPanel pnl_CourseAdd;
    private JTextField fld_courseName;
    private DefaultTableModel mdl_userList;
    private Object[] row_userList;
    private DefaultTableModel mdl_patikalist;
    private Object[] row_patikaList;
    private JPopupMenu patikaMenu;

    public OperatorGUI(Operator operator){
        this.operator=operator;
        add(wrapper);
        setSize(600,600);
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
        row_userList = new Object[col_userList.length];
        loadUserModel();
        cmb_userType.setModel(new DefaultComboBoxModel(Helper.getEnumValuesFromDatabase().toArray()));


        mdl_patikalist = new DefaultTableModel();
        Object[] col_patikaList = {"ID","Patika Adı"};
        mdl_patikalist.setColumnIdentifiers(col_patikaList);
        row_patikaList = new Object[col_patikaList.length];
        loadpatikaModel();

        patikaMenu = new JPopupMenu();
        JMenuItem updateMenu = new JMenuItem("Güncelle");
        JMenuItem removeMenu = new JMenuItem("Exterminate");
        patikaMenu.add(updateMenu);
        patikaMenu.add(removeMenu);
        tbl_patikaList.setComponentPopupMenu(patikaMenu);


        updateMenu.addActionListener(e -> {
            int selectId= Integer.parseInt(tbl_patikaList.getValueAt(tbl_patikaList.getSelectedRow(),0).toString());

            UpdatePatikaGui updateGui = new UpdatePatikaGui(Patika.getFetch(selectId));
            updateGui.addWindowListener(new WindowAdapter() {
                @Override
                public void windowClosed(WindowEvent e) {
                    loadpatikaModel();
                }
            });
        });

        tbl_patikaList.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                int r = tbl_patikaList.rowAtPoint(e.getPoint());
                if (r >= 0 && r < tbl_patikaList.getRowCount()) {
                    tbl_patikaList.setRowSelectionInterval(r, r);
                } else {
                    tbl_patikaList.clearSelection();
                }

                int rowindex = tbl_patikaList.getSelectedRow();
                if (rowindex < 0)
                    return;
                if (e.isPopupTrigger() && e.getComponent() instanceof JTable ) {

                    patikaMenu.show(e.getComponent(), e.getX(), e.getY());
                }
            }
        });


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

                    loadUserModel();
                    fld_userName.setText("");
                    fld_userUName.setText("");
                    fld_userPass.setText("");
                    

                    Helper.showMassage("done");
                }
                else{
                    Helper.showMassage("error");
                }
            }
        });




        btn_remove.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if(tbl_userList.getSelectedRow()!=-1) {
                    int id = (int) tbl_userList.getValueAt(tbl_userList.getSelectedRow(), 0);
                    if(User.remove(id)) {
                        Helper.showMassage("done");
                        loadUserModel();
                    }
                    else
                        Helper.showMassage("error");
                }else
                    Helper.showMassage("error");

            }
        });
        tbl_userList.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                super.mouseClicked(e);
                int row = tbl_userList.getSelectedRow();
                int id = (int) tbl_userList.getValueAt(row, 0);
                System.out.println("row = -> " + row + " id = -> " + id);
            }
        });

        tbl_userList.getModel().addTableModelListener(e->{
            if(e.getType()== TableModelEvent.UPDATE){
                int id=Integer.parseInt(tbl_userList.getValueAt(tbl_userList.getSelectedRow(),0).toString());
                String name=tbl_userList.getValueAt(tbl_userList.getSelectedRow(),1).toString();
                String uname=tbl_userList.getValueAt(tbl_userList.getSelectedRow(),2).toString();
                String pass=tbl_userList.getValueAt(tbl_userList.getSelectedRow(),3).toString();
                String type=tbl_userList.getValueAt(tbl_userList.getSelectedRow(),4).toString();
                if(User.update(id,name,uname,pass,type)){
                    loadUserModel();
                    Helper.showMassage("done");
                }
                else
                    Helper.showMassage("error");
            }
        });

        btn_patikaAdd.addActionListener(e -> {
            if(Helper.isFieldEmpty(fld_patikaName)){
                Helper.showMassage("fill");
            }
            else{
                String name=fld_patikaName.getText();
                if(Patika.Add(name)){

                    loadpatikaModel();
                    fld_patikaName.setText("");
                    Helper.showMassage("done");
                }
                else{
                    Helper.showMassage("error");
                }
            }

        });
        btn_patikRemove.addActionListener(e -> {

        });
        tbl_patikaList.addMouseListener(new MouseAdapter() {
        });
        btn_logOut.addActionListener(e -> {
            dispose();
        });
    }

    private void loadpatikaModel() {
        DefaultTableModel clearModel = (DefaultTableModel) tbl_patikaList.getModel();
        clearModel.setRowCount(0);
        int i=0;
        for(Patika obj : Patika.getList()){
            i=0;
            row_patikaList[i++]=obj.getId();
            row_patikaList[i++]=obj.getName();
            mdl_patikalist.addRow(row_patikaList);
        }
        tbl_patikaList.setModel(mdl_patikalist);
        tbl_patikaList.getTableHeader().setReorderingAllowed(false);
        tbl_patikaList.getColumnModel().getColumn(0).setMaxWidth(77);
    }


    public void loadUserModel(){

        DefaultTableModel clearModel = (DefaultTableModel) tbl_userList.getModel();
        clearModel.setRowCount(0);
        int i=0;
        for (User obj : User.getList()){
            row_userList[0] = obj.getId();
            row_userList[1] = obj.getName();
            row_userList[2] = obj.getUname();
            row_userList[3] = obj.getPass();
            row_userList[4] = obj.getType();
            mdl_userList.addRow(row_userList);
        }

        tbl_userList.setModel(mdl_userList);
        tbl_userList.getTableHeader().setReorderingAllowed(false);

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
