package main;

import controllers.GuiController;
import javafx.stage.Stage;
import repository.DatabaseSeeder;

public class Application extends javafx.application.Application {
    public static void main(String[] args) {
        try {
            DatabaseSeeder.seedDatabase();
            launch();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void start(Stage stage) {
        GuiController guiController = new GuiController();
        guiController.start(stage);
    }
}