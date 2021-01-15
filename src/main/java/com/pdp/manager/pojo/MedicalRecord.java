package com.pdp.manager.pojo;

import java.io.Serializable;
import java.util.Date;

public class MedicalRecord implements Serializable {
    private Integer data_id;

    private String case_num;

    private Integer year;

    private Date diag_time;

    private String memo;

    private static final long serialVersionUID = 1L;

    public Integer getData_id() {
        return data_id;
    }

    public void setData_id(Integer data_id) {
        this.data_id = data_id;
    }

    public String getCase_num() {
        return case_num;
    }

    public void setCase_num(String case_num) {
        this.case_num = case_num == null ? null : case_num.trim();
    }

    public Integer getYear() {
        return year;
    }

    public void setYear(Integer year) {
        this.year = year;
    }

    public Date getDiag_time() {
        return diag_time;
    }

    public void setDiag_time(Date diag_time) {
        this.diag_time = diag_time;
    }

    public String getMemo() {
        return memo;
    }

    public void setMemo(String memo) {
        this.memo = memo == null ? null : memo.trim();
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(getClass().getSimpleName());
        sb.append(" [");
        sb.append("Hash = ").append(hashCode());
        sb.append(", data_id=").append(data_id);
        sb.append(", case_num=").append(case_num);
        sb.append(", year=").append(year);
        sb.append(", diag_time=").append(diag_time);
        sb.append(", memo=").append(memo);
        sb.append(", serialVersionUID=").append(serialVersionUID);
        sb.append("]");
        return sb.toString();
    }
}