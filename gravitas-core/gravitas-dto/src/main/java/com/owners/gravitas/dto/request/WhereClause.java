package com.owners.gravitas.dto.request;


/**
 * To support where condtion in mysql query
 * in record view frame work
 * @author sandeepsoni
 *
 */
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
@JsonIgnoreProperties(ignoreUnknown = true)
public class WhereClause {
	
	private String columnName;
	
	private String operator;
	
	private String conditionValue;
	
	private String nextConditionOperator;
	
	private String conditionValueTemp;
	

	/**
	 * @return the conditionValueTemp
	 */
	public String getConditionValueTemp() {
		return conditionValueTemp;
	}

	/**
	 * @param conditionValueTemp the conditionValueTemp to set
	 */
	public void setConditionValueTemp(String conditionValueTemp) {
		this.conditionValueTemp = conditionValueTemp;
	}
	

	/**
	 * @return the nextConditionOperator
	 */
	public String getNextConditionOperator() {
		return nextConditionOperator;
	}

	/**
	 * @param nextConditionOperator the nextConditionOperator to set
	 */
	public void setNextConditionOperator(String nextConditionOperator) {
		this.nextConditionOperator = nextConditionOperator;
	}

	/**
	 * @return the columnName
	 */
	public String getColumnName() {
		return columnName;
	}

	/**
	 * @param columnName the columnName to set
	 */
	public void setColumnName(String columnName) {
		this.columnName = columnName;
	}

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
	 * @return the conditionValue
	 */
	public String getConditionValue() {
		return conditionValue;
	}

	/**
	 * @param conditionValue the conditionValue to set
	 */
	public void setConditionValue(String conditionValue) {
		this.conditionValue = conditionValue;
	}

	
}
