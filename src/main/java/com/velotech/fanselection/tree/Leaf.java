
package com.velotech.fanselection.tree;

public class Leaf implements Comparable<Leaf> {

	private int id;

	private String text;

	private String code;

	private boolean leaf;

	private Double sequence;

	private String title;

	private String xtype;

	private Integer departmentId;

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

	public String getTitle() {

		return title;
	}

	public void setTitle(String title) {

		this.title = title;
	}

	public String getXtype() {

		return xtype;
	}

	public void setXtype(String xtype) {

		this.xtype = xtype;
	}

	public String getCode() {

		return code;
	}

	public void setCode(String code) {

		this.code = code;
	}

	public Integer getDepartmentId() {

		return departmentId;
	}

	public void setDepartmentId(Integer departmentId) {

		this.departmentId = departmentId;
	}

	@Override
	public int compareTo(Leaf o) {

		if (this.id > o.id)
			return 1;
		else
			return -1;
	}

}
