package org.sefglobal.githubleaderboad.api.exception;

public class GiraffeAPIException extends Exception {

    public GiraffeAPIException() {
        super();
    }

    public GiraffeAPIException(String msg) {
        super(msg);
    }

    public GiraffeAPIException(String msg, Throwable e) {
        super(msg, e);
    }
}
