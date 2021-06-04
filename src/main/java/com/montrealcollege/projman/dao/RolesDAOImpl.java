package com.montrealcollege.projman.dao;

import com.montrealcollege.projman.model.Roles;
import com.montrealcollege.projman.model.Users;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.*;
import java.util.List;

@Repository
@Transactional
public class RolesDAOImpl implements RolesDAO{
    @Autowired
    private EntityManager entityManager;

    public String getRoleName(Long role) {

        CriteriaBuilder builder = entityManager.getCriteriaBuilder();
        CriteriaQuery<String> criteria = builder.createQuery(String.class);
        Root<Roles> rolesRoot = criteria.from(Roles.class);
        criteria.select(rolesRoot.get("roleName")).where(builder.equal(rolesRoot.get("roleId"), role));

        TypedQuery<String> query = entityManager.createQuery(criteria);
        return query.getSingleResult();
    }
}
