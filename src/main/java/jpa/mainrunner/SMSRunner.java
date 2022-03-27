package jpa.mainrunner;

import jpa.entitymodels.Course;
import jpa.service.CourseService;
import jpa.service.StudentService;

import java.util.*;

public class SMSRunner {

    private final StudentService studentService = new StudentService();
    private final CourseService courseService = new CourseService();
    public Scanner input = new Scanner(System.in);

    public static void main(String[] args) {
        new SMSRunner().work();
    }

    public void work(){
        //Start question
        System.out.print("Are you a(n)\n 1. Student\n 2. quit\n Please, enter 1 or 2");
        //Takes input from user
        String x = input.nextLine();

        //If the user enters 1, they are prompted to enter their email and password
        if(Objects.equals(x, "1")){
            System.out.println("Enter Your Email:");
            String email = input.nextLine();
            System.out.println("Enter Your Password:");
            String password = input.nextLine();
            //Takes the input and checks if the email and password matches what is in the database
            boolean validate = studentService.validateStudent(email, password);

            //If the student is validated, the workflow continues
            if (validate) {
                //Prints out all the courses that they are registered in
                List<Course> allStudentCourses = studentService.getStudentCourses(email);
                System.out.print("My Classes:\n");
                System.out.printf("%-3s %-20s %-20s\n", "#", "COURSE NAME", "INSTRUCTOR NAME");
                allStudentCourses.forEach(sc -> {
                    System.out.printf("%-3d %-20s %-20s\n", sc.getCId(), sc.getCName(), sc.getCInstructorName());
                });

                //Start of loop to allow student to register to other courses or log out
                loop:
                while(true){
                    System.out.print("1. Register to class\n 2. Logout");
                    String y = input.next();
                    switch (y) {
                        case "1":
                            //If a student chooses to register to a new course, all courses are printed out
                            List<Course> allCourses = courseService.getAllCourses();
                            System.out.print("All Courses:\n");
                            System.out.printf("%-3s %-20s %-20s\n", "#", "COURSE NAME", "INSTRUCTOR NAME");
                            allCourses.forEach(ac -> {
                                System.out.printf("%-3d %-20s %-20s\n", ac.getCId(), ac.getCName(), ac.getCInstructorName());
                            });

                            //Prompts student to choose a course
                            System.out.print("Which course?\n");

                            //If the input is an integer, the workflow continues
                            if(input.hasNextInt()){
                                studentService.registerStudentToCourse(email, input.nextInt());
                                System.out.print("My Classes:\n");
                                System.out.printf("%-3s %-20s %-20s\n", "#", "COURSE NAME", "INSTRUCTOR NAME");
                                List<Course> allStudentNewCourses = studentService.getStudentCourses(email);
                                allStudentNewCourses.forEach(sc -> {
                                    System.out.printf("%-3d %-20s %-20s\n", sc.getCId(), sc.getCName(), sc.getCInstructorName());
                                });
                            }
                            //If the input is not an integer, the following message is displayed
                                else{
                                    System.out.println("Not a valid course number");
                                    String dudLine = input.next();
                                }
                            break;

                        //If the student chooses to log out, the programs ends with this message:
                        case "2":
                            System.out.println("You have been signed out");
                            break loop;

                        //If the student enters something other than 1 or 2, this message is displayed
                        default:
                            System.out.print("That was not a valid input. Please, enter 1 or 2\n");
                            break;
                    }
                }
            }
            //If the validation fails, this message is displayed and the work method starts again
                else {
                System.out.println("Invalid login credentials. Please try again.");
                work();
                }
        }
        //If the user selects 2, the application ends and this message is displayed:
            else if(Objects.equals(x, "2")) {
                System.out.println("You have been signed out");
            }
        //If the user enters something other than 1 or 2, this message is displayed and the work method starts again
            else{
                System.out.print("That was not a valid option\n");
                work();
            }
    }
}


//sbowden1@yellowbook.com
//SJc4aWSU