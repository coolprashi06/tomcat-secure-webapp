package com.prashast.servlets;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.security.Principal;

/**
 * Created by prashastsaxena on 12/29/16.
 */
public class SecuredServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {

        Principal principal = req.getUserPrincipal();
        /*res.setContentType("text/html");
        PrintWriter out = res.getWriter();
        out.println("Authenticated User Id :: "+principal.getName());*/

        res.setHeader("Cache-Control", "no-cache, no-store, must-revalidate"); // HTTP 1.1.
        res.setHeader("Pragma", "no-cache"); // HTTP 1.0.
        res.setDateHeader("Expires", 0);


        HttpSession session = req.getSession(true);
        session.setMaxInactiveInterval(10); //in seconds
        session.setAttribute("user",principal.getName());
        session.setAttribute("createdTime",System.currentTimeMillis());

        req.getRequestDispatcher("/index.jsp").forward(req, res);

    }

}
