package com.lds.frmwk.graphql;


import com.coxautodev.graphql.tools.GraphQLQueryResolver;
import lombok.RequiredArgsConstructor;
import com.lds.frmwk.graphql.domain.Post;
import com.lds.frmwk.graphql.dto.PostResponse;
import com.lds.frmwk.graphql.repository.PostRepository;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
public class MyQuery implements GraphQLQueryResolver {
    
	private final PostRepository postRepository;

    public List<PostResponse> getRecentPosts(int count, int offset) {
        final List<Post> all = postRepository.findAll();
        return PostResponse.from(all);
    }
}