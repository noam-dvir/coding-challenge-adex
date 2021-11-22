package com.adex.codingchallenge.repository;

import com.adex.codingchallenge.model.IpBlacklist;
import org.springframework.data.jpa.repository.JpaRepository;

public interface IpBlacklistRepository extends JpaRepository<IpBlacklist, String>{
    
}
