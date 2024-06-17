package com.example.cafe.payload.api;

import com.example.cafe.entity.FileImg;
import com.example.cafe.exceptions.RestException;
import com.example.cafe.utils.MessageConstants;
import org.springframework.http.HttpStatus;

import java.util.Comparator;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

public class FileDTO {

    public static UUID mapFile(FileImg fileImg){
        return fileImg.getId();
    }

    public static List<UUID> mapFile(List<FileImg> images) {
        return images
                .stream()
                .map(FileImg::getId)
                .sorted()
                .collect(Collectors.toList());
    }

    public static UUID getFirst(List<FileImg> images) {
        if (images.size() < 1)
            return null;

        images.sort(Comparator.comparing(FileImg::getId));
        return images
                .stream()
                .findFirst()
                .orElseThrow(() ->
                        RestException
                                .restThrow(MessageConstants.RESOURCE_NOT_FOUND, HttpStatus.NOT_FOUND))
                .getId();
    }
}
