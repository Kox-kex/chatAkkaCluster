import User.{Command, Message, PrivetMessage, Subscribe, SubscribePrivetChat}
import akka.actor.typed.{ActorRef, Behavior}
import akka.actor.typed.scaladsl.{AbstractBehavior, ActorContext, Behaviors}
import javafx.application.Platform
import javafx.stage.Stage


object User {

  sealed trait Command
  case class Message(msg: String, name: String) extends Command with CborSerializable
  case class PrivetMessage(msg: String, userNamePrivateChat: String, outMessageName: String) extends Command with CborSerializable
  case class Subscribe(username: String) extends Command with CborSerializable
  case class SubscribePrivetChat(username: String) extends Command with CborSerializable

  def apply(): Behavior[Command] =
    Behaviors.setup{ context =>
      val user = new User(context)
      user
    }
}

class User(context: ActorContext[Command])
  extends AbstractBehavior[User.Command](context) {

  //при инициализации актера юзера так же иниацилизируется актер чат
  var chat: ActorRef[Chat.Command] = context.spawn(Chat(name = ""), "chat")
  val userListener: ActorRef[UserListener.Event] = context.spawn(UserListener(name = ""), "userListener")


  override def onMessage(msg: Command): Behavior[Command] = {
    msg match {
      //отправляем в чат на рассылку участникам
      case Message(msg, name) =>
        chat ! Chat.BroadcastMessage(msg, name)
        Behaviors.same

      //с помощью Platform.runLater выводим сообщение в UI, с помощью userNamePrivateChat
      //находим из каллекции нужную приватную панель
      case PrivetMessage(msg, userNamePrivateChat, outMessageName) =>
        Platform.runLater(new Runnable {
          override def run(): Unit = {
            Main.txtAreaDisplayPrivetChat
              .get(userNamePrivateChat)
              .appendText(s"[PrivetMessage]: [$outMessageName]: $msg\n")
          }
        })
        Behaviors.same

        //запрос на приватный чат
      case SubscribePrivetChat(username) =>
        Platform.runLater(new Runnable {
          override def run(): Unit = {
            PrivateChatPane.startPrivetChat(new Stage(), username);
          }
        })
        Behaviors.same

        //запрос на подписку общего чата
      case Subscribe(username) =>
        chat ! Chat.Subscribe()
        userListener ! UserListener.BroadcastRefUser(context.self, username)
        Behaviors.same
    }
  }
}
