package com.gz.gzcar.Database;

import org.xutils.db.annotation.Column;

/**
 * Created by Endeavor on 2016/9/6.
 */
class PersonTable {
    @Column(name = "id", isId = true, autoGen = true)
    private int id;

    @Column(name = "name")
    private String name;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return "PersonTable{" +
                "id=" + id +
                ", name='" + name + '\'' +
                '}';
    }
}