package com.montrealcollege.projman.dao;

import com.montrealcollege.projman.model.Users;
import org.hibernate.Session;
import org.hibernate.query.Query;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import java.util.List;

@Repository
public class UsersDAOImpl implements UsersDAO {

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public void createUser(Users user) {

    }

    @Override
    public List<Users> listUsers() {
        Session session = entityManager.unwrap(Session.class);
        CriteriaBuilder criteriaBuilder = session.getCriteriaBuilder();
        CriteriaQuery<Users> criteriaQuery = criteriaBuilder.createQuery(Users.class);
        Root<Users> root = criteriaQuery.from(Users.class);
        criteriaQuery.select(root);

        Query<Users> query = session.createQuery(criteriaQuery);
        return query.list();
    }

    @Override
    public Users displayUser(Integer id) {
        return null;
    }

    @Override
    public void updateUser(Users user) {

    }

    @Override
    public void deleteUser(Users user) {

    }
}
