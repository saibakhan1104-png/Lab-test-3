import java.io.*;
import java.util.*;

public class LabTest3 {

    // Inner class to store each question
    static class Question {
        String question, category;
        String[] options = new String[4];
        int correct;

        Question(String q, String[] opts, int c, String cat) {
            question = q;
            options = opts;
            correct = c;
            category = cat;
        }
    }

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        // 1. Get player name
        System.out.print("Enter your name: ");
        String name = sc.nextLine();

        // 2. Get category
        System.out.print("Choose category (science/history/sports/literature/geography): ");
        String category = sc.nextLine().toLowerCase();

        // 3. Load questions from your existing file
        List<Question> questions = loadQuestions("Questions.txt");

        int score = 0;

        System.out.println("\n===== QUIZ START =====");

        // 4. Loop through all questions in the chosen category
        for (Question q : questions) {

            if (!q.category.equals(category))
                continue; // skip questions from other categories

            // Display question and options
            System.out.println("\n" + q.question);
            System.out.println("1. " + q.options[0]);
            System.out.println("2. " + q.options[1]);
            System.out.println("3. " + q.options[2]);
            System.out.println("4. " + q.options[3]);

            System.out.print("Your answer: ");
            int ans = sc.nextInt();

            if (ans == q.correct) {
                System.out.println("Correct!");
                score++;
            } else {
                System.out.println("Wrong! Correct answer was: " + q.correct);
            }
        }

        // 5. Show final score
        System.out.println("\nFinal Score: " + score);

        // 6. Save score to file
        saveScore(name, category, score);
        System.out.println("Your score has been saved!");
    }

    // Function to load questions from file
    public static List<Question> loadQuestions(String fileName) {
        List<Question> list = new ArrayList<>();

        try (BufferedReader br = new BufferedReader(new FileReader(fileName))) {
            String line;

            while ((line = br.readLine()) != null) {
                String[] p = line.split("\\|");
                if (p.length < 7) continue; // skip invalid lines

                String qText = p[0];
                String[] opts = {p[1], p[2], p[3], p[4]};
                int correct = Integer.parseInt(p[5]);
                String category = p[6].toLowerCase();

                list.add(new Question(qText, opts, correct, category));
            }

        } catch (Exception e) {
            System.out.println("Error loading questions file!");
        }

        return list;
    }

    // Function to save score
    public static void saveScore(String name, String category, int score) {
        try (FileWriter fw = new FileWriter("gk_scores.txt", true)) {
            fw.write("Name: " + name + ", Category: " + category + ", Score: " + score + "\n");
        } catch (Exception e) {
            System.out.println("Error saving score!");
        }
    }
}