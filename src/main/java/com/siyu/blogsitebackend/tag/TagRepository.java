package com.siyu.blogsitebackend.tag;


import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;


public interface TagRepository extends JpaRepository<Tag,Long>{
    List<Tag> findAllByArticles_id(Long id);
}
