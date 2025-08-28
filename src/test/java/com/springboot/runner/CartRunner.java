package com.springboot.runner;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.springboot.controller.ApplyOfferResponse;
import com.springboot.controller.OfferRequest;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;

public class CartRunner {

    public boolean addOffer(OfferRequest offerRequest) throws Exception {

        //build http request Defines the endpoint URL. Opens a connection to the API using HttpURLConnection.
        String urlString = "http://localhost:9001/api/v1/offer";
        URL url = new URL(urlString);
        HttpURLConnection con = (HttpURLConnection) url.openConnection();

        //tells Java we will send a request body (POST)
        con.setDoOutput(true);
        con.setRequestProperty("Content-Type", "application/json");

//Uses Jackson’s ObjectMapper to serialize Java object → JSON string.
        ObjectMapper mapper = new ObjectMapper();
        String POST_PARAMS = mapper.writeValueAsString(offerRequest);

        //Writes the JSON request body into the connection’s output stream. Sends it to the server.
        OutputStream os = con.getOutputStream();
        os.write(POST_PARAMS.getBytes());
        os.flush();
        os.close();

        int responseCode = con.getResponseCode();
        System.out.println("POST Response Code :: " + responseCode);

        if (responseCode == HttpURLConnection.HTTP_OK) { //success
            BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream()));
            String inputLine;
            StringBuffer response = new StringBuffer();

            while ((inputLine = in.readLine()) != null) {
                response.append(inputLine);
            }
            in.close();
            // print result
            System.out.println(response.toString());
        } else {
            System.out.println("POST request did not work.");
        }
        return true;
    }

    public ApplyOfferResponse applyOffer(int cartValue, int userId, int restaurantId) throws Exception {

        // build http request
        String urlString = "http://localhost:9001/api/v1/cart/apply_offer";
        URL url = new URL(urlString);
        HttpURLConnection con = (HttpURLConnection) url.openConnection();
        con.setDoOutput(true);
        con.setRequestProperty("Content-Type", "application/json");

        //create json payload
        String POST_PARAMS = "{\"cart_value\":" + cartValue + ",\"user_id\":" + userId + ",\"restaurant_id\":" + restaurantId + "}";

        //Sends the JSON payload through HTTP request body.
        OutputStream os = con.getOutputStream();
        os.write(POST_PARAMS.getBytes());
        os.flush();
        os.close();

        int responseCode = con.getResponseCode();
        System.out.println("POST Response Code :: " + responseCode);

        if (responseCode == HttpURLConnection.HTTP_OK) {
            //Reads response body line by line using BufferedReader. Stores it into a StringBuffer.
            BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream()));
            String inputLine;
            StringBuffer response = new StringBuffer();
            while ((inputLine = in.readLine()) != null) {
                response.append(inputLine);
            }
            in.close();

            // Deserialize the response using JsonNode (instead of ApplyOfferResponse)
            ObjectMapper mapper = new ObjectMapper();
            JsonNode responseJson = mapper.readTree(response.toString());

            // Extract the cart_value from the response
            int cartValueAfterOffer = responseJson.path("cart_value").asInt();

            // Return ApplyOfferResponse with extracted cart_value
            return new ApplyOfferResponse(cartValueAfterOffer);
        } else {
            System.out.println("POST request did not work.");
            return null;
        }
    }

    public String getUserSegment(int userId) {
        try {
            String urlString = "http://localhost:1080/api/v1/user_segment?user_id=" + userId;
            URL url = new URL(urlString);
            HttpURLConnection con = (HttpURLConnection) url.openConnection();
            con.setRequestMethod("GET");
            con.setRequestProperty("accept", "application/json");

            int responseCode = con.getResponseCode();
            if (responseCode == HttpURLConnection.HTTP_OK) {
                BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream()));
                String inputLine;
                StringBuilder response = new StringBuilder();
                while ((inputLine = in.readLine()) != null) {
                    response.append(inputLine);
                }
                in.close();
                ObjectMapper mapper = new ObjectMapper();
                JsonNode responseJson = mapper.readTree(response.toString());
                return responseJson.path("segment").asText();
            } else {
                throw new Exception("Failed to fetch user segment for userId " + userId);
            }
        } catch (Exception e) {
            throw new RuntimeException("Error fetching user segment", e);
        }
    }

}
