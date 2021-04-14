package com.mycompany.webapplication3kg;

import com.mycompany.webapplication3kg.Data.ApplicationData;
import com.mycompany.webapplication3kg.Data.ApplicationLogic;
import com.mycompany.webapplication3kg.Data.T_Dane;
//import com.mycompany.webapplication3kg.Data.User;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.Enumeration;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author karol
 */
public class MyServlet extends HttpServlet {

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
        
        //if(getServletContext().getAttribute("action")=null){
        
        if (request.getParameter("action") != null) {
            if (request.getParameter("action").equals("addData")) {
                response.sendRedirect(request.getContextPath() + "/addDataForm.jsp"); //zmien ba addDataForm
            }
            if(request.getParameter("action").equals("editData")){
                response.sendRedirect(request.getContextPath()+"/editDataForm.jsp?previousId="+request.getParameter("previousId"));
            }
            if(request.getParameter("action").equals("deleteData?id="+request.getParameter("id"))){
                processDeleteT_Dane(request, response);
            }

        } else {
            //System.out.println(request.getContextPath()+"/webapp/index.jsp");
            response.sendRedirect(request.getContextPath() + "/index.jsp");
        }

        try ( PrintWriter out = response.getWriter()) {
            /* TODO output your page here. You may use following sample code. */
            out.println("<!DOCTYPE html>");
            out.println("<html>");
            out.println("<head>");
            out.println("<title>Servlet MyServlet</title>");
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Servlet MyServlet at " + request.getContextPath() + "</h1>");
            out.println("</body>");
            out.println("</html>");
        }
    }
    protected void processDeleteT_Dane(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        request.getRequestDispatcher("UtilServlet").forward(request, response);
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
    public ApplicationData getApplicatioData(){
    ResultSet wynikZapytania = null;
        ResultSetMetaData metaData = null;
        int IloscKolumn = 0;
        int IloscWierszyMSSQL = 0;
        try {
            wynikZapytania = ApplicationLogic.getConnectionFromContext("mssql").createStatement(java.sql.ResultSet.TYPE_SCROLL_INSENSITIVE, java.sql.ResultSet.CONCUR_READ_ONLY).executeQuery("select * from t_dane");
            IloscWierszyMSSQL = wynikZapytania.getRow();
            IloscKolumn = wynikZapytania.getMetaData().getColumnCount();
        } catch (SQLException ex) {
            String sBlad = ex.toString();
            System.out.println(sBlad);
        }
        try {
            metaData = wynikZapytania.getMetaData();
            
            for (int i = 1; i <= IloscKolumn; i++) {
                /* to jest do sprawdzenia co znajduje sie w tabeli
                System.out.println(metaData.getColumnName(i));
                System.out.println(metaData.getColumnTypeName(i));
                System.out.println(metaData.getColumnDisplaySize(i));
                System.out.println("--");
                */
            }
            
        } catch (SQLException ex) {
            Logger.getLogger(MyServlet.class.getName()).log(Level.SEVERE, null, ex);
        }
        ApplicationData appData = new ApplicationData();
        try {
            while (wynikZapytania.next()) {
                appData.addDane(new T_Dane(
                        wynikZapytania.getInt("id"),
                        wynikZapytania.getString("nr"),
                        wynikZapytania.getString("tytul"),
                        wynikZapytania.getString("opis"),
                        wynikZapytania.getBigDecimal("kwota"),
                        wynikZapytania.getBytes("obraz")
                ));
            }
        } catch (SQLException ex) {
            Logger.getLogger(MyServlet.class.getName()).log(Level.SEVERE, null, ex);
        }
        return appData;
    }
    @Override
    public void init() {
        //Przyklad zapytania ze zrodla stowroznego w kontekscie servera

        
        ApplicationData appData = getApplicatioData();
        

        ////Przyk�ad utworzenia zmiennych globalnych (w kontek�cie aplikacji )
        
        getServletContext().setAttribute("appVer", "20210409");
        getServletContext().setAttribute("appData", appData);
        //inicjalizacja głównego servletu  
        //inicjalizacja jdbc/activemq/inne

        // nawiazanie polaczenia na poziomie aplikacj Web w meotodzie init servletu
        //i zapis do zmiennej instancyjnej databaseConnection serwletu 
        //databaseConnection=ApplicationLogic.connectDatabase(); 
        //Przykład utworzenia zmiannych globalnych (w instancji klasy)
        //java.util.Calendar kalendarzData= java.util.Calendar.getInstance();
        //java.util.Date dataUruchomienia=kalendarzData.getTime();
        //sDataUruchomienia=dataUruchomienia.toString();
    }

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
