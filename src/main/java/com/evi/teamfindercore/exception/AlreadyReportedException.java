package com.evi.teamfindercore.exception;

import lombok.Getter;

@Getter
public class AlreadyReportedException extends RuntimeException{

    private final String code = "9";

    public AlreadyReportedException(String message){super(message);}
}
