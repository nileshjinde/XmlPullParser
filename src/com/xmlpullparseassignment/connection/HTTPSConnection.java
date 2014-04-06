package com.xmlpullparseassignment.connection;

import org.apache.http.HttpStatus;
import org.apache.http.client.ClientProtocolException;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

public class HTTPSConnection {

    public HTTPSConnection() {
    }

    /**
     * This method is used to call the GET methods of web services from application and
     * returns the response from server as a BufferedReader Value.
     * @param webServiceURL The web service to hit and get the data.
     * @return reader The BufferedReader response from returned by the web service.
     */
    public InputStream getDataFromServer(String webService) {
        InputStream reader = null;
        HttpURLConnection connection;
        try {
            URL url = new URL(webService);
            connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("GET");
            connection.connect();

            //Check for HttpStatus 200 which means the web service is functional and returns the response
            if (connection.getResponseCode() == HttpStatus.SC_OK) {
                reader = connection.getInputStream();
            }
        } catch (ClientProtocolException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return reader;
    }
}
