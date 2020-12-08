import akka.actor.Address;
import akka.actor.AddressFromURIString;
import akka.actor.typed.ActorSystem;
import akka.cluster.typed.Cluster;
import akka.cluster.typed.JoinSeedNodes;
import com.typesafe.config.Config;
import com.typesafe.config.ConfigFactory;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

//присоединение к кластеру
public class StartAkkaCluster {

    private static ActorSystem<User.Command> system;

    public static ActorSystem<User.Command> getSystem() {
        return system;
    }

    static void startup(String ip, int seedPort1, int seedPort2, int port) {
        //Присоеденяем узел к кластеру
        Map<String, Object> overrides = new HashMap<>();
        overrides.put("akka.remote.artery.canonical.hostname", ip);
        overrides.put("akka.remote.artery.canonical.port", port);

        Config config = ConfigFactory.parseMap(overrides)
                .withFallback(ConfigFactory.load());
        system =
                ActorSystem.create(User.apply(), "ClusterSystem", config);

        Main.setSystem(system);

        String address1 = String.format("akka://ClusterSystem@%s:%s", ip, seedPort1);
        String address2 = String.format("akka://ClusterSystem@%s:%s", ip, seedPort2);

        List<Address> seedNodes = new ArrayList<>();
        seedNodes.add(AddressFromURIString.parse(address1));
        seedNodes.add(AddressFromURIString.parse(address2));

        Cluster.get(system).manager().tell(new JoinSeedNodes(seedNodes));
    }
}
