package com.app.DAO;

import com.app.JDBC.ConnectionManager;
import com.app.model.Student;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;

public class StudentDao {
    private static final Connection conn = ConnectionManager.getConnection();
    public ArrayList<Student> getStudents(int classId) {
        ArrayList<Student> studentList = new ArrayList<>();
        try {
            PreparedStatement st = conn.prepareStatement("select s.name, sc.grade from student_class sc join student s on sc.student_id = s.student_id where sc.class_id = ?");
            st.setInt(1, classId);

            ResultSet rs = st.executeQuery();

            while (rs.next()) {
                String name = rs.getString(1);
                double grade = rs.getDouble(2);

                studentList.add(new Student(name, grade));
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return studentList;
    }

    public int getStudentId(String studentName) {
        int id = -1;
        try {
            PreparedStatement st = conn.prepareStatement("select student_id from student where name = ?");
            st.setString(1, studentName);

            ResultSet rs = st.executeQuery();

            while (rs.next()) {
                id = rs.getInt("student_id");
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return id;
    }

    public double getStudentGrade(int studentId, int classId) {
        double id = -1.0;
        try {
            PreparedStatement st = conn.prepareStatement("select grade from student_class where student_id = ? and class_id = ?");
            st.setInt(1, studentId);
            st.setInt(2, classId);

            ResultSet rs = st.executeQuery();

            while (rs.next()) {
                id = rs.getInt("grade");
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return id;
    }

    public int getStudentIdByUsername(String studentUsername) {
        int id = -1;
        try {
            PreparedStatement st = conn.prepareStatement("select student_id from student where username = ?");
            st.setString(1, studentUsername);

            ResultSet rs = st.executeQuery();

            while (rs.next()) {
                id = rs.getInt("student_id");
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return id;
    }
}
