package it.polimi.ingsw.view.GUI;

/*
import it.polimi.ingsw.view.ClientView;

import javax.swing.*;
import java.awt.*;
//TODO creare classe atratta da cui si eredita la creazione di bottoni con immagine
//TODO aggiungere il check sull'inserimento nella colonna direttamente sulla cli
public class EndTurnPanel extends JPanel {


    private JButton[][] matrix;

    private static final double SCALE_FACTOR = 1.5;


    private static final int BUTTON_SIZE = (100);


    public EndTurnPanel(ClientView clientView, int gap) {
        super(new BorderLayout(gap, gap));
        matrix = new JButton[clientView.getBookshelfView().length][clientView.getBookshelfView()[0].length];
        JPanel buttonPanel = new JPanel(new GridLayout(matrix.length, matrix[0].length, gap, gap));
        JPanel panel = new JPanel(new FlowLayout(FlowLayout.CENTER, gap, gap));
        panel.add(buttonPanel);
        add(panel, BorderLayout.CENTER);
        for (int i = 0; i <matrix.length ; i++) {
            for (int j = 0; j < matrix[0].length; j++) {
                JButton button = new JButton();
                if (clientView.getBoardView()[i][j].getItemTileView().getTileID() != -1) {
                    button = createEmptyButton(button,".\\src\\main\\java\\it\\polimi\\ingsw\\Images\\Item tiles\\" + clientView.getBookshelfView()[i][j].getTypeView() + " " + clientView.getBookshelfView()[i][j].getTileID() % 3 + ".png");
                } else {
                    button = createEmptyButton(button,".\\src\\main\\java\\it\\polimi\\ingsw\\Images\\Nero.png");
                }
                buttonPanel.add(button);
                matrix[i][j] = button;
            }
        }
        add(buttonPanel, BorderLayout.CENTER);

    }

    private JButton createEmptyButton(JButton button,String path) {
        button.setOpaque(false);
        button.setContentAreaFilled(false);
        button.setBorderPainted(false);
        button.setPreferredSize(new Dimension(BUTTON_SIZE, BUTTON_SIZE));
        ImageIcon emptyIcon = new ImageIcon(path);
        Image emptyImage = emptyIcon.getImage();
        Image scaledEmptyImage = emptyImage.getScaledInstance(BUTTON_SIZE, BUTTON_SIZE, Image.SCALE_SMOOTH);
        button.setIcon(new ImageIcon(scaledEmptyImage));
        return button;
    }
}
/*
    private JButton createConfirmButton(JButton button,String path) {
        button.setOpaque(false);
        button.setContentAreaFilled(false);
        button.setBorderPainted(false);
        button.setPreferredSize(new Dimension(BUTTON_SIZE3, BUTTON_SIZE3));
        ImageIcon emptyIcon = new ImageIcon(path);
        Image emptyImage = emptyIcon.getImage();
        Image scaledEmptyImage = emptyImage.getScaledInstance(BUTTON_SIZE3, BUTTON_SIZE3, Image.SCALE_SMOOTH);
        button.setIcon(new ImageIcon(scaledEmptyImage));
        return button;
    }

             */

