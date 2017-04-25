package com.masterdrive.storage;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;

import org.springframework.stereotype.Repository;

import com.masterdrive.MasterDriveException;

@Repository
@Transactional
public class DropboxDaoImpl implements DropboxDao {

	@PersistenceContext
	private EntityManager entityManager;
	
	@Override
	public void create(Dropbox object) throws MasterDriveException {
		// TODO Auto-generated method stub
		entityManager.persist(object);
	}

	@Override
	public void delete(Dropbox object) throws MasterDriveException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public List<Dropbox> getAll() throws MasterDriveException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Dropbox get(long id) throws MasterDriveException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void update(Dropbox object) throws MasterDriveException {
		// TODO Auto-generated method stub
		
	}

}
