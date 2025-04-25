CREATE TABLE IF NOT EXISTS booking (
  booking_id VARCHAR(255) PRIMARY KEY,
  user_id INT NOT NULL,
  product_id INT NOT NULL,
  booking_start_date DATE NOT NULL,
  booking_end_date DATE NOT NULL,
  status VARCHAR(20) DEFAULT 'Pending',
  total_amount DECIMAL(10,2),
  created_at DATETIME DEFAULT CURRENT_TIMESTAMP,
  updated_at DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP
);

CREATE TABLE IF NOT EXISTS booking_history (
  id INT AUTO_INCREMENT PRIMARY KEY,
  booking_id VARCHAR(255),
  status VARCHAR(20),
  changed_at DATETIME DEFAULT CURRENT_TIMESTAMP,
  changed_by INT
);

CREATE TABLE IF NOT EXISTS product_bookings (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    product_id INT NOT NULL,
    date DATE NOT NULL
);
