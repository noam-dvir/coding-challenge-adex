package com.adex.codingchallenge.service;

import java.util.List;
import com.adex.codingchallenge.model.UaBlacklist;

public interface UaBlacklistService 
{
    UaBlacklist addBlacklistedUa(UaBlacklist uaBlacklist); 

    List<UaBlacklist> getAllBlacklistedUas(); 

    boolean isUaBlacklisted(UaBlacklist uaBlacklist);
    
    void deleteUaFromBlacklist(UaBlacklist uaBlacklist); 
}
