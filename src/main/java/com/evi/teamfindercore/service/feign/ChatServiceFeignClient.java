package com.evi.teamfindercore.service.feign;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@FeignClient(name = "chat-service")
public interface ChatServiceFeignClient {

    @PostMapping(value = "/api/v1/chat/private")
    ResponseEntity<Long> createPrivateChat();

    @DeleteMapping(value="/api/v1/chat/private/{chatId}")
    ResponseEntity<Void> deletePrivateChat(@PathVariable Long chatId);
}
