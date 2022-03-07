package org.introduct.pcip.pcipdemo.repository;

import org.introduct.pcip.pcipdemo.domain.Message;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;

public interface MessageRepository extends ReactiveCrudRepository<Message, Integer> {

}