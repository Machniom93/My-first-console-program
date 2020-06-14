package pl.coderslab;

import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.math.NumberUtils;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.Scanner;

public class TaskManager {
    static String[][] tasks = new String[3][];

    public static void main(String[] args) {
        readTheDataAndPutItIntoArrayOfArraysTasks();

        while (true) {
            System.out.println("Please select an option: \nadd\nremove\nlist\n" + ConsoleColors.BLUE + "exit" + ConsoleColors.RESET);
            Scanner scann = new Scanner(System.in);
            String temporary = scann.nextLine().toLowerCase();
            switch (temporary) {
                case "add":
                    addingNewTaskToList();
                    break;
                case "remove":
                    removingTaskFromList();
                    break;
                case "list":
                    showsListOfTasks();
                    break;
                case "exit":
                    savingDataFromArrayToFile();
                    break;
                default:
                    System.out.println("Please select a correct option.");
            }
            if (temporary.equals("exit")) {
                System.out.println(ConsoleColors.RED + "Bye, bye.");
                break;
            }
        }
    }

    public static void readTheDataAndPutItIntoArrayOfArraysTasks() {
        File file = new File("src/pl/coderslab/tasks.csv");
        long numberOfLinesInFile = 0;
        try {
            numberOfLinesInFile = Files.lines(Paths.get("src/pl/coderslab/tasks.csv")).count();
        } catch (IOException e) {
            System.out.println(e);
        }
        tasks = new String[Integer.parseInt(String.valueOf(numberOfLinesInFile))][];
        int counter = 0;
        try {
            Scanner scann = new Scanner(file);
            while (scann.hasNextLine()) {
                String tmp = scann.nextLine();
                String[] arrayTmp = tmp.split(", ");
                tasks[counter] = arrayTmp;
                counter ++;
            }
        } catch (FileNotFoundException e) {
            System.out.println(e);
        }
    }

    public static void showsListOfTasks() {
        int counter = 0;
        for (int i = 0; i < tasks.length; i++) {
            System.out.print(counter+" : ");
            for (int j = 0; j < tasks[i].length; j++) {
                System.out.print(tasks[i][j] + " ");
            }
            System.out.println();
            counter ++;
        }
        System.out.println();
    }

    public static void addingNewTaskToList() {
        Scanner scann = new Scanner(System.in);
        System.out.println("Please add task description");
        String taskDescription = scann.nextLine();
        System.out.println("Please add task due date");
        String taskDueDate = scann.nextLine();
        System.out.println("Is your task important?: " + ConsoleColors.RED + "true/false" + ConsoleColors.RESET);
        String isImportant = scann.nextLine();
        String joinedNewTask = String.join(", ",taskDescription,taskDueDate,isImportant);
        String[] arrayOfTheTask = joinedNewTask.split(", ");
        tasks = Arrays.copyOf(tasks,tasks.length+1);
        tasks[tasks.length-1] = arrayOfTheTask;
        System.out.println();
    }

    public static void removingTaskFromList() {
        Scanner scann = new Scanner(System.in);
        System.out.println("Please select number to remove.");
        while (true) {
            String numberToRemove = scann.nextLine();
            if (NumberUtils.isParsable(numberToRemove) && Integer.parseInt(numberToRemove) >= 0) {
                int tmp = Integer.parseInt(numberToRemove);
                try {
                    if (tmp >= tasks.length) {
                        throw new NullPointerException("Value must be less than " + tasks.length);
                    }
                } catch (NullPointerException e) {
                    System.out.println(e);
                    continue;
                }
                tasks = ArrayUtils.remove(tasks, tmp);
                System.out.println("Value was successfully deleted.");
                System.out.println();
                break;
            } else {
                System.out.println("Incorrect argument passed. Please give number greater or equal 0");
            }
        }
    }

    public static void savingDataFromArrayToFile() {
        try {
            try (PrintWriter pw = new PrintWriter("src/pl/coderslab/tasks.csv")) {
                for (int i = 0; i < tasks.length; i++) {
                    for (int j = 0; j < tasks[i].length; j++) {
                        if (j != tasks[i].length - 1) {
                            pw.append(tasks[i][j] + ", ");
                        } else {
                            pw.append(tasks[i][j]);
                        }
                    }
                    pw.println();
                }
            }
        } catch (FileNotFoundException e) {
            System.out.println(e);
        }
    }

}
