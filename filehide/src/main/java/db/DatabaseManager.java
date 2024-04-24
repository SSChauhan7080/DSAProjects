package db;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Reader;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import model.data;

public class DatabaseManager {
    // JDBC URL, username, and password of MySQL server
    private static final String JDBC_URL = "jdbc:mysql://localhost:3306/dbms";
    private static final String USERNAME = "root";
    private static final String PASSWORD = "singh25";

    public boolean userExists(String email) {
        try (Connection conn = DriverManager.getConnection(JDBC_URL, USERNAME, PASSWORD)) {
            String sql = "SELECT * FROM user WHERE email = ?";
            try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
                pstmt.setString(1, email);
                ResultSet rs = pstmt.executeQuery();
                return rs.next();
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public void registerUser(String name,String email, String password) {
        try (Connection conn = DriverManager.getConnection(JDBC_URL, USERNAME, PASSWORD)) {
            String sql = "INSERT INTO user (name,email, password) VALUES (?,?, ?)";
            try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
                pstmt.setString(1, name);
                pstmt.setString(2, email);
                pstmt.setString(3, password);
                pstmt.executeUpdate();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public boolean loginUser(String email, String password) {
        try (Connection conn = DriverManager.getConnection(JDBC_URL, USERNAME, PASSWORD)) {
            String sql = "SELECT * FROM user WHERE email = ? AND password = ?";
            try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
                pstmt.setString(1, email);
                pstmt.setString(2, password);
                ResultSet rs = pstmt.executeQuery();
                return rs.next();
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
    public String Myname(String email)throws SQLException{
      try(  Connection conn = DriverManager.getConnection(JDBC_URL, USERNAME, PASSWORD)){
            String sql="select name from user where email=?";
            try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
                pstmt.setString(1, email);
                ResultSet rs = pstmt.executeQuery();
                String name=rs.getString(1);
                return name;
            }
        } catch (SQLException e) {
                e.printStackTrace();
                return null;
            }
        } 
    
    public static List<data> getAllfiles(String email)throws SQLException{
        Connection conn = DriverManager.getConnection(JDBC_URL, USERNAME, PASSWORD);
            String sql="select * from data where email=?";
            try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
                pstmt.setString(1, email);
                ResultSet rs = pstmt.executeQuery();
                List<data> files=new ArrayList<>();
              while(rs.next()){
                 int id=rs.getInt(1);
                 String name=rs.getString(2);
                 String epath=rs.getString(3);
                 files.add(new data(id, name, epath));
              }
              return files;
            }
    } 
    public static int hideFiles(data file) throws SQLException, IOException{
        Connection conn = DriverManager.getConnection(JDBC_URL, USERNAME, PASSWORD);
        PreparedStatement ps=conn.prepareStatement("insert into data( name, path, email, bin_data) values( ?, ?, ?, ?)");
        ps.setString(1, file.getName());
        ps.setString(2, file.getPath());
        ps.setString(3, file.getEmail());
        File f=new File(file.getPath());
        FileReader fr=new FileReader(f);
        ps.setCharacterStream(4, fr,f.length());
        int ans=ps.executeUpdate();
        fr.close();
        f.delete();
        return ans;
        };

    public static void unhide(int id)throws SQLException, IOException{
        Connection conn = DriverManager.getConnection(JDBC_URL, USERNAME, PASSWORD);
        PreparedStatement ps=conn.prepareStatement("select path, bin_data from data where id = ?");
        ps.setInt(1, id);
        ResultSet rs=ps.executeQuery();
        rs.next();
        String path=rs.getString("path");
        Clob c=rs.getClob("bin_data");
        Reader r=c.getCharacterStream();
        FileWriter fw=new FileWriter(path);
        int i;
        while((i=r.read())!=-1){
              fw.write((char) i);
        }
        fw.close();
        ps=conn.prepareStatement("delete from data where id=?");
        ps.setInt(1, id);
        ps.executeUpdate();
        System.out.println("file Unhide********");
        
    }
        
    

}
