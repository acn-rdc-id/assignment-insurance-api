-- Set active database
USE mysqldb;

-- Prepopulate PLAN table
INSERT IGNORE INTO plan (plan_id, plan_name, coverage_amount, base_premium, duration)
VALUES
    (1, 'Plan 200k', 200000, 100.00, 20),
    (2, 'Plan 300k', 300000, 150.00, 20),
    (3, 'Plan 500k', 500000, 250.00, 20);

-- Plan 200k - MONTHLY
INSERT IGNORE INTO ruleset (ruleset_id, plan_id, rule_type, operator, age_limit, coverage_amount, premium_amount, gender, payment_frequency, status)
VALUES
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
    (12,1, 'premium', '<=', 60, 200000, 140.00, 'F', 'MONTHLY', 'ACTIVE');

-- Plan 200k - YEARLY
INSERT IGNORE INTO ruleset (ruleset_id, plan_id, rule_type, operator, age_limit, coverage_amount, premium_amount, gender, payment_frequency, status)
VALUES
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
    (112,1, 'premium', '<=', 60, 200000, 1680.00, 'F', 'YEARLY', 'ACTIVE');

-- Plan 300k - MONTHLY
INSERT IGNORE INTO ruleset (ruleset_id, plan_id, rule_type, operator, age_limit, coverage_amount, premium_amount, gender, payment_frequency, status)
VALUES
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
    (24, 2, 'premium', '<=', 60, 300000, 210.00, 'F', 'MONTHLY', 'ACTIVE');

-- Plan 300k - YEARLY
INSERT IGNORE INTO ruleset (ruleset_id, plan_id, rule_type, operator, age_limit, coverage_amount, premium_amount, gender, payment_frequency, status)
VALUES
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
    (124, 2, 'premium', '<=', 60, 300000, 2520.00, 'F', 'YEARLY', 'ACTIVE');

-- Plan 500k - MONTHLY
INSERT IGNORE INTO ruleset (ruleset_id, plan_id, rule_type, operator, age_limit, coverage_amount, premium_amount, gender, payment_frequency, status)
VALUES
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
    (36, 3, 'premium', '<=', 60, 500000, 350.00, 'F', 'MONTHLY', 'ACTIVE');

-- Plan 500k - YEARLY
INSERT IGNORE INTO ruleset (ruleset_id, plan_id, rule_type, operator, age_limit, coverage_amount, premium_amount, gender, payment_frequency, status)
VALUES
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
    (136, 3, 'premium', '<=', 60, 500000, 4200.00, 'F', 'YEARLY', 'ACTIVE');

-- Prepopulate TERMS_DECLARATION table
INSERT IGNORE INTO TERMS_DECLARATION (CREATED_AT, IS_REQUIRED, STATUS, TERMS_HTML, UPDATED_AT)
VALUES (
    CURRENT_TIMESTAMP,
    FALSE,
    'Active',
    '<h2>Terms and Conditions</h2>
    <ol>
        <li>
            Are you aware that this product pays out benefits:
            <ul>
                <li>(i) Upon Death. / Selepas Kematian.</li>
                <li>(ii) Upon Total and Permanent Disability (TPD).</li>
            </ul>
            <p><strong>Yes</strong><br>
            If you are unsure, <a href="#">click here for more information</a>.</p>
        </li>
        <li>
			Are you aware that this product does not pay out benefits:
            <ul>
                <li>(i) In the event of death caused by suicide within 1 year from policy issue date.</li>
                <li>(ii) In the event of TPD caused by attempting suicide or self-inflicted bodily injuries while sane or insane.</li>
                <li>(iii) In the event of TPD caused by Pre-existing Conditions.</li>
            </ul>
            <p><strong>Yes</strong><br>
            If you are unsure, and for more details on exclusions, terms and conditions, <a href="#">click here</a>.</p>
        </li>
        <li>
            Are you aware that:
            <ul>
                <li>(i) If you change your mind, you have 15 days to return the policy from the date you receive the policy and you can obtain a refund.</li>
                <li>(ii) You can nominate your beneficiaries by <a href="#">clicking here</a> (you may wish to inform them about the policy to make payment of future claims easier).</li>
            </ul>
            <p><strong>Yes</strong><br>
            To make a claim, please refer to the claims guide. If you wish to access all other product documentation, <a href="#">click here</a>. For any other enquiries, our customer service can be reached at <a href="tel:1300225542">1300 22 5542</a> or <a href="mailto:customer.service@allianz.com.my">customer.service@allianz.com.my</a>.</p>
        </li>
    </ol>
    <p>I confirm that I have read your <a href="#">Privacy Notice</a> and agree to provide the relevant information for the purposes stated in the Privacy Notice. I understand that I can choose to unsubscribe at any time if I no longer wish to receive any marketing/promotions from Allianz by writing to <a href="mailto:customer.service@allianz.com.my">customer.service@allianz.com.my</a>.</p>
    <p>Also, I confirm that I have read and taken note of my duty of disclosure as laid down in the notice on Duty of Disclosure.</p>',
    CURRENT_TIMESTAMP
);

INSERT IGNORE INTO TERMS_DECLARATION (CREATED_AT, IS_REQUIRED, STATUS, TERMS_HTML, UPDATED_AT)
VALUES (
    CURRENT_TIMESTAMP,
    FALSE,
    'Inactive',
    '<h2>Terms and Conditions</h2>
    <ol>
        <li>
            Are you aware that this product pays out benefits:
            <ul>
                <li>(i) Upon Death. / Selepas Kematian.</li>
                <li>(ii) Upon Total and Permanent Disability (TPD).</li>
            </ul>
            <p><strong>Yes</strong><br>
            If you are unsure, <a href="#">click here for more information</a>.</p>
        </li>
        <li>
            Are you aware that this product does not pay out benefits:
            <ul>
                <li>(i) In the event of death caused by suicide within 1 year from policy issue date.</li>
                <li>(ii) In the event of TPD caused by attempting suicide or self-inflicted bodily injuries while sane or insane.</li>
                <li>(iii) In the event of TPD caused by Pre-existing Conditions.</li>
            </ul>
            <p><strong>Yes</strong><br>
            If you are unsure, and for more details on exclusions, terms and conditions, <a href="#">click here</a>.</p>
        </li>
        <li>
            Are you aware that:
            <ul>
                <li>(i) If you change your mind, you have 15 days to return the policy from the date you receive the policy and you can obtain a refund.</li>
                <li>(ii) You can nominate your beneficiaries by <a href="#">clicking here</a> (you may wish to inform them about the policy to make payment of future claims easier).</li>
            </ul>
            <p><strong>Yes</strong><br>
            To make a claim, please refer to the claims guide. If you wish to access all other product documentation, <a href="#">click here</a>. For any other enquiries, our customer service can be reached at <a href="tel:1300225542">1300 22 5542</a> or <a href="mailto:customer.service@allianz.com.my">customer.service@allianz.com.my</a>.</p>
        </li>
    </ol>
    <p>I confirm that I have read your <a href="#">Privacy Notice</a> and agree to provide the relevant information for the purposes stated in the Privacy Notice. I understand that I can choose to unsubscribe at any time if I no longer wish to receive any marketing/promotions from Allianz by writing to <a href="mailto:customer.service@allianz.com.my">customer.service@allianz.com.my</a>.</p>
    <p>Also, I confirm that I have read and taken note of my duty of disclosure as laid down in the notice on Duty of Disclosure.</p>',
    CURRENT_TIMESTAMP
);
