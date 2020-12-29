package com.lds.frmwk.graphql2.provider;

import graphql.schema.DataFetcher;
import graphql.schema.DataFetchingEnvironment;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Component;

import com.lds.frmwk.graphql2.core.Coffee;
import com.lds.frmwk.graphql2.core.CoffeeRepository;

import java.util.List;

@Component
public class AllCoffeesOrderByDataFetcher implements DataFetcher<List<Coffee>> {

    private final CoffeeRepository coffeeRepository;

    public AllCoffeesOrderByDataFetcher(CoffeeRepository coffeeRepository) {
        this.coffeeRepository = coffeeRepository;
    }

    @Override
    public List<Coffee> get(DataFetchingEnvironment environment) {
        //TODO:리팩토링 필요
        //return coffeeRepository.findAll(new Sort(Sort.Direction.fromString(environment.getArgument("sortOrder")), ((String)environment.getArgument("sortBy"))));
        return coffeeRepository.findAll();
    }
}