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



    // titles for the infos
    public static void printBoardLogo() {
        String boardLogo = """

                ╔╦╗╦ ╦╔═╗  ╔╗ ╔═╗╔═╗╦═╗╔╦╗
                 ║ ╠═╣║╣   ╠╩╗║ ║╠═╣╠╦╝ ║║
                 ╩ ╩ ╩╚═╝  ╚═╝╚═╝╩ ╩╩╚══╩╝
                                                                                                                      \s
                """;
        out.println(boardLogo);
    }

    public static void printBookshelfLogo() {
        String myBookshelfLogo = """

                ╔╦╗╦ ╦  ╔╗ ╔═╗╔═╗╦╔═╔═╗╦ ╦╔═╗╦  ╔═╗
                ║║║╚╦╝  ╠╩╗║ ║║ ║╠╩╗╚═╗╠═╣║╣ ║  ╠╣\s
                ╩ ╩ ╩   ╚═╝╚═╝╚═╝╩ ╩╚═╝╩ ╩╚═╝╩═╝╚ \s
                                                                                                                      \s
                """;
        out.println(myBookshelfLogo);
    }

    public static void printGamesRulesLogo() {
        String gamesRulesLogo = """

                ╔═╗╔═╗╔╦╗╔═╗╔═╗  ╦═╗╦ ╦╦  ╔═╗╔═╗
                ║ ╦╠═╣║║║║╣ ╚═╗  ╠╦╝║ ║║  ║╣ ╚═╗
                ╚═╝╩ ╩╩ ╩╚═╝╚═╝  ╩╚═╚═╝╩═╝╚═╝╚═╝
                                                                                                                      \s
                """;
        out.println(gamesRulesLogo);
    }



    // titles for the phases of the game and the lobby
    public static void printGlobalLobbyPhase() {
        String globalLobbyPhaseLogo = """
                
                                            ╔═╗╦  ╔═╗╔╗ ╔═╗╦    ╦  ╔═╗╔╗ ╔╗ ╦ ╦
                                            ║ ╦║  ║ ║╠╩╗╠═╣║    ║  ║ ║╠╩╗╠╩╗╚╦╝
                                            ╚═╝╩═╝╚═╝╚═╝╩ ╩╩═╝  ╩═╝╚═╝╚═╝╚═╝ ╩\s
                
                """;
        out.println(globalLobbyPhaseLogo);
    }

    public static void printGameLobbyPhase() {
        String gameLobbyPhaseLogo = """
                
                                               ╔═╗╔═╗╔╦╗╔═╗  ╦  ╔═╗╔╗ ╔╗ ╦ ╦
                                               ║ ╦╠═╣║║║║╣   ║  ║ ║╠╩╗╠╩╗╚╦╝
                                               ╚═╝╩ ╩╩ ╩╚═╝  ╩═╝╚═╝╚═╝╚═╝ ╩\s
                
                """;
        out.println(gameLobbyPhaseLogo);
    }

    public static void printYourTurnPhase() {
        String yourTurnPhaseLogo = """
                
                ╦ ╦╔═╗╦ ╦╦═╗  ╔╦╗╦ ╦╦═╗╔╗╔
                ╚╦╝║ ║║ ║╠╦╝   ║ ║ ║╠╦╝║║║
                 ╩ ╚═╝╚═╝╩╚═   ╩ ╚═╝╩╚═╝╚╝\s
                 
                """;
        out.println(yourTurnPhaseLogo);
    }

    public static void printWaitingTurnPhase() {
        String waitingTurnPhaseLogo = """
                
                ╦ ╦╔═╗╦╔╦╗╦╔╗╔╔═╗  ╦ ╦╔═╗╦ ╦╦═╗  ╔╦╗╦ ╦╦═╗╔╗╔
                ║║║╠═╣║ ║ ║║║║║ ╦  ╚╦╝║ ║║ ║╠╦╝   ║ ║ ║╠╦╝║║║
                ╚╩╝╩ ╩╩ ╩ ╩╝╚╝╚═╝   ╩ ╚═╝╚═╝╩╚═   ╩ ╚═╝╩╚═╝╚╝\s
                
                """;
        out.println(waitingTurnPhaseLogo);
    } //+ printWaitingLogo

    public static void printBoardPhase() {
        String boardPhaseLogo = """
                
                ╔╗ ╔═╗╔═╗╦═╗╔╦╗  ╔═╗╦ ╦╔═╗╔═╗╔═╗
                ╠╩╗║ ║╠═╣╠╦╝ ║║  ╠═╝╠═╣╠═╣╚═╗║╣\s
                ╚═╝╚═╝╩ ╩╩╚══╩╝  ╩  ╩ ╩╩ ╩╚═╝╚═╝
                
                """;
        out.println(boardPhaseLogo);
    }

    public static void printOrderPhase() {
        String orderPhaseLogo = """
                
                ╔═╗╦═╗╔╦╗╔═╗╦═╗  ╔═╗╦ ╦╔═╗╔═╗╔═╗
                ║ ║╠╦╝ ║║║╣ ╠╦╝  ╠═╝╠═╣╠═╣╚═╗║╣\s
                ╚═╝╩╚══╩╝╚═╝╩╚═  ╩  ╩ ╩╩ ╩╚═╝╚═╝

                """;
        out.println(orderPhaseLogo);
    }

    public static void printColumnPhase() {
        String columnPhaseLogo = """
                
                ╔═╗╔═╗╦  ╦ ╦╔╦╗╔╗╔  ╔═╗╦ ╦╔═╗╔═╗╔═╗
                ║  ║ ║║  ║ ║║║║║║║  ╠═╝╠═╣╠═╣╚═╗║╣\s
                ╚═╝╚═╝╩═╝╚═╝╩ ╩╝╚╝  ╩  ╩ ╩╩ ╩╚═╝╚═╝
                
                """;
        out.println(columnPhaseLogo);
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
    public static void printWinnerLogo() {
        String winnerLogo = """


                ────────────█▄─█─▄▀▀▄─────▄█────────────
                ────────────█─▀█─█──█──────█────────────
                ────────────▀──▀──▀▀──▀────▀────────────
                ________¶¶¶¶¶¶¶¶¶¶¶¶¶¶¶¶¶¶¶¶¶¶¶¶¶¶\s
                ___¶¶¶¶¶¶¶¶¶¶¶¶¶¶¶¶¶¶¶¶¶¶¶¶¶¶¶¶¶¶¶¶¶¶¶¶¶\s
                ¶¶¶_______¶¶¶¶¶¶¶¶¶¶¶¶¶¶¶¶¶¶¶¶¶¶________¶¶¶\s
                ¶¶¶_____¶¶¶¶¶¶¶¶¶¶¶¶¶¶¶¶¶¶¶¶¶¶¶¶¶¶______¶¶¶\s
                _¶¶¶___¶¶¶_¶¶¶¶¶¶¶¶¶¶¶¶¶¶¶¶¶¶¶¶_¶¶¶____¶¶¶\s
                ___¶¶¶¶__¶¶¶¶¶¶¶¶¶¶¶¶¶¶¶¶¶¶¶¶¶¶¶¶¶_¶¶¶¶¶\s
                ______¶¶¶¶¶¶__¶¶¶¶¶¶¶¶¶¶¶¶¶¶___¶¶¶¶¶¶\s
                _______________¶¶¶¶¶¶¶¶¶¶¶¶\s
                ___________________¶¶¶¶\s
                _______________¶¶¶¶¶¶¶¶¶¶¶¶\s
                ____________¶¶¶¶¶¶¶¶¶¶¶¶¶¶¶¶¶¶\s
                ____________¶¶¶____________¶¶¶\s
                ____________¶¶¶¶¶¶¶¶¶¶¶¶¶¶¶¶¶¶\s
                _________¶¶¶¶¶¶¶¶¶¶¶¶¶¶¶¶¶¶¶¶¶¶¶¶
                                                                                                                      \s
                """;
        out.println(winnerLogo);
    }

    private static void printDisconnectionForcedLogo() {
        String disconnectionForcedLogo = """

                ▒▒▒▒▒▒▒▒▒▒▒▄▄▄▄░▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒
                ▒▒▒▒▒▒▒▄██▀░░▀██▄▒▒▒▒████████▄▒▒▒▒▒▒
                ▒▒▒▒▒▒███░░░░░░██▒▒▒▒▒▒█▀▀▀▀▀██▄▄▒▒▒
                ▒▒▒▒▒███░░▐█░█▌░██▒▒▒▒█▌▒▒▒▒▒▒▒▒▒▒▀▌
                ▒▒▒▒████░▐█▌░▐█▌██▒▒▒██▒▒▒▒▒▒▒▒▒▒▒▒▒
                ▒▒▒▐████░▐░░░░░▌██▒▒▒█▌▒▒▒▒▒▒▒▒▒▒▒▒▒
                ▒▒▒▒████░░░▄█░░░██▒▒▐█▒▒▒▒▒▒▒▒▒▒▒▒▒▒
                ▒▒▒▒████▌░▐█░░███▒▒▒█▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒
                ▒▒▒▒▐████░░▌░███▒▒▒██▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒
                ▒▒▒██████▌░████▒▒▒██▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒
                ▒▐████████████▒▒███▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒
                ▒█████████████▄████▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒
                █████████████████▀▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒
                ████████████████▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒
                                                                                                                      \s
                """;
        out.println(disconnectionForcedLogo);
    }

    public static void printLostConnectionLogo() {
        String lostConnectionLogo = """

                ──▄────▄▄▄▄▄▄▄────▄───
                ─▀▀▄─▄█████████▄─▄▀▀──
                ─────██─▀███▀─██──────
                ───▄─▀████▀████▀─▄────
                ─▀█────██▀█▀██────█▀──
                                                                                                           \s
                """;
        out.println(lostConnectionLogo);
    }

    public static void printErrorLogo() {
        String errorLogo = """

                ░░░░░░░▄▄▄▄▄▄░░░░░░░░░░░░░░░░░░░
                ░░░░░▄█████████▄▄░░░░░░░░▄▄░░░░░
                ░░░░███████████████▄▄░░░░▄██▄░░░
                ░▄██████████████████████████▀░░░
                ░███░░░░░░░░░░░░░░░░░░░█░░░░░░░░
                ░███░███████████████████░░░░░░░░
                ░███░░▀████───████────█▀░░░░░░░░
                ░███░░░▀▄▄▄▄▄▀░▀▄▄▄▄▄▀▀▀▄░░░░░░░
                ░░██░░░░░░░░░░░░░░░░░░░░░▀▄░░░░░
                ░░▄█░░░░░▄▀█▀▀▀▄▄▄░░░░░▄▀░░▀▄░░░
                ░█░░░░░▄▀──█───█──▀█▄▄░▀▀░░░░▀▄░
                ░▀▄▄░░█────█───█───█──▀▀▄░░▄▀▀▀░
                ░░░█░██▄▄▄▄█▄▄▄█▄▄▄█▄▄▄▄▄█▄▄▀░░░
                ░░░█░█████████████▄▄░░░░░░░░░░░░
                ░░░█░█████████████████████████▄░
                ░░░█░██████████▀▀░░░░░░░░░░░░░█░
                ░░░█░███▀░░░░░░░░░░░░░░░░░░░░░█░
                ░░░█░░░░░░░░░░░░░░▀▄░▀▄░░░░░░░█░
                ░░░█░░░░░░░░░░░░▄▄▄▀▄▄▀░░░░░░░█░
                ░░░█░░░░░░░░░░░░░░░░░░░░░░░░░░█░
                ░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░
                ░█▀▄░█░█▄░█░█░█░█░░█▀░█▀▄░█▀░█▀▄░▄▀▀░█░
                ░█░█░█░█▀██░█▀▄░█░░█▀░█▀▄░█▀░█▀▄░█░█░▀░
                ░▀▀░░▀░▀░░▀░▀░▀░▀▀░▀▀░▀▀░░▀▀░▀░▀░░▀▀░▀░
                ░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░
                                                                                                                      \s
                """;
        out.println(errorLogo);
    }

    public static void printErrorLogo2() {
        String errorLogo = """

▄██████████████▄▐█▄▄▄▄█▌
██████▌▄▌▄▐▐▌███▌▀▀██▀▀
████▄█▌▄▌▄▐▐▌▀███▄▄█▌
▄▄▄▄▄██████████████▀
                                                                                                    \s
                """;
        out.println(errorLogo);
    }

    public static void printWaitingLogo() {
        String waitingLogo = """
                ───▄▄▄
                ─▄▀░▄░▀▄
                ─█░█▄▀░█
                ─█░▀▄▄▀█▄█▄▀
                ▄▄█▄▄▄▄███▀
                                
                """;
        out.println(waitingLogo);
    }

}
