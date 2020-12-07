package com.lds.frmwk.graphql;

import org.springframework.stereotype.Component;

import com.coxautodev.graphql.tools.GraphQLMutationResolver;

import lombok.RequiredArgsConstructor;

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