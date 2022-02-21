DROP TABLE IF EXISTS oauth_client;
CREATE TABLE oauth_client  (
    client_id varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT 'github oauth应用ID',
    client_secrets varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT 'github oauth应用密钥',
    PRIMARY KEY (client_id) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;