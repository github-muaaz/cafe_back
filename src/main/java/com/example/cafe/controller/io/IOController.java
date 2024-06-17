package com.example.cafe.controller.io;

import com.example.cafe.utils.RestConstants;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.UUID;

@RequestMapping(path = IOController.IO_CONTROLLER_BASE_PATH)
public interface IOController {

    String IO_CONTROLLER_BASE_PATH = RestConstants.BASE_PATH + "file";

    @GetMapping("/{id}")
    void serveImage(@PathVariable UUID id, HttpServletResponse response) throws IOException;
}
