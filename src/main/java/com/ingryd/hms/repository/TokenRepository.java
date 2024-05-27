package com.ingryd.hms.repository;

import com.ingryd.hms.entity.Token;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TokenRepository extends JpaRepository<Token, Integer> {
    boolean findByValue(int value);
}
