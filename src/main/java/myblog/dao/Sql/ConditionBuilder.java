package myblog.dao.Sql;

import myblog.domain.Domain;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ConditionBuilder {

	private List<Class<? extends Domain>> leftJoins;

	private List<Class<? extends Domain>> rightJoins;

	private List<Class<? extends Domain>> innerJoins;

	public ConditionBuilder() {
		this.leftJoins = new ArrayList<Class<? extends Domain>>();
		this.rightJoins = new ArrayList<Class<? extends Domain>>();
		this.innerJoins = new ArrayList<Class<? extends Domain>>();
	}

	public ConditionBuilder addLeftJoinCondition(Class<? extends Domain> clazz) {
		this.leftJoins.add(clazz);

		return this;
	}

	public ConditionBuilder addRightJoinCondition(Class<? extends Domain> clazz) {
		this.rightJoins.add(clazz);

		return this;
	}

	public ConditionBuilder addInnerJoinCondition(Class<? extends Domain> clazz) {
		this.innerJoins.add(clazz);

		return this;
	}

	public Condition build() {
		List<Map<String, String>> leftJoins = new ArrayList<Map<String, String>>();
		List<Map<String, String>> rightJoins = new ArrayList<Map<String, String>>();
		List<Map<String, String>> innerJoins = new ArrayList<Map<String, String>>();

		this.leftJoins.forEach(clazz -> {
			Map<String, String> join = new HashMap<String, String>();
			join.put("table", Domain.getTableName(clazz));
			join.put("field", Domain.getPrimaryKeyField(clazz).getName());
			leftJoins.add(join);
		});

		this.rightJoins.forEach(clazz -> {
			Map<String, String> join = new HashMap<String, String>();
			join.put("table", Domain.getTableName(clazz));
			join.put("field", Domain.getPrimaryKeyField(clazz).getName());
			rightJoins.add(join);
		});

		this.innerJoins.forEach(clazz -> {
			Map<String, String> join = new HashMap<String, String>();
			join.put("table", Domain.getTableName(clazz));
			join.put("field", Domain.getPrimaryKeyField(clazz).getName());
			innerJoins.add(join);
		});

		return new Condition(leftJoins, rightJoins, innerJoins);
	}
}
