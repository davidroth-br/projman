package com.montrealcollege.projman.dao;

//import com.montrealcollege.projman.model.AppUser;
import com.montrealcollege.projman.model.Users;
import org.hibernate.Session;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Join;
import javax.persistence.criteria.Root;
import java.util.List;

@Repository
@Transactional
public class UsersDAOImpl implements UsersDAO {

    @PersistenceContext
//    @Autowired
    private EntityManager entityManager;

    public Users findUserAccount(String userName) {
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
    public void createUser(Users user) {

    }

    @Override
    public List<Object[]> listUsers() {
        Session session = entityManager.unwrap(Session.class);
        CriteriaBuilder builder = session.getCriteriaBuilder();
        CriteriaQuery<Object[]> criteria = builder.createQuery(Object[].class);
        Root<Users> usersRoot = criteria.from(Users.class);
//        Join<AppUser, Users> usersRoot = appUserRoot.join("users");
        criteria.multiselect(usersRoot.get("id"),
                usersRoot.get("userName"),
                usersRoot.get("encryptedPassword"),
                usersRoot.get("firstName"),
                usersRoot.get("lastName"),
                usersRoot.get("email"),
                usersRoot.get("phone")
        );

        Query<Object[]> query = session.createQuery(criteria);
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
