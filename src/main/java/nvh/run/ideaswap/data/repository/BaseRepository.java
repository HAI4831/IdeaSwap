package com.run.basemodule.data.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
//@NoRepositoryBean// đánh dấu lớp base cho repo inherit
public interface BaseRepository<T> extends JpaRepository<T, Long> {
}

