package pe.edu.pucp.tel131lab9.dao;

import pe.edu.pucp.tel131lab9.bean.Employee;
import pe.edu.pucp.tel131lab9.bean.Post;

import java.sql.*;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

public class PostDao extends DaoBase{

    public ArrayList<Post> listPosts() {

        ArrayList<Post> posts = new ArrayList<>();

        String sql = "SELECT * FROM post left join employees e on e.employee_id = post.employee_id";

        try (Connection conn = this.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
                Post post = new Post();
                fetchPostData(post, rs);
                posts.add(post);
            }
        } catch (SQLException ex) {
            Logger.getLogger(PostDao.class.getName()).log(Level.SEVERE, null, ex);
        }
        return posts;
    }

    public ArrayList<Post> findPostByName(String name) {

        ArrayList<Post> posts = new ArrayList<>();

        String sql = "SELECT p.post_id,p.title,p.content,e.employee_id,e.first_name,e.last_name,p.datetime\n" +
                "FROM post p\n" +
                "JOIN employees e ON p.employee_id = e.employee_id\n" +
                "WHERE p.title LIKE ? OR\n" +
                "      p.content LIKE ? OR\n" +
                "      e.first_name LIKE ? OR\n" +
                "      e.last_name LIKE ? ;";

        try (Connection conn = this.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, "%" +name+ "%");
            stmt.setString( 2, "%" +name+ "%");
            stmt.setString( 3, "%" +name+ "%");
            stmt.setString( 4, "%" +name+ "%");

            try(ResultSet rs = stmt.executeQuery()){
                while (rs.next()) {
                    Post post = new Post();
                    fetchPostData(post, rs);
                    posts.add(post);
                }
            }

        } catch (SQLException ex) {
            Logger.getLogger(PostDao.class.getName()).log(Level.SEVERE, null, ex);
        }
        return posts;
    }


    public Post getPost(int id) {

        Post post = null;

        String sql = "SELECT * FROM post p left join employees e on p.employee_id = e.employee_id "+
                "where p.post_id = ?";

        try (Connection conn = this.getConnection();
             PreparedStatement statement = conn.prepareStatement(sql)) {

            statement.setInt(1, id);

            try (ResultSet rs = statement.executeQuery()) {

                if (rs.next()) {
                    post = new Post();
                    fetchPostData(post, rs);
                }
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }

        return post;
    }

    public Post savePost(Post post) {

        return post;
    }

    private void fetchPostData(Post post, ResultSet rs) throws SQLException {
        post.setPostId(rs.getInt(1));
        post.setTitle(rs.getString(2));
        post.setContent(rs.getString(3));
        post.setEmployeeId(rs.getInt(4));


        Employee employee = new Employee();
        employee.setEmployeeId(rs.getInt("e.employee_id"));
        employee.setFirstName(rs.getString("e.first_name"));
        employee.setLastName(rs.getString("e.last_name"));
        post.setEmployee(employee);

    }

    public void newPost(Post post) {
        String sql = "INSERT INTO post (title, content, employee_id, datetime) " +
                "VALUES (?,?,?,?)";
        try(Connection connection = this.getConnection();
            PreparedStatement psmt = connection.prepareStatement(sql)) {

            psmt.setString(1, post.getTitle());
            psmt.setString(2, post.getContent());
            psmt.setInt(3, post.getEmployeeId());
            psmt.setTimestamp(4, post.getDatetime());

            psmt.executeUpdate();

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

}
