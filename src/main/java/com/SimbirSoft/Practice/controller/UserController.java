package com.SimbirSoft.Practice.controller;

import com.SimbirSoft.Practice.domain.util.User;
import com.SimbirSoft.Practice.exception.UserAlreadyExistException;
import com.SimbirSoft.Practice.exception.UserNotFoundException;
import com.SimbirSoft.Practice.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/users")
public class UserController {

    @Autowired
    private UserService userService;

    @PostMapping
    public ResponseEntity registration(@RequestBody User user) {
        try {
            userService.registration(user);
            return ResponseEntity.ok("Пользователь сохранён!");
        } catch (UserAlreadyExistException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Error");
        }
    }

    @GetMapping
    public ResponseEntity getOneUser(@RequestParam Long id) {
        try {
            return ResponseEntity.ok(userService.getOne(id));
        } catch (UserNotFoundException e){
            return ResponseEntity.badRequest().body(e.getMessage());
        } catch (Exception e){
            return ResponseEntity.badRequest().body("Error");
        }
    }

   @DeleteMapping("/{id}")
    public ResponseEntity DeleteUser(@PathVariable Long id) {
        try {
            return ResponseEntity.ok(userService.delete(id));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Error");
        }
    }

}
