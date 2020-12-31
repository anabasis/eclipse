package com.lds.frmwk.rest.web;


import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.lds.frmwk.rest.core.CoffeeUseCase;
import com.lds.frmwk.rest.vo.Coffee;

@Controller
@RequestMapping("/restcoffees")
public class RestController {

    private final CoffeeUseCase coffeeUseCase;

    public RestController(CoffeeUseCase coffeeUseCase) {
    	System.out.println("START REST CONTROLLER");
        this.coffeeUseCase = coffeeUseCase;
    }

    @GetMapping
    public List<Coffee> getList(){
    	return coffeeUseCase.findAll();
    }
    
    
    @GetMapping
    public Coffee findByCid() {
    	return coffeeUseCase.findByCid("");
    }
}
