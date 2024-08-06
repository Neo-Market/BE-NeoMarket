package com.neo.neomarket.repository.elasticsearch;

import com.neo.neomarket.entity.elasticsearch.UserLogEntity;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserLogRepository extends ElasticsearchRepository<UserLogEntity, Long> {
    
}