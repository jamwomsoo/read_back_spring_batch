package com.example.daemonTest.core.repository;

import com.example.daemonTest.core.domain.Company;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface CompanyRepository extends JpaRepository<Company,Long> {
    @Query("SELECT (COUNT(companyName) > 0) " +
            "FROM Company " +
            "WHERE companyName=:company_name " +
            "AND address=:address")
    boolean existByCompany(@Param("company_name") String company_name, @Param("address") String address);
}
