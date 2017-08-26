package com.example.storage;

/**
 * Created by Nikolay V. Petrov on 27.08.2017.
 */

public class StorageException extends RuntimeException {

    public StorageException(String message) {
        super(message);
    }

    public StorageException(String message, Throwable cause) {
        super(message, cause);
    }
}
