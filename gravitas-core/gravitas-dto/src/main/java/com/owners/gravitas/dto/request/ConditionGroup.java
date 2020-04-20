package com.owners.gravitas.dto.request;

import java.util.List;

/**
 * To support where condtion in mysql query
 * in record view frame work
 * @author sandeepsoni
 *
 */
public class ConditionGroup {
	
	private String operator;
	
	private List<WhereClause> conditions;

	/**
	 * @return the operator
	 */
	public String getOperator() {
		return operator;
	}

	/**
	 * @param operator the operator to set
	 */
	public void setOperator(String operator) {
		this.operator = operator;
	}

	/**
	 * @return the conditons
	 */
	public List<WhereClause> getConditions() {
		return conditions;
	}

	/**
	 * @param conditons the conditons to set
	 */
	public void setConditions(List<WhereClause> conditons) {
		this.conditions = conditons;
	}
	
	

}
