package com.pdp.manager.pojo;

import java.io.Serializable;
import java.util.Date;

public class TakeMediHandbook implements Serializable {
    private Integer data_id;

    private String handbook_num;

    private String patient_name;

    private String patient_tel;

    private String patient_cardid;

    private String contactor_name;

    private String contactor_tel;

    private Date issuing_time;

    private String expiry_date;

    private Date expiration_time;

    private String hosp_code;

    private String hosp_name;

    private String give_card_org_code;

    private String give_card_org_name;

    private Date file_date;

    private Byte is_cancellation;

    private Date cancellation_date;

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

    public String getPatient_name() {
        return patient_name;
    }

    public void setPatient_name(String patient_name) {
        this.patient_name = patient_name == null ? null : patient_name.trim();
    }

    public String getPatient_tel() {
        return patient_tel;
    }

    public void setPatient_tel(String patient_tel) {
        this.patient_tel = patient_tel == null ? null : patient_tel.trim();
    }

    public String getPatient_cardid() {
        return patient_cardid;
    }

    public void setPatient_cardid(String patient_cardid) {
        this.patient_cardid = patient_cardid == null ? null : patient_cardid.trim();
    }

    public String getContactor_name() {
        return contactor_name;
    }

    public void setContactor_name(String contactor_name) {
        this.contactor_name = contactor_name == null ? null : contactor_name.trim();
    }

    public String getContactor_tel() {
        return contactor_tel;
    }

    public void setContactor_tel(String contactor_tel) {
        this.contactor_tel = contactor_tel == null ? null : contactor_tel.trim();
    }

    public Date getIssuing_time() {
        return issuing_time;
    }

    public void setIssuing_time(Date issuing_time) {
        this.issuing_time = issuing_time;
    }

    public String getExpiry_date() {
        return expiry_date;
    }

    public void setExpiry_date(String expiry_date) {
        this.expiry_date = expiry_date == null ? null : expiry_date.trim();
    }

    public Date getExpiration_time() {
        return expiration_time;
    }

    public void setExpiration_time(Date expiration_time) {
        this.expiration_time = expiration_time;
    }

    public String getHosp_code() {
        return hosp_code;
    }

    public void setHosp_code(String hosp_code) {
        this.hosp_code = hosp_code == null ? null : hosp_code.trim();
    }

    public String getHosp_name() {
        return hosp_name;
    }

    public void setHosp_name(String hosp_name) {
        this.hosp_name = hosp_name == null ? null : hosp_name.trim();
    }

    public String getGive_card_org_code() {
        return give_card_org_code;
    }

    public void setGive_card_org_code(String give_card_org_code) {
        this.give_card_org_code = give_card_org_code == null ? null : give_card_org_code.trim();
    }

    public String getGive_card_org_name() {
        return give_card_org_name;
    }

    public void setGive_card_org_name(String give_card_org_name) {
        this.give_card_org_name = give_card_org_name == null ? null : give_card_org_name.trim();
    }

    public Date getFile_date() {
        return file_date;
    }

    public void setFile_date(Date file_date) {
        this.file_date = file_date;
    }

    public Byte getIs_cancellation() {
        return is_cancellation;
    }

    public void setIs_cancellation(Byte is_cancellation) {
        this.is_cancellation = is_cancellation;
    }

    public Date getCancellation_date() {
        return cancellation_date;
    }

    public void setCancellation_date(Date cancellation_date) {
        this.cancellation_date = cancellation_date;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(getClass().getSimpleName());
        sb.append(" [");
        sb.append("Hash = ").append(hashCode());
        sb.append(", data_id=").append(data_id);
        sb.append(", handbook_num=").append(handbook_num);
        sb.append(", patient_name=").append(patient_name);
        sb.append(", patient_tel=").append(patient_tel);
        sb.append(", patient_cardid=").append(patient_cardid);
        sb.append(", contactor_name=").append(contactor_name);
        sb.append(", contactor_tel=").append(contactor_tel);
        sb.append(", issuing_time=").append(issuing_time);
        sb.append(", expiry_date=").append(expiry_date);
        sb.append(", expiration_time=").append(expiration_time);
        sb.append(", hosp_code=").append(hosp_code);
        sb.append(", hosp_name=").append(hosp_name);
        sb.append(", give_card_org_code=").append(give_card_org_code);
        sb.append(", give_card_org_name=").append(give_card_org_name);
        sb.append(", file_date=").append(file_date);
        sb.append(", is_cancellation=").append(is_cancellation);
        sb.append(", cancellation_date=").append(cancellation_date);
        sb.append(", serialVersionUID=").append(serialVersionUID);
        sb.append("]");
        return sb.toString();
    }
}