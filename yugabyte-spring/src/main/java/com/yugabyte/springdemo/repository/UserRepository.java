package com.yugabyte.springdemo.repository;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import com.yugabyte.springdemo.model.User;

public interface UserRepository extends JpaRepository<User, UUID> {

}
