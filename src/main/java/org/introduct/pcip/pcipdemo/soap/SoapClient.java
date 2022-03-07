package org.introduct.pcip.pcipdemo.soap;

import lombok.RequiredArgsConstructor;
import org.introduct.pcip.pcipdemo.domain.Message;
import org.introduct.pcip.pcipdemo.service.MessageService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.reactive.function.client.WebClient;

import org.springframework.web.server.ResponseStatusException;
import reactor.core.publisher.Mono;
import wsdl.generated.client.GetMessageRequest;
import wsdl.generated.client.GetMessageResponse;

@Service
@RequiredArgsConstructor
public class SoapClient {

    private final WebClient webClient;
    private final MessageService messageService;

    @Value("${soap.url}")
    private String soapUrl;

    @Transactional
    public Mono<Long> callWebService(String message) {
        return getMessage(message)
                .then(messageService.addMessage(new Message(message)))
                .map(Message::getId);
    }

    private Mono<Integer> getMessage(String message){
        GetMessageRequest getMessageRequest = new GetMessageRequest();
        getMessageRequest.setMessage(message);
        SoapRequest soapEnvelopeRequest = new SoapRequest(null, getMessageRequest);

        return webClient.post()
                .uri(soapUrl)
                .contentType(MediaType.TEXT_XML)
                .body(Mono.just(soapEnvelopeRequest), SoapRequest.class)
                .retrieve()
                .onStatus(
                        HttpStatus::isError,
                        clientResponse ->
                                clientResponse
                                        .bodyToMono(String.class)
                                        .flatMap(
                                                errorResponseBody ->
                                                        Mono.error(
                                                                new ResponseStatusException(
                                                                        clientResponse.statusCode(),
                                                                        errorResponseBody))))

                .bodyToMono(GetMessageResponse.class)
                .map(GetMessageResponse::getResultCode);
    }

}
