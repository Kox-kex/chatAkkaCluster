import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.stage.Stage;

//класс LoginPane создает панель для авторизации нового пользователя
public class LoginPane {

    public static void startLogin(Stage primaryStage) {
        primaryStage.setTitle("Login");
        GridPane grid = new GridPane();
        grid.setAlignment(Pos.CENTER);
        grid.setHgap(10);
        grid.setVgap(10);
        grid.setPadding(new Insets(25, 25, 25, 25));

        Text scenetitle = new Text("Welcome");
        scenetitle.setFont(Font.font("Tahoma", FontWeight.NORMAL, 20));
        grid.add(scenetitle, 0, 0, 2, 1);

        Label labelUserName = new Label("User Name:");
        grid.add(labelUserName, 0, 1);

        TextField userTextField = new TextField();
        grid.add(userTextField, 1, 1);

        Button btn = new Button("Sign in");
        HBox hbBtn = new HBox(10);
        hbBtn.setAlignment(Pos.BOTTOM_RIGHT);
        hbBtn.getChildren().add(btn);
        grid.add(hbBtn, 1, 4);

        final Text actiontarget = new Text();
        grid.add(actiontarget, 1, 6);

        btn.setOnAction(new EventHandler<ActionEvent>() {

            @Override
            public void handle(ActionEvent e) {
                String userName = userTextField.getText();

                if (!userName.equals("")) {
                    if(Main.membersChat.containsKey(userName)) {
                        actiontarget.setFill(Color.FIREBRICK);
                        actiontarget.setText("This username already exists: " + userName);
                    } else {
                        Main.userName = userName;
                        Main.system.tell(new User.Subscribe(Main.userName));
                        primaryStage.close();
                    }
                } else {
                    actiontarget.setText("the user name must not be empty");
                }
            }
        });

        Scene scene = new Scene(grid, 300, 275);
        primaryStage.setScene(scene);
        primaryStage.show();
    }
}
