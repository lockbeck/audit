package com.lockbeck.entities.user;

import com.lockbeck.demo.Response;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;

@RestController
@RequestMapping("/users")
@RequiredArgsConstructor
@Validated
@Slf4j
public class UserController {

    private final UserService userService;

    @PostMapping("/createUser")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Response> createUser(@Valid @RequestBody UserCreateDTO dto) {
        return ResponseEntity.ok(userService.createUser(dto));
    }
    @GetMapping("/getUsers")
    public ResponseEntity<Response> getUsers() {

        return ResponseEntity.ok(userService.getUsers());
    }
    @GetMapping("/getProfile")
    public ResponseEntity<Response> getProfile() {
        log.info("get profile api called");

        return ResponseEntity.ok(userService.getProfile());
    }
    @PutMapping("/changePsw")
    public ResponseEntity<Response> changePassword(
          @RequestBody ChangePasswordRequest request,
          Principal connectedUser
    ) {
        userService.changePassword(request, connectedUser);
        return ResponseEntity.ok().build();
    }

    @PutMapping("/changePswAdmin")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Response> changePswAdmin(@Valid @RequestBody ChangePasswordRequestAdmin dto) {
        return ResponseEntity.ok(userService.changePswAdmin(dto,dto.getUserId()));
    }

    @DeleteMapping("/delete/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Response> delete(@PathVariable("id") Integer id)
    {
        return ResponseEntity.ok(userService.delete(id));
    }
    @PutMapping("/update")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Response> update(@RequestBody UserUpdateDTO dto)
    {
        return ResponseEntity.ok(userService.update(dto));
    }
    @GetMapping("/get/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Response> get(@PathVariable("id") Integer id){
        return ResponseEntity.ok(userService.getUserById(id));
    }

}
