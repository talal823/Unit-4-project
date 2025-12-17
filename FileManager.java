import java.io.*;
import java.util.HashMap;

public class FileManager {
    static String usersFile = "users.txt";
    static String highScoreFile = "highscore.txt";
    
        //loading users to 
        public static HashMap<String, String> loadUsers() {
            HashMap<String, String> users = new HashMap<>();
            try {
                File file = new File(usersFile);
                if (!file.exists()) return users;

                BufferedReader br = new BufferedReader(new FileReader(file));
                String line;
                while ((line = br.readLine()) != null) {
                    String[] parts = line.split(":");
                    if (parts.length == 2) {
                        users.put(parts[0], parts[1]);
                    }
                }
                br.close();
            } catch (Exception e) {
                System.out.println("Error loading users: " + e.getMessage());
            }
                return users; 
        }

        //saving new users
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

        public static int loadHighScore() {
            try {
                File file = new File(highScoreFile);
                if (!file.exists()) return 0;
    
                BufferedReader br = new BufferedReader(new FileReader(file));
                int score = Integer.parseInt(br.readLine());
                br.close();
                return score;
            } catch (Exception e) {
                return 0;
            }
        }

        public static void saveHighScore(int score) {
            try {
                BufferedWriter bw = new BufferedWriter(new FileWriter(highScoreFile));
                bw.write(String.valueOf(score));
                bw.close();
            } catch (Exception e) {
                System.out.println("Error saving high score: " + e.getMessage());
            }
        }
}
