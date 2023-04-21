package com.app.controller;

import com.app.DAO.AccountDao;
import com.app.DAO.ClassDao;
import com.app.DAO.StudentDao;
import com.app.model.Student;
import com.app.utility.ConsoleColors;

import java.util.ArrayList;
import java.util.Scanner;

public class StudentController {

    static AccountDao accountDao = new AccountDao();
    static ClassDao classDao = new ClassDao();
    static StudentDao studentDao = new StudentDao();
    public static void editInfo(String activeAccount, Scanner input) {
        String choice;

        System.out.println(ConsoleColors.BLUE_BOLD_BRIGHT + "Your current username is " + activeAccount);

        System.out.println(ConsoleColors.WHITE_BOLD_BRIGHT + "Please enter your desired username:");

        choice = input.nextLine();

        if (accountDao.getStudentUsernames().contains(choice)) {
            System.out.println(ConsoleColors.RED_BOLD_BRIGHT + "Username is already taken. Please try again");
            editInfo(activeAccount, input);
        }

        try {
            if (accountDao.updateStudentUsername(choice, activeAccount)) {
                System.out.println(ConsoleColors.GREEN_BOLD_BRIGHT + "Username successfully updated!");
                Menu.studentMenu(input);
            } else {
                System.out.println(ConsoleColors.RED_BOLD_BRIGHT + "Something when wrong when updating the username");
            }
        } catch (Exception e) {
            System.out.println(ConsoleColors.RED_BOLD_BRIGHT + "Something when wrong when updating the username");
        }
    }

    public static void viewStudent(int studentId, Scanner input, int classId, ArrayList<Student> studentList) {
        String choice;

        System.out.println(ConsoleColors.WHITE_BOLD_BRIGHT + "Would you like to:");
        System.out.println(ConsoleColors.WHITE_BOLD_BRIGHT + "1. Update the students grade");
        System.out.println(ConsoleColors.WHITE_BOLD_BRIGHT + "2. Remove the student from the class");
        System.out.println(ConsoleColors.WHITE_BOLD_BRIGHT + "3. Go back");

        choice = input.nextLine();

        switch (choice) {
            case "1" -> updateStudentGrade(studentId, classId, input, studentList);
            case "2" -> {
                if (classDao.removeStudentFromClass(studentId, classId)) {
                    System.out.println(ConsoleColors.GREEN_BOLD_BRIGHT + "Student successfully removed");

                    ArrayList<Student> newStudentList = studentDao.getStudents(classId);

                    ClassController.viewClass(classId, input, newStudentList);
                } else {
                    System.out.println(ConsoleColors.RED_BOLD_BRIGHT + "Something went wrong when removing the student");
                }
            }
            case "3" -> ClassController.viewClass(classId, input, studentList);
        }

    }

    private static void updateStudentGrade(int studentId, int classId, Scanner input, ArrayList<Student> studentList) {
        String choice;

        System.out.println(ConsoleColors.BLUE_BOLD_BRIGHT + "The student currently has a " + studentDao.getStudentGrade(studentId, classId) + " in the class");

        System.out.println(ConsoleColors.WHITE_BOLD_BRIGHT + "Please enter the name of the assignment you would like to upload");

        input.nextLine();

        System.out.println(ConsoleColors.WHITE_BOLD_BRIGHT + "Please enter the grade on the assignment");

        choice = input.nextLine();

        try{
            double grade = Double.parseDouble(choice);

            grade = (grade + studentDao.getStudentGrade(studentId, classId)) / 2;

            if (classDao.updateGrade(grade, studentId, classId)) {
                System.out.println(ConsoleColors.GREEN_BOLD_BRIGHT + "Grade updated successfully!");

                System.out.println(ConsoleColors.BLUE_BOLD_BRIGHT + "The student now has a " + studentDao.getStudentGrade(studentId, classId) + " in the class");

                ArrayList<Student> newStudentList = studentDao.getStudents(classId);

                viewStudent(studentId, input, classId, newStudentList);
            }

        } catch (Exception e) {
            System.out.println(ConsoleColors.RED_BOLD_BRIGHT + "Something went wrong when adding the assignment please try again");
            updateStudentGrade(studentId, classId, input, studentList);
        }

    }
}
