package com.yugabyte.mybatis.server;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.InetSocketAddress;
import java.util.List;
import java.util.Properties;

import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
import org.apache.log4j.Logger;

import com.google.gson.Gson;
import com.sun.net.httpserver.Headers;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpServer;
import com.yugabyte.mybatis.Exceptions.ResourceNotFoundException;
import com.yugabyte.mybatis.model.Product;
import com.yugabyte.mybatis.model.User;
import com.yugabyte.mybatis.model.requests.CreateOrderRequest;
import com.yugabyte.mybatis.model.requests.OrderIdClass;
import com.yugabyte.mybatis.model.requests.UserIdClass;
import com.yugabyte.mybatis.model.response.CreateOrderResponse;
import com.yugabyte.mybatis.model.response.DeleteUserResponse;
import com.yugabyte.mybatis.service.DemoService;

public class BasicHttpServer {

    final static Logger logger = Logger.getLogger(BasicHttpServer.class);
    private static DemoService service = new DemoService();

    private static Properties applicationProperties;

    private static void readProperties() {
        try (InputStream in = BasicHttpServer.class.getClassLoader().getResourceAsStream("config.properties")) {
            applicationProperties = new Properties();
            assert in != null;
            applicationProperties.load( in );
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    

    public static void main(String[] args) throws IOException {

        readProperties();
        int port = Integer.parseInt(applicationProperties.getProperty("server.port", "8080"));
        HttpServer server = HttpServer.create(new InetSocketAddress(port), 0);
        logger.info("Listening on port: " + port);

        server.createContext("/users")
            .setHandler(BasicHttpServer::handleUsersRequest);

        server.createContext("/products")
            .setHandler(BasicHttpServer::handleProductRequest);

        server.createContext("/orders")
            .setHandler(BasicHttpServer::handleOrderRequest);

        server.createContext("/list-orders")
            .setHandler(BasicHttpServer::handleListOrders);

        server.createContext("/")
            .setHandler(BasicHttpServer::handleRootRequest);

        server.start();
    }

    private static void handleRootRequest(final HttpExchange exchange) throws IOException {
        exchange.sendResponseHeaders(404, 0);
        exchange.close();
    }

    private static void handleProductRequest(final HttpExchange exchange) throws IOException {

        if (exchange.getRequestMethod().equals("POST")) {
            handleCreateProduct(exchange);
        } else if (exchange.getRequestMethod().equals("GET")) {
            handleListProducts(exchange);
        }
    }
    private static void handleOrderRequest(final HttpExchange exchange) throws IOException {

        if (exchange.getRequestMethod().equals("POST")) {
            handleCreateOrder(exchange);
        } else if (exchange.getRequestMethod().equals("DELETE")) {
            handleDeleteOrder(exchange);
        }
    }

    private static void handleUsersRequest(final HttpExchange exchange) throws IOException {

        if (exchange.getRequestMethod().equals("POST")) {
            handleUserPostRequest(exchange);
        } else if (exchange.getRequestMethod().equals("GET")) {
            handleUserGetRequest(exchange);
        } else {
            handleUserDeleteRequest(exchange);
        }
    }


    private static void handleUserPostRequest(final HttpExchange exchange) throws IOException {
        final Gson gson = new Gson();
        final BufferedReader reader =
            new BufferedReader(new InputStreamReader(exchange.getRequestBody()));
        User user = gson.fromJson(reader, User.class);
        try {
            service.create(user);
            sendJsonResponse(exchange, gson.toJson(user));
        } catch (ResourceNotFoundException rnfe) {
            sendErrorResponse(exchange, 400, rnfe.getMessage());
            rnfe.printStackTrace();
        } catch (RuntimeException rte) {
            sendErrorResponse(exchange, 500, "Internal Server error");
            rte.printStackTrace();
        }
    }

    private static void handleUserGetRequest(final HttpExchange exchange) throws IOException {
        final Gson gson = new Gson();
        try {
            List < User > allUsers = service.getAllUsers();
            sendJsonResponse(exchange, gson.toJson(allUsers));
        } catch (ResourceNotFoundException rnfe) {
            sendErrorResponse(exchange, 400, rnfe.getMessage());
            rnfe.printStackTrace();
        } catch (RuntimeException rte) {
            sendErrorResponse(exchange, 500, "Internal Server error");
            rte.printStackTrace();
        }
    }

    private static void handleUserDeleteRequest(final HttpExchange exchange) throws IOException {
        try {
            final Gson gson = new Gson();
            final BufferedReader reader =
                new BufferedReader(new InputStreamReader(exchange.getRequestBody()));
            UserIdClass request = gson.fromJson(reader, UserIdClass.class);
            DeleteUserResponse userDeleted = service.deleteUser(request.userId);
            sendJsonResponse(exchange, gson.toJson(userDeleted));
        } catch (ResourceNotFoundException rnfe) {
            sendErrorResponse(exchange, 400, rnfe.getMessage());
            rnfe.printStackTrace();
        } catch (RuntimeException rte) {
            sendErrorResponse(exchange, 500, rte.getMessage());
            rte.printStackTrace();
        }
    }

    private static void handleCreateProduct(final HttpExchange exchange) throws IOException {
        final Gson gson = new Gson();
        final BufferedReader reader =
            new BufferedReader(new InputStreamReader(exchange.getRequestBody()));
        Product product = gson.fromJson(reader, Product.class);

        try {
            service.create(product);
            sendJsonResponse(exchange, gson.toJson(product));
        } catch (ResourceNotFoundException rnfe) {
            sendErrorResponse(exchange, 400, rnfe.getMessage());
            rnfe.printStackTrace();
        } catch (RuntimeException rte) {
            sendErrorResponse(exchange, 500, "Internal Server error");
            rte.printStackTrace();
        }
    }

    private static void sendJsonResponse(final HttpExchange exchange, final String json) throws IOException {
        Headers responseHeaders = exchange.getResponseHeaders();
        responseHeaders.set("Content-Type", "application/json");
        exchange.sendResponseHeaders(200, json.length());
        try (OutputStream os = exchange.getResponseBody()) {
            os.write(json.getBytes());
        }
    }

    private static void sendErrorResponse(final HttpExchange exchange, final int errorCode, final String message) throws IOException {
        Headers responseHeaders = exchange.getResponseHeaders();
        responseHeaders.set("Content-Type", "application/text");
        exchange.sendResponseHeaders(errorCode, message.length());
        try (OutputStream os = exchange.getResponseBody()) {
            os.write(message.getBytes());
        }
    }

    private static void handleListProducts(final HttpExchange exchange) throws IOException {
        final Gson gson = new Gson();
        try {
            List < Product > allProducts = service.getAllProducts();
            sendJsonResponse(exchange, gson.toJson(allProducts));
        } catch (ResourceNotFoundException rnfe) {
            sendErrorResponse(exchange, 400, rnfe.getMessage());
            rnfe.printStackTrace();
        } catch (RuntimeException rte) {
            sendErrorResponse(exchange, 500, "Internal Server error");
            rte.printStackTrace();
        }

    }

    private static void handleCreateOrder(final HttpExchange exchange) throws IOException {

        try {
            final Gson gson = new Gson();
            final BufferedReader reader =
                new BufferedReader(new InputStreamReader(exchange.getRequestBody()));
            CreateOrderRequest request = gson.fromJson(reader, CreateOrderRequest.class);
            CreateOrderResponse response = service.create(request);
            sendJsonResponse(exchange, gson.toJson(response));
        } catch (ResourceNotFoundException rnfe) {
            sendErrorResponse(exchange, 400, rnfe.getMessage());
            rnfe.printStackTrace();
        } catch (RuntimeException rte) {
            sendErrorResponse(exchange, 500, "Internal Server error");
            rte.printStackTrace();
        }
    }

    private static void handleListOrders(final HttpExchange exchange) throws IOException {
        try {
            final Gson gson = new Gson();
            final BufferedReader reader =
                new BufferedReader(new InputStreamReader(exchange.getRequestBody()));
            UserIdClass request = gson.fromJson(reader, UserIdClass.class);
            sendJsonResponse(exchange, gson.toJson(service.listOrders(request.userId)));
        } catch (ResourceNotFoundException rnfe) {
            sendErrorResponse(exchange, 400, rnfe.getMessage());
            rnfe.printStackTrace();
        } catch (RuntimeException rte) {
            sendErrorResponse(exchange, 500, "Internal Server error");
            rte.printStackTrace();
        }
    }

    private static void handleDeleteOrder(final HttpExchange exchange) throws IOException {
        try {
            final Gson gson = new Gson();
            final BufferedReader reader =
                new BufferedReader(new InputStreamReader(exchange.getRequestBody()));
            OrderIdClass request = gson.fromJson(reader, OrderIdClass.class);
            CreateOrderResponse response = service.deleteOrder(request.orderId);
            sendJsonResponse(exchange, gson.toJson(response));
        } catch (ResourceNotFoundException rnfe) {
            sendErrorResponse(exchange, 400, rnfe.getMessage());
            rnfe.printStackTrace();
        } catch (RuntimeException rte) {
            sendErrorResponse(exchange, 500, "Internal Server error");
            rte.printStackTrace();
        }
    }
}