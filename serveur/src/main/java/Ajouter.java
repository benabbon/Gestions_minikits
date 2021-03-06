/*
* To change this license header, choose License Headers in Project Properties.
* To change this template file, choose Tools | Templates
* and open the template in the editor.
*/

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 *
 * @author mouad
 */
@WebServlet(urlPatterns = {"/Ajouter"})
public class Ajouter extends HttpServlet {
    
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
        
        HttpSession sess = request.getSession();
        if (sess == null){
			response.sendRedirect("invalid.html");
			return;
		}    
        else{
            if(sess.getAttribute("utilisateur")==null){
				response.sendRedirect("invalid.html");
				return;
			}
            else{
                if (!((String)sess.getAttribute("utilisateur")).equals("admin")|| request.getParameter("nb_mini_kits")==null){
					response.sendRedirect("erreur.jsp?cause=\"page non disponible\"");
					return;
				}        
            }
        }
        try{
            Class.forName("com.mysql.jdbc.Driver");
            Connection con;
            con=DriverManager.getConnection("jdbc:mysql://localhost:3306/fablabDB","fablab","fablab");
            int nb_mini_kits = (Integer.parseInt(request.getParameter("nb_mini_kits")));
            if(request.getParameter("login").isEmpty() ||request.getParameter("password").isEmpty()){
                response.sendRedirect("erreur.jsp?cause=\"Les champs login et password sont obligatoires\"");
                con.close();
                return;
            }
            Statement st = con.createStatement();
            ResultSet rs =  st.executeQuery("select * from admins where admin =\""+request.getParameter("login")+"\"");
            if(rs.next()){
                con.close();
                response.sendRedirect("erreur.jsp?cause=\"Ce login existe déjà\"");
                return;
            }
			String log = request.getParameter("login");
            String pass = request.getParameter("password");
			String hash = BCrypt.hashpw(pass,BCrypt.gensalt());
            Statement stm = con.createStatement();
            stm.executeUpdate("insert into admins values (\""+log+"\" ,\""+hash+"\", null,3,3 )");
			
            for(int i = 1; i<= nb_mini_kits;i++){
                if(request.getParameter("mini_kit"+i) != null && request.getParameter("mini_kit"+i).equals("on")){
                    stm = con.createStatement();
                    String query = "insert into droits values ("+i+",\""+log+"\")";
                    stm.executeUpdate(query);
                    
                }
            }
            
            response.sendRedirect("administrateur.jsp");
            
            
            con.close();
        }catch(SQLException ex){
            response.sendRedirect("erreur.jsp");
        }catch(ClassNotFoundException ex){
            response.sendRedirect("erreur.jsp");
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
