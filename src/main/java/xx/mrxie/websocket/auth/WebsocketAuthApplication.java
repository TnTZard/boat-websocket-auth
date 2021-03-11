package xx.mrxie.websocket.auth;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.retry.annotation.EnableRetry;
import xx.mrxie.websocket.auth.client.MyClient;

import javax.websocket.ContainerProvider;
import javax.websocket.WebSocketContainer;
import java.net.URI;

@EnableRetry
@SpringBootApplication
public class WebsocketAuthApplication {

    public static void main(String[] args) {
        SpringApplication.run(WebsocketAuthApplication.class, args);
    }




}
