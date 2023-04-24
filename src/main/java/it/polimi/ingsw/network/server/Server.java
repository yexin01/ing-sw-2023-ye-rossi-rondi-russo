package it.polimi.ingsw.network.server;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;
import java.util.Scanner;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Server implements Runnable {

    //TODO: importa da Json le porte
    private int socketPort = 20201;
    private int rmiPort = 20202;
    private static Server instance;
    private final ExecutorService executorService = Executors.newCachedThreadPool();
    static final int maxRetries = 20;



    protected Server() throws RemoteException {
    }

    public static Server getInstance() throws RemoteException {
        if (instance == null) {
            instance = new Server();
        }
        return instance;
    }

    public static void main(String[] args) {

        Thread rmiThread = new Thread(() -> {
            try {
                startRMI();
            } catch (RemoteException e) {
                System.err.println("Cannot start RMI. This protocol will be disabled.");
            }
        });
        rmiThread.start();

        Thread socketThread = new Thread(() -> {
            Scanner sc = new Scanner(System.in);
            int port = 12345;
            boolean portCorrect = false;
            System.out.println("Insert the port: ");
            while (!portCorrect) {
                try {
                    System.out.print("> ");
                    port = Integer.parseInt(sc.next());
                    if (port < 1024 || port > 65535) System.out.println("Port must be between 1024 and 65535, retry");
                    else portCorrect = true;
                } catch (NumberFormatException e) {
                    System.out.println("Port must be a number, retry");
                }
            }
            try {
                //TODO if port is already in use, retry
                it.polimi.ingsw.server.Server server = new ServerImpl(port);
                System.out.println("Server started on port " + port);
                startMyTimer();
                startSocket(port);
            } catch (RemoteException e) {
                System.err.println("Cannot start socket. This protocol will be disabled.");
            }
        });
        socketThread.start();

        try {
            rmiThread.join();
            socketThread.join();
        } catch (InterruptedException e) {
            System.err.println("No connection protocol available. Exiting...");
        }
    }

    private void startServers() {
        SocketServer serverSocket = new SocketServer(this, socketPort);
        serverSocket.startServer();

        System.out.println("Socket Server Started");

        RMIServer rmiServer = new RMIServer(this, rmiPort);
        rmiServer.startServer();

        System.out.println("RMI Server Started");
    }

    /*
    private static void startRMI() throws RemoteException {
        Server server = getInstance();

        Registry registry = LocateRegistry.getRegistry();
        registry.rebind("server", server);
    }

     */

    //TODO controllare se c'è tutto in socketconnection e socketserver
    /*
    public static void startSocket(int port) throws RemoteException {
        Server instance = getInstance();
        try (ServerSocket serverSocket = new ServerSocket(port)) {
            while (true) {
                Socket socket = serverSocket.accept();
                instance.executorService.submit(() -> {
                    try {
                        ClientSkeleton clientSkeleton = new ClientSkeleton(socket);
                        it.polimi.ingsw.server.Server server = new ServerImpl();
                        server.register(clientSkeleton);
                        while (true) {
                            clientSkeleton.receive(server);
                        }
                    } catch (RemoteException e) {
                        System.err.println("Cannot receive from client. Closing this connection...");
                    } finally {
                        try {
                            socket.close();
                        } catch (IOException e) {
                            System.err.println("Cannot close socket");
                        }
                    }
                });
            }
        } catch (IOException e) {
            throw new RemoteException("Cannot start socket server", e);
        }
    }

     */

    /**
     * Process that pings all the clients to check if they are still connected
     */
    @Override
    public void run() {
        while (!Thread.currentThread().isInterrupted()) {
            synchronized (clientsLock) {
                for (Map.Entry<String, Connection> client : clients.entrySet()) {
                    if (client.getValue() != null && client.getValue().isConnected()) {
                        client.getValue().ping();
                    }
                }
            }

            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                LOGGER.severe(e.getMessage());
                Thread.currentThread().interrupt();
            }
        }
    }

    static void startMyTimer() {
        Timer timer = new Timer();

        // lambda we pass down (to show another way to be called back by another class)
        TimeOutCheckerInterface timeOutChecker = (l) -> {
            System.out.println(l);
            boolean timeoutReached = l>maxRetries;
            if (timeoutReached){
                System.out.println("Got timeout inside server class");
                return true;
            }
            return false;
        };

        TimerTask task = new TimeoutCounter(timeOutChecker);
        int initialDelay = 50;
        int delta = 1000;
        timer.schedule(task, initialDelay, delta);
    }


}


