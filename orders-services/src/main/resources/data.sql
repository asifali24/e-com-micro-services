-- Seed orders. The explicit UUIDs make these rows stable across restarts.
INSERT INTO orders (id, order_status, total_price)
VALUES
    ('9bce9994-65dd-4035-8bbc-3f029ba6a11b', 'PENDING', 5097),
    ('60411645-d614-4cc9-bc9b-10966fd3399b', 'APPROVED', 14698),
    ('087bd681-0f17-4433-a226-d43cb5aafd8b', 'APPROVED', 11996),
    ('b92027c9-9760-4efe-bff0-28ca6ec45bfb', 'CANCELED', 17798),
    ('34e5c9f2-fff0-4ead-814d-5d1d3c66fdda', 'PENDING', 11797)
ON CONFLICT (id) DO NOTHING;

-- Product IDs correspond to the products seeded by inventory-services.
INSERT INTO orders_items (id, product_id, quantity, order_id)
VALUES
    ('46a88dac-b1a4-4d66-b8e1-bc4934ad6429', 'b6b49536-2a13-4214-b97b-9c58282b32b0', 1, '9bce9994-65dd-4035-8bbc-3f029ba6a11b'),
    ('90078236-790a-4a35-934e-cf1fa51dd9cb', '5837ab4c-4b11-41f2-af4d-1dafd1ed2d81', 2, '9bce9994-65dd-4035-8bbc-3f029ba6a11b'),
    ('310fd274-2bdf-4bf5-8aff-a50e684bc6ef', 'df1acd51-bece-40c4-bb63-1d4d6be175f7', 1, '60411645-d614-4cc9-bc9b-10966fd3399b'),
    ('2a17c90e-bb8a-45a0-bf9c-3585c0f89b41', 'f3cc7cd3-8f5b-4b29-a84b-bd16ce4850dd', 1, '60411645-d614-4cc9-bc9b-10966fd3399b'),
    ('6261fbb1-4ba6-4563-8375-7ebf815bcfac', '3000bc3d-e486-4cf8-bf6f-aeba2c29ad0f', 1, '087bd681-0f17-4433-a226-d43cb5aafd8b'),
    ('10fe142f-86e5-47d1-b326-3143e1a307e4', '38e25f04-41b3-4c81-957e-440909cfe85f', 1, '087bd681-0f17-4433-a226-d43cb5aafd8b'),
    ('39ae50e0-c12d-4b3b-9e5e-cf20fc9f0fb5', '0b662b05-fb1c-466a-81fe-e3e6a96de37f', 2, '087bd681-0f17-4433-a226-d43cb5aafd8b'),
    ('639c5ce6-d98c-4b37-b68a-719d3ae606a5', '135a24ad-0567-4e08-862c-761a8fde9250', 1, 'b92027c9-9760-4efe-bff0-28ca6ec45bfb'),
    ('3b9733c9-42b5-41d7-9329-f0b84f9df198', '99fbe07e-2ffa-49a5-8b40-d0e28b1d5b89', 1, 'b92027c9-9760-4efe-bff0-28ca6ec45bfb'),
    ('40582cfd-b66a-4ff2-98c5-d025ac3dc3ed', '5e0196f9-6fbe-4b2b-9d0d-11c9a487fa42', 1, '34e5c9f2-fff0-4ead-814d-5d1d3c66fdda'),
    ('49329958-714c-4c72-98e6-8ee72b55eebd', '748e4afd-3835-450c-b4c3-57947c72fbb9', 1, '34e5c9f2-fff0-4ead-814d-5d1d3c66fdda'),
    ('a15637c4-404f-4492-a6de-2e182ad6f07a', 'e9540734-370f-4c74-9612-0187694a2734', 1, '34e5c9f2-fff0-4ead-814d-5d1d3c66fdda')
ON CONFLICT (id) DO NOTHING;
