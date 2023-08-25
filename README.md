//////////DB////////
CREATE TABLE roots (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    root DOUBLE
);

CREATE TABLE expressions (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    expression VARCHAR(255)
);

CREATE TABLE root_expression (
    root_id BIGINT,
    expression_id BIGINT,
    FOREIGN KEY (root_id) REFERENCES roots(id),
    FOREIGN KEY (expression_id) REFERENCES expressions(id)
);
