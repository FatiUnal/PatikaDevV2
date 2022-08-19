package com.PatikaDev.Helper.Model;

import com.PatikaDev.Helper.Helper.DBConnector;

import java.io.PipedReader;
import java.io.Serializable;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

public class Patika {
    private int id;
    private String name;

    public Patika(int id, String name) {
        this.id = id;
        this.name = name;
    }
    public Patika() {


    }

    public static ArrayList<Patika> getList(){
        Patika obj;
        ArrayList<Patika> patikaList = new ArrayList<>();
        String query = "SELECT * FROM patika";

        try {
            Statement st = DBConnector.getInstance().createStatement();
            ResultSet rs = st.executeQuery(query);
            while (rs.next()){
                obj = new Patika();
                obj.setId(rs.getInt("id")); // veritabanındaki her bir kulllanıcı icin obje uretiriz ve bunları array liste aktarırız
                obj.setName(rs.getString("name"));
                patikaList.add(obj);
            }
            st.close();
            rs.close();

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return patikaList;
    }




    public static boolean add(String name){
        String query = "INSERT INTO patika (name) VALUES (?)";

        try {
            PreparedStatement pr = DBConnector.getInstance().prepareStatement(query);
            pr.setString(1,name);
            return pr.executeUpdate() != -1;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return true;

    }
    public static Patika getFetch(int id){ //verilen id deki patika objesini dönduruyor
        Patika obj = null;
        String query = "SELECT * FROM patika WHERE id = ?";

        try {
            PreparedStatement pr = DBConnector.getInstance().prepareStatement(query);
            pr.setInt(1,id);
            ResultSet rs = pr.executeQuery();
            if (rs.next()){
                obj = new Patika(rs.getInt("id"),rs.getString("name"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return obj;
    }

    public static boolean update(int id ,String name){{
        String query = "UPDATE patika SET name = ? WHERE id = ?";

        try {
            PreparedStatement pr = DBConnector.getInstance().prepareStatement(query);
            pr.setInt(2,id);
            pr.setString(1,name);
            return pr.executeUpdate() != -1;

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
        return true;
    }

    public static boolean delete(int id){
        String query = "DELETE FROM patika WHERE id = ?";
        ArrayList<Course> courseList = Course.getList();
        for (Course c:courseList){
            if (c.getPatika().getId() == id){
                Course.delete(c.getId());
            }

        }

        try {
            PreparedStatement pt = DBConnector.getInstance().prepareStatement(query);
            pt.setInt(1,id);
            return pt.executeUpdate() != -1;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return true;
    }












    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
