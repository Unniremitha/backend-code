CREATE TABLE user_document(
     id UUID PRIMARY KEY NOT NULL,
     user_id UUID,
     document_number VARCHAR(255) NOT NULL,
     document_name VARCHAR(255) NOT NULL,
     document_type VARCHAR(255) NOT NULL,
     FOREIGN KEY (user_id) REFERENCES k_smart_user(user_id)
    );

