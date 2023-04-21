package com.app.DAO;

import com.app.JDBC.ConnectionManager;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class AccountDao {

    private static final Connection conn = ConnectionManager.getConnection();

    public boolean checkTeacherCredentials(String username, String password) {
        try {
            PreparedStatement st = conn.prepareStatement("SELECT * FROM teacher WHERE username = ? AND password = ?");
            st.setString(1, username);
            st.setString(2, password);

            ResultSet rs = st.executeQuery();

            if(rs.next()) {
                rs.close();
                st.close();

                return true;
            }

        } catch (Exception e) {

            e.printStackTrace();
        }

        return false;
    }

    public boolean checkStudentCredentials(String username, String password) {
        try {
            PreparedStatement st = conn.prepareStatement("SELECT * FROM student WHERE username = ? AND password = ?");
            st.setString(1, username);
            st.setString(2, password);

            ResultSet rs = st.executeQuery();

            if(rs.next()) {
                rs.close();
                st.close();

                return true;
            }

        } catch (Exception e) {

            e.printStackTrace();
        }

        return false;
    }

    public boolean createTeacherAccount(String username, String password) {

        try {
            PreparedStatement st = conn.prepareStatement("INSERT INTO teacher(username, password) values (?, ?)");
            st.setString(1, username);
            st.setString(2, password);

            st.execute();

            st.close();

            return true;

        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean createStudentAccount(String name) {

        try {
            PreparedStatement st = conn.prepareStatement("INSERT INTO student(name) values (?)");
            st.setString(1, name);

            st.execute();

            st.close();

            return true;

        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public ArrayList<String> getTeacherUsernames() {
        ArrayList<String> usernameList = new ArrayList<>();
        try {
            PreparedStatement st = conn.prepareStatement("select username from teacher");

            ResultSet rs = st.executeQuery();
            
            while (rs.next()) {
                usernameList.add(rs.getString(1));
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return usernameList;
    }

    public ArrayList<String> getStudentUsernames() {
        ArrayList<String> usernameList = new ArrayList<>();
        try {
            PreparedStatement st = conn.prepareStatement("select username from student");

            ResultSet rs = st.executeQuery();

            while (rs.next()) {
                usernameList.add(rs.getString(1));
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return usernameList;
    }

    public ArrayList<String> getStudentNames() {
        ArrayList<String> usernameList = new ArrayList<>();
        try {
            PreparedStatement st = conn.prepareStatement("select name from student");

            ResultSet rs = st.executeQuery();

            while (rs.next()) {
                usernameList.add(rs.getString(1));
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return usernameList;
    }

    public String getStudentUsername(String studentName) {
        String username = null;
        try {
            PreparedStatement st = conn.prepareStatement("select username from student where name = ?");
            st.setString(1, studentName);

            ResultSet rs = st.executeQuery();

            while (rs.next()) {
                username = rs.getString("username");
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return username;
    }

    public boolean updateStudentPassword(String password, String studentName) {
        try {
            PreparedStatement ps = conn.prepareStatement("update student set password = ? where name = ?");
            ps.setString(1, password);
            ps.setString(2, studentName);

            int rowsAffected = ps.executeUpdate();

            if (rowsAffected > 0) {
                return true;
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return false;

    }

    public int getTeacherId(String teacherUsername) {
        int id = -1;
        try {
            PreparedStatement st = conn.prepareStatement("select teacher_id from teacher where username = ?");
            st.setString(1, teacherUsername);

            ResultSet rs = st.executeQuery();

            while (rs.next()) {
                id = rs.getInt("teacher_id");
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return id;
    }

    public boolean updateStudentUsername(String username, String activeAccount) {
        try {
            PreparedStatement ps = conn.prepareStatement("update student set username = ? where username = ?");
            ps.setString(1, username);
            ps.setString(2, activeAccount);

            int rowsAffected = ps.executeUpdate();

            if (rowsAffected > 0) {
                return true;
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return false;
    }
}
