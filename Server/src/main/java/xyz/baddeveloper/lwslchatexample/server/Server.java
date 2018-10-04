package xyz.baddeveloper.lwslchatexample.server;

import org.json.JSONObject;
import xyz.baddeveloper.lwsl.packet.Packet;
import xyz.baddeveloper.lwsl.server.SocketServer;

public class Server {

    private SocketServer socketServer;

    public static void main(String[] args) {
        new Server().start();
    }

    private void start() {
        socketServer = new SocketServer(7879)
                .setMaxConnections(20)
                .addReadyEvent((event) -> System.out.println("Server is ready!"))
                .addPacketReceivedEvent((socket, packet) -> socketServer.getController().sendPacketToAll(new Packet(new JSONObject().put("message", String.format("%s> %s", socket.getSocket().getInetAddress().getHostAddress(), packet.getObject().getString("message"))))))
                .addConnectEvent((socket -> socketServer.getController().sendPacketToAll(new Packet(new JSONObject().put("message", socket.getInetAddress().getHostAddress() + " connected.")))))
                .addDisconnectEvent((socket -> socketServer.getController().sendPacketToAll(new Packet(new JSONObject().put("message", socket.getInetAddress().getHostAddress() + " disconnected.")))));

        socketServer.start();
    }
}
