package com.montrealcollege.projman.dao;

import com.montrealcollege.projman.model.Roles;
import com.montrealcollege.projman.model.Users;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.*;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
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
            CriteriaBuilder builder = entityManager.getCriteriaBuilder();
            CriteriaQuery<Users> criteria = builder.createQuery(Users.class);
            Root<Users> usersRoot = criteria.from(Users.class);
            criteria.select(usersRoot).where(builder.equal(usersRoot.get("userName"), userName));

            TypedQuery<Users> query = entityManager.createQuery(criteria);
            return query.getSingleResult();

        } catch (NoResultException e) {
            return null;
        }
    }

    @Override
    public void createUser(Users user) {
        EntityManager entityManager = entityManagerFactory.createEntityManager();

        entityManager.getTransaction().begin();
        entityManager.persist(user);
        entityManager.getTransaction().commit();
    }

    @Override
    public List<Users> listUsers() {
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        CriteriaBuilder builder = entityManager.getCriteriaBuilder();
        CriteriaQuery<Users> criteria = builder.createQuery(Users.class);
        Root<Users> usersRoot = criteria.from(Users.class);
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
        EntityManager entityManager = entityManagerFactory.createEntityManager();

        entityManager.getTransaction().begin();
        entityManager.merge(user);
        entityManager.getTransaction().commit();
    }

    @Override
    public void deleteUser(Users user) {

    }
}
