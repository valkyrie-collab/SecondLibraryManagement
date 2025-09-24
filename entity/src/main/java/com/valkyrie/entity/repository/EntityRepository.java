package com.valkyrie.entity.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.valkyrie.entity.model.Users;

@Repository
public interface EntityRepository extends JpaRepository<Users, String> {

    @Query("SELECT e FROM Users e WHERE LOWER(e.id) LIKE %:substring%")
    List<Users> findUsersByUsername(@Param("substring") String substring);

}
