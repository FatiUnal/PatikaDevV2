package com.PatikaDev.Helper.Model;

import com.PatikaDev.Helper.Helper.DBConnector;
import com.PatikaDev.Helper.Helper.Helper;

import javax.swing.text.html.HTMLDocument;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

public class CheckPatika {
    private int id;
    private int user_id;
    private int patika_id;

    public CheckPatika(){}

    public CheckPatika(int id,int patika_id, int user_id) {
        this.id = id;
        this.patika_id = patika_id;
        this.user_id = user_id;

    }

    public static boolean add(int user_id,int patika_id){
        String query = "INSERT INTO userspatika (user_id,patika_id) VALUES (?,?)";

        try {
            PreparedStatement pr = DBConnector.getInstance().prepareStatement(query);
            pr.setInt(1,user_id);
            pr.setInt(2,patika_id);

            int respons = pr.executeUpdate();
            if (respons == -1 ){
                Helper.showMsg("error");
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return true;
    }








    public static ArrayList<CheckPatika> privgetList(int user_id){
        CheckPatika obj;
        ArrayList<CheckPatika> checkPatikaList = new ArrayList<>();
        String query = "SELECT * FROM userspatika WHERE user_id =?";

        try {
            PreparedStatement pr = DBConnector.getInstance().prepareStatement(query);
            pr.setInt(1,user_id);
            ResultSet rs = pr.executeQuery();
            while (rs.next()){
                obj = new CheckPatika();
                obj.setId(rs.getInt("id"));
                obj.setPatika_id(rs.getInt("patika_id"));
                obj.setUser_id(rs.getInt("user_id"));
                checkPatikaList.add(obj);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return checkPatikaList;
    }

    public static Patika findID(String name){ // burda lessonlistte kullanmak için checklistteki patikanın id sini almayı yazdık
        Patika obj = null;
        String query = "SELECT * FROM patika WHERE name = ?";

        try {
            PreparedStatement pr = DBConnector.getInstance().prepareStatement(query);
            pr.setString(1,name);
            ResultSet rs = pr.executeQuery();
            if (rs.next()){
                obj = new Patika();
                obj.setId(rs.getInt("id"));
                obj.setName(rs.getString("name"));
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return obj;
    }

    public static ArrayList<Course> loadPatikasCourseList(int patika_id){
        Course obj;
        ArrayList<Course> lessonlist = new ArrayList<>();
        String query = "SELECT * FROM course WHERE patika_id = ?";

        try {
            PreparedStatement pr = DBConnector.getInstance().prepareStatement(query);
            pr.setInt(1,patika_id);
            ResultSet rs = pr.executeQuery();

            while (rs.next()){
                obj = new Course();
                obj.setId(rs.getInt("id"));
                obj.setName(rs.getString("name"));
                obj.setLang(rs.getString("lang"));
                obj.setPatika_id(rs.getInt("patika_id"));
                obj.setUser_id(rs.getInt("user_id"));
                lessonlist.add(obj);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }


        return lessonlist;
    }



    public static CheckPatika noCopygetList(int user_id,int patika_id){ // seçilen patikayı daha önce sectiyse tekrar seçmesini engellemelk için yaparız
        CheckPatika obj = null;
        String query = "SELECT * FROM userspatika WHERE user_id =? AND patika_id = ?";

        try {
            PreparedStatement pr = DBConnector.getInstance().prepareStatement(query);
            pr.setInt(1,user_id);
            pr.setInt(2,patika_id);
            ResultSet rs = pr.executeQuery();
            if (rs.next()){
                obj = new CheckPatika();
                obj.setId(rs.getInt("id"));
                obj.setPatika_id(rs.getInt("patika_id"));
                obj.setUser_id(rs.getInt("user_id"));
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return obj;
    }







    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getUser_id() {
        return user_id;
    }

    public void setUser_id(int user_id) {
        this.user_id = user_id;
    }

    public int getPatika_id() {
        return patika_id;
    }

    public void setPatika_id(int patika_id) {
        this.patika_id = patika_id;
    }
}
