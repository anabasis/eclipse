package com.lds.frmwk.rest.core;

import java.util.List;

import com.lds.frmwk.rest.vo.Coffee;

public interface CoffeeDetails {

    List<Coffee> findAll();
    Coffee findByCid(String cid);

}
