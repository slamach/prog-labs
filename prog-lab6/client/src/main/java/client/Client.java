package client;

import client.utility.UserHandler;
import common.exceptions.ConnectionErrorException;
import common.exceptions.NotInDeclaredLimitsException;
import common.interaction.Request;
import common.interaction.Response;
import common.utility.Outputer;

import java.io.*;
import java.net.InetSocketAddress;
import java.nio.channels.SocketChannel;

/**
 * Runs the client.
 */
public class Client {
    private String host;
    private int port;
    private int reconnectionTimeout;
    private int reconnectionAttempts;
    private int maxReconnectionAttempts;
    private UserHandler userHandler;
    private SocketChannel socketChannel;
    private ObjectOutputStream serverWriter;
    private ObjectInputStream serverReader;

    public Client(String host, int port, int reconnectionTimeout, int maxReconnectionAttempts, UserHandler userHandler) {
        this.host = host;
        this.port = port;
        this.reconnectionTimeout = reconnectionTimeout;
        this.maxReconnectionAttempts = maxReconnectionAttempts;
        this.userHandler = userHandler;
    }

    /**
     * Begins client operation.
     */
    public void run() {
        try {
            boolean processingStatus = true;
            while (processingStatus) {
                try {
                    connectToServer();
                    processingStatus = processRequestToServer();
                } catch (ConnectionErrorException exception) {
                    if (reconnectionAttempts >= maxReconnectionAttempts) {
                        Outputer.printerror("Превышено количество попыток подключения!");
                        break;
                    }
                    try {
                        Thread.sleep(reconnectionTimeout);
                    } catch (IllegalArgumentException timeoutException) {
                        Outputer.printerror("Время ожидания подключения '" + reconnectionTimeout +
                                "' находится за пределами возможных значений!");
                        Outputer.println("Повторное подключение будет произведено немедленно.");
                    } catch (Exception timeoutException) {
                        Outputer.printerror("Произошла ошибка при попытке ожидания подключения!");
                        Outputer.println("Повторное подключение будет произведено немедленно.");
                    }
                }
                reconnectionAttempts++;
            }
            if (socketChannel != null) socketChannel.close();
            Outputer.println("Работа клиента успешно завершена.");
        } catch (NotInDeclaredLimitsException exception) {
            Outputer.printerror("Клиент не может быть запущен!");
        } catch (IOException exception) {
            Outputer.printerror("Произошла ошибка при попытке завершить соединение с сервером!");
        }
    }

    /**
     * Connecting to server.
     */
    private void connectToServer() throws ConnectionErrorException, NotInDeclaredLimitsException {
        try {
            if (reconnectionAttempts >= 1) Outputer.println("Повторное соединение с сервером...");
            socketChannel = SocketChannel.open(new InetSocketAddress(host, port));
            Outputer.println("Соединение с сервером успешно установлено.");
            Outputer.println("Ожидание разрешения на обмен данными...");
            serverWriter = new ObjectOutputStream(socketChannel.socket().getOutputStream());
            serverReader = new ObjectInputStream(socketChannel.socket().getInputStream());
            Outputer.println("Разрешение на обмен данными получено.");
        } catch (IllegalArgumentException exception) {
            Outputer.printerror("Адрес сервера введен некорректно!");
            throw new NotInDeclaredLimitsException();
        } catch (IOException exception) {
            Outputer.printerror("Произошла ошибка при соединении с сервером!");
            throw new ConnectionErrorException();
        }
    }

    /**
     * Server request process.
     */
    private boolean processRequestToServer() {
        Request requestToServer = null;
        Response serverResponse = null;
        do {
            try {
                requestToServer = serverResponse != null ? userHandler.handle(serverResponse.getResponseCode()) :
                        userHandler.handle(null);
                if (requestToServer.isEmpty()) continue;
                serverWriter.writeObject(requestToServer);
                serverResponse = (Response) serverReader.readObject();
                Outputer.print(serverResponse.getResponseBody());
            } catch (InvalidClassException | NotSerializableException exception) {
                Outputer.printerror("Произошла ошибка при отправке данных на сервер!");
            } catch (ClassNotFoundException exception) {
                Outputer.printerror("Произошла ошибка при чтении полученных данных!");
            } catch (IOException exception) {
                Outputer.printerror("Соединение с сервером разорвано!");
                try {
                    reconnectionAttempts++;
                    connectToServer();
                } catch (ConnectionErrorException | NotInDeclaredLimitsException reconnectionException) {
                    if (requestToServer.getCommandName().equals("exit"))
                        Outputer.println("Команда не будет зарегистрирована на сервере.");
                    else Outputer.println("Попробуйте повторить команду позднее.");
                }
            }
        } while (!requestToServer.getCommandName().equals("exit"));
        return false;
    }
}
