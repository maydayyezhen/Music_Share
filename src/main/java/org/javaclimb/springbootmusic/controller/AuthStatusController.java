package org.javaclimb.springbootmusic.controller;

import org.apache.tomcat.util.net.openssl.ciphers.Authentication;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping("/status")
public class AuthStatusController {

    @GetMapping("/login-success")
    public ResponseEntity<String> getDashboardData() {
        return ResponseEntity.ok("登录成功！");
    }

    @GetMapping("/logout-success")
    public ResponseEntity<String> logout() {
        return ResponseEntity.ok("注销成功！");
    }
}


