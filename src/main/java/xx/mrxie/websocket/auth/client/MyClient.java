package xx.mrxie.websocket.auth.client;


import com.alibaba.fastjson.JSON;
import lombok.extern.slf4j.Slf4j;
import xx.mrxie.websocket.auth.configure.SocketClientConfigure;
import xx.mrxie.websocket.auth.constant.Constants;
import xx.mrxie.websocket.auth.netty.MyNettyClient;

import javax.websocket.ClientEndpoint;
import javax.websocket.*;
import java.io.*;
import java.net.Socket;
import java.net.URI;
import java.util.*;

@Slf4j
@ClientEndpoint(configurator = SocketClientConfigure.class)
public class MyClient{

    private static String boatStatus;

    @OnOpen
    public void onOpen(Session session) {
        log.info("连接-----" + session.getBasicRemote().toString());
    }

    @OnMessage
    public void onMessage(String message) {
        log.info("收到-----" + message);
        Map map = (Map) JSON.parse(message);
        String status = "false".equals(map.get(Constants.ONLINE).toString()) ? "船离线" : "船上线";
        boatStatus = status;
        sendSocket();
        log.info("----" + status + "----");
    }

    @OnError
    public void onError(Throwable t) {
        t.printStackTrace();
       // log.info("Error-----"+ t.getStackTrace());
    }

    @OnClose
    public void onClosed(CloseReason closeReason) throws IOException, DeploymentException {
        log.info("关闭----" + closeReason);
        // 关闭后重复连接
        WebSocketContainer container = ContainerProvider.getWebSocketContainer();
        container.connectToServer(MyClient.class, URI.create(Constants.uri));
    }

    /**
     * 向业主方发送
     */
    public void sendSocket() {
        try {
            Socket socket = new Socket("127.0.0.1", 11211);
            //InputStream is = socket.getInputStream();
            OutputStream os = socket.getOutputStream();
            BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(os));
            bw.write(boatStatus);
            bw.flush();
            log.info("发送成功");
            socket.close();
        } catch (IOException e) {
            log.error("连接失败");
        }
    }

}
