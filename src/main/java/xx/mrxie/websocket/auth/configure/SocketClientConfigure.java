package xx.mrxie.websocket.auth.configure;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.retry.annotation.Backoff;
import org.springframework.retry.annotation.Retryable;
import org.springframework.web.client.RestTemplate;

import javax.websocket.ClientEndpointConfig;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Slf4j
public class SocketClientConfigure extends ClientEndpointConfig.Configurator {

    /**
     * 为避免有时候登录失败，引入重试机制
     * @param headers
     */
    @Retryable(value = Exception.class, maxAttempts = 5, backoff = @Backoff(delay = 2000,multiplier = 1.5))
    @Override
    public void beforeRequest(Map<String, List<String>> headers) {
        // 先请求
        try {
            RestTemplate client = new RestTemplate();
            HttpMethod method = HttpMethod.POST;
            String api = "http://open.zwiivc.com/usvapi/login";
            Map<String, Object> jsonMap = new HashMap<>();
            jsonMap.put("name", "anzhi");
            jsonMap.put("pwd", "anzhi123456");
            HttpEntity<Map<String, Object>> requestEntity = new HttpEntity<>(jsonMap, new HttpHeaders());
            ResponseEntity<Object> responseEntity = client.exchange(api, method, requestEntity, Object.class);
            System.out.println("登录完成" + responseEntity.getBody());
            String s = responseEntity.getHeaders().get("Set-Cookie").toString();
            String cookie = s.split(";")[0].substring(1);
            List<String> cookies = new ArrayList<>();
            cookies.add(cookie);
            headers.put("Cookie", cookies);
        } catch (Exception e) {
            log.error(e.getMessage());
        }

    }
}
