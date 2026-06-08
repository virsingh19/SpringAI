package com.app.realestate.repository;
import com.app.realestate.model.House;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface AppRepository extends JpaRepository<House, String> {
    @Query("SELECT h FROM House h WHERE h.type LIKE %:type%")
    List<House> findByType(@Param("type") String type);

    @Query("SELECT h FROM House h WHERE h.type LIKE %:type% AND h.zipCode = :zipCode")
    List<House> findByTypeAndZipCode(@Param("type") String type, @Param("zipCode") String zipCode);
}
