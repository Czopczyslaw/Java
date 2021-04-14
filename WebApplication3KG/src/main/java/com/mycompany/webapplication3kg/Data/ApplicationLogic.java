/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.webapplication3kg.Data;

import java.sql.SQLException;

/**
 *
 * @author karol
 */
public class ApplicationLogic {

    public static java.sql.Connection getConnectionFromContext(String serwerType) throws SQLException {
        //przyklad pobierania polaczenia z bazą z puli polaczen zdefiniowanych
        //w pliku konfiguracyjnym context.xml na serwerze Tomcat
        try {
            //ustalanie kontekstu Java Namin Directory Interface
            javax.naming.Context initContext = new javax.naming.InitialContext();
            javax.naming.Context envContext = (javax.naming.Context) initContext.lookup("java:/comp/env");

            javax.sql.DataSource ds_mssql = (javax.sql.DataSource) envContext.lookup("jdbc/bazaTestowaMSSQL");
            java.sql.Connection connection;
            if (serwerType.equals("mssql")) {
                connection = ds_mssql.getConnection();
                return connection;
            }
            return null;
        } catch (Exception ex) {
            System.out.println(ex);
            throw new SQLException("Nie pobrano połączenia z kontekstu");
        }
    }

}
