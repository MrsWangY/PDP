package com.pdp.manager.response;

import java.util.List;

import lombok.Getter;
import lombok.Setter;
@Getter
@Setter
/**
 * @Title: PageDataResult
 * @Description: 封装DTO分页数据（记录数和所有记录）
 * @author: LIXr
 * @version: 1.0
 */
public class PageDataResult {

    private Integer code=200;

    //总记录数量
    private Integer totals;
    
    private Integer pageNum;
    
    private Integer pageSize;

    private List<?> list;

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    public Integer getTotals() {
        return totals;
    }

    public void setTotals(Integer totals) {
        this.totals = totals;
    }

    public List <?> getList() {
        return list;
    }

    public void setList(List <?> list) {
        this.list = list;
    }

    @Override
    public String toString() {
        return "PageDataResult{" +
                "code=" + code +
                ", totals=" + totals +
                ", list=" + list +
                '}';
    }
}
