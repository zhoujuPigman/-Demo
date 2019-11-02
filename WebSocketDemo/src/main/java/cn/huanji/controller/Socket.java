package cn.huanji.controller;

import org.springframework.stereotype.Controller;

import javax.websocket.*;
import javax.websocket.server.ServerEndpoint;
import java.io.IOException;
import java.util.concurrent.CopyOnWriteArraySet;

/**
 * 开启一个监听器类,用户监听客户端操作
 */
@Controller
@ServerEndpoint("/websocket")
public class Socket {

    /*客户端会话Session*/
    private Session session;
    /*线程安全Set 用于存放每个客户端处理消息的对象，用Set为不重复*/
    private static CopyOnWriteArraySet<Socket> webSocketSet =  new CopyOnWriteArraySet<>();

    /**
     * webSocket 链接建立之后进行调用
     * @param session
     */
    @OnOpen
    public void onOpen(Session session){
        this.session = session;
        //将新建立的连接添加到Set集合里
        webSocketSet.add(this);
        System.out.println("webSocket 有新的连接建立/现在的连接数量:"+webSocketSet.size());
    }

    /**
     * 连接关闭调用
     */
    @OnClose
    public void onClose(){
        //将旧连接从Set集合清除
        webSocketSet.remove(this);
        System.out.println("webSocket 有连接关闭/现在的连接数量:"+webSocketSet.size());
    }

    /**
     * 收到客户端信息后调用方法
     * @param message
     * @throws IOException
     */
    @OnMessage
    public void onMessage(String message) throws IOException {
        // 群发消息,遍历所有会话对象,轮询发送消息
        for (Socket socket:webSocketSet){
            socket.session.getBasicRemote().sendText("收到客户端发来信息【群发消息】:"+message);
        }
    }

    /**
     * 发生错误时调用
     * @param throwable
     */
    @OnError
    public void onError(Throwable throwable){
        throwable.printStackTrace();
    }

    /**
     * 收到客户端发送消息
     * @param message
     * @throws IOException
     */
    public void sendMessage(String message) throws IOException{
        for (Socket socket:webSocketSet){
            socket.session.getBasicRemote().sendText(message);
        }
    }

}
