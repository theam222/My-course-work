import java.util.*;

public class ProjectMain {
    // course -> prereqs
    static HashMap<String, HashSet<String>> prereqs = new HashMap<>();
    // student -> completed courses
    static HashMap<String, HashSet<String>> completed = new HashMap<>();
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        System.out.println("Course Enrollment Planner — Commands:");
        printHelp();
        while (true) {
            System.out.print("> ");
            String line = sc.nextLine();
            if (line.trim().isEmpty()) {
                continue;
            }
            String[] parts = line.split(" ");
            String cmd = parts[0].toUpperCase();
            // HElP
            if (cmd.equals("HELP")) {
                printHelp();
            }
            // ADD_COURSE
            else if (cmd.equals("ADD_COURSE")) {
                if (parts.length < 2) {
                    System.out.println("Missing course name");
                    continue;
                }
                String c = parts[1];
                prereqs.putIfAbsent(c, new HashSet<>());
                System.out.println("Added course: " + c);
            }
            // ADD_PREREQ
            else if (cmd.equals("ADD_PREREQ")) {
                if (parts.length < 3) {
                    System.out.println("Missing arguments");
                    continue;
                }
                String c = parts[1];
                String p = parts[2];
                if (c.equals(p)) {
                    System.out.println("A course cannot be its own prerequisite");
                    continue;
                }
                prereqs.putIfAbsent(c, new HashSet<>());
                prereqs.putIfAbsent(p, new HashSet<>());
                prereqs.get(c).add(p);
                System.out.println("Added prereq: " + p + " -> " + c);
            }
            // PREREQS
            else if (cmd.equals("PREREQS")) {
                if (parts.length < 2) {
                    System.out.println("Missing course name");
                    continue;
                }
                String c = parts[1];
                if (!prereqs.containsKey(c)) {
                    System.out.println("Course not found");
                } else {
                    System.out.println("Prereqs for " + c + ": " + prereqs.get(c));
                }
            }
            // COMPLETE
            else if (cmd.equals("COMPLETE")) {
                if (parts.length < 3) {
                    System.out.println("Missing arguments");
                    continue;
                }
                String student = parts[1];
                String course = parts[2];

                completed.putIfAbsent(student, new HashSet<>());

                completed.get(student).add(course);

                System.out.println(student + " completed " + course);
            }
            // DONE
            else if (cmd.equals("DONE")) {
                if (parts.length < 2) {
                    System.out.println("Missing student name");
                    continue;
                }
                String student = parts[1];
                if (!completed.containsKey(student)) {
                    System.out.println("No record");
                } else {
                    System.out.println("Completed: " + completed.get(student));
                }
            }
            // CAN_TAKE
            else if (cmd.equals("CAN_TAKE")) {
                if (parts.length < 3) {
                    System.out.println("Missing arguments");
                    continue;
                }
                String student = parts[1];
                String course = parts[2];
                HashSet<String> need = prereqs.get(course);
                if (need == null || need.isEmpty()) {
                    System.out.println("YES");
                    continue;
                }
                HashSet<String> done = completed.getOrDefault(student, new HashSet<>());
                boolean ok = true;
                for (String p : need) {
                    if (!done.contains(p)) {
                        ok = false;
                        break;
                    }
                }
                if (ok) {
                    System.out.println("YES");
                } else {
                    System.out.println("NO");
                }
            }
            // EXIT
            else if (cmd.equals("EXIT")) {
                System.out.println("Goodbye!");
                break;
            }
            else {
                System.out.println("Unknown command. Type HELP.");
            }
        }
        sc.close();
    }
    // helper method
    static void printHelp() {
        System.out.println("HELP");
        System.out.println("ADD_COURSE <C>");
        System.out.println("ADD_PREREQ <C> <P>");
        System.out.println("PREREQS <C>");
        System.out.println("COMPLETE <student> <C>");
        System.out.println("DONE <student>");
        System.out.println("CAN_TAKE <student> <C>");
        System.out.println("EXIT");
    }
}