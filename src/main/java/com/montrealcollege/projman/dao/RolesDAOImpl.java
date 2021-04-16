package com.montrealcollege.projman.dao;

import com.montrealcollege.projman.model.UsersRoles;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import java.util.List;

@Repository
@Transactional
public class RolesDAOImpl implements RolesDAO{
    @Autowired
    private EntityManager entityManager;

    public List<String> getRoleNames(Long userId) {
        String sql = "Select ur.role.roleName from " + UsersRoles.class.getName() + " ur "
                + " where ur.user.id = :userId ";

        Query query = this.entityManager.createQuery(sql, String.class);
        query.setParameter("userId", userId);
        return query.getResultList();
    }
}
