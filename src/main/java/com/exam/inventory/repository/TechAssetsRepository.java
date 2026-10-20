package com.exam.inventory.repository;

import com.exam.inventory.entity.TechAssetsEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface TechAssetsRepository extends JpaRepository<TechAssetsEntity, String>, JpaSpecificationExecutor<TechAssetsEntity> {

    @Modifying
    @Query("Update TechAssetsEntity t set t.status = ?1 where t.id = ?2")
    void updateStatusById(long status, String id);

}
