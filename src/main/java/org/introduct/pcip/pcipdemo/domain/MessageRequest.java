package org.introduct.pcip.pcipdemo.domain;

import lombok.Data;

// This class is used for request object, despite we have only 1 parameter
@Data
public class MessageRequest {
    private String message;
}
