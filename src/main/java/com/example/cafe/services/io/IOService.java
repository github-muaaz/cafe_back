package com.example.cafe.services.io;

import com.example.cafe.entity.FileImg;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;
import java.util.UUID;

public interface IOService {

    FileImg upload(MultipartFile file) throws IOException;

    List<FileImg> upload(List<MultipartFile> file) throws IOException;

    void delete(FileImg file);

    void delete(List<FileImg> files);

    void serveImage(UUID id, HttpServletResponse response) throws IOException;
}
