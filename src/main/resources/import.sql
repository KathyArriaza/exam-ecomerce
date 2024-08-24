INSERT INTO customers (name, email, address) VALUES ('Carlos Henandez', 'carlos@gmail.com', '123 Main St');
INSERT INTO customers (name, email, address) VALUES ('Luisa Juarez', 'lu@example.com', '45 th Street');

INSERT INTO categories (category_name, category_type) VALUES ('Electronicos', 'Dispositivos');
INSERT INTO categories (category_name, category_type) VALUES ('Pincel', 'N3');

INSERT INTO products (product_name, category_id) VALUES ('Audifono', 1);
INSERT INTO products (product_name, category_id) VALUES ('Pinceles', 2);

INSERT INTO delivery (type, status) VALUES ('Estandar', 'En Proceso');
INSERT INTO delivery (type, status) VALUES ('Rapida', 'Entregado');

INSERT INTO orders (order_date, customer_id, delivery_id) VALUES (CURRENT_DATE, 1, 1);
INSERT INTO orders (order_date, customer_id, delivery_id) VALUES (CURRENT_DATE, 2, 2);


