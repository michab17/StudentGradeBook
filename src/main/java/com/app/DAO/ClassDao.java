package com.app.DAO;

import com.app.JDBC.ConnectionManager;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.HashMap;

public class ClassDao {
    private static final Connection conn = ConnectionManager.getConnection();
    StudentDao studentDao = new StudentDao();
    DecimalFormat df = new DecimalFormat("#.##");
    public ArrayList<String> getTeacherClasses(int teacherId) {
        ArrayList<String> classList = new ArrayList<>();
        try {
            PreparedStatement st = conn.prepareStatement("select name from class where teacher_id = ? ");
            st.setInt(1, teacherId);

            ResultSet rs = st.executeQuery();

            while (rs.next()) {
                classList.add(rs.getString(1));
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return classList;
    }

    public HashMap<String, Double> getStudentClasses(int studentId) {
        HashMap<String, Double> classList= new HashMap<>();
        try {
            PreparedStatement st = conn.prepareStatement("select c.name, sc.grade from class c join student_class sc on c.class_id = sc.class_id where sc.student_id = ?");
            st.setInt(1, studentId);

            ResultSet rs = st.executeQuery();

            while (rs.next()) {
                classList.put(rs.getString("c.name"), rs.getDouble("sc.grade"));
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return classList;
    }

    public int getClassId(String className) {
        int id = -1;
        try {
            PreparedStatement st = conn.prepareStatement("select class_id from class where name = ?");
            st.setString(1, className);

            ResultSet rs = st.executeQuery();

            while (rs.next()) {
                id = rs.getInt("class_id");
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return id;
    }

    public Double getAverage(int classId) {
        double averageGrade = -1.0;
        try {
            PreparedStatement st = conn.prepareStatement("select avg(grade) from student_class where class_id = ?");
            st.setInt(1, classId);

            ResultSet rs = st.executeQuery();

            while (rs.next()) {
                averageGrade = rs.getDouble(1);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return Double.valueOf(df.format(averageGrade));

    }

    public ArrayList<Double> getGrades(int classId) {
        ArrayList<Double> grades = new ArrayList<>();

        try {
            PreparedStatement st = conn.prepareStatement("select grade from student_class where class_id = ? ");
            st.setInt(1, classId);

            ResultSet rs = st.executeQuery();

            while (rs.next()) {
                grades.add(rs.getDouble(1));
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return grades;

    }

    public boolean addStudentToClass(int classId, String studentName) {

        try {
            PreparedStatement ps = conn.prepareStatement("insert into student(name) values(?)");
            ps.setString(1, studentName);

            ps.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }

        try {
            PreparedStatement ps = conn.prepareStatement("insert into student_class(class_id, student_id, grade) values(?, ?, 100)");
            ps.setInt(1, classId);
            ps.setInt(2, studentDao.getStudentId(studentName));

            int rowsAffected = ps.executeUpdate();

            if (rowsAffected > 0) {
                return true;
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return false;

    }

    public boolean addClass(String newClassName, int teacher_id) {
        try {
            PreparedStatement ps = conn.prepareStatement("insert into class(name, teacher_id) values(?, ?)");
            ps.setString(1, newClassName);
            ps.setInt(2, teacher_id);

            int rowsAffected = ps.executeUpdate();

            if (rowsAffected > 0) {
                return true;
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return false;
    }

    public boolean removeStudentFromClass(int studentId, int classId) {
        try {
            PreparedStatement ps = conn.prepareStatement("delete from student_class where student_id = ? and class_id = ?");
            ps.setInt(1, studentId);
            ps.setInt(2, classId);

            int rowsAffected = ps.executeUpdate();

            if (rowsAffected > 0) {
                return true;
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return false;
    }

    public boolean updateGrade(double newGrade, int studentId, int classId) {
        try {
            PreparedStatement ps = conn.prepareStatement("update student_class set grade = ? where student_id = ? and class_id = ?");
            ps.setDouble(1, newGrade);
            ps.setInt(2, studentId);
            ps.setInt(3, classId);

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
