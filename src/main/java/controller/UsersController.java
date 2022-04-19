package controller;

import exception.ResourceNotFoundException;
import model.Users;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import repository.UsersRepository;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
//@RequestMapping("/v1")
public class UsersController {

    @Autowired
    private UsersRepository usersRepository;

    // get users
    @GetMapping("/users")
    public List<Users> getAllUsers() {
        return this.usersRepository.findAll();
    }

    // get user by id
    @GetMapping("/user/{id}")
    public ResponseEntity<Users> getUserById(@PathVariable(value = "id") Long userId) throws ResourceNotFoundException {
        Users user = usersRepository.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("User with this id not found:: " + userId));
        return ResponseEntity.ok().body(user);
    }

    // save user
    @PostMapping("/users")
    public Users createUser(@Validated @RequestBody Users user) {
        return usersRepository.save(user);
    }

    // update user
    @PutMapping("/users/{id}")
    public ResponseEntity<Users> updateUser(@PathVariable(value = "id") Long userId,
                                            @Validated @RequestBody Users userDetails) throws ResourceNotFoundException {
        Users user = usersRepository.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("User with this id not found:: " + userId));

        user.setEmail(userDetails.getEmail());
        user.setFirstName(userDetails.getFirstName());
        user.setLastName(userDetails.getLastName());

        return ResponseEntity.ok(this.usersRepository.save(user));
    }

    //delete user
    @DeleteMapping("/users/{id}")
    public Map<String, Boolean> deleteUser(@PathVariable(value = "id") Long userId) throws ResourceNotFoundException {
        Users user = usersRepository.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("User with this id not found:: " + userId));

        this.usersRepository.delete(user);

        Map<String, Boolean> response = new HashMap<>();
        response.put("deleted", Boolean.TRUE);

        return response;
    }

}



















