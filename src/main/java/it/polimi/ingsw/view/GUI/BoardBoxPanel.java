package it.polimi.ingsw.view.GUI;
/*
import it.polimi.ingsw.model.modelView.BoardBoxView;
import it.polimi.ingsw.network.server.ErrorType;
import it.polimi.ingsw.view.ClientInterface;
import it.polimi.ingsw.view.ClientView;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
//TODO crere classe atratta da cui si eredita la creazione di bottoni con immagine
public class BoardBoxPanel extends ClientInterface {
    //private BoardBoxView[][] boardView;

    private JButton[][] matrix;
    private String imageFolderPath;
    private ChoicePanel choicePanel;
    private static final double SCALE_FACTOR = 1.5;
    //private int[] selectedButtons=new int[6];
    private ArrayList<Integer> selectedButtons = new ArrayList<>();

    //private int[] selectedButtons=new int[6];

    private int selectedCount;


    private int numSelectableTiles = 3;

    private static final int BUTTON_SIZE = (int) (100 * SCALE_FACTOR);
    //private ArrayList<JButton> selectedButtonsList = new ArrayList<>();
    private JButton confirmChoiceButton;
    private JButton resetChoiceButton;

    public BoardBoxPanel(ClientView clientView, String imageFolderPath, ChoicePanel choicePanel) {
        super(new BorderLayout(5, 5));

        //this.boardView = boardView;
        this.imageFolderPath = imageFolderPath;
        this.choicePanel = choicePanel;
        matrix = new JButton[9][9];
        //Arrays.fill(selectedButtons, -1);
        JPanel buttonPanel = new JPanel(new GridLayout(9, 9, 5, 5));
        for (int i = 0; i < 9; i++) {
            for (int j = 0; j < 9; j++) {
                JButton button = new JButton();
                if (clientView.getBoardView()[i][j].getType() != null) {
                    button = createImageButton(button, i, j);
                } else {
                    button = createEmptyButton(button);
                }
                buttonPanel.add(button);
                matrix[i][j] = button;
            }
        }
        add(buttonPanel, BorderLayout.CENTER);

        JPanel confirmPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT, 5, 5));
        confirmChoiceButton = new JButton("Confirm choice");
        confirmChoiceButton.addActionListener(e -> {
            //selectedButtons.splice(-selectedButtons.length-2*selectedCount);
            clientView.setSelectionTiles(selectedButtons.stream().mapToInt(Integer::intValue).toArray());
            clientView.askClient(3);
            choicePanel.addActionListener();
            choicePanel.enlargePanel();
            //clientView.changePhase();
        });
        confirmPanel.add(confirmChoiceButton);

        resetChoiceButton = new JButton("Reset choice");
        resetChoiceButton.addActionListener(e -> {
            if (selectedCount != 0) {
                resetChoice();
            }

        });
        confirmPanel.add(resetChoiceButton);

        add(confirmPanel, BorderLayout.SOUTH);
    }

    private JButton createEmptyButton(JButton button) {
        button.setOpaque(false);
        button.setContentAreaFilled(false);
        button.setBorderPainted(false);
        button.setPreferredSize(new Dimension(BUTTON_SIZE, BUTTON_SIZE));
        return button;
    }

    private JButton createImageButton(JButton button, int i, int j) {
        button.setOpaque(false);
        button.setContentAreaFilled(false);
        button.setBorderPainted(false);
        button.setSize(new Dimension(BUTTON_SIZE, BUTTON_SIZE));
        ImageIcon icon = new ImageIcon(".\\src\\main\\java\\it\\polimi\\ingsw\\Images\\Item tiles\\" + clientView.getBoardView()[i][j].getType() + " " + clientView.getBoardView()[i][j].getId() % 3 + ".png");
        Image image = icon.getImage();
        Image scaledImage = image.getScaledInstance(button.getWidth(), button.getHeight(), Image.SCALE_SMOOTH);
        button.setIcon(new ImageIcon(scaledImage));
        button.setPreferredSize(new Dimension(icon.getIconWidth(), icon.getIconHeight()));
        button.addActionListener(e -> {
            ErrorType error;
            error = checkSelectable(i, j);
            if (error == null) {
                selectButton(button, i, j);
            } else {
                JOptionPane.showMessageDialog(this, error.getErrorMessage());
            }
        });
        return button;
    }

    private void selectButton(JButton button, int i, int j) {
        choicePanel.setButtonIcon(".\\src\\main\\java\\org\\example\\Images\\Item tiles\\" + clientView.getBoardView()[i][j].getType() + " " + clientView.getBoardView()[i][j].getId() % 3 + ".png", selectedCount);
        button.removeActionListener(button.getActionListeners()[0]);
        selectedCount++;
        button.setIcon(new ImageIcon(".\\src\\main\\java\\org\\example\\Images\\boards\\Empty.JPG"));
    }

    public void resetChoice() {
        for (int k = 0; k < selectedCount * 2; k = k + 2) {
            int x = selectedButtons.get(k);
            int y = selectedButtons.get(k + 1);
            JButton button = matrix[x][y];
            choicePanel.resetChoice();
            createImageButton(button, x, y);
        }
        selectedCount = 0;
        selectedButtons = new ArrayList<>();
        //Arrays.fill(selectedButtons, -1);


        for (int k = 0; k < selectedCount * 2; k = k + 2) {
            int x = selectedButtons[k];
            int y = selectedButtons[k + 1];
            JButton button = matrix[x][y];
            choicePanel.resetChoice();
            createImageButton(button, x, y);
        }
        selectedCount = 0;
        Arrays.fill(selectedButtons, -1);


    }

    @Override
    public String getNickname() {
        return null;
    }

    @Override
    public int[] askCoordinates() {
        return new int[0];
    }

    @Override
    public int[] askOrder() {
        return new int[0];
    }

    @Override
    public int[] askColumn() {
        return new int[0];
    }
/*
    @Override
    public ClientView getCurrentView() {
        return null;
    }
/*
    /**
     * @return check that each ItemTile of selectedBoard is adjacent to the previous one

    public boolean allAdjacent() {
        for (int i = 2; i <= selectedCount * 2; i = i + 2) {
            if (Math.abs(selectedButtons.get(i) - selectedButtons.get(i - 2)) != 1 && Math.abs(selectedButtons.get(i + 1) - selectedButtons.get(i - 1)) != 1) {
                JOptionPane.showMessageDialog(this, "NON SONO ADIACENTI");
                return false;
            }
        }
        return true;
    }

    /**
     * @return check that all the ItemTiles in the selectedBoard array are on the same row or column

    public boolean allSameRowOrSameColumn() {
        int firstX = selectedButtons.get(0);
        int firstY = selectedButtons.get(1);
        boolean allSameRow = true;
        boolean allSameColumn = true;
        for (int i = 2; i <= selectedCount * 2; i = i + 2) {
            if (selectedButtons.get(i) != firstX) {
                allSameRow = false;
            }
            if (selectedButtons.get(i + 1) != firstY) {
                allSameColumn = false;
            }
        }
        if (allSameRow ^ allSameColumn) {
            return true;
        }


        int firstX = selectedButtons[0];
        int firstY = selectedButtons[1];
        boolean allSameRow = true;
        boolean allSameColumn = true;
        for (int i = 2; i <= selectedCount * 2; i = i + 2) {
            if (selectedButtons[i] != firstX) {
                allSameRow = false;
            }
            if (selectedButtons[i + 1] != firstY) {
                allSameColumn = false;
            }
        }
        if (allSameRow ^ allSameColumn) {
            return true;
        }


        return false;
    }

    /**
     * adjacent, in the same row or column and adjacent
     *
     * @return


    public ErrorType checkSelectable(int x, int y) throws Error {
        //TODO AGGIUNGERLO COME PARAMETRO
        if (selectedCount >= (3)) {
            return ErrorType.TOO_MANY_TILES;
        }
        BoardBoxView boardBox = clientView.getBoardView()[x][y];
        if ((boardBox.getFreeEdges() <= 0)) {
            return ErrorType.NOT_SELECTABLE_TILE;
        }
        selectedButtons.add(x);
        selectedButtons.add(y);
        /*
        selectedButtons[selectedCount * 2] = x;
        selectedButtons[selectedCount * 2 + 1] = y;




        if (selectedCount == 0) {
            return null;
        }
        if (!allAdjacent() || !allSameRowOrSameColumn()) {
            selectedButtons.remove(selectedButtons.size() - 1);
            selectedButtons.remove(selectedButtons.size() - 1);
            /*
            selectedButtons[selectedCount* 2]=-1;
            selectedButtons[selectedCount* 2 + 1]=-1;




            return ErrorType.NOT_SELECTABLE_TILE;
        }
    }
}

 */






        /*
        if(selectedCount*2 >= (selectedButtons.length)){
            return ErrorType.TOO_MANY_TILES;
        }
        BoardBoxView boardBox=clientView.getBoardView()[x][y];
        if ((boardBox.getFreeEdges() <= 0)) {
            return ErrorType.NOT_SELECTABLE_TILE;
        }
        selectedButtons[selectedCount * 2] = x;
        selectedButtons[selectedCount * 2 + 1] = y;
        if (selectedCount == 0) {
            return null;
        }
        if (!allAdjacent() || !allSameRowOrSameColumn()) {
            selectedButtons[selectedCount* 2]=-1;
            selectedButtons[selectedCount* 2 + 1]=-1;
            return ErrorType.NOT_SELECTABLE_TILE;
        }


        return null;
    }

   */





