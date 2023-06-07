package com.security.demo.base;

import com.fasterxml.jackson.annotation.JsonAlias;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

import java.time.LocalDateTime;

@Getter @Setter
@SuperBuilder
public class ErrorMessage {
    @JsonAlias("status_code")
    private int statusCode;
    @JsonAlias("date") @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime date;
    @JsonAlias("message")
    private String message;
    @JsonAlias("uri")
    private String uri;
}
