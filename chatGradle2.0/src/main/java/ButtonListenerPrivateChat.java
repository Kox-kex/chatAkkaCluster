import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.TextField;

//кнопка send в панели приватного чата
public class ButtonListenerPrivateChat implements EventHandler<ActionEvent> {

    private final TextField textField;
    private final String name;

    public ButtonListenerPrivateChat(TextField textField, String name) {
        this.textField = textField;
        this.name = name;
    }

    @Override
    public void handle(ActionEvent e) {
        //get username and message
        String message = textField.getText();

        //if message is empty, just return : don't send the message
        if (message.length() == 0) {
            return;
        }
        //отправляем приватное сообщение себе и собеседнику
        try {
            Main.membersChat.get(name).tell(new User.PrivetMessage(message, Main.userName, Main.userName));
        } catch (NullPointerException exception) {
            exception.printStackTrace();
            System.out.println("Main.membersChat: нет такого чата");
        }
        Main.system.tell(new User.PrivetMessage(message, name, Main.userName));

        textField.clear();
    }
}
