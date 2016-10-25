package org.libre.lingvo.dto.exception;

/**
 * Created by igorek2312 on 18.10.16.
 */
public class CustomErrorException extends IllegalArgumentException {
    private CustomError error;

    public CustomErrorException(CustomError customError) {
        super(customError.getDescription());
        this.error = customError;
    }

    public CustomErrorException(String message, CustomError customError) {
        super(message);
        customError.setDescription(message);
        this.error = customError;
    }

    public CustomError getError() {
        return error;
    }
}
