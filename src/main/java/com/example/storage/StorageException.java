package com.example.storage;

/**
 * Created by Nikolay V. Petrov on 27.08.2017.
 */

class StorageException extends RuntimeException {

    StorageException(String message) {
        super(message);
    }

    StorageException(String message, Throwable cause) {
        super(message, cause);
    }
}
