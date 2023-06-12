package it.polimi.ingsw.network.server.persistence;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import it.polimi.ingsw.json.GameRules;
import it.polimi.ingsw.message.Message;
import it.polimi.ingsw.message.MessageHeader;
import it.polimi.ingsw.message.MessagePayload;
import it.polimi.ingsw.model.PersonalGoalCard;
import it.polimi.ingsw.model.modelView.BoardBoxView;
import it.polimi.ingsw.model.modelView.ItemTileView;
import it.polimi.ingsw.model.modelView.ModelView;
import it.polimi.ingsw.model.modelView.PlayerPointsView;
import it.polimi.ingsw.network.server.GameLobby;
import it.polimi.ingsw.network.server.GlobalLobby;
import it.polimi.ingsw.network.server.Server;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

public class SaveGame {
    private static final String jarPathString;
    private static final Gson gson;

    static {
        File jarPath;
        try {
            jarPath = new File(Server.class.getProtectionDomain().getCodeSource().getLocation().toURI().getPath());
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        //jarPathString = jarPath.getParentFile().getAbsolutePath();
        jarPathString = ".\\src\\main\\resources";
        new File(jarPathString + "/persistence").mkdir();

        GsonBuilder gsonBuilder = new GsonBuilder();
        gson = gsonBuilder.create();
    }

    public static void loadGameLobbies(GlobalLobby globalLobby, GameRules gamerules) {
        File persistenceFolder = new File(jarPathString + "/persistence");
        File[] files = persistenceFolder.listFiles();
        List<GameLobby> gameLobbies = new ArrayList<>();
        if (files != null) {
            for (File file : files) {
                if (file.isFile() && file.getName().startsWith("Game_With_ID_")) {
                    try {
                        int id = extractIdFromFile(file.getName());
                        GameLobby gameLobby = getGameInfo(id).restoreGameLobby(globalLobby, gamerules);
                        gameLobbies.add(gameLobby);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }
            for (GameLobby gl : gameLobbies) {
                globalLobby.getGameLobbies().put(gl.getIdGameLobby(), gl);
            }
        }
    }

    private static int extractIdFromFile(String filename) {
        // Remove the initial part "Game_With_ID_"
        String idString = filename.substring("Game_With_ID_".length());
        idString = idString.replace(".json", "");
        // Parse the id as an integer
        return Integer.parseInt(idString);
    }

    public static void saveGame(GameLobbyInfo gameLobbyInfo) throws IOException {
        BoardBoxView[][] b=gameLobbyInfo.getModelView().getBoardView();
        System.out.println("GAME LOBBY 7,3 "+b[7][3].getType()+" 7 4 "+b[7][4].getType()+" 7 5  "+b[7][5].getType());
        String fileName = jarPathString + "/persistence/Game_With_ID_" + gameLobbyInfo.getIdGameLobby() + ".json";
        System.out.println("ci entra1");
        try (FileWriter writer = new FileWriter(fileName)) {
            System.out.println("ci entra2");
            gson.toJson(gameLobbyInfo, writer);
            System.out.println("ci entra3");
        } catch (IOException e) {
            System.out.println("Wasn't able to save the game " + gameLobbyInfo.getIdGameLobby() + " on disk");
            e.printStackTrace();
        }
    }


/*
    public static void saveGame(GameLobbyInfo gameLobbyInfo) throws IOException {
        String fileName = jarPathString + "/persistence/Game_With_ID_" + gameLobbyInfo.getIdGameLobby() + ".json";
        File file = new File(fileName);
        boolean created = file.createNewFile();

        String tempFileName = fileName + ".tmp";
        File tempFile = new File(tempFileName);

        try (Writer writer = new FileWriter(tempFile)) {
            gson.toJson(gameLobbyInfo, writer);
        } catch (Exception e) {
            System.out.println("Wasn't able to save the game " + gameLobbyInfo.getIdGameLobby() + " on disk");
        }

        if (tempFile.exists()) {
            if (file.exists()) {
                file.delete();
            }
            tempFile.renameTo(file);
        }
    }

 */

    public static GameLobbyInfo getGameInfo(int gameId) throws FileNotFoundException, IOException {
        FileInputStream fileInputStream = new FileInputStream(jarPathString + "/persistence/Game_With_ID_" + gameId + ".json");
        InputStreamReader streamReader = new InputStreamReader(fileInputStream, StandardCharsets.UTF_8);
        GameLobbyInfo gameLobbyInfo = gson.fromJson(streamReader, GameLobbyInfo.class);
        streamReader.close();
        return gameLobbyInfo;
    }

    public static void deleteGameInfo(int gameId) {
        File fileToDelete = new File(jarPathString + "/persistence/Game_With_ID_" + gameId + ".json");
        try {
            if (!fileToDelete.delete()) throw new Exception();
            System.out.println("Deleted file of game " + gameId);
        } catch (Exception e) {
            System.out.println("Could not delete file of name " + fileToDelete.getName());
        }
    }
}
