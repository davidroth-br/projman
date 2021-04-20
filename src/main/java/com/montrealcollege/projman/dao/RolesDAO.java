package com.montrealcollege.projman.dao;

import com.montrealcollege.projman.model.Roles;

import java.util.List;

public interface RolesDAO {

    List<String> getRoleNames(Long userId);

}
