package com.PatikaDev.Helper.View;

import com.PatikaDev.Helper.Helper.Config;
import com.PatikaDev.Helper.Helper.Helper;
import com.PatikaDev.Helper.Model.Operator;
import com.PatikaDev.Helper.Model.Student;
import com.PatikaDev.Helper.Model.User;

import javax.swing.*;

public class StudentGUI extends JFrame{
    private JPanel wrapper;
    private JLabel lbl_student_name;
    private User student;

    public StudentGUI(){
        add(wrapper);
        setSize(400,500);
        setTitle(Config.PROJECT_TITLE);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocation(Helper.screenCenterPoint("x",getSize()),Helper.screenCenterPoint("y",getSize()));
        setResizable(false);
        setVisible(true);
    }
}
