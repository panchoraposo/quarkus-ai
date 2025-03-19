package com.redhat.demo;

import io.quarkus.websockets.next.OnOpen;
import io.quarkus.websockets.next.OnTextMessage;
import io.quarkus.websockets.next.WebSocket;
import io.smallrye.mutiny.Multi;

@WebSocket(path = "/customer-support-agent")
public class CustomerSupportAgentWebSocket {

    private final CustomerSupportAgent customerSupportAgent;

    public CustomerSupportAgentWebSocket(CustomerSupportAgent customerSupportAgent) {
        this.customerSupportAgent = customerSupportAgent;
    }

    @OnOpen
    public String onOpen() {
        return "Welcome! How can I help you today?";
    }

    /*@OnTextMessage
    public String onTextMessage(String message) {
        return customerSupportAgent.chat(message);
    }*/

    @OnTextMessage
    public Multi<String> onTextMessage(String message) {
        return customerSupportAgent.chat(message);
    }
}