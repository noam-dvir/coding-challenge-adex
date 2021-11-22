package com.adex.codingchallenge.controller;

import java.util.List;
import com.adex.codingchallenge.model.IpBlacklist;
import com.adex.codingchallenge.service.IpBlacklistService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class IpBlacklistController {

    @Autowired
    private IpBlacklistService ipBlacklistService;

    @PostMapping("/ip_blacklist")
    public ResponseEntity<IpBlacklist> addBlacklistedIp(@RequestBody IpBlacklist ipBlacklist){
        return ResponseEntity.ok().body(this.ipBlacklistService.addBlacklistedIp(ipBlacklist));
    }

    @GetMapping("/ip_blacklist")
    public ResponseEntity<List<IpBlacklist>> getAllBlacklistedIps(){
        return ResponseEntity.ok().body(ipBlacklistService.getAllBlacklistedIps());
    }

    @GetMapping("/ip_blacklist/check_ip")
    public ResponseEntity<String> isIpBlacklisted(@RequestBody IpBlacklist ipBlacklist){
        if (ipBlacklistService.isIpBlacklisted(ipBlacklist)){
            return ResponseEntity.ok().body("ip "+ipBlacklist.getIp()+" is blacklisted");
        }
        return ResponseEntity.ok().body("ip "+ipBlacklist.getIp()+" is not blacklisted");
    }

    @DeleteMapping("/ip_blacklist")
    public HttpStatus deleteIpFromBlacklist(@RequestBody IpBlacklist ipBlacklist){
        this.ipBlacklistService.deleteIpFromBlacklist(ipBlacklist);
        return HttpStatus.OK;
    }


}
