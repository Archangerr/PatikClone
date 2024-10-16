package com.PatikaDev.View;

import com.PatikaDev.Hepler.Helper;
import com.PatikaDev.Model.Patika;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class UpdatePatikaGui  extends  JFrame{
    private JPanel wrapper;
    private JTextField fld_update;
    private JButton btn_update;
    private Patika patika;



    public UpdatePatikaGui(Patika patika){
        this.patika=patika;
        add(wrapper);
        setSize(600,600);
        int x = Helper.screenCenterPoint(getSize())[0];
        int y = Helper.screenCenterPoint(getSize())[1];
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocation(x,y);
        setVisible(true);
        fld_update.setText(patika.getName());
        btn_update.addActionListener(e -> {
            if(Helper.isFieldEmpty(fld_update)){
                Helper.showMassage("fill");
            }else {
                if(Patika.update(patika.getId(), fld_update.getText())){
                    Helper.showMassage("done");
                }
            dispose();
            }
        });
    }

}
