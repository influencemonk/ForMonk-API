package com.ForMonk2.repo;



import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import com.ForMonk2.model.FollowerTrendMasterModel;

@Repository
public interface IFTRepository extends MongoRepository<FollowerTrendMasterModel , String> {
	FollowerTrendMasterModel findByimcId(String imcId);
}
