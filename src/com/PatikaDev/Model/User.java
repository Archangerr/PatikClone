package com.PatikaDev.Model;

import com.PatikaDev.Hepler.DBConnecter;
import com.PatikaDev.Hepler.Helper;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

public class User {
    private int id;
    private String name;
    private String uname;
    private String pass;
    private String type;

    public User() {
    }


    public User(int id, String name, String uname, String pass, String type) {
        this.id = id;
        this.name = name;
        this.uname = uname;
        this.pass = pass;
        this.type = type;
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

    public String getUname() {
        return uname;
    }

    public void setUname(String uname) {
        this.uname = uname;
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

    public static ArrayList<User> getList() {
        ArrayList<User> userList = new ArrayList<>();
        String query = "SELECT * FROM user";
        User obj;
        try {
            Statement st = DBConnecter.getInstance().createStatement();
            ResultSet rs = st.executeQuery(query);
            while (rs.next()) {
                obj = new User(rs.getInt("id")
                        , rs.getString("name"), rs.getString("uname"),
                        rs.getString("pass"), rs.getString("type"));
                userList.add(obj);
            }
            rs.close();
            st.close();

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return userList;

    }

    public static boolean add(String name,String uname,String pass,String type){
        String query = "INSERT INTO user (name,uname,pass,type) VALUES (?,?,?,?)";

        User findUser= User.getFetch(uname);
        if(findUser!=null){
            Helper.showMassage("error");
            return false;
        }
        try {
            PreparedStatement pr = DBConnecter.getInstance().prepareStatement(query);
            pr.setString(1,name);
            pr.setString(2,uname);
            pr.setString(3,pass);
            pr.setString(4,type);
            return pr.executeUpdate() !=-1;


        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public static boolean remove(int id){
        String query = "DELETE FROM user WHERE id = ?";
        try {
            PreparedStatement pr =DBConnecter.getInstance().prepareStatement(query);
            pr.setInt(1,id);
            return pr.executeUpdate() !=-1;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }

    public static User getFetch(String uname){
        User obj = null;
        String query = "SELECT * FROM user where uname = ?";

        try {
            PreparedStatement pr = DBConnecter.getInstance().prepareStatement((query));
            pr.setString(1,uname);
            ResultSet rs= pr.executeQuery();

            while(rs.next()){
                obj = new User(rs.getInt("id")
                        , rs.getString("name"), rs.getString("uname"),
                        rs.getString("pass"), rs.getString("type"));
                break;
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return obj;

    }

    public static User getFetch(int id){
        User obj = null;
        String query = "SELECT * FROM user where id = ?";

        try {
            PreparedStatement pr = DBConnecter.getInstance().prepareStatement((query));
            pr.setInt(1,id);
            ResultSet rs= pr.executeQuery();

            while(rs.next()){
                obj = new User(rs.getInt("id")
                        , rs.getString("name"), rs.getString("uname"),
                        rs.getString("pass"), rs.getString("type"));
                break;
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return obj;

    }

    public static boolean update(int id,String name, String uname,String pass, String type) {
        String query = "UPDATE user SET name=?, uname =?,pass=?,type=? WHERE id=?";

        try {
            PreparedStatement pr = DBConnecter.getInstance().prepareStatement(query);
            pr.setString(1,name);
            pr.setString(2,uname);
            pr.setString(3,pass);
            pr.setString(4,type);
            pr.setInt(5,id);
            return pr.executeUpdate() !=-1;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
