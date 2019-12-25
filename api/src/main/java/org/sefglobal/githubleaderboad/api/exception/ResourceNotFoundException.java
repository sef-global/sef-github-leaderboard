package org.sefglobal.githubleaderboad.api.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

// Exception to throw when resource not found with 404 code

@ResponseStatus(value = HttpStatus.NOT_FOUND)
public class ResourceNotFoundException extends GiraffeAPIException {


    public ResourceNotFoundException() {
        super();
    }

    public ResourceNotFoundException(String msg) {
        super(msg);
    }

    public ResourceNotFoundException(String msg, Throwable e) {
        super(msg, e);
    }
}
