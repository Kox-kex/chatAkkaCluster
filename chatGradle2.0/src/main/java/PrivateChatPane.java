import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

//класс который сосдает отдельную вкладку для приватного чата
public class PrivateChatPane {

    public static void startPrivetChat(Stage primaryStage, String name) {
        VBox vBox = new VBox();
        ScrollPane scrollPane = new ScrollPane();   //панель для отображения текстовых сообщений
        HBox hBox = new HBox(); //панель для поля ввода и кнопки отправки

        TextArea txtAreaDisplay = new TextArea();
        Main.txtAreaDisplayPrivetChat.put(name, txtAreaDisplay);//Добавляем в коллекцию приватных панелей
        txtAreaDisplay.setEditable(false);
        scrollPane.setContent(txtAreaDisplay);
        scrollPane.setFitToHeight(true);
        scrollPane.setFitToWidth(true);

        //текстовое поле и кнопку добавим в hBox
        TextField txtInput = new TextField();
        txtInput.setPromptText("New message");
        txtInput.setTooltip(new Tooltip("Write your message. "));
        Button btnSend = new Button("Send");
        btnSend.setOnAction(new ButtonListenerPrivateChat(txtInput, name));

        hBox.getChildren().addAll(txtInput, btnSend);
        hBox.setHgrow(txtInput, Priority.ALWAYS);  //увеличения по мере увеличения размера окна

        vBox.getChildren().addAll(scrollPane, hBox);
        vBox.setVgrow(scrollPane, Priority.ALWAYS);


        //создание сцены
        Scene scene = new Scene(vBox, 450, 500);
        primaryStage.setTitle("Client: JavaFx Text Chat App");
        primaryStage.setScene(scene);
        primaryStage.show();
    }
}
