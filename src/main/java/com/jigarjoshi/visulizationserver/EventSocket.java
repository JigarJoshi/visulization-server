package com.jigarjoshi.visulizationserver;

import org.eclipse.jetty.websocket.api.Session;
import org.eclipse.jetty.websocket.api.WebSocketAdapter;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.InputStreamReader;
/**
 * @author jigar.joshi
 */
public class EventSocket extends WebSocketAdapter {
    @Override
    public void onWebSocketConnect(Session sess) {
        super.onWebSocketConnect(sess);
        new Thread(() -> {
            try {
                BufferedReader reader = new BufferedReader(new InputStreamReader(new FileInputStream("/Users/jigar.joshi/Desktop/cc.log")));
                String line;
                while (true) {
                    line = reader.readLine();
                    if (line == null) {
                        //wait until there is more of the file for us to read
                        Thread.sleep(1000);
                    } else {
                        if (line.contains("")) {
                            line = line.substring(line.indexOf("{"));
                            getSession().getRemote().sendString(line);
                        }
                    }
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
