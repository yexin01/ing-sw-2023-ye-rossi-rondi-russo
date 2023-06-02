package it.polimi.ingsw.view.CLI;

import static java.lang.System.out;

/**
 * Class that prints the logos or drawings of the game in the CLI version of the game (ASCII art)
 * using ANSI escape codes to color the output text in the terminal (only for Linux and MacOS)
 * and the Unicode characters to print the drawings in the terminal
 */
public class PrinterLogo {

    public synchronized static void printMyShelfieLogo() {
        String myShelfieLogo = """
                                                                                                                                          
                             ███████████████████████████████████████████████████████████████████████████████████████████████████████████████████████████████████
                             █                                                                                                                                 █
                             █                                                                                                                                 █
                             █                           ███╗   ███╗██╗   ██╗    ███████╗██╗  ██╗███████╗██╗     ███████╗██╗███████╗                           █
                             █                           ████╗ ████║╚██╗ ██╔╝    ██╔════╝██║  ██║██╔════╝██║     ██╔════╝██║██╔════╝                           █
                             █                           ██╔████╔██║ ╚████╔╝     ███████╗███████║█████╗  ██║     █████╗  ██║█████╗                             █
                             █                           ██║╚██╔╝██║  ╚██╔╝      ╚════██║██╔══██║██╔══╝  ██║     ██╔══╝  ██║██╔══╝                             █
                             █                           ██║ ╚═╝ ██║   ██║       ███████║██║  ██║███████╗███████╗██║     ██║███████╗                           █
                             █                           ╚═╝     ╚═╝   ╚═╝       ╚══════╝╚═╝  ╚═╝╚══════╝╚══════╝╚═╝     ╚═╝╚══════╝                           █
                             █                                                                                                                                 █
                             █           Welcome to MyShelfie Board Game made by Andrea Rondi, Giulia Rossi, Samuele Russo and Xin Ye.                         █
                             █                                                                                                                                 █
                             █                                                                                                                                 █
                             █        C:\\> _Before starting playing you need to setup some things:                                                             █
                             █                                                                                                                                 █
                             ███████████████████████████████████████████████████████████████████████████████████████████████████████████████████████████████████
                                               
                                                                            ▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄
                                                                           █──▄────▄─▄─▄─▄─▄─▄▄▄─▄─▄─▄─▄─▄─▄─▄──▄───█
                                                                          █──▄─▄──▄─▄─▄─▄─▄▄▄▄▄▄──▄─▄─▄─▄─▄─▄─▄─▄─▄──█
                                                                         █────────────────────────────────────────────█
                                                                        ▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀
                                                                                                                                                          \s
                """;

        System.out.println(myShelfieLogo);
    }

    private synchronized static void printMyShelfieLogo2() {
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

        System.out.println(myShelfieLogo);
    }
    public synchronized static void printLogo(String[] lines, int freeSpaces) {
        out.println();
        for (String line : lines) {
            Colors.printFreeSpaces(freeSpaces);
            Colors.colorize(Colors.GAME_INSTRUCTION,line);
            System.out.println();
        }
    }



    // titles for the infos
    public synchronized static void printBoardLogo(int freeSpaces) {
        String boardLogo[] = {

                "╔╦╗╦ ╦╔═╗  ╔╗ ╔═╗╔═╗╦═╗╔╦╗",
                " ║ ╠═╣║╣   ╠╩╗║ ║╠═╣╠╦╝ ║║",
                " ╩ ╩ ╩╚═╝  ╚═╝╚═╝╩ ╩╩╚══╩╝"};

        printLogo(boardLogo,freeSpaces);
    }

    public synchronized static void printBookshelfLogo(int freeSpaces) {
        String[] bookshelfLogo = {
                "╔╦╗╦ ╦  ╔╗ ╔═╗╔═╗╦╔═╔═╗╦ ╦╔═╗╦  ╔═╗",
                "║║║╚╦╝  ╠╩╗║ ║║ ║╠╩╗╚═╗╠═╣║╣ ║  ╠╣",
                "╩ ╩ ╩   ╚═╝╚═╝╚═╝╩ ╩╚═╝╩ ╩╚═╝╩═╝╚"
        };

        printLogo(bookshelfLogo, freeSpaces);
    }
    private static int terminalWidth = 80;
    public synchronized static void printGamesRulesLogo() {
        String[] gamesRulesLogo = {
                Colors.paint(Colors.YELLOW_CODE, "╔═╗╔═╗╔╦╗╔═╗╔═╗  ╦═╗╦ ╦╦  ╔═╗╔═╗"),
                Colors.paint(Colors.YELLOW_CODE, "║ ╦╠═╣║║║║╣ ╚═╗  ╠╦╝║ ║║  ║╣ ╚═╗"),
                Colors.paint(Colors.YELLOW_CODE, "╚═╝╩ ╩╩ ╩╚═╝╚═╝  ╩╚═╚═╝╩═╝╚═╝╚═╝"),
                "",
                Colors.paint(Colors.ORANGE_CODE, "GOAL OF THE GAME:"),
                "Players take item tiles from the living room and place them in their bookshelves to score points; ",
                "The game ends when a player completely fills their bookshelf. The player with more points at the end will win the game. ",
                "",
                Colors.paint(Colors.ORANGE_CODE, "There are 4 ways to score points:"),
                Colors.paint(Colors.WHITE_CODE, "(1) PERSONAL GOAL CARD:"),
                "The personal goal card grants points if you match the highlighted spaces with the corresponding item tiles.",
                Colors.paint(Colors.WHITE_CODE, "(2) COMMON GOAL CARDS:"),
                "The common goal cards grant points to the players who achieve the illustrated pattern. ",
                Colors.paint(Colors.WHITE_CODE, "(3) ADJACENT ITEM TILES:"),
                "Groups of adjacent item tiles of the same type on your bookshelf grant points depending on how many tiles are connected",
                " (with one side touching).",
                Colors.paint(Colors.WHITE_CODE, "(4) GAME-END TRIGGER"),
                "The first player who completely fills their bookshelf scores 1 additional point.",
                "",
                Colors.paint(Colors.ORANGE_CODE, "GAMEPLAY:"),
                "The game is divided in turns that take place in a clockwise order starting from the first player.",
                "During your turn, you must take 1, 2 or 3 item tiles from the living room board, following these rules:",
                "The tiles you take must be adjacent to each other and form a straight line.",
                "All the tiles you take must have at least one side free (not touching directly other tiles)",
                "at the beginning of your turn (i.e. you cannot take a tile that becomes free after your first pick).",
                "Then, you must place all the tiles you’ve picked into 1 column of your bookshelf.",
                "You can decide the order, but you cannot place tiles in more than 1 column in a single turn.",
                "",
                Colors.paint(Colors.WHITE_CODE, "Note: You cannot take tiles if you don’t have enough available spaces in your bookshelf."),
        };

        for (String str : gamesRulesLogo){
            System.out.println(" ".repeat(terminalWidth-getPrintableLength(str)/2)+str);
        }

        //printLogo(gamesRulesLogo, freeSpaces);
    }

    public static int getPrintableLength(String input) {
        int printableLength = 0;
        boolean escape = false;

        for (int i = 0; i < input.length(); i++) {
            char c = input.charAt(i);

            if (escape) {
                if (c == 'm') {
                    escape = false;
                }
            } else {
                if (c == '\u001B') {
                    escape = true;
                } else {
                    printableLength++;
                }
            }
        }

        return printableLength;
    }


    public synchronized static void printGlobalLobbyPhase(int freeSpaces) {
        String[] globalLobbyPhaseLogo = {
                "╔═╗╦  ╔═╗╔╗ ╔═╗╦    ╦  ╔═╗╔╗ ╔╗ ╦ ╦",
                "║ ╦║  ║ ║╠╩╗╠═╣║    ║  ║ ║╠╩╗╠╩╗╚╦╝",
                "╚═╝╩═╝╚═╝╚═╝╩ ╩╩═╝  ╩═╝╚═╝╚═╝╚═╝ ╩"
        };

        printLogo(globalLobbyPhaseLogo, freeSpaces);
    }

    public synchronized static void printGameLobbyPhase(int freeSpaces) {
        String[] gameLobbyPhaseLogo = {
                "╔═╗╔═╗╔╦╗╔═╗  ╦  ╔═╗╔╗ ╔╗ ╦ ╦",
                "║ ╦╠═╣║║║║╣   ║  ║ ║╠╩╗╠╩╗╚╦╝",
                "╚═╝╩ ╩╩ ╩╚═╝  ╩═╝╚═╝╚═╝╚═╝ ╩"
        };

        printLogo(gameLobbyPhaseLogo, freeSpaces);
    }

    public synchronized static void printYourTurnPhase() {
        String yourTurnPhaseLogo = """
                
                ╦ ╦╔═╗╦ ╦╦═╗  ╔╦╗╦ ╦╦═╗╔╗╔
                ╚╦╝║ ║║ ║╠╦╝   ║ ║ ║╠╦╝║║║
                 ╩ ╚═╝╚═╝╩╚═   ╩ ╚═╝╩╚═╝╚╝\s
                 
                """;
        out.println(yourTurnPhaseLogo);
    }

    public synchronized static void printWaitingTurnPhase(int freeSpaces) {
        String[] waitingTurnPhaseLogo = {
                "╦ ╦╔═╗╦╔╦╗╦╔╗╔╔═╗  ╦ ╦╔═╗╦ ╦╦═╗  ╔╦╗╦ ╦╦═╗╔╗╔",
                "║║║╠═╣║ ║ ║║║║║ ╦  ╚╦╝║ ║║ ║╠╦╝   ║ ║ ║╠╦╝║║║",
                "╚╩╝╩ ╩╩ ╩ ╩╝╚╝╚═╝   ╩ ╚═╝╚═╝╩╚═   ╩ ╚═╝╩╚═╝╚╝"
        };

        printLogo(waitingTurnPhaseLogo, freeSpaces);
    }


    public synchronized static void printBoardPhase(int freeSpaces) {
        String[] boardPhaseLogo = {
                "╔╗ ╔═╗╔═╗╦═╗╔╦╗  ╔═╗╦ ╦╔═╗╔═╗╔═╗",
                "╠╩╗║ ║╠═╣╠╦╝ ║║  ╠═╝╠═╣╠═╣╚═╗║╣",
                "╚═╝╚═╝╩ ╩╩╚══╩╝  ╩  ╩ ╩╩ ╩╚═╝╚═╝"
        };

        printLogo(boardPhaseLogo, freeSpaces);
    }

    public synchronized static void printOrderPhase(int freeSpaces) {
        String[] orderPhaseLogo = {
                "╔═╗╦═╗╔╦╗╔═╗╦═╗  ╔═╗╦ ╦╔═╗╔═╗╔═╗",
                "║ ║╠╦╝ ║║║╣ ╠╦╝  ╠═╝╠═╣╠═╣╚═╗║╣",
                "╚═╝╩╚══╩╝╚═╝╩╚═  ╩  ╩ ╩╩ ╩╚═╝╚═╝"
        };

        printLogo(orderPhaseLogo, freeSpaces);
    }

    public synchronized static void printColumnPhase(int freeSpaces) {
        String[] columnPhaseLogo = {
                "╔═╗╔═╗╦  ╦ ╦╔╦╗╔╗╔  ╔═╗╦ ╦╔═╗╔═╗╔═╗",
                "║  ║ ║║  ║ ║║║║║║║  ╠═╝╠═╣╠═╣╚═╗║╣",
                "╚═╝╚═╝╩═╝╚═╝╩ ╩╝╚╝  ╩  ╩ ╩╩ ╩╚═╝╚═╝"
        };

        printLogo(columnPhaseLogo, freeSpaces);
    }

    public synchronized static void printFinalRankingPhase(int freeSpaces) {
        String[] finalRankingPhaseLogo = {
                "╔═╗╦╔╗╔╔═╗╦    ╦═╗╔═╗╔╗╔╦╔═╦╔╗╔╔═╗",
                "╠╣ ║║║║╠═╣║    ╠╦╝╠═╣║║║╠╩╗║║║║║ ╦",
                "╚  ╩╝╚╝╩ ╩╩═╝  ╩╚═╩ ╩╝╚╝╩ ╩╩╝╚╝╚═╝"
        };

        printLogo(finalRankingPhaseLogo, freeSpaces);
    }



    // drawings :)
    public synchronized static void printWinnerLogo(int m) {
        out.println();
        String[] winnerLogo = {
                "────╔═╗╦╔╗╔╔═╗╦    ╦═╗╔═╗╔╗╔╦╔═╦╔╗╔╔═╗───",
                "────╠╣ ║║║║╠═╣║    ╠╦╝╠═╣║║║╠╩╗║║║║║ ╦───",
                "────╚  ╩╝╚╝╩ ╩╩═╝  ╩╚═╩ ╩╝╚╝╩ ╩╩╝╚╝╚═╝───",
                "",

                "        ¶¶¶¶¶¶¶¶¶¶¶¶¶¶¶¶¶¶¶¶¶¶¶¶¶¶¶\s",
                "   ¶¶¶¶¶¶¶¶¶¶¶¶¶¶¶¶¶¶¶¶¶¶¶¶¶¶¶¶¶¶¶¶¶¶¶¶¶\s",
                "¶¶¶       ¶¶¶¶¶¶¶¶¶¶¶¶¶¶¶¶¶¶¶¶¶¶        ¶¶¶\s",
                "¶¶¶     ¶¶¶¶¶¶¶¶¶¶¶¶¶¶¶¶¶¶¶¶¶¶¶¶¶      ¶¶¶\s",
                " ¶¶¶   ¶¶¶_¶¶¶¶¶¶¶¶¶¶¶¶¶¶¶¶¶¶¶_¶¶¶    ¶¶¶\s",
                "   ¶¶¶¶__¶¶¶¶¶¶¶¶¶¶¶¶¶¶¶¶¶¶¶¶¶¶¶_¶¶¶¶¶\s",
                "      ¶¶¶¶¶¶__¶¶¶¶¶¶¶¶¶¶¶¶¶¶___¶¶¶¶¶\s",
                "               ¶¶¶¶¶¶¶¶¶¶¶¶\s",
                "                   ¶¶¶¶\s",
                "               ¶¶¶¶¶¶¶¶¶¶¶¶\s",
                "            ¶¶¶¶¶¶¶¶¶¶¶¶¶¶¶¶¶¶\s",
                "            ¶¶¶            ¶¶¶\s",
                "            ¶¶¶¶¶¶¶¶¶¶¶¶¶¶¶¶¶¶\s",
                "         ¶¶¶¶¶¶¶¶¶¶¶¶¶¶¶¶¶¶¶¶¶¶¶¶\s",
                ""



        };
        /*
        for (String str : winnerLogo){
            System.out.println(" ".repeat(terminalWidth-(getPrintableLength(str)/2))+str);
        }

         */

        printLogo(winnerLogo, m);
    }


    private synchronized static void printDisconnectionForcedLogo(int freeSpaces) {
        String disconnectionForcedLogo[] = {

                "▒▒▒▒▒▒▒▒▒▒▒▄▄▄▄░▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒",
                "▒▒▒▒▒▒▒▄██▀░░▀██▄▒▒▒▒████████▄▒▒▒▒▒▒",
                "▒▒▒▒▒▒███░░░░░░██▒▒▒▒▒▒█▀▀▀▀▀██▄▄▒▒▒",
                "▒▒▒▒▒███░░▐█░█▌░██▒▒▒▒█▌▒▒▒▒▒▒▒▒▒▒▀▌",
                "▒▒▒▒████░▐█▌░▐█▌██▒▒▒██▒▒▒▒▒▒▒▒▒▒▒▒▒",
                "▒▒▒▐████░▐░░░░░▌██▒▒▒█▌▒▒▒▒▒▒▒▒▒▒▒▒▒",
                "▒▒▒▒████░░░▄█░░░██▒▒▐█▒▒▒▒▒▒▒▒▒▒▒▒▒▒",
                "▒▒▒▒████▌░▐█░░███▒▒▒█▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒",
                "▒▒▒▒▐████░░▌░███▒▒▒██▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒",
                "▒▒▒██████▌░████▒▒▒██▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒",
                "▒▐████████████▒▒███▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒",
                "▒█████████████▄████▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒",
                "█████████████████▀▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒",
                "████████████████▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒"};


        printLogo(disconnectionForcedLogo, freeSpaces);
    }

    public synchronized static void printLostConnectionLogo(int freeSpaces) {
        String[] lostConnectionLogo = {
                "            ──▄────▄▄▄▄▄▄▄────▄───",
                "            ─▀▀▄─▄█████████▄─▄▀▀──",
                "            ─────██─▀███▀─██──────",
                "            ───▄─▀████▀████▀─▄────",
                "            ─▀█────██▀█▀██────█▀──"
        };

        printLogo(lostConnectionLogo, freeSpaces);
    }


    public synchronized static void printErrorLogo(int freeSpaces) {
        String[] errorLogo = {
                "                ░░░░░░░▄▄▄▄▄▄░░░░░░░░░░░░░░░░░░░",
                "                ░░░░░▄█████████▄▄░░░░░░░░▄▄░░░░░",
                "                ░░░░███████████████▄▄░░░░▄██▄░░░",
                "                ░▄██████████████████████████▀░░░",
                "                ░███░░░░░░░░░░░░░░░░░░░█░░░░░░░░",
                "                ░███░███████████████████░░░░░░░░",
                "                ░███░░▀████───████────█▀░░░░░░░░",
                "                ░███░░░▀▄▄▄▄▄▀░▀▄▄▄▄▄▀▀▀▄░░░░░░░",
                "                ░░██░░░░░░░░░░░░░░░░░░░░░▀▄░░░░░",
                "                ░░▄█░░░░░▄▀█▀▀▀▄▄▄░░░░░▄▀░░▀▄░░░",
                "                ░█░░░░░▄▀──█───█──▀█▄▄░▀▀░░░░▀▄░",
                "                ░▀▄▄░░█────█───█───█──▀▀▄░░▄▀▀▀░",
                "                ░░░█░██▄▄▄▄█▄▄▄█▄▄▄█▄▄▄▄▄█▄▄▀░░░",
                "                ░░░█░█████████████▄▄░░░░░░░░░░░░",
                "                ░░░█░█████████████████████████▄░",
                "                ░░░█░██████████▀▀░░░░░░░░░░░░░█░",
                "                ░░░█░███▀░░░░░░░░░░░░░░░░░░░░░█░",
                "                ░░░█░░░░░░░░░░░░░▀▄░▀▄░░░░░░░█░",
                "                ░░░█░░░░░░░░░░░░▄▄▄▀▄▄▀░░░░░░░█░",
                "                ░░░█░░░░░░░░░░░░░░░░░░░░░░░░░░█░",
                "                ░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░",
                "                ░█▀▄░█░█▄░█░█░█░█░░█▀░█▀▄░█▀░█▀▄░▄▀▀░█░",
                "                ░█░█░█░█▀██░█▀▄░█░░█▀░█▀▄░█▀░█▀▄░█░█░▀░",
                "                ░▀▀░░▀░▀░░▀░▀░▀░▀▀░▀▀░▀▀░░▀▀░▀░▀░░▀▀░▀░",
                "                ░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░"};

        printLogo(errorLogo, freeSpaces);
    }

    public synchronized static void printErrorLogo2(int freeSpaces) {
        String[] errorLogo = {
                "▄██████████████▄▐█▄▄▄▄█▌",
                "██████▌▄▌▄▐▐▌███▌▀▀██▀▀",
                "████▄█▌▄▌▄▐▐▌▀███▄▄█▌",
                "▄▄▄▄▄██████████████▀",
                "                                                                                                    "
        };

        printLogo(errorLogo, freeSpaces);
    }
    public synchronized static void onlyPlayer(int freeSpaces) {
        String[] onlyLogo = {
                "      |\\      _,,,---,,_" ,
                        "ZZZzz /,`.-'`'    -.  ;-;;,_" ,
                        "     |,4-  ) )-,_. ,\\ (  `'-'" ,
                        "    '---''(_/--'  `-'\\_) ", "                                                                                             "
        };

        printLogo(onlyLogo, freeSpaces);
    }

    public synchronized static void printWaitingLogo(int freeSpaces) {
        String[] waitingLogo = {
                "───▄▄▄",
                "─▄▀░▄░▀▄",
                "─█░█▄▀░█",
                "─█░▀▄▄▀█▄█▄▀",
                "▄▄█▄▄▄▄███▀"
        };

        printLogo(waitingLogo, freeSpaces);
    }

}
