
package com.monotoneid.eishms.exceptions;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class ResourceNotFoundException extends RuntimeException {
    private static final long serialVersionUID = 1L;

    public ResourceNotFoundException(String reason) {
        super(reason);
    }

    public ResourceNotFoundException(String message, Throwable cause) {
        super(message, cause);
    }
}