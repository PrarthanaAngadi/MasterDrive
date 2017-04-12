package com.masterdrive;

import java.util.List;

public interface MasterDriveDao<T> {

	/**
	 * Persist a new Object
	 * 
	 * @param object
	 * @throws MasterDriveException
	 */
	public void create(T object) throws MasterDriveException;
	
	/**
	 * Delete an existing object
	 * 
	 * @param object
	 * @throws MasterDriveException
	 */
	public void delete(T object) throws MasterDriveException;
	
	/**
	 * Retrieve all objects from the database
	 * 
	 * @return List of objects
	 * @throws MasterDriveException
	 */
	public List<T> getAll() throws MasterDriveException;
	
	/**
	 * Get a specific object from the database
	 * 
	 * @param id
	 * @return the object
	 * @throws MasterDriveException
	 */
	public T get(long id) throws MasterDriveException;
	
	/**
	 * Update the persisted object
	 * 
	 * @param object
	 * @throws MasterDriveException
	 */
	public void update(T object) throws MasterDriveException;
	
}
