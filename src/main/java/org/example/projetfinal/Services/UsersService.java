package org.example.projetfinal.Services;


import org.example.projetfinal.Entity.Users;
import org.example.projetfinal.Repository.UsersRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UsersService  implements UserDetailsService  {
    @Autowired
    private UsersRepository usersRepository;

    public Users getUserById(int id){
        return usersRepository.findById(id).orElse(null);
    }
    public boolean existsByEmail(String email) {
        return usersRepository.existsByEmailIgnoreCase(email);
    }
    public List<Users> getAllUsers() {
        return usersRepository.findAll();
    }
    public  void deleteUser(int id ){
        usersRepository.deleteById(id);
    }
    public Users updateUserByAdmin(int id, Users userDetails){
        Users existingUser = usersRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Utilisateur non trouvé avec l'id : " + id));
        existingUser.setEmail(userDetails.getEmail());
        existingUser.setUsername(userDetails.getUsername());
        existingUser.setRoles(userDetails.getRoles());
        existingUser.setEnabled(userDetails.isEnabled());
        return usersRepository.save(existingUser);
    }

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        Users user = usersRepository.findByEmailIgnoreCase(email);
        if (user == null) {
            throw new UsernameNotFoundException("User not found");
        }
        return user;
    }

    public void save(Users user) {
        usersRepository.save(user);
    }
}
