package com.ForMonk2.interfaces;



import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import com.ForMonk2.model.IMCModel;


@Repository
public interface IMCRepository extends MongoRepository<IMCModel , String> {

	public IMCModel findBysocialAccounts(String socialHandle , String clientId);
	
}
