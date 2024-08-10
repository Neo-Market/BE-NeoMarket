package com.neo.neomarket.repository.mysql;

import com.neo.neomarket.entity.mysql.ProductEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductRepository extends JpaRepository<ProductEntity, Long> {
}
