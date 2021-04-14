<%-- 
    Document   : addUserForm
    Created on : 31 mar 2021, 14:10:59
    Author     : karol
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>JSP Page</title>
    </head>
    <body>
        <form method="post" action="UtilServlet">
            <p>
                ID: <input type="number" name="id">
            </p>
            <p>
                NR: <input type="text" name="nr">
            </p>
            <p>
                Tytul: <input type="text" name="tytul">
            </p>
            <p>
                Opis: <input type="text" name="opis">
            </p>
            <p>
                Kwota: <input type="text" name="kwota">
            </p>
            <p>
                Obraz <input type="image" name="obraz">
            </p>
            <p>
                <input type="submit" value="submit">
            </p>
        </form>
    </body>
</html>
