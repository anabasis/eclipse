package com.lds.frmwk.graphql.repository;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.lds.frmwk.graphql.domain.Author;

@Repository
public interface AuthorRepository extends JpaRepository<Author, Long> {
}
