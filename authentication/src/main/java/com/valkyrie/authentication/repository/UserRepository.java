package com.valkyrie.authentication.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.valkyrie.authentication.model.User;

@Repository
public interface UserRepository extends JpaRepository<User, String> {

}
