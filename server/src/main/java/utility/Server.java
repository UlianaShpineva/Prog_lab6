package utility;

import managers.CollectionManager;
import managers.FileManager;
import network.Request;
import network.Response;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.*;
import java.net.InetSocketAddress;
import java.net.SocketAddress;
import java.net.SocketException;

abstract class Server {
    private int soTimeout;
    private Printable console = new utility.Console();
    private final RequestHandler requestHandler;
    private managers.FileManager fileManager;
    private CollectionManager collectionManager;

    private InetSocketAddress addr;
    private boolean running = true;

    BufferedInputStream bf = new BufferedInputStream(System.in);

    BufferedReader scanner = new BufferedReader(new InputStreamReader(bf));
    static final Logger serverLogger = LogManager.getLogger(Server.class);

    public Server(InetSocketAddress addr, int soTimeout, RequestHandler requestHandler, FileManager fileManager, CollectionManager collectionManager) { //InetAddress host, int port, int soTimeout, RequestHandler handler, FileManager fileManager) {
        this.addr = addr;
        this.requestHandler = requestHandler;
        this.soTimeout = soTimeout;
        this.fileManager = fileManager;
        this.collectionManager = collectionManager;
    }




    public InetSocketAddress getAddr() {
        return addr;
    }

    public abstract Pair receiveData() throws IOException;

    /**
     * Отправляет данные клиенту
     */
    public abstract void sendData(byte[] data, SocketAddress addr) throws IOException;

    public abstract void connectToClient(SocketAddress addr) throws SocketException;

    public abstract void disconnectFromClient();
    public abstract void close();

    public void stop() {
        running = false;
    }

    public void run() throws IOException {
        while (running) {
            Pair pair;
            try {
                pair = receiveData();
            } catch (Exception e) {
                disconnectFromClient();
                continue;
            }

            if (scanner.ready()) {
                String line = scanner.readLine();
                if (line.equals("save") || line.equals("s")) {
                    fileManager.writeCollection(collectionManager.getCollection());
                }
            }
            byte[] dataFromClient = pair.getData();
            SocketAddress clientAddr = pair.getAddr();

            try {
                connectToClient(clientAddr);
                serverLogger.info("Соединение установлено");
            } catch (Exception e) {
                serverLogger.info("Ошибка подключения");
            }

            Request request = null;
            try {
                ByteArrayInputStream bais = new ByteArrayInputStream(dataFromClient);
                ObjectInputStream ois = new ObjectInputStream(bais);
                request = (Request) ois.readObject();
                serverLogger.info("Получен реквест с командой " + request.getCommandName(), request);
            } catch (IOException e) {
                disconnectFromClient();
                continue;
            } catch (ClassNotFoundException e) {
                serverLogger.info("Произошла ошибка при чтении полученных данных!");
            }

            Response response = null;
            try {
                response = requestHandler.handle(request);
            } catch (Exception e) {
            }
            ByteArrayOutputStream bStream = new ByteArrayOutputStream();
            ObjectOutput oo = new ObjectOutputStream(bStream);
            oo.writeObject(response);
            oo.flush();
            oo.close();
            byte[] data = bStream.toByteArray();

            try {
                sendData(data, clientAddr);
                serverLogger.info("Отправлен ответ", response);
            } catch (Exception e) {
            }

            disconnectFromClient();

        }

        close();
    }
}