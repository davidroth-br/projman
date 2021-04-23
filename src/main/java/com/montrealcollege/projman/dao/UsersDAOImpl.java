package com.montrealcollege.projman.dao;

import com.montrealcollege.projman.model.Roles;
import com.montrealcollege.projman.model.Users;
import com.montrealcollege.projman.model.UsersRoles;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.*;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Join;
import javax.persistence.criteria.Root;
import java.util.List;

@Repository
public class UsersDAOImpl implements UsersDAO {

    @PersistenceUnit
    private EntityManagerFactory entityManagerFactory;

    @Override
    @Transactional
    public Users findUserAccount(String userName) {
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        try {
            String sql = "Select e from " + Users.class.getName() + " e "
                    + " Where e.userName = :userName ";

            javax.persistence.Query query = entityManager.createQuery(sql, Users.class);
            query.setParameter("userName", userName);

            return (Users) query.getSingleResult();
        } catch (NoResultException e) {
            return null;
        }
    }

    @Override
    public void createUser(Users user, Long roleId) {
//    public void createUser(Users user) {
        EntityManager entityManager = entityManagerFactory.createEntityManager();

        Roles role = new Roles();

        role.setRoleId(roleId);
        if (roleId == 1L)
            role.setRoleName("ROLE_ADMIN");
        else
            role.setRoleName("ROLE_USER");

        UsersRoles userRole = new UsersRoles();
        userRole.setUser(user);
        userRole.setRole(role);

//        user.getRolesSet().add(role);

        entityManager.getTransaction().begin();
        entityManager.persist(user);
        entityManager.persist(userRole);
        entityManager.getTransaction().commit();
    }

    @Override
    public List<Users> listUsers() {
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        CriteriaBuilder builder = entityManager.getCriteriaBuilder();
        CriteriaQuery<Users> criteria = builder.createQuery(Users.class);
        Root<Users> usersRoot = criteria.from(Users.class);
//        Join<Users, Roles> rolesRoot = usersRoot.join(usersRoot.get("id"));
        criteria.select(usersRoot).orderBy(
                builder.asc(usersRoot.get("firstName")),
                builder.asc(usersRoot.get("lastName"))
        );

        TypedQuery<Users> query = entityManager.createQuery(criteria);
        return query.getResultList();
    }

    @Override
    public Users displayUser(Long id) {
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        CriteriaBuilder builder = entityManager.getCriteriaBuilder();
        CriteriaQuery<Users> criteria = builder.createQuery(Users.class);
        Root<Users> usersRoot = criteria.from(Users.class);
        criteria.select(usersRoot).where(builder.equal(usersRoot.get("id"), id));

        TypedQuery<Users> query = entityManager.createQuery(criteria);
        return query.getSingleResult();
    }

    @Override
    public void updateUser(Users user) {

    }

    @Override
    public void deleteUser(Users user) {

    }
}
