SELECT * FROM transaction; 
SELECT * FROM transaction_book_ids;
DROP TABLE transactions;
DROP TABLE transaction_book_ids;
DROP TABLE transaction;

SELECT * FROM transaction_book_ids;

DELETE FROM transaction_book_ids WHERE transaction_id = '5b1f8d87-fc4b-4898-88c6-8ee997e41f1c';
DELETE FROM transaction WHERE id = '5b1f8d87-fc4b-4898-88c6-8ee997e41f1c';

select t.book_id, te.issue_date, te.return_date from transaction_book_ids t 
join transaction te on t.transaction_id = te.id  
where te.borrower_id = 'BOR123' 
and te.status = true;

INSERT INTO transaction (
  id, borrower_id, issue_date, due_date, return_date, number_of_due_date, status
) VALUES (
  'TXN001',
  'BOR123',
  '2025-09-01',        -- issueDate
  '2025-09-15',        -- dueDate
  '2025-09-10',        -- returnDate
  14,                  -- numberOfDueDate
  true                 -- status
);

INSERT INTO transaction_book_ids (transaction_id, book_id) VALUES
  ('TXN001', '9780134685991'),
  ('TXN001', '9780596009205'),
  ('TXN001', '9781449330729');

CREATE TABLE transaction (
  id VARCHAR(255) PRIMARY KEY,
  borrower_id VARCHAR(255) NOT NULL,
  issue_date DATE,
  due_date DATE,
  return_date DATE,
  number_of_due_date BIGINT,
  status BOOLEAN
);

CREATE TABLE transaction_book_ids (
  transaction_id VARCHAR(255) NOT NULL,
  book_id VARCHAR(255) NOT NULL,
  PRIMARY KEY (transaction_id, book_id),
  FOREIGN KEY (transaction_id) REFERENCES transaction(id) ON DELETE CASCADE
);

