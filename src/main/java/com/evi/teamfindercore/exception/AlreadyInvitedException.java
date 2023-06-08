package com.evi.teamfindercore.exception;

import lombok.Getter;

@Getter

public class AlreadyInvitedException  extends RuntimeException{
    private final String code = "11";

    public AlreadyInvitedException(String message){super(message);}
}
