package ca.senecacollege.recipeapp;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class NetworkingService {

    private final String APP_ID = "a3b0ccd8";
    private final String APP_KEY = "07116fec21fc610fb743ca7939a98799";

    private String recipeURL1 = "https://api.edamam.com/search?q=";
    private String recipeURL2 = "&app_id=" + APP_ID + "&app_key=" + APP_KEY;

    public static ExecutorService networkExecutorService = Executors.newFixedThreadPool(1000);
    static Handler networkingHandler = new Handler(Looper.getMainLooper());

    interface NetworkingListner{
        void dataFromAPI(String jsonString);
    }

    public NetworkingListner listner;

    public void searchRecipes(String recipeName){

        // In Background
        networkExecutorService.execute(new Runnable() {
            @Override
            public void run() {

                HttpURLConnection urlConnection = null;

                try {
                    String jsonData = "";
                    String completeURLString = recipeURL1 + recipeName + recipeURL2;


                    Log.d("Check", "Conn Starts");
                    URL urlObj = new URL(completeURLString);
                    urlConnection = (HttpURLConnection) urlObj.openConnection();
                    urlConnection.setRequestMethod("GET");
                    urlConnection.setRequestProperty("Content-Type", "application/json");

                    InputStream in = urlConnection.getInputStream();
                    InputStreamReader reader = new InputStreamReader(in);
                    int streamData = 0;

                    while((streamData = reader.read()) != -1){
                        char nextChar = (char) streamData;
                        jsonData += nextChar ;
                    }

                    final String jData = jsonData;

                    Log.d("Check", "JSON Data: " + jData);

                    networkingHandler.post(new Runnable() {
                        @Override
                        public void run() {

                            listner.dataFromAPI(jData);

                        }
                    });

                } catch (MalformedURLException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                } finally {
                    urlConnection.disconnect();
                }

            }
        });

    }
}
