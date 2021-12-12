package ca.senecacollege.recipeapp;

import android.app.Application;

public class myApp extends Application {

    private NetworkingService networkingService = new NetworkingService();

    private JsonService jsonService = new JsonService();

    private DatabaseManager databaseManager = new DatabaseManager();

    public DatabaseManager getDatabaseManager() {
        return databaseManager;
    }

    public NetworkingService getNetworkingService() {
        return networkingService;
    }

    public JsonService getJsonService() {
        return jsonService;
    }

}
