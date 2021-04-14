<%-- 
    Document   : index
    Created on : 31 mar 2021, 13:15:56
    Author     : karol
--%>

<%@page import="com.mycompany.webapplication3kg.Data.T_Dane"%>
<%@page import="com.mycompany.webapplication3kg.Data.ApplicationData"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>

<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>JSP Page</title>
    </head>
    <body>
        <h1>Autor:Karol Góźdź</h1><br>
        <table border="1">
            <%
            ApplicationData appData=(ApplicationData)getServletContext().getAttribute("appData"); 
            
            //System.out.println(appData.getLength());
            for(T_Dane t_dane:appData.getDane()){
                    //System.out.println(t_dane);
                    
                    out.println("<tr>");
                    out.println("<td>");
                    out.println(t_dane.getId());
                    out.println("</td>");
                    out.println("<td>");
                    out.println(t_dane.getNr());
                    out.println("</td>");
                    out.println("<td>");
                    out.println(t_dane.getTytul());
                    out.println("</td>");
                    out.println("<td>");
                    out.println(t_dane.getOpis());
                    out.println("</td>");
                    out.println("<td>");
                    out.println(t_dane.sGetKwota());
                    out.println("</td>");
                    out.println("<td>");
                    out.println("<img src=\"data:image/png;base64,"+t_dane.getBase64image()+"\"");
                    out.println("</td>");
                    out.println("<td>");
                    out.println("<form method=\"post\" action=\"MyServlet?action=editData&previousId="+ t_dane.getId() +"\">");
                    out.println("<p>");
                    out.println("<input type=\"submit\" value=\"Edytuj\"");
                    out.println("</p>");
                    out.println("</form>");
                    out.println("</td>");
                    out.println("<td>");
                    out.println("<form method=\"post\" action=\"MyServlet?action=deleteData&id="+t_dane.getId()+"\">");
                    out.println("<p>");
                    out.println("<input type=\"submit\" value=\"Usuń\"");
                    out.println("</p>");
                    out.println("</form>");
                    out.println("</td>");
                    out.println("</tr>");
                }
            //out.println(<td>);
            
            %>
            
        </table>
            <%
            String appVer = (String) getServletContext().getAttribute("appVer");
            out.println("Application Version: "+appVer);
            %>
        <form method="post" action="MyServlet?action=addData">
            <p>
                <input type="submit" value="Add Record">
            </p>
        </form>
    </body>
</html>
