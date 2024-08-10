package com.neo.neomarket.repository.mysql;

import com.neo.neomarket.entity.mysql.PictureEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PictureRepository extends JpaRepository<PictureEntity, Long> {
}
