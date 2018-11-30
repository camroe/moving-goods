package com.ford.mg.cloud.impl;

import android.util.Log;

import com.ford.mg.BO.Order;
import com.ford.mg.BO.OrderState;
import com.ford.mg.DTO.OrderDTO;
import com.ford.mg.cloud.IF.OrderIF;
import com.ford.mg.constant.Constants;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.UnknownHostException;

public class RemoteOrderAPI implements OrderIF {
    private String TAG = RemoteOrderAPI.class.getCanonicalName();
    private Socket socket = null;
    private BufferedReader console = null;
    private PrintWriter streamOut = null;
    private BufferedReader streamIn;

    @Override
    public OrderDTO order(String customerID) {
        String methodTAG = TAG + " order";
        String line;
        System.out.println("Establishing connection. Please wait ...");
        try {
            socket = new Socket(Constants.SERVER_IP, Constants.SERVER_PORT);
            System.out.println("Connected: " + socket);
            start();
        } catch (UnknownHostException uhe) {
            System.out.println("Host unknown: " + uhe.getMessage());
        } catch (IOException ioe) {
            System.out.println("Unexpected exception: " + ioe.getMessage());
        }
        System.out.println("Sending .... ");
        line = buildJsonCommand(customerID);

//        line = "{\"method\":\"order\",\"customerID\":\"Ryan\"}";
        streamOut.println(line);
        streamOut.flush();
        System.out.println("Sent ....");
        try {
            System.out.println("Waiting for Read .....");
            line = streamIn.readLine();
            System.out.println("Response Read");
        } catch (IOException e) {
            Log.e(methodTAG, "Error in Reading Response");
            e.printStackTrace();
        }
        System.out.println(line);
        stop();
        OrderDTO orderDTO = null;
        try {
            orderDTO = processResponse(line);
        } catch (JSONException e) {
            Log.e(methodTAG, "Error in processing Response JSON");
            e.printStackTrace();
        }
        return orderDTO;
    }

    private OrderDTO processResponse(String jsonString) throws JSONException {
        Order order = new Order();
        JSONObject json = new JSONObject(jsonString);

        order.setOrderNumber(json.getInt(Constants.ORDER_NUMBER));
        order.setCustomerID(json.getString(Constants.CUSTOMER_ID));
        order.setVehicle(json.getString(Constants.VEHICLE));
        order.setLocker(json.getInt(Constants.LOCKER));
        order.setCombination(json.getString(Constants.COMBINATION));
        order.setState((OrderState) json.get(Constants.STATE));

        return new OrderDTO(order);
    }

    private String buildJsonCommand(String customerID) {
        StringBuilder sb = new StringBuilder();
        String quote = "\"";
        String open = "{";
        String close = "}";
        String comma = ",";
        String seperator = ":";

        sb.append(open)
                .append(quote)
                .append(Constants.METHOD_LABEL)
                .append(quote)
                .append(seperator)
                .append(quote)
                .append(Constants.ORDER_METHOD)
                .append(quote)
                .append(comma)
                .append(quote)
                .append(Constants.CUSTOMER_ID_NAME)
                .append(quote)
                .append(seperator)
                .append(quote)
                .append(customerID)
                .append(quote)
                .append(close);
        System.out.println("BUILT COMMAND: " + sb.toString());
        return sb.toString();
    }

    public void start() throws IOException {
        console = new BufferedReader(new InputStreamReader(System.in));
        streamOut = new PrintWriter(new OutputStreamWriter(socket.getOutputStream()), true);
        streamIn = new BufferedReader(new InputStreamReader(socket.getInputStream()));
    }

    private void stop() {
        try {
            if (console != null) console.close();
            if (streamOut != null) streamOut.close();
            if (streamIn != null) streamIn.close();
            if (socket != null) socket.close();
        } catch (IOException ioe) {
            System.out.println("Error closing ...");
        }
    }

}

