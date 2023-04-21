package com.app.controller;

import com.app.DAO.AccountDao;
import com.app.utility.ConsoleColors;

import java.util.Scanner;

public class Menu {
    static AccountDao accountDao = new AccountDao();
    public static void startMenu(Scanner input) {
        String choice;
        String welcome = "\033[3mSTUDENT GRADE BOOK\033[0m";
        String register = "\033[3m1.  REGISTER\033[0m";
        String login = "\033[3m2.  LOGIN\033[0m";
        String exit = "\033[3m3.  EXIT\033[0m";


        System.out.println(ConsoleColors.BLUE_BOLD_BRIGHT +
                "         ___/__/__/__/__/__/___\n" +
                "        /                     /|\n" +
                "       / " + ConsoleColors.BLUE_BOLD_BRIGHT + welcome + "  " +
                ConsoleColors.BLUE_BOLD_BRIGHT +
                "//\n" +
                "      /                     //\n" +
                "     /   " + ConsoleColors.BLUE_BOLD_BRIGHT + register + "      " +
                ConsoleColors.BLUE_BOLD_BRIGHT +
                "//\n" +
                "    /   " + ConsoleColors.BLUE_BOLD_BRIGHT + login + "         " +
                ConsoleColors.BLUE_BOLD_BRIGHT + "//\n" +
                "   /   " + ConsoleColors.BLUE_BOLD_BRIGHT + exit + "          " +
                ConsoleColors.BLUE_BOLD_BRIGHT + "//\n" +
                "  /                     //\n" +
                " /_____________________//\n");

        System.out.println(ConsoleColors.WHITE_BOLD_BRIGHT + "Please make a selection");

        choice = input.nextLine();

        switch (choice) {
            case "1" -> Menu.registerMenu(input);
            case "2" -> AccountManipulation.login(input);
            case "3" -> {
                System.out.println(ConsoleColors.BLUE_BOLD_BRIGHT + "\nCome back soon!");
                input.close();
                System.exit(0);
            }
            default -> {
                System.out.println(ConsoleColors.RED_BOLD_BRIGHT + "Please enter a valid input");
                startMenu(input);
            }
        }
    }

    private static void registerMenu(Scanner input) {
        String choice;
        String welcome = "\033[3mREGISTER AS:\033[0m";
        String teacherRegister = "\033[3m1. TEACHER\033[0m";
        String studentRegister = "\033[3m2. STUDENT\033[0m";
        String back = "\033[3m3.  BACK\033[0m";

        System.out.println(ConsoleColors.BLUE_BOLD_BRIGHT +
                "         ___/__/__/__/__/__/___\n" +
                "        /                     /|\n" +
                "       /    " + ConsoleColors.BLUE_BOLD_BRIGHT + welcome + "     " +
                ConsoleColors.BLUE_BOLD_BRIGHT +
                "//|\n" +
                "      /                     // |\n" +
                "     /    " + ConsoleColors.BLUE_BOLD_BRIGHT + teacherRegister + "       " +
                ConsoleColors.BLUE_BOLD_BRIGHT +
                "//  |\n" +
                "    /    " + ConsoleColors.BLUE_BOLD_BRIGHT + studentRegister + "       " +
                ConsoleColors.BLUE_BOLD_BRIGHT + "//   |\n" +
                "   /    " + ConsoleColors.BLUE_BOLD_BRIGHT + back + "         " +
                ConsoleColors.BLUE_BOLD_BRIGHT + "//    |\n" +
                "  /                     //_____|\n" +
                " /_____________________//\n");

        System.out.println(ConsoleColors.WHITE_BOLD_BRIGHT + "Please make a selection");

        choice = input.nextLine();

        switch (choice) {
            case "1" -> AccountManipulation.teacherRegister(input);
            case "2" -> AccountManipulation.studentRegister(input);
            case "3" -> startMenu(input);
            default -> {
                System.out.println(ConsoleColors.RED_BOLD_BRIGHT + "Please enter a valid input");
                registerMenu(input);
            }
        }
    }

    public static void teacherMenu(Scanner input) {
        String choice;
        String viewClasses = "\033[3m1. VIEW CLASSES\033[0m";
        String addClass = "\033[3m2. ADD CLASS\033[0m";
        String logout = "\033[3m3.  LOGOUT\033[0m";

        System.out.println(ConsoleColors.BLUE_BOLD_BRIGHT +
                "         ___/__/__/__/__/__/___\n" +
                "        /                     /|\n" +
                "       /                     //|\n" +
                "      /   " + ConsoleColors.BLUE_BOLD_BRIGHT + viewClasses + "   " +
                ConsoleColors.BLUE_BOLD_BRIGHT +
                "// |\n" +
                "     /   " + ConsoleColors.BLUE_BOLD_BRIGHT + addClass + "      " +
                ConsoleColors.BLUE_BOLD_BRIGHT + "//  |\n" +
                "    /   " + ConsoleColors.BLUE_BOLD_BRIGHT + logout + "        " +
                ConsoleColors.BLUE_BOLD_BRIGHT + "//   |\n" +
                "   /                     //____|\n" +
                "  /_____________________//\n");

        System.out.println(ConsoleColors.WHITE_BOLD_BRIGHT + "Please make a selection");

        choice = input.nextLine();

        switch (choice) {
            case "1" -> ClassController.viewTeacherClasses(AccountManipulation.activeAccount, input);
            case "2" -> ClassController.addClass(AccountManipulation.activeAccount, input);
            case "3" -> startMenu(input);
            default -> {
                System.out.println(ConsoleColors.RED_BOLD_BRIGHT + "Please enter a valid input");
                teacherMenu(input);
            }
        }
    }

    public static void studentMenu(Scanner input) {
        String choice;
        String viewClasses = "\033[3m1. VIEW CLASSES\033[0m";
        String editInfo = "\033[3m2. EDIT USERNAME\033[0m";
        String logout = "\033[3m3.  LOGOUT\033[0m";

        System.out.println(ConsoleColors.BLUE_BOLD_BRIGHT +
                "         ___/__/__/__/__/__/___\n" +
                "        /                     /|\n" +
                "       /                     //|\n" +
                "      /   " + ConsoleColors.BLUE_BOLD_BRIGHT + viewClasses + "   " +
                ConsoleColors.BLUE_BOLD_BRIGHT +
                "// |\n" +
                "     /   " + ConsoleColors.BLUE_BOLD_BRIGHT + editInfo + "  " +
                ConsoleColors.BLUE_BOLD_BRIGHT + "//  |\n" +
                "    /   " + ConsoleColors.BLUE_BOLD_BRIGHT + logout + "        " +
                ConsoleColors.BLUE_BOLD_BRIGHT + "//   |\n" +
                "   /                     //____|\n" +
                "  /_____________________//\n");

        choice = input.nextLine();

        switch (choice) {
            case "1" -> ClassController.viewStudentClasses(AccountManipulation.activeAccount, input);
            case "2" -> StudentController.editInfo(AccountManipulation.activeAccount, input);
            case "3" -> startMenu(input);
            default -> {
                System.out.println(ConsoleColors.RED_BOLD_BRIGHT + "Please enter a valid input");
                studentMenu(input);
            }
        }
    }
}
