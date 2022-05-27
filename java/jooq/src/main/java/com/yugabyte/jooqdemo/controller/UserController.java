package com.yugabyte.jooqdemo.controller;

import com.yugabyte.jooqdemo.controller.DTO.User;
import com.yugabyte.jooqdemo.db.tables.records.UsersRecord;
import com.yugabyte.jooqdemo.exception.ResourceNotFoundException;
import org.jooq.DSLContext;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static com.yugabyte.jooqdemo.db.Tables.USERS;
import static org.jooq.Records.mapping;

@RestController
public class UserController {

    private final DSLContext ctx;

    public UserController(DSLContext ctx) {
        this.ctx = ctx;
    }

    @GetMapping("/users")
    public List<User> getUsers() {
        return ctx.selectFrom(USERS).fetch(mapping(User::new));
    }

    @PostMapping("/users")
    public User createUser(@RequestBody User user) {
        UsersRecord record = ctx.newRecord(USERS, user);
        record.changed(USERS.USER_ID, false);
        record.insert();
        return record.into(User.class);
    }

    @PutMapping("/users/{userId}")
    public User updateUser(
        @PathVariable Long userId,
        @RequestBody User user
    ) {
        UsersRecord record = ctx.newRecord(USERS, user);
        record.setUserId(userId);

        if (record.update() == 1)
            return user;
        else
            throw new ResourceNotFoundException("User not found with id " + userId);
    }

    @DeleteMapping("/users/{userId}")
    public ResponseEntity<?> deleteUser(@PathVariable Long userId) {
        if (ctx.delete(USERS).where(USERS.USER_ID.eq(userId)).execute() == 1)
            return ResponseEntity.ok().build();
        else
            throw new ResourceNotFoundException("User not found with id " + userId);
    }
}
