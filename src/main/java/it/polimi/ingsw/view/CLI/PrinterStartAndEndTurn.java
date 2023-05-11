package it.polimi.ingsw.view.CLI;

import it.polimi.ingsw.network.client.ClientHandler;
import it.polimi.ingsw.view.ClientView;

import java.io.PrintStream;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.Scanner;

import static java.lang.System.out;

public class PrinterStartAndEndTurn extends ClientHandler {
    private final Scanner in;
    private static PrintStream out;
    public PrinterStartAndEndTurn(){
       super();
       this.in = new Scanner(System.in);
       this.out = new PrintStream(System.out,true);
    }

//TODO aggiungere logo
    public void initialLobby(){
        printMyShelfieLogo();
        doConnection();
    }

    private void doConnection(){
        int connectionType = -1;

        String nickname = askNickname();
        out.println("Hi "+ nickname +"!");

        connectionType = askConnection();
        if (connectionType == 0) {
            out.println("You chose Socket connection\n");
        } else if (connectionType == 1){
            out.println("You chose RMI connection\n");
        } else {
            out.println("Invalid connection");
            doConnection();
        }

        String ip = askIp();
        int port = askPort(connectionType);

        out.println("Server Ip Address: " + ip);
        out.println("Server Port: " + port + "\n");

        try{
            //metodo di Clienthanlder (la cli estende ClientHandler)
            createConnection(connectionType, nickname, ip, port);
            out.println("Connection created");
        } catch (Exception e){
            out.println("Error in creating connection. Please try again.\n");
            doConnection();
        }

    }

    private String askNickname(){
        Colors.colorize(Colors.RED_CODE,"Enter your username: ");
        return in.nextLine().toLowerCase();
    }

    private int askConnection(){
        int connectionType = -1;
        do{
            out.println("Enter your connection type: (0 for Socket, 1 for RMI) ");
            connectionType = in.nextInt();
            in.nextLine(); // aggiungo questa riga per consumare il carattere di fine riga rimanente
        } while (connectionType != 0 && connectionType != 1);
        return connectionType;
    }
    //TODO questo i stess come quello sotto poi ci accordiamo su dove metterlo
    private String askIp() {
        String defaultIp = "127.0.0.1";
        out.println("Enter the server Ip Address (default " + defaultIp + "): (press Enter button to choose default)");
        in.reset();

        do {
            if (in.hasNextLine()) {
                String line = in.nextLine();

                if (line.equals("")) {
                    return defaultIp;
                } else {
                    try {
                        InetAddress address = InetAddress.getByName(line);
                        return address.getHostAddress();
                    } catch (UnknownHostException e) {
                        out.println("Invalid IP address. Please enter a valid IP address or press Enter to choose the default.");
                    }
                }
            } else {
                in.nextLine();
                out.println("Invalid input. Please enter a valid IP address or press Enter to choose the default.");
            }
        } while (true);
    }
    //TODO poi questa funzione la spostamo sulla CLI ho il metodo che crea la connessione chiede il soprannome, crea gli handler...
    private int askPort(int connectionType) {
        int defaultPort = (connectionType == 0 ? 51634 : 51633);
        out.println("Enter the server port (default " + defaultPort + "): (press Enter button to choose default)");
        in.reset();

        do {
            if (in.hasNextLine()) {
                String line = in.nextLine();

                if (line.equals("")) {
                    return defaultPort;
                } else {
                    try {
                        int port = Integer.parseInt(line);
                        if (port >= 1024 && port <= 65535) {
                            return port;
                        } else {
                            out.println("Invalid port number. Please enter a port number between 1024 and 65535.");
                        }
                    } catch (NumberFormatException e) {
                        out.println("Invalid input. Please enter a valid port number.");
                    }
                }
            } else {
                in.nextLine();
                out.println("Invalid input. Please enter a valid port number.");
            }
        } while (true);
    }

    private static void printMyShelfieLogo() {
        String myShelfieLogo = """


                                                           ███╗   ███╗██╗   ██╗    ███████╗██╗  ██╗███████╗██╗     ███████╗██╗███████╗
                                                           ████╗ ████║╚██╗ ██╔╝    ██╔════╝██║  ██║██╔════╝██║     ██╔════╝██║██╔════╝
                                                           ██╔████╔██║ ╚████╔╝     ███████╗███████║█████╗  ██║     █████╗  ██║█████╗ \s
                                                           ██║╚██╔╝██║  ╚██╔╝      ╚════██║██╔══██║██╔══╝  ██║     ██╔══╝  ██║██╔══╝ \s
                                                           ██║ ╚═╝ ██║   ██║       ███████║██║  ██║███████╗███████╗██║     ██║███████╗
                                                           ╚═╝     ╚═╝   ╚═╝       ╚══════╝╚═╝  ╚═╝╚══════╝╚══════╝╚═╝     ╚═╝╚══════╝
                                                                                          \s
                                                                                                                      \s
                                                   Welcome to MyShelfie Board Game made by Andrea Rondi, Giulia Rossi, Samuele Russo and Xin Ye.🐈‍⬛🪴📚🏆🧩🖼️🐈◼︎
                                                                                                                      \s
                                                                                                                      \s
                                                                                                                      \s
                Before starting playing you need to setup some things:
                """;

        out.println(myShelfieLogo);
    }
    public void endGame(ClientView clientView){
        //TODO stampare la classifica e nel caso aggiugo altri dati che servono nel messaggio



    }
    public void finishTurn(){
        //TODO magari stampare un disegno di fine turno che puo vedere il giocatore in attesa, comunque lascio la possibilita all utente
        //TODO di guardare le regole, la bookshelf....
    }
    public void rulesGame(){
        //TODO stampere tutte le regole del gioco quindi i punteggi delle Personal, regole di selezione delle tessere...
    }




}
