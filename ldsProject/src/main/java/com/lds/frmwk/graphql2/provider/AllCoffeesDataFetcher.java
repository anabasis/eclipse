package com.lds.frmwk.graphql2.provider;

import graphql.schema.DataFetcher;
import graphql.schema.DataFetchingEnvironment;
import org.springframework.stereotype.Component;

import com.lds.frmwk.graphql2.core.Coffee;
import com.lds.frmwk.graphql2.core.CoffeeRepository;

import java.util.List;

@Component
public class AllCoffeesDataFetcher implements DataFetcher<List<Coffee>> {

    private final CoffeeRepository coffeeRepository;

    public AllCoffeesDataFetcher(CoffeeRepository coffeeRepository) {
        this.coffeeRepository = coffeeRepository;
    }

    @Override
    public List<Coffee> get(DataFetchingEnvironment environment) {
        return coffeeRepository.findAll();
    }
}