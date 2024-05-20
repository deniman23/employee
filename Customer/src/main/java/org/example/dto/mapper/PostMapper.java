package org.example.dto.mapper;


import org.example.dao.model.Post;
import org.example.dto.PostDto;


public class PostMapper {

    public static PostDto entityToDto(Post post){
        if (post==null){
            return null;
        }
        PostDto dto = new PostDto();
        dto.setPostName(post.getPostName());
        dto.setId(post.getId());
        return dto;
    }

}
