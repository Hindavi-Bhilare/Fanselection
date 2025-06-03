
package com.velotech.fanselection.tree;

import java.util.Set;

public class Branch implements Comparable<Branch> {

	private int id;

	private String text;

	private boolean leaf;

	private Double sequence;

	private boolean expanded;

	private Set<Object> data;

	public int getId() {

		return id;
	}

	public void setId(int id) {

		this.id = id;
	}

	public String getText() {

		return text;
	}

	public void setText(String text) {

		this.text = text;
	}

	public boolean isLeaf() {

		return leaf;
	}

	public void setLeaf(boolean leaf) {

		this.leaf = leaf;
	}

	public Double getSequence() {

		return sequence;
	}

	public void setSequence(Double sequence) {

		this.sequence = sequence;
	}

	public boolean isExpanded() {

		return expanded;
	}

	public void setExpanded(boolean expanded) {

		this.expanded = expanded;
	}

	public Set<Object> getData() {

		return data;
	}

	public void setData(Set<Object> data) {

		this.data = data;
	}

	@Override
	public int compareTo(Branch o) {

		if (this.id > o.id)
			return 1;
		else
			return -1;
	}

}
