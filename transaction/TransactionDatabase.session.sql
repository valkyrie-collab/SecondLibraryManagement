SELECT * FROM transaction; 
SELECT * FROM transaction_book_ids;
DROP TABLE transactions;
DROP TABLE transaction_book_ids;
DROP TABLE transaction;

SELECT * FROM transaction_book_ids;

DELETE FROM transaction_book_ids WHERE transaction_id = '5b1f8d87-fc4b-4898-88c6-8ee997e41f1c';
DELETE FROM transaction WHERE id = '5b1f8d87-fc4b-4898-88c6-8ee997e41f1c';

select t.book_ids from transaction_book_ids t  
join transaction te on t.transaction_id = te.id  
where te.borrower_id = 'Rajarshi' 
and te.status = false;
