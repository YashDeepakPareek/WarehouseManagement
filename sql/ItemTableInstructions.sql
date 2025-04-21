USE warehouse_db;

CREATE TABLE ItemCategory (
    id INT AUTO_INCREMENT PRIMARY KEY,
    category VARCHAR(50) UNIQUE
);

CREATE TABLE Item (
    id INT AUTO_INCREMENT PRIMARY KEY,
    description VARCHAR(255),
    height DOUBLE,
    width DOUBLE,
    depth DOUBLE,
    volume DOUBLE,
    location VARCHAR(100),
    quantity_in_stock INT,
    category_id INT,
    FOREIGN KEY (category_id) REFERENCES ItemCategory(id)
);

INSERT INTO ItemCategory (category) VALUES
('Coffee Appliances'),
('Cooking Appliances'),
('Food Prep'),
('Kitchen Essentials'),
('Specialty Appliances');

INSERT INTO Item (description, height, width, depth, volume, location, quantity_in_stock, category_id) VALUES
-- Coffee Appliances
('Coffee Maker', 35.0, 20.0, 25.0, 17500.0, 'A1-Shelf', 10, (SELECT id FROM ItemCategory WHERE category = 'Coffee Appliances')),
('Espresso Machine', 34.0, 22.0, 30.0, 22440.0, 'A1-Shelf', 6, (SELECT id FROM ItemCategory WHERE category = 'Coffee Appliances')),

-- Cooking Appliances
('Microwave Oven', 30.0, 45.0, 35.0, 47250.0, 'B2-Shelf', 5, (SELECT id FROM ItemCategory WHERE category = 'Cooking Appliances')),
('Air Fryer', 32.0, 28.0, 30.0, 26880.0, 'C1-Shelf', 6, (SELECT id FROM ItemCategory WHERE category = 'Cooking Appliances')),
('Mini Oven', 28.0, 40.0, 35.0, 39200.0, 'C2-Shelf', 3, (SELECT id FROM ItemCategory WHERE category = 'Cooking Appliances')),
('Bread Maker', 30.0, 30.0, 30.0, 27000.0, 'B3-Shelf', 4, (SELECT id FROM ItemCategory WHERE category = 'Cooking Appliances')),

-- Food Prep
('Blender', 40.0, 15.0, 20.0, 12000.0, 'A2-Shelf', 12, (SELECT id FROM ItemCategory WHERE category = 'Food Prep')),
('Food Processor', 30.0, 25.0, 25.0, 18750.0, 'D1-Shelf', 10, (SELECT id FROM ItemCategory WHERE category = 'Food Prep')),
('Waffle Maker', 10.0, 25.0, 25.0, 6250.0, 'D2-Shelf', 14, (SELECT id FROM ItemCategory WHERE category = 'Food Prep')),

-- Kitchen Essentials
('Toaster', 20.0, 30.0, 20.0, 12000.0, 'A1-Shelf', 8, (SELECT id FROM ItemCategory WHERE category = 'Kitchen Essentials')),
('Electric Kettle', 25.0, 20.0, 20.0, 10000.0, 'A3-Shelf', 15, (SELECT id FROM ItemCategory WHERE category = 'Kitchen Essentials')),
('Rice Cooker', 30.0, 25.0, 25.0, 18750.0, 'B1-Shelf', 7, (SELECT id FROM ItemCategory WHERE category = 'Kitchen Essentials')),
('Slow Cooker', 25.0, 35.0, 30.0, 26250.0, 'C3-Shelf', 9, (SELECT id FROM ItemCategory WHERE category = 'Kitchen Essentials')),
('Steam Cooker', 35.0, 25.0, 25.0, 218
