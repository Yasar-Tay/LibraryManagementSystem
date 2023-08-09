package com.tpe.repository;


import com.tpe.domain.Teacher;
import com.tpe.dto.TeacherDTO;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface TeacherRepository extends JpaRepository<Teacher, Long> {
    Teacher findByEmail(String email);
    List<Teacher> findByLastName(String lastName);

    @Query("SELECT new com.tpe.dto.TeacherDTO(t) FROM Teacher t WHERE t.id = :id")
    Optional<TeacherDTO> findTeacherDTO( @Param("id") Long id);

    @Query("SELECT t FROM Teacher t WHERE t.lastName = :lastName")
    List<Teacher> findByLastNameUsingJPQL(@Param("lastName") String lastName);
}
