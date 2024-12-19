package net.oasisgames.tradeai.common.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

@Data
@AllArgsConstructor
public class ChatgptAPIResponse {

    private List<Choice> choices;

    @Data
    public static class Choice {
        private int index;
        private MessageAPIResponse message;
    }

}