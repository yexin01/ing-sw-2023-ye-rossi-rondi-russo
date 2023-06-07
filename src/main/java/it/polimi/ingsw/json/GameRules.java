package it.polimi.ingsw.json;

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
 * The GameRules class represents the game rules and provides access to various game-related
 * settings and configurations.
 */
public class GameRules {
    private JSONObject json;
    private String gameRulesJsonFile ="gameRules.json";
    /**
     * Constructs a new instance of the GameRules class.
     * @throws Exception If an error occurs while reading the JSON file.
     */
    public GameRules() throws Exception {
        this.json = readJsonFromFile();
    }

    /**
     * Returns the maximum number of selectable tiles.
     * According to the rules of the game, it corresponds to 3.
     * @return The maximum number of selectable tiles.
     */
    public int getMaxSelectableTiles() {
        return ((Long) json.get("max_selectable_tiles")).intValue();
    }

    /**
     * Returns the maximum number of letters acceptable for the player's nickname.
     * @return The maximum number of letters acceptable for the player's nickname.
     */
    public int getMaxCharactersPlayers() {
        return ((Long) json.get("max_characters_players")).intValue();
    }

    /**
     * Returns the minimum number of letters acceptable for the player's nickname.
     * @return The minimum number of letters acceptable for the player's nickname.
     */
    public int getMinCharactersPlayers() {
        return ((Long) json.get("min_characters_players")).intValue();
    }

    /**
     * Returns the minimum number of players to start a game.
     * According to the rules of the game, it corresponds to 2.
     * @return The minimum number of players to start a game.
     */
    public int getMinPlayers() {
        return ((Long) json.get("min_players")).intValue();
    }
    /**
     * Returns an array of the number of tiles per type.
     * @return An array of the number of tiles per type.
     */
    public int[] getNumTilesPerType() {
        JSONArray numTilesArr = (JSONArray) json.get("num_tiles_per_type");
        int[] numTiles = new int[numTilesArr.size()];
        for (int i = 0; i < numTilesArr.size(); i++) {
            numTiles[i] = ((Long) numTilesArr.get(i)).intValue();
        }
        return numTiles;
    }
    /**
     * Returns an array of the personal goal points.
     * @return An array of the personal goal points.
     */
    public int[] getPersonalGoalPoints() {
        JSONArray numTilesArr = (JSONArray) json.get("personalGoalPoints");
        int[] numTiles = new int[numTilesArr.size()];
        for (int i = 0; i < numTilesArr.size(); i++) {
            numTiles[i] = ((Long) numTilesArr.get(i)).intValue();
        }
        return numTiles;
    }
    /**
     * Returns an array of the adjacent array points.
     * @return An array of the adjacent array points.
     */
    public int[] getAdjacentArray() {
        JSONArray pointsArr = (JSONArray) json.get("adjacentPoints");
        int[] points = new int[pointsArr.size()];
        for (int i = 0; i < pointsArr.size(); i++) {
            points[i] = ((Long) pointsArr.get(i)).intValue();
        }
        return points;
    }
    /**
     * Returns the maximum number of players.
     * According to the rules of the game, it corresponds to 4.
     * @return The maximum number of players.
     */
    public int getMaxPlayers() {
        return ((Long) json.get("max_players")).intValue();
    }
    /**
     * Returns the number of rows for the bookshelf.
     * @return The number of rows for the bookshelf.
     */
    public int getRowsBookshelf() {
        return ((Long) json.get("maxRowsBookshelf")).intValue();
    }

    /**
     * Returns the number of columns for the bookshelf.
     *@return The number of columns for the bookshelf.
     */

    public int getColumnsBookshelf() {
        return ((Long) json.get("maxColumnsBookshelf")).intValue();
    }
    /**
     * Returns the common goal card at the specified index.
     * @param index The index of the common goal card.
     * @return The common goal card at the specified index.
     * @throws IndexOutOfBoundsException If the index is out of bounds.
     */
    public String getCommonGoalCard(int index) throws IndexOutOfBoundsException {
        JSONArray jsonArray = (JSONArray) json.get("commonGoalCards");
        if (index < 0 || index >= jsonArray.size()) {
            throw new IndexOutOfBoundsException("Index " + index + " is out of bounds for array of size " + jsonArray.size());
        }
        String card = (String) jsonArray.get(index);
        return card;
    }
    /**
     * Returns the number of common goal cards.
     * @return The number of common goal cards.
     */
    public int getCommonGoalCardsSize() {
        JSONArray jsonArray = (JSONArray) json.get("commonGoalCards");
        int count = jsonArray.size();
        return count;
    }

    /**
     * Returns the number of possible personal goals.
     * @return The number of possible personal goals.
     */
    public int getPossiblePersonalGoalsSize() {
        JSONArray jsonArray = (JSONArray) json.get("personalGoalCard");
        int count = jsonArray.size();
        return count;
    }
    /**
     * Reads a JSON file and returns the JSON object.
     * @return The JSON object read from the file.
     * @throws Exception If the file is not found or there is an error reading the file.
     */
    private JSONObject readJsonFromFile() throws Exception {
        JSONParser parser = new JSONParser();
        try {
            InputStream inputStream = getClass().getClassLoader().getResourceAsStream(gameRulesJsonFile);
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
    /**
     * Parses a matrix from a JSON array.
     * @param matrixData The JSON array containing the matrix data.
     * @return The parsed matrix as a 2D integer array.
     */
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

    /**
     * Returns a matrix of occupaiable cells based on the number of players.
     * @param numPlayers The number of players.
     * @return The matrix based on the number of players.
     * @throws Exception If the number of players is invalid.
     */
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
    /**
     * Returns the common goal points based on the number of players.
     * @param numPlayers The number of players.
     * @return The common goal points based on the number of players.
     * @throws Exception If the number of players is invalid.
     */
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
        return null;
    }
    /**
     * Returns the number of common goals.
     * According to the rules of the game, it corresponds to 2.
     * @return The number of common goals.
     */
    public int getNumOfCommonGoals() {
        return ((Long) json.get("numOfCommonGoals")).intValue();
    }
    /**
     * Returns the personal goal card at the specified index.
     * @param index The index of the personal goal card.
     * @return The personal goal card at the specified index.
     * @throws IndexOutOfBoundsException If the index is out of bounds.
     */
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
            cells.add(new PersonalGoalBox( type, x, y));
        }
        PersonalGoalCard card = new PersonalGoalCard(index, cells);
        return card;
    }

}
