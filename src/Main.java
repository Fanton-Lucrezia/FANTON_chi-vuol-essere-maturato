import com.google.gson.Gson;

import java.io.FileWriter;
import java.io.IOException;

public class Main{
    public static void main(String[] args){
        ApiClient client = new ApiClient();
        String playerName = "";
        int correctAnswers = 0;
        boolean used5050 = false;
        boolean usedAudience = false;

        PlayerStatistics stats = new PlayerStatistics(
                playerName, correctAnswers, used5050, usedAudience
        );

        Gson gson = new Gson();
        try (FileWriter writer = new FileWriter(playerName + "_stats.json")) {
            gson.toJson(stats, writer);
            System.out.println("Statistiche salvate correttamente in " + playerName + "_stats.json");
        } catch (IOException e) {
            System.out.println("Errore durante il salvataggio delle statistiche: " + e.getMessage());
        }
        String response = client.fetchQuestions(5, "multiple", "easy");
        System.out.println(response);
    }
}