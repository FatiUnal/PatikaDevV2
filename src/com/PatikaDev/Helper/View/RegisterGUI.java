package com.PatikaDev.Helper.View;

import com.PatikaDev.Helper.Helper.Config;
import com.PatikaDev.Helper.Helper.Helper;
import com.PatikaDev.Helper.Model.User;

import javax.swing.*;

public class RegisterGUI extends JFrame {

    private JPanel wrapper;
    private JTextField fld_register_name;
    private JTextField fld_register_username;
    private JButton btn_register_reg;
    private JPasswordField psf_register_pass;

    public RegisterGUI(){
        add(wrapper);
        setSize(300,300);
        setTitle(Config.PROJECT_TITLE);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocation(Helper.screenCenterPoint("x",getSize()),Helper.screenCenterPoint("y",getSize()));
        setResizable(false);
        setVisible(true);
        btn_register_reg.addActionListener(e -> {
            if (fld_register_name.getText().isEmpty() || fld_register_username.getText().isEmpty() || psf_register_pass.getText().isEmpty()){
                Helper.showMsg("fill");
                fld_register_username.setText(null);
                fld_register_name.setText(null);
                psf_register_pass.setText(null);
            }else{
                if (User.addRegister(fld_register_name.getText(),fld_register_username.getText(),psf_register_pass.getText())){
                    Helper.showMsg("done");
                    fld_register_username.setText(null);
                    fld_register_name.setText(null);
                    psf_register_pass.setText(null);
                }else{
                    fld_register_username.setText(null);
                    fld_register_name.setText(null);
                    psf_register_pass.setText(null);

                }
            }
        });
    }
}
