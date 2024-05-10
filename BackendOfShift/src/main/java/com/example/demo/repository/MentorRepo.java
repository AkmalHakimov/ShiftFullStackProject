package com.example.demo.repository;

import com.example.demo.entity.Mentor;
import com.example.demo.projection.MentorProjection;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.UUID;

public interface MentorRepo extends JpaRepository<Mentor, UUID> {

}
