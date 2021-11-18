package by.ginel.autopark.model;

import java.util.Date;

public class Ticket {

    private Integer spotNum;
    private Date dateOfExp;

    public Integer getSpotNum() { return spotNum; }

    public void setSpotNum(Integer spotNum) { this.spotNum = spotNum; }

    public Date getDateOfExp() { return dateOfExp; }

    public void setDateOfExp(Date dateOfExp) { this.dateOfExp = dateOfExp; }
}
