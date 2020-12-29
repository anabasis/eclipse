package com.lds.frmwk.graphql;

import java.util.List;

import org.springframework.stereotype.Component;

import com.coxautodev.graphql.tools.GraphQLQueryResolver;
import com.lds.frmwk.graphql.domain.Post;
import com.lds.frmwk.graphql.repository.PostRepository;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class MyQuery implements GraphQLQueryResolver {
    
	private final PostRepository postRepository = null;

    public List<PostResponse> getRecentPosts(int count, int offset) {
        final List<Post> all = postRepository.findAll();
        return PostResponse.from(all);
    }
}