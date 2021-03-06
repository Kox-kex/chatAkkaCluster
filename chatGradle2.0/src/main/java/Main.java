import akka.actor.typed.ActorRef;
import javafx.application.Application;
import javafx.scene.control.*;
import javafx.stage.Stage;
import akka.actor.typed.ActorSystem;

import java.util.HashMap;
import java.util.Map;

public class Main extends Application {

    static Map<String, ActorRef<User.Command>> membersChat = new HashMap<>();//ActorRef членов чата
    static Map<String, TextArea> txtAreaDisplayPrivetChat = new HashMap<>();//Панель под каждый приватный чат
    static ActorSystem<User.Command> system;
    static String userName;

    public static void setSystem(ActorSystem<User.Command> system) {
        Main.system = system;
    }

    @Override
    public void start(Stage primaryStage) {
        ChatPane.startChat(new Stage());
        LoginPane.startLogin(new Stage());
    }

    //./gradlew run --args="127.0.0.1 25251 25252 port"
    public static void main(String[] args) {
        String ip = null;
        int seedPort1 = 0;
        int seedPort2 = 0;
        int port = 0;
        if (args.length != 4)
            throw new IllegalArgumentException("Usage: ./gradlew run --args=\"127.0.0.1 25251 25252 port\"");

            try {
                ip = args[0];
                seedPort1 = Integer.parseInt(args[1]);
                seedPort2 = Integer.parseInt(args[2]);
                port = Integer.parseInt(args[3]);
            } catch (NumberFormatException e) {
                e.printStackTrace();
            }

        StartAkkaCluster.startup(ip, seedPort1, seedPort2, port); //Запускаем серверную часть
        launch(args); // Запускаем UI
    }
}



