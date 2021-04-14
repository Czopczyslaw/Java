<%-- 
    Document   : editDataForm
    Created on : 10 kwi 2021, 12:39:34
    Author     : karol
--%>

<%@page import="com.mycompany.webapplication3kg.UtilServlet"%>
<%@page import="java.util.logging.Level"%>
<%@page import="java.util.logging.Logger"%>
<%@page import="com.mycompany.webapplication3kg.Data.ApplicationData"%>
<%@page import="com.mycompany.webapplication3kg.Data.T_Dane"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>JSP Page</title>
    </head>
    <body>
        
          <%
                out.println("<form method=\"post\" action=\"UtilServlet?action=Save\" enctype=\"multipart/form-data\"");
                ApplicationData appData = (ApplicationData) getServletContext().getAttribute("appData");
                T_Dane dane = null;
                //System.out.println(request.getParameter("previousId"));
                if (appData != null) {
                    try {
                        dane = appData.findId(Integer.parseInt(request.getParameter("previousId")));
                    } catch (Exception e) {
                        Logger.getLogger(UtilServlet.class.getName()).log(Level.SEVERE, null, e);
                    }
                }
                if (dane == null) {
                    response.sendRedirect(request.getContextPath() + "/index.jsp");
                } else {
                    System.out.println(dane);
                    out.println("<p>");
                    out.println("ID: <input type=\"number\" name=\"id\" value=\"" + dane.getId() + "\">");
                    out.println("</p>");
                    out.println("<p>");
                    out.println("Nr: <input type=\"text\" name=\"nr\" value=\"" + dane.getNr() + "\">");
                    out.println("</p>");
                    out.println("<p>");
                    out.println("Tytul: <input type=\"text\" name=\"tytul\" value=\"" + dane.getTytul() + "\">");
                    out.println("</p>");
                    out.println("<p>");
                    out.println("Opis <input type=\"text\" name=\"opis\" value=\"" + dane.getOpis() + "\">");
                    out.println("</p>");
                    out.println("<p>");
                    out.println("Kwota: <input type=\"text\" name=\"kwota\" value=\"" + dane.sGetKwota() + "\">");
                    out.println("</p>");
                    out.println("<p>");
                    out.println("<label for=\"obraz\">Wybierz Obraz:</label>");
                    out.println("Obraz: <input type=\"file\" id=\"obraz\" name=\"obraz\">");
                    out.println("</p>");
                    out.println("<p>");
                    out.println("<input type=\"hidden\" name=\"previousId\" value=\"" + request.getParameter("previousId") + "\">");
                    out.println("</p>");
                }
            %>

            <p>
                <input type="submit" value="Save">
            </p>

        </form>
    </body>
</html>
