package com.ForMonk2.repo;

import java.util.List;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import com.ForMonk2.entity.ProfileLink;

@Repository
public interface PLRepository extends MongoRepository<ProfileLink, String> {
	
	List<ProfileLink> findByImcId(String imcId);
	
}
