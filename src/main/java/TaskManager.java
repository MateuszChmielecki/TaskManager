import org.apache.commons.lang3.ArrayUtils;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

import static java.lang.System.in;

public class TaskManager {

    static String[][] tasks;

    public static void main(String [] args){
        String fileName = "tasks.csv";
        tasks = loadData(fileName);
        boolean loop = true;
        while(loop){
            options();
            Scanner scanner = new Scanner(in);
            String str = "";
            str = scanner.next();

            if("add".equals(str)){          //// najpierw cos co nigdy nie bedzie nullem, nie może byc "str.equals("add")"
                add();
            }else if("remove".equals(str)){
                remove();
            }else if("list".equals(str)){
                list();
            }else if("exit".equals(str)){
                exit(fileName);
                System.out.print(ConsoleColors.RED_BOLD);
                System.out.println("Bye, Bye");
                loop=false;
            }else{
                System.out.print(ConsoleColors.RED_BOLD);
                System.out.println("You select a uncorrect options");
            }
        }
    }
    public static void options () {
        System.out.print(ConsoleColors.BLUE_BOLD_BRIGHT);
        System.out.println("Please select a correct option:");
        System.out.print(ConsoleColors.PURPLE);
        System.out.println("add");
        System.out.println("remove");
        System.out.println("list");
        System.out.println("exit");
        System.out.print(ConsoleColors.RESET);
    }
    public static String[][] loadData(String fileName) {
        Path table = Paths.get(fileName);
        if (!Files.exists(table)) {
            System.out.println("File not exist.");
            System.exit(0);
        }

        String[][] tab = null;
        try {
            List<String> strings = Files.readAllLines(table);
            tab = new String[strings.size()][strings.get(0).split(",").length];

            for (int i = 0; i < strings.size(); i++) {
                String[] split = strings.get(i).split(", ");
                for (int j = 0; j < split.length; j++) {
                    tab[i][j] = split[j];
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return tab;
    }
    public static void add () {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Please add task description");
        String description = scanner.nextLine();
        System.out.println("Please add task due date");
        String dueDate = scanner.nextLine();
        System.out.println("Is your task important: true/false");
        String isImportant = scanner.nextLine();
        tasks = Arrays.copyOf(tasks, tasks.length + 1);
        tasks[tasks.length - 1] = new String[3];
        tasks[tasks.length - 1][0] = description;
        tasks[tasks.length - 1][1] = dueDate;
        tasks[tasks.length - 1][2] = isImportant;
    }
    public static void remove () {
        Scanner remove = new Scanner(System.in);
        System.out.print(ConsoleColors.RED_BOLD);
        System.out.println("Please select a line to remove:");
        String toRemove = remove.next();
        int r = Integer.parseInt(toRemove);
        if (r < tasks.length){
            tasks = ArrayUtils.remove(tasks, r);
            System.out.println("Line nr " + r + " is deleted");
        }else{
            System.out.println("Line nr " + r + " does not exist.");
        }
    }
    public static void list () {
        String tab [][] = tasks;
        for (int i = 0; i < tab.length; i++) {
            System.out.print(i + " : ");
            for (int j = 0; j < tab[i].length; j++) {
                System.out.print(tab[i][j] + "  ");
            }
            System.out.println();
        }
    }
    public static void exit (String fileName) {
        Path table = Paths.get(fileName);

        String[] lines = new String[tasks.length];
        for (int i = 0; i < tasks.length; i++) {
            lines[i] = String.join(", ", tasks[i]);
        }
        try {
            Files.write(table, Arrays.asList(lines));
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }
}

