/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.camp.servlet;

import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.sql.*;

/**
 *
 * @author Akirabear
 */
public class challenge61 extends HttpServlet {

    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code>
     * methods.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        try(PrintWriter out = response.getWriter()){
            
            out.println("<!DOCTYPE html>");
            out.println("<html>");
            out.println("<head>");
            out.println("<title>結果</title>");
            out.println("</head>");
            out.println("<body>");

            Connection db_con = null;
            PreparedStatement db_st = null;
            ResultSet db_data = null;
        
            try{
                String adress = "jdbc:mysql://localhost:8889/Challenge_db";
                String userID = "Akirabear";
                String pass = "QoiwEa4Z";
                
                //mymqlに接続
                Class.forName("com.mysql.jdbc.Driver").newInstance();
                db_con = DriverManager.getConnection(adress, userID, pass);
                
                String sql = "update profiles set name = ?, tel = ?, age = ?, birthday = ? where profilesID = ?";
                //入力データを取得
                db_st = db_con.prepareStatement(sql);
                
                request.setCharacterEncoding("UTF-8");
                String id = request.getParameter("txtId");
                String name = request.getParameter("txtName");
                String tel = request.getParameter("telNum");
                String age = request.getParameter("numAge");
                String birthday = request.getParameter("dateBirth");
                db_st.setString(5,id);
                db_st.setString(1,name);
                db_st.setString(2,tel);
                db_st.setString(3,age);
                db_st.setString(4,birthday);
                
                int num = db_st.executeUpdate();
                
                sql = "select * from profiles order by profilesID";
                db_st = db_con.prepareStatement(sql);
                db_data = db_st.executeQuery();
                //記述
                while(db_data.next()){
                    out.println("ユーザー番号："+ db_data.getInt("profilesID") +"<br>");
                    out.println("名前：" + db_data.getString("name") +"<br>");
                    out.println("電話番号："+ db_data.getString("tel") +"<br>");
                    out.println("年齢："+ db_data.getInt("age") +"<br>");
                    out.println("生年月日："+ db_data.getDate("birthday") +"<br><br>");
                }
                //クローズ
                db_data.close();
                db_st.close();
                db_con.close();
            }catch(SQLException e_sql){
                out.println("接続時にエラーが発生しました："+e_sql.toString());
            }catch(Exception e){
                out.println("接続時にエラーが発生しました："+e.toString());
            }finally{
                if(db_con != null){
                    try {
                        db_con.close();
                    }catch (Exception e_con){
                        out.println(e_con.getMessage());
                    }
                }
            }
        out.println("</body>");
        out.println("</html>");
        }
    }
    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    /**
     * Handles the HTTP <code>GET</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Handles the HTTP <code>POST</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Returns a short description of the servlet.
     *
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

}
