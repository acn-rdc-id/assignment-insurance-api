-- Set active database
USE mysqldb;

-- Prepopulate Claim Type table
INSERT IGNORE INTO claim_type(claim_type_id, claim_type_description, claim_type_name)
VALUES
    (1, 'Claim due to diagnosis of critical illness', 'Critical Illness Claim'),
    (2, 'Total Permanent Disability due to accident or illness', 'TPD Claim'),
    (3, 'Reimbursement of hospital expenses', 'Hospitalization Claim'),
    (4, 'Payout after policy term completion', 'Maturity Claim');

-- Prepopulate Document Type table
INSERT IGNORE INTO document_type(document_type_is_required,claim_type_id,document_type_id,document_type_name)
VALUES
    ('1',1,1,'Diagnosis Report'),
    ('1',1,2,'Medical Reports'),
    ('1',1,3,'ID Proof'),
    ('1',1,4,'Policy Document'),
    ('1',2,5,'Disability Certificate'),
    ('1',2,6,'Medical Reports'),
    ('1',2,7,'ID Proof'),
    ('1',2,8,'Policy Document'),
    ('1',3,9,'Hospital Bills'),
    ('1',3,10,'Discharge Summary'),
    ('1',3,11,'ID Proof'),
    ('1',3,12,'Policy Document'),
    ('1',4,13,'Policy Document'),
    ('1',4,14,'ID Proof'),
    ('1',4,15,'Bank Account Proof');

