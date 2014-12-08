package com.jigarjoshi.visulizationserver;

import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.server.ServerConnector;
import org.eclipse.jetty.servlet.ServletContextHandler;
import org.eclipse.jetty.servlet.ServletHolder;

/**
 * Main launcher
 * @author jigar.joshi
 */
public class EventServer {
    public static void main(String[] args) {
        Server server = new Server();
        ServerConnector connector = new ServerConnector(server);
        connector.setPort(8080);
        server.addConnector(connector);

        // Setup the basic application "context" for this application at "/"
        // This is also known as the handler tree (in jetty speak)
        ServletContextHandler context = new ServletContextHandler(ServletContextHandler.SESSIONS);
        context.setContextPath("/");
        server.setHandler(context);

        // Add a websocket to a specific path spec
        ServletHolder holderEvents = new ServletHolder("ws-events", EventServlet.class);
        ServletHolder realTimeEvents = new ServletHolder("rt-ws-events", RealTimeServlet.class);
        ServletHolder lastDayEvents = new ServletHolder("1d-ws-events", LastDayServlet.class);
        context.addServlet(holderEvents, "/events/*");
        context.addServlet(realTimeEvents, "/rt-events/*");
        context.addServlet(lastDayEvents, "/1d-events/*");


        try {
            server.start();
            server.dump(System.err);
            server.join();
        } catch (Throwable t) {
            t.printStackTrace(System.err);
        }
    }
}
