/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.dht.services;

import com.dht.pojo.Category;
import com.dht.pojo.Question;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Admin
 */
public class CategoryServices {
    private static Connection conn = Utils.getConn();
    
    public static Category getCategoryById(int id) throws SQLException {
        String sql = "SELECT * FROM category WHERE id=?";
        
        PreparedStatement stm = conn.prepareStatement(sql);
        stm.setInt(1, id);
        
        ResultSet rs = stm.executeQuery();
        while (rs.next())
            return new Category(rs.getInt("id"), rs.getString("name"));
        
        return null;
    }
    
    public static  List<Category> getCategories() throws SQLException {
        
        Statement stm = conn.createStatement();
        ResultSet rs = stm.executeQuery("SELECT * FROM category");
        
        List<Category> kq = new ArrayList<>();
        while (rs.next()) {
            int id = rs.getInt("id");
            String name = rs.getString("name");
            
            Category c = new Category(id, name);
            kq.add(c);
        }
        return kq;
    }
}
