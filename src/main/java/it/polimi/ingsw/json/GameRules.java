package it.polimi.ingsw.json;

import it.polimi.ingsw.model.Type;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

public class GameRules {
    private JSONObject json;
    private String boardJsonFile=".\\src\\main\\java\\it\\polimi\\ingsw\\json\\gameRules.json";
    public GameRules() throws Exception {
        this.json = readJsonFromFile();
    }

    public int getMaxSelectableTiles() {
        return ((Long) json.get("max_selectable_tiles")).intValue();
    }
    public int[] getNumTilesPerType() {
        JSONArray numTilesArr = (JSONArray) json.get("num_tiles_per_type");
        int[] numTiles = new int[numTilesArr.size()];
        for (int i = 0; i < numTilesArr.size(); i++) {
            numTiles[i] = ((Long) numTilesArr.get(i)).intValue();
        }
        return numTiles;
    }
    public int getMaxPlayers() {
        return ((Long) json.get("max_players")).intValue();
    }
    public int getRowsBookshelf() {
        return ((Long) json.get("maxRowsBookshelf")).intValue();
    }
    public int getColumnsBookshelf() {
        return ((Long) json.get("maxColumnsBookshelf")).intValue();
    }
    public String getCommonGoalCard(int index) throws IndexOutOfBoundsException {
        JSONArray jsonArray = (JSONArray) json.get("commonGoalCards");
        if (index < 0 || index >= jsonArray.size()) {
            throw new IndexOutOfBoundsException("Index " + index + " is out of bounds for array of size " + jsonArray.size());
        }
        String card = (String) jsonArray.get(index);
        return card;
    }

    public int getCommonGoalCardsSize() {
        JSONArray jsonArray = (JSONArray) json.get("commonGoalCards");
        int count = jsonArray.size();
        return count;
    }

    public ArrayList<Integer> getPersonalGoalCardCoordinates(int index) throws IndexOutOfBoundsException {
        JSONArray jsonArray = (JSONArray) json.get("personalGoalsCoordinates");
        if (index < 0 || index >= jsonArray.size()) {
            throw new IndexOutOfBoundsException("Index " + index + " is out of bounds for array of size " + jsonArray.size());
        }
        JSONArray innerArray = (JSONArray) jsonArray.get(index);
        ArrayList<Integer> goals = new ArrayList<>();
        for (Object innerObj : innerArray) {
            int goal = ((Long) innerObj).intValue();
            goals.add(goal);
        }
        return goals;

    }

    public ArrayList<Type> getPersonalGoalCardTypes(int index) throws IndexOutOfBoundsException {
        JSONArray jsonArray = (JSONArray) json.get("personalGoalsTypes");
        if (index < 0 || index >= jsonArray.size()) {
            throw new IndexOutOfBoundsException("Index " + index + " is out of bounds for array of size " + jsonArray.size());
        }
        JSONArray innerArray = (JSONArray) jsonArray.get(index);
        ArrayList<Type> types = new ArrayList<>();
        for (Object innerObj : innerArray) {
            Type type = Type.valueOf((String) innerObj);
            types.add(type);
        }
        return types;
    }
/*
    public Type[] getPersonalGoalCardTypes(int index) throws IndexOutOfBoundsException {
        JSONArray jsonArray = (JSONArray) json.get("personalGoalsTypes");
        if (index < 0 || index >= jsonArray.size()) {
            throw new IndexOutOfBoundsException("Index " + index + " is out of bounds for array of size " + jsonArray.size());
        }
        Type[] card = (Type[]) jsonArray.get(index);
        return card;
    }

 */

    public int getPossiblePersonalGoalsSize() {
        JSONArray jsonArray = (JSONArray) json.get("personalGoalsTypes");
        int count = jsonArray.size();
        return count;
    }





    private JSONObject readJsonFromFile() throws Exception {
        JSONParser parser = new JSONParser();
        try {
            Object obj = parser.parse(new FileReader(boardJsonFile));
            return (JSONObject) obj;
        } catch (IOException | ParseException e) {
            throw new Exception("Error reading JSON file: " + e.getMessage());
        }
    }

    private int[][] parseMatrix(JSONArray matrixData) {
        int[][] matrix = new int[matrixData.size()][((JSONArray) matrixData.get(0)).size()];
        for (int i = 0; i < matrixData.size(); i++) {
            JSONArray row = (JSONArray) matrixData.get(i);
            for (int j = 0; j < row.size(); j++) {
                matrix[i][j] = ((Long) row.get(j)).intValue();
            }
        }
        return matrix;
    }

    public int[][] getMatrix(int numPlayers) throws Exception {
        JSONArray matrices = (JSONArray) json.get("matrices");
        int maxPlayers = ((Long) json.get("max_players")).intValue();

        // Check if the number of players is valid
        if (numPlayers > maxPlayers) {
            throw new Exception("Invalid number of players!");
        }

        // Select the matrix based on the number of players
        int[][] matrix = null;
        for (Object matrixObj : matrices) {
            JSONObject matrixData = (JSONObject) matrixObj;
            if (((Long) matrixData.get("num_players")).intValue() == numPlayers) {
                matrix = parseMatrix((JSONArray) matrixData.get("matrix"));
                break;
            }
        }

        // If no matrix was found, use the default matrix
        if (matrix == null) {
            matrix = parseMatrix((JSONArray) ((JSONObject) matrices.get(0)).get("matrix"));
        }

        return matrix;
    }
    public int getNumOfCommonGoals() {
        return ((Long) json.get("numOfCommonGoals")).intValue();
    }

    public ArrayList<String> getCommonGoalCards() {
        ArrayList<String> commonGoalCards = new ArrayList<>();
        JSONArray jsonArray = (JSONArray) json.get("commonGoalCards");
        for (Object obj : jsonArray) {
            String card = (String) obj;
            commonGoalCards.add(card);
        }
        return commonGoalCards;
    }


    public ArrayList<ArrayList<Type>> getPersonalGoalTypes() {
        ArrayList<ArrayList<Type>> types = new ArrayList<>();
        JSONArray jsonArray = (JSONArray) json.get("types");
        for (Object obj : jsonArray) {
            JSONArray innerArray = (JSONArray) obj;
            ArrayList<Type> innerList = new ArrayList<>();
            for (Object innerObj : innerArray) {
                Type type = Type.valueOf((String) innerObj);
                innerList.add(type);
            }
            types.add(innerList);
        }
        return types;
    }

    public ArrayList<ArrayList<Integer>> getPersonalGoalCoordinates() {
        ArrayList<ArrayList<Integer>> personalGoals = new ArrayList<>();
        JSONArray jsonArray = (JSONArray) json.get("personalGoals");
        for (Object obj : jsonArray) {
            JSONArray innerArray = (JSONArray) obj;
            ArrayList<Integer> goals = new ArrayList<>();
            for (Object innerObj : innerArray) {
                int goal = ((Long) innerObj).intValue();
                goals.add(goal);
            }
            personalGoals.add(goals);
        }
        return personalGoals;
    }
/*  //TODO possible implementation of personalGoalCards
    public PersonalGoalCard getPersonalGoalCard(int index) throws IndexOutOfBoundsException {
        JSONArray jsonArray = (JSONArray) json.get("personalGoalCards");
        if (index < 0 || index >= jsonArray.size()) {
            throw new IndexOutOfBoundsException("Index " + index + " is out of bounds for array of size " + jsonArray.size());
        }
        JSONObject jsonCard = (JSONObject) jsonArray.get(index);
        JSONArray jsonTypes = (JSONArray) jsonCard.get("types");
        ArrayList<Type> types = new ArrayList<>();
        for (Object obj : jsonTypes) {
            JSONObject jsonType = (JSONObject) obj;
            String type = (String) jsonType.get("type");
            int x = ((Long) jsonType.get("x")).intValue();
            int y = ((Long) jsonType.get("y")).intValue();
            types.add(new Type(type, x, y));
        }
        PersonalGoalCard card = new PersonalGoalCard(types);
        return card;
    }
/*
    public static ArrayList<Integer> getArrayListInPosizione(String jsonContent, int tot) throws JSONException {
        try {
            // Converti la stringa JSON in un oggetto JSONObject
            JSONObject jsonObject = new JSONObject(jsonContent);

            // Ottieni l'array "personalGoals" come un JSONArray
            JSONArray personalGoalsArray = jsonObject.getJSONArray("personalGoals");

            // Verifica che la posizione tot sia valida
            if (tot < 0 || tot >= personalGoalsArray.length()) {
                throw new IllegalArgumentException("Posizione tot non valida");
            }

            // Ottieni l'arraylist in posizione tot come un JSONArray
            JSONArray arrayListJSON = personalGoalsArray.getJSONArray(tot);

            // Converte l'arraylist JSON in un oggetto ArrayList<Integer>
            ArrayList<Integer> arrayList = new ArrayList<>();
            for (int i = 0; i < arrayListJSON.length(); i++) {
                arrayList.add(arrayListJSON.getInt(i));
            }

            // Restituisci l'ArrayList<Integer>
            return arrayList;
        } catch (JSONException e) {
            // Se c'è un errore di parsing JSON, solleva una JSONException
            throw new JSONException("Errore di parsing JSON: " + e.getMessage());
        }
    }

 */
}
