package com.example.demo.repository;

import com.example.demo.entity.Mentor;
import com.example.demo.entity.User;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface UserRepo extends JpaRepository<User, UUID> {

    Optional<User> findByPhone(String x);

    @Query(value ="select u.id,u.age,u.first_name,u.last_name,u.description,u.attachment_id,u.password,u.phone from users u join users_roles ur on u.id = ur.user_id and roles_id = 2 order by u.id desc\n",nativeQuery = true)
            List<User> getByRoleUser();

            @Modifying
            @Transactional
    @Query(value = "DELETE FROM users_roles WHERE user_id = :userId",nativeQuery = true)
            void deleteByUserId( UUID userId);

}
