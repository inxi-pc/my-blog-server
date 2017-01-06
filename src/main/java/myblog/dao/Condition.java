package myblog.dao;

import java.lang.reflect.Field;
import java.util.*;

public class Condition {

	private List<String> fieldList;

	private List<Map<String, String>> leftJoinList;

	private List<Map<String, String>> rightJoinList;

	private List<Map<String, String>> innerJoinList;

	public Condition() {
		this.fieldList = Arrays.asList("*");
		this.leftJoinList = new ArrayList<Map<String, String>>();
		this.rightJoinList = new ArrayList<Map<String, String>>();
		this.innerJoinList = new ArrayList<Map<String, String>>();
	}

	public List<String> getFieldList() {
		return fieldList;
	}

	public List<Map<String, String>> getLeftJoinList() {
		return leftJoinList;
	}

	public List<Map<String, String>> getRightJoinList() {
		return rightJoinList;
	}

	public List<Map<String, String>> getInnerJoinList() {
		return innerJoinList;
	}

	public Condition addFieldCondition(Field[] fields) {
		Arrays.stream(fields).forEach((Field field) -> {
			this.fieldList.add(field.getName());
		});

		return this;
	}

	public Condition addLeftJoinCondition(String tableName, Field field) {
		Map<String, String> join = new HashMap<String, String>();
		join.put("table", tableName);
		join.put("field", field.getName());
		this.leftJoinList.add(join);

		return this;
	}

	public Condition addRightJoinCondition(String tableName, Field field) {
		Map<String, String> join = new HashMap<String, String>();
		join.put("table", tableName);
		join.put("field", field.getName());
		this.rightJoinList.add(join);

		return this;
	}

	public Condition addInnerJoinCondition(String tableName, Field field) {
		Map<String, String> join = new HashMap<String, String>();
		join.put("table", tableName);
		join.put("field", field.getName());
		this.innerJoinList.add(join);

		return this;
	}
}
