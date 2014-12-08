package com.jigarjoshi.visulizationserver;

import org.eclipse.jetty.websocket.api.Session;
import org.eclipse.jetty.websocket.api.WebSocketAdapter;

import java.util.Random;

/**
 * @author jigar.joshi
 */
public class RealTimeSocket extends WebSocketAdapter {
    private static String template = "{\"lat\": \"%s\", \"lon\": \"%s\", \"id\": \"%s\", \"qty\": \"%s\"}";
    private double sfLat = 37.774929500000000000D;
    private double sfLon = -122.419415500000010000D;
    private static final Random rand = new Random();
    private static long id = 1L;

    @Override
    public void onWebSocketConnect(Session sess) {
        super.onWebSocketConnect(sess);
        new Thread(() -> {
            try {
                while (true) {
                    String line = String.format(template, Double.toString(sfLat + Math.random()), Double.toString(sfLon + Math.random()), Long.toString(id++ % 250), rand.nextInt(50));
                    getSession().getRemote().sendString(line);
                    Thread.sleep(rand.nextInt(5000));
                }
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        }).start();
    }

    @Override
    public void onWebSocketText(String message) {
        super.onWebSocketText(message);
        /* respond */
        try {
        } catch (Exception ex) {
            ex.printStackTrace();
        }

    }

    @Override
    public void onWebSocketClose(int statusCode, String reason) {
        super.onWebSocketClose(statusCode, reason);
    }

    @Override
    public void onWebSocketError(Throwable cause) {
        super.onWebSocketError(cause);
        cause.printStackTrace(System.err);
    }
}
