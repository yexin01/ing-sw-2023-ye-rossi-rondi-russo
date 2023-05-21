package it.polimi.ingsw.view.CLI;

import static java.lang.System.out;

/**
 * Class that prints the logos or drawings of the game in the CLI version of the game (ASCII art)
 * using ANSI escape codes to color the output text in the terminal (only for Linux and MacOS)
 * and the Unicode characters to print the drawings in the terminal
 */
public class PrinterLogo {

    public static void printMyShelfieLogo() {
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
                          █           Welcome to MyShelfie Board Game made by Andrea Rondi, Giulia Rossi, Samuele Russo and Xin Ye.🐈‍⬛🪴📚🏆🧩🖼️🐈          █
                          █                                                                                                                                 █
                          █                                                                                                                                 █
                          █        C:\\> _Before starting playing you need to setup some things:                                                             █
                          █                                                                                                                                 █
                          ███████████████████████████████████████████████████████████████████████████████████████████████████████████████████████████████████
                                            
                                                                         ▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄
                                                                        █──▄────▄─▄─▄─▄─▄─▄▄▄─▄─▄─▄─▄─▄─▄─▄▐─▄─▌─█
                                                                       █──▄─▄▐─▄─▄─▄─▄─▄▄▄▄▄▄──▄─▄─▄─▄─▄─▄─▄─▄─▄──█
                                                                      █────────────────────────────────────────────█
                                                                     ▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀
                                                                                                                                                          \s
                """;

        System.out.println(myShelfieLogo);
    }

    private static void printMyShelfieLogo2() {
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
    public static void printLogo(String[] lines, int freeSpaces) {
        out.println();
        for (String line : lines) {
            Colors.printFreeSpaces(freeSpaces);
            Colors.colorize(Colors.GAME_INSTRUCTION,line);
            System.out.println();
        }
    }



    // titles for the infos
    public static void printBoardLogo(int freeSpaces) {
        String boardLogo[] = {

                "╔╦╗╦ ╦╔═╗  ╔╗ ╔═╗╔═╗╦═╗╔╦╗",
                 "║ ╠═╣║╣   ╠╩╗║ ║╠═╣╠╦╝ ║║",
                 "╩ ╩ ╩╚═╝  ╚═╝╚═╝╩ ╩╩╚══╩╝"};

        printLogo(boardLogo,freeSpaces);
    }

    public static void printBookshelfLogo(int freeSpaces) {
        String[] bookshelfLogo = {
                "╔╦╗╦ ╦  ╔╗ ╔═╗╔═╗╦╔═╔═╗╦ ╦╔═╗╦  ╔═╗",
                "║║║╚╦╝  ╠╩╗║ ║║ ║╠╩╗╚═╗╠═╣║╣ ║  ╠╣",
                "╩ ╩ ╩   ╚═╝╚═╝╚═╝╩ ╩╚═╝╩ ╩╚═╝╩═╝╚"
        };

        printLogo(bookshelfLogo, freeSpaces);
    }

    public static void printGamesRulesLogo(int freeSpaces) {
        String[] gamesRulesLogo = {
                "╔═╗╔═╗╔╦╗╔═╗╔═╗  ╦═╗╦ ╦╦  ╔═╗╔═╗",
                "║ ╦╠═╣║║║║╣ ╚═╗  ╠╦╝║ ║║  ║╣ ╚═╗",
                "╚═╝╩ ╩╩ ╩╚═╝╚═╝  ╩╚═╚═╝╩═╝╚═╝╚═╝"
        };

        printLogo(gamesRulesLogo, freeSpaces);
    }

    public static void printGlobalLobbyPhase(int freeSpaces) {
        String[] globalLobbyPhaseLogo = {
                "╔═╗╦  ╔═╗╔╗ ╔═╗╦    ╦  ╔═╗╔╗ ╔╗ ╦ ╦",
                "║ ╦║  ║ ║╠╩╗╠═╣║    ║  ║ ║╠╩╗╠╩╗╚╦╝",
                "╚═╝╩═╝╚═╝╚═╝╩ ╩╩═╝  ╩═╝╚═╝╚═╝╚═╝ ╩"
        };

        printLogo(globalLobbyPhaseLogo, freeSpaces);
    }

    public static void printGameLobbyPhase(int freeSpaces) {
        String[] gameLobbyPhaseLogo = {
                "╔═╗╔═╗╔╦╗╔═╗  ╦  ╔═╗╔╗ ╔╗ ╦ ╦",
                "║ ╦╠═╣║║║║╣   ║  ║ ║╠╩╗╠╩╗╚╦╝",
                "╚═╝╩ ╩╩ ╩╚═╝  ╩═╝╚═╝╚═╝╚═╝ ╩"
        };

        printLogo(gameLobbyPhaseLogo, freeSpaces);
    }

    public static void printYourTurnPhase() {
        String yourTurnPhaseLogo = """
                
                ╦ ╦╔═╗╦ ╦╦═╗  ╔╦╗╦ ╦╦═╗╔╗╔
                ╚╦╝║ ║║ ║╠╦╝   ║ ║ ║╠╦╝║║║
                 ╩ ╚═╝╚═╝╩╚═   ╩ ╚═╝╩╚═╝╚╝\s
                 
                """;
        out.println(yourTurnPhaseLogo);
    }

    public static void printWaitingTurnPhase(int freeSpaces) {
        String[] waitingTurnPhaseLogo = {
                "╦ ╦╔═╗╦╔╦╗╦╔╗╔╔═╗  ╦ ╦╔═╗╦ ╦╦═╗  ╔╦╗╦ ╦╦═╗╔╗╔",
                "║║║╠═╣║ ║ ║║║║║ ╦  ╚╦╝║ ║║ ║╠╦╝   ║ ║ ║╠╦╝║║║",
                "╚╩╝╩ ╩╩ ╩ ╩╝╚╝╚═╝   ╩ ╚═╝╚═╝╩╚═   ╩ ╚═╝╩╚═╝╚╝"
        };

        printLogo(waitingTurnPhaseLogo, freeSpaces);
    }


    public static void printBoardPhase(int freeSpaces) {
        String[] boardPhaseLogo = {
                "╔╗ ╔═╗╔═╗╦═╗╔╦╗  ╔═╗╦ ╦╔═╗╔═╗╔═╗",
                "╠╩╗║ ║╠═╣╠╦╝ ║║  ╠═╝╠═╣╠═╣╚═╗║╣",
                "╚═╝╚═╝╩ ╩╩╚══╩╝  ╩  ╩ ╩╩ ╩╚═╝╚═╝"
        };

        printLogo(boardPhaseLogo, freeSpaces);
    }

    public static void printOrderPhase(int freeSpaces) {
        String[] orderPhaseLogo = {
                "╔═╗╦═╗╔╦╗╔═╗╦═╗  ╔═╗╦ ╦╔═╗╔═╗╔═╗",
                "║ ║╠╦╝ ║║║╣ ╠╦╝  ╠═╝╠═╣╠═╣╚═╗║╣",
                "╚═╝╩╚══╩╝╚═╝╩╚═  ╩  ╩ ╩╩ ╩╚═╝╚═╝"
        };

        printLogo(orderPhaseLogo, freeSpaces);
    }

    public static void printColumnPhase(int freeSpaces) {
        String[] columnPhaseLogo = {
                "╔═╗╔═╗╦  ╦ ╦╔╦╗╔╗╔  ╔═╗╦ ╦╔═╗╔═╗╔═╗",
                "║  ║ ║║  ║ ║║║║║║║  ╠═╝╠═╣╠═╣╚═╗║╣",
                "╚═╝╚═╝╩═╝╚═╝╩ ╩╝╚╝  ╩  ╩ ╩╩ ╩╚═╝╚═╝"
        };

        printLogo(columnPhaseLogo, freeSpaces);
    }

    public static void printFinalRankingPhase() {
        String finalRankingPhaseLogo = """
                
                                ╔═╗╦╔╗╔╔═╗╦    ╦═╗╔═╗╔╗╔╦╔═╦╔╗╔╔═╗
                                ╠╣ ║║║║╠═╣║    ╠╦╝╠═╣║║║╠╩╗║║║║║ ╦
                                ╚  ╩╝╚╝╩ ╩╩═╝  ╩╚═╩ ╩╝╚╝╩ ╩╩╝╚╝╚═╝
                
                """;
        out.println(finalRankingPhaseLogo);
    } //+ printWinnerLogo



    // drawings :)
    public static void printWinnerLogo(int m) {
        out.println();
        String[] winnerLogo = {
                "                       ────────────█▄─█─▄▀▀▄─────▄█────────────",
                "                       ────────────█─▀█─█──█──────█────────────",
                "                       ────────────▀──▀──▀▀──▀────▀────────────",
                "                       ________¶¶¶¶¶¶¶¶¶¶¶¶¶¶¶¶¶¶¶¶¶¶¶¶¶¶¶\s",
                "                       ___¶¶¶¶¶¶¶¶¶¶¶¶¶¶¶¶¶¶¶¶¶¶¶¶¶¶¶¶¶¶¶¶¶¶¶¶¶\s",
                "                       ¶¶¶_______¶¶¶¶¶¶¶¶¶¶¶¶¶¶¶¶¶¶¶¶¶¶________¶¶¶\s",
                "                       ¶¶¶_____¶¶¶¶¶¶¶¶¶¶¶¶¶¶¶¶¶¶¶¶¶¶¶¶¶______¶¶¶\s",
                "                       _¶¶¶___¶¶¶_¶¶¶¶¶¶¶¶¶¶¶¶¶¶¶¶¶¶¶_¶¶¶____¶¶¶\s",
                "                       ___¶¶¶¶__¶¶¶¶¶¶¶¶¶¶¶¶¶¶¶¶¶¶¶¶¶¶¶_¶¶¶¶¶\s",
                "                       ______¶¶¶¶¶¶__¶¶¶¶¶¶¶¶¶¶¶¶¶¶___¶¶¶¶¶\s",
                "                       _______________¶¶¶¶¶¶¶¶¶¶¶¶\s",
                "                       ___________________¶¶¶¶\s",
                "                       _______________¶¶¶¶¶¶¶¶¶¶¶¶\s",
                "                       ____________¶¶¶¶¶¶¶¶¶¶¶¶¶¶¶¶¶¶\s",
                "                       ____________¶¶¶____________¶¶¶\s",
                "                       ____________¶¶¶¶¶¶¶¶¶¶¶¶¶¶¶¶¶¶\s",
                "                       _________¶¶¶¶¶¶¶¶¶¶¶¶¶¶¶¶¶¶¶¶¶¶¶¶"
        };

        printLogo(winnerLogo, m);
    }


    private static void printDisconnectionForcedLogo(int freeSpaces) {
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

    public static void printLostConnectionLogo(int freeSpaces) {
        String[] lostConnectionLogo = {
                "            ──▄────▄▄▄▄▄▄▄────▄───",
                "            ─▀▀▄─▄█████████▄─▄▀▀──",
                "            ─────██─▀███▀─██──────",
                "            ───▄─▀████▀████▀─▄────",
                "            ─▀█────██▀█▀██────█▀──"
        };

        printLogo(lostConnectionLogo, freeSpaces);
    }


    public static void printErrorLogo(int freeSpaces) {
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

    public static void printErrorLogo2(int freeSpaces) {
        String[] errorLogo = {
                "▄██████████████▄▐█▄▄▄▄█▌",
                "██████▌▄▌▄▐▐▌███▌▀▀██▀▀",
                "████▄█▌▄▌▄▐▐▌▀███▄▄█▌",
                "▄▄▄▄▄██████████████▀",
                "                                                                                                    "
        };

        printLogo(errorLogo, freeSpaces);
    }

    public static void printWaitingLogo(int freeSpaces) {
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
