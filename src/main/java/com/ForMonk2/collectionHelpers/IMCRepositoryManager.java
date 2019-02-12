package com.ForMonk2.collectionHelpers;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.aggregation.Aggregation;
import org.springframework.data.mongodb.core.aggregation.MatchOperation;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.stereotype.Component;

import com.ForMonk2.interfaces.IMCRepository;
import com.ForMonk2.model.IMCModel;
import com.ForMonk2.model.IMCSocialAccount;
import com.ForMonk2.utils.CollectionUtils;


@Component
public class IMCRepositoryManager implements IMCRepository{
	
	@Autowired
	private MongoTemplate mongoTemplate;
	
	
	@Override
	public <S extends IMCModel> List<S> saveAll(Iterable<S> entities) {
		// TODO Auto-generated method stub
		
		return null;
	}

	@Override
	public List<IMCModel> findAll() {
		// TODO Auto-generated method stub
		return mongoTemplate.findAll(IMCModel.class);
	}

	@Override
	public List<IMCModel> findAll(Sort sort) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public <S extends IMCModel> S insert(S entity) {
		// TODO Auto-generated method stub
		 mongoTemplate.insert(entity);
		 
		 return entity;
	}

	@Override
	public <S extends IMCModel> List<S> insert(Iterable<S> entities) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public <S extends IMCModel> List<S> findAll(Example<S> example) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public <S extends IMCModel> List<S> findAll(Example<S> example, Sort sort) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Page<IMCModel> findAll(Pageable pageable) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public <S extends IMCModel> S save(S entity) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Optional<IMCModel> findById(String id) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean existsById(String id) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public Iterable<IMCModel> findAllById(Iterable<String> ids) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public long count() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public void deleteById(String id) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void delete(IMCModel entity) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void deleteAll(Iterable<? extends IMCModel> entities) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void deleteAll() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public <S extends IMCModel> Optional<S> findOne(Example<S> example) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public <S extends IMCModel> Page<S> findAll(Example<S> example, Pageable pageable) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public <S extends IMCModel> long count(Example<S> example) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public <S extends IMCModel> boolean exists(Example<S> example) {
		// TODO Auto-generated method stub
		return false;
	}
	
	public IMCModel insert(String socialHandle , String clientId) {
		
		IMCModel imcModel = new IMCModel();
		IMCSocialAccount imSocialAccount = new IMCSocialAccount();
		imSocialAccount.setClientId(clientId);
		imSocialAccount.setSocialHandle(socialHandle);
		List<IMCSocialAccount> accounts = new ArrayList<>();
		accounts.add(imSocialAccount);
		imcModel.setSocialAccounts(accounts);
		
		return insert(imcModel);
	}

	@Override
	public IMCModel findBysocialAccounts(String socialHandle , String clientId) {
		// TODO Auto-generated method stub
		
		
		MatchOperation matchStage = Aggregation.match(new Criteria("socialAccounts.socialHandle").is(socialHandle));
		MatchOperation clientIdMath = Aggregation.match(Criteria.where("socialAccounts.clientId").is(clientId));
		//LimitOperation limitOperation = Aggregation.limit(1);
		
		Aggregation aggregation 
		  = Aggregation.newAggregation(matchStage,clientIdMath);
		
		return mongoTemplate.aggregate(
				aggregation, 
				CollectionUtils.DBCollections.InfluencerMasterCollection.name(), 
				IMCModel.class)
				.getUniqueMappedResult();
	}

}
