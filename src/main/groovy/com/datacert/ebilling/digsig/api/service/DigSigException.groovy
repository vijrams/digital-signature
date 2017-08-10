package com.datacert.ebilling.digsig.api.service

/**
 * Created by ranadeep.palle on 4/20/2017.
 */
class DigSigException extends RuntimeException{
    public DigSigException(String message) {
        super("Exception in DigSigService :"+message)
    }
}
