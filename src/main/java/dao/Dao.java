package dao;

import java.util.List;

import entity.AbstractEntity;

public interface Dao<T extends AbstractEntity, I> {

	List<T> findAll();

	T find(I id);

	T save(T newEntry);

	void delete(I id);

}
