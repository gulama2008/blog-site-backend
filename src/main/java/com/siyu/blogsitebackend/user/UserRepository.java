package com.siyu.blogsitebackend.user;

import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User,Long>{
    User findByComments_id(Long id); 
}
