package com.coship.game_platform.dao;

import java.io.Serializable;
import java.util.List;

/**
 * 通用的实体操作接口
 * 
 * @author Administrator
 * 
 */
public interface DAO<M> {
	/**
	 * 增加
	 * 
	 * @param m
	 * @return
	 */
	long insert(M m);

	/**
	 * 删除
	 * 
	 * @param id
	 * @return
	 */
	int delete(Serializable id);// int long String——Serializable:JPA Hibernate

	/**
	 * 更新
	 * 
	 * @param m
	 * @return
	 */
	int update(M m);

	/**
	 * 查询全部
	 * 
	 * @return
	 */
	List<M> findAll();

	/**
	 * 条件查询
	 * 
	 * @param orderBy
	 * @return
	 */
	public List<M> findByCondition(String orderBy);

	public M getInstance();
}
