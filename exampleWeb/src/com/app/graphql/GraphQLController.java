package com.app.graphql;


import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import graphql.ExecutionResult;

@RestController
@RequestMapping("/graphcoffees")
public class GraphQLController {

    private final CoffeeUseCase coffeeUseCase;

    public GraphQLController(CoffeeUseCase coffeeUseCase) {
        this.coffeeUseCase = coffeeUseCase;
    }

    @PostMapping
    public ResponseEntity<Object> getCoffeeByQuery(@RequestBody String query){
    	ExecutionResult execute = coffeeUseCase.execute(query);
    	return new ResponseEntity<>(execute, HttpStatus.OK);
    }
    
    
}
