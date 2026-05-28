package org.example.projetfinal.Services;

import org.example.projetfinal.Entity.Role;
import org.example.projetfinal.Entity.Users;
import org.example.projetfinal.Repository.RoleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RoleService {
    @Autowired
    private RoleRepository roleRepository;

    public List<Role> getAllRoles(){
        return roleRepository.findAll();
    }
}
