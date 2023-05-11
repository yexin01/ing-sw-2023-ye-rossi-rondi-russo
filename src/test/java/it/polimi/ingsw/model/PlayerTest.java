package it.polimi.ingsw.model;

/*
class PlayerTest {

    @Test
    @DisplayName("selection: generic 3 tiles check")
    void selection() throws Exception {
        HashMap<String, Integer> playersId = new HashMap<String, Integer>();
        GameRules gameRules = new GameRules();
        ModelView modelView = new ModelView(playersId, gameRules);
        Player player = new Player("player1", modelView);
        Board board = new Board(modelView);
        board.fillBag(gameRules);
        board.firstFillBoard(2, new GameRules());
        ArrayList<BoardBox> selectedTiles = new ArrayList<>();
        int size = 3; int tileID = 0;
        for (int i = 0; i<size; i++) {
            BoardBox boardBox = new BoardBox(i, i);
            boardBox.setTile(new ItemTile(Type.CAT, tileID));
            selectedTiles.add(boardBox);
            tileID++;
        }
        board.setSelectedBoard(selectedTiles);
        player.selection(board);
        int [] order = new int[]{1, 2, 5};
        assertEquals(ErrorType.INVALID_ORDER_TILE, player.checkPermuteSelection(order));
        //assertThrows(Error.class, ()->player.checkPermuteSelection(order));
    }

    @Test
    @DisplayName("checkPermuteSelection: out of bounds order index")
    void checkPermuteSelection() throws Exception {
        HashMap<String, Integer> playersId = new HashMap<String, Integer>();
        GameRules gameRules = new GameRules();
        ModelView modelView = new ModelView(playersId, gameRules);
        Player player = new Player("player1", modelView);
        Board board = new Board(modelView);
        board.fillBag(gameRules);
        board.firstFillBoard(2, new GameRules());
        ArrayList<BoardBox> selectedTiles = new ArrayList<>();
        int size = 3; int tileID = 0;
        for (int i = 0; i<size; i++) {
            BoardBox boardBox = new BoardBox(i, i);
            boardBox.setTile(new ItemTile(Type.CAT, tileID));
            selectedTiles.add(boardBox);
            tileID++;
        }
        board.setSelectedBoard(selectedTiles);
        player.selection(board);
        int [] order = new int[]{1, 2, 5};
        assertEquals(ErrorType.INVALID_ORDER_TILE, player.checkPermuteSelection(order));
        //assertThrows(Error.class, ()->player.checkPermuteSelection(order));
    }

    @Test
    @DisplayName("checkPermuteSelection: repeated order index")
    void checkPermuteSelectionCC1() throws Exception {
        HashMap<String, Integer> playersId = new HashMap<String, Integer>();
        GameRules gameRules = new GameRules();
        ModelView modelView = new ModelView(playersId, gameRules);
        Player player = new Player("player1", modelView);
        Board board = new Board(modelView);
        board.fillBag(gameRules);
        board.firstFillBoard(2, new GameRules());
        ArrayList<BoardBox> selectedTiles = new ArrayList<>();
        int size = 3; int tileID = 0;
        for (int i = 0; i<size; i++) {
            BoardBox boardBox = new BoardBox(i, i);
            boardBox.setTile(new ItemTile(Type.CAT, tileID));
            selectedTiles.add(boardBox);
            tileID++;
        }
        board.setSelectedBoard(selectedTiles);
        player.selection(board);
        int [] order = new int[]{1, 2, 1};
        assertEquals(ErrorType.INVALID_ORDER_TILE, player.checkPermuteSelection(order));
        //assertThrows(Error.class, ()->player.checkPermuteSelection(order));
    }

    @Test
    @DisplayName("permuteSelection: generic check with 3 tiles")
    void permuteSelection() throws Exception {
        HashMap<String, Integer> playersId = new HashMap<String, Integer>();
        GameRules gameRules = new GameRules();
        ModelView modelView = new ModelView(playersId, gameRules);
        Player player = new Player("player1", modelView);
        Board board = new Board(modelView);
        board.fillBag(gameRules);
        board.firstFillBoard(2, new GameRules());
        ArrayList<BoardBox> selectedBoard = new ArrayList<>();
        int a=1; int b=3;  int c=1; int d=4;  int e=1; int f=5; int tileID=0;
        ItemTile itemTile1 = new ItemTile(Type.CAT, tileID); tileID++;
        ItemTile itemTile2 = new ItemTile(Type.TROPHY, tileID); tileID++;
        ItemTile itemTile3 = new ItemTile(Type.PLANT, tileID);
        BoardBox boardBox1 = new BoardBox(a,b); boardBox1.setTile(itemTile1);
        BoardBox boardBox2 = new BoardBox(c,d); boardBox2.setTile(itemTile2);
        BoardBox boardBox3 = new BoardBox(e,f); boardBox3.setTile(itemTile3);
        selectedBoard.add(boardBox1);
        selectedBoard.add(boardBox2);
        selectedBoard.add(boardBox3);
        board.setSelectedBoard(selectedBoard);
        player.selection(board);
        ArrayList<ItemTile> selectedItems = new ArrayList<>();
        selectedItems.add(boardBox2.getTile());
        selectedItems.add(boardBox1.getTile());
        selectedItems.add(boardBox3.getTile());
        int [] order = new int[]{1, 0, 2};
        player.permuteSelection(order);
        assertIterableEquals(selectedItems, player.getSelectedItems());
    }
} */

