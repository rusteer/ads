package com.ads.repository.framework;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.PagingAndSortingRepository;

public interface MyJpaRepository<T> extends PagingAndSortingRepository<T, Long>, JpaSpecificationExecutor<T> {}
