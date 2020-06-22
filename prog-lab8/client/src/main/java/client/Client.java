package client;

import client.controllers.MainWindowController;
import client.utility.Outputer;
import client.utility.OutputerUI;
import client.utility.ScriptHandler;
import common.data.SpaceMarine;
import common.exceptions.ConnectionErrorException;
import common.exceptions.NotInDeclaredLimitsException;
import common.interaction.Request;
import common.interaction.Response;
import common.interaction.ResponseCode;
import common.interaction.User;

import java.io.*;
import java.net.InetSocketAddress;
import java.nio.channels.SocketChannel;
import java.util.NavigableSet;

/**
 * Runs the client.
 */
public class Client implements Runnable {
    private String host;
    private int port;
    private SocketChannel socketChannel;
    private ObjectOutputStream serverWriter;
    private ObjectInputStream serverReader;
    private User user;
    private boolean isConnected;

    public Client(String host, int port) {
        this.host = host;
        this.port = port;
    }

    @Override
    public void run() {
        try {
            connectToServer();
        } catch (NotInDeclaredLimitsException exception) {
            Outputer.printerror("ClientException");
            System.exit(0);
        } catch (ConnectionErrorException exception) { /* ? */ }
    }

    /**
     * Stops a client.
     */
    public void stop() {
        try {
            processRequestToServer(MainWindowController.EXIT_COMMAND_NAME, "", null);
            socketChannel.close();
            Outputer.println("EndWorkOfClient");
        } catch (IOException | NullPointerException exception) {
            Outputer.printerror("EndWorkOfClientException");
            if (socketChannel == null) Outputer.printerror("EndRunningWorkOfClientException");
        }
    }

    /**
     * Server request process.
     *
     * @param commandName           Command name.
     * @param commandStringArgument Command string argument.
     * @param commandObjectArgument Command object argument.
     * @return Collection.
     */
    public NavigableSet<SpaceMarine> processRequestToServer(String commandName, String commandStringArgument,
                                                            Serializable commandObjectArgument) {
        Request requestToServer = null;
        Response serverResponse = null;
        try {
            requestToServer = new Request(commandName, commandStringArgument, commandObjectArgument, user);
            serverWriter.writeObject(requestToServer);
            serverResponse = (Response) serverReader.readObject();
            if (!serverResponse.getResponseBody().isEmpty())
                OutputerUI.tryError(serverResponse.getResponseBody(), serverResponse.getResponseBodyArgs());
        } catch (InvalidClassException | NotSerializableException exception) {
            OutputerUI.error("DataSendingException");
        } catch (ClassNotFoundException exception) {
            OutputerUI.error("DataReadingException");
        } catch (IOException exception) {
            if (requestToServer.getCommandName().equals(MainWindowController.EXIT_COMMAND_NAME)) return null;
            OutputerUI.error("EndConnectionToServerException");
            try {
                connectToServer();
                OutputerUI.info("ConnectionToServerComplete");
            } catch (ConnectionErrorException | NotInDeclaredLimitsException reconnectionException) {
                OutputerUI.info("TryCommandLater");
            }
        }
        return serverResponse == null ? null : serverResponse.getMarinesCollection();
    }

    /**
     * Server script process.
     *
     * @param scriptFile Scipt file.
     * @return Is everything OK.
     */
    public boolean processScriptToServer(File scriptFile) {
        Request requestToServer = null;
        Response serverResponse = null;
        ScriptHandler scriptHandler = new ScriptHandler(scriptFile);
        do {
            try {
                requestToServer = serverResponse != null ? scriptHandler.handle(serverResponse.getResponseCode(), user) :
                        scriptHandler.handle(null, user);
                if (requestToServer == null) return false;
                if (requestToServer.isEmpty()) continue;
                serverWriter.writeObject(requestToServer);
                serverResponse = (Response) serverReader.readObject();
                if (!serverResponse.getResponseBody().isEmpty())
                    OutputerUI.tryError(serverResponse.getResponseBody(), serverResponse.getResponseBodyArgs());
            } catch (InvalidClassException | NotSerializableException exception) {
                OutputerUI.error("DataSendingException");
            } catch (ClassNotFoundException exception) {
                OutputerUI.error("DataReadingException");
            } catch (IOException exception) {
                OutputerUI.error("EndConnectionToServerException");
                try {
                    connectToServer();
                    OutputerUI.info("ConnectionToServerComplete");
                } catch (ConnectionErrorException | NotInDeclaredLimitsException reconnectionException) {
                    OutputerUI.info("TryCommandLater");
                }
            }
        } while (!requestToServer.getCommandName().equals("exit"));
        return true;
    }

    /**
     * Handle process authentication.
     *
     * @param username Username.
     * @param password Password.
     * @param register Register.
     * @return Is everything OK.
     */
    public boolean processAuthentication(String username, String password, boolean register) {
        // TODO: Переместить все в один метод (?)
        Request requestToServer = null;
        Response serverResponse = null;
        String command;
        try {
            command = register ? MainWindowController.REGISTER_COMMAND_NAME : MainWindowController.LOGIN_COMMAND_NAME;
            requestToServer = new Request(command, "", new User(username, password));
            if (serverWriter == null) throw new IOException();
            serverWriter.writeObject(requestToServer);
            serverResponse = (Response) serverReader.readObject();
            OutputerUI.tryError(serverResponse.getResponseBody(), serverResponse.getResponseBodyArgs());
        } catch (InvalidClassException | NotSerializableException exception) {
            OutputerUI.error("DataSendingException");
        } catch (ClassNotFoundException exception) {
            OutputerUI.error("DataReadingException");
        } catch (IOException exception) {
            OutputerUI.error("EndConnectionToServerException");
            try {
                connectToServer();
                OutputerUI.info("ConnectionToServerComplete");
            } catch (ConnectionErrorException | NotInDeclaredLimitsException reconnectionException) {
                OutputerUI.info("TryAuthLater");
            }
        }
        if (serverResponse != null && serverResponse.getResponseCode().equals(ResponseCode.OK)) {
            user = requestToServer.getUser();
            return true;
        }
        return false;
    }

    /**
     * Checking to connected of client.
     *
     * @return Status of connection.
     */
    public boolean isConnected() {
        return isConnected;
    }

    public String getUsername() {
        return user == null ? null : user.getUsername();
    }

    /**
     * Connecting to server.
     */
    private void connectToServer() throws ConnectionErrorException, NotInDeclaredLimitsException {
        try {
            Outputer.println("ConnectionToServer");
            socketChannel = SocketChannel.open(new InetSocketAddress(host, port));
            serverWriter = new ObjectOutputStream(socketChannel.socket().getOutputStream());
            serverReader = new ObjectInputStream(socketChannel.socket().getInputStream());
            isConnected = true;
            Outputer.println("ConnectionToServerComplete");
        } catch (IllegalArgumentException exception) {
            Outputer.printerror("ServerAddressException");
            isConnected = false;
            throw new NotInDeclaredLimitsException();
        } catch (IOException exception) {
            Outputer.printerror("ConnectionToServerException");
            isConnected = false;
            throw new ConnectionErrorException();
        }
    }
}
