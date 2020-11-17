/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.dht.services;

import com.dht.pojo.Category;
import com.dht.pojo.Choice;
import com.dht.pojo.Question;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Admin
 */
public class QuestionServices {
    public static List<Question> getQuestions(String kw) throws SQLException {
        Connection conn = Utils.getConn();
        String sql = "SELECT * FROM question";
        if (kw != null && !kw.trim().isEmpty())
            sql += " WHERE content like ?";
        
        PreparedStatement stm = conn.prepareStatement(sql);
        if (kw != null && !kw.trim().isEmpty())
            stm.setString(1, String.format("%%%s%%", kw.trim()));
        
        ResultSet rs = stm.executeQuery();
        
        List<Question> questions = new ArrayList<>();
        while (rs.next()) {
            String id = rs.getString("id");
            String content = rs.getString("content");
            int cateId = rs.getInt("category_id");
            Category c = new Category(cateId);
            Question q = new Question(id, content, c);
            
            questions.add(q);
        }
        
        return questions;
    }
    
    public static boolean addQuestion(Question q, List<Choice> choices) {
        Connection conn = Utils.getConn();
        try {
            conn.setAutoCommit(false);
            String sql = "INSERT INTO question(id, content, category_id)"
                    + "VALUES(?, ?, ?)";
            PreparedStatement stm = conn.prepareStatement(sql);
            stm.setString(1, q.getId());
            stm.setString(2, q.getContent());
            stm.setInt(3, q.getCategory().getId());
            
            if (stm.executeUpdate() > 0) {
                for (Choice c: choices) {
                    sql = "INSERT INTO choice(id, content, question_id, is_correct)"
                    + "VALUES(?, ?, ?, ?)";
                    
                    stm = conn.prepareStatement(sql);
                    stm.setString(1, c.getId());
                    stm.setString(2, c.getContent());
                    stm.setString(3, q.getId());
                    stm.setBoolean(4, c.isCorrect());
                    
                    stm.executeUpdate();
                }
                
                conn.commit();
            }
            
            return true;
        } catch (SQLException ex) {
            try {
                conn.rollback();
            } catch (SQLException ex1) {
                Logger.getLogger(QuestionServices.class.getName()).log(Level.SEVERE, null, ex1);
            }
        }
        
        return false;
    }
}
