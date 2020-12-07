package com.lds.frmwk.graphql2.core;

import graphql.ExecutionResult;

public interface MyQuery {

    ExecutionResult execute(String query);

}
