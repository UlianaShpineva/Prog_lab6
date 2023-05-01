package utility;

import exceptions.ConnectionErrorException;
import exceptions.OpeningServerException;
import managers.CollectionManager;
import managers.FileManager;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.net.*;
import java.util.HashMap;
import java.util.Map;

public class DatagramServer extends Server {
    private final DatagramSocket socket;
    private int soTimeout;
    private FileManager fileManager;
    private CollectionManager collectionManager;
    static final Logger datagramServerLogger = LogManager.getLogger(DatagramServer.class);
    public DatagramServer(InetAddress address, int port, int soTimeout, RequestHandler requestHandler, FileManager fileManager, CollectionManager collectionManager) throws SocketException {
        super(new InetSocketAddress(address, port), soTimeout, requestHandler, fileManager, collectionManager);
        this.socket = new DatagramSocket(getAddr());
        this.socket.setReuseAddress(true);
        this.soTimeout = soTimeout;
    }


    @Override
    public Pair receiveData() throws IOException {
        boolean received = false;
        byte[] res = new byte[0];
        SocketAddress addr = null;
        Pair pair = new Pair(res, addr);
        while (!received) {
            byte[] buffer = new byte[102400000];
            DatagramPacket packet = new DatagramPacket(buffer, buffer.length);

            socket.receive(packet);
            addr = packet.getSocketAddress();
            received = true;
            res = buffer;

        }
        pair.setDataAndAddr(res, addr);
        return  pair;
    }


    @Override
    public void sendData(byte[] data, SocketAddress addr) throws IOException {
        DatagramPacket packet = new DatagramPacket(data, data.length, addr);
        socket.send(packet);
    }

    @Override
    public void connectToClient(SocketAddress addr) throws SocketException {
        socket.connect(addr);
        //socket.setSoTimeout(100);
    }

    @Override
    public void disconnectFromClient() {
        datagramServerLogger.info("Соединение разорвано");
        socket.disconnect();
    }

    @Override
    public void close() {
        socket.close();
    }
}
