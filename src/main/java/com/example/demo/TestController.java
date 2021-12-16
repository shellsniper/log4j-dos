package com.example.demo;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.ThreadContext;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.nio.charset.StandardCharsets;
import java.util.Base64;

@Controller
public class TestController {
    private static final Logger logger = LogManager.getLogger(TestController.class);

    @RequestMapping("/poc1")
    @ResponseBody
    public String poc1(String message) {
        try {
            String msg = new String(Base64.getDecoder().decode(message));
            ThreadContext.put("message", msg);

        } catch (Exception e) {
            return e.getMessage();
        }
        return "poc1 done";
    }

    @RequestMapping("/poc2")
    @ResponseBody
    public String poc2(String message) {
        try {
            // Base64解码
            String data = new String(Base64.getDecoder().decode(message));
            logger.error("message:" + data);

        } catch (Exception e) {
            logger.error("message: unknown error" );
            return e.getMessage();
        }
        return "poc2 done";
    }
}
