package com.adex.codingchallenge.controller;

import java.util.List;
import com.adex.codingchallenge.model.UaBlacklist;
import com.adex.codingchallenge.service.UaBlacklistService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class UaBlacklistController {

    @Autowired
    private UaBlacklistService uaBlacklistService;

    @PostMapping("/ua_blacklist")
    public ResponseEntity<UaBlacklist> addBlacklistedUa(@RequestBody UaBlacklist uaBlacklist){
        return ResponseEntity.ok().body(this.uaBlacklistService.addBlacklistedUa(uaBlacklist));
    }

    @GetMapping("/ua_blacklist")
    public ResponseEntity<List<UaBlacklist>> getAllBlacklistedUas(){
        return ResponseEntity.ok().body(uaBlacklistService.getAllBlacklistedUas());
    }

    @GetMapping("/ua_blacklist/check_ua")
    public ResponseEntity<String> isUaBlacklisted(@RequestBody UaBlacklist uaBlacklist){
        if (uaBlacklistService.isUaBlacklisted(uaBlacklist)){
            return ResponseEntity.ok().body("ua "+uaBlacklist.getUa()+" is blacklisted");
        }
        return ResponseEntity.ok().body("ua "+uaBlacklist.getUa()+" is not blacklisted");
    }

    @DeleteMapping("/ua_blacklist")
    public HttpStatus deleteUaFromBlacklist(@RequestBody UaBlacklist uaBlacklist){
        this.uaBlacklistService.deleteUaFromBlacklist(uaBlacklist);
        return HttpStatus.OK;
    }


}
