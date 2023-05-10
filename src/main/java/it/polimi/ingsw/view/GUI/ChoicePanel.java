package it.polimi.ingsw.view.GUI;


import it.polimi.ingsw.view.ClientView;
 /*
import javax.swing.*;
import java.awt.*;
//TODO creare classe atratta da cui si eredita la creazione di bottoni con immagine
public class ChoicePanel extends JPanel {

    private ClientView clientView;
    private int[] idSelected;
    private JButton[] itemButtons;
    private String nero=".\\src\\main\\java\\org\\example\\Images\\Nero.png";


    private ImageIcon[] itemImages;


    private static final double SCALE_FACTOR = 1;
    //private static final int BUTTON_SIZE = (int) (1 * SCALE_FACTOR);
    private static final int BUTTON_SIZE1 = 75;
    private static final int BUTTON_SIZE2 = 200;
    private static final int BUTTON_SIZE3 = 100;
    //private BoardBoxPanel boardBoxPanel;



    public ChoicePanel(ClientView clientView,int gap,int dimension,String stringPath) {
        super(new BorderLayout(gap, gap));
        this.clientView=clientView;
        this.nero=stringPath;
        JPanel buttonPanel = new JPanel(new GridLayout(1, 3, gap, gap));
        setPreferredSize(new Dimension(dimension, dimension));
        itemButtons = new JButton[3];
        idSelected = new int[3];
        for (int i = 0; i < 3; i++) {
            itemButtons[i] = new JButton();
            createEmptyButton(itemButtons[i]);
            buttonPanel.add(itemButtons[i]);
        }
        add(buttonPanel, BorderLayout.CENTER);

    }
    private JButton createEmptyButton(JButton button) {
        button.setOpaque(false);
        button.setContentAreaFilled(false);
        button.setBorderPainted(false);
        button.setPreferredSize(new Dimension(BUTTON_SIZE1, BUTTON_SIZE1));
        ImageIcon emptyIcon = new ImageIcon(nero);
        Image emptyImage = emptyIcon.getImage();
        Image scaledEmptyImage = emptyImage.getScaledInstance(BUTTON_SIZE1, BUTTON_SIZE1, Image.SCALE_SMOOTH);
        button.setIcon(new ImageIcon(scaledEmptyImage));
        return button;
    }
    public void increaseButtonSize(JButton button,int i) {
        button.setPreferredSize(new Dimension(BUTTON_SIZE2, BUTTON_SIZE2));
        //TODO avevo aggiunto il metodo sulla client view che ti restituisce la tile inserendo il numero
        ImageIcon newIcon = new ImageIcon(".\\src\\main\\java\\it\\polimi\\ingsw\\Images\\Item tiles\\"+clientView.getTilesSelected(i).getTypeView()+" "+clientView.getTilesSelected(i).getTileID()%3+".png");
        Image currentImage = newIcon.getImage();
        Image scaledImage = currentImage.getScaledInstance(BUTTON_SIZE2, BUTTON_SIZE2, Image.SCALE_SMOOTH);
        button.setIcon(new ImageIcon(scaledImage));
    }

    public void resetChoice() {
        for(JButton n:itemButtons){
            createEmptyButton(n);
        }
    }


    public void addActionListener() {
        for (int i = 0; i < itemButtons.length; i++) {
            final int index = i;
            int finalI = i;
            itemButtons[i].addActionListener(e -> {
                JButton button = (JButton) e.getSource();
                //int buttonIndex = Arrays.asList(itemButtons).indexOf(button);
                //TODO nella vecchia ClientView  c'era questo array va riaggiungto
                //clientView.setSelectOrder(finalI);
                button.setPreferredSize(new Dimension(BUTTON_SIZE2 - 10, BUTTON_SIZE2 - 10));
                button.removeActionListener(button.getActionListeners()[0]);
            });
        }
    }

        public void enlargePanel() {
            setLayout(new BorderLayout());
            int newWidth = getWidth() * 2;
            int newHeight = getHeight() * 2;
            setPreferredSize(new Dimension(newWidth, newHeight));
            //removeAll(); // Rimuovi tutti i componenti esistenti dal pannello

            int index=0;
            for (JButton i : itemButtons) {
                increaseButtonSize(i, index++);
            }
            JButton newButton = new JButton("New Button");
            add(newButton, BorderLayout.EAST); // Aggiungi il nuovo bottone alla destra
            revalidate();
        }




    public void enlargePanel() {
        int newWidth = (int) (getWidth() * 2);
        int newHeight = (int) (getHeight() * 2);
        setPreferredSize(new Dimension(newWidth, newHeight));
        int index=0;
        for(JButton i:itemButtons){
            increaseButtonSize(i, index++);
        }
        JButton confirmOrder = new JButton("Confirm Order");
        confirmOrder.addActionListener(e -> {
            //clientView.askClient(4);
        });
        JButton resetOrder = new JButton("Reset Order");

        resetOrder.addActionListener(e -> {

            //clientView.resetOrder();
        });
        //setLayout(new MigLayout("fill, insets 0")); //

        for (int i = 0; i < itemButtons.length; i++) {
            add(itemButtons[i], "cell " + i + " 0");
        }

        add(resetOrder, "alignx right, aligny top");
        add(confirmOrder, "alignx right, aligny top");
        revalidate();
    }
    /*
public void enlargePanel() {
    int newWidth = (int) (getWidth() * 2);
    int newHeight = (int) (getHeight() * 2);
    setPreferredSize(new Dimension(newWidth, newHeight));
    int index=0;
    for(JButton i:itemButtons){
        increaseButtonSize(i, index++);
    }
    JButton confirmOrder = new JButton("Confirm Order");
    confirmOrder.addActionListener(e -> {
        clientView
    });

    setLayout(new MigLayout("fill, insets 0")); // utilizziamo MigLayout

// posizioniamo i bottoni esistenti in una griglia
    for (int i = 0; i < itemButtons.length; i++) {
        add(itemButtons[i], "cell " + i + " 0");
    }

// posizioniamo il nuovo bottone in alto a destra
    add(confirmOrder, "alignx right, aligny top");

    revalidate();
}

     */

    /*

    public void enlargePanel() {
        int newWidth = (int) (getWidth() * 2);
        int newHeight = (int) (getHeight() * 2);
        setPreferredSize(new Dimension(newWidth, newHeight));
        int index=0;
        for(JButton i:itemButtons){
            increaseButtonSize(i,index++);
        }
        JButton newButton = new JButton("New Button");

        setLayout(new GridBagLayout());
        GridBagConstraints c = new GridBagConstraints();

        for (int i = 0; i < itemButtons.length; i++) {
            c.gridx = i;
            c.gridy = 0;
            add(itemButtons[i], c);
        }

        c.gridx = GridBagConstraints.REMAINDER;
        c.gridy = 0;
        c.anchor = GridBagConstraints.LINE_END;
        add(newButton, c);


        revalidate();
    }



    public void setButtonIcon(String imagePath, int index) {
        ImageIcon icon = new ImageIcon(imagePath);
        Image image = icon.getImage();
        Image scaledImage = image.getScaledInstance(BUTTON_SIZE1, BUTTON_SIZE1, Image.SCALE_SMOOTH);
        itemButtons[index].setIcon(new ImageIcon(scaledImage));
        /*
        if (image.getWidth(null) != 0 && image.getHeight(null) != 0) {

            if (selectedCount < itemButtons.length) {

            }
        }


    }


}*//*
        Dimension buttonDimension = new Dimension(BUTTON_SIZE, BUTTON_SIZE);
        for (JButton button : itemButtons) {
            button.setPreferredSize(buttonDimension);
            System.out.println("Button width: " + button.getWidth() + ", height: " + button.getHeight());

            ImageIcon emptyIcon = new ImageIcon(".\\src\\main\\java\\org\\example\\Images\\Nero.png");
            Image emptyImage = emptyIcon.getImage();
            if (emptyImage.getWidth(null) != 0 && emptyImage.getHeight(null) != 0) {
                Image scaledEmptyImage = emptyImage.getScaledInstance(BUTTON_SIZE, BUTTON_SIZE, Image.SCALE_SMOOTH);
                button.setIcon(new ImageIcon(scaledEmptyImage));
                System.out.println("Image width: " + scaledEmptyImage.getWidth(null) + ", height: " + scaledEmptyImage.getHeight(null));
            }
        }

         */

/*
        confirmChoiceButton = new JButton("Conferma Scelta");
        confirmChoiceButton.addActionListener(e -> {
            if (selectedCount <= 2 && selectedCount > 0) {
                JOptionPane.showMessageDialog(this, "Hai già selezionato il massimo numero di tessere.");
            } else {
                JOptionPane.showMessageDialog(this, "Non hai selezionato tessere!");
            }
        });

        resetChoiceButton = new JButton("Resetta Scelta");
        resetChoiceButton.addActionListener(e -> {
            if (selectedCount != 0) {
                JOptionPane.showMessageDialog(this, "Sicuro di voler eliminare la scelta?");
            }
            //resetChoice();
        });

        JPanel confirmPanel = new JPanel(new GridLayout(2, 1, 5, 5));
        confirmPanel.add(confirmChoiceButton);
        confirmPanel.add(resetChoiceButton);
        add(confirmPanel, BorderLayout.EAST);

 */
/*
    public boolean checkTile(int id) {
        for (int i = 0; i < selectedCount; i++) {
            if (idSelected[i] == id) {
                JOptionPane.showMessageDialog(this, "Hai già selezionato questo oggetto!");
                return false;
            }
        }
        if (selectedCount == idSelected.length) {
            JOptionPane.showMessageDialog(this, "Hai selezionato il numero massimo di tessere");
            return false;
        }

        return true;
    }

 */















    /*
    public ChoicePanel() {
        super(new BorderLayout(10, 10));

        JPanel buttonPanel = new JPanel(new GridLayout(1, 3, 10, 10));
        itemButtons = new JButton[3];
        for (int i = 0; i < 3; i++) {
            itemButtons[i] = new JButton();

            // Dimensione dei bottoni del ChoicePanel
            itemButtons[i].setContentAreaFilled(false);
            itemButtons[i].setBorderPainted(true);
            itemButtons[i].setOpaque(true);
            itemButtons[i].setPreferredSize(new Dimension(BUTTON_SIZE, BUTTON_SIZE));

            // Assegna l'icona di default
            //ImageIcon icon = new ImageIcon(".\\src\\main\\java\\org\\example\\Images\\Publisher material\\Icon.png");
            //Image image = icon.getImage();
            //Image scaledImage = image.getScaledInstance(BUTTON_SIZE, BUTTON_SIZE, Image.SCALE_SMOOTH);
            //itemButtons[i].setIcon(new ImageIcon(scaledImage));

            buttonPanel.add(itemButtons[i]);
        }
        add(buttonPanel, BorderLayout.CENTER);

        confirmChoiceButton = new JButton("Conferma Scelta");
        confirmChoiceButton.addActionListener(e -> {
            if (selectedCount <= 2 && selectedCount > 0) {
                JOptionPane.showMessageDialog(this, "Hai già selezionato il massimo numero di tessere.");
            } else {
                JOptionPane.showMessageDialog(this, "Non hai selezionato tessere!");
            }
        });

        resetChoiceButton = new JButton("Resetta Scelta");
        resetChoiceButton.addActionListener(e -> {
            if (selectedCount != 0) {
                JOptionPane.showMessageDialog(this, "Sicuro di voler eliminare la scelta?");
            }
            resetChoice();
        });

        JPanel confirmPanel = new JPanel(new GridLayout(2, 1, 5, 5));
        confirmPanel.add(confirmChoiceButton);
        confirmPanel.add(resetChoiceButton);
        add(confirmPanel, BorderLayout.EAST);
    }

    public void setEmptyIcon() {
        ImageIcon emptyIcon = new ImageIcon(".\\src\\main\\java\\org\\example\\Images\\Empty.png");
        Image emptyImage = emptyIcon.getImage();
        Image scaledEmptyImage = emptyImage.getScaledInstance(BUTTON_SIZE, BUTTON_SIZE, Image.SCALE_SMOOTH);
        for (int i = 0; i < 3; i++) {
            itemButtons[i].setIcon(new ImageIcon(scaledEmptyImage));
        }
    }
    /*
    public void addImage(String path){
        ImageIcon tesseraIcon = new ImageIcon(".\src\main\java\org\example\Images\tessera.png");
        Image tesseraImage = tesseraIcon.getImage();
        Image scaledTesseraImage = tesseraImage.getScaledInstance(BUTTON_SIZE, BUTTON_SIZE, Image.SCALE_SMOOTH);
        choicePanel.getItemButtons()[selectedIndex].setIcon(new ImageIcon(scaledTesseraImage)); // Sostituisce l'icona vuota con quella della tessera selezionata.

    }


    /*
    public ChoicePanel() {
        super(new BorderLayout(10, 10)); // modifica del layout manager del ChoicePanel
// creazione dei bottoni selezionati

        JPanel buttonPanel = new JPanel(new GridLayout(1, 3, 10, 10)); // nuovo pannello per i bottoni
        itemButtons = new JButton[3];
        for (int i = 0; i < 3; i++) {
            itemButtons[i] = new JButton(); // istanziazione del JButton
            itemButtons[i].setContentAreaFilled(false);
            itemButtons[i].setBorderPainted(true);
            itemButtons[i].setOpaque(true);
            int buttonSize = (int) (BUTTON_SIZE * SCALE_FACTOR); // dimensione proporzionale
            itemButtons[i].setPreferredSize(new Dimension(buttonSize, buttonSize));
            ImageIcon icon = new ImageIcon(".\\src\\main\\java\\org\\example\\Images\\scoring tokens\\Nero.png");
            Image image = icon.getImage();
            Image scaledImage = image.getScaledInstance(buttonSize, buttonSize, Image.SCALE_SMOOTH); // ridimensionamento dell'immagine
            itemButtons[i].setIcon(new ImageIcon(scaledImage)); // assegnazione dell'icona
            buttonPanel.add(itemButtons[i]); // aggiunta del JButton al pannello dei bottoni
        }
        add(buttonPanel, BorderLayout.CENTER); // aggiunta del pannello dei bottoni al ChoicePanel

        confirmChoiceButton = new JButton("Conferma Scelta");
        confirmChoiceButton.addActionListener(e -> {
            if (selectedCount <= 2 && selectedCount > 0) {
                JOptionPane.showMessageDialog(this, "Hai già selezionato il massimo numero di tessere.");
            } else {
                JOptionPane.showMessageDialog(this, "Non hai selezionato tessere!");
            }
        });

        resetChoiceButton = new JButton("Resetta Scelta");
        resetChoiceButton.addActionListener(e -> {
            if (selectedCount != 0) {
                JOptionPane.showMessageDialog(this, "Sicuro di voler eliminare la scelta?");
            }
            resetChoice();
            //SelectionBoardPanel parentPanel = (SelectionBoardPanel) ChoicePanel.this.getParent();
            //parentPanel.enableRemainingButtons();
        });

        JPanel confirmPanel = new JPanel(new GridLayout(2, 1, 5, 5));
        confirmPanel.add(confirmChoiceButton);
        confirmPanel.add(resetChoiceButton);
        add(confirmPanel, BorderLayout.EAST); // aggiunta del pannello destro al ChoicePanel
    }

     */


    /*
    public ChoicePanel() {
        super(new GridLayout(1, 5));

        buttons = new JButton[5];
        lastIndex = 0;

        for (int i = 0; i < buttons.length - 2; i++) {
            buttons[i] = new JButton();
            add(buttons[i]);
        }

        add(new JPanel());

        JButton confirmButton = new JButton("ConfirmChoice");
        confirmButton.addActionListener(e -> confirmChoice());
        add(confirmButton);

        JButton resetButton = new JButton("ResetChoice");
        resetButton.addActionListener(e -> resetChoice());
        add(resetButton);
    }

     */
    /*
    public void moveButton(String pathImage,JButton button) {
        if (selectedCount < itemButtons.length - 2) {
            int buttonSize = (int) (BUTTON_SIZE * SCALE_FACTOR);
            ImageIcon icon = new ImageIcon(pathImage);
            Image image = icon.getImage();
            Image scaledImage = image.getScaledInstance(buttonSize, buttonSize, Image.SCALE_SMOOTH); // ridimensionamento dell'immagine
            itemButtons[selectedCount].setIcon(new ImageIcon(scaledImage));
            //itemButtons[selectedCount]=button;
            //itemButtons[selectedCount].setText(label);
           // itemButtons[selectedCount].addActionListener(e -> buttonClicked(label));
            //JOptionPane.showMessageDialog(this, "E stato inserito il primo");
            selectedCount++;
        }else{
            JOptionPane.showMessageDialog(this, "Non puoi selezionarne più di 3");
        }
    }
    public void addButton(String label,JButton button) {
        if (selectedCount < itemButtons.length - 2) {
            itemButtons[selectedCount]=button;
            //itemButtons[selectedCount].setText(label);
            itemButtons[selectedCount].addActionListener(e -> buttonClicked(label));
            JOptionPane.showMessageDialog(this, "E stato inserito il primo");
            selectedCount++;
        }else{
            JOptionPane.showMessageDialog(this, "Non puoi selezionarne più di 3");
        }
    }

    private void buttonClicked(String label) {
        System.out.println("Button " + label + " clicked");
    }

    private void confirmChoice() {
        System.out.println("Choice confirmed");
    }

    private void resetChoice() {
        for (JButton button : itemButtons) {
            if (button != null) {
                button.setText("");
            }
        }
        lastIndex = 0;
        System.out.println("Choice reset");
    }
}

     */





/*
public class ChoicePanel extends JPanel {

    private JButton[] itemButtons;
    private JButton confirmChoiceButton;
    private JButton resetChoiceButton;
    private int[] selectedButtons = new int[3];

    public ChoicePanel(BoardBoxView[][] boardBoxViews) {
        super(new FlowLayout(FlowLayout.LEFT));

        // creazione dei bottoni selezionabili
        itemButtons = new JButton[3];
        for (int i = 0; i < itemButtons.length; i++) {
            itemButtons[i] = new JButton(new ImageIcon("path/to/image" + i + ".png"));
            //itemButtons[i] = new JButton(new ImageIcon(".\\src\\main\\java\\org\\example\\Images\\Item tiles\\"+itemTileViews[i].getType()+" "+itemTileViews[i].getId()+".png"));
            itemButtons[i].setPreferredSize(new Dimension(50, 50));
            // aggiunta di un listener per spostare il bottone selezionato sopra alla lista dei selezionati
            itemButtons[i].addActionListener(e -> {
                JButton selectedButton = (JButton) e.getSource();
                /*
                int selectedIndex = indexOf(itemButtons, selectedButton);
                if (indexOf(selectedButtons, boardBoxViews[selectedIndex].getId()) != -1) { // controlla che il bottone selezionato sia nella lista dei selezionati
                    selectedButtons[indexOf(selectedButtons, boardBoxViews[selectedIndex].getId())] = 0; // deseleziona il bottone
                    resetItemButtons(); // aggiorna graficamente la ChoicePanel
                }


            });
            add(itemButtons[i]);
        }

                    */
        /*
        for (int i = 0; i < 6; i++) {
            itemButtons[i] = new JButton(new ImageIcon("path/to/image" + i + ".png"));
            itemButtons[i].setPreferredSize(new Dimension(50, 50));
            // aggiunta di un listener per spostare il bottone selezionato sopra alla lista dei selezionati
            itemButtons[i].addActionListener(e -> {
                JButton selectedButton = (JButton) e.getSource();
                int selectedIndex = indexOf(itemButtons, selectedButton);
                moveItemToTop(selectedIndex);
            });
            add(itemButtons[i]);
        }



        // creazione dei bottoni per conferma/reset della scelta
        confirmChoiceButton = new JButton("Conferm Choice");
        confirmChoiceButton.addActionListener(e -> {
            JOptionPane.showMessageDialog(this, "Bravo hai selezionato le tessere!");
        });
        resetChoiceButton = new JButton("Reset Choice");
        resetChoiceButton.addActionListener(e -> {
            resetItemButtons();
        });

        // aggiunta dei bottoni conferma/reset in alto a destra
        JPanel confirmPanel = new JPanel(new GridLayout(2, 1, 5, 5));
        confirmPanel.add(confirmChoiceButton);
        confirmPanel.add(resetChoiceButton);
        add(confirmPanel, BorderLayout.EAST);
    }

    private int indexOf(JButton[] buttons, JButton button) {
        for (int i = 0; i < buttons.length; i++) {
            if (buttons[i] == button) {
                return i;
            }
        }
        return -1;
    }
    public void moveItemToTop(int index,JButton button) {
        itemButtons[0]=button;
        revalidate();
        repaint();

        /*remove(itemButtons[index]);
        add(itemButtons[index], 0);
        revalidate();
        repaint();


    }
            */
    /*
    private void moveItemToTop(int index) {
        remove(itemButtons[index]);
        add(itemButtons[index], 0);
        revalidate();
        repaint();
    }



    private void resetItemButtons() {
        for (int i = 0; i < 6; i++) {
            remove(itemButtons[i]);
            add(itemButtons[i]);
        }
        revalidate();
        repaint();
    }
    public void addSelectedItem(int buttonId,JButton button) {
        moveItemToTop(0,button);
        /*
        for (int i = 0; i < selectedButtons.length; i++) {
            if (selectedButtons[i] == 0) {
                selectedButtons[i] = buttonId;
                updateChoicePanel(); // aggiorna la ChoicePanel con il nuovo bottone selezionato
                break;
            }
        }


    }
    private void updateChoicePanel() {


        //moveItemToTop(itemButtons[j]);
        /*
        for (int i = 0; i < selectedButtons.length; i++) {
            if (selectedButtons[i] != 0) {

                // cerca il bottone corrispondente all'ID selezionato
                for (int j = 0; j < itemButtons.length; j++) {
                    if (itemTileViews[j].getId() == selectedButtons[i]) {
                        // sposta il bottone in prima posizione nella ChoicePanel

                    }
                }


            }
        }

         */

    /*
    private void moveItemToTop(int index) {
        if(selectedCount < 3){
            remove(itemButtons[index]);
            add(itemButtons[index], 0);
            revalidate();
            repaint();
            selectedCount++;
            if(selectedCount == 3){
                limitReached = true; // limite raggiunto
                disableRemainingButtons();
            }
        } else {
            MessageManager messageManager = new MessageManager();
            messageManager.showError("Hai già selezionato il massimo numero di tessere.");
        }
    }


    private int countSelectedButtons() {
        int count = 0;
        for (int i = 0; i < selectedButtons.length; i++) {
            if (selectedButtons[i] != 0) {
                count++;
            }
        }
        return count;
    }

    private void addSelectedItem(int buttonId) {
        for (int i = 0; i < selectedButtons.length; i++) {
            if (selectedButtons[i] == 0) {
                selectedButtons[i] = buttonId;
                updateChoicePanel(); // aggiorna la ChoicePanel con il nuovo bottone selezionato
                break;
            }
        }
    }
    private void updateChoicePanel() {
        for (int i = 0; i < selectedButtons.length; i++) {
            if (selectedButtons[i] != 0) {

                // cerca il bottone corrispondente all'ID selezionato
                for (int j = 0; j < itemButtons.length; j++) {
                    if (itemTileViews[j].getId() == selectedButtons[i]) {
                        // sposta il bottone in prima posizione nella ChoicePanel
                        moveItemToTop(itemButtons[j]);
                    }
                }


            }
        }
    }
}
            */
    /*
    private void moveItemToTop(int index) {
        remove(itemButtons[index]);
        add(itemButtons[index], 0);
        revalidate();
        repaint();
    }



    private void resetItemButtons() {
        for (int i = 0; i < 6; i++) {
            remove(itemButtons[i]);
            add(itemButtons[i]);
        }
        revalidate();
        repaint();
    }
    public void addSelectedItem(int buttonId,JButton button) {
        moveItemToTop(0,button);
        /*
        for (int i = 0; i < selectedButtons.length; i++) {
            if (selectedButtons[i] == 0) {
                selectedButtons[i] = buttonId;
                updateChoicePanel(); // aggiorna la ChoicePanel con il nuovo bottone selezionato
                break;
            }
        }


    }
    private void updateChoicePanel() {


        //moveItemToTop(itemButtons[j]);
        /*
        for (int i = 0; i < selectedButtons.length; i++) {
            if (selectedButtons[i] != 0) {

                // cerca il bottone corrispondente all'ID selezionato
                for (int j = 0; j < itemButtons.length; j++) {
                    if (itemTileViews[j].getId() == selectedButtons[i]) {
                        // sposta il bottone in prima posizione nella ChoicePanel

                    }
                }


            }
        }

         */

    /*
    private void moveItemToTop(int index) {
        if(selectedCount < 3){
            remove(itemButtons[index]);
            add(itemButtons[index], 0);
            revalidate();
            repaint();
            selectedCount++;
            if(selectedCount == 3){
                limitReached = true; // limite raggiunto
                disableRemainingButtons();
            }
        } else {
            MessageManager messageManager = new MessageManager();
            messageManager.showError("Hai già selezionato il massimo numero di tessere.");
        }
    }


    private int countSelectedButtons() {
        int count = 0;
        for (int i = 0; i < selectedButtons.length; i++) {
            if (selectedButtons[i] != 0) {
                count++;
            }
        }
        return count;
    }

    private void addSelectedItem(int buttonId) {
        for (int i = 0; i < selectedButtons.length; i++) {
            if (selectedButtons[i] == 0) {
                selectedButtons[i] = buttonId;
                updateChoicePanel(); // aggiorna la ChoicePanel con il nuovo bottone selezionato
                break;
            }
        }
    }
    private void updateChoicePanel() {
        for (int i = 0; i < selectedButtons.length; i++) {
            if (selectedButtons[i] != 0) {

                // cerca il bottone corrispondente all'ID selezionato
                for (int j = 0; j < itemButtons.length; j++) {
                    if (itemTileViews[j].getId() == selectedButtons[i]) {
                        // sposta il bottone in prima posizione nella ChoicePanel
                        moveItemToTop(itemButtons[j]);
                    }
                }


            }
        }
    }
}

public class ChoicePanel extends JPanel {
    private JButton[] itemButtons;
    private JButton confirmChoiceButton;
    private JButton resetChoiceButton;
    private int selectedCount;
    private boolean limitReached;

    public ChoicePanel() {
        super(new FlowLayout(FlowLayout.LEFT));

        // creazione dei bottoni selezionati
        itemButtons = new JButton[3];
        for (int i = 0; i < 3; i++) {
            itemButtons[i] = new JButton();
            itemButtons[i].setPreferredSize(new Dimension(50, 50));
        }

        confirmChoiceButton = new JButton("Conferma Scelta");
        confirmChoiceButton.addActionListener(e -> {
            if (limitReached) {
                JOptionPane.showMessageDialog(this, "Hai già selezionato il massimo numero di tessere.");
            } else {
                JOptionPane.showMessageDialog(this, "Bravo hai selezionato le tessere!");
            }
        });

        resetChoiceButton = new JButton("Resetta Scelta");
        resetChoiceButton.addActionListener(e -> {
            resetItemButtons();
            SelectionBoardPanel parentPanel = (SelectionBoardPanel) ChoicePanel.this.getParent();
            parentPanel.enableRemainingButtons();
        });

        JPanel confirmPanel = new JPanel(new GridLayout(2, 1, 5, 5));
        confirmPanel.add(confirmChoiceButton);
        confirmPanel.add(resetChoiceButton);
        add(confirmPanel, BorderLayout.EAST);
    }

    public void addButtonToSelected(JButton button) {
        if (selectedCount < 3) {
            JButton selectedButton = getFirstEmptyButton();
            selectedButton.setIcon(button.getIcon());
            moveItemToTop(selectedButton);
            selectedCount++;
            if (selectedCount == 3) {
                limitReached = true;
                disableRemainingButtons();
            }
        }
    }

    public void removeButtonFromSelected(JButton button) {
        for (JButton selectedButton : itemButtons) {
            if (selectedButton.getIcon() == button.getIcon()) {
                selectedButton.setIcon(null);
                selectedCount--;
                limitReached = false;
                enableRemainingButtons();
                return;
            }
        }
    }

    private JButton getFirstEmptyButton() {
        for (JButton selectedButton : itemButtons) {
            if (selectedButton.getIcon() == null)
                return selectedButton;
        }
        return null;
    }

    private void moveItemToTop(JButton selectedButton) {
        remove(selectedButton);
        add(selectedButton, 0);
        revalidate();
        repaint();
    }

    private void resetItemButtons() {
        for (JButton selectedButton : itemButtons) {
            selectedButton.setIcon(null);
        }
        selectedCount = 0;
        limitReached = false;
        enableRemainingButtons();
        revalidate();
        repaint();
    }

    private void disableRemainingButtons() {
        BoardBoxPanel boardBox = SelectionBoardPanel.this.boardBoxPanel;
        for (JButton boardButton : boardBox.itemButtons) {
            if (boardButton.isEnabled()) {
                boardButton.setEnabled(false);
            }
        }
    }

    private void enableRemainingButtons() {
        BoardBoxPanel boardBox = SelectionBoardPanel.this.boardBoxPanel;
        for (JButton boardButton : boardBox.itemButtons) {
            if (!boardButton.isEnabled()) {
                boardButton.setEnabled(true);
            }
        }
    }
}





/*
public class ChoicePanel extends JPanel {

    private JButton[] itemButtons;
    private JButton confirmChoiceButton;
    private JButton resetChoiceButton;
    private int selectedCount;
    private boolean limitReached;

    public ChoicePanel() {
        super(new FlowLayout(FlowLayout.LEFT));

// creazione dei bottoni selezionabili
        itemButtons = new JButton[3];
        for (int i = 0; i < 3; i++) {
            itemButtons[i] = new JButton(new ImageIcon("path/to/image" + i + ".png"));
            itemButtons[i].setPreferredSize(new Dimension(50, 50));
            // aggiunta di un listener per spostare il bottone selezionato sopra alla lista dei selezionati
            itemButtons[i].addActionListener(e -> {
                if (selectedCount < 3) {
                    JButton selectedButton = (JButton) e.getSource();
                    //int selectedIndex = indexOf(itemButtons, selectedButton);
                    moveItemToTop(0);
                    selectedCount++;
                    if (selectedCount == 3) {
                        limitReached = true; // limite raggiunto
                    }
                } else {
                    JOptionPane.showMessageDialog(this, "Hai già selezionato il massimo numero di tessere.");
                }
            });
            confirmChoiceButton.addActionListener(e -> {
                if(limitReached){
                    JOptionPane.showMessageDialog(this, "Hai selezionato il massimo numero di tessere.");
                } else {
                    JOptionPane.showMessageDialog(this, "Bravo hai selezionato le tessere!");
                }
            });

            /*
            itemButtons[i].addActionListener(e -> {
                JButton selectedButton = (JButton) e.getSource();
                int selectedIndex = indexOf(itemButtons, selectedButton);
                moveItemToTop(selectedIndex);
            });
            add(itemButtons[i]);
        }



// creazione dei bottoni per conferma/reset della scelta
            confirmChoiceButton = new JButton("Conferm Choice");
            confirmChoiceButton.addActionListener(e -> {
                JOptionPane.showMessageDialog(this, "Bravo hai selezionato le tessere!");
            });
            resetChoiceButton = new JButton("Reset Choice");
            resetChoiceButton.addActionListener(e -> {
                resetItemButtons();
            });

// aggiunta dei bottoni conferma/reset in alto a destra
            JPanel confirmPanel = new JPanel(new GridLayout(2, 1, 5, 5));
            confirmPanel.add(confirmChoiceButton);
            confirmPanel.add(resetChoiceButton);
            add(confirmPanel, BorderLayout.EAST);
        }
    }
    public void addButtonToSelected(JButton button) {
        if (selectedCount < 3) {
            JButton selectedButton = new JButton();
            selectedButton.setIcon(button.getIcon());
            selectedButton.setPreferredSize(new Dimension(50, 50));
           // itemButtons[0]=selectedButton;

            moveItemToTop(add(selectedButton, 0));
            //moveItemToTop(add(selectedButton, 0));
            selectedCount++;
            if (selectedCount == 3) {
                limitReached = true;
                disableRemainingButtons();
            }
        }
    }

    private int indexOf(JButton[] buttons, JButton button) {
        for (int i = 0; i < buttons.length; i++) {
            if (buttons[i] == button) {
                return i;
            }
        }
        return -1;
    }

    private void moveItemToTop(Component index) {
        remove(itemButtons[index]);
        add(itemButtons[index], 0);
        revalidate();
        repaint();
    }

    private void resetItemButtons() {
        for (int i = 0; i < 6; i++) {
            remove(itemButtons[i]);
            add(itemButtons[i]);
        }
        revalidate();
        repaint();
    }
    public void disableRemainingButtons(){
        if(selectedCount == 3){
            for (int i = 0; i < itemButtons.length; i++) {
                if(!itemButtons[i].isEnabled()){
                    itemButtons[i].setEnabled(false);
                }
            }
        }
    }
    public void enableRemainingButtons(){
        for (int i = 0; i < itemButtons.length; i++) {
            if(!itemButtons[i].isEnabled()){
                itemButtons[i].setEnabled(true);
            }
        }
    }

}


/*
public class ChoicePanel extends JPanel {

    private JButton[] itemButtons;
    private JButton confirmChoiceButton;
    private JButton resetChoiceButton;
    private int[] selectedButtons = new int[3];

    public ChoicePanel(BoardBoxView[][] boardBoxViews) {
        super(new FlowLayout(FlowLayout.LEFT));

        // creazione dei bottoni selezionabili
        itemButtons = new JButton[3];
        for (int i = 0; i < itemButtons.length; i++) {
            itemButtons[i] = new JButton(new ImageIcon("path/to/image" + i + ".png"));
            //itemButtons[i] = new JButton(new ImageIcon(".\\src\\main\\java\\org\\example\\Images\\Item tiles\\"+itemTileViews[i].getType()+" "+itemTileViews[i].getId()+".png"));
            itemButtons[i].setPreferredSize(new Dimension(50, 50));
            // aggiunta di un listener per spostare il bottone selezionato sopra alla lista dei selezionati
            itemButtons[i].addActionListener(e -> {
                JButton selectedButton = (JButton) e.getSource();
                /*
                int selectedIndex = indexOf(itemButtons, selectedButton);
                if (indexOf(selectedButtons, boardBoxViews[selectedIndex].getId()) != -1) { // controlla che il bottone selezionato sia nella lista dei selezionati
                    selectedButtons[indexOf(selectedButtons, boardBoxViews[selectedIndex].getId())] = 0; // deseleziona il bottone
                    resetItemButtons(); // aggiorna graficamente la ChoicePanel
                }


            });
            add(itemButtons[i]);
        }

                    */
        /*
        for (int i = 0; i < 6; i++) {
            itemButtons[i] = new JButton(new ImageIcon("path/to/image" + i + ".png"));
            itemButtons[i].setPreferredSize(new Dimension(50, 50));
            // aggiunta di un listener per spostare il bottone selezionato sopra alla lista dei selezionati
            itemButtons[i].addActionListener(e -> {
                JButton selectedButton = (JButton) e.getSource();
                int selectedIndex = indexOf(itemButtons, selectedButton);
                moveItemToTop(selectedIndex);
            });
            add(itemButtons[i]);
        }



        // creazione dei bottoni per conferma/reset della scelta
        confirmChoiceButton = new JButton("Conferm Choice");
        confirmChoiceButton.addActionListener(e -> {
            JOptionPane.showMessageDialog(this, "Bravo hai selezionato le tessere!");
        });
        resetChoiceButton = new JButton("Reset Choice");
        resetChoiceButton.addActionListener(e -> {
            resetItemButtons();
        });

        // aggiunta dei bottoni conferma/reset in alto a destra
        JPanel confirmPanel = new JPanel(new GridLayout(2, 1, 5, 5));
        confirmPanel.add(confirmChoiceButton);
        confirmPanel.add(resetChoiceButton);
        add(confirmPanel, BorderLayout.EAST);
    }

    private int indexOf(JButton[] buttons, JButton button) {
        for (int i = 0; i < buttons.length; i++) {
            if (buttons[i] == button) {
                return i;
            }
        }
        return -1;
    }
    public void moveItemToTop(int index,JButton button) {
        itemButtons[0]=button;
        revalidate();
        repaint();

        /*remove(itemButtons[index]);
        add(itemButtons[index], 0);
        revalidate();
        repaint();


    }
            */
    /*
    private void moveItemToTop(int index) {
        remove(itemButtons[index]);
        add(itemButtons[index], 0);
        revalidate();
        repaint();
    }



    private void resetItemButtons() {
        for (int i = 0; i < 6; i++) {
            remove(itemButtons[i]);
            add(itemButtons[i]);
        }
        revalidate();
        repaint();
    }
    public void addSelectedItem(int buttonId,JButton button) {
        moveItemToTop(0,button);
        /*
        for (int i = 0; i < selectedButtons.length; i++) {
            if (selectedButtons[i] == 0) {
                selectedButtons[i] = buttonId;
                updateChoicePanel(); // aggiorna la ChoicePanel con il nuovo bottone selezionato
                break;
            }
        }


    }
    private void updateChoicePanel() {


        //moveItemToTop(itemButtons[j]);
        /*
        for (int i = 0; i < selectedButtons.length; i++) {
            if (selectedButtons[i] != 0) {

                // cerca il bottone corrispondente all'ID selezionato
                for (int j = 0; j < itemButtons.length; j++) {
                    if (itemTileViews[j].getId() == selectedButtons[i]) {
                        // sposta il bottone in prima posizione nella ChoicePanel

                    }
                }


            }
        }

         */

    /*
    private void moveItemToTop(int index) {
        if(selectedCount < 3){
            remove(itemButtons[index]);
            add(itemButtons[index], 0);
            revalidate();
            repaint();
            selectedCount++;
            if(selectedCount == 3){
                limitReached = true; // limite raggiunto
                disableRemainingButtons();
            }
        } else {
            MessageManager messageManager = new MessageManager();
            messageManager.showError("Hai già selezionato il massimo numero di tessere.");
        }
    }


    private int countSelectedButtons() {
        int count = 0;
        for (int i = 0; i < selectedButtons.length; i++) {
            if (selectedButtons[i] != 0) {
                count++;
            }
        }
        return count;
    }

    private void addSelectedItem(int buttonId) {
        for (int i = 0; i < selectedButtons.length; i++) {
            if (selectedButtons[i] == 0) {
                selectedButtons[i] = buttonId;
                updateChoicePanel(); // aggiorna la ChoicePanel con il nuovo bottone selezionato
                break;
            }
        }
    }
    private void updateChoicePanel() {
        for (int i = 0; i < selectedButtons.length; i++) {
            if (selectedButtons[i] != 0) {

                // cerca il bottone corrispondente all'ID selezionato
                for (int j = 0; j < itemButtons.length; j++) {
                    if (itemTileViews[j].getId() == selectedButtons[i]) {
                        // sposta il bottone in prima posizione nella ChoicePanel
                        moveItemToTop(itemButtons[j]);
                    }
                }


            }
        }
    }
}

     */

