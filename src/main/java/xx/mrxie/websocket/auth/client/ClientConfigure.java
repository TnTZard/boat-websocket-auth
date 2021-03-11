package xx.mrxie.websocket.auth.client;

import org.springframework.stereotype.Component;
import xx.mrxie.websocket.auth.constant.Constants;

import javax.annotation.PostConstruct;
import javax.websocket.*;
import java.net.URI;

@Component
public class ClientConfigure {


    @PostConstruct
    public void startMyClient() {
//        String uri = "ws://open.zwiivc.com/usvapi/control/471";
//        System.out.println("Connecting to " + uri);
        try {
            System.out.println("Connecting to " + Constants.uri);
            WebSocketContainer container = ContainerProvider.getWebSocketContainer();
            container.connectToServer(MyClient.class, URI.create(Constants.uri));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


}
