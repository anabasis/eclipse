package com.lds.frmwk.graphql;


import com.coxautodev.graphql.tools.GraphQLResolver;
import lombok.RequiredArgsConstructor;
import com.lds.frmwk.graphql.domain.Author;
import com.lds.frmwk.graphql.dto.PostResponse;
import com.lds.frmwk.graphql.repository.AuthorRepository;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class PostResolver implements GraphQLResolver<PostResponse> {
    
	private final AuthorRepository authorRepository;

    public Author getAuthor(PostResponse postResponse) {
        return authorRepository.findById(postResponse.getAuthor().getId()).orElseThrow(NullPointerException::new);
    }
}