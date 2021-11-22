package com.adex.codingchallenge.repository;

import com.adex.codingchallenge.model.UaBlacklist;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UaBlacklistRepository extends JpaRepository<UaBlacklist, String>{
    
}
