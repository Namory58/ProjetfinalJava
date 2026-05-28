package org.example.projetfinal.Repository;

import org.example.projetfinal.Entity.Users;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UsersRepository extends JpaRepository<Users,Integer> {
    Users findByEmailIgnoreCase(String email);
    boolean existsByEmailIgnoreCase(String email);

}
