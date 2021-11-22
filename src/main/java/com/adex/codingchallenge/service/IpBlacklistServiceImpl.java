package com.adex.codingchallenge.service;

import java.util.List;
import java.util.Optional;
import com.adex.codingchallenge.repository.IpBlacklistRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.adex.codingchallenge.model.IpBlacklist;

@Service
@Transactional
public class IpBlacklistServiceImpl implements IpBlacklistService{

    @Autowired
    private IpBlacklistRepository ipBlacklistRepository;

    @Override
    public IpBlacklist addBlacklistedIp(IpBlacklist ipBlacklist) {
        return ipBlacklistRepository.save(ipBlacklist);
    }

    @Override
    public List<IpBlacklist> getAllBlacklistedIps() {
        return this.ipBlacklistRepository.findAll();
    }

    @Override
    public boolean isIpBlacklisted(IpBlacklist ipBlacklist) {
        Optional<IpBlacklist> ipBlacklistItem = this.ipBlacklistRepository.findById(ipBlacklist.getIp());
        return ipBlacklistItem.isPresent();
    }

    @Override
    public void deleteIpFromBlacklist(IpBlacklist ipBlacklist) {
        Optional<IpBlacklist> ipBlacklistItem = this.ipBlacklistRepository.findById(ipBlacklist.getIp());
        if (ipBlacklistItem.isPresent()){
            this.ipBlacklistRepository.delete(ipBlacklistItem.get());
        }
    }
    
}
