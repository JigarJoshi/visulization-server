package com.jigarjoshi.visulizationserver;

import org.eclipse.jetty.websocket.servlet.WebSocketServlet;
import org.eclipse.jetty.websocket.servlet.WebSocketServletFactory;

@SuppressWarnings("serial")
/**
 * @author jigar.joshi
 */
public class RealTimeServlet extends WebSocketServlet {
    @Override
    public void configure(WebSocketServletFactory factory) {
        factory.register(RealTimeSocket.class);

        factory.getPolicy().setIdleTimeout(Long.MAX_VALUE);
    }

}
