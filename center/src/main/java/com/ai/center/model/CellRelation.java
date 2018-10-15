package com.ai.center.model;

public class CellRelation {
	private long id;
	private int cellId;
	private long relatedId;
	private double weight;
	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
	
	
	public double getWeight() {
		return weight;
	}
	public void setWeight(double weight) {
		this.weight = weight;
	}
	public long getRelatedId() {
		return relatedId;
	}
	public void setRelatedId(long relatedId) {
		this.relatedId = relatedId;
	}
	public int getCellId() {
		return cellId;
	}
	public void setCellId(int cellId) {
		this.cellId = cellId;
	}
	
	
}
