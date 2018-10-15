package com.ai.center.model;

public class WordRelation {
	private long id;
	private long formerRelatedId;
	private double weight;
	private long backRelatedId;
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
	public long getFormerRelatedId() {
		return formerRelatedId;
	}
	public void setFormerRelatedId(long formerRelatedId) {
		this.formerRelatedId = formerRelatedId;
	}
	public long getBackRelatedId() {
		return backRelatedId;
	}
	public void setBackRelatedId(long backRelatedId) {
		this.backRelatedId = backRelatedId;
	}
	
}
