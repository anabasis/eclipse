package com.lds.frmwk.graphql;

import com.coxautodev.graphql.tools.GraphQLResolver;
import com.lds.frmwk.graphql.domain.Author;
import com.lds.frmwk.graphql.PostResponse;
import com.lds.frmwk.graphql.repository.AuthorRepository;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class PostResolver implements GraphQLResolver<PostResponse> {
    
	private AuthorRepository authorRepository;

    public Author getAuthor(PostResponse postResponse) {
        return authorRepository.findById(postResponse.getAuthor().getId()).orElseThrow(NullPointerException::new);
    }
}
