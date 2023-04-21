package com.app.controller;

import com.app.DAO.AccountDao;
import com.app.DAO.ClassDao;
import com.app.DAO.StudentDao;
import com.app.model.Student;
import com.app.utility.ConsoleColors;

import java.text.DecimalFormat;
import java.util.*;

public class ClassController {

    static ClassDao classDao = new ClassDao();
    static AccountDao accountDao = new AccountDao();
    static StudentDao studentDao = new StudentDao();
    static DecimalFormat df = new DecimalFormat("#.##");
    public static void viewStudentClasses(String activeAccount, Scanner input) {
        HashMap<String, Double> classList = classDao.getStudentClasses(studentDao.getStudentIdByUsername(activeAccount));

        System.out.println(ConsoleColors.BLUE_BOLD_BRIGHT + "      ||     ||     ||     ||     ||     ||     ||     ||     ");
        System.out.println(ConsoleColors.BLUE_BOLD_BRIGHT + "+=====||=====||=====||=====||=====||=====||=====||=====||=====+");
        System.out.println(ConsoleColors.BLUE_BOLD_BRIGHT + "|                         CLASS LIST                          |");
        System.out.println(ConsoleColors.BLUE_BOLD_BRIGHT + "+=============================================================+");
        for (Map.Entry<String, Double> entry : classList.entrySet()) {
            System.out.printf("| %-22s | %-34s |\n", entry.getKey(), entry.getValue());
        }
        System.out.println(ConsoleColors.BLUE_BOLD_BRIGHT + "+=============================================================+");
        System.out.println();

        System.out.println(ConsoleColors.WHITE_BOLD_BRIGHT + "Press any keys to go back");

        if(input.nextLine() != null) {
            Menu.studentMenu(input);
        }

    }

    public static void viewTeacherClasses(String activeAccount, Scanner input) {
        String choice;

        ArrayList<String> classList = classDao.getTeacherClasses(accountDao.getTeacherId(activeAccount));

        System.out.println(ConsoleColors.BLUE_BOLD_BRIGHT + "      ||     ||     ||     ||     ||     ||     ||     ||     ");
        System.out.println(ConsoleColors.BLUE_BOLD_BRIGHT + "+=====||=====||=====||=====||=====||=====||=====||=====||=====+");
        System.out.println(ConsoleColors.BLUE_BOLD_BRIGHT + "|                         CLASS LIST                          |");
        System.out.println(ConsoleColors.BLUE_BOLD_BRIGHT + "+=============================================================+");
        for (int i = 0; i < classList.size(); i++) {
            System.out.printf("| %-2s | %-54s |\n", (i + 1), classList.get(i).trim());
        }
        System.out.println(ConsoleColors.BLUE_BOLD_BRIGHT + "+=============================================================+");
        System.out.println();

        System.out.println(ConsoleColors.WHITE_BOLD_BRIGHT + "Please pick the class you would like to view or enter B to go back");

        choice = input.nextLine();

        try {
            if (choice.equalsIgnoreCase("B")) {
                Menu.teacherMenu(input);
            } else {
                int classId = classDao.getClassId(classList.get(Integer.parseInt(choice) - 1));

                ArrayList<Student> studentList = studentDao.getStudents(classId);

                viewClass(classId, input, studentList);
            }
        } catch(Exception e) {
            System.out.println(ConsoleColors.RED_BOLD_BRIGHT + "Please enter a valid input!!");
            viewTeacherClasses(activeAccount, input);
        }
    }

    public static void viewClass(int classId, Scanner input, ArrayList<Student> studentList) {
        String choice;

        System.out.println(ConsoleColors.BLUE_BOLD_BRIGHT + "      ||     ||     ||     ||     ||     ||     ||     ||     ");
        System.out.println(ConsoleColors.BLUE_BOLD_BRIGHT + "+=====||=====||=====||=====||=====||=====||=====||=====||=====+");
        System.out.println(ConsoleColors.BLUE_BOLD_BRIGHT + "|                         CLASS LIST                          |");
        System.out.println(ConsoleColors.BLUE_BOLD_BRIGHT + "+=============================================================+");
        System.out.println(ConsoleColors.BLUE_BOLD_BRIGHT + "|                                                             |");
        System.out.println(ConsoleColors.BLUE_BOLD_BRIGHT + "|     \033[4mSTUDENT NAME\033[0m                                  " +
                ConsoleColors.BLUE_BOLD_BRIGHT + "\033[4mGRADE\033[0m" +
                ConsoleColors.BLUE_BOLD_BRIGHT + "     |");
        System.out.println(ConsoleColors.BLUE_BOLD_BRIGHT + "|                                                             |");
        for (int i = 0; i < studentList.size(); i++) {
            Student student = studentList.get(i);
            System.out.printf("|  %-2s %-45s %-9s |\n", (i + 1), student.getName(), student.getGrade());
        }
        System.out.println(ConsoleColors.BLUE_BOLD_BRIGHT + "+=============================================================+");
        System.out.println();

        System.out.println(ConsoleColors.WHITE_BOLD_BRIGHT + "If you would like to update a students grade or remove a student please enter their corresponding number");
        System.out.println(ConsoleColors.WHITE_BOLD_BRIGHT + "Other options:");
        System.out.println(ConsoleColors.WHITE_BOLD_BRIGHT + "B: Go Back to Class List");
        System.out.println(ConsoleColors.WHITE_BOLD_BRIGHT + "A: Get the average grade for the current class");
        System.out.println(ConsoleColors.WHITE_BOLD_BRIGHT + "M: Get the median grade for the current class");
        System.out.println(ConsoleColors.WHITE_BOLD_BRIGHT + "SA: Sort Students alphabetically");
        System.out.println(ConsoleColors.WHITE_BOLD_BRIGHT + "SG: Sort Students by their grade");
        System.out.println(ConsoleColors.WHITE_BOLD_BRIGHT + "Add: Add student to the class");

        choice = input.nextLine();

        try {
            if (choice.equalsIgnoreCase("B")) {
                viewTeacherClasses(AccountManipulation.activeAccount, input);
            } else if (choice.equalsIgnoreCase("a")) {
                System.out.println(ConsoleColors.GREEN_BOLD_BRIGHT + "The average grade for this class is " + classDao.getAverage(classId));

                System.out.println(ConsoleColors.WHITE_BOLD_BRIGHT + "Press any key to go back");

                if(input.nextLine() != null) {
                    viewClass(classId, input, studentList);
                }
            } else if (choice.equalsIgnoreCase("m")) {
                double median = getMedian(classDao.getGrades(classId));

                System.out.println(ConsoleColors.GREEN_BOLD_BRIGHT + "The median grade for this class is " + median);

                System.out.println(ConsoleColors.WHITE_BOLD_BRIGHT + "Press any key to go back");

                if(input.nextLine() != null) {
                    viewClass(classId, input, studentList);
                }
            } else if (choice.equalsIgnoreCase("sa")) {
                studentList.sort(Comparator.comparing(Student::getName));

                viewClass(classId, input, studentList);
            } else if (choice.equalsIgnoreCase("sg")) {
                studentList.sort(Comparator.comparing(Student::getGrade).reversed());

                viewClass(classId, input, studentList);
            } else if (choice.equalsIgnoreCase("add")) {
                System.out.println(ConsoleColors.WHITE_BOLD_BRIGHT + "Please enter the name of the student");

                String studentName = input.nextLine();

                try {
                    if (classDao.addStudentToClass(classId, studentName)) {
                        System.out.println(ConsoleColors.GREEN_BOLD_BRIGHT + "Student added successfully");

                        ArrayList<Student> newStudentList = studentDao.getStudents(classId);

                        viewClass(classId, input, newStudentList);
                    } else {
                        System.out.println(ConsoleColors.RED_BOLD_BRIGHT + "Something went wrong when adding the student");
                    }
                } catch (Exception e) {
                    System.out.println(ConsoleColors.RED_BOLD_BRIGHT + "Something went wrong when adding the student");
                }
            } else if (studentList.get(Integer.parseInt(choice) - 1) != null) {
                int studentId = studentDao.getStudentId(studentList.get(Integer.parseInt(choice) - 1).getName());

                StudentController.viewStudent(studentId, input, classId, studentList);
            } else {
                System.out.println(ConsoleColors.RED_BOLD_BRIGHT + "Please enter a valid input!");
            }

        } catch(Exception e) {
            System.out.println(ConsoleColors.RED_BOLD_BRIGHT + "Please enter a valid input!!!");
            viewClass(classId, input, studentList);
        }

    }

    public static void addClass(String activeAccount, Scanner input) {
        System.out.println(ConsoleColors.WHITE_BOLD_BRIGHT + "Please enter the name of the class you would like to add");

        String newClassName = input.nextLine();

        try {
            if(classDao.addClass(newClassName, accountDao.getTeacherId(AccountManipulation.activeAccount))) {
                System.out.println(ConsoleColors.GREEN_BOLD_BRIGHT + "Class successfully added");

                viewTeacherClasses(activeAccount, input);
            } else {
                System.out.println(ConsoleColors.RED_BOLD_BRIGHT + "Something went wrong when adding the class");
            }
        } catch (Exception e) {
            System.out.println(ConsoleColors.RED_BOLD_BRIGHT + "Something went wrong when adding the class");
        }

    }

    public static Double getMedian(ArrayList<Double> grades) {
        Double output;
        Collections.sort(grades);

        if (grades.size() % 2 == 0) {
            double grade1 = grades.get((grades.size() / 2) - 1);
            double grade2 = grades.get((grades.size() / 2));

            output =  ((grade1 + grade2) / 2);

            return Double.valueOf(df.format(output));
        } else {
            output =  grades.get((int) ((grades.size() / 2) - 0.5));

            return Double.valueOf(df.format(output));
        }
    }
}
