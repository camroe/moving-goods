package com.ford.mg.services.impl;

import android.util.Log;

import com.ford.mg.BO.Order;
import com.ford.mg.BO.OrderState;
import com.ford.mg.DTO.OrderDTO;
import com.ford.mg.constant.Constants;
import com.ford.mg.services.IF.OrderCombinationIF;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class RemoteCombinationAPI implements OrderCombinationIF {
    String comma = ",";
    private String TAG = RemoteCombinationAPI.class.getCanonicalName();
    private Socket socket = null;
    private BufferedReader console = null;
    private PrintWriter streamOut = null;
    private BufferedReader streamIn;
    private String server;
    private int port;
    private String quote = "\"";
    private String open = "{";
    private String close = "}";
    private String seperator = ":";

    public RemoteCombinationAPI(String server, int port) {
        this.server = server;
        this.port = port;
    }

    @Override
    public List<OrderDTO> getOrders(String customerID) {
        String methodTAG = TAG + " getOrders";
        String commandLine = buildJsonGetOrdersCommand(customerID);
        String responseLine = getResponse(commandLine);
        List<OrderDTO> orderDTOs = null;
        try {
            orderDTOs = processGetOrdersResponse(responseLine);
        } catch (JSONException e) {
            Log.e(methodTAG, "Error in processing Response JSON");
            e.printStackTrace();
        }
        return orderDTOs;
    }

    @Override
    public List<OrderDTO> getUnfulfilledOrders() {
        String methodTAG = TAG + ".getUnfulfilledOrders";
        String commandLine = buildJsonGetUnfulfilledOrders();
        String responseLine = getResponse(commandLine);
        List<OrderDTO> orderDTOs = null;
        try {
            orderDTOs = processGetOrdersResponse(responseLine);
        } catch (JSONException e) {
            Log.e(methodTAG, "Error in processing Response JSON");
            e.printStackTrace();
        }
        return orderDTOs;
    }

    @Override
    public List<Integer> getUnfulfilledOrderIDs() {
        String methodTAG = TAG + ".getUnfulfilledOrderIDs";
        String commandLine = buildJsonGetUnfulfilledOrderIDs();
        String responseLine = getResponse(commandLine);
        List<Integer> orderIDs = null;
        try {
            orderIDs = processGetUnfulfilledOrdersResponse(responseLine);
        } catch (JSONException e) {
            Log.e(methodTAG, "Error in processing Response JSON");
            e.printStackTrace();
        }
        return orderIDs;
    }

    @Override
    public List<Integer> getOrderIDs(String customerID) {
        String methodTAG = TAG + ".getOrderIDs";
        String commandLine = buildJsonGetOrderIDs(customerID);
        String responseLine = getResponse(commandLine);
        List<Integer> orderIDs = null;
        try {
            orderIDs = processGetUnfulfilledOrdersResponse(responseLine);
        } catch (JSONException e) {
            Log.e(methodTAG, "Error in processing Response JSON");
            e.printStackTrace();
        }
        return orderIDs;
    }


    @Override
    public void fulfill(int orderID) {
        String methodTAG = TAG + ".fulfill";
        String commandLine = buildJsonFulfull(orderID);
        String responseLine = getResponse(commandLine);
        System.out.println(responseLine);
    }

    @Override
    public Order find(int orderID) {

        String methodTAG = TAG + ".find";
        String commandLine = buildJsonFindOrder(orderID);
        String responseLine = getResponse(commandLine);
        Order order = null;
        try {
            order = processGetOrderResponse(responseLine);
        } catch (JSONException e) {
            Log.e(methodTAG, "Error in processing Response JSON");
            e.printStackTrace();
        }
        return order;
    }


    @Override
    public void orderPickedup(int orderID) {
        String methodTag = TAG + ".orderPickedup";
        Log.d(methodTag, "Not sure what to do with a remote pickup when NFC is not imple yet. ");
        //TODO: Not sure what to do here.
    }

    public void start() throws IOException {
        console = new BufferedReader(new InputStreamReader(System.in));
        streamOut = new PrintWriter(new OutputStreamWriter(socket.getOutputStream()), true);
        streamIn = new BufferedReader(new InputStreamReader(socket.getInputStream()));
    }

    private List<OrderDTO> processGetOrdersResponse(String jsonString) throws JSONException {
        String methodTag = TAG + ".processGetOrdersResponse";
        List<OrderDTO> result = Collections.emptyList();
        JSONArray jsonArray = new JSONArray(jsonString);
        if (jsonArray.length() == 0) {
            Log.w(methodTag, "Zero length Array received from server");
            return Collections.emptyList();
        } else {
            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject json = jsonArray.getJSONObject(i);
                result.add(new OrderDTO(constructOrder(json)));
            }
        }
        return result;
    }

    private Order processGetOrderResponse(String responseLine) throws JSONException {
        String methodTag = TAG + ".processGetOrderResponse";
        JSONObject jsonObject = new JSONObject(responseLine);
        return constructOrder(jsonObject);
    }

    private List<Integer> processGetUnfulfilledOrdersResponse(String jsonString) throws JSONException {
        String methodTag = TAG + ".processGetOrdersResponse";
        List<Integer> result = new ArrayList<>();
        JSONArray jsonArray = new JSONArray(jsonString);
        if (jsonArray.length() == 0) {
            Log.w(methodTag, "Zero length Array received from server");
            return Collections.emptyList();
        } else {
            for (int i = 0; i < jsonArray.length(); i++) {
                result.add(new Integer(jsonArray
                        .getJSONObject(i)
                        .getInt(Constants.ORDER_NUMBER)));
            }
        }
        return result;
    }

    private String getResponse(String commandline) {
        String methodTAG = TAG + ".getResponse";
        System.out.println("Establishing connection. Please wait ...");
        try {
            socket = new Socket(server, port);
            System.out.println("Connected: " + socket);
            start();
        } catch (UnknownHostException uhe) {
            System.out.println("Host unknown: " + uhe.getMessage());
        } catch (IOException ioe) {
            System.out.println("Unexpected exception: " + ioe.getMessage());
        }
        System.out.println("Sending .... ");
        //Send Command
        streamOut.println(commandline);
        streamOut.flush();
        System.out.println("Sent ....");
        String responseLine = "";
        try {
            System.out.println("Waiting for Read .....");
            responseLine = streamIn.readLine();
            System.out.println("Response Read");
        } catch (IOException e) {
            Log.e(methodTAG, "Error in Reading Response");
            e.printStackTrace();
        }
        System.out.println(responseLine);
        stop();
        return responseLine;
    }

    private Order constructOrder(JSONObject json) throws JSONException {
        Order order = new Order();
        order.setOrderNumber(json.getInt(Constants.ORDER_NUMBER));
        order.setCustomerID(json.getString(Constants.CUSTOMER_ID));
        order.setVehicle(json.getString(Constants.VEHICLE));
        order.setLocker(json.getInt(Constants.LOCKER));
        order.setCombination(json.getString(Constants.COMBINATION));
        order.setState(OrderState.valueOf(json.getString(Constants.STATE)));
        return order;
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

    /**
     * {“method”:”getOrders”,”customerID”:”Ryan”}
     *
     * @param customerID
     * @return
     */
    private String buildJsonGetOrdersCommand(String customerID) {
        StringBuilder sb = new StringBuilder();
        sb.append(open)
                .append(quote)
                .append(Constants.METHOD_LABEL)
                .append(quote)
                .append(seperator)
                .append(quote)
                .append(Constants.GET_ORDERS_METHOD)
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

    /**
     * {“method”:”getUnfulfilledOrders”}
     *
     * @return
     */
    private String buildJsonGetUnfulfilledOrderIDs() {
        StringBuilder sb = new StringBuilder();
        sb.append(open)
                .append(quote)
                .append(Constants.METHOD_LABEL)
                .append(quote)
                .append(seperator)
                .append(quote)
                .append(Constants.GET_UNFULFILLED_ORDER_IDS_METHOD)
                .append(quote)
                .append(close);
        System.out.println("BUILT COMMAND: " + sb.toString());
        return sb.toString();
    }

    private String buildJsonGetUnfulfilledOrders() {
        StringBuilder sb = new StringBuilder();
        sb.append(open)
                .append(quote)
                .append(Constants.METHOD_LABEL)
                .append(quote)
                .append(seperator)
                .append(quote)
                .append(Constants.GET_UNFULFILLED_ORDERS_METHOD)
                .append(quote)
                .append(close);
        System.out.println("BUILT COMMAND: " + sb.toString());
        return sb.toString();
    }


    private String buildJsonGetOrderIDs(String customerID) {
        StringBuilder sb = new StringBuilder();
        sb.append(open)
                .append(quote)
                .append(Constants.METHOD_LABEL)
                .append(quote)
                .append(seperator)
                .append(quote)
                .append(Constants.GET_ORDER_IDS_METHOD)
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

    private String buildJsonFulfull(int orderID) {
        StringBuilder sb = new StringBuilder();
        sb.append(open)
                .append(quote)
                .append(Constants.METHOD_LABEL)
                .append(quote)
                .append(seperator)
                .append(quote)
                .append(Constants.FULFILL_ORDER)
                .append(quote)
                .append(comma)
                .append(quote)
                .append(Constants.ORDER_ID_NAME)
                .append(quote)
                .append(seperator)
                .append(quote)
                .append(orderID)
                .append(quote)
                .append(close);
        System.out.println("BUILT COMMAND: " + sb.toString());
        return sb.toString();
    }

    private String buildJsonFindOrder(int orderID) {
        StringBuilder sb = new StringBuilder();
        sb.append(open)
                .append(quote)
                .append(Constants.METHOD_LABEL)
                .append(quote)
                .append(seperator)
                .append(quote)
                .append(Constants.FIND_ORDER)
                .append(quote)
                .append(comma)
                .append(quote)
                .append(Constants.ORDER_ID_NAME)
                .append(quote)
                .append(seperator)
                .append(quote)
                .append(orderID)
                .append(quote)
                .append(close);
        System.out.println("BUILT COMMAND: " + sb.toString());
        return sb.toString();
    }
}
