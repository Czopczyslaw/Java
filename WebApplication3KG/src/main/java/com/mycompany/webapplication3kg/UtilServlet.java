/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.webapplication3kg;

import com.mycompany.webapplication3kg.Data.ApplicationData;
import com.mycompany.webapplication3kg.Data.ApplicationLogic;
import com.mycompany.webapplication3kg.Data.T_Dane;
import com.mycompany.webapplication3kg.Data.User;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.imageio.ImageIO;
import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileItemIterator;
import org.apache.commons.fileupload.FileItemStream;
import org.apache.commons.fileupload.FileUploadException;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.apache.commons.fileupload.util.Streams;
import org.apache.commons.io.FilenameUtils;
import org.apache.commons.io.IOUtils;

/**
 *
 * @author karol
 */
public class UtilServlet extends HttpServlet {

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
        /*
        ApplicationData appData = (ApplicationData) getServletContext().getAttribute("appData");
        System.out.println(request.getParameter("id"));
        appData.addDane(new T_Dane(                
                Integer.parseInt(request.getParameter("id")),
                request.getParameter("nr"),
                request.getParameter("tytul"),
                request.getParameter("opis"),
                new BigDecimal(request.getParameter("kwota")),
                request.getParameter("obraz").getBytes()));
         */
        if (request.getParameter("action") != null) {
            if (request.getParameter("action").equals("Save")) {
                processSaveT_Dane(request, response);
            }

        } else {

            response.sendRedirect(request.getContextPath() + "/index.jsp");
        }
    }

    protected void processSaveT_Dane(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String previousId = "";
        ApplicationData appData = (ApplicationData) getServletContext().getAttribute("appData");
        String updateTable = "UPDATE t_dane SET id=?,nr=?,tytul=?,opis=?,kwota=?,obraz=? WHERE id=?";
        Connection con = null;
        if (appData != null) {

            try {
                ServletFileUpload upload = new ServletFileUpload();
                FileItemIterator iterator = upload.getItemIterator(request);
                byte[] img = new byte[0];

                T_Dane bufor = new T_Dane();

                while (iterator.hasNext()) {
                    FileItemStream item = iterator.next();
                    String name = item.getFieldName();
                    if (name.equals("id")) {
                        String itemString = Streams.asString(item.openStream());
                        bufor.setId(itemString);
                    }
                    if (name.equals("nr")) {
                        String itemString = Streams.asString(item.openStream());
                        bufor.setNr(itemString);
                    }
                    if (name.equals("tytul")) {
                        String itemString = Streams.asString(item.openStream());
                        bufor.setTytul(itemString);
                    }
                    if (name.equals("opis")) {
                        String itemString = Streams.asString(item.openStream());
                        bufor.setOpis(itemString);
                    }
                    if (name.equals("kwota")) {
                        String itemString = Streams.asString(item.openStream());
                        bufor.setKwota(itemString);
                    }
                    if (name.equals("previousId")) {
                        String itemString = Streams.asString(item.openStream());
                        previousId = itemString;
                    }
                    //if(item.getFieldName()=="obraz")isImg = item.getInputStream();
                    if (item.getFieldName().equals("obraz")) {
                        try {
                            BufferedImage imgb = ImageIO.read(item.openStream());
                            ByteArrayOutputStream baos = new ByteArrayOutputStream();
                            ImageIO.write(imgb, "jpeg", baos);
                            img = baos.toByteArray();
                        } catch (IOException E) {
                            img = null;

                        }
                    }
                }

                System.out.println(previousId);

                System.out.println(img.length);
                System.out.println(bufor.toString());

                T_Dane dana = appData.findId(Integer.parseInt(previousId));
                if (dana != null) {
                    if (img == null) {
                        img = dana.getObraz();  //jesli obrazka nie  ma, wez stary obrazek
                    }
                    bufor.setObraz(img);
                }
                System.out.println("/---------------\\" + bufor.toString());
                try {
                    con = ApplicationLogic.getConnectionFromContext("mssql");
                } catch (SQLException ex) {
                    String sBlad = ex.toString();
                    System.out.println(sBlad);
                }
                try ( PreparedStatement updateT_Dane = con.prepareStatement(updateTable);) {
                    con.setAutoCommit(false);
                    //System.out.println("--------------------"+bufor.getId());
                    //System.out.println("##########################"+previousId);
                    updateT_Dane.setLong(1, Long.valueOf(bufor.getId()));
                    updateT_Dane.setString(2, bufor.getNr());
                    updateT_Dane.setString(3, bufor.getTytul());
                    updateT_Dane.setString(4, bufor.getOpis());
                    updateT_Dane.setBigDecimal(5, bufor.getKwota());
                    updateT_Dane.setBytes(6, bufor.getObraz());
                    updateT_Dane.setLong(7, Long.parseLong(previousId));

                    System.out.println(updateT_Dane.toString());

                    updateT_Dane.executeUpdate();
                    con.commit();

                } catch (SQLException e) {
                    if (con != null) {
                        try {
                            System.err.print("Rolling back commit");
                            con.rollback();
                        } catch (SQLException excep) {
                            String ex = excep.toString();
                            System.out.println("utilservlet error " + ex);
                        }
                    }
                }

                response.sendRedirect(request.getContextPath() + "/index.jsp");
            } catch (Exception E) {
                System.out.println(E);
            }
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
