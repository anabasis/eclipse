package com.lds.frmwk.rest.vo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CoffeeRepository extends JpaRepository<Coffee, String> {
    
	Coffee findByCid(String cid);

}
