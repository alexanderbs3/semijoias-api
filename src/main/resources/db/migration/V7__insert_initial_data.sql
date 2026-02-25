INSERT INTO categories (name, description, active) VALUES
('Anéis', 'Anéis folheados a ouro e prata', true),
('Brincos', 'Brincos com pedras naturais', true),
('Colares', 'Correntes e pingentes exclusivos', true),
('Pulseiras', 'Pulseiras ajustáveis e braceletes', true);

INSERT INTO products (name, description, price, stock_quantity, image_url, category_id) VALUES
('Anel Solitário Cristal', 'Anel clássico folheado a ouro 18k', 89.90, 50, 'https://link-imagem.com/anel1.jpg', 1),
('Brinco Argola M', 'Argola lisa polida', 45.00, 100, 'https://link-imagem.com/brinco1.jpg', 2),
('Colar Ponto de Luz', 'Colar com zircônia premium', 120.00, 30, 'https://link-imagem.com/colar1.jpg', 3);

-- Senha 'admin123' hasheada com BCrypt
INSERT INTO users (name, email, password, role, state) VALUES
('Admin', 'admin@semijoia.com', '$2a$10$Y50UaMky4u.x.T.x1H9X/.x1H9X/.x1H9X/.x1H9X/.x1H9X/', 'ADMIN', 'SP');