package com.isnet.mgr.domain;

public class Rule {

	private String column;
	private String operator;
	private String op;
	private Object value;

	public Rule(String column, String operator, String op, Object value){
		this.column = column;
		this.operator = operator;
		this.op = op;
		this.value = value;
	}

	public String getColumn() {
		return column;
	}
	public void setColumn(String column) {
		this.column = column;
	}
	public String getOperator() {
		return operator;
	}
	public void setOperator(String operator) {
		this.operator = operator;
	}
	public String getOp() {
		return op;
	}
	public void setOp(String op) {
		this.op = op;
	}
	public Object getValue() {
		return value;
	}
	public void setValue(Object value) {
		this.value = value;
	}
	@Override
	public String toString() {
		return "Rule [column=" + column + ", operator=" + operator
				+ ", value=" + value + "]";
	}      
	
	
}

