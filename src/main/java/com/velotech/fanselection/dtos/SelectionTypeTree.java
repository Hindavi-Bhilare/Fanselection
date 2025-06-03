
package com.velotech.fanselection.dtos;

import java.util.List;

public class SelectionTypeTree {

	private Integer id;

	private String text;

	private boolean leaf;

	private boolean expanded;

	private String iconCls;

	private List<Object> data;

	public Integer getId() {

		return id;
	}

	public void setId(Integer id) {

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

	public boolean isExpanded() {

		return expanded;
	}

	public void setExpanded(boolean expanded) {

		this.expanded = expanded;
	}

	public String getIconCls() {

		return iconCls;
	}

	public void setIconCls(String iconCls) {

		this.iconCls = iconCls;
	}

	public List<Object> getData() {

		return data;
	}

	public void setData(List<Object> data) {

		this.data = data;
	}

}
