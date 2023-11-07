package com.scrabble.calculator.dto.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.experimental.Accessors;

@Getter
@Accessors(chain=true)
@AllArgsConstructor
public class ErrorMessage {
    String message;
}
