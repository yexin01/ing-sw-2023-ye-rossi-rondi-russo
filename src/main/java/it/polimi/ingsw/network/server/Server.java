package it.polimi.ingsw.network.server;

import it.polimi.ingsw.json.GameRules;
import it.polimi.ingsw.message.*;

import java.io.IOException;
import java.util.*;
import java.util.concurrent.*;
import java.util.stream.Collectors;

/**
 * Server class that handles the connection with the clients.
 */
public class Server implements Runnable{
    private final Object clientsLock = new Object();
    private static int rmiPort;
    private static int socketPort;
    private static String ipAddress = null;

    private static Server instance = null;
    private RMIServer rmiServer;
    private SocketServer socketServer;

    private ExecutorService executor;

    private ConcurrentHashMap<String, Connection> clientsConnected; //map of all the clients with an active connection, for the disconnected ones there is the list of disconnected in the gameLobby
    private GlobalLobby globalLobby;

    private static int MAX_PLAYERS;
    private static int MIN_PLAYERS;
    static int MAX_LENGTH_NICKNAME;
    static int MIN_LENGTH_NICKNAME;

    /**
     * Constructor of the server
     * @param rmiPort port for the rmi connection
     * @param socketPort port for the socket connection
     * @param ipAddress ip address of the server
     */
    public Server(int rmiPort, int socketPort, String ipAddress) {
        GameRules gameRules;
        try {
            gameRules = new GameRules();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        MAX_PLAYERS= gameRules.getMaxPlayers();
        MIN_PLAYERS= gameRules.getMinPlayers();

        MAX_LENGTH_NICKNAME= gameRules.getMaxCharactersPlayers();
        MIN_LENGTH_NICKNAME= gameRules.getMinCharactersPlayers();
        // it checks if there is already an instance of the server
        if (instance != null) {
            return;
        }
        Server.rmiPort = rmiPort;
        Server.socketPort = socketPort;
        synchronized (clientsLock) {
            this.clientsConnected = new ConcurrentHashMap<>();
        }
        startServers(ipAddress);
        executor = new ThreadPoolExecutor(4, 20, 0L, TimeUnit.MILLISECONDS, new LinkedBlockingQueue<>());
        globalLobby = new GlobalLobby();
    }

    /**
     * Method to set the ip address of the server
     * @param ipAddress ip address of the server
     */
    private static void setIpAddress(String ipAddress) {
        Server.ipAddress = ipAddress;
    }

    public String getIpAddress() {
        return Server.ipAddress;
    }

    /*
    Per avviare il server da terminale macbook: (da cambiare il path in base alla posizione del progetto):
    
    cd ~/Desktop/prog_sw/progsw_ingsw2023/ing-sw-2023-ye-rossi-rondi-russo
    mvn clean install
    javac src/main/java/it/polimi/ingsw/network/server/Server.java
    java src/main/java/it/polimi/ingsw/network/server/Server 6000 7000 192.168.1.100

     */
    //TODO: da fare il jar per lanciare da terminale windows e mac
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        int portRmi = 1099;
        int portSocket = 1100;
        String ipAddress = "127.0.0.1";
//TODO verrà cancellato, provo a vedere con il jar quali caratteri vengono visualizzati sul terminale
        boolean correctInput = false;
        System.out.println("" +
                "alt 1      ☺       \n" +
                "  alt 2      ☻       \n" +
                "  alt 3      ♥       \n" +
                "  alt 4      ♦       \n" +
                "  alt 5      ♣       \n" +
                "  alt 6      ♠       \n" +
                "  alt 7      •       \n" +
                "  alt 8      ◘       \n" +
                "  alt 9      ○       \n" +
                "  alt 10     ◙       \n" +
                "  alt 11     ♂       \n" +
                "  alt 12     ♀       \n" +
                "  alt 13     ♪       \n" +
                "  alt 14     ♫       \n" +
                "  alt 15     ☼       \n" +
                "  alt 16     ►       \n" +
                "  alt 17     ◄       \n" +
                "  alt 18     ↕       \n" +
                "  alt 19     ‼       \n" +
                "  alt 20     ¶       \n" +
                "  alt 21     §       \n" +
                "  alt 22     ▬       \n" +
                "  alt 23     ↨       \n" +
                "  alt 24     ↑       \n" +
                "  alt 25     ↓       \n" +
                "  alt 26     →       \n" +
                "  alt 27     ←       \n" +
                "  alt 28     ∟       \n" +
                "  alt 29     ↔       \n" +
                "  alt 30     ▲       \n" +
                "  alt 31     ▼       \n" +
                "  alt 32             \n" +
                "  alt 33     !       \n" +
                "  alt 34     \"       \n" +
                "  alt 35     #       \n" +
                "  alt 36     $       \n" +
                "  alt 37     %       \n" +
                "  alt 38     &       \n" +
                "  alt 39     '       \n" +
                "  alt 40     (       \n" +
                "  alt 41     )       \n" +
                "  alt 42     *       \n" +
                "  alt 43     +       \n" +
                "  alt 44     ,       \n" +
                "  alt 45     -       \n" +
                "  alt 46     .       \n" +
                "  alt 47     /       \n" +
                "  alt 48     0       \n" +
                "  alt 49     1       \n" +
                "  alt 50     2       \n" +
                "  alt 51     3       \n" +
                "  alt 52     4       \n" +
                "  alt 53     5       \n" +
                "  alt 54     6       \n" +
                "  alt 55     7       \n" +
                "  alt 56     8       \n" +
                "  alt 57     9       \n" +
                "  alt 58     :       \n" +
                "  alt 59     ;       \n" +
                "  alt 60     <       \n" +
                "  alt 61     =       \n" +
                "  alt 62     >       \n" +
                "  alt 63     ?       \n" +
                "  alt 64     @       \n" +
                "  alt 65     A       \n" +
                "  alt 66     B       \n" +
                "  alt 67     C       \n" +
                "  alt 68     D       \n" +
                "  alt 69     E       \n" +
                "  alt 70     F       \n" +
                "  alt 71     G       \n" +
                "  alt 72     H       \n" +
                "  alt 73     I       \n" +
                "  alt 74     J       \n" +
                "  alt 75     K       \n" +
                "  alt 76     L       \n" +
                "  alt 77     M       \n" +
                "  alt 78     N       \n" +
                "  alt 79     O       \n" +
                "  alt 80     P       \n" +
                "  alt 81     Q       \n" +
                "  alt 82     R       \n" +
                "  alt 83     S       \n" +
                "  alt 84     T       \n" +
                "  alt 85     U       \n" +
                "  alt 86     V       \n" +
                "  alt 87     W       \n" +
                "  alt 88     X       \n" +
                "  alt 89     Y       \n" +
                "  alt 90     Z       \n" +
                "  alt 91     [       \n" +
                "  alt 91     [       \n" +
                "  alt 92     \\       \n" +
                "  alt 92     \\       \n" +
                "  alt 93     ]       \n" +
                "  alt 93     ]       \n" +
                "  alt 94     ^       \n" +
                "  alt 95     _       \n" +
                "  alt 96     `       \n" +
                "  alt 97     a       \n" +
                "  alt 98     b       \n" +
                "  alt 99     c       \n" +
                "  alt 100    d       \n" +
                "  alt 101    e       \n" +
                "  alt 102    f       \n" +
                "  alt 103    g       \n" +
                "  alt 104    h       \n" +
                "  alt 105    i       \n" +
                "  alt 106    j       \n" +
                "  alt 107    k       \n" +
                "  alt 108    l       \n" +
                "  alt 109    m       \n" +
                "  alt 110    n       \n" +
                "  alt 111    o       \n" +
                "  alt 112    p       \n" +
                "  alt 113    q       \n" +
                "  alt 114    r       \n" +
                "  alt 115    s       \n" +
                "  alt 116    t       \n" +
                "  alt 117    u       \n" +
                "  alt 118    v       \n" +
                "  alt 119    w       \n" +
                "  alt 120    x       \n" +
                "  alt 121    y       \n" +
                "  alt 122    z       \n" +
                "  alt 123    {       \n" +
                "  alt 124    |       \n" +
                "  alt 125    }       \n" +
                "  alt 126    ~       \n" +
                "  alt 127    ⌂       \n" +
                "  alt 155    ¢       \n" +
                "  alt 156    £       \n" +
                "  alt 157    ¥       \n" +
                "  alt 158    ₧       \n" +
                "  alt 159    ƒ       \n" +
                "  alt 164    ñ       \n" +
                "  alt 165    Ñ       \n" +
                "  alt 166    ª       \n" +
                "  alt 167    º       \n" +
                "  alt 168    ¿       \n" +
                "  alt 169    ®       \n" +
                "  alt 170    ¬       \n" +
                "  alt 171    ½       \n" +
                "  alt 172    ¼       \n" +
                "  alt 173    ¡       \n" +
                "  alt 174    «       \n" +
                "  alt 175    »       \n" +
                "  alt 176    ░       \n" +
                "  alt 177    ▒       \n" +
                "  alt 178    ▓       \n" +
                "  alt 179    │       \n" +
                "  alt 180    ┤       \n" +
                "  alt 181    ╡       \n" +
                "  alt 182    ╢       \n" +
                "  alt 183    ╖       \n" +
                "  alt 184    ╕       \n" +
                "  alt 185    ╣       \n" +
                "  alt 186    ║       \n" +
                "  alt 187    ╗       \n" +
                "  alt 188    ╝       \n" +
                "  alt 189    ╜       \n" +
                "  alt 190    ╛       \n" +
                "  alt 191    ┐       \n" +
                "  alt 192    └       \n" +
                "  alt 193    ┴       \n" +
                "  alt 194    ┬       \n" +
                "  alt 195    ├       \n" +
                "  alt 196    ─       \n" +
                "  alt 197    ┼       \n" +
                "  alt 198    ╞       \n" +
                "  alt 199    ╟       \n" +
                "  alt 200    ╚       \n" +
                "  alt 201    ╔       \n" +
                "  alt 202    ╩       \n" +
                "  alt 203    ╦       \n" +
                "  alt 204    ╠       \n" +
                "  alt 205    ═       \n" +
                "  alt 206    ╬       \n" +
                "  alt 207    ╧       \n" +
                "  alt 208    ╨       \n" +
                "  alt 209    ╤       \n" +
                "  alt 210    ╥       \n" +
                "  alt 211    ╙       \n" +
                "  alt 212    ╘       \n" +
                "  alt 213    ╒       \n" +
                "  alt 214    ╓       \n" +
                "  alt 215    ╫       \n" +
                "  alt 216    ╪       \n" +
                "  alt 217    ┘       \n" +
                "  alt 218    ┌       \n" +
                "  alt 219    █       \n" +
                "  alt 220    ▄       \n" +
                "  alt 221    ▌       \n" +
                "  alt 222    ▐       \n" +
                "  alt 223    ▀       \n" +
                "  alt 224    α       \n" +
                "  alt 225    ß       \n" +
                "  alt 226    Γ       \n" +
                "  alt 227    π       \n" +
                "  alt 228    Σ       \n" +
                "  alt 229    σ       \n" +
                "  alt 230    µ       \n" +
                "  alt 231    τ       \n" +
                "  alt 232    Φ       \n" +
                "  alt 233    Θ       \n" +
                "  alt 234    Ω       \n" +
                "  alt 235    δ       \n" +
                "  alt 236    ∞       \n" +
                "  alt 237    φ       \n" +
                "  alt 238    ε       \n" +
                "  alt 239    ∩       \n" +
                "  alt 240    ≡       \n" +
                "  alt 241    ±       \n" +
                "  alt 242    ≥       \n" +
                "  alt 243    ≤       \n" +
                "  alt 244    ⌠       \n" +
                "  alt 245    ⌡       \n" +
                "  alt 247    ≈       \n" +
                "  alt 248    °       \n" +
                "  alt 249    ·       \n" +
                "  alt 250    ·       \n" +
                "  alt 251    √       \n" +
                "  alt 252    ⁿ       \n" +
                "  alt 254    ■       \n" +
                "  alt 255            \n" +
                "  alt 0128   €       \n" +
                "  alt 0130   ‘       \n" +
                "  alt 0132   „       \n" +
                "  alt 0133   …       \n" +
                "  alt 0134   †       \n" +
                "  alt 0135   ‡       \n" +
                "  alt 0137   ‰       \n" +
                "  alt 0138   Š       \n" +
                "  alt 0139   ‹       \n" +
                "  alt 0140   Œ       \n" +
                "  alt 0142   Ž       \n" +
                "  alt 0145   ‘       \n" +
                "  alt 0146   ’       \n" +
                "  alt 0147   “       \n" +
                "  alt 0148   ”       \n" +
                "  alt 0151   —       \n" +
                "  alt 0153   ™       \n" +
                "  alt 0154   š       \n" +
                "  alt 0155   ›       \n" +
                "  alt 0156   œ       \n" +
                "  alt 0158   ž       \n" +
                "  alt 0159   Ÿ       \n" +
                "  alt 0164   ¤       \n" +
                "  alt 0166   ¦       \n" +
                "  alt 0168   ¨       \n" +
                "  alt 0169   ©       \n" +
                "  alt 0175   ¯       \n" +
                "  alt 0178   ²       \n" +
                "  alt 0179   ³       \n" +
                "  alt 0180   ´       \n" +
                "  alt 0183   ·       \n" +
                "  alt 0184   ¸       \n" +
                "  alt 0185   ¹       \n" +
                "  alt 0188   ¼       \n" +
                "  alt 0189   ½       \n" +
                "  alt 0190   ¾       \n" +
                "  alt 0192   À       \n" +
                "  alt 0193   Á       \n" +
                "  alt 0194   Â       \n" +
                "  alt 0195   Ã       \n" +
                "  alt 0196   Ä       \n" +
                "  alt 0197   Å       \n" +
                "  alt 0198   Æ       \n" +
                "  alt 0199   Ç       \n" +
                "  alt 0200   È       \n" +
                "  alt 0201   É       \n" +
                "  alt 0202   Ê       \n" +
                "  alt 0203   Ë       \n" +
                "  alt 0204   Ì       \n" +
                "  alt 0205   Í       \n" +
                "  alt 0206   Ï       \n" +
                "  alt 0207   Ï       \n" +
                "  alt 0208   Ð       \n" +
                "  alt 0210   Ò       \n" +
                "  alt 0211   Ó       \n" +
                "  alt 0212   Ô       \n" +
                "  alt 0213   Õ       \n" +
                "  alt 0214   Ö       \n" +
                "  alt 0215   ×       \n" +
                "  alt 0216   Ø       \n" +
                "  alt 0217   Ù       \n" +
                "  alt 0218   Ú       \n" +
                "  alt 0219   Û       \n" +
                "  alt 0220   Ü       \n" +
                "  alt 0221   Ý       \n" +
                "  alt 0222   Þ       \n" +
                "  alt 0223   ß       \n" +
                "  alt 0224   à       \n" +
                "  alt 0225   á       \n" +
                "  alt 0226   â       \n" +
                "  alt 0227   ã       \n" +
                "  alt 0228   ä       \n" +
                "  alt 0229   å       \n" +
                "  alt 0230   æ       \n" +
                "  alt 0231   ç       \n" +
                "  alt 0232   è       \n" +
                "  alt 0233   é       \n" +
                "  alt 0234   ê       \n" +
                "  alt 0235   ë       \n" +
                "  alt 0236   ì       \n" +
                "  alt 0237   í       \n" +
                "  alt 0238   î       \n" +
                "  alt 0239   ï       \n" +
                "  alt 0240   ð       \n" +
                "  alt 0242   ò       \n" +
                "  alt 0243   ó       \n" +
                "  alt 0244   ô       \n" +
                "  alt 0245   õ       \n" +
                "  alt 0246   ö       \n" +
                "  alt 0247   ÷       \n" +
                "  alt 0248   ø       \n" +
                "  alt 0249   ú       \n" +
                "  alt 0250   û       \n" +
                "  alt 0251   ü       \n" +
                "  alt 0252   ù       \n" +
                "  alt 0253   ý       \n" +
                "  alt 0254   þ       \n" +
                "  alt 0255   ÿ      32 spazio 75 K 118 v 161 í 206 ╬\n" +
                "33 ! 76 L 119 w 162 ó 207 ╧\n" +
                "34 \" 77 M 120 x 163 ú 208 ╨\n" +
                "35 # 78 N 121 y 164 ñ 209 ╤\n" +
                "36 $ 79 O 122 z 165 Ñ 210 ╥\n" +
                "37 % 80 P 123 { 166 ª 211 ╙\n" +
                "38 & 81 Q 124 | 167 º 212 Ô\n" +
                "39 ' 82 R 125 } 168 ¿ 213 ╒\n" +
                "40 ( 83 S 126 ~ 169 ⌐ 214 ╓\n" +
                "41 ) 84 T 127 CANC 170 ¬ 215 ╫\n" +
                "42 * 85 U 128 Ç 171 ½ 216 ╪\n" +
                "43 + 86 V 129 ü 172 ¼ 217 ┘\n" +
                "44 , 87 w 130 é 173 ¡ 218 ┌\n" +
                "45 - 88 X 131 â 174 « 224 α\n" +
                "46 . 89 Y 132 ä 175 » 225 ß\n" +
                "47 / 90 Z 133 à 176 ░ 226 Γ\n" +
                "48 0 91 [ 134 å 179 │ 227 π\n" +
                "49 1 92 \\ 135 ç 180 ┤ 228 Σ\n" +
                "50 2 93 ] 136 ê 181 ╡ 229 σ\n" +
                "51 3 94 ^ 137 ë 182 ╢ 230 µ\n" +
                "52 4 95 _ 138 è 183 ╖ 231 τ\n" +
                "53 5 96 ` 139 ï 184 ╕ 232 F\n" +
                "54 6 97 a 140 î 185 ╣ 233 T\n" +
                "55 7 98 b 141 ì 186 ║ 234 Ω\n" +
                "56 8 99 c 142 Ä 187 ╗ 235 δ\n" +
                "57 9 100 d 143 Å 188 ╝ 236 8\n" +
                "58 : 101 e 144 É 189 ╜ 237 φ\n" +
                "59 ; 102 f 145 æ 190 ╛ 238 ε\n" +
                "60 < 103 g 146 Æ 191 ┐ 239 ∩\n" +
                "61 = 104 h 147 ô 192 └ 240 ≡\n" +
                "62 > 105 i 148 ö 193 ┴ 241 ±\n" +
                "63 ? 106 j 149 ò 194 ┬ 242 ≥\n" +
                "64 @ 107 k 150 û 195 ├ 243 ≤\n" +
                "65 A 108 l 151 ù 196 ─ 244 ⌠\n" +
                "66 B 109 m 152 ÿ 197 ┼ 245 ⌡\n" +
                "67 C 110 n 153 Ö 198 ╞ 246 ÷\n" +
                "68 D 111 o 154 Ü 199 ╟ 247 ≈\n" +
                "69 E 112 p 155 ¢ 200 ╚ 248 ≈\n" +
                "70 F 113 q 156 £ 201 ╔ 249 ∙\n" +
                "71 G 114 r 157 Ø 202 ╩ 250 ·\n" +
                "72 H 115 s 158 ₧ 203 ╦ 251 √\n" +
                "73 I 116 t 159 ƒ 204 ╠ 252 ⁿ\n" +
                "74 J 117 u 160 á 205 ═ 253 ²\n\nInsert the RMI port (default "+portRmi+"): (Press Enter to use default port)");
        while (!correctInput) {
            try {
                System.out.print("> ");
                String input = sc.nextLine();
                if (input.equals("")) {
                    portRmi = 1099;
                    break;
                }
                portRmi = Integer.parseInt(input);
                if (portRmi < 1024 || portRmi > 65535)
                    System.out.println("Port must be between 1024 and 65535, retry");
                else
                    correctInput = true;
            } catch (NumberFormatException e) {
                System.out.println("Port must be a number, retry");
            }
        }

        correctInput = false;
        System.out.println("Insert the Socket port (default "+portSocket+"): (Press Enter to use default port)");
        while (!correctInput) {
            try {
                System.out.print("> ");
                String input = sc.nextLine();
                if (input.equals("")) {
                    portSocket = 1100;
                    break;
                }
                portSocket = Integer.parseInt(input);
                if (portSocket < 1024 || portSocket > 65535)
                    System.out.println("Port must be between 1024 and 65535, retry");
                else if (portSocket == portRmi)
                    System.out.println("Port already in use, retry");
                else
                    correctInput = true;
            } catch (NumberFormatException e) {
                System.out.println("Port must be a number, retry");
            }
        }

        correctInput = false;
        System.out.println("Insert the IP address (default " + ipAddress + "): (Press Enter to use default IP)");
        while (!correctInput) {
            try {
                System.out.print("> ");
                String input = sc.nextLine();
                if (input.equals("")) {
                    ipAddress = "127.0.0.1";
                    break;
                }
                ipAddress = input;
                if (!ipAddress.matches("^([01]?\\d\\d?|2[0-4]\\d|25[0-5])\\." +
                        "([01]?\\d\\d?|2[0-4]\\d|25[0-5])\\." +
                        "([01]?\\d\\d?|2[0-4]\\d|25[0-5])\\." +
                        "([01]?\\d\\d?|2[0-4]\\d|25[0-5])$"))
                    System.out.println("Invalid IP address, retry");
                else
                    correctInput = true;
            } catch (InputMismatchException e) {
                System.out.println("Invalid IP address, retry");
            }
        }
        System.out.println("Starting server with RMI port: " + portRmi + ", Socket port: " + portSocket + ", IP address: " + ipAddress + "\n");

        Server server = new Server(portRmi, portSocket, ipAddress);
        server.run();
    }

    /**
     * Starts the server (RMI and Socket) and prints the IP address of the server on the console
     */
    private void startServers(String ipAddress){
        if (instance != null) {
            System.out.println("Servers already started");
            return;
        }
        instance = this;
        setIpAddress(ipAddress);

        instance.rmiServer = new RMIServer(this, rmiPort);
        instance.rmiServer.startServer();
        System.out.println("RMI Server started on port: " + rmiPort );

        instance.socketServer = new SocketServer(this, socketPort);
        instance.socketServer.startServer();
        System.out.println("Socket Server started on port: " + socketPort + "\n");

        System.out.println("Server started successfully with IP address: " + ipAddress + "\n");
    }

    /**
     * Process that pings all the clients to check if they are still connected
     */
    @Override
    public void run() {
        while (!Thread.currentThread().isInterrupted()) {
            synchronized (clientsLock) {
                for (Connection connection : clientsConnected.values()) {
                    if (connection != null && connection.isConnected() && connection.getClientPinger() == null) {
                        ClientPinger pinger = new ClientPinger(getUsernameByConnection(connection), connection, clientsLock);
                        connection.setClientPinger(pinger);
                        executor.execute(pinger);
                    }
                }
            }
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                System.out.println("Server ping interrupted");
                Thread.currentThread().interrupt();
            }
        }
    }

    /**
     * This method is used to get the instance of the server if it is already started and to start it if it is not already started
     * @return the instance of the server if it is already started, otherwise it starts the server and returns the instance of the server
     * @throws IOException if the server is not started
     */
    public static synchronized Server getInstance() throws IOException {
        if (instance == null) {
            instance = new Server(rmiPort, socketPort, ipAddress);
        }
        return instance;
    }

    /**
     * Method that handles the login of a player to the server into 2 cases: if the player is already known or not known by the server
     * @param nickname of the player
     * @param connection of the player
     * @throws Exception if the player is already connected or if the nickname is already used
     */
    public synchronized void loginToServer(String nickname, Connection connection) throws Exception {
        //it works on the map of clientsConnected then it will enter in the globalLobby and then in the gameLobby
        try {
            synchronized (clientsLock) {
                if (globalLobby.isPlayerDisconnectedInAnyGameLobby(nickname) || clientsConnected.containsKey(nickname)) {
                    knownPlayerLogin(nickname, connection);
                } else {
                    newPlayerLogin(nickname, connection);
                }
            }
        } catch (IOException e) {
            System.out.println("Something went wrong during login. Connection refused!");

            MessageHeader header = new MessageHeader(MessageType.ERROR, nickname);
            MessagePayload payload = new MessagePayload(KeyErrorPayload.ERROR_CONNECTION);
            payload.put(Data.ERROR, ErrorType.DISCONNECTION_FORCED);
            connection.sendMessageToClient(new Message(header,payload));

            connection.disconnect();
        }
    }

    /**
     * Method that handles the login of a new player to the server
     * @param nickname of the player
     * @param connection of the player
     * @throws Exception if the player used an invalid nickname (too long or too short)
     */
    private synchronized void newPlayerLogin(String nickname, Connection connection) throws Exception {
        if (checkNickname(nickname)) { // nickname legit
            clientsConnected.put(nickname, connection);

            String token = UUID.randomUUID().toString();
            connection.setToken(token);

            System.out.println(nickname + " connected to server!");
            this.globalLobby.addPlayerToWaiting(nickname, connection,false);

        } else { // nickname not legit
            MessageHeader header = new MessageHeader(MessageType.ERROR, nickname);
            MessagePayload payload = new MessagePayload(KeyErrorPayload.ERROR_CONNECTION);
            payload.put(Data.ERROR, ErrorType.ERR_NICKNAME_LENGTH);
            connection.sendMessageToClient(new Message(header,payload));

            connection.disconnect();
            System.out.println("Attention! " + nickname + " tried to connect with invalid name length! Connection refused!");
        }
    }

    /**
     * Method that handles the login of a player to the server if the player is already known by the server into 2 cases: if the player was disconnected or if the nickname is already used
     * @param nickname of the player
     * @param connection of the player
     * @throws Exception if the player is already connected or if the nickname is already used
     */
    private synchronized void knownPlayerLogin(String nickname, Connection connection) throws Exception {
        if (globalLobby.isPlayerDisconnectedInAnyGameLobby(nickname)) { // player was disconnected
            clientsConnected.put(nickname, connection);

            String token = UUID.randomUUID().toString();
            connection.setToken(token);

            MessageHeader header = new MessageHeader(MessageType.CONNECTION, nickname);
            MessagePayload payload = new MessagePayload(KeyConnectionPayload.BROADCAST);
            String content = "Login was successful! You will be reconnected to your previous game...";
            payload.put(Data.CONTENT, content);
            connection.sendMessageToClient(new Message(header, payload));

            System.out.println(nickname + " reconnected to server!");
            globalLobby.reconnectPlayerToGameLobby(nickname, connection);

        } else if (clientsConnected.containsKey(nickname)) { // nickname already in use

            MessageHeader header = new MessageHeader(MessageType.ERROR, nickname);
            MessagePayload payload = new MessagePayload(KeyErrorPayload.ERROR_CONNECTION);
            payload.put(Data.ERROR, ErrorType.ERR_NICKNAME_TAKEN);
            connection.sendMessageToClient(new Message(header,payload));

            connection.disconnect();
            System.out.println("Attention! " + nickname + " tried to connect with a nickname already taken!");
        }
    }

    /**
     * Method that checks if the nickname is valid (not too long or too short)
     * @param nickname of the player
     * @return true if the nickname is valid, false otherwise
     */
    private boolean checkNickname(String nickname) {
        return nickname.length() <= MAX_LENGTH_NICKNAME && nickname.length() >= MIN_LENGTH_NICKNAME;
    }

    /**
     * Method that handles the disconnection of a player from the server
     * @param playerConnection of the player
     * @throws IOException if the player is not connected to the server
     */
    public synchronized void onDisconnect(Connection playerConnection) throws IOException {
        String username = getUsernameByConnection(playerConnection);

        if (username != null) {
            synchronized (clientsLock) {
                System.out.println(username + " disconnected from server!");
                this.globalLobby.disconnectPlayerFromGlobalLobby(username);
                clientsConnected.remove(username);
            }
        }
    }

    /**
     * Method that returns the username of a player given his connection from the map of clientsConnected to the server
     * @param connection of the player
     * @return the username of the player
     */
    private String getUsernameByConnection(Connection connection) {
        Set<String> usernameList;
        synchronized (clientsLock) {
            usernameList = clientsConnected.entrySet()
                    .stream()
                    .filter(entry -> connection.equals(entry.getValue()))
                    .map(HashMap.Entry::getKey)
                    .collect(Collectors.toSet());
        }
        if (usernameList.isEmpty()) {
            return null;
        } else {
            return usernameList.iterator().next();
        }
    }

    /**
     * Method that handles the message received from a client
     * @param message received from the client
     * @throws Exception if the message is not valid
     */
    public synchronized void receiveMessageFromClient(Message message) throws Exception {
        MessageType messageType = message.getHeader().getMessageType();
        switch (messageType) {
            case LOBBY -> handleGlobalLobbyPhase(message);
            case DATA-> handleData(message);
            case ERROR -> handleErrorFromClient(message);
            default -> throw new IllegalStateException("Unexpected value: " + messageType);
        }
    }

    /**
     * Method that handles a message of type LOBBY received from a client into 4 cases: create a new game lobby, join a specific game lobby, join a random game lobby, quit the server
     * @param message received from the client
     * @throws Exception if the message is not valid
     */
    private synchronized void handleGlobalLobbyPhase(Message message) throws Exception {
        KeyLobbyPayload keyLobbyPayload = (KeyLobbyPayload) message.getPayload().getKey();
        switch (keyLobbyPayload) {
            case CREATE_GAME_LOBBY -> handleCreateGameLobby(message);
            case JOIN_SPECIFIC_GAME_LOBBY -> handleJoinSpecificGameLobby(message);
            case JOIN_RANDOM_GAME_LOBBY -> handleJoinRandomGameLobby(message);
            case QUIT_SERVER -> handleQuitServer(message);
            default -> throw new IllegalStateException("Unexpected value: " + keyLobbyPayload);
        }
    }

    /**
     * Method that handles the creation of a new game lobby by a client given the number of players wanted in the game
     * @param message received from the client
     * @throws IOException if the number of players wanted is not valid
     */
    private synchronized void handleCreateGameLobby(Message message) throws IOException {
        int wantedPlayers = (int) message.getPayload().getContent(Data.VALUE_CLIENT);
        if(wantedPlayers < MIN_PLAYERS || wantedPlayers > MAX_PLAYERS){
            MessageHeader header = new MessageHeader(MessageType.ERROR, message.getHeader().getNickname());
            MessagePayload payload = new MessagePayload(KeyErrorPayload.ERROR_LOBBY);
            payload.put(Data.ERROR, ErrorType.ERR_NUM_PLAYER_WANTED);
            Message messageToClient = new Message(header, payload);
            clientsConnected.get(message.getHeader().getNickname()).sendMessageToClient(messageToClient);

            System.out.println("Attention! " + message.getHeader().getNickname() + " tried to create a game lobby with invalid number of players!");
            return;
        }
        this.globalLobby.playerCreatesGameLobby(wantedPlayers, message.getHeader().getNickname(), clientsConnected.get(message.getHeader().getNickname()));
    }

    /**
     * Method that handles the join of a client to a specific game lobby given the id of the game lobby he wants to join
     * @param message received from the client
     * @throws IOException if the game lobby is full or if the game lobby is not found in the list of game lobbies of the server
     */
    private synchronized void handleJoinSpecificGameLobby(Message message) throws IOException {
        int gameId = (int) message.getPayload().getContent(Data.VALUE_CLIENT);
        this.globalLobby.playerJoinsGameLobbyId(gameId, message.getHeader().getNickname(), clientsConnected.get(message.getHeader().getNickname()));
    }

    /**
     * Method that handles the join of a client to a random game lobby with a free spot in it if there is one otherwise it creates a new game lobby with minimum number of players and adds the client to it
     * @param message received from the client
     * @throws IOException if the game lobby is full
     */
    private synchronized void handleJoinRandomGameLobby(Message message) throws IOException {
        this.globalLobby.playerJoinsFirstFreeSpotInRandomGame(message.getHeader().getNickname(), MIN_PLAYERS,clientsConnected.get(message.getHeader().getNickname()));
    }

    /**
     * Method that handles the quit of a client from the server by disconnecting him from the global lobby and removing him from the list of clients connected to the server
     * @param message received from the client
     * @throws Exception if the client is not found in the list of clients connected to the server
     */
    private synchronized void handleQuitServer(Message message) throws Exception {
        this.globalLobby.disconnectPlayerFromGlobalLobby(message.getHeader().getNickname());
        this.clientsConnected.remove(message.getHeader().getNickname());
        clientsConnected.get(message.getHeader().getNickname()).disconnect();
    }

    /**
     * Method that handles a message of type DATA received from a client
     * @param message received from the client
     * @throws IOException if the message is not valid
     */
    private synchronized void handleData(Message message) throws IOException {
        String nickname = message.getHeader().getNickname();

        if(globalLobby.isPlayerActiveInAnyGameLobby(nickname)){
            GameLobby gameLobby = this.globalLobby.findGameLobbyByNickname(nickname);
            gameLobby.handleTurn(message);
        } else {
            MessageHeader header = new MessageHeader(MessageType.ERROR, nickname);
            MessagePayload payload = new MessagePayload(KeyErrorPayload.ERROR_LOBBY);
            payload.put(Data.ERROR, ErrorType.ERR_GAME_NOT_FOUND);
            Message messageToClient = new Message(header, payload);
            clientsConnected.get(nickname).sendMessageToClient(messageToClient);
        }
    }

    /**
     * Method that handles a message of type ERROR received from a client and forwards it to the game lobby of the client who sent it to the server
     * if he is active in a game lobby otherwise it sends an error message
     * @param message received from the client
     * @throws IOException if the message is not valid
     */
    private synchronized void handleErrorFromClient(Message message) throws IOException {
        String nickname = message.getHeader().getNickname();

        if (globalLobby.isPlayerActiveInAnyGameLobby(nickname)) {
            GameLobby gameLobby = this.globalLobby.findGameLobbyByNickname(nickname);
            gameLobby.handleErrorFromClient(message);

        } else {
            MessageHeader header = new MessageHeader(MessageType.ERROR, nickname);
            MessagePayload payload = new MessagePayload(KeyErrorPayload.ERROR_LOBBY);
            payload.put(Data.ERROR, ErrorType.ERR_GAME_NOT_FOUND);
            Message messageToClient = new Message(header, payload);
            clientsConnected.get(nickname).sendMessageToClient(messageToClient);
        }
    }

}
