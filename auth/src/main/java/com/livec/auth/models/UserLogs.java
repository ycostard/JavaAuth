package com.livec.auth.models;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "users_logs")
@Getter
@Setter
public class UserLogs {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id")
    private Long id;

    @Column(name = "user_id")
    private String user;

    @Column(name = "ip_address")
    private String ipAddress;

    @Column(name = "status")
    private String status;

    public UserLogs() {
    }

    public UserLogs(String user, String ipAddress, String status) {
        this.user = user;
        this.ipAddress = ipAddress;
        this.status = status;
    }
}
