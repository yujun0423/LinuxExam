package com.atguigu.jdbc.controller;

import com.atguigu.jdbc.util.jdbcUtil;

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

@WebServlet("/update/student")
public class UpdateServletStudent extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String sql="update student set studentName=? where studentId=?";
        PreparedStatement preparedStatement=null;
        ResultSet resultSet=null;
        Connection jdbcConnection = jdbcUtil.getJdbcConnection();
        String name = req.getParameter("name");
        Integer id = Integer.parseInt(req.getParameter("id"));
        PrintWriter writer = resp.getWriter();
        try {
            preparedStatement = jdbcConnection.prepareStatement(sql);
            preparedStatement.setString(1,name);
            preparedStatement.setInt(2,id);
            boolean execute = preparedStatement.execute();
            if (execute){
                writer.println("error");
            }else {
                writer.println("success");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }finally {
            jdbcUtil.release(jdbcConnection,preparedStatement,resultSet);
        }
    }
}
