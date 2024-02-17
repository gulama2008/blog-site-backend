package com.siyu.blogsitebackend.tag;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class TagCreateDTO {

    private Long id;
    @NotBlank
    private String name;

    public TagCreateDTO() {
    };

    public TagCreateDTO(String name) {
        this.name = name;
    }

    public TagCreateDTO(Long id, String name) {
        this.id = id;
        this.name = name;
    }
}
