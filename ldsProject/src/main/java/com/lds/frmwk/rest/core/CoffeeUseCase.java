package com.lds.frmwk.rest.core;

import java.util.List;

import org.springframework.stereotype.Service;

import com.lds.frmwk.rest.vo.Coffee;

@Service
public class CoffeeUseCase {

    private final CoffeeDetails coffeeDetails;

    public CoffeeUseCase(CoffeeDetails coffeeDetails) {
        this.coffeeDetails = coffeeDetails;
    }

    public List<Coffee> findAll(){
    	return coffeeDetails.findAll();
    }
    
    public Coffee findByCid(String cid) {
    	return coffeeDetails.findByCid(cid);
    }
}