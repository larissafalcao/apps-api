package com.rogalabs.rogalabsapi.repository;

import com.rogalabs.rogalabsapi.domain.AppEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AppRepository extends JpaRepository<AppEntity, Long> {
    List<AppEntity> findByType(String type);
    List<AppEntity> findByNameContaining(String name);

}
