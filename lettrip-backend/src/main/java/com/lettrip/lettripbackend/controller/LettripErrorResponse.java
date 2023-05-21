package com.lettrip.lettripbackend.controller;

import com.lettrip.lettripbackend.exception.LettripErrorCode;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class LettripErrorResponse {
    private Boolean success;
    private LettripErrorCode errorCode;
    private String message;
}
