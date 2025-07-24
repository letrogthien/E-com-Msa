

-- Database: product_service

-- `categories`: Defines the hierarchical structure of your marketplace's offerings.
CREATE TABLE categories
(
    id          BINARY(16)   NOT NULL DEFAULT (UUID_TO_BIN(UUID())) PRIMARY KEY,
    name        VARCHAR(100) NOT NULL UNIQUE,
    slug        VARCHAR(100) NOT NULL UNIQUE, -- For clean URLs (e.g., /game-accounts)
    parent_id   BINARY(16) NULL,              -- For nested categories (e.g., "Game Accounts" > "LoL Accounts")
    description TEXT,
    icon_url    VARCHAR(255),
    created_at  TIMESTAMP    DEFAULT CURRENT_TIMESTAMP,
    updated_at  TIMESTAMP    DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    FOREIGN KEY (parent_id) REFERENCES categories (id) ON DELETE SET NULL,
    INDEX       idx_category_name (name),
    INDEX       idx_category_parent (parent_id)
);

-- `category_attributes`: Defines the custom fields required for products within a specific category.
CREATE TABLE category_attributes
(
    id                BINARY(16)   NOT NULL DEFAULT (UUID_TO_BIN(UUID())) PRIMARY KEY,
    category_id       BINARY(16)   NOT NULL,
    attribute_name    VARCHAR(100) NOT NULL, -- e.g., "Server", "Game Rank", "Character Name"
    attribute_type    VARCHAR(20)  NOT NULL, -- 'STRING', 'INTEGER', 'BOOLEAN', 'ENUM', 'TEXT'
    is_required       BOOLEAN      NOT NULL DEFAULT FALSE,
    options_json      JSON NULL,             -- For 'ENUM' types, holds allowed values (e.g., ["NA", "EU", "ASIA"])
    validation_regex  VARCHAR(255),          -- Regex for input validation on the frontend/backend
    display_order     INT          NOT NULL DEFAULT 0, -- Order for displaying input fields
    created_at        TIMESTAMP    DEFAULT CURRENT_TIMESTAMP,
    updated_at        TIMESTAMP    DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    FOREIGN KEY (category_id) REFERENCES categories (id) ON DELETE CASCADE,
    UNIQUE (category_id, attribute_name), -- Ensures unique attribute names per category
    INDEX             idx_cat_attr_category (category_id)
);

-- `products`: The main table for all product listings on your marketplace.
CREATE TABLE products
(
    id              BINARY(16)   NOT NULL DEFAULT (UUID_TO_BIN(UUID())) PRIMARY KEY,
    seller_id       BINARY(16)   NOT NULL, -- UUID from the User Service, identifies the seller
    category_id     BINARY(16)   NOT NULL, -- Links to the `categories` table
    title           VARCHAR(255) NOT NULL,
    description     TEXT,
    price           DECIMAL(10, 2) NOT NULL,
    currency        VARCHAR(10)  NOT NULL, -- e.g., 'USD', 'EUR', 'VND'
    stock_quantity  INT          NOT NULL DEFAULT 1, -- Quantity available (0 for services/digital items sold once)
    product_status  VARCHAR(50)  NOT NULL , -- 'DRAFT', 'PENDING_REVIEW', 'ACTIVE', 'SOLD_OUT', 'PAUSED', 'REJECTED'
    thumbnail_url   VARCHAR(255), -- Main image for the listing
    details_json    JSON NULL,              -- **Key for dynamic attributes**: stores data for `category_attributes`
    created_at      TIMESTAMP    DEFAULT CURRENT_TIMESTAMP,
    updated_at      TIMESTAMP    DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    FOREIGN KEY (category_id) REFERENCES categories (id) ON DELETE RESTRICT, -- Prevent deleting categories with active products
    INDEX           idx_product_seller (seller_id),
    INDEX           idx_product_category (category_id),
    INDEX           idx_product_status (product_status)
);

-- `product_images`: Stores additional images for a product listing.
CREATE TABLE product_images
(
    id          BINARY(16)   NOT NULL DEFAULT (UUID_TO_BIN(UUID())) PRIMARY KEY,
    product_id  BINARY(16)   NOT NULL,
    image_url   VARCHAR(255) NOT NULL,
    display_order INT        NOT NULL DEFAULT 0, -- Order in which images are displayed
    created_at  TIMESTAMP    DEFAULT CURRENT_TIMESTAMP,
    updated_at  TIMESTAMP    DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (product_id) REFERENCES products (id) ON DELETE CASCADE,
    INDEX       idx_image_product (product_id)
);

-- `product_reviews`: Stores customer reviews and ratings for specific products.
CREATE TABLE product_reviews
(
    id          BINARY(16)   NOT NULL DEFAULT (UUID_TO_BIN(UUID())) PRIMARY KEY,
    product_id  BINARY(16)   NOT NULL,
    user_id     BINARY(16)   NOT NULL, -- Reviewer's ID from the User Service
    rating_score TINYINT     NOT NULL CHECK (rating_score BETWEEN 1 AND 5),
    review_text TEXT,
    created_at  TIMESTAMP    DEFAULT CURRENT_TIMESTAMP,
    updated_at  TIMESTAMP    DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    status      VARCHAR(20)  NOT NULL, -- For moderation ('PENDING', 'APPROVED', 'REJECTED')
    FOREIGN KEY (product_id) REFERENCES products (id) ON DELETE CASCADE,
    INDEX       idx_review_product (product_id),
    INDEX       idx_review_user (user_id)
);

-- `product_variants`: Optional table for products with multiple specific options (e.g., an item with different colors/sizes/servers if not covered by dynamic attributes).
CREATE TABLE product_variants
(
    id                  BINARY(16)   NOT NULL DEFAULT (UUID_TO_BIN(UUID())) PRIMARY KEY,
    product_id          BINARY(16)   NOT NULL,
    sku                 VARCHAR(100) NOT NULL UNIQUE, -- Unique Stock Keeping Unit for this variant
    price_adjustment    DECIMAL(10,2) NULL, -- Optional price difference from the base product price
    stock_quantity      INT          NOT NULL,
    variant_attributes_json JSON NULL,         -- e.g., {"Color": "Red", "Size": "M", "Server": "EU"}
    thumbnail_url       VARCHAR(255), -- Specific image for this variant
    created_at          TIMESTAMP    DEFAULT CURRENT_TIMESTAMP,
    updated_at          TIMESTAMP    DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    FOREIGN KEY (product_id) REFERENCES products (id) ON DELETE CASCADE,
    INDEX               idx_variant_product (product_id)
);