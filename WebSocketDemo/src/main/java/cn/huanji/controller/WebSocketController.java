package cn.huanji.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;

/**
 * @author chy
 */
@RestController
@RequestMapping("webSocket")
@Api("WebSocket控制类")
public class WebSocketController {

    @Autowired
    private Socket socket;

    /**
     * 向前端发送消息
     * @param message
     * @throws IOException
     */
    @GetMapping("/{message}")
    @ApiOperation("客户端向前端发送消息[群发]")
    @ApiImplicitParam(name = "message",value = "发送消息内容")
    public void sendMessage(@PathVariable("message") String message) throws IOException {
        socket.sendMessage("Controller发送消息："+message);
        System.out.println("发送的消息是:"+message);
    }
}
