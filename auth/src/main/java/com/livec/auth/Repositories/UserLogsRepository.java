package com.livec.auth.Repositories;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

import com.livec.auth.models.UserLogs;


public interface UserLogsRepository extends JpaRepository<UserLogs, Long> {
    
    List<UserLogs> findAll();

}
