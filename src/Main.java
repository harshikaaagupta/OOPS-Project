package src;

import src.models.*;
import src.users.*;
import java.util.*;

public class Main {
    static List<Question> questions = new ArrayList<>();

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        User currentUser = null;
        Admin admin = new Admin("admin", "admin123");
        Student student = new Student("student", "stud123");
        admin.loadQuestions(questions);

        while (true) {
            System.out.println("Login as: 1. Admin 2. Student 3. Exit");
            int choice = sc.nextInt();
            try {
                if (choice == 1) {
                    currentUser = login(sc, admin);
                    if (currentUser != null) adminMenu(sc, admin);
                } else if (choice == 2) {
                    currentUser = login(sc, student);
                    if (currentUser != null) studentMenu(sc, student);
                } else if (choice == 3) break;
                else System.out.println("Invalid choice.");
            } catch (InvalidLoginException e) {
                System.out.println("Login failed: " + e.getMessage());
            }
        }
        sc.close();
    }

    static User login(Scanner sc, User user) throws InvalidLoginException {
        sc.nextLine();
        System.out.print("Enter username: ");
        String uname = sc.nextLine();
        System.out.print("Enter password: ");
        String pass = sc.nextLine();
        if (uname.equals(user.username) && pass.equals(user.password)) {
            System.out.println("Login successful.");
            return user;
        } else throw new InvalidLoginException("Invalid credentials.");
    }

    static void adminMenu(Scanner sc, Admin admin) {
        boolean adminMenu = true;
        while (adminMenu) {
            admin.displayMenu();
            switch (sc.nextInt()) {
                case 1 -> admin.addQuestion(sc, questions);
                case 2 -> admin.viewQuestions(questions);
                case 3 -> admin.saveQuestions(questions);
                case 4 -> admin.loadQuestions(questions);
                case 5 -> admin.viewResults();
                default -> adminMenu = false;
            }
        }
    }

    static void studentMenu(Scanner sc, Student student) {
        boolean studMenu = true;
        while (studMenu) {
            student.displayMenu();
            switch (sc.nextInt()) {
                case 1, 2 -> student.takeExam(sc, questions);
                default -> studMenu = false;
            }
        }
    }
}
