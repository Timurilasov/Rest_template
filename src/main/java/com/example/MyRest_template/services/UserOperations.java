package com.example.MyRest_template.services;

import com.example.MyRest_template.model.User;
import com.example.MyRest_template.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class UserOperations implements CommandLineRunner {

    @Autowired
    private UserService userService;

    @Override
    public void run(String... args) {
        System.out.println("Начинаем выполнение операций...");

        // 1. Получаем список пользователей
        List<User> users = userService.getAllUsers();
        System.out.println("Список пользователей: " + users);

        // 2. Сохраняем нового пользователя
        User newUser = new User(3L, "James", "Brown", (byte) 30);
        String part1 = userService.saveUser(newUser);
        System.out.println("Ответ на добавление пользователя: " + part1);

        // 3. Обновляем пользователя
        newUser.setName("Thomas");
        newUser.setLastName("Shelby");
        String part2 = userService.updateUser(newUser);
        System.out.println("Ответ на обновление пользователя: " + part2);

        // 4. Удаляем пользователя
        String part3 = userService.deleteUser(3L);
        System.out.println("Ответ на удаление пользователя: " + part3);

        // Конкатенируем все части кода
        String finalCode = part1 + part2 + part3;
        System.out.println("Итоговый код: " + finalCode);
    }
}
