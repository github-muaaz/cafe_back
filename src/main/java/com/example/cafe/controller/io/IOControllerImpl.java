package com.example.cafe.controller.io;

import com.example.cafe.services.io.IOService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.UUID;

@RestController
@RequiredArgsConstructor
public class IOControllerImpl implements IOController{

    private final IOService ioService;

    @Override
    public void serveImage(UUID id, HttpServletResponse response) throws IOException {
        ioService.serveImage(id, response);
    }
}
