package com.example.cafe.component;

import com.example.cafe.entity.*;
import com.example.cafe.entity.enums.PageEnum;
import com.example.cafe.entity.enums.PermissionEnum;
import com.example.cafe.entity.enums.UniStatusEnum;
import com.example.cafe.entity.language.Language;
import com.example.cafe.entity.language.LanguageKey;
import com.example.cafe.entity.language.LanguageValue;
import com.example.cafe.repository.*;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.io.File;
import java.time.LocalDate;
import java.util.*;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class DataLoader implements CommandLineRunner {

    private final RoleRepository roleRepository;
    private final UserRepository userRepository;
    private final LanguageRepository languageRepository;

    private final PasswordEncoder passwordEncoder;

    @Value("${app.admin.email}")
    private String adminEmail;

    @Value("${app.admin.password}")
    private String adminPassword;

    @Value("${spring.jpa.hibernate.ddl-auto}")
    private String modeType;
    private final LanguageKeyRepository languageKeyRepository;
    private final LanguageValueRepository languageValueRepository;
    private final CategoryRepository categoryRepository;

    @Override
    public void run(String... args) {
        if (Objects.equals("create", modeType)) {
            addAdmin();
            addDefaultLanguage();
            addUserRole();
            addDefaultCategories();
        }
    }

    private void addDefaultCategories() {
        String[] defaults = {"Food", "Fast food", "Drink", "Desert", "Fruit", "Fish", };
        List<Category> categories = Arrays
                .stream(defaults)
                .map((str) -> Category.builder().name(str).status(UniStatusEnum.ACTIVE).build())
                .collect(Collectors.toList());

        categoryRepository.saveAll(categories);
    }

    private void addDefaultLanguage() {
        Language uzbLanguage = languageRepository.save(
                Language.builder()
                        .code("uzb")
                        .name("Uzbek")
                        .status(UniStatusEnum.ACTIVE)
                        .build());

        languageRepository.save(
                Language.builder()
                        .code("rus")
                        .name("Ruscha")
                        .status(UniStatusEnum.ACTIVE)
                        .build());

        LanguageKey sendLanguageKey = languageKeyRepository.save(
                LanguageKey.builder()
                        .key("send")
                        .build());

        LanguageKey cancelLanguageKey = languageKeyRepository.save(
                LanguageKey.builder()
                        .key("cancel")
                        .build());

        List<LanguageValue> languageValues = new ArrayList<>();

        languageValues.add(LanguageValue.builder()
                .languageKey(cancelLanguageKey)
                .language(uzbLanguage)
                .value("bekor qilish")
                .build());

        languageValues.add(LanguageValue.builder()
                .languageKey(sendLanguageKey)
                .language(uzbLanguage)
                .value("jo'natish")
                .build());

        languageValueRepository.saveAll(languageValues);
    }

    private void addAdmin() {
        userRepository.save(
                User.builder()
                        .firstname("Admin")
                        .lastname("Main")
                        .email(adminEmail)
                        .password(passwordEncoder.encode(adminPassword))
                        .role(addAdminRole())
                        .enabled(true)
                        .build());

//        userRepository.save(
//                User.builder()
//                        .firstname("Waiter")
//                        .lastname("_")
//                        .email("waiter@gmail.com")
//                        .password(passwordEncoder.encode("waiterPasswords"))
//                        .role(addAdminRole())
//                        .enabled(true)
//                        .build());
    }

    private Role addAdminRole() {
        return roleRepository.save(
                new Role("Admin",
                        "System owner",
                        PageEnum.MENU,
                        Set.of(PermissionEnum.values()),
                        Set.of(PageEnum.values())
                )
        );
    }

    private Role addWaiterRole() {
        return roleRepository.save(
                new Role("Waiter",
                        "Main waiter",
                        PageEnum.MENU,
                        Set.of(PermissionEnum.values()),
                        Collections.singleton(PageEnum.PRODUCTS)
                )
        );
    }

    private void addUserRole() {
        roleRepository.save(
                new Role("Waiter",
                        "Default Waiter role",
                        PageEnum.PRODUCTS,
                        Arrays
                                .stream(PermissionEnum.values())
                                .filter(permissionEnum -> {
                                    System.out.println(permissionEnum);
                                    return true;
                                })
                                .collect(Collectors.toSet()),
                        Arrays
                                .stream(PageEnum.values())
//                                .filter(pageEnum -> !Objects.equals(pageEnum, PageEnum.USERS) && !Objects.equals(pageEnum, PageEnum.ROLE))
                                .collect(Collectors.toSet())
                )
        );
    }
}
