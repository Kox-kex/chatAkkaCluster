import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.TextField;

//кнопка send в панели общего чата
public class ButtonListenerChat implements EventHandler<ActionEvent> {

    private final TextField textField;

    public ButtonListenerChat(TextField textField) {
        this.textField = textField;
    }

    @Override
    public void handle(ActionEvent e) {
        //get message
        String message = textField.getText();

        //если сообщение пустое или юзер не прошел форму регистрации
        if (message.length() == 0 || Main.userName == null) {
            return;
        }
        //запрос на приватный чат
        //которое должно иметь данную валидную форму 'PM: username'
        if (message.startsWith("PM")) {
            String[] a = message.split(": ");
            if (a.length == 2) {
                String namePM = a[1];
                if (Main.membersChat.containsKey(namePM)) {
                    //отправляем сообщение в свой чат и в чат нужному нам юзеру
                    Main.membersChat.get(namePM).tell(new User.SubscribePrivetChat(Main.userName));
                    Main.system.tell(new User.SubscribePrivetChat(namePM));
                }
            }
        } else {
            //иначе рассылаем его всем участникам чата
            Main.system.tell(new User.Message(message, Main.userName));
        }
        textField.clear();
    }
}
