

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
    status      VARCHAR(20) NOT NULL,
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
    status            VARCHAR(20) NOT NULL,
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
    status          VARCHAR(20) NOT NULL,
    -- 'DRAFT', 'PENDING_REVIEW', 'ACTIVE', 'SOLD_OUT', 'PAUSED', 'REJECTED'
    thumbnail_url   VARCHAR(255), -- Main image for the listing
    details_json    JSON NULL,              -- **Key for dynamic attributes**: stores data for `category_attributes`
    created_at      TIMESTAMP    DEFAULT CURRENT_TIMESTAMP,
    updated_at      TIMESTAMP    DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    FOREIGN KEY (category_id) REFERENCES categories (id) ON DELETE RESTRICT, -- Prevent deleting categories with active products
    INDEX           idx_product_seller (seller_id),
    INDEX           idx_product_category (category_id),
    INDEX           idx_product_status (status)
);

-- `product_images`: Stores additional images for a product listing.
CREATE TABLE product_images
(
    id          BINARY(16)   NOT NULL DEFAULT (UUID_TO_BIN(UUID())) PRIMARY KEY,
    product_id  BINARY(16)   NOT NULL,
    image_url   VARCHAR(255) NOT NULL,
    status      VARCHAR(20) NOT NULL,
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
    status      VARCHAR(20) NOT NULL,
    variant_attributes_json JSON NULL,         -- e.g., {"Color": "Red", "Size": "M", "Server": "EU"}
    thumbnail_url       VARCHAR(255), -- Specific image for this variant
    created_at          TIMESTAMP    DEFAULT CURRENT_TIMESTAMP,
    updated_at          TIMESTAMP    DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    FOREIGN KEY (product_id) REFERENCES products (id) ON DELETE CASCADE,
    INDEX               idx_variant_product (product_id)
);



INSERT INTO categories (id, name, slug, parent_id, description, icon_url, status)
VALUES 
    (UUID_TO_BIN(UUID()), 'Digital Games', 'digital-games', NULL, 'Digital game products and services', '/icons/digital-games.png', 'ACTIVE'),
    (UUID_TO_BIN(UUID()), 'Game Accounts', 'game-accounts', NULL, 'Gaming accounts for various platforms', '/icons/game-accounts.png', 'ACTIVE'),
    (UUID_TO_BIN(UUID()), 'Virtual Items', 'virtual-items', NULL, 'In-game items and virtual goods', '/icons/virtual-items.png', 'ACTIVE'),
    (UUID_TO_BIN(UUID()), 'Gaming Services', 'gaming-services', NULL, 'Professional gaming services', '/icons/gaming-services.png', 'ACTIVE');

-- Store parent category IDs in variables for subcategories
SET @digital_games_id = (SELECT id FROM categories WHERE slug = 'digital-games');
SET @game_accounts_id = (SELECT id FROM categories WHERE slug = 'game-accounts');
SET @virtual_items_id = (SELECT id FROM categories WHERE slug = 'virtual-items');
SET @gaming_services_id = (SELECT id FROM categories WHERE slug = 'gaming-services');

-- Insert subcategories
INSERT INTO categories (id, name, slug, parent_id, description, icon_url, status)
VALUES 
    -- Digital Games subcategories
    (UUID_TO_BIN(UUID()), 'Steam Games', 'steam-games', @digital_games_id, 'Steam platform games', '/icons/steam-games.png', 'ACTIVE'),
    (UUID_TO_BIN(UUID()), 'Epic Games', 'epic-games', @digital_games_id, 'Epic Games platform products', '/icons/epic-games.png', 'ACTIVE'),
    (UUID_TO_BIN(UUID()), 'Origin Games', 'origin-games', @digital_games_id, 'EA Origin platform games', '/icons/origin-games.png', 'ACTIVE'),

    -- Game Accounts subcategories
    (UUID_TO_BIN(UUID()), 'League of Legends', 'lol-accounts', @game_accounts_id, 'LoL game accounts', '/icons/lol-accounts.png', 'ACTIVE'),
    (UUID_TO_BIN(UUID()), 'World of Warcraft', 'wow-accounts', @game_accounts_id, 'WoW game accounts', '/icons/wow-accounts.png', 'ACTIVE'),
    (UUID_TO_BIN(UUID()), 'Valorant', 'valorant-accounts', @game_accounts_id, 'Valorant game accounts', '/icons/valorant-accounts.png', 'ACTIVE'),

    -- Virtual Items subcategories
    (UUID_TO_BIN(UUID()), 'CSGO Skins', 'csgo-skins', @virtual_items_id, 'Counter-Strike: Global Offensive items', '/icons/csgo-skins.png', 'ACTIVE'),
    (UUID_TO_BIN(UUID()), 'LoL Skins', 'lol-skins', @virtual_items_id, 'League of Legends skins', '/icons/lol-skins.png', 'ACTIVE'),
    (UUID_TO_BIN(UUID()), 'Dota 2 Items', 'dota2-items', @virtual_items_id, 'Dota 2 virtual items', '/icons/dota2-items.png', 'ACTIVE'),

    -- Gaming Services subcategories
    (UUID_TO_BIN(UUID()), 'Game Coaching', 'game-coaching', @gaming_services_id, 'Professional gaming coaching', '/icons/game-coaching.png', 'ACTIVE'),
    (UUID_TO_BIN(UUID()), 'Power Leveling', 'power-leveling', @gaming_services_id, 'Character leveling services', '/icons/power-leveling.png', 'ACTIVE'),
    (UUID_TO_BIN(UUID()), 'Boosting Services', 'boosting-services', @gaming_services_id, 'Competitive rank boosting', '/icons/boosting-services.png', 'ACTIVE');

-- Insert category attributes for Game Accounts
INSERT INTO category_attributes (id, category_id, attribute_name, attribute_type, is_required, status, options_json, validation_regex, display_order)
VALUES
    -- LoL Accounts attributes
    (UUID_TO_BIN(UUID()), (SELECT id FROM categories WHERE slug = 'lol-accounts'), 'Server Region', 'ENUM', TRUE, 'ACTIVE', '["NA", "EU", "KR", "OCE"]', NULL, 1),
    (UUID_TO_BIN(UUID()), (SELECT id FROM categories WHERE slug = 'lol-accounts'), 'Account Level', 'INTEGER', TRUE, 'ACTIVE', NULL, '^[1-9][0-9]*$', 2),
    (UUID_TO_BIN(UUID()), (SELECT id FROM categories WHERE slug = 'lol-accounts'), 'Rank', 'ENUM', TRUE, 'ACTIVE', '["UNRANKED", "IRON", "BRONZE", "SILVER", "GOLD", "PLATINUM", "DIAMOND", "MASTER", "GRANDMASTER", "CHALLENGER"]', NULL, 3),
    
    -- WoW Accounts attributes
    (UUID_TO_BIN(UUID()), (SELECT id FROM categories WHERE slug = 'wow-accounts'), 'Server Type', 'ENUM', TRUE, 'ACTIVE', '["PVE", "PVP", "RP", "RPPVP"]', NULL, 1),
    (UUID_TO_BIN(UUID()), (SELECT id FROM categories WHERE slug = 'wow-accounts'), 'Character Level', 'INTEGER', TRUE, 'ACTIVE', NULL, '^[1-9][0-9]*$', 2),
    (UUID_TO_BIN(UUID()), (SELECT id FROM categories WHERE slug = 'wow-accounts'), 'Expansion', 'ENUM', TRUE, 'ACTIVE', '["Classic", "TBC", "WOTLK", "Retail"]', NULL, 3),
    
    -- CSGO Skins attributes
    (UUID_TO_BIN(UUID()), (SELECT id FROM categories WHERE slug = 'csgo-skins'), 'Wear', 'ENUM', TRUE, 'ACTIVE', '["Factory New", "Minimal Wear", "Field-Tested", "Well-Worn", "Battle-Scarred"]', NULL, 1),
    (UUID_TO_BIN(UUID()), (SELECT id FROM categories WHERE slug = 'csgo-skins'), 'StatTrak', 'BOOLEAN', TRUE, 'ACTIVE', NULL, NULL, 2),
    (UUID_TO_BIN(UUID()), (SELECT id FROM categories WHERE slug = 'csgo-skins'), 'Rarity', 'ENUM', TRUE, 'ACTIVE', '["Consumer Grade", "Industrial Grade", "Mil-Spec", "Restricted", "Classified", "Covert"]', NULL, 3);
