package com.example.euronba.controller;

import android.os.AsyncTask;

import org.json.JSONObject;

import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Scanner;

public class RetrieveInfo extends AsyncTask<String, Integer, JSONObject> {
    @Override
    protected JSONObject doInBackground(String... urls) {
        JSONObject jobj = null;
        try {

            URL url = new URL(urls[0]);

            HttpURLConnection con = (HttpURLConnection) url.openConnection();

            con.setRequestMethod("GET");

            String inline = "";

            con.connect();

            int responsecode = con.getResponseCode();

            if (responsecode != 200) {
                throw new RuntimeException("HttpResponseCode: " + responsecode);
            } else {
                Scanner sc = new Scanner(url.openStream());
                while (sc.hasNext()) {
                    inline += sc.nextLine();
                }
                sc.close();
            }

            jobj = new JSONObject(inline);

        } catch (Exception e) {
            e.printStackTrace();
        }
        return jobj;
    }
}
