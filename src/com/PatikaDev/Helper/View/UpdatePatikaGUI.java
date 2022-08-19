package com.PatikaDev.Helper.View;

import com.PatikaDev.Helper.Helper.Helper;
import com.PatikaDev.Helper.Model.Patika;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class UpdatePatikaGUI extends JFrame{
    private JPanel wrapper;
    private JTextField fld_update_name;
    private JButton btn_update;
    Patika patika;

    public UpdatePatikaGUI(Patika patika){
        this.patika = patika;
        add(wrapper);
        setSize(300,150);
        setLocation(Helper.screenCenterPoint("x",getSize()),Helper.screenCenterPoint("y",getSize()));
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setVisible(true);

        fld_update_name.setText(patika.getName());


        btn_update.addActionListener(e -> {
            if (fld_update_name.getText().isEmpty()){
                Helper.showMsg("fill");
            }else{
                if (Patika.update(patika.getId(),fld_update_name.getText())){
                    Helper.showMsg("done");
                }
                dispose();
            }
        });
    }

    public static void main(String[] args) {
        Patika p = new Patika();
        Helper.setLayout();
        UpdatePatikaGUI pat = new UpdatePatikaGUI(p);
    }







}
