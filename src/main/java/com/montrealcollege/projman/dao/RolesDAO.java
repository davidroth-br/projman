package com.montrealcollege.projman.dao;

import java.util.List;

public interface RolesDAO {

    List<String> getRoleNames(Long userId);
}
