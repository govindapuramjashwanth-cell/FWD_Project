import java.util.*;

class Feedback {
    String name;
    String dept;
    int rating;
    String comment;

    Feedback(String name, String dept, int rating, String comment) {
        this.name = name;
        this.dept = dept;
        this.rating = rating;
        this.comment = comment;
    }
}

public class HospitalFeedbackSystem {

    static ArrayList<Feedback> feedbackList = new ArrayList<>();

    public static void addFeedback(String name, String dept, int rating, String comment) {
        if (comment.trim().isEmpty()) {
            comment = "No comment";
        }

        Feedback f = new Feedback(name, dept, rating, comment);
        feedbackList.add(f);

        System.out.println("Feedback submitted successfully\n");
    }

    public static void updateDashboard() {

        int total = feedbackList.size();

        if (total == 0) {
            System.out.println("No feedback available\n");
            return;
        }

        int sum = 0;
        int positive = 0;

        for (Feedback f : feedbackList) {
            sum += f.rating;
            if (f.rating >= 4) {
                positive++;
            }
        }

        double avg = (double) sum / total;
        int posPercent = (positive * 100) / total;

        System.out.println("Total Feedback: " + total);
        System.out.println("Average Rating: " + String.format("%.1f", avg));
        System.out.println("Positive Feedback: " + posPercent + "%");

        ratingDistribution();
        departmentScores();
        showReviews();
    }

    public static void ratingDistribution() {

        int[] count = new int[6];

        for (Feedback f : feedbackList) {
            count[f.rating]++;
        }

        System.out.println("\nRating Distribution:");
        for (int i = 1; i <= 5; i++) {
            System.out.println(i + " Star: " + count[i]);
        }
    }

    public static void departmentScores() {

        HashMap<String, ArrayList<Integer>> map = new HashMap<>();

        for (Feedback f : feedbackList) {
            map.putIfAbsent(f.dept, new ArrayList<>());
            map.get(f.dept).add(f.rating);
        }

        System.out.println("\nDepartment Scores:");

        for (String dept : map.keySet()) {

            ArrayList<Integer> ratings = map.get(dept);

            int sum = 0;
            for (int r : ratings) {
                sum += r;
            }

            double avg = (double) sum / ratings.size();

            System.out.println(dept + " Staff Score: " + String.format("%.1f", avg));
        }
    }

    public static void showReviews() {

        System.out.println("\nReviews:");

        ListIterator<Feedback> it = feedbackList.listIterator(feedbackList.size());

        while (it.hasPrevious()) {
            Feedback f = it.previous();
            System.out.println(f.name + " (" + f.dept + ") - " + f.rating + "★ : " + f.comment);
        }
    }

    public static void main(String[] args) {

        Scanner sc = new Scanner(System.in);

        while (true) {

            System.out.println("\n1 Add Feedback");
            System.out.println("2 Show Dashboard");
            System.out.println("3 Exit");

            int choice = sc.nextInt();
            sc.nextLine();

            if (choice == 1) {

                System.out.print("Patient Name: ");
                String name = sc.nextLine();

                System.out.print("Department: ");
                String dept = sc.nextLine();

                System.out.print("Rating (1-5): ");
                int rating = sc.nextInt();
                sc.nextLine();

                System.out.print("Comment: ");
                String comment = sc.nextLine();

                addFeedback(name, dept, rating, comment);
            }

            else if (choice == 2) {
                updateDashboard();
            }

            else if (choice == 3) {
                break;
            }
        }

        sc.close();
    }
}