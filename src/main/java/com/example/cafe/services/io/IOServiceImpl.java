package com.example.cafe.services.io;

import com.example.cafe.entity.FileImg;
import com.example.cafe.entity.template.AbsUUIDEntity;
import com.example.cafe.exceptions.RestException;
import com.example.cafe.repository.FileRepository;
import com.example.cafe.utils.RestConstants;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.util.StreamUtils;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Date;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class IOServiceImpl implements IOService{

    private static final int BUFFER_SIZE = 4096;

    private final FileRepository fileRepository;

    private static final String UPLOAD_DIRECTORY = RestConstants.UPLOAD_FILE;

    public IOServiceImpl(FileRepository fileRepository) throws IOException {

        this.fileRepository = fileRepository;

        Path path = Paths.get(UPLOAD_DIRECTORY).toAbsolutePath().normalize();

        Files.createDirectories(path);
    }

    @Override
    public FileImg upload(MultipartFile originalFile) {
        try {
            if (originalFile.isEmpty())
                throw RestException
                        .restThrow("No file chosen", HttpStatus.BAD_REQUEST);

            Date date = new Date();

            Path path = Paths.get(UPLOAD_DIRECTORY, date.getTime() + "-" + originalFile.getOriginalFilename());

            Files.write(path, originalFile.getBytes());

            FileImg file = new FileImg();
            file.setName("image-" + date.getTime() + originalFile.getName());
            file.setPath(path.toString());

            return fileRepository.save(file);
        } catch (IOException e) {
            e.printStackTrace(); // Print the stack trace
            throw new RestException("COULD NOT SAVE IMAGE", HttpStatus.CONFLICT);
        }
    }

    @Override
    public List<FileImg> upload(List<MultipartFile> files) {
        return files
                .stream()
                .map(this::upload)
                .collect(Collectors.toList());
    }

    @Override
    public void delete(FileImg file) {
        try {
            Files.deleteIfExists(Path.of(file.getPath()));

            fileRepository.deleteById(file.getId());
        } catch (IOException e) {
            e.printStackTrace();
            throw new RestException("COULD NOT DELETE FILE", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @Override
    public void delete(List<FileImg> files) {
        List<UUID> ids = files.stream().map(AbsUUIDEntity::getId).collect(Collectors.toList());
        try {
            for (FileImg file : files)
                Files.deleteIfExists(Path.of(file.getPath()));

            fileRepository.deleteAllById(ids);
        } catch (IOException e) {
            e.printStackTrace();
            throw new RestException("COULD NOT DELETE FILE", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @Override
    public void serveImage(UUID id, HttpServletResponse response) throws IOException {

        FileImg file = fileRepository.findById(id)
                .orElseThrow(() ->
                        RestException
                                .restThrow("FILE NOT FOUND", HttpStatus.NOT_FOUND)
                );

        String filePath = file.getPath();

        InputStream resource = new FileInputStream(filePath);

        response.setContentType(MediaType.ALL_VALUE);

        StreamUtils.copy(resource, response.getOutputStream());
    }
}
