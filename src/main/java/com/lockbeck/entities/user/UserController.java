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
import java.util.List;
@RestController
@RequestMapping("/users")
@PreAuthorize("hasAnyRole('ADMIN','MANAGER')")
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
    @PreAuthorize("hasAnyAuthority('admin:read','management:read')")
    public ResponseEntity<List<UserDTO>> getUsers() {

        return ResponseEntity.ok(userService.getUsers());
    }
    @GetMapping("/getProfile")
    @PreAuthorize("hasAnyRole('ADMIN','MANAGER','VIEWER')")
    public ResponseEntity<?> getProfile() {
        log.info("get profile api called");

        return ResponseEntity.ok(userService.getUserDTO());
    }
    @PutMapping("/changePsw")
    @PreAuthorize("hasAnyRole('ADMIN','MANAGER','VIEWER')")
    public ResponseEntity<?> changePassword(
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
    @PreAuthorize("hasAuthority('admin:delete')")
    public ResponseEntity<?> delete(@PathVariable("id") Integer id)
    {
        return ResponseEntity.ok(userService.delete(id));
    }
    @PutMapping("/update")
    @PreAuthorize("hasAuthority('admin:update')")
    public ResponseEntity<?> update(@RequestBody UserUpdateDTO dto)
    {
        return ResponseEntity.ok(userService.update(dto));
    }
}
