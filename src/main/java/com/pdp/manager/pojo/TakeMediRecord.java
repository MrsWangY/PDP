package com.pdp.manager.pojo;

import java.io.Serializable;
import java.util.Date;

public class TakeMediRecord implements Serializable {
    private Integer data_id;

    private String handbook_num;

    private Date recipel_dispensing_date;

    private String depart_no_exp;

    private String depart_name_exp;

    private String drug_name;

    private Byte drug_spec;

    private String drug_spec_unit;

    private Integer quantity;

    private String recipel_build_doct_no;

    private String recipel_build_doct_name;

    private String recipel_dispensing_doct_no;

    private String recipel_dispensing_doct_name;

    private Integer back_quantity;

    private String batch_number;

    private String memo;

    private static final long serialVersionUID = 1L;

    public Integer getData_id() {
        return data_id;
    }

    public void setData_id(Integer data_id) {
        this.data_id = data_id;
    }

    public String getHandbook_num() {
        return handbook_num;
    }

    public void setHandbook_num(String handbook_num) {
        this.handbook_num = handbook_num == null ? null : handbook_num.trim();
    }

    public Date getRecipel_dispensing_date() {
        return recipel_dispensing_date;
    }

    public void setRecipel_dispensing_date(Date recipel_dispensing_date) {
        this.recipel_dispensing_date = recipel_dispensing_date;
    }

    public String getDepart_no_exp() {
        return depart_no_exp;
    }

    public void setDepart_no_exp(String depart_no_exp) {
        this.depart_no_exp = depart_no_exp == null ? null : depart_no_exp.trim();
    }

    public String getDepart_name_exp() {
        return depart_name_exp;
    }

    public void setDepart_name_exp(String depart_name_exp) {
        this.depart_name_exp = depart_name_exp == null ? null : depart_name_exp.trim();
    }

    public String getDrug_name() {
        return drug_name;
    }

    public void setDrug_name(String drug_name) {
        this.drug_name = drug_name == null ? null : drug_name.trim();
    }

    public Byte getDrug_spec() {
        return drug_spec;
    }

    public void setDrug_spec(Byte drug_spec) {
        this.drug_spec = drug_spec;
    }

    public String getDrug_spec_unit() {
        return drug_spec_unit;
    }

    public void setDrug_spec_unit(String drug_spec_unit) {
        this.drug_spec_unit = drug_spec_unit == null ? null : drug_spec_unit.trim();
    }

    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }

    public String getRecipel_build_doct_no() {
        return recipel_build_doct_no;
    }

    public void setRecipel_build_doct_no(String recipel_build_doct_no) {
        this.recipel_build_doct_no = recipel_build_doct_no == null ? null : recipel_build_doct_no.trim();
    }

    public String getRecipel_build_doct_name() {
        return recipel_build_doct_name;
    }

    public void setRecipel_build_doct_name(String recipel_build_doct_name) {
        this.recipel_build_doct_name = recipel_build_doct_name == null ? null : recipel_build_doct_name.trim();
    }

    public String getRecipel_dispensing_doct_no() {
        return recipel_dispensing_doct_no;
    }

    public void setRecipel_dispensing_doct_no(String recipel_dispensing_doct_no) {
        this.recipel_dispensing_doct_no = recipel_dispensing_doct_no == null ? null : recipel_dispensing_doct_no.trim();
    }

    public String getRecipel_dispensing_doct_name() {
        return recipel_dispensing_doct_name;
    }

    public void setRecipel_dispensing_doct_name(String recipel_dispensing_doct_name) {
        this.recipel_dispensing_doct_name = recipel_dispensing_doct_name == null ? null : recipel_dispensing_doct_name.trim();
    }

    public Integer getBack_quantity() {
        return back_quantity;
    }

    public void setBack_quantity(Integer back_quantity) {
        this.back_quantity = back_quantity;
    }

    public String getBatch_number() {
        return batch_number;
    }

    public void setBatch_number(String batch_number) {
        this.batch_number = batch_number == null ? null : batch_number.trim();
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
        sb.append(", handbook_num=").append(handbook_num);
        sb.append(", recipel_dispensing_date=").append(recipel_dispensing_date);
        sb.append(", depart_no_exp=").append(depart_no_exp);
        sb.append(", depart_name_exp=").append(depart_name_exp);
        sb.append(", drug_name=").append(drug_name);
        sb.append(", drug_spec=").append(drug_spec);
        sb.append(", drug_spec_unit=").append(drug_spec_unit);
        sb.append(", quantity=").append(quantity);
        sb.append(", recipel_build_doct_no=").append(recipel_build_doct_no);
        sb.append(", recipel_build_doct_name=").append(recipel_build_doct_name);
        sb.append(", recipel_dispensing_doct_no=").append(recipel_dispensing_doct_no);
        sb.append(", recipel_dispensing_doct_name=").append(recipel_dispensing_doct_name);
        sb.append(", back_quantity=").append(back_quantity);
        sb.append(", batch_number=").append(batch_number);
        sb.append(", memo=").append(memo);
        sb.append(", serialVersionUID=").append(serialVersionUID);
        sb.append("]");
        return sb.toString();
    }
}