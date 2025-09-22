package com.valkyrie.entity.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.valkyrie.entity.model.Users;

@Repository
public interface EntityRepository extends JpaRepository<Users, String> {

}
