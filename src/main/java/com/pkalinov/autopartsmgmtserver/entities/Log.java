package com.pkalinov.autopartsmgmtserver.entities;

import javax.persistence.*;
import java.io.Serializable;
import java.sql.Timestamp;

@Entity
@Table(name = "logs")
public class Log implements Serializable {
    public static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "incidentTime")
    private Timestamp incidentTime;

    @ManyToOne
    @JoinColumn(name = "partId")
    private Part part;

    @Column(name = "errorMessage")
    private String errorMessage;

    public Log() {
    }//default constructor

    public Log(Long id, Timestamp incidentTime, Part part, String errorMessage) {
        this.id = id;
        this.incidentTime = incidentTime;
        this.part = part;
        this.errorMessage = errorMessage;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Timestamp getIncidentTime() {
        return incidentTime;
    }

    public void setIncidentTime(Timestamp incidentTime) {
        this.incidentTime = incidentTime;
    }

    public Part getPart() {
        return part;
    }

    public void setPart(Part part) {
        this.part = part;
    }

    public String getErrorMessage() {
        return errorMessage;
    }

    public void setErrorMessage(String errorMessage) {
        this.errorMessage = errorMessage;
    }
}
