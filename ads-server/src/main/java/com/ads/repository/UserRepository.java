package com.ads.repository;
import com.ads.entity.UserEntity;
import com.ads.repository.framework.MyJpaRepository;

public interface UserRepository extends MyJpaRepository<UserEntity> {
    UserEntity findByLoginName(String loginName);
}
