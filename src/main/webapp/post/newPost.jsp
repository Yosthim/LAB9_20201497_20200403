<%@page import="java.util.ArrayList" %>
<%@ page import="pe.edu.pucp.tel131lab9.bean.Post" %>

<%@page contentType="text/html" pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="utf-8">
    <title>Lista empleados</title>
    <jsp:include page="../includes/headCss.jsp"></jsp:include>
</head>
<body>
<div class='container'>
    <jsp:include page="../includes/navbar.jsp">
        <jsp:param name="currentPage" value="newPost"/>
    </jsp:include>
    <div class="row mb-5 mt-4">
        <div class="col-md-7">
            <h1>New Post</h1>
        </div>
    </div>
    <form method="POST" action="<%=request.getContextPath()%>/PostServlet?action=new">
        <div class="form-group mb-3">
            <label class="form-label" for="title">Title</label>
            <input class="form-control" type="text" id="title" name="titulo" placeholder="Insert Title">
        </div>
        <div class="form-group mb-3">
            <label class="form-label" for="comment">Comment</label>
            <input class="form-control" type="text" id="comment" name="comentario" placeholder="Insert Comment">
        </div>
        <div class="row px-5 mt-5 justify-content-center">
            <a class="btn btn-danger mx-2 col-3" href="<%=request.getContextPath()%>/">Cancelar</a>
            <button class="btn btn-primary mx-2 col-3" type="submit">Save</button>
        </div>
    </form>

    <jsp:include page="../includes/footer.jsp"/>
</div>
</body>
</html>
