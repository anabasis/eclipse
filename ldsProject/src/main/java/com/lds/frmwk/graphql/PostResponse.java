package com.lds.frmwk.graphql;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

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