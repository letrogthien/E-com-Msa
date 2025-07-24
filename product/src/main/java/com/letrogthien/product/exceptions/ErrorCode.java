package com.letrogthien.product.exceptions;



import lombok.Getter;
import org.springframework.http.HttpStatus;


@Getter
public enum ErrorCode {
    PRODUCT_NOT_FOUND("AUTH_002", "PRODUCT not found", HttpStatus.NOT_FOUND),
    INVALID_TOKEN("AUTH_006", "Invalid authentication token", HttpStatus.UNAUTHORIZED),
    UNAUTHORIZED("AUTH_022", "Unauthorized access", HttpStatus.UNAUTHORIZED),
    INVALID_CLAIM("AUTH_023", "Invalid claim in token", HttpStatus.UNAUTHORIZED),
    NOT_FOUND("PRODUCT_024", "Resource not found", HttpStatus.NOT_FOUND),
    INTERNAL_SERVER_ERROR("PRODUCT_028", "server error",HttpStatus.INTERNAL_SERVER_ERROR ),
    KYC_ALREADY_APPROVED("PRODUCT_029", "current kyc available", HttpStatus.BAD_REQUEST ),
    KYC_DOCUMENT_ALREADY_EXISTS("PRODUCT_030", "current document approved", HttpStatus.BAD_REQUEST),
    KYC_ALREADY_REJECTED("PRODUCT_031", "rejected",HttpStatus.BAD_REQUEST ),
    KYC_NOT_APPROVED("PRODUCT_032", "not approved", HttpStatus.BAD_REQUEST),
    INVALID_STATUS("PRODUCT_033", "invalid status",HttpStatus.BAD_REQUEST ),
    FILE_UPLOAD_FAILED("PRODUCT_034","up load fail" , HttpStatus.BAD_REQUEST),
    BUYER_NOT_PARTICIPATED_IN_TRANSACTION("PRODUCT_035", "buyer not participated in this transaction", HttpStatus.BAD_REQUEST);

    private final String code;
    private final String message;
    private final HttpStatus status;

    ErrorCode(String code, String message, HttpStatus status) {
        this.code = code;
        this.message = message;
        this.status = status;
    }

}
