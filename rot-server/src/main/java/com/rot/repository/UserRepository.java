package com.rot.repository;
import com.rot.entity.UserEntity;
import com.rot.repository.framework.MyJpaRepository;

public interface UserRepository extends MyJpaRepository<UserEntity> {
    UserEntity findByLoginName(String loginName);
}
