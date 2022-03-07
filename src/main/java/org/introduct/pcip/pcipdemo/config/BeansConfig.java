package org.introduct.pcip.pcipdemo.config;

import io.netty.channel.ChannelOption;
import io.netty.handler.timeout.ReadTimeoutHandler;
import io.netty.handler.timeout.WriteTimeoutHandler;
import org.introduct.pcip.pcipdemo.soap.Jaxb2SoapDecoder;
import org.introduct.pcip.pcipdemo.soap.Jaxb2SoapEncoder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.client.reactive.ReactorClientHttpConnector;
import org.springframework.web.reactive.function.client.ExchangeStrategies;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.netty.http.client.HttpClient;

import java.time.Duration;
import java.util.concurrent.TimeUnit;

@Configuration
public class BeansConfig {

    public static final int TIMEOUT = 1000;

    @Bean
    public WebClient webClient(){

        HttpClient httpClient = HttpClient.create()
                .option(ChannelOption.CONNECT_TIMEOUT_MILLIS, TIMEOUT)
                .responseTimeout(Duration.ofMillis(TIMEOUT))
                .doOnConnected(conn ->
                        conn.addHandlerLast(new ReadTimeoutHandler(TIMEOUT, TimeUnit.MILLISECONDS))
                                .addHandlerLast(new WriteTimeoutHandler(TIMEOUT, TimeUnit.MILLISECONDS)));
        ExchangeStrategies exchangeStrategies = ExchangeStrategies.builder().codecs(clientCodecConfigurer -> {
            clientCodecConfigurer.customCodecs().register(new Jaxb2SoapEncoder());
            clientCodecConfigurer.customCodecs().register(new Jaxb2SoapDecoder());
        }).build();

        WebClient webClient = WebClient.builder()
                .clientConnector(new ReactorClientHttpConnector(httpClient))
                .exchangeStrategies( exchangeStrategies )
                .build();

        return webClient;
    }
}
