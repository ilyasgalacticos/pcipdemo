package org.introduct.pcip.pcipdemo.api;

import lombok.RequiredArgsConstructor;
import org.introduct.pcip.pcipdemo.domain.Message;
import org.introduct.pcip.pcipdemo.domain.MessageRequest;
import org.introduct.pcip.pcipdemo.service.MessageService;
import org.introduct.pcip.pcipdemo.soap.SoapClient;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping(value = "/api")
@RequiredArgsConstructor
public class MainController {

    private final MessageService messageService;
    private final SoapClient soapClient;

    @PostMapping(value = "/process")
    public Mono<Long> process(@RequestBody MessageRequest request){
        return soapClient.callWebService(request.getMessage());
    }

}