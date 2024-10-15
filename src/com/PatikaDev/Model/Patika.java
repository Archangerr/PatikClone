package com.PatikaDev.Model;

import com.PatikaDev.Hepler.DBConnecter;
import com.PatikaDev.Hepler.Helper;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

public class Patika {
    private int id;
    private String name;

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

    public Patika(int id, String name) {
        this.id = id;
        this.name = name;
    }

    public  static ArrayList<Patika> getList(){
        ArrayList<Patika> patikaList = new ArrayList<>();
        Patika obj;

        Statement st = null;
        try {
            st = DBConnecter.getInstance().createStatement();

            ResultSet rs = st.executeQuery("SELECT * FROM patika");
            while (rs.next()) {
                obj = new Patika(rs.getInt("id"), rs.getString("name"));
                patikaList.add(obj);
            }
        }catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return patikaList;
    }

    public static boolean Add(String name){
        if(getFetch(name)!=null){
            Helper.showMassage("error");
            return false;
        }
        else{

            String query="INSERT INTO patika (name) VALUE (?)";
            try {
                PreparedStatement pr = DBConnecter.getInstance().prepareStatement(query);
                pr.setString(1,name);
                return pr.executeUpdate() !=-1;

            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }

    }

    public static Patika getFetch(String name){
        Patika obj = null;
        String query = "SELECT * FROM patika WHERE name = ?";
        try {
            PreparedStatement pr = DBConnecter.getInstance().prepareStatement(query);
            pr.setString(1,name);
            ResultSet rs = pr.executeQuery();
            if(rs.next()){
                obj= new Patika(rs.getInt("ID"),rs.getString("name"));
                return obj;
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return null;

    }

    public static boolean remove(int id){
        String query = "DELETE FROM patika WHERE id = ?";
        try {
            PreparedStatement pr =DBConnecter.getInstance().prepareStatement(query);
            pr.setInt(1,id);
            return pr.executeUpdate() !=-1;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }
    
    
}
