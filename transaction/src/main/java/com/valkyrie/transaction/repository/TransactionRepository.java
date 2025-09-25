package com.valkyrie.transaction.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.valkyrie.transaction.model.Transaction;

import java.util.Date;
import java.util.List;

@Repository
public interface TransactionRepository extends JpaRepository<Transaction, String> {

    @Modifying
    @Transactional
    @Query("update Transaction t set t.dueDate = :dueDate where t.id = :id")
    void updateDueDate(@Param("dueDate") Date dueDate, @Param("id") String id);

    @Modifying
    @Transactional
    @Query("UPDATE Transaction t SET t.status = :status WHERE t.id = :id")
    void updateStatus(@Param("status") boolean status, @Param("id") String id);

    @Query(value = "select t.book_ids from transaction_book_ids t " +
        " join transaction te on t.transaction_id = te.id " + 
        " where te.memberId = :memberId and te.status = :status", nativeQuery = true)
    List<String> getBooksIds(@Param("memberId") String memberId, @Param("status") boolean status);

    List<Transaction> findAllByBorrowerId(String borrowerId);

//    @Modifying
//    @Transactional
//    void updateReturnDate
}
