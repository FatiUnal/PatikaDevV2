package com.PatikaDev.Helper.View;

import com.PatikaDev.Helper.Helper.Helper;
import com.PatikaDev.Helper.Model.Course;
import com.PatikaDev.Helper.Model.Operator;

import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.event.TableModelEvent;
import javax.swing.table.DefaultTableModel;

import com.PatikaDev.Helper.Helper.*;
import com.PatikaDev.Helper.Model.Patika;
import com.PatikaDev.Helper.Model.User;

import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;

public class OperatorGUI extends JFrame {
    private final Operator operator;


    private JPanel wrapper;
    private JTabbedPane tab_operator;
    private JPanel pnl_top;
    private JLabel lbl_welcome;
    private JButton btn_exit;
    private JPanel pnl_user_list;
    private JScrollPane scrl_user_list;
    private JTable tbl_user_list;
    private JPanel pnl_user_form;
    private JTextField fld_user_name;
    private JTextField fld_user_username;
    private JTextField fld_user_pass;
    private JComboBox cmb_user_type;
    private JButton btn_user_add;
    private JTextField fld_user_delete;
    private JButton btn_user_delete;
    private JTextField fld_username_sh;
    private JTextField fld_name_sh;
    private JComboBox cmb_user_sh;
    private JButton btn_user_sh;
    private JTextField fld_patika_name;
    private JButton btn_patika_add;
    private JTable tbl_patika_list;
    private JScrollPane scrl_course_list;
    private JTable tbl_course_list;
    private JTextField fld_course_name;
    private JTextField fld_course_lang;
    private JComboBox cmb_course_patika;
    private JComboBox cmb_course_educator;
    private JButton btn_course_add;
    private JPanel pnl_course_add;
    private DefaultTableModel mdl_user_list;
    private Object[] row_user_list;
    private DefaultTableModel mdl_patika_list;
    private Object[] row_patika_list;
    private DefaultTableModel mdl_course_list;
    private Object[] row_course_list;
    JPopupMenu patikaMenu;

    OperatorGUI(Operator operator) {
        this.operator = operator;
        add(wrapper);
        setSize(1000, 500);
        setLocation(Helper.screenCenterPoint("x", getSize()), Helper.screenCenterPoint("y", getSize()));
        setTitle(Config.PROJECT_TITLE);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setVisible(true);

        lbl_welcome.setText("Hosgeldiniz : " + operator.getName());

        //PATİKA
        patikaMenu = new JPopupMenu();
        JMenuItem updateMenu = new JMenuItem("Güncelle");
        JMenuItem deleteMenu = new JMenuItem("Sil");
        patikaMenu.add(updateMenu);
        patikaMenu.add(deleteMenu);
        updateMenu.addActionListener(e -> {
            int sel_id = Integer.parseInt(tbl_patika_list.getValueAt(tbl_patika_list.getSelectedRow(), 0).toString());
            UpdatePatikaGUI updateGuı = new UpdatePatikaGUI(Patika.getFetch(sel_id));
            updateGuı.addWindowListener(new WindowAdapter() {
                @Override
                public void windowClosed(WindowEvent e) {
                    loadPatikaModel();
                    loadPatikaCombo();
                    loadCourseModel();
                }
            });
        });

        deleteMenu.addActionListener(e -> {

            int select_id = Integer.parseInt(tbl_patika_list.getValueAt(tbl_patika_list.getSelectedRow(), 0).toString());
            if (Patika.delete(select_id)) {
                Helper.showMsg("done");
                loadPatikaModel();
                loadCourseModel();
                loadPatikaCombo();
            } else {
                Helper.showMsg("error");
            }

        });


        mdl_patika_list = new DefaultTableModel() {
            @Override
            public boolean isCellEditable(int row, int column) {
                if (column == 0)
                    return false;

                return super.isCellEditable(row, column);
            }
        };

        Object[] cal_patika_list = {"ID", "PAtika Adı"};
        mdl_patika_list.setColumnIdentifiers(cal_patika_list);
        row_patika_list = new Object[cal_patika_list.length];
        loadPatikaModel();

        tbl_patika_list.setModel(mdl_patika_list);
        tbl_patika_list.getTableHeader().setReorderingAllowed(false);
        tbl_patika_list.getColumnModel().getColumn(0).setMaxWidth(75);
        tbl_patika_list.setComponentPopupMenu(patikaMenu);
        tbl_patika_list.addMouseListener(new MouseAdapter() { // sağ tık yaptıgımızda basılı olanı secmesi için bunu ayarlıcaz
            @Override
            public void mousePressed(MouseEvent e) {
                Point point = e.getPoint();
                int selected_row = tbl_patika_list.rowAtPoint(point); // tıkladıgımız kordinatta hangi row var onu secmeyi sağlıyor
                tbl_patika_list.setRowSelectionInterval(selected_row, selected_row); //sağa tıkladıgımız yer secili hale geliyor

            }
        });

        // # PATİKA


        // COURSE
        mdl_course_list = new DefaultTableModel();
        Object[] col_course_list = {"ID", "Ders Adı", "Programlama Dili", "Patika", "Eğitmen"};
        mdl_course_list.setColumnIdentifiers(col_course_list);
        row_course_list = new Object[col_course_list.length]; //satır uzunlugu kolon sayısı ile aynı olması icin yazarız
        loadCourseModel();
        tbl_course_list.setModel(mdl_course_list);
        tbl_course_list.getColumnModel().getColumn(0).setMaxWidth(75);
        tbl_course_list.getTableHeader().setReorderingAllowed(false);
        loadPatikaCombo();
        loadUserCombo();
/*

        //Helper Clasından  Item sınıfı ile cmb lere eleman ekleyebiliyoruz
        cmb_course_patika.addItem(new Item(1,"1.eleman"));
        cmb_course_patika.addItem(new Item(2,"2.eleman"));
        cmb_course_patika.addItem(new Item(3,"3.eleman"));

*/


        // ## COURSE


        // Model User
        mdl_user_list = new DefaultTableModel() {
            @Override
            public boolean isCellEditable(int row, int column) {
                if (column == 0)
                    return false;

                return super.isCellEditable(row, column);
            }
        };
        Object[] cal_user_list = {"ID", "Ad Soyad", "Kullanıcı Adı", "Sifre", "Üyelik Tipi"}; // bu tipte model yaparız
        mdl_user_list.setColumnIdentifiers(cal_user_list);
        row_user_list = new Object[cal_user_list.length];// cal_user uzunlugunda olması için yaptık
        loadUserModel();
        tbl_user_list.setModel(mdl_user_list);
        tbl_user_list.getTableHeader().setReorderingAllowed(false);  //sutunların hareketini engeller
        tbl_user_list.getColumnModel().getColumn(0).setMaxWidth(75);

        tbl_user_list.getSelectionModel().addListSelectionListener(new ListSelectionListener() {
            @Override
            public void valueChanged(ListSelectionEvent e) {
                try {
                    String select_user_id = tbl_user_list.getValueAt(tbl_user_list.getSelectedRow(), 0).toString();
                    fld_user_delete.setText(select_user_id);
                } catch (Exception ex) {
                    System.out.println(ex.getMessage());
                }
            }
        });


        // Tabloya update yapma
        tbl_user_list.getModel().addTableModelListener(e -> {
            if (e.getType() == TableModelEvent.UPDATE) {
                int select_user_id = Integer.parseInt(tbl_user_list.getValueAt(tbl_user_list.getSelectedRow(), 0).toString());
                String select_user_name = tbl_user_list.getValueAt(tbl_user_list.getSelectedRow(), 1).toString();
                String select_user_username = tbl_user_list.getValueAt(tbl_user_list.getSelectedRow(), 2).toString();
                String select_user_pass = tbl_user_list.getValueAt(tbl_user_list.getSelectedRow(), 3).toString();
                String select_user_type = tbl_user_list.getValueAt(tbl_user_list.getSelectedRow(), 4).toString();
                System.out.println("adım 1");

                if (User.update(select_user_id, select_user_name, select_user_username, select_user_pass, select_user_type)) {
                    Helper.showMsg("done");
                    System.out.println("adım 2");
                }
                System.out.println("adım 3");
                loadUserModel();
                loadUserCombo();
                loadCourseModel();

            }
        });


        // Tabloya kullanıcı ekleme

        btn_user_add.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (fld_user_name.getText().isEmpty() || fld_user_username.getText().isEmpty() || fld_user_pass.getText().isEmpty()) {
                    Helper.showMsg("fill");
                } else {
                    String name = fld_user_name.getText();
                    String username = fld_user_username.getText();
                    String pass = fld_user_pass.getText();
                    String type = cmb_user_type.getSelectedItem().toString();

                    if (User.add(name, username, pass, type)) {
                        Helper.showMsg("done");
                        fld_user_name.setText(null);
                        fld_user_username.setText(null);
                        fld_user_pass.setText(null);
                    }
                    loadUserModel();
                    loadUserCombo();
                }


            }
        });

        // cikis butonu
        btn_exit.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose();
                LoginGUI l = new LoginGUI();
            }
        });

        // kullanıcıları silme methodu
        btn_user_delete.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (fld_user_delete.getText().isEmpty()) {
                    Helper.showMsg("fill");
                } else {
                    int userID = Integer.parseInt(fld_user_delete.getText());
                    if (User.delete(userID)) {
                        loadUserModel();
                        loadUserCombo();
                        loadCourseModel();
                        fld_user_delete.setText(null);
                    } else {
                        Helper.showMsg("error");
                    }

                }

            }
        });


        btn_user_sh.addActionListener(e -> {
            String name = fld_name_sh.getText();
            String username = fld_username_sh.getText();
            String type = cmb_user_sh.getSelectedItem().toString();
            String query = User.searchQuery(name, username, type);
            loadUserModel(User.searchUserList(query));

        });


        btn_patika_add.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (fld_patika_name.getText().isEmpty()) {
                    Helper.showMsg("fill");
                } else {
                    String name = fld_patika_name.getText();
                    if (Patika.add(name)) {
                        loadPatikaModel();
                        loadPatikaCombo();
                        Helper.showMsg("done");

                    } else {
                        Helper.showMsg("error");
                    }
                }

            }
        });
        btn_course_add.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (fld_course_name.getText().isEmpty() || fld_course_lang.getText().isEmpty()){
                    Helper.showMsg("fill");
                }else{
                    Item patikaItem = (Item) cmb_course_patika.getSelectedItem();
                    Item userItem = (Item) cmb_course_educator.getSelectedItem();
                    String name = fld_course_name.getText();
                    String lang = fld_course_lang.getText();
                    if (Course.add(userItem.getKey(),patikaItem.getKey(),name,lang)){
                        Helper.showMsg("done");
                        loadCourseModel();
                        fld_course_lang.setText(null);
                        fld_course_name.setText(null);
                    }else{
                        Helper.showMsg("error");
                    }


                }

            }
        });
    }

    public void loadPatikaModel() {
        DefaultTableModel clearMdl = (DefaultTableModel) tbl_patika_list.getModel();
        clearMdl.setRowCount(0);

        for (Patika obj : Patika.getList()) {
            int i = 0;
            row_patika_list[i++] = obj.getId();
            row_patika_list[i++] = obj.getName();
            mdl_patika_list.addRow(row_patika_list);
        }
    }


    public void loadPatikaCombo(){  // loadPatikaModel() olan her yere yazdık cumku patikalar güncellenirse cmb da güncellenmesi lazım
        cmb_course_patika.removeAllItems();
        for (Patika obj: Patika.getList()) {
            cmb_course_patika.addItem(new Item(obj.getId(), obj.getName())); // id yi alip sıraya koyar o sıraya da getname ile yazar
        }
    }
/*
    public void loadUserCombo(){

        cmb_course_educator.removeAllItems();
        for (User obj: User.getList()){
            if (obj.getType().equals("educator")){
                cmb_course_educator.addItem(new Item(obj.getId(),obj.getName()));
            }
        }
    }
*/

        //bunu bi gözden gecir cunku calısmıyo
    public void loadUserCombo(){
        cmb_course_educator.removeAllItems();
        for (User obj: User.getListOnlyEducator()){
                cmb_course_educator.addItem(new Item(obj.getId(),obj.getName()));

        }
    }








    public void loadCourseModel() {
        DefaultTableModel clearMdl = (DefaultTableModel) tbl_course_list.getModel();
        clearMdl.setRowCount(0);

        for (Course obj : Course.getList()) {
            int i = 0;
            row_course_list[i++] = obj.getId();
            row_course_list[i++] = obj.getName();
            row_course_list[i++] = obj.getLang();
            row_course_list[i++] = obj.getPatika().getName();
            row_course_list[i++] = obj.getEducator().getName();
            mdl_course_list.addRow(row_course_list);
        }


    }


    public void loadUserModel() {
        DefaultTableModel clearMDl = (DefaultTableModel) tbl_user_list.getModel();
        clearMDl.setRowCount(0);


        try{
            for (User obj : User.getList()) {
                int i = 0;
                row_user_list[i++] = obj.getId();
                row_user_list[i++] = obj.getName();
                row_user_list[i++] = obj.getUsername();
                row_user_list[i++] = obj.getPass();
                row_user_list[i++] = obj.getType();
                mdl_user_list.addRow(row_user_list);


        }}catch (Exception e){
                System.out.println(e);
        }

    }

    public void loadUserModel(ArrayList<User> arr) {
        DefaultTableModel clearMdl = (DefaultTableModel) tbl_user_list.getModel();
        clearMdl.setRowCount(0);

        for (User obj : arr) {
            int i = 0;
            row_user_list[i++] = obj.getId();
            row_user_list[i++] = obj.getName();
            row_user_list[i++] = obj.getUsername();
            row_user_list[i++] = obj.getPass();
            row_user_list[i++] = obj.getType();
            mdl_user_list.addRow(row_user_list);
        }

    }


    public static void main(String[] args) {
        Helper.setLayout();
        Operator o = new Operator();
        o.setName("fatih");
        o.setId(1);
        o.setPass("fdadsasd");
        o.setType("operator");
        o.setUsername("fatii");
        OperatorGUI op = new OperatorGUI(o);
    }


}
