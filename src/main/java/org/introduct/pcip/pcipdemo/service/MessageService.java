package org.introduct.pcip.pcipdemo.service;

import lombok.RequiredArgsConstructor;
import org.introduct.pcip.pcipdemo.domain.Message;
import org.introduct.pcip.pcipdemo.repository.MessageRepository;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
@RequiredArgsConstructor
public class MessageService {
    private final MessageRepository messageRepository;

    public Flux<Message> getAllMessages(){
        return messageRepository.findAll();
    }

    public Mono<Message> addMessage(Message message){
        return messageRepository.save(message);
    }

}