package com.lawrence.repository;

import org.springframework.data.repository.CrudRepository;

import com.lawrence.security.Role;

public interface RoleRepository extends CrudRepository<Role, Integer> {
    Role findByName(String name);
}
