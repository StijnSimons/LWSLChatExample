package xyz.baddeveloper.lwslchatexample.client;

import org.json.JSONObject;
import xyz.baddeveloper.lwsl.client.SocketClient;
import xyz.baddeveloper.lwsl.client.exceptions.ConnectException;
import xyz.baddeveloper.lwsl.packet.Packet;

import java.util.Scanner;

public class Client {

    private SocketClient socketClient;

    public static void main(String[] args) {
        new Client().connect();
    }

    private void connect() {
        socketClient = new SocketClient("localhost", 7879)
                .addConnectEvent((event) -> System.out.println("Connected to the server."))
                .addPacketReceivedEvent(((serverSocket, packet) -> System.out.println(packet.getObject().getString("message"))));

        try {
            socketClient.connect();

            final Scanner scanner = new Scanner(System.in);
            while (socketClient.isConnected())
                socketClient.sendPacket(new Packet(new JSONObject().put("message", scanner.nextLine())));
        } catch (ConnectException e) {
            e.printStackTrace();
        }
    }
}
