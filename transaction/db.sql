CREATE DATABASE IF NOT EXISTS `order_db`;

USE `order_db`;

-- MySQL 8.0+ supports uuid_to_bin(uuid()) directly as a default value.
-- If you are on an older MySQL version (e.g., 5.7), you might need triggers
-- or generate UUIDs in your application layer.

CREATE TABLE `orders` (
    `id` BINARY(16) NOT NULL DEFAULT (uuid_to_bin(uuid())) PRIMARY KEY,
    `order_code` VARCHAR(50) NOT NULL UNIQUE,
    `buyer_id` BINARY(16) NOT NULL,
    `seller_id` BINARY(16) NOT NULL,
    `total_products_amount` DECIMAL(12, 2) NOT NULL,
    `currency` VARCHAR(5) NOT NULL,
    `shipping_cost` DECIMAL(10, 2) NOT NULL,
    `discount_amount` DECIMAL(10, 2) NOT NULL,
    `final_amount` DECIMAL(12, 2) NOT NULL,
    `order_status` VARCHAR(30) NOT NULL,
    `payment_method` VARCHAR(50) NULL,
    `payment_ref_id` BINARY(16) NULL,
    `payment_gateway_txn_id` VARCHAR(255) NULL,
    `payment_status_detail` VARCHAR(30) NOT NULL,
    `order_notes` TEXT NULL,
    `order_date` DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP,
    `last_updated_at` DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    INDEX `idx_order_buyer_id` (`buyer_id`),
    INDEX `idx_order_seller_id` (`seller_id`),
    INDEX `idx_order_status` (`order_status`),
    INDEX `idx_order_payment_ref_id` (`payment_ref_id`)
);

CREATE TABLE `order_items` (
    `id` BINARY(16) NOT NULL DEFAULT (uuid_to_bin(uuid())) PRIMARY KEY,
    `order_id` BINARY(16) NOT NULL,
    `product_id` BINARY(16) NOT NULL,
    `product_name` VARCHAR(255) NOT NULL,
    `sku` VARCHAR(100) NULL,
    `quantity` INT NOT NULL,
    `unit_price` DECIMAL(10, 2) NOT NULL,
    `total_item_price` DECIMAL(10, 2) NOT NULL,
    `digital_asset_info` JSON NULL,
    `created_at` DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (`order_id`) REFERENCES `orders`(`id`) ON DELETE CASCADE,
    INDEX `idx_order_item_product_id` (`product_id`)
);

CREATE TABLE `transaction_events_log` (
    `id` BINARY(16) NOT NULL DEFAULT (uuid_to_bin(uuid())) PRIMARY KEY,
    `order_id` BINARY(16) NOT NULL,
    `event_type` VARCHAR(100) NOT NULL,
    `event_data` JSON NULL,
    `description` TEXT NULL,
    `logged_at` DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP,
    `actor_id` BINARY(16) NULL,
    FOREIGN KEY (`order_id`) REFERENCES `orders`(`id`) ON DELETE CASCADE,
    INDEX `idx_trans_log_order_id` (`order_id`),
    INDEX `idx_trans_log_event_type` (`event_type`)
);

CREATE TABLE `carts` (
    `id` BINARY(16) NOT NULL DEFAULT (uuid_to_bin(uuid())) PRIMARY KEY,
    `user_id` BINARY(16) NOT NULL UNIQUE,
    `created_at` DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP,
    `updated_at` DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    INDEX `idx_cart_user_id` (`user_id`)
);

CREATE TABLE `cart_items` (
    `id` BINARY(16) NOT NULL DEFAULT (uuid_to_bin(uuid())) PRIMARY KEY,
    `cart_id` BINARY(16) NOT NULL,
    `product_id` BINARY(16) NOT NULL,
    `quantity` INT NOT NULL,
    `added_at` DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP,
    `updated_at` DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    FOREIGN KEY (`cart_id`) REFERENCES `carts`(`id`) ON DELETE CASCADE,
    UNIQUE KEY `uq_cart_product` (`cart_id`, `product_id`),
    INDEX `idx_cart_item_product_id` (`product_id`)
);