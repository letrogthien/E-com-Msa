package com.letrogthien.user.exceptions;



import lombok.Getter;
import org.springframework.http.HttpStatus;


@Getter
public enum ErrorCode {
    USER_NOT_FOUND("AUTH_002", "User not found", HttpStatus.NOT_FOUND),
    INVALID_TOKEN("AUTH_006", "Invalid authentication token", HttpStatus.UNAUTHORIZED),
    UNAUTHORIZED("AUTH_022", "Unauthorized access", HttpStatus.UNAUTHORIZED),
    INVALID_CLAIM("AUTH_023", "Invalid claim in token", HttpStatus.UNAUTHORIZED),
    NOT_FOUND("USER_024", "Resource not found", HttpStatus.NOT_FOUND),
    INTERNAL_SERVER_ERROR("USER_028", "server error",HttpStatus.INTERNAL_SERVER_ERROR ),
    KYC_ALREADY_APPROVED("USER_029", "current kyc available", HttpStatus.BAD_REQUEST ),
    KYC_DOCUMENT_ALREADY_EXISTS("USER_030", "current document approved", HttpStatus.BAD_REQUEST),
    KYC_ALREADY_REJECTED("USER_031", "rejected",HttpStatus.BAD_REQUEST ),
    KYC_NOT_APPROVED("USER_032", "not approved", HttpStatus.BAD_REQUEST),
    INVALID_STATUS("USER_033", "invalid status",HttpStatus.BAD_REQUEST ),
    FILE_UPLOAD_FAILED("USER_034","up load fail" , HttpStatus.BAD_REQUEST);

    private final String code;
    private final String message;
    private final HttpStatus status;

    ErrorCode(String code, String message, HttpStatus status) {
        this.code = code;
        this.message = message;
        this.status = status;
    }

}
