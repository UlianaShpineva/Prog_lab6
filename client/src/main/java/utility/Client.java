package utility;

import console.Console;
import network.Request;
import network.Response;
import network.ResponseStatus;

import java.io.*;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.SocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.DatagramChannel;
import java.util.Objects;


public class Client {
    private DatagramChannel client;
    private SocketAddress addr;
    private InetAddress host;
    private int port;
    private int reconnectionTimeout;
    private int reconnectionAttempts;
    private int maxReconnectionAttempts;
    private Console console;

    private ByteArrayOutputStream bStream;
    private ObjectInputStream iStream;
    //ObjectOutput oo;
    //private ObjectOutputStream serverWriter;
    //private ObjectInputStream serverReader;

    public Client(InetAddress host, int port, Console console) throws IOException {
        this.host = host;
        this.port = port;
        this.console = console;

        this.addr = new InetSocketAddress(host, port);
        this.client = DatagramChannel.open().bind(null).connect(addr);
        this.client.configureBlocking(false);
    }

    public Response sendAndAskResponse(Request request) throws IOException {
        if (request.isEmpty()) return new Response(ResponseStatus.WRONG_ARGUMENTS, "Запрос пустой!");

        bStream = new ByteArrayOutputStream();
        ObjectOutput oo = new ObjectOutputStream(bStream);
        oo.writeObject(request);
        oo.flush();
        oo.close();
        byte[] serializedMessage = bStream.toByteArray();
        ByteBuffer buf = ByteBuffer.wrap(serializedMessage);
        client.send(buf, this.addr);
        buf.clear();

        ByteBuffer receiveBuf = ByteBuffer.allocate(65000);
        SocketAddress address = null;
        while (address == null) {
            address = client.receive(receiveBuf);
        }

        receiveBuf.flip();
        byte[] bytes = new byte[receiveBuf.remaining()];
        receiveBuf.get(bytes);

        byte[] toDeserialize = receiveBuf.array();
        this.iStream = new ObjectInputStream(new ByteArrayInputStream(toDeserialize));
        Response response = null;
        try {
            response = (Response) iStream.readObject();
        } catch (ClassNotFoundException ex) {
            console.printError(String.valueOf(ex));
        }
        iStream.close();
        return response;
    }

//            } catch (IOException e) {
//                if (reconnectionAttempts == 0){
//                    connectToServer();
//                    reconnectionAttempts++;
//                    continue;
//                } else {
//                    console.printError("Соединение с сервером разорвано");
//                }
//                try {
//                    reconnectionAttempts++;
//                    if (reconnectionAttempts >= maxReconnectionAttempts) {
//                        console.printError("Превышено максимальное количество попыток соединения с сервером");
//                        return new Response(ResponseStatus.EXIT);
//                    }
//                    console.println("Повторная попытка через " + reconnectionTimeout / 1000 + " секунд");
//                    Thread.sleep(reconnectionTimeout);
//                    connectToServer();
//                } catch (Exception exception) {
//                    console.printError("Попытка соединения с сервером неуспешна");
//                }
//            } catch (ClassNotFoundException e) {
//                console.printError("Неизвестная ошибка");
//            }
        //}


    public void connectToServer()  {
        //if (!client.isConnected()) {
         //   client.connect(addr);
        //}

        try{
            if(reconnectionAttempts > 0) console.println("Попытка повторного подключения");
            this.addr = new InetSocketAddress(host, port);
            //console.println(addr.toString());
            this.client = DatagramChannel.open().bind(null).connect(addr);
            //console.println(client.toString());
            this.client.configureBlocking(false);
        } catch (IllegalArgumentException e){
            console.printError("Адрес сервера введен некорректно");
        } catch (IOException e) {
            console.printError("Произошла ошибка при соединении с сервером");
        }
    }

    public void disconnectFromServer(){
        try {
            client.disconnect();
            client.close();
        } catch (IOException e) {
            console.printError("Не подключен к серверу");
        }
    }
}
