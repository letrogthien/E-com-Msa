CREATE
DATABASE user_service;

USE
user_service;

-- Table for user profiles
CREATE TABLE users
(
    id      VARCHAR(36) PRIMARY KEY, -- UUID from AuthService
    display_name VARCHAR(50)         NOT NULL,
    email        VARCHAR(100) UNIQUE NOT NULL,
    country      VARCHAR(50),
    status       VARCHAR(20) DEFAULT ,
    avatar_url   VARCHAR(255),
    is_seller    BOOLEAN   DEFAULT FALSE,
    seller_bio   TEXT,
    created_at   TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at   TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    deleted_at   TIMESTAMP NULL,          -- Soft delete for compliance
    INDEX        idx_email (email)
);

-- Table for user preferences
CREATE TABLE preferences
(
    id          VARCHAR(36) PRIMARY KEY, -- UUID
    user_id                VARCHAR(36) NOT NULL,
    notification_email     BOOLEAN   DEFAULT TRUE,
    notification_push      BOOLEAN   DEFAULT TRUE,
    preferred_currency     VARCHAR(10),             -- e.g., USD, BTC
    preferred_platform     VARCHAR(50),             -- e.g., Web, Mobile
    privacy_public_profile BOOLEAN   DEFAULT TRUE,
    created_at             TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at             TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    FOREIGN KEY (user_id) REFERENCES users (id) ON DELETE CASCADE
);

-- Table for transactions
CREATE TABLE transactions
(
    id   VARCHAR(36) PRIMARY KEY, -- UUID
    user_id          VARCHAR(36)    NOT NULL,
    product_id       VARCHAR(36)    NOT NULL, -- UUID from Product Service
    order_id         VARCHAR(36)    NOT NULL, -- UUID from Order Service
    transaction_type VARCHAR(20) NOT NULL,
    amount           DECIMAL(10, 2) NOT NULL,
    status           VARCHAR(20) NOT NULL,
    created_at       TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (user_id) REFERENCES users (id) ON DELETE CASCADE,
    INDEX            idx_user_id (user_id),
    INDEX            idx_product_id (product_id)
);

-- Table for seller ratings
CREATE TABLE seller_ratings
(
    id      VARCHAR(36) PRIMARY KEY, -- UUID
    seller_id      VARCHAR(36) NOT NULL,
    buyer_id       VARCHAR(36) NOT NULL,
    transaction_id VARCHAR(36) NOT NULL,
    rating_score   TINYINT     NOT NULL CHECK (rating_score BETWEEN 1 AND 5),
    review_text    TEXT,
    created_at     TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (seller_id) REFERENCES users (id) ON DELETE CASCADE,
    FOREIGN KEY (buyer_id) REFERENCES users (id) ON DELETE CASCADE,
    FOREIGN KEY (transaction_id) REFERENCES transactions (id) ON DELETE CASCADE,
    INDEX          idx_seller_id (seller_id)
);

-- Table for seller verifications
CREATE TABLE user_verifications
(
    id     VARCHAR(36) PRIMARY KEY, -- UUID
    user_id             VARCHAR(36) NOT NULL,
    verification_status VARCHAR(20) NOT NULL ,
    face_id_front_url         VARCHAR(255),    -- Link to secure storage
    face_id_back_url          VARCHAR(255),    -- Link to secure storage
    face_id_smile_url         VARCHAR(255),    -- Link to secure storage
    document_front_url        VARCHAR(255),
    document_back_url        VARCHAR(255),     -- Link to secure storage
    version              INT, -- Versioning for KYC documents
    verified_at         TIMESTAMP NULL,
    created_at          TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at          TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    FOREIGN KEY (user_id) REFERENCES users (id) ON DELETE CASCADE
);

CREATE TABLE delete_kyc_requests
(
    id          VARCHAR(36) PRIMARY KEY, -- UUID
    user_id     VARCHAR(36) NOT NULL,
    request_date TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    selfie_url  VARCHAR(255), -- Link to secure storage for selfie
    status      VARCHAR(20) NOT NULL, -- e.g., 'pending', 'approved', 'rejected'
    FOREIGN KEY (user_id) REFERENCES users (id) ON DELETE CASCADE
);