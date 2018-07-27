package com.samyotech.fabcustomer.DTO;

import java.io.Serializable;

public class TicketDTO implements Serializable {
    String id = "";
    String user_id = "";
    String reason = "";
    int status = 0;
    long craeted_at = 0;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUser_id() {
        return user_id;
    }

    public void setUser_id(String user_id) {
        this.user_id = user_id;
    }

    public String getReason() {
        return reason;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public long getCraeted_at() {
        return craeted_at;
    }

    public void setCraeted_at(long craeted_at) {
        this.craeted_at = craeted_at;
    }
}
