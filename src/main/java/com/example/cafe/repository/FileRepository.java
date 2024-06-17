package com.example.cafe.repository;

import com.example.cafe.entity.FileImg;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.UUID;

public interface FileRepository extends JpaRepository<FileImg, UUID> {

    @Query(value = "SELECT * FROM file_img WHERE name = 'defaultAvatar'", nativeQuery = true)
    FileImg getDefaultAvatar();

    @Query(value = "SELECT * FROM file_img WHERE name = 'defaultBg'", nativeQuery = true)
    FileImg getDefaultBg();

    @Query(value = "SELECT path FROM file_img where id =?", nativeQuery = true)
    String findPathById(UUID id);

}
