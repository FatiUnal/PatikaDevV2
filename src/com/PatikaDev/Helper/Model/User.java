package com.PatikaDev.Helper.Model;

import com.PatikaDev.Helper.Helper.DBConnector;
import com.PatikaDev.Helper.Helper.Helper;
import com.PatikaDev.Helper.View.OperatorGUI;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

public class User {
    static OperatorGUI op;
    private int id;
    private String  name;
    private String username;
    private String pass;
    private String type;
    public User(){

    }

    public User(int id, String name, String username,String pass, String type) {
        this.id = id;
        this.name = name;
        this.username = username;
        this.type = type;
        this.pass = pass;
    }

    public static ArrayList<User> getList(){
        User obj;
        ArrayList<User> userList = new ArrayList<>();
        String query = "SELECT * FROM user";

        try {
            Statement st = DBConnector.getInstance().createStatement();
            ResultSet rs = st.executeQuery(query);
            while (rs.next()){
                obj = new User();
                obj.setId(rs.getInt("id")); // veritabanındaki her bir kulllanıcı icin obje uretiriz ve bunları array liste aktarırız
                obj.setName(rs.getString("name"));
                obj.setUsername(rs.getString("username"));
                obj.setPass(rs.getString("pass"));
                obj.setType(rs.getString("type"));
                userList.add(obj);
            }
            st.close();
            rs.close();

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return userList;
    }


    public static ArrayList<User> getListOnlyEducator(){
        User obj;
        ArrayList<User> userList = new ArrayList<>();
        String query = "SELECT * FROM user WHERE type = 'educator'";

        try {
            Statement st = DBConnector.getInstance().createStatement();
            ResultSet rs = st.executeQuery(query);
            while (rs.next()){
                obj = new User();
                obj.setId(rs.getInt("id")); // veritabanındaki her bir kulllanıcı icin obje uretiriz ve bunları array liste aktarırız
                obj.setName(rs.getString("name"));
                obj.setUsername(rs.getString("username"));
                obj.setPass(rs.getString("pass"));
                obj.setType(rs.getString("type"));
                userList.add(obj);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return userList;
    }

    public static boolean addRegister(String name,String username,String pass){
        String type = "student";
        String query = "INSERT INTO user (name,username,pass,type) VALUES (?,?,?,?)";
        User user = getFetchByUname(username);
        if (user != null){
            Helper.showMsg("Bu Kullanıcı Adı Kullanılmaktadır");
            return false;
        }

        try {
            PreparedStatement pr = DBConnector.getInstance().prepareStatement(query);
            pr.setString(1,name);
            pr.setString(2,username);
            pr.setString(3,pass);
            pr.setString(4,type);

            int respons = pr.executeUpdate();
            if (respons == -1){
                Helper.showMsg("error");
                return false;
            }
            return respons != -1;


        } catch (SQLException e) {
            e.printStackTrace();
        }


        return true;
    }




    public static boolean add(String name,String username,String pass,String type){
        String query = "INSERT INTO user (name,username,pass,type) VALUES (?,?,?,?)";
        User user = getFetchByUname(username);
        if (user != null){
            Helper.showMsg("Bu Kullanıcı Adı Kullanılmaktadır ");
            return false;
        }

        try {
            PreparedStatement pr = DBConnector.getInstance().prepareStatement(query);
            pr.setString(1,name);
            pr.setString(2,username);
            pr.setString(3,pass);
            pr.setString(4,type);

            int respons = pr.executeUpdate();
            if (respons == -1 ){
                Helper.showMsg("error");
            }
            return respons != -1;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return true;
    }



    public static boolean delete(int id){
        String query = "DELETE FROM user WHERE id = ?";
        ArrayList<Course> courseList= Course.getListByUser(id);
        for (Course c : courseList){
            Course.delete(c.getId());
        }

        try {
            PreparedStatement pr = DBConnector.getInstance().prepareStatement(query);
            pr.setInt(1,id);
            return pr.executeUpdate() != -1;

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return true;
    }


    public static boolean update(int id,String name,String username,String pass,String type){
        String query = "UPDATE user SET name = ?,username =?,pass = ?,type = ? WHERE id = ?";

        User findUser = User.getFetchByUname(username); // bu kullanıcı adına sahip baskası var mı kontrol eder varsa yapmaz
        if (findUser != null && findUser.getId() != id){ // o kullanıcı adına sahip baskası varsa ve o kullanıcı adına sahip olan kisinin id si bizim verdiğimiz id ye eşit değilse bu hatayı ver
            Helper.showMsg("Bu kullanıcı adı daha önceden alındı");
            return false;
        }

        try {
            PreparedStatement pr = DBConnector.getInstance().prepareStatement(query);
            pr.setString(1,name);
            pr.setString(2,username);
            pr.setString(3,pass);
            pr.setString(4,type);
            pr.setInt(5,id);
            return pr.executeUpdate() != -1;

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return true;
    }

    public static String searchQuery(String name,String username,String type){
        String query = "SELECT * FROM user WHERE username LIKE '%{{username}}%' AND name LIKE '%{{name}}%'";

        query = query.replace("{{username}}",username);
        query = query.replace("{{name}}",name);
        if (!type.isEmpty()){
            query += " AND type = '{{type}}'";
            query = query.replace("{{type}}",type);
        }
        return query;
    }

    public static ArrayList<User> searchUserList(String query){
        ArrayList<User> userList = new ArrayList<>();
        User obj;

        try {
            Statement st = DBConnector.getInstance().createStatement();
            ResultSet rs = st.executeQuery(query);

            while (rs.next()){
                obj = new User();
                obj.setId(rs.getInt("id"));
                obj.setName(rs.getString("name"));
                obj.setUsername(rs.getString("username"));
                obj.setPass(rs.getString("pass"));
                obj.setPass(rs.getString("type"));
                userList.add(obj);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return userList;
    }




    public static User getFetchByUname(String username){
        User obj = null;
        String query = "SELECT * FROM user WHERE username = ?";

        try {
            PreparedStatement pr= DBConnector.getInstance().prepareStatement(query);
            pr.setString(1,username);
            ResultSet rs = pr.executeQuery();
            if (rs.next()){
                obj = new User();
                obj.setId(rs.getInt("id"));
                obj.setName(rs.getString("name"));
                obj.setUsername(rs.getString("username"));
                obj.setPass(rs.getString("pass"));
                obj.setType(rs.getString("type"));

            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return obj;
    }

    public static User getFetch(String username,String pass){
        User obj = null;
        String query = "SELECT * FROM user WHERE username = ? AND pass = ?";

        try {
            PreparedStatement pr= DBConnector.getInstance().prepareStatement(query);
            pr.setString(1,username);
            pr.setString(2,pass);
            ResultSet rs = pr.executeQuery();
            if (rs.next()){
                switch (rs.getString("type")){
                    case "operator":
                        obj = new Operator();
                        break;
                    default:
                        obj = new User();

                }

                obj.setId(rs.getInt("id"));
                obj.setName(rs.getString("name"));
                obj.setUsername(rs.getString("username"));
                obj.setPass(rs.getString("pass"));
                obj.setType(rs.getString("type"));

            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return obj;
    }





    public static User getFetch(int id){
        String query = "SELECT * FROM user WHERE id = ?";
        User obj = null;

        try {
            PreparedStatement pr = DBConnector.getInstance().prepareStatement(query);
            pr.setInt(1,id);
            ResultSet rs = pr.executeQuery();;

            if (rs.next()){
                obj = new User();
                obj.setId(rs.getInt("id"));
                obj.setName(rs.getString("name"));
                obj.setUsername(rs.getString("username"));
                obj.setPass(rs.getString("pass"));
                obj.setType(rs.getString("type"));
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

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPass() {
        return pass;
    }

    public void setPass(String pass) {
        this.pass = pass;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }




}
