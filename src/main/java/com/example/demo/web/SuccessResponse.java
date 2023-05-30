package com.example.demo.web;

import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Builder
public class SuccessResponse<T> {
    private T data;
    private String message;
}