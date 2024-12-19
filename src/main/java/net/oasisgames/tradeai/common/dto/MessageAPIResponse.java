package net.oasisgames.tradeai.common.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class MessageAPIResponse {

    private String role;
    private String content;

}