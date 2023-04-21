package com.app;

//import com.app.controller.Menu;
import com.app.controller.Menu;
import com.app.utility.ConsoleColors;

import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner input = new Scanner(System.in);

        Menu.startMenu(input);
    }
}