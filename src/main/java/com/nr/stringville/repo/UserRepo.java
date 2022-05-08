package com.nr.stringville.repo;

import com.nr.stringville.dto.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepo extends JpaRepository<User, String> {
}
