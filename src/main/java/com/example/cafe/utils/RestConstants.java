package com.example.cafe.utils;

import com.example.cafe.controller.auth.AuthController;
import com.example.cafe.controller.category.CategoryController;
import com.example.cafe.controller.io.IOController;
import com.example.cafe.controller.language.LanguageController;
import com.example.cafe.controller.product.ProductController;
import com.fasterxml.jackson.databind.ObjectMapper;

public interface RestConstants {
    ObjectMapper objectMapper = new ObjectMapper();

    String AUTHENTICATION_HEADER = "Authorization";

    String UPLOAD_FILE = "/home/ubuntu/files/img";

    String REFRESH_TOKEN_HEADER = "RefreshToken";

    String REQUEST_ATTRIBUTE_CURRENT_USER = "User";
    String REQUEST_ATTRIBUTE_CURRENT_USER_ID = "UserId";
    String REQUEST_ATTRIBUTE_CURRENT_USER_PERMISSIONS = "Permissions";

    String[] OPEN_PAGES = {
            "/*",
            AuthController.AUTH_CONTROLLER_BASE_PATH + "/**",
            LanguageController.LANGUAGE_CONTROLLER_BASE_PATH + "/**",
            IOController.IO_CONTROLLER_BASE_PATH + "/**",
            ProductController.PRODUCT_CONTROLLER_BASE_PATH + ProductController.GET_PRODUCT_BY_CATEGORY_PATH,
            CategoryController.CATEGORY_CONTROLLER_BASE_PATH + CategoryController.GET_ALL_CATEGORIES_BY_PARENT_PATH,
            RestConstants.BASE_PATH + "/**"
    };
    String BASE_PATH = "/api/v1/";
    String DEFAULT_PAGE = "0";
    String NO = "NO";
    String YES = "YES";
}
