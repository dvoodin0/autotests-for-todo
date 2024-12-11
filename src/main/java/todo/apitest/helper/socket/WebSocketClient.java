package todo.apitest.helper.socket;

import jakarta.websocket.ClientEndpoint;


import jakarta.websocket.OnMessage;
import jakarta.websocket.OnOpen;
import jakarta.websocket.Session;
import org.glassfish.tyrus.client.ClientManager;

import java.net.URI;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

@ClientEndpoint
public class WebSocketClient {

    private CountDownLatch latch;
    private String receivedMessage;
    private Session session;

    public WebSocketClient(int messageCount) {
        this.latch = new CountDownLatch(messageCount);
    }

    public void connect(String uri) throws Exception {
        ClientManager clientManager = ClientManager.createClient();
        clientManager.connectToServer(this, URI.create(uri));
    }

    public void waitForMessage() throws InterruptedException {
        latch.await(30, TimeUnit.SECONDS);
        if (latch.getCount() > 0) {
            throw new RuntimeException("Not all messages were received in time.");
        }
    }

    @OnMessage
    public void onMessage(String message) {
        receivedMessage = message;
        latch.countDown();
    }

    @OnOpen
    public void onOpen(Session session) {
        this.session = session;
    }

    public String getReceivedMessage() {
        return receivedMessage;
    }

    public void clearReceivedMessage() {
        receivedMessage = null;
    }

    public void disconnect() {
        try {
            if (session != null && session.isOpen()) {
                session.close();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
