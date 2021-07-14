package com.pkalinov.autopartsmgmtserver.repositories;

import com.pkalinov.autopartsmgmtserver.entities.Log;
import org.springframework.data.jpa.repository.JpaRepository;

public interface LogRepository extends JpaRepository<Log, Long> {

}
