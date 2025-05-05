-- Prepopulate PLAN table
INSERT INTO plan (plan_id, plan_name, coverage_amount, base_premium, duration) VALUES
  (1, 'Plan 200k', 200000, 100.00, 20),
  (2, 'Plan 300k', 300000, 150.00, 20),
  (3, 'Plan 500k', 500000, 250.00, 20) on conflict do NOTHING;

-- Plan 200k - MONTHLY
INSERT INTO ruleset (ruleset_id, plan_id, rule_type, operator, age_limit, coverage_amount, premium_amount, gender, payment_frequency, status) VALUES
(1, 1, 'premium', '>=', 18, 200000, 100.00, 'M', 'MONTHLY', 'ACTIVE'),
(2, 1, 'premium', '<=', 30, 200000, 100.00, 'M', 'MONTHLY', 'ACTIVE'),
(3, 1, 'premium', '>=', 31, 200000, 120.00, 'M', 'MONTHLY', 'ACTIVE'),
(4, 1, 'premium', '<=', 50, 200000, 120.00, 'M', 'MONTHLY', 'ACTIVE'),
(5, 1, 'premium', '>=', 51, 200000, 150.00, 'M', 'MONTHLY', 'ACTIVE'),
(6, 1, 'premium', '<=', 60, 200000, 150.00, 'M', 'MONTHLY', 'ACTIVE'),

(7, 1, 'premium', '>=', 18, 200000, 90.00, 'F', 'MONTHLY', 'ACTIVE'),
(8, 1, 'premium', '<=', 30, 200000, 90.00, 'F', 'MONTHLY', 'ACTIVE'),
(9, 1, 'premium', '>=', 31, 200000, 110.00, 'F', 'MONTHLY', 'ACTIVE'),
(10,1, 'premium', '<=', 50, 200000, 110.00, 'F', 'MONTHLY', 'ACTIVE'),
(11,1, 'premium', '>=', 51, 200000, 140.00, 'F', 'MONTHLY', 'ACTIVE'),
(12,1, 'premium', '<=', 60, 200000, 140.00, 'F', 'MONTHLY', 'ACTIVE') on conflict do NOTHING;;

-- Plan 200k - YEARLY
INSERT INTO ruleset (ruleset_id, plan_id, rule_type, operator, age_limit, coverage_amount, premium_amount, gender, payment_frequency, status) VALUES
(101, 1, 'premium', '>=', 18, 200000, 1200.00, 'M', 'YEARLY', 'ACTIVE'),
(102, 1, 'premium', '<=', 30, 200000, 1200.00, 'M', 'YEARLY', 'ACTIVE'),
(103, 1, 'premium', '>=', 31, 200000, 1440.00, 'M', 'YEARLY', 'ACTIVE'),
(104, 1, 'premium', '<=', 50, 200000, 1440.00, 'M', 'YEARLY', 'ACTIVE'),
(105, 1, 'premium', '>=', 51, 200000, 1800.00, 'M', 'YEARLY', 'ACTIVE'),
(106, 1, 'premium', '<=', 60, 200000, 1800.00, 'M', 'YEARLY', 'ACTIVE'),

(107, 1, 'premium', '>=', 18, 200000, 1080.00, 'F', 'YEARLY', 'ACTIVE'),
(108, 1, 'premium', '<=', 30, 200000, 1080.00, 'F', 'YEARLY', 'ACTIVE'),
(109, 1, 'premium', '>=', 31, 200000, 1320.00, 'F', 'YEARLY', 'ACTIVE'),
(110,1, 'premium', '<=', 50, 200000, 1320.00, 'F', 'YEARLY', 'ACTIVE'),
(111,1, 'premium', '>=', 51, 200000, 1680.00, 'F', 'YEARLY', 'ACTIVE'),
(112,1, 'premium', '<=', 60, 200000, 1680.00, 'F', 'YEARLY', 'ACTIVE') on conflict do NOTHING;;

-- Plan 300k - MONTHLY
INSERT INTO ruleset (ruleset_id, plan_id, rule_type, operator, age_limit, coverage_amount, premium_amount, gender, payment_frequency, status) VALUES
(13, 2, 'premium', '>=', 18, 300000, 150.00, 'M', 'MONTHLY', 'ACTIVE'),
(14, 2, 'premium', '<=', 30, 300000, 150.00, 'M', 'MONTHLY', 'ACTIVE'),
(15, 2, 'premium', '>=', 31, 300000, 180.00, 'M', 'MONTHLY', 'ACTIVE'),
(16, 2, 'premium', '<=', 50, 300000, 180.00, 'M', 'MONTHLY', 'ACTIVE'),
(17, 2, 'premium', '>=', 51, 300000, 220.00, 'M', 'MONTHLY', 'ACTIVE'),
(18, 2, 'premium', '<=', 60, 300000, 220.00, 'M', 'MONTHLY', 'ACTIVE'),

(19, 2, 'premium', '>=', 18, 300000, 135.00, 'F', 'MONTHLY', 'ACTIVE'),
(20, 2, 'premium', '<=', 30, 300000, 135.00, 'F', 'MONTHLY', 'ACTIVE'),
(21, 2, 'premium', '>=', 31, 300000, 165.00, 'F', 'MONTHLY', 'ACTIVE'),
(22, 2, 'premium', '<=', 50, 300000, 165.00, 'F', 'MONTHLY', 'ACTIVE'),
(23, 2, 'premium', '>=', 51, 300000, 210.00, 'F', 'MONTHLY', 'ACTIVE'),
(24, 2, 'premium', '<=', 60, 300000, 210.00, 'F', 'MONTHLY', 'ACTIVE') on conflict do NOTHING;;

-- Plan 300k - YEARLY
INSERT INTO ruleset (ruleset_id, plan_id, rule_type, operator, age_limit, coverage_amount, premium_amount, gender, payment_frequency, status) VALUES
(113, 2, 'premium', '>=', 18, 300000, 1800.00, 'M', 'YEARLY', 'ACTIVE'),
(114, 2, 'premium', '<=', 30, 300000, 1800.00, 'M', 'YEARLY', 'ACTIVE'),
(115, 2, 'premium', '>=', 31, 300000, 2160.00, 'M', 'YEARLY', 'ACTIVE'),
(116, 2, 'premium', '<=', 50, 300000, 2160.00, 'M', 'YEARLY', 'ACTIVE'),
(117, 2, 'premium', '>=', 51, 300000, 2640.00, 'M', 'YEARLY', 'ACTIVE'),
(118, 2, 'premium', '<=', 60, 300000, 2640.00, 'M', 'YEARLY', 'ACTIVE'),

(119, 2, 'premium', '>=', 18, 300000, 1620.00, 'F', 'YEARLY', 'ACTIVE'),
(120, 2, 'premium', '<=', 30, 300000, 1620.00, 'F', 'YEARLY', 'ACTIVE'),
(121, 2, 'premium', '>=', 31, 300000, 1980.00, 'F', 'YEARLY', 'ACTIVE'),
(122, 2, 'premium', '<=', 50, 300000, 1980.00, 'F', 'YEARLY', 'ACTIVE'),
(123, 2, 'premium', '>=', 51, 300000, 2520.00, 'F', 'YEARLY', 'ACTIVE'),
(124, 2, 'premium', '<=', 60, 300000, 2520.00, 'F', 'YEARLY', 'ACTIVE') on conflict do NOTHING;;

-- Plan 500k - MONTHLY
INSERT INTO ruleset (ruleset_id, plan_id, rule_type, operator, age_limit, coverage_amount, premium_amount, gender, payment_frequency, status) VALUES
(25, 3, 'premium', '>=', 18, 500000, 250.00, 'M', 'MONTHLY', 'ACTIVE'),
(26, 3, 'premium', '<=', 30, 500000, 250.00, 'M', 'MONTHLY', 'ACTIVE'),
(27, 3, 'premium', '>=', 31, 500000, 300.00, 'M', 'MONTHLY', 'ACTIVE'),
(28, 3, 'premium', '<=', 50, 500000, 300.00, 'M', 'MONTHLY', 'ACTIVE'),
(29, 3, 'premium', '>=', 51, 500000, 370.00, 'M', 'MONTHLY', 'ACTIVE'),
(30, 3, 'premium', '<=', 60, 500000, 370.00, 'M', 'MONTHLY', 'ACTIVE'),

(31, 3, 'premium', '>=', 18, 500000, 225.00, 'F', 'MONTHLY', 'ACTIVE'),
(32, 3, 'premium', '<=', 30, 500000, 225.00, 'F', 'MONTHLY', 'ACTIVE'),
(33, 3, 'premium', '>=', 31, 500000, 275.00, 'F', 'MONTHLY', 'ACTIVE'),
(34, 3, 'premium', '<=', 50, 500000, 275.00, 'F', 'MONTHLY', 'ACTIVE'),
(35, 3, 'premium', '>=', 51, 500000, 350.00, 'F', 'MONTHLY', 'ACTIVE'),
(36, 3, 'premium', '<=', 60, 500000, 350.00, 'F', 'MONTHLY', 'ACTIVE') on conflict do NOTHING;;

-- Plan 500k - YEARLY
INSERT INTO ruleset (ruleset_id, plan_id, rule_type, operator, age_limit, coverage_amount, premium_amount, gender, payment_frequency, status) VALUES
(125, 3, 'premium', '>=', 18, 500000, 3000.00, 'M', 'YEARLY', 'ACTIVE'),
(126, 3, 'premium', '<=', 30, 500000, 3000.00, 'M', 'YEARLY', 'ACTIVE'),
(127, 3, 'premium', '>=', 31, 500000, 3600.00, 'M', 'YEARLY', 'ACTIVE'),
(128, 3, 'premium', '<=', 50, 500000, 3600.00, 'M', 'YEARLY', 'ACTIVE'),
(129, 3, 'premium', '>=', 51, 500000, 4440.00, 'M', 'YEARLY', 'ACTIVE'),
(130, 3, 'premium', '<=', 60, 500000, 4440.00, 'M', 'YEARLY', 'ACTIVE'),

(131, 3, 'premium', '>=', 18, 500000, 2700.00, 'F', 'YEARLY', 'ACTIVE'),
(132, 3, 'premium', '<=', 30, 500000, 2700.00, 'F', 'YEARLY', 'ACTIVE'),
(133, 3, 'premium', '>=', 31, 500000, 3300.00, 'F', 'YEARLY', 'ACTIVE'),
(134, 3, 'premium', '<=', 50, 500000, 3300.00, 'F', 'YEARLY', 'ACTIVE'),
(135, 3, 'premium', '>=', 51, 500000, 4200.00, 'F', 'YEARLY', 'ACTIVE'),
(136, 3, 'premium', '<=', 60, 500000, 4200.00, 'F', 'YEARLY', 'ACTIVE') on conflict do NOTHING;