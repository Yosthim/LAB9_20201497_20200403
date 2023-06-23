package pe.edu.pucp.tel131lab9.controller;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import pe.edu.pucp.tel131lab9.bean.Employee;
import pe.edu.pucp.tel131lab9.bean.Post;
import pe.edu.pucp.tel131lab9.dao.CommentDao;
import pe.edu.pucp.tel131lab9.dao.PostDao;

import java.io.IOException;
import java.sql.Timestamp;

@WebServlet(name = "PostServlet", urlPatterns = {"/PostServlet"})
public class PostServlet extends HttpServlet {
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setCharacterEncoding("UTF-8");
        String action = req.getParameter("action") != null ? req.getParameter("action") : "";
        PostDao postDao = new PostDao();

        if (action.equals("new")) {
            Post newPost = new Post();
            Timestamp datetime = new Timestamp(System.currentTimeMillis());
            Employee empleado = (Employee) req.getSession().getAttribute("userSession");
            newPost.setTitle(req.getParameter("titulo").trim());
            newPost.setContent(req.getParameter("comentario").trim());
            newPost.setEmployeeId(empleado.getEmployeeId());
            newPost.setDatetime(datetime);

            postDao.newPost(newPost);
            resp.sendRedirect(req.getContextPath() + "/");
        }
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        RequestDispatcher view;
        String action = req.getParameter("action") != null ? req.getParameter("action") : "";

        if (action.equals("new")) {
            view = req.getRequestDispatcher("post/newPost.jsp");
            view.forward(req, resp);
        }
        else if (action.equals("view")) {
            String id = req.getParameter("id") != null ? req.getParameter("id") : "";
            System.out.println(id);
            PostDao postDao = new PostDao();
            CommentDao commentDao = new CommentDao();
            req.setAttribute("post", postDao.getPost(Integer.parseInt(id)));
            req.setAttribute("comments", commentDao.listCommentsByPostId(Integer.parseInt(id)));
            view = req.getRequestDispatcher("post/viewPost.jsp");
            view.forward(req, resp);
        }

    }
}
