package org.javaboy.vhr.model;

import java.util.ArrayList;
import java.util.List;

public class Department {
    private Integer id;

    private String name;

    private Integer parentId;

    private String depPath;

    private Boolean enabled;

    private Boolean isParent;

    private List<Department> children = new ArrayList<>();

    private Integer result;

    @Override
    public String toString() {
        return "Department{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", parentId=" + parentId +
                ", depPath='" + depPath + '\'' +
                ", enabled=" + enabled +
                ", isParent=" + isParent +
                ", children=" + children +
                ", result=" + result +
                '}';
    }

    public Integer getResult() {
        return result;
    }

    public void setResult(Integer result) {
        this.result = result;
    }

    public List<Department> getChildren() {
        return children;
    }

    public void setChildren(List<Department> children) {
        this.children = children;
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
        this.name = name == null ? null : name.trim();
    }

    public Integer getParentId() {
        return parentId;
    }

    public void setParentId(Integer parentId) {
        this.parentId = parentId;
    }

    public String getDepPath() {
        return depPath;
    }

    public void setDepPath(String depPath) {
        this.depPath = depPath;
    }

    public Boolean getParent() {
        return isParent;
    }

    public void setParent(Boolean parent) {
        isParent = parent;
    }

    public Boolean getEnabled() {
        return enabled;
    }

    public void setEnabled(Boolean enabled) {
        this.enabled = enabled;
    }
}