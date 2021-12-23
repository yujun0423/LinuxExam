package com.atguigu.jdbc.controller;

import com.atguigu.jdbc.entity.student;
import com.atguigu.jdbc.util.jdbcUtil;
import com.atguigu.jdbc.util.redisUtil;
import redis.clients.jedis.Jedis;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

@WebServlet("/select/student")
public class SelectServletStudent extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        Connection jdbcConnection = jdbcUtil.getJdbcConnection();
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        Jedis jedis = redisUtil.getConnection();
        String sql = "select * from student where studentId=?";
        PrintWriter writer = resp.getWriter();
        System.out.println(jedis);
            try {
                Integer id = Integer.parseInt(req.getParameter("id"));
                preparedStatement = jdbcConnection.prepareStatement(sql);
                preparedStatement.setInt(1, id);
                resultSet = preparedStatement.executeQuery();
                while (resultSet.next()) {
                    Integer studentId = Integer.parseInt(resultSet.getString("studentId"));
                    String studentName = resultSet.getString("studentName");
                    Integer studentAge = Integer.parseInt(resultSet.getString("studentAge"));
                    student st = new student(studentId, studentName, studentAge);
                    writer.println(st);
                }

            } catch (SQLException e) {
                e.printStackTrace();
            } finally {
                jdbcUtil.release(jdbcConnection, preparedStatement, resultSet);
            }
        }
}
