CREATE TABLE certification (

    id BIGINT PRIMARY KEY AUTO_INCREMENT,

    certification_name VARCHAR(255) NOT NULL,

    issuing_organization VARCHAR(255) NOT NULL,

    issue_date DATE,

    expiry_date DATE,

    credential_id VARCHAR(255),

    credential_url VARCHAR(500),

    display_order INT NOT NULL,

    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,

    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
        ON UPDATE CURRENT_TIMESTAMP
);