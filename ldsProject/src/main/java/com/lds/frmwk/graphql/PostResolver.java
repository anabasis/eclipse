package com.lds.frmwk.graphql;

import org.springframework.stereotype.Component;

import com.coxautodev.graphql.tools.GraphQLResolver;

@Component
@RequiredArgsConstructor
public class PostResolver implements GraphQLResolver<PostResponse> {
    private final AuthorRepository authorRepository;

    public Author getAuthor(PostResponse postResponse) {
        return authorRepository.findById(postResponse
				.getAuthor().getId())
				.orElseThrow(NullPointerException::new);
    }
}
