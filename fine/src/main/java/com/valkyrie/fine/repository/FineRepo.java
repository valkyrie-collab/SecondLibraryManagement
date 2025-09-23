package com.valkyrie.fine.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.valkyrie.fine.model.Fine;

@Repository
public interface FineRepo extends JpaRepository<Fine,String> {
    @Modifying
    @Transactional
    @Query("DELETE FROM Fine s WHERE s.paidStatus = true")
    void deletefine();

    @Modifying
    @Transactional
    @Query("UPDATE Fine s SET s.paidStatus = true WHERE s.id = :id")
    void updatestatus(@Param("id") String id);

}
