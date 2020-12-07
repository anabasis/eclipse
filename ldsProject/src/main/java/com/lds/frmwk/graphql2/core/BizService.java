package com.lds.frmwk.graphql2.core;

import org.springframework.stereotype.Service;
import graphql.ExecutionResult;

@Service
public class BizService {

    private final MyQuery myquery;

    public BizService(MyQuery myquery) {
        this.myquery = myquery;
    }

    public ExecutionResult execute(String query){
        return myquery.execute(query);
    }
}