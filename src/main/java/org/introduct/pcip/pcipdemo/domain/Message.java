package org.introduct.pcip.pcipdemo.domain;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;

@Data
@NoArgsConstructor
public class Message {

    @Id
    private Long id;
    private String message;

    public Message(String message){
        this.message = message;
    }

}