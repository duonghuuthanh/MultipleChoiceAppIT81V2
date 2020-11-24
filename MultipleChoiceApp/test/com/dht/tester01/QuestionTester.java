/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.dht.tester01;

import com.dht.pojo.Choice;
import com.dht.pojo.Question;
import com.dht.services.QuestionServices;
import com.dht.services.Utils;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

/**
 *
 * @author Admin
 */
public class QuestionTester {
    private static Connection conn;
    
    @BeforeClass
    public static void setUp() {
        conn = Utils.getConn();
    }
    
    @AfterClass
    public static void tearDown() {
        try {
            conn.close();
        } catch (SQLException ex) {
            Logger.getLogger(QuestionTester.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    @Test
    public void testNoFilters1() {
        try {
            List<Question> kq1 = QuestionServices.getQuestions(null);
            List<Question> kq2 = QuestionServices.getQuestions("");
            
            Assert.assertEquals(kq1.size(), kq2.size());
        } catch (SQLException ex) {
            Logger.getLogger(QuestionTester.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    @Test
    public void testNoFilters2() {
        try {
            List<Question> kq1 = QuestionServices.getQuestions(null);
            List<Question> kq2 = QuestionServices.getQuestions("   ");
            
            Assert.assertEquals(kq1.size(), kq2.size());
        } catch (SQLException ex) {
            Logger.getLogger(QuestionTester.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    @Test
    public void testFilterQuestion() {
        try {
            String kw = " a  ";
            List<Question> q = QuestionServices.getQuestions(kw);
            
            Assert.assertEquals(3, q.size());
            for (Question que: q)
                Assert.assertTrue(que.getContent().contains(kw.trim()));
        } catch (SQLException ex) {
            Logger.getLogger(QuestionTester.class.getName()).log(Level.SEVERE, null, ex);
        }
        
    }
    
    @Test
    public void testDeleteSuccessful() {
        try {
            String questionId = "24877fc3-de59-4acc-81dc-3e7a0c1012df";
            
            boolean kq = QuestionServices.deleteQuestion(questionId);
            Assert.assertTrue(kq);
            
            Question q = QuestionServices.getQuestionById(questionId);
            Assert.assertNull(q);
            
            List<Choice> choices = QuestionServices.getChoicesByQuestionId(questionId);
            Assert.assertEquals(0, choices.size());
        } catch (SQLException ex) {
            Logger.getLogger(QuestionTester.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
