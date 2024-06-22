package com.example.cafe.services.io;

import com.example.cafe.entity.FileImg;
import com.example.cafe.entity.template.AbsUUIDEntity;
import com.example.cafe.exceptions.RestException;
import com.example.cafe.repository.FileRepository;
import com.example.cafe.utils.RestConstants;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.util.StreamUtils;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Date;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class IOServiceImpl implements IOService {

    private final FileRepository fileRepository;

    private static final String UPLOAD_DIRECTORY = RestConstants.UPLOAD_FILE;

    public IOServiceImpl(FileRepository fileRepository) {
        this.fileRepository = fileRepository;
    }

    @Override
    public FileImg upload(MultipartFile originalFile) {
        try {
            if (originalFile.isEmpty())
                throw RestException.restThrow("No file chosen", HttpStatus.BAD_REQUEST);

            Date date = new Date();
            byte[] content = originalFile.getBytes();

            FileImg file = new FileImg();
            file.setName("image-" + date.getTime() + originalFile.getOriginalFilename());
            file.setPath(UPLOAD_DIRECTORY);
            file.setContent(content);

            return fileRepository.save(file);
        } catch (IOException e) {
            e.printStackTrace(); // Print the stack trace
            throw new RestException("COULD NOT SAVE IMAGE", HttpStatus.CONFLICT);
        }
    }

    @Override
    public List<FileImg> upload(List<MultipartFile> files) {
        return files.stream()
                .map(this::upload)
                .collect(Collectors.toList());
    }

    @Override
    public void delete(FileImg file) {
        try {
            fileRepository.delete(file);
        } catch (Exception e) {
            e.printStackTrace();
            throw new RestException("COULD NOT DELETE FILE", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @Override
    public void delete(List<FileImg> files) {
        try {
            fileRepository.deleteInBatch(files);
        } catch (Exception e) {
            e.printStackTrace();
            throw new RestException("COULD NOT DELETE FILE", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @Override
    public void serveImage(UUID id, HttpServletResponse response) throws IOException {
        FileImg file = fileRepository.findById(id)
                .orElseThrow(() ->
                        RestException.restThrow("FILE NOT FOUND", HttpStatus.NOT_FOUND));

        response.setContentType("application/octet-stream");
        response.setContentLengthLong(file.getContent().length);
        response.setHeader("Content-Disposition", "inline; filename=\"" + file.getName() + "\"");

        StreamUtils.copy(file.getContent(), response.getOutputStream());
    }
}
