import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.lang.reflect.Type;

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

        /*Gson gson = new Gson();
        try (FileWriter writer = new FileWriter(playerName + "_stats.json")) {
            gson.toJson(stats, writer);
            System.out.println("Statistiche salvate correttamente in " + playerName + "_stats.json");
        } catch (IOException e) {
            System.out.println("Errore durante il salvataggio delle statistiche: " + e.getMessage());
        }*/


        //MODIFICA PER CASA
        Gson gson = new Gson();
        File statsFile = new File("statistics.json");

        //se il file esiste, legge i dati già presenti
        List<PlayerStatistics> allStats = new ArrayList<>();
        if (statsFile.exists()) {
            try (FileReader reader = new FileReader(statsFile)) {
                Type listType = new TypeToken<List<PlayerStatistics>>(){}.getType();
                allStats = gson.fromJson(reader, listType);
                if (allStats == null) {
                    allStats = new ArrayList<>();
                }
            } catch (IOException e) {
                System.out.println("Errore durante la lettura delle statistiche: " + e.getMessage());
                //continua comunque con una lista vuota
                allStats = new ArrayList<>();
            }
        }

        //aggiunge le statistiche della partita corrente
        allStats.add(stats);

        //salva la lista aggiornata (riscrivendo il file)
        try (FileWriter writer = new FileWriter(statsFile)) {
            gson.toJson(allStats, writer);
            System.out.println("Statistiche aggiornate correttamente in statistics.json");
        } catch (IOException e) {
            System.out.println("Errore durante il salvataggio delle statistiche: " + e.getMessage());
        }

        String response = client.fetchQuestions(5, "multiple", "easy");
        System.out.println(response);
    }
}