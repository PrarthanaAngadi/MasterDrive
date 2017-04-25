package com.masterdrive.user;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;

import org.springframework.stereotype.Repository;

import com.masterdrive.MasterDriveException;
import com.masterdrive.util.Status.Code;

@Repository
@Transactional
public class UserDaoImpl implements UserDao {

	UserDaoImpl() {
	}

	@PersistenceContext
	private EntityManager entityManager;

	@Override
	public void create(User user) throws UserException, MasterDriveException {

		if (this.get(user.getEmail()) == null) {
			entityManager.persist(user);
		} else {
			throw new UserException(Code.USER_EXISTS, user);
		}

	}

	@Override
	public void delete(User object) throws MasterDriveException {
		// TODO Auto-generated method stub

	}

	@Override
	public List<User> getAll() throws MasterDriveException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public User get(long id) throws MasterDriveException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void update(User user) throws UserException {
			User value = entityManager.merge(user);
			if(value == null)
				throw new UserException(Code.UPDATE_FAILURE, user);	
	}

	@Override
	public User get(String email) throws MasterDriveException {

		try {
			User user = (User) entityManager.createQuery("from User where email = :email").setParameter("email", email)
					.getSingleResult();
			return user;

		} catch (NoResultException e) {
			return null;
		}

	}

	

	

}
