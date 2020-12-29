package com.lds.frmwk.graphql2.web;

import graphql.ExecutionResult;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.lds.frmwk.graphql2.core.BizService;

@RestController
@RequestMapping("/bizservice")
public class BizController {

    private final BizService bizservice;

    // 서비스 초기화
    public BizController(BizService bizservice) {
        this.bizservice = bizservice;
    }

    @PostMapping
    public ResponseEntity<Object> getMyQuery(@RequestBody String query) {
        ExecutionResult execute = bizservice.execute(query);

        return new ResponseEntity<>(execute, HttpStatus.OK);
    }
}
