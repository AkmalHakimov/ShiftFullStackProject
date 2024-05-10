package com.example.demo.repository;


import com.example.demo.entity.Attachment;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface FileRepo extends JpaRepository<Attachment,UUID> {

}
