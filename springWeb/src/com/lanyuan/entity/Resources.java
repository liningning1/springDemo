package com.lanyuan.entity;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

@SuppressWarnings("serial")
public class Resources implements Serializable {

	private Integer id;
	private String name;
	private Integer parentId;//父类的id
	private String parentName;
	private String resKey;//这个权限key是唯一的，新增的时候要注意
	private String resUrl;//url地址。 如 /videoType/query  不需要项目名和http://xxx:8080
	private Integer level;
	private String type;//权限类型, 0.表示目录 1.表示菜单 2.表示按钮   在spring security3安全权限中，涉及精确到按钮控制
    private String description;
    private Set<Roles> roles = new HashSet<Roles>(0);
    private Set<Resources> childs = new HashSet<Resources>(0);
    
    public Resources()
    {
    	
    }

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Integer getParentId() {
		return parentId;
	}

	public void setParentId(Integer parentId) {
		this.parentId = parentId;
	}

	public String getParentName() {
		return parentName;
	}

	public void setParentName(String parentName) {
		this.parentName = parentName;
	}

	public String getResKey() {
		return resKey;
	}

	public void setResKey(String resKey) {
		this.resKey = resKey;
	}

	public String getResUrl() {
		return resUrl;
	}

	public void setResUrl(String resUrl) {
		this.resUrl = resUrl;
	}

	public Integer getLevel() {
		return level;
	}

	public void setLevel(Integer level) {
		this.level = level;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public Set<Roles> getRoles() {
		return roles;
	}

	public void setRoles(Set<Roles> roles) {
		this.roles = roles;
	}

	public Set<Resources> getChilds() {
		return childs;
	}

	public void setChilds(Set<Resources> childs) {
		this.childs = childs;
	}
}
