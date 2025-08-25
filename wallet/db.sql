CREATE
DATABASE  IF NOT EXISTS `wallet_service`;
USE `wallet_service`;

-- 1. Bảng `wallets`: Quản lý ví của người dùng
CREATE TABLE wallets
(
    id                 BINARY(16)     NOT NULL DEFAULT (UUID_TO_BIN(UUID())) PRIMARY KEY,
    user_id            BINARY(16)     NOT NULL UNIQUE, -- Liên kết 1-1 với user_id từ User Service
    balance            DECIMAL(18, 4) NOT NULL DEFAULT 0.0000, -- Số dư ví hiện tại, 4 chữ số thập phân cho độ chính xác cao
    currency           VARCHAR(10)    NOT NULL, -- Đơn vị tiền tệ của ví
    status             VARCHAR(20)    NOT NULL, -- ACTIVE, INACTIVE, SUSPENDED, BLOCKED
    last_transaction_id BINARY(16) NULL, -- ID của giao dịch cuối cùng để hỗ trợ kiểm tra tính nhất quán
    created_at         TIMESTAMP      DEFAULT CURRENT_TIMESTAMP,
    updated_at         TIMESTAMP      DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    INDEX              idx_wallet_user_id (user_id) -- Chỉ mục cho việc tìm kiếm ví theo user_id
);

CREATE wallet_secure
(
    id                 BINARY(16)     NOT NULL DEFAULT (UUID_TO_BIN(UUID())) PRIMARY KEY,
    wallet_id          BINARY(16)     NOT NULL UNIQUE, -- Liên kết 1-1 với wallet_id từ Wallet Service
    wallet_password     VARCHAR(255)   NOT NULL, -- Mật khẩu của ví
    wallet_pin             VARCHAR(255)   NOT NULL, -- PIN của ví
    created_at         TIMESTAMP      DEFAULT CURRENT_TIMESTAMP,
    updated_at         TIMESTAMP      DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
)

-- 2. Bảng `wallet_transactions`: Lưu trữ chi tiết mọi giao dịch phát sinh trên ví
-- Đây là bảng quan trọng nhất cho việc kiểm toán và truy vết tài chính.
CREATE TABLE wallet_transactions
(
    id                     BINARY(16)     NOT NULL DEFAULT (UUID_TO_BIN(UUID())) PRIMARY KEY,
    wallet_id              BINARY(16)     NOT NULL, -- Ví bị ảnh hưởng bởi giao dịch
    transaction_type       VARCHAR(50)    NOT NULL, -- Loại giao dịch: DEPOSIT, WITHDRAWAL, TRANSFER_SEND, TRANSFER_RECEIVE, PAYMENT, REFUND, FEE
    amount                 DECIMAL(18, 4) NOT NULL, -- Số tiền gốc của giao dịch (luôn là số dương)
    fee_amount             DECIMAL(18, 4) NOT NULL DEFAULT 0.0000, -- Số tiền phí nếu có
    final_amount           DECIMAL(18, 4) NOT NULL, -- Số tiền cuối cùng ảnh hưởng đến ví (amount +/- fee)
    current_balance_before DECIMAL(18, 4) NOT NULL, -- Số dư ví TRƯỚC khi giao dịch
    current_balance_after  DECIMAL(18, 4) NOT NULL, -- Số dư ví SAU khi giao dịch
    status                 VARCHAR(20)    NOT NULL, -- PENDING, COMPLETED, FAILED, REFUNDED, CANCELLED, REVERSED
    reference_id           BINARY(16) NULL,        -- ID của bản ghi liên quan (order_id, deposit_request_id, withdrawal_request_id, target_wallet_id)
    reference_type         VARCHAR(50) NULL,       -- Loại tham chiếu: ORDER, DEPOSIT_REQUEST, WITHDRAWAL_REQUEST, WALLET (cho chuyển khoản nội bộ)
    description            TEXT,                   -- Mô tả chi tiết giao dịch
    external_transaction_id VARCHAR(255) NULL,      -- ID giao dịch từ cổng thanh toán bên ngoài (VNPay transaction ID, etc.)
    idempotency_key        VARCHAR(255) NULL UNIQUE, -- Khóa đảm bảo duy nhất cho mỗi yêu cầu để tránh xử lý trùng lặp
    created_at             TIMESTAMP      DEFAULT CURRENT_TIMESTAMP,
    updated_at             TIMESTAMP      DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    FOREIGN KEY            (wallet_id) REFERENCES wallets (id) ON DELETE CASCADE,
    INDEX                  idx_trans_wallet_id (wallet_id),
    INDEX                  idx_trans_type_status (transaction_type, status),
    INDEX                  idx_trans_reference (reference_id, reference_type)
);

-- 3. Bảng `deposit_requests`: Lưu trữ các yêu cầu nạp tiền vào ví
CREATE TABLE deposit_requests
(
    id                     BINARY(16)     NOT NULL DEFAULT (UUID_TO_BIN(UUID())) PRIMARY KEY,
    order_id               BINARY(16) NULL, -- Mã đơn hàng
    amount                 DECIMAL(18, 4) NOT NULL, -- Số tiền người dùng muốn nạp
    payment_method         VARCHAR(50)    NOT NULL , -- Phương thức thanh toán (VNPAY_QR, BANK_TRANSFER, etc.)
    bank_code              VARCHAR(50) NULL, -- Mã ngân hàng mà người dùng dùng để thanh toán (nếu có)
    vnpay_txn_ref          VARCHAR(255) NULL UNIQUE, -- Mã giao dịch bên phía VNPay
    vnpay_order_info       VARCHAR(255) NULL,      -- Thông tin đơn hàng gửi sang VNPay
    vnpay_response_code    VARCHAR(10) NULL,     -- Mã phản hồi cuối cùng từ VNPay (00 là thành công)
    status                 VARCHAR(20)    NOT NULL , -- PENDING, SUCCESS, FAILED, CANCELLED
    error_message          TEXT,                   -- Thông báo lỗi từ VNPay hoặc hệ thống
    wallet_transaction_id BINARY(16) NULL, -- Liên kết đến wallet_transaction khi giao dịch thành công
    created_at             TIMESTAMP      DEFAULT CURRENT_TIMESTAMP,
    updated_at             TIMESTAMP      DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    FOREIGN KEY            (wallet_id) REFERENCES wallets (id) ON DELETE CASCADE,
    INDEX                  idx_deposit_wallet_id (wallet_id),
    INDEX                  idx_deposit_status (status),
    INDEX                  idx_deposit_vnpay_txn (vnpay_txn_ref)
);

-- 4. Bảng `withdrawal_requests`: Lưu trữ các yêu cầu rút tiền từ ví
CREATE TABLE withdrawal_requests
(
    id                     BINARY(16)     NOT NULL DEFAULT (UUID_TO_BIN(UUID())) PRIMARY KEY,
    wallet_id              BINARY(16)     NOT NULL,
    amount                 DECIMAL(18, 4) NOT NULL, -- Số tiền người dùng muốn rút
    fee_amount             DECIMAL(18, 4) NOT NULL DEFAULT 0.0000, -- Phí rút tiền
    net_amount             DECIMAL(18, 4) NOT NULL, -- Số tiền thực nhận sau khi trừ phí
    recipient_bank_name    VARCHAR(100)   NOT NULL, -- Tên ngân hàng nhận (VD: VIETCOMBANK)
    recipient_bank_account VARCHAR(100)   NOT NULL, -- Số tài khoản nhận
    recipient_account_name VARCHAR(100)   NOT NULL, -- Tên chủ tài khoản nhận
    vnpay_txn_ref          VARCHAR(255) NULL UNIQUE, -- Mã giao dịch VNPay cho lệnh chuyển tiền đi (nếu sử dụng API chuyển tiền của VNPay)
    status                 VARCHAR(20)    NOT NULL , -- PENDING_APPROVAL, APPROVED, REJECTED, PROCESSING, COMPLETED, FAILED, CANCELLED
    reviewer_id            BINARY(16) NULL,        -- ID của admin duyệt yêu cầu
    review_notes           TEXT,                   -- Ghi chú của admin khi duyệt/từ chối
    error_message          TEXT,                   -- Lỗi nếu giao dịch chuyển tiền thất bại
    wallet_transaction_id BINARY(16) NULL, -- Liên kết đến wallet_transaction đã tạo khi yêu cầu được tạo
    created_at             TIMESTAMP      DEFAULT CURRENT_TIMESTAMP,
    updated_at             TIMESTAMP      DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    FOREIGN KEY            (wallet_id) REFERENCES wallets (id) ON DELETE CASCADE,
    INDEX                  idx_withdraw_wallet_id (wallet_id),
    INDEX                  idx_withdraw_status (status)
);

-- 5. Bảng `payment_transactions`: Lưu trữ các giao dịch thanh toán trực tiếp cho đơn hàng (không qua ví)
-- Dùng cho trường hợp người dùng chọn quét mã VNPay trực tiếp để thanh toán đơn hàng.
CREATE TABLE payment_transactions
(
    id                     BINARY(16)     NOT NULL DEFAULT (UUID_TO_BIN(UUID())) PRIMARY KEY,
    order_id               BINARY(16)     NOT NULL, -- Liên kết đến Order Service (đơn hàng cần thanh toán)
    user_id                BINARY(16)     NOT NULL, -- Người dùng thực hiện thanh toán
    amount                 DECIMAL(18, 4) NOT NULL, -- Số tiền của đơn hàng
    currency               VARCHAR(10)    NOT NULL,
    payment_method         VARCHAR(50)    NOT NULL, -- VNPAY_QR, VNPAY_BANKING, etc.
    gateway_transaction_id VARCHAR(255) NULL UNIQUE, -- ID giao dịch từ cổng thanh toán (vnp_TxnRef của VNPay)
    status                 VARCHAR(20)    NOT NULL , -- PENDING, SUCCESS, FAILED, REFUNDED, CANCELED
    error_message          TEXT,                   -- Thông báo lỗi nếu có
    ip_address             VARCHAR(45) NULL,        -- IP của người dùng khi thực hiện giao dịch (cho mục đích an ninh)
    user_agent             VARCHAR(255) NULL,       -- User-Agent của trình duyệt/ứng dụng
    created_at             TIMESTAMP      DEFAULT CURRENT_TIMESTAMP,
    updated_at             TIMESTAMP      DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    INDEX                  idx_paytrans_order_id (order_id),
    INDEX                  idx_paytrans_user_id (user_id),
    INDEX                  idx_paytrans_status (status),
    INDEX                  idx_paytrans_gateway_id (gateway_transaction_id)
);


CREATE TABLE `send_message_error`
(
    id         binary(16) NOT NULL DEFAULT (uuid_to_bin(uuid())),
    topic       varchar(255) NOT NULL,
    message text NOT NULL,
    status varchar(50) NOT NULL,
    created_at  timestamp NULL DEFAULT CURRENT_TIMESTAMP,
    PRIMARY KEY (id)
)

