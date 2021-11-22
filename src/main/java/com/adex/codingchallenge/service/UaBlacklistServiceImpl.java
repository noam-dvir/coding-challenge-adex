package com.adex.codingchallenge.service;

import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.adex.codingchallenge.model.UaBlacklist;
import com.adex.codingchallenge.repository.UaBlacklistRepository;

@Service
@Transactional
public class UaBlacklistServiceImpl implements UaBlacklistService{

    @Autowired
    private UaBlacklistRepository uaBlacklistRepository;

    @Override
    public UaBlacklist addBlacklistedUa(UaBlacklist uaBlacklist) {
        return uaBlacklistRepository.save(uaBlacklist);
    }

    @Override
    public List<UaBlacklist> getAllBlacklistedUas() {
        return this.uaBlacklistRepository.findAll();
    }

    @Override
    public boolean isUaBlacklisted(UaBlacklist uaBlacklist) {
        Optional<UaBlacklist> uaBlacklistItem = this.uaBlacklistRepository.findById(uaBlacklist.getUa());
        return uaBlacklistItem.isPresent();
    }

    @Override
    public void deleteUaFromBlacklist(UaBlacklist uaBlacklist) {
        Optional<UaBlacklist> uaBlacklistItem = this.uaBlacklistRepository.findById(uaBlacklist.getUa());
        if (uaBlacklistItem.isPresent()){
            this.uaBlacklistRepository.delete(uaBlacklistItem.get());
        }
    }    
}
