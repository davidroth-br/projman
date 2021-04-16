package com.montrealcollege.projman.service;

import com.montrealcollege.projman.dao.RolesDAOImpl;
import com.montrealcollege.projman.dao.UsersDAOImpl;
import com.montrealcollege.projman.model.Users;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {

    @Autowired
    private UsersDAOImpl usersDAO;

    @Autowired
    private RolesDAOImpl rolesDAO;

    @Override
    public UserDetails loadUserByUsername(String userName) throws UsernameNotFoundException {
        Users user = this.usersDAO.findUserAccount(userName);

        if (user == null) {
            System.out.println("User not found! " + userName);
            throw new UsernameNotFoundException("User " + userName + " was not found in the database");
        }

        System.out.println("Found User: " + user);

        // [ROLE_USER, ROLE_ADMIN,..]
        List<String> roleNames = this.rolesDAO.getRoleNames(user.getId());

        List<GrantedAuthority> grantList = new ArrayList<GrantedAuthority>();
        if (roleNames != null) {
            for (String role : roleNames) {
                // ROLE_USER, ROLE_ADMIN,..
                GrantedAuthority authority = new SimpleGrantedAuthority(role);
                grantList.add(authority);
            }
        }

        UserDetails userDetails = (UserDetails) new User(user.getUserName(),
                user.getEncryptedPassword(), grantList);

        return userDetails;
    }
}
