package it.polimi.ingsw.json;

import com.google.gson.Gson;
import it.polimi.ingsw.model.PersonalGoalBox;
import it.polimi.ingsw.model.PersonalGoalCard;
import it.polimi.ingsw.model.Type;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.*;
import java.util.ArrayList;

/**
 *class that allows to read from the json file
 */
public class GameRules {
    private JSONObject json;
    private String boardJsonFile="gameRules.json";
    public GameRules() throws Exception {
        this.json = readJsonFromFile();
    }

    /**
     *
     * @return: maximum number of selectable tiles. According to the rules of the game it corresponds to 3.
     */

    public int getMaxSelectableTiles() {
        return ((Long) json.get("max_selectable_tiles")).intValue();
    }

    /**
     *
     * @return: maximum number of letters acceptable for the player's nickname.
     */
    public int getMaxCharactersPlayers() {
        return ((Long) json.get("max_characters_players")).intValue();
    }

    /**
     *
     * @return: minimum number of letters acceptable for the player's nickname.
     */
    public int getMinCharactersPlayers() {
        return ((Long) json.get("min_characters_players")).intValue();
    }

    /**
     *
     * @return: minimum number of players to start a game.
     */
    public int getMinPlayers() {
        return ((Long) json.get("min_players")).intValue();
    }
    public int[] getNumTilesPerType() {
        JSONArray numTilesArr = (JSONArray) json.get("num_tiles_per_type");
        int[] numTiles = new int[numTilesArr.size()];
        for (int i = 0; i < numTilesArr.size(); i++) {
            numTiles[i] = ((Long) numTilesArr.get(i)).intValue();
        }
        return numTiles;
    }

    public int[] getPersonalGoalPoints() {
        JSONArray numTilesArr = (JSONArray) json.get("personalGoalPoints");
        int[] numTiles = new int[numTilesArr.size()];
        for (int i = 0; i < numTilesArr.size(); i++) {
            numTiles[i] = ((Long) numTilesArr.get(i)).intValue();
        }
        return numTiles;
    }

    public int[] getAdjacentArray() {
        JSONArray pointsArr = (JSONArray) json.get("adjacentPoints");
        int[] points = new int[pointsArr.size()];
        for (int i = 0; i < pointsArr.size(); i++) {
            points[i] = ((Long) pointsArr.get(i)).intValue();
        }
        return points;
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

    public int getPossiblePersonalGoalsSize() {
        JSONArray jsonArray = (JSONArray) json.get("personalGoalCard");
        int count = jsonArray.size();
        return count;
    }

    private JSONObject readJsonFromFile() throws Exception {
        JSONParser parser = new JSONParser();
        try {
            InputStream inputStream = getClass().getClassLoader().getResourceAsStream(boardJsonFile);
            if (inputStream != null) {
                InputStreamReader reader = new InputStreamReader(inputStream);
                Object obj = parser.parse(reader);
                return (JSONObject) obj;
            } else {
                throw new Exception("JSON file not found in resources folder");
            }
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
    private ArrayList<Integer> parseArray(JSONArray pointsData) {
        ArrayList<Integer> points = new ArrayList<>();
        for (Object innerObj : points) {
            int point = ((Long) innerObj).intValue();
            points.add(point);
        }
        return points;
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

    public ArrayList<Integer> getCommonGoalPoints(int numPlayers) throws Exception {
        JSONArray commonGoals = (JSONArray) json.get("pointsCommonGoal");
        int maxPlayers = ((Long) json.get("max_players")).intValue();
        // Check if the number of players is valid
        if (numPlayers > maxPlayers) {
            throw new Exception("Invalid number of players!");
        }
        ArrayList<Integer> points = new ArrayList<>();
        // Select the matrix based on the number of players
        for (Object commonGoalObj : commonGoals) {
            JSONObject pointsData = (JSONObject) commonGoalObj;
            if (((Long) pointsData.get("num_players")).intValue() == numPlayers) {
                JSONArray jsonArray = (JSONArray) pointsData.get("points");
                    for (Object innerObj : jsonArray) {
                        int point = ((Long) innerObj).intValue();
                        points.add(point);
                    }
                return points;
            }
        }
        //TODO add default case
       /* if (points.size() == 0) {

        }

        */
        return null;
    }
    public int getNumOfCommonGoals() {
        return ((Long) json.get("numOfCommonGoals")).intValue();
    }

    public PersonalGoalCard getPersonalGoalCard(int index) throws IndexOutOfBoundsException {
        JSONArray jsonArray = (JSONArray) json.get("personalGoalCard");
        if (index < 0 || index >= jsonArray.size()) {
            throw new IndexOutOfBoundsException("Index " + index + " is out of bounds for array of size " + jsonArray.size());
        }
        JSONObject jsonCard = (JSONObject) jsonArray.get(index);
        JSONArray jsonCells = (JSONArray) jsonCard.get("types");
        ArrayList<PersonalGoalBox> cells = new ArrayList<>();
        for (Object obj : jsonCells) {
            JSONObject jsonType = (JSONObject) obj;

            int x = ((Long) jsonType.get("x")).intValue();
            int y = ((Long) jsonType.get("y")).intValue();
            String s = (String) jsonType.get("type");
            Type type=Type.valueOf(s);

            //Type type= Parse(Type, s);
            //Type type = (Type) jsonType.get("type");
            cells.add(new PersonalGoalBox( type, x, y));
        }
        PersonalGoalCard card = new PersonalGoalCard(index, cells);
        return card;
    }

}
