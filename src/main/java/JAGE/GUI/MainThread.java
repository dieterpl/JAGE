package JAGE.GUI;

import JAGE.supervisor.Supervisor;
import javafx.application.Application;
import javafx.stage.Stage;

public class MainThread extends Application implements Runnable {

    @Override
    public void start(Stage stage) throws Exception {
    }

    public void run() {
        Supervisor.getInstance().startEmulator();
    }
}