-- ProfitIQ Database Initialization Script
-- Run this script with: psql -U postgres -f init_db.sql

-- Create the database
CREATE DATABASE profitiq;

-- Connect to the profitiq database
\c profitiq

-- Enable pgcrypto extension for password hashing
CREATE EXTENSION IF NOT EXISTS pgcrypto;

-- Create tables
CREATE TABLE IF NOT EXISTS users (
    id SERIAL PRIMARY KEY,
    username VARCHAR(50) UNIQUE NOT NULL,
    email VARCHAR(100) UNIQUE NOT NULL,
    password_hash TEXT NOT NULL,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

CREATE TABLE IF NOT EXISTS companies (
    id SERIAL PRIMARY KEY,
    name VARCHAR(100) NOT NULL,
    revenue DECIMAL(15, 2) NOT NULL,
    profit DECIMAL(15, 2) NOT NULL,
    growth_percent DECIMAL(5, 2) NOT NULL,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

CREATE TABLE IF NOT EXISTS colleges (
    id SERIAL PRIMARY KEY,
    name VARCHAR(100) NOT NULL,
    placement_rate DECIMAL(5, 2) NOT NULL,
    avg_salary DECIMAL(10, 2) NOT NULL,
    recruiters INTEGER NOT NULL,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

CREATE TABLE IF NOT EXISTS research (
    id SERIAL PRIMARY KEY,
    paper_title VARCHAR(200) NOT NULL,
    author VARCHAR(100) NOT NULL,
    citations INTEGER NOT NULL,
    year INTEGER NOT NULL,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

-- Insert sample data
INSERT INTO companies (name, revenue, profit, growth_percent) VALUES
('TechCorp', 1000000.00, 250000.00, 15.50),
('Innovate Inc', 750000.00, 180000.00, 12.75),
('Global Solutions', 2000000.00, 400000.00, 8.25);

INSERT INTO colleges (name, placement_rate, avg_salary, recruiters) VALUES
('MIT', 95.50, 120000.00, 150),
('Stanford', 93.75, 115000.00, 140),
('Harvard', 90.25, 110000.00, 130);

INSERT INTO research (paper_title, author, citations, year) VALUES
('Machine Learning Advances', 'Dr. Smith', 500, 2020),
('Quantum Computing Breakthrough', 'Dr. Johnson', 350, 2021),
('AI in Healthcare', 'Dr. Williams', 420, 2019);

-- Display success message
\echo 'Database initialization completed successfully!'