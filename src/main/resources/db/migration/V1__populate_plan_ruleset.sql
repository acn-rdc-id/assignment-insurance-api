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
    (3, 1, 'premium', '>=', 31, 200000, 120.00, 'M', 'MONTHLY', 'ACTIVE');

-- Plan 200k - YEARLY
INSERT IGNORE INTO ruleset (ruleset_id, plan_id, rule_type, operator, age_limit, coverage_amount, premium_amount, gender, payment_frequency, status)
VALUES
    (101, 1, 'premium', '>=', 18, 200000, 1200.00, 'M', 'YEARLY', 'ACTIVE'),
    (102, 1, 'premium', '<=', 30, 200000, 1200.00, 'M', 'YEARLY', 'ACTIVE'),
    (103, 1, 'premium', '>=', 31, 200000, 1440.00, 'M', 'YEARLY', 'ACTIVE');

-- Plan 300k - MONTHLY
INSERT IGNORE INTO ruleset (ruleset_id, plan_id, rule_type, operator, age_limit, coverage_amount, premium_amount, gender, payment_frequency, status)
VALUES
    (13, 2, 'premium', '>=', 18, 300000, 150.00, 'M', 'MONTHLY', 'ACTIVE'),
    (14, 2, 'premium', '<=', 30, 300000, 150.00, 'M', 'MONTHLY', 'ACTIVE'),
    (15, 2, 'premium', '>=', 31, 300000, 180.00, 'M', 'MONTHLY', 'ACTIVE');

-- Plan 500k - MONTHLY
INSERT IGNORE INTO ruleset (ruleset_id, plan_id, rule_type, operator, age_limit, coverage_amount, premium_amount, gender, payment_frequency, status)
VALUES
    (25, 3, 'premium', '>=', 18, 500000, 250.00, 'M', 'MONTHLY', 'ACTIVE'),
    (26, 3, 'premium', '<=', 30, 500000, 250.00, 'M', 'MONTHLY', 'ACTIVE'),
    (27, 3, 'premium', '>=', 31, 500000, 300.00, 'M', 'MONTHLY', 'ACTIVE');

-- Prepopulate TERMS_DECLARATION table
INSERT IGNORE INTO TERMS_DECLARATION (CREATED_AT, IS_REQUIRED, STATUS, TERMS_HTML, UPDATED_AT)
VALUES (
    CURRENT_TIMESTAMP,
    FALSE,
    'Active',
    '<h2>Terms and Conditions</h2>
    <ol>
        <li>
            This product pays benefits upon:
            <ul>
                <li>(i) Death</li>
                <li>(ii) Total and Permanent Disability (TPD)</li>
            </ul>
        </li>
        <li>
            Some exclusions apply, including:
            <ul>
                <li>(i) Death caused by suicide within 1 year.</li>
                <li>(ii) TPD caused by self-inflicted injuries.</li>
                <li>(iii) Pre-existing conditions.</li>
            </ul>
        </li>
        <li>
            You have 15 days to cancel the policy for a refund.
        </li>
    </ol>
    <p>I confirm I have read the <a href="#">Privacy Notice</a>.</p>',
    CURRENT_TIMESTAMP
);