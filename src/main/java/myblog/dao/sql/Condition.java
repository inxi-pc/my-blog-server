package myblog.dao.sql;

import java.util.List;
import java.util.Map;

public class Condition {

	private List<Map<String, String>> leftJoinList;

	private List<Map<String, String>> rightJoinList;

	private List<Map<String, String>> innerJoinList;

	public Condition(List<Map<String, String>> leftJoinList,
					 List<Map<String, String>> rightJoinList,
					 List<Map<String, String>> innerJoinList) {
		this.leftJoinList = leftJoinList;
		this.rightJoinList = rightJoinList;
		this.innerJoinList = innerJoinList;
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
}
