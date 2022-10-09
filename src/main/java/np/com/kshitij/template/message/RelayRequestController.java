package np.com.kshitij.template.message;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.web.util.HtmlUtils;

@Slf4j
@Controller
@RequiredArgsConstructor
public class RelayRequestController {
    private final SimpMessagingTemplate simpMessagingTemplate;

    @MessageMapping("/relay")
    public Message greeting(Message message) {
        return new Message("Hello, " + HtmlUtils.htmlEscape(message.getContent()) + "!");
    }

    public void sendHeartBeat() {
        log.info("Sending ws heartbeat");
        this.simpMessagingTemplate.convertAndSend("/queue/hc", "OK");
    }
}