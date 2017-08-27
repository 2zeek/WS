package com.example.storage;

/**
 * Created by Nikolay V. Petrov on 27.08.2017.
 */

public class StorageFileNotFoundException extends StorageException {

    StorageFileNotFoundException(String message) {
        super(message);
    }

    StorageFileNotFoundException(String message, Throwable cause) {
        super(message, cause);
    }
}