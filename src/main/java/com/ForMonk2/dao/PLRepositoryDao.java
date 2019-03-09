package com.ForMonk2.dao;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Repository;

import com.ForMonk2.entity.ProfileLink;
import com.ForMonk2.repo.PLRepository;

@Repository
public class PLRepositoryDao implements PLRepository {
	
	@Autowired
	private MongoTemplate mongoTemplate;

	@Override
	public <S extends ProfileLink> List<S> saveAll(Iterable<S> entities) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<ProfileLink> findAll() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<ProfileLink> findAll(Sort sort) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public <S extends ProfileLink> S insert(S entity) {
		mongoTemplate.insert(entity);
		return entity;
	}

	@Override
	public <S extends ProfileLink> List<S> insert(Iterable<S> entities) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public <S extends ProfileLink> List<S> findAll(Example<S> example) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public <S extends ProfileLink> List<S> findAll(Example<S> example, Sort sort) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Page<ProfileLink> findAll(Pageable pageable) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public <S extends ProfileLink> S save(S entity) {
		mongoTemplate.save(entity);
		return entity;
	}

	@Override
	public Optional<ProfileLink> findById(String id) {
		
		return Optional.ofNullable(mongoTemplate.findById(id, ProfileLink.class));
		
	}

	@Override
	public boolean existsById(String id) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public Iterable<ProfileLink> findAllById(Iterable<String> ids) {
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
		
		Optional<ProfileLink> searchResult = findById(id);
		
		if(searchResult.isPresent()) {
			mongoTemplate.remove(searchResult.get());
		}
		
	}

	@Override
	public void delete(ProfileLink entity) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void deleteAll(Iterable<? extends ProfileLink> entities) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void deleteAll() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public <S extends ProfileLink> Optional<S> findOne(Example<S> example) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public <S extends ProfileLink> Page<S> findAll(Example<S> example, Pageable pageable) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public <S extends ProfileLink> long count(Example<S> example) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public <S extends ProfileLink> boolean exists(Example<S> example) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public List<ProfileLink> findByImcId(String imcId) {
		
		Query query = new Query();
		query.addCriteria(Criteria.where("imcId").is(imcId));
		
		return mongoTemplate.find(query, ProfileLink.class);
		
	}
	

}
