package com.lds.frmwk.graphql.dto;

import lombok.*;
import com.lds.frmwk.graphql.domain.Author;
import com.lds.frmwk.graphql.domain.Post;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PostResponse {
    
	private long id;
    private String title;
    private String text;
    private String category;
    private Author author;

    public static List<PostResponse> from(Collection<Post> entities) {
        return entities.stream().map(PostResponse::from).collect(Collectors.toList());
    }

    public static PostResponse from(Post entity) {
        return PostResponse.builder()
                .id(entity.getId())
                .title(entity.getTitle())
                .text(entity.getText())
                .category(entity.getCategory())
                .author(entity.getAuthor())
                .build();
    }
}