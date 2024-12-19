package net.oasisgames.tradeai.common.dto;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class ChatgptAPIRequest {

    private String model;
    private List<MessageAPIResponse> messages;
    private int n = 1;
    private double temperature;

    public ChatgptAPIRequest(String model, String content) {
        this.model = model;
        this.messages = new ArrayList<>();
        this.messages.add(new MessageAPIResponse("user", content));
    }

}