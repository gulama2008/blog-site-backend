package com.siyu.blogsitebackend.exceptions;

public class NotFoundException extends RuntimeException {
    private static final long serialVersionUID = 1l;
    
    private String message;

    public NotFoundException(String message) {
        super();
        this.message = message;
    }

    public String getMessage() {
        return this.message;
    }
}