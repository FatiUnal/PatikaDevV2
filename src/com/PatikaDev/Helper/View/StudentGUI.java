package com.PatikaDev.Helper.View;

import com.PatikaDev.Helper.Helper.Config;
import com.PatikaDev.Helper.Helper.Helper;
import com.PatikaDev.Helper.Model.CheckPatika;
import com.PatikaDev.Helper.Model.Patika;
import com.PatikaDev.Helper.Model.Student;
import com.PatikaDev.Helper.Model.User;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class StudentGUI extends JFrame {
    private JPanel wrapper;
    private JTabbedPane tabbedPane1;
    private JTable tbl_student_list;
    private JTextField fld_student_patika;
    private JButton btn_student_kaydet;
    private JTable tbl_student_checklist;
    private JTable tbl_student_patikalessons;
    private User user;
    private DefaultTableModel mdl_student_list;
    private Object[] row_student_list;
    private DefaultTableModel mdl_student_checklist;
    private Object[] row_student_checklist;


    public StudentGUI(User user) {
        this.user = user;
        add(wrapper);
        setSize(400, 500);
        setTitle(Config.PROJECT_TITLE);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocation(Helper.screenCenterPoint("x", getSize()), Helper.screenCenterPoint("y", getSize()));
        setResizable(false);
        setVisible(true);


        mdl_student_list = new DefaultTableModel() {
            @Override
            public boolean isCellEditable(int row, int column) {
                if (column == 0)
                    return false;

                return super.isCellEditable(row, column);
            }
        };


        // patikaların modeli

        Object[] col_student_list = {"ID", "Patika"};
        mdl_student_list.setColumnIdentifiers(col_student_list);
        row_student_list = new Object[col_student_list.length];
        loadStudentMdl();
        tbl_student_list.setModel(mdl_student_list);
        tbl_student_list.getTableHeader().setReorderingAllowed(false);
        tbl_student_list.getColumnModel().getColumn(0).setMaxWidth(75);


        btn_student_kaydet.addActionListener(new ActionListener() { // burda kayıtlı patika kısmına eklemek için işlemler yaparız
            @Override
            public void actionPerformed(ActionEvent e) {
                if (fld_student_patika.getText().isEmpty()) {
                    Helper.showMsg("fill");
                } else {
                    int savePatikaid = Integer.parseInt(fld_student_patika.getText());
                    if (CheckPatika.noCopygetList(user.getId(), savePatikaid) == null){
                        if (CheckPatika.add(user.getId(), savePatikaid)) {
                            Helper.showMsg("done");
                            fld_student_patika.setText(null);
                            loadStudendCheckMdl();
                        }else{
                            Helper.showMsg("error");
                            fld_student_patika.setText(null);
                        }
                    }else{
                        Helper.showMsg("Bu patikaya kayıt oldun");
                        fld_student_patika.setText(null);
                    }
                }
            }
        });



        //kayıtlı patikalrın modeli

        mdl_student_checklist = new DefaultTableModel();
        Object[] col_student_checklist = {"ID", "Patika Adı"};
        mdl_student_checklist.setColumnIdentifiers(col_student_checklist);
        row_student_checklist = new Object[col_student_checklist.length];
        loadStudendCheckMdl();
        tbl_student_checklist.setModel(mdl_student_checklist);
        tbl_student_checklist.getColumnModel().getColumn(0).setMaxWidth(75);
        tbl_student_checklist.getTableHeader().setReorderingAllowed(false);
    }







    public void loadStudendCheckMdl() { // burda herkesin kendi kaydettiği patikayı görmesini sağladım
        DefaultTableModel clearmdl = (DefaultTableModel) tbl_student_checklist.getModel();
        clearmdl.setRowCount(0);
        System.out.println("user id: "+user.getId());

        for (CheckPatika obj : CheckPatika.privgetList(user.getId())) {
            int i = 0;
            System.out.println("i: "+i);
            row_student_checklist[i++] = obj.getId();
            row_student_checklist[i++] = Patika.getFetch(obj.getPatika_id()).getName();

            mdl_student_checklist.addRow(row_student_checklist);
        }

    }

    public void loadStudentMdl() {
        DefaultTableModel clearMdl = (DefaultTableModel) tbl_student_list.getModel();
        clearMdl.setRowCount(0);

        for (Patika obj : Patika.getList()) {
            int i = 0;
            row_student_list[i++] = obj.getId();
            row_student_list[i++] = obj.getName();

            mdl_student_list.addRow(row_student_list);
        }
    }


}
