package com.PatikaDev.Helper.View;

import com.PatikaDev.Helper.Helper.Config;
import com.PatikaDev.Helper.Helper.Helper;
import com.PatikaDev.Helper.Model.Operator;
import com.PatikaDev.Helper.Model.Student;
import com.PatikaDev.Helper.Model.User;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class LoginGUI extends JFrame {
    private JPanel wrapper;
    private JPanel wtop;
    private JPanel wbottom;
    private JTextField fld_login_name;
    private JPasswordField psf_login_pass;
    private JButton btn_login_log;
    private JButton btn_login_register;

    public LoginGUI(){
        add(wrapper);
        setSize(400,500);
        setTitle(Config.PROJECT_TITLE);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocation(Helper.screenCenterPoint("x",getSize()),Helper.screenCenterPoint("y",getSize()));
        setResizable(false);
        setVisible(true);
        btn_login_log.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (fld_login_name.getText().isEmpty() || psf_login_pass.getPassword().toString().isEmpty()){
                    Helper.showMsg("fill");
                    fld_login_name.setText(null);
                    psf_login_pass.setText(null);
                }else{
                    User u= User.getFetch(fld_login_name.getText(),psf_login_pass.getText());
                    if (u == null){
                        Helper.showMsg("Kullanıcı Bulunamadı");
                    }else{
                        switch (u.getType()){
                            case "operator":
                                OperatorGUI o = new OperatorGUI((Operator) u);
                                break;
                            case "student":
                                StudentGUI s = new StudentGUI();
                                break;
                            case "educator":
                                EducatorGUI k = new EducatorGUI();
                                break;
                        }
                        dispose();
                    }

                }
            }
        });
        btn_login_register.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose();
                RegisterGUI reg = new RegisterGUI();
            }
        });
    }









    public static void main(String[] args) {
        LoginGUI l = new LoginGUI();
    }

}
