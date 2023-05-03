package it.polimi.ingsw.view.GUI;

import it.polimi.ingsw.listeners.EventListener;
import it.polimi.ingsw.messages.EventType;
import it.polimi.ingsw.view.ClientView;

import javax.swing.*;
import java.awt.*;
//TODO alternativa javaFX aggiungere questi Panel con JAVAFX

//TODO struttura principale dei frame con gli handler cambiero la fase e in base a quello cambierà il panel
//TODO alternativa i panel si possono aprire quando viene chiamato il metodo askCoordinates, askColumn...
public class PhaseFrame extends JFrame implements EventListener {
    private ClientView clientView;
    private JPanel mainPanel;
    private ChoicePanel choicePanel;
    private BoardBoxPanel boardBoxPanel;


    private EndTurnPanel endTurnPanel;
    private String imageFolderPath;
    public PhaseFrame(ClientView clientView){
        this.clientView=clientView;
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(800, 600);

        this.choicePanel=new ChoicePanel(clientView,10,200,".\\src\\main\\java\\it\\polimi\\ingsw\\Images\\Nero.png");
        this.boardBoxPanel= new BoardBoxPanel(clientView, imageFolderPath, choicePanel);
        //this.provaBookshelf =new ProvaBookshelf(clientView.getBookshelfView(),null, clientView.getSelectionTiles());

        //BottomButtonsPanel bottomButtonsPanel = new BottomButtonsPanel();
        mainPanel = new JPanel(new BorderLayout());
        //this.bookshelfPanel =new BookshelfPanel(clientView);
        clientView.addListener(EventType.TILES_SELECTED,this);
        clientView.addListener(EventType.END_TURN,this);
        backgroundPanel=new BackgroundPanel(new ImageIcon(".\\src\\main\\java\\it\\polimi\\ingsw\\Images\\boards\\bookshelf.png").getImage());
        backgroundPanel.setLayout(new BorderLayout());
        mainPanel.add(backgroundPanel, BorderLayout.CENTER);
        vai();
    }

    public void vai() {
        switch (clientView.getTurnPhase()) {
            case SELECT_FROM_BOARD:
                mainPanel.add(choicePanel, BorderLayout.NORTH);
                mainPanel.add(boardBoxPanel, BorderLayout.CENTER);
                break;
            case SELECT_ORDER_TILES:
                //mainPanel.add(orderTilesPanel, BorderLayout.NORTH);
                mainPanel.remove(boardBoxPanel);
                mainPanel.add(bookshelfPanel, BorderLayout.CENTER);
                break;
            case INSERT_BOOKSHELF_AND_POINTS:
                System.out.println("PANEL BOOKSHELF PRINT");
                mainPanel.remove(bookshelfPanel);
                mainPanel.remove(choicePanel);
                mainPanel.add(new EndTurnPanel(clientView,6), BorderLayout.CENTER);

            default:
                break;
        }

//mainPanel.add(helpButtonPanel, BorderLayout.SOUTH);
//mainPanel.add(abandonButtonPanel, BorderLayout.EAST);

        add(mainPanel);
        setVisible(true);
    }

    @Override
    public void fireEvent(EventType event, String playerNickname, Object newValue) {
        vai();
    }
}


/*
public class MainFrame extends JFrame {

    private String imageFolderPath;
    private BoardBoxView[][] boardView;
    private ItemTileView[][] itemTileViews;
    private TurnPhase currentPhase=TurnPhase.SELECT_FROM_BOARD;

    public void vai(){
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(800, 600);
        //setPhase(Phase.SELECT_FROM_BOARD);
        ChoicePanel choicePanel = new ChoicePanel(boardView);
        BoardBoxPanel boardBoxPanel = new BoardBoxPanel(boardView, imageFolderPath,choicePanel);

        //HelpButtonPanel helpButtonPanel = new HelpButtonPanel();
        //AbandonButtonPanel abandonButtonPanel = new AbandonButtonPanel();
        BottomButtonsPanel bottomButtonsPanel=new BottomButtonsPanel();
        JPanel mainPanel = new JPanel(new BorderLayout());
        BookshelfPanel bookshelfPanel=new BookshelfPanel(itemTileViews,".\\src\\main\\java\\org\\example\\Images\\boards\\bookshelf.png");
        switch (currentPhase) {
            case SELECT_FROM_BOARD:
                mainPanel.add(boardBoxPanel, BorderLayout.CENTER);
                mainPanel.add(choicePanel, BorderLayout.NORTH);
                break;
            case SELECT_COLUMN:
                mainPanel.add(bookshelfPanel, BorderLayout.CENTER);
                break;

            default:
                break;
        }

        //mainPanel.add(helpButtonPanel, BorderLayout.SOUTH);
        //mainPanel.add(abandonButtonPanel, BorderLayout.EAST);

        add(mainPanel);
        setVisible(true);
    }



    /*
    public void vai(){
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(800, 600);
        this.imageFolderPath = imageFolderPath;
        //boardView = new BoardBoxView[9][9];

        BoardBoxPanel boardBoxPanel = new BoardBoxPanel(boardView, imageFolderPath);
        BottomButtonsPanel bottomButtonsPanel = new BottomButtonsPanel();

        JPanel mainPanel = new JPanel(new BorderLayout());
        mainPanel.add(boardBoxPanel, BorderLayout.CENTER);
        mainPanel.add(bottomButtonsPanel, BorderLayout.SOUTH);

        add(mainPanel);
        setVisible(true);
    }

     */
/*
    public void setBoardView(BoardBoxView[][] boardView){
        this.boardView=boardView;
    }

    /*
    public static void main(String[] args) {
        MainFrame frame = new MainFrame(".\src\main\java\org\example\Images\boards\livingroom.png");
    }



    public String getImageFolderPath() {
        return imageFolderPath;
    }

    public void setImageFolderPath(String imageFolderPath) {
        this.imageFolderPath = imageFolderPath;
    }

    public void setItemTileViews(BoardBoxView[][] itemTileViews) {
        this.boardView = itemTileViews;
    }


}

/*
public class MainFrame extends JFrame {

    private String imageFolderPath;
    private BoardBoxView[][] boardView;

    /*
    public MainFrame(String imageFolderPath) {
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(800, 600);
        this.imageFolderPath = imageFolderPath;
        boardView = new BoardBoxView[9][9];
        // populate boardView with appropriate values

        BoardBoxPanel boardBoxPanel = new BoardBoxPanel(boardView, imageFolderPath);
        BottomButtonsPanel bottomButtonsPanel = new BottomButtonsPanel();

        JPanel mainPanel = new JPanel(new BorderLayout());
        mainPanel.add(boardBoxPanel, BorderLayout.CENTER);
        mainPanel.add(bottomButtonsPanel, BorderLayout.SOUTH);

        add(mainPanel);
        setVisible(true);
    }*/

/*
    public void vai(){
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(800, 600);
        this.imageFolderPath = imageFolderPath;
        //boardView = new BoardBoxView[9][9];

        BoardBoxPanel boardBoxPanel = new BoardBoxPanel(boardView, imageFolderPath);
        BottomButtonsPanel bottomButtonsPanel = new BottomButtonsPanel();

        JPanel mainPanel = new JPanel(new BorderLayout());
        mainPanel.add(boardBoxPanel, BorderLayout.CENTER);
        mainPanel.add(bottomButtonsPanel, BorderLayout.SOUTH);

        add(mainPanel);
        setVisible(true);

    }
    public void setBoardView(BoardBoxView[][] boardView){
        this.boardView=boardView;
    }

    public String getImageFolderPath() {
        return imageFolderPath;
    }

    public void setImageFolderPath(String imageFolderPath) {
        this.imageFolderPath = imageFolderPath;
    }
}


/*
public class MainFrame extends JFrame {

    private String imageFolderPath;
    private BoardBoxView[][] boardView;

    /*
    public MainFrame(String imageFolderPath) {
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(800, 600);
        this.imageFolderPath = imageFolderPath;
        boardView = new BoardBoxView[9][9];
        // populate boardView with appropriate values

        BoardBoxPanel boardBoxPanel = new BoardBoxPanel(boardView, imageFolderPath);
        BottomButtonsPanel bottomButtonsPanel = new BottomButtonsPanel();

        JPanel mainPanel = new JPanel(new BorderLayout());
        mainPanel.add(boardBoxPanel, BorderLayout.CENTER);
        mainPanel.add(bottomButtonsPanel, BorderLayout.SOUTH);

        add(mainPanel);
        setVisible(true);
    }


    public void vai(){
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(800, 600);
        this.imageFolderPath = imageFolderPath;
        //boardView = new BoardBoxView[9][9];

        BoardBoxPanel boardBoxPanel = new BoardBoxPanel(boardView, imageFolderPath);
        BottomButtonsPanel bottomButtonsPanel = new BottomButtonsPanel();

        JPanel mainPanel = new JPanel(new BorderLayout());
        mainPanel.add(boardBoxPanel, BorderLayout.CENTER);
        mainPanel.add(bottomButtonsPanel, BorderLayout.SOUTH);

        add(mainPanel);
        setVisible(true);

    }
    public void setBoardView(BoardBoxView[][] boardView){
        this.boardView=boardView;
    }

    public static void main(String[] args) {
        MainFrame frame = new MainFrame(".\\src\\main\\java\\org\\example\\Images\\boards\\livingroom.png");
    }


    public String getImageFolderPath() {
        return imageFolderPath;
    }

    public void setImageFolderPath(String imageFolderPath) {
        this.imageFolderPath = imageFolderPath;
    }
}
/*
public class MainFrame extends JFrame {

    private ImagePanel imagePanel;
    private BufferedImage image;

    public MainFrame() {
        // Imposta la dimensione del frame
        setSize(800, 600);

        try {
            // Carica l'immagine PNG dal file
            image = ImageIO.read(new File("image.png"));
        } catch (Exception e) {
            e.printStackTrace();
        }

        // Crea un'istanza del pannello con l'immagine
        imagePanel = new ImagePanel(image);

        // Aggiungi il pannello dell'immagine al centro del frame
        add(imagePanel, BorderLayout.CENTER);
    }
}

 */
