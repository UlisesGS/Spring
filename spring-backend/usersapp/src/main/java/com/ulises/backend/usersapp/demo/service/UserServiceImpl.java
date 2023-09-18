package com.ulises.backend.usersapp.demo.service;

import com.ulises.backend.usersapp.demo.entity.Role;
import com.ulises.backend.usersapp.demo.entity.User;
import com.ulises.backend.usersapp.demo.repository.RoleRepository;
import com.ulises.backend.usersapp.demo.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class UserServiceImpl implements UserService{


    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    @Transactional(readOnly = true)
    public List<User> findAll() {
        return userRepository.findAll();
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<User> findById(Long id) {
        return userRepository.findById(id);
    }

    @Override
    @Transactional
    public User save(User user) {
        user.setPassword(passwordEncoder.encode(user.getPassword()));/*ENCRIPTO LA CONTRASEÑA*/

        Optional<Role> roleOptional = roleRepository.findByName("ROLE_USER");

        List<Role>roles = new ArrayList<>();
        if (roleOptional.isPresent()){
            roles.add(roleOptional.orElseThrow());
        }
        user.setRoles(roles);
        return userRepository.save(user);
    }

    @Override
    @Transactional
    public void deleteById(Long id) {
        userRepository.deleteById(id);
    }
}
