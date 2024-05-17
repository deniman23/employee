package org.example.dto.mapper;


import org.example.dao.model.Post;
import org.example.dto.PostDto;

import java.util.ArrayList;
import java.util.List;

public class PostMapper {

    public static PostDto entityToDto(Post post){
        PostDto dto = new PostDto();
        dto.setPostName(post.getPostName());
        dto.setId(post.getId());
        return dto;
    }

    public static List<PostDto> entityToDto(List<Post> posts){
        List<PostDto> list = new ArrayList<>();
        for (Post post : posts) {
            PostDto postDto = entityToDto(post);
            list.add(postDto);
        }

        return list;
    }
}
