package org.example;

import lombok.Data;

import java.util.UUID;

@Data
public class User {
    private UUID id;
    private String username;
    private String email;
}
