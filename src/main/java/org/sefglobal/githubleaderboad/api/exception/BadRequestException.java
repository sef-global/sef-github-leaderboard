package org.sefglobal.githubleaderboad.api.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

// Exception to throw when resource not found with 404 code

@ResponseStatus(value = HttpStatus.BAD_REQUEST)
public class BadRequestException extends GiraffeAPIException {


    public BadRequestException() {
        super();
    }

    public BadRequestException(String msg) {
        super(msg);
    }

    public BadRequestException(String msg, Throwable e) {
        super(msg, e);
    }
}
