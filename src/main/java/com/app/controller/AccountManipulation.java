package com.app.controller;

import com.app.DAO.AccountDao;
import com.app.utility.ConsoleColors;

import java.util.ArrayList;
import java.util.Scanner;

public class AccountManipulation {
    static AccountDao accountDao = new AccountDao();
    static String activeAccount;
    public static void teacherRegister(Scanner input) {
        String username;
        String password;

        System.out.println(ConsoleColors.YELLOW_BOLD_BRIGHT + "Welcome to the Teacher Account Creation Page!");

        while (true) {
            ArrayList<String> usernameList = accountDao.getTeacherUsernames();

            System.out.println(ConsoleColors.WHITE_BOLD_BRIGHT + "Please enter your desired username:");

            String tempUsername = input.nextLine();

            if (usernameList.stream().noneMatch(string -> string.equals(tempUsername))) {
                username = tempUsername;

                System.out.println(ConsoleColors.WHITE_BOLD_BRIGHT + "Please enter your desired password:");

                password = input.nextLine();

                System.out.println(ConsoleColors.WHITE_BOLD_BRIGHT + "Please confirm your password:");
                if (password.equals(input.nextLine())) {
                    if (accountDao.createTeacherAccount(username, password)) {
                        System.out.println(ConsoleColors.BLUE_BOLD_BRIGHT + "Account creation successful, please log in");
                        Menu.startMenu(input);
                    }
                } else {
                    System.out.println(ConsoleColors.RED_BOLD_BRIGHT + "Passwords did not match, please try again.");
                }
            } else {
                System.out.println(ConsoleColors.RED_BOLD_BRIGHT + "Teacher with that username already exists, please try again.");
                teacherRegister(input);
            }
        }
    }

    public static void login(Scanner input) {
        String username;
        String password;

        System.out.println(ConsoleColors.YELLOW_BOLD_BRIGHT + "Welcome to the Login Page!");
        System.out.println(ConsoleColors.WHITE_BOLD_BRIGHT + "Please enter your username:\n");

        username = input.nextLine();

        System.out.println(ConsoleColors.WHITE_BOLD_BRIGHT + "Please enter your password:\n");

        password = input.nextLine();

        if (accountDao.checkTeacherCredentials(username, password)) {
            activeAccount = username;
            System.out.println(ConsoleColors.BLUE_BOLD_BRIGHT + "Log in successful!\n");
            Menu.teacherMenu(input);
        } else if (accountDao.checkStudentCredentials(username, password)) {
            activeAccount = username;
            System.out.println(ConsoleColors.BLUE_BOLD_BRIGHT + "Log in successful!\n");
            Menu.studentMenu(input);
        } else {
            System.out.println(ConsoleColors.RED_BOLD_BRIGHT + "Log in unsuccessful, please try again\n");
            login(input);
        }
    }

    public static void studentRegister(Scanner input) {
        String studentName;
        String password;

        System.out.println(ConsoleColors.YELLOW_BOLD_BRIGHT + "Welcome to the Student Account Creation Page!");

        while (true) {
            ArrayList<String> nameList = accountDao.getStudentNames();

            System.out.println(ConsoleColors.WHITE_BOLD_BRIGHT + "Please enter your full name:");

            String tempStudentName = input.nextLine();

            if (nameList.stream().anyMatch(string -> string.equals(tempStudentName))) {
                studentName = tempStudentName;

                System.out.println(ConsoleColors.GREEN_BOLD_BRIGHT + "Student found!");
                System.out.println(ConsoleColors.GREEN_BOLD_BRIGHT + "Your automatically generated username is " + accountDao.getStudentUsername(studentName));
                System.out.println(ConsoleColors.GREEN_BOLD_BRIGHT + "If desired, you can change this later");

                System.out.println(ConsoleColors.WHITE_BOLD_BRIGHT + "Please enter your desired password:");

                password = input.nextLine();

                System.out.println(ConsoleColors.WHITE_BOLD_BRIGHT + "Please confirm your password:");
                if (password.equals(input.nextLine())) {
                    if (accountDao.updateStudentPassword(password, studentName)) {
                        System.out.println(ConsoleColors.BLUE_BOLD_BRIGHT + "Account creation successful, please log in");
                        Menu.startMenu(input);
                    }
                } else {
                    System.out.println(ConsoleColors.RED_BOLD_BRIGHT + "Passwords did not match, please try again.");
                }
            } else {
                System.out.println(ConsoleColors.RED_BOLD_BRIGHT + "No Student with that name found, please try again.");
            }
        }
    }
}
