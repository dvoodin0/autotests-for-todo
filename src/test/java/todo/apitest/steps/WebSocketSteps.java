package todo.apitest.steps;

import io.qameta.allure.Step;
import todo.apitest.helper.socket.WebSocketClient;


public class WebSocketSteps {

    private static WebSocketClient client;
    private static String SOCKET_URL = "ws://localhost:8080/ws";
    private static final String SOCKET_PREFIX = "/ws";

    @Step("Connect to WebSocket server")
    public static void connectToWebSocketServer(int messageCount) throws Exception {
        if (client == null) {
            client = new WebSocketClient(messageCount);
        }
        client.connect(SOCKET_URL + SOCKET_PREFIX);
    }

    @Step("Wait for WebSocket message to be received")
    public static void waitForMessage() throws InterruptedException {
        if (client != null) {
            client.waitForMessage();
        }
    }

    @Step("Retrieve the received WebSocket message")
    public static String getReceivedMessage() {
        return client != null ? client.getReceivedMessage() : null;
    }

    @Step("Clear received message")
    public static void clearReceivedMessage() {
        if (client != null) {
            client.clearReceivedMessage();
        }
    }

    @Step("Disconnect from WebSocket server")
    public static void disconnectFromWebSocket() {
        if (client != null) {
            client.disconnect();
        }
    }
}
