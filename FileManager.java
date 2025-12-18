import java.io.*;
import java.util.HashMap;

public class FileManager {
    // Handles reading and writing users and high scores
    static String usersFile = "users.txt";
    static String highScoresFile = "highscores.txt";

    // Load all saved users into a HashMap
    public static HashMap<String, String> loadUsers() {
        HashMap<String, String> users = new HashMap<>();
        try {
            File file = new File(usersFile);
            if (!file.exists()) return users; // return empty if no file

            BufferedReader br = new BufferedReader(new FileReader(file));
            String line;
            while ((line = br.readLine()) != null) {
                String[] parts = line.split(":");
                if (parts.length == 2) users.put(parts[0], parts[1]);
            }
            br.close();
        } catch (Exception e) {
            System.out.println("Error loading users: " + e.getMessage());
        }
        return users; 
    }

    // Save a new user
    public static void saveUser(String username, String password) {
        try {
            BufferedWriter bw = new BufferedWriter(new FileWriter(usersFile, true));
            bw.write(username + ":" + password);
            bw.newLine();
            bw.close();
        } catch (Exception e) {
            System.out.println("Error saving user: " + e.getMessage());
        }
    }

    // Loading user highscores
    public static int loadUserHighScore(String username) {
        try {
            File file = new File(highScoresFile);
            if (!file.exists()) return 0;
    
            BufferedReader br = new BufferedReader(new FileReader(file));
            String line;
    
            while ((line = br.readLine()) != null) {
                String[] parts = line.split(":");
                if (parts.length == 2 && parts[0].equals(username)) {
                    br.close();
                    return Integer.parseInt(parts[1]);
                }
            }
            br.close();
        } catch (Exception e) {
            System.out.println("Error loading high score");
        }
        return 0;
    }

    //saving a new user high sccore
    public static void saveUserHighScore(String username, int score) {
    HashMap<String, Integer> scores = new HashMap<>();

    try {
        File file = new File(highScoresFile);

        if (file.exists()) {
            BufferedReader br = new BufferedReader(new FileReader(file));
            String line;
            while ((line = br.readLine()) != null) {
                String[] parts = line.split(":");
                if (parts.length == 2) {
                    scores.put(parts[0], Integer.parseInt(parts[1]));
                }
            }
            br.close();
        }

        // update only if higher
        if (!scores.containsKey(username) || score > scores.get(username)) {
            scores.put(username, score);
        }

        BufferedWriter bw = new BufferedWriter(new FileWriter(file));
        for (String user : scores.keySet()) {
            bw.write(user + ":" + scores.get(user));
            bw.newLine();
        }
        bw.close();

    } catch (Exception e) {
        System.out.println("Error saving high score");
    }
}

}

