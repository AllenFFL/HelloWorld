package com.coship.game_platform.dao;

import java.io.Serializable;
import java.lang.reflect.Field;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;


/**
 * 实体操作的实现类
 * 
 * @author Administrator
 * 
 * @param <M>
 */
public abstract class DAOSupport<M> implements DAO<M> {

	@SuppressWarnings("unused")
	private static final String TAG = "DAOSupport";
	protected Context context;
	protected DBHelper helper;
	protected SQLiteDatabase database;

	public DAOSupport(Context context) {
		super();
		this.context = context;
		helper = new DBHelper(context);
		database = helper.getWritableDatabase();
	}

	@Override
	public long insert(M m) {
		ContentValues values = new ContentValues();
		// values.put(DBHelper.TABLE_NEWS_TITLE, news.getTitle());
		fillContentValues(m, values);

		return database.insert(getTableName(), null, values);

	}

	@Override
	public int delete(Serializable id) {
		return database.delete(getTableName(), DBHelper.TABLE_ID + "=?", new String[] { id.toString() });
	}

	@Override
	public int update(M m) {
		ContentValues values = new ContentValues();
		fillContentValues(m, values);
		return database.update(getTableName(), values, DBHelper.TABLE_ID + "=?", new String[] { getId(m) });
	}

	@Override
	public List<M> findAll() {
		List<M> result;// List<M> result;
		Cursor query = database.query(getTableName(), null, null, null, null, null, null);
		if (query != null) {
			result = new ArrayList<M>();
			while (query.moveToNext()) {
				M m = getInstance();// M m=new M();
				fillField(query, m);
				result.add(m);
			}
			query.close();// 可以开启多少个Cursor
			return result;
		}
		return null;
	}

	public List<M> findByCondition(String orderBy) {
		return findByCondition(null, null, null, null, null, orderBy);
	}

	public List<M> findByCondition(String selection, String[] selectionArgs, String orderBy) {
		return findByCondition(null, selection, selectionArgs, null, null, orderBy);
	}

	public List<M> findByCondition(String[] columns, String selection, String[] selectionArgs, String groupBy, String having,
			String orderBy) {
		List<M> result;// List<M> result;
		Cursor query = database.query(getTableName(), columns, selection, selectionArgs, groupBy, having, orderBy);
		if (query != null) {
			result = new ArrayList<M>();
			while (query.moveToNext()) {
				M m = getInstance();// M m=new M();
				fillField(query, m);
				result.add(m);
			}
			query.close();// 可以开启多少个Cursor
			return result;
		}
		return null;
	}

	// 问题一：表名的获取
	// 问题二：数据库表中列信息如何与实体的属性一一对应
	// 问题三：如何将实体中的数据设置给表
	// 问题四：如何获取的主键内容
	// 问题五：实体对应的实例创建
	/**
	 * 问题一：表名的获取
	 * 
	 * @return
	 */
	private String getTableName() {
		M m = getInstance();
		TableName annotation = m.getClass().getAnnotation(TableName.class);
		if (annotation != null) {
			return annotation.value();
		}

		return "";
	}

	/**
	 * 问题二：数据库表中列信息如何与实体的属性一一对应
	 * 
	 * @param query
	 * @param m
	 */
	private void fillField(Cursor cursor, M m) {
		// int index=query.getColumnIndex(DBHelper.TABLE_NEWS_TITLE);
		// String title=query.getString(index);
		// news.setTitle(title);

		// 此处省略n行代码

		// 获取到所有的Field信息
		Field[] fields = m.getClass().getDeclaredFields();

		for (Field item : fields) {
			item.setAccessible(true);// 暴力反射
			Column column = item.getAnnotation(Column.class);
			if (column != null) {
				// 需要将数据库中信息填充到给Field
				int index = cursor.getColumnIndex(column.value());
				String value = cursor.getString(index);

				try {
					// 如果自增的主键
					ID id = item.getAnnotation(ID.class);
					if (id != null) {
						if (id.autoIncrement()) {
							if (item.getType() == int.class) {
								item.set(m, Integer.parseInt(value));

							} else if (item.getType() == long.class) {
								item.set(m, Long.parseLong(value));
							}

							else {
								item.set(m, value);// 给Feild设置数据 在设置id的时候会出现问题
							}
						} else {
							item.set(m, value);// 给Feild设置数据 在设置id的时候会出现问题
						}
					} else {
						item.set(m, value);// 给Feild设置数据 在设置id的时候会出现问题
					}

				} catch (IllegalArgumentException e) {
					e.printStackTrace();
				} catch (IllegalAccessException e) {
					e.printStackTrace();
				}
			}
		}

	}

	/**
	 * 问题三：如何将实体中的数据设置给表
	 * 
	 * @param m
	 * @param values
	 */
	private void fillContentValues(M m, ContentValues values) {
		// values.put(DBHelper.TABLE_NEWS_TITLE, news.getTitle());

		Field[] fields = m.getClass().getDeclaredFields();

		for (Field item : fields) {
			item.setAccessible(true);
			Column column = item.getAnnotation(Column.class);
			if (column != null) {
				// 如果当前的item是主键，并且自增主键，不能设置数据到values
				ID id = item.getAnnotation(ID.class);
				if (id != null) {
					if (id.autoIncrement()) {
						// 自增的主键，不能设置数据到values

					} else {
						String key = column.value();// values.put的key信息
						try {
							String value = item.get(m).toString();

							values.put(key, value);
						} catch (IllegalArgumentException e) {
							e.printStackTrace();
						} catch (IllegalAccessException e) {
							e.printStackTrace();
						}
					}
				} else {
					String key = column.value();// values.put的key信息
					try {
						String value = item.get(m).toString();

						values.put(key, value);
					} catch (IllegalArgumentException e) {
						e.printStackTrace();
					} catch (IllegalAccessException e) {
						e.printStackTrace();
					}
				}

			}
		}

	}

	/**
	 * 问题四：如何获取的主键内容
	 * 
	 * @param m
	 * @return
	 */
	private String getId(M m) {
		Field[] fields = m.getClass().getDeclaredFields();
		for (Field item : fields) {
			item.setAccessible(true);
			ID id = item.getAnnotation(ID.class);
			if (id != null) {
				try {
					return item.get(m).toString();
				} catch (IllegalArgumentException e) {
					e.printStackTrace();
				} catch (IllegalAccessException e) {
					e.printStackTrace();
				}
			}
		}
		return "";
	}

	/**
	 * 问题五：实体对应的实例创建
	 * 
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public M getInstance() {
		// ①获取实际运行的子类
		Class<?> clazz = getClass();
		// ②获取父类——支持泛型的父类
		Type superclass = clazz.getGenericSuperclass();// 获取到支持泛型的父类，返回值包含泛型的参数信息
		// ③在父类获取到对应的参数——参数化的类型
		if (superclass instanceof ParameterizedType) {
			Type[] actualTypeArguments = ((ParameterizedType) superclass).getActualTypeArguments();
			Class<?> target = (Class<?>) actualTypeArguments[0];// News
			// ④创建实体的实例信息
			try {
				return (M) target.newInstance();
			} catch (InstantiationException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IllegalAccessException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return null;
	}

}
