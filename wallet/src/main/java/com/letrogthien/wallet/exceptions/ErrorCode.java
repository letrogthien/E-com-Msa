package com.letrogthien.wallet.exceptions;



import lombok.Getter;
import org.springframework.http.HttpStatus;


@Getter
public enum ErrorCode {
    WALLET_NOT_FOUND("WALLET_002", "User not found", HttpStatus.NOT_FOUND),
    INVALID_TOKEN("WALLET_006", "Invalid authentication token", HttpStatus.UNAUTHORIZED),
    UNAUTHORIZED("WALLET_022", "Unauthorized access", HttpStatus.UNAUTHORIZED),
    INVALID_CLAIM("WALLET_023", "Invalid claim in token", HttpStatus.UNAUTHORIZED),
    NOT_FOUND("WALLET_024", "Resource not found", HttpStatus.NOT_FOUND),
    INTERNAL_SERVER_ERROR("WALLET_028", "server error",HttpStatus.INTERNAL_SERVER_ERROR ),
    INVALID_STATUS("WALLET_033", "invalid status",HttpStatus.BAD_REQUEST ),
    FILE_UPLOAD_FAILED("WALLET_034","up load fail" , HttpStatus.BAD_REQUEST), 
    WALLET_ALREADY_DELETED("WALLET_035", "Wallet already deleted", HttpStatus.BAD_REQUEST), 
    WALLET_NOT_KYC("WALLET_036", "Wallet not kyc", HttpStatus.BAD_REQUEST), 
    BALANCE_NOT_ENOUGH("WALLET_037", "Balance not enough", HttpStatus.BAD_REQUEST), 
    WALLET_NOT_ACTIVE("WALLET_038", "Wallet not active", HttpStatus.BAD_REQUEST), 
    WALLET_NOT_ALREADY_SECURE("WALLET_039", "Wallet not already secure", HttpStatus.BAD_REQUEST), 
    WALLET_PASSWORD_INCORRECT("WALLET_040", "Wallet password incorrect", HttpStatus.BAD_REQUEST), 
    JWT_PAYTOKEN_FALSE("WALLET_041", "JWT paytoken false", HttpStatus.BAD_REQUEST),;
    
    private final String code;
    private final String message;
    private final HttpStatus status;

    ErrorCode(String code, String message, HttpStatus status) {
        this.code = code;
        this.message = message;
        this.status = status;
    }

}
