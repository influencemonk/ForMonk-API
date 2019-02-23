package com.ForMonk2.interfaces;



import org.springframework.data.mongodb.repository.MongoRepository;

import com.ForMonk2.model.FollowerTrendMasterModel;

public interface IFTRepository extends MongoRepository<FollowerTrendMasterModel , String> {
	FollowerTrendMasterModel findByimcId(String imcId);
}
