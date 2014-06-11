package com.coship.game_platform.dao;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
/**
 * 实体与表名的对应
 * @author Administrator
 *
 */
public @interface TableName {
	String value();
}
