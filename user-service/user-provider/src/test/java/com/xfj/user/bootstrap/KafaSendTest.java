package com.xfj.user.bootstrap;

import com.xfj.commons.producer.KafKaMessageProducer;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.HashMap;
import java.util.Map;

@RunWith(SpringRunner.class)
@SpringBootTest
public class KafaSendTest {
    @Autowired
    KafKaMessageProducer sendMessage;

    @Test
    public void sendMesg() {
        Map<String, Object> map = new HashMap<>();
        map.put("a", "1");
        sendMessage.sendMessage("aa", map);
    }

}
