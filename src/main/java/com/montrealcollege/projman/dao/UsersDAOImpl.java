package com.montrealcollege.projman.dao;

import com.montrealcollege.projman.model.Users;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.*;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import java.util.List;

@Repository
@Transactional
public class UsersDAOImpl implements UsersDAO {

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public Users findUserAccount(String userName) {
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
    public Users findCurrentUser() {
        return findUserAccount(((UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getUsername());
    }

    @Override
    public void createUser(Users user) {
        entityManager.persist(user);
    }

    @Override
    public List<Users> listUsers() {
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
    public Users findUserById(Long id) {
        return entityManager.find(Users.class, id);
    }

    @Override
    public void updateUser(Users user) {
        Users dbUser = entityManager.find(Users.class, user.getId());

        dbUser.setUserName(user.getUserName());
        dbUser.setEncryptedPassword(user.getEncryptedPassword());
        dbUser.setFirstName(user.getFirstName());
        dbUser.setLastName(user.getLastName());
        dbUser.setEmail(user.getEmail());
        dbUser.setPhone(user.getPhone());
        dbUser.setProjects(user.getProjects());
        dbUser.setTasks(user.getTasks());
        dbUser.setRole(user.getRole());
        dbUser.setEnabled(user.isEnabled());

        entityManager.persist(dbUser);
        entityManager.flush();
    }

    @Override
    public void deleteUser(Long id) {
        Users user = entityManager.find(Users.class, id);
        entityManager.remove(user);
    }
}
