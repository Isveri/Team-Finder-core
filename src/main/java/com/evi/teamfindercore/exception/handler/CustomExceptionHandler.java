package com.evi.teamfindercore.exception.handler;


import com.evi.teamfindercore.exception.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;


@Slf4j
@ControllerAdvice
public class CustomExceptionHandler {

    @ExceptionHandler(UserNotFoundException.class)
    public ResponseEntity<ErrorCodeMsg> userNotFound(UserNotFoundException e){
        log.error(e.getMessage());
        return ResponseEntity.badRequest().body(ErrorCodeMsg.builder().code(e.getCode()).build());
    }

    @ExceptionHandler({AlreadyBannedException.class})
    public ResponseEntity<ErrorCodeMsg> alreadyBannedException(AlreadyBannedException e){
        log.error(e.getMessage());
        return ResponseEntity.badRequest().body(ErrorCodeMsg.builder().code(e.getCode()).build());
    }

    @ExceptionHandler({AlreadyInvitedException.class})
    public ResponseEntity<ErrorCodeMsg> alreadyInvitedException(AlreadyInvitedException e){
        log.error(e.getMessage());
        return ResponseEntity.badRequest().body(ErrorCodeMsg.builder().code(e.getCode()).build());
    }
    @ExceptionHandler({AlreadyFriendException.class})
    public ResponseEntity<ErrorCodeMsg> alreadyFriendException(AlreadyFriendException e){
        log.error(e.getMessage());
        return ResponseEntity.badRequest().body(ErrorCodeMsg.builder().code(e.getCode()).build());
    }
    @ExceptionHandler({AlreadyReportedException.class})
    public ResponseEntity<ErrorCodeMsg> alreadyReportedException(AlreadyReportedException e){
        log.error(e.getMessage());
        return ResponseEntity.badRequest().body(ErrorCodeMsg.builder().code(e.getCode()).build());
    }

}
