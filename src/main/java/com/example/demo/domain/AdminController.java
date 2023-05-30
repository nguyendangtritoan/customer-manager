package com.example.demo.domain;

import com.example.demo.user.User;
import com.example.demo.web.SuccessResponse;
import com.example.demo.web.exception.BadRequestException;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.util.ObjectUtils;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/v1/admin")
@PreAuthorize("hasRole('ADMIN')")
@RequiredArgsConstructor
public class AdminController {

    private final AdminService adminService;

    @GetMapping("test")
    @PreAuthorize("hasAuthority('admin:read')")
    public String get() {
        return "GET:: admin controller";
    }

    @GetMapping
    @PreAuthorize("hasAuthority('admin:read')")
    public ResponseEntity<SuccessResponse<List<User>>> list() {
        return new ResponseEntity<>(new SuccessResponse<>(adminService.listCustomer(), "Success"), HttpStatus.OK);
    }

    @PostMapping
    @PreAuthorize("hasAuthority('admin:create')")
    public ResponseEntity<SuccessResponse<User>> post(@RequestBody User user) {
        if (!ObjectUtils.isEmpty(user.getId())) {
            throw new BadRequestException("A new data cannot already have an ID");
        }
        return new ResponseEntity<>(new SuccessResponse<>(adminService.createCustomer(user), "Success"), HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasAuthority('admin:update')")
    public ResponseEntity<SuccessResponse<User>> put(
            @PathVariable("id") Integer id,
            @RequestBody User updatedUser) {
        Optional<User> updatedResult = adminService.updateUser(id, updatedUser);
        return updatedResult.map(
                user ->  new ResponseEntity<>(new SuccessResponse<>(user, "Success"), HttpStatus.OK)
        ).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasAuthority('admin:delete')")
    public ResponseEntity<SuccessResponse<Boolean>> delete(@PathVariable("id") Integer id) {
        return new ResponseEntity<>(new SuccessResponse<>(adminService.deleteUser(id), "Deletion completed successfully"), HttpStatus.OK);
    }
}