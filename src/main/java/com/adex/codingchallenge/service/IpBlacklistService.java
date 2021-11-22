package com.adex.codingchallenge.service;

import java.util.List;

import com.adex.codingchallenge.model.IpBlacklist;

public interface IpBlacklistService {

    IpBlacklist addBlacklistedIp(IpBlacklist ipBlacklist); 

    List<IpBlacklist> getAllBlacklistedIps(); 

    boolean isIpBlacklisted(IpBlacklist ipBlacklist);
    
    void deleteIpFromBlacklist(IpBlacklist ipBlacklist);
}
