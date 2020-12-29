package com.lds.frmwk.graphql;


import com.coxautodev.graphql.tools.GraphQLMutationResolver;
import lombok.RequiredArgsConstructor;
import com.lds.frmwk.graphql.domain.Post;
import com.lds.frmwk.graphql.dto.PostResponse;
import com.lds.frmwk.graphql.repository.AuthorRepository;
import com.lds.frmwk.graphql.repository.PostRepository;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class MyMutation implements GraphQLMutationResolver {
    
	private final PostRepository postRepository;
    private final AuthorRepository authorRepository;

    public PostResponse writePost(String title, String text, String category) {
        Post post = new Post();
        post.setTitle(title);
        post.setText(text);
        post.setCategory(category);
        post.setAuthor(authorRepository.getOne(1L));

        final Post save = postRepository.save(post);

        return PostResponse.from(save);
    }
}