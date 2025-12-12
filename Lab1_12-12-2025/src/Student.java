import java.util.Scanner;

public class Student {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        System.out.print("Enter student name: ");
        String name = sc.nextLine();

        System.out.print("Enter roll number: ");
        int rollNo = sc.nextInt();
        sc.nextLine();

        System.out.print("Enter branch: ");
        String branch = sc.nextLine();

        System.out.print("Enter GPA: ");
        double gpa = sc.nextDouble();

        System.out.println("\n--- Student Details ---");
        System.out.println("Name: " + name);
        System.out.println("Roll No: " + rollNo);
        System.out.println("Branch: " + branch);
        System.out.println("GPA: " + gpa);
    }
}

