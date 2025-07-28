package com.letrogthien.transaction.exceptions;



import lombok.Getter;
import org.springframework.http.HttpStatus;


@Getter
public enum ErrorCode {
    TRANSACTION_NOT_FOUND("TRANSACTION_002", "User not found", HttpStatus.NOT_FOUND),
    INVALID_TOKEN("TRANSACTION_006", "Invalid authentication token", HttpStatus.UNAUTHORIZED),
    UNAUTHORIZED("TRANSACTION_022", "Unauthorized access", HttpStatus.UNAUTHORIZED),
    INVALID_CLAIM("TRANSACTION_023", "Invalid claim in token", HttpStatus.UNAUTHORIZED),
    NOT_FOUND("TRANSACTION_024", "Resource not found", HttpStatus.NOT_FOUND),
    INTERNAL_SERVER_ERROR("TRANSACTION_028", "server error",HttpStatus.INTERNAL_SERVER_ERROR ),
    KYC_ALREADY_APPROVED("TRANSACTION_029", "current kyc available", HttpStatus.BAD_REQUEST ),
    KYC_DOCUMENT_ALREADY_EXISTS("TRANSACTION_030", "current document approved", HttpStatus.BAD_REQUEST),
    KYC_ALREADY_REJECTED("TRANSACTION_031", "rejected",HttpStatus.BAD_REQUEST ),
    KYC_NOT_APPROVED("TRANSACTION_032", "not approved", HttpStatus.BAD_REQUEST),
    INVALID_STATUS("TRANSACTION_033", "invalid status",HttpStatus.BAD_REQUEST ),
    FILE_UPLOAD_FAILED("TRANSACTION_034","up load fail" , HttpStatus.BAD_REQUEST),
    BUYER_NOT_PARTICIPATED_IN_TRANSACTION("TRANSACTION_035", "buyer not participated in this transaction", HttpStatus.BAD_REQUEST),
    ORDER_NOT_FOUND("TRANSACTION_036", "Order not found", HttpStatus.NOT_FOUND), 
    CART_NOT_FOUND("TRANSACTION_037", "Cart not found", HttpStatus.NOT_FOUND),
    INVALID_REQUEST("TRANSACTION_038", "Invalid request", HttpStatus.BAD_REQUEST),
    CART_ITEM_NOT_FOUND("TRANSACTION_039", "Cart item not found", HttpStatus.NOT_FOUND), 
    EVENT_LOG_NOT_FOUND("TRANSACTION_040", "Event log not found", HttpStatus.NOT_FOUND);

    private final String code;
    private final String message;
    private final HttpStatus status;

    ErrorCode(String code, String message, HttpStatus status) {
        this.code = code;
        this.message = message;
        this.status = status;
    }

}
