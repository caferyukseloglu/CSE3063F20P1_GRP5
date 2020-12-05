package com.dls;

public class Main {

    public static void main(String[] args) {

        Config config = new Config("config.json");
        ReadJSON read = new ReadJSON(config);
        Dataset dataset = read.readInput();
        config.loginInterface();
        RandomBot randomBot = new RandomBot(dataset, config.getActiveUser());
        config.loginInterface();
        RandomBot randomBot2 = new RandomBot(dataset, config.getActiveUser());
        WriteJSON write = new WriteJSON(dataset, config);

    }


}

