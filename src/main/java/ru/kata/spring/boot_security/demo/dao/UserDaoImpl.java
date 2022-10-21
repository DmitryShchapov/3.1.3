package ru.kata.spring.boot_security.demo.dao;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Repository;
import ru.kata.spring.boot_security.demo.entity.User;
import ru.kata.spring.boot_security.demo.service.RoleService;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import java.util.List;
import java.util.stream.Collectors;

@Repository
public class UserDaoImpl implements UserDao {

    @PersistenceContext
    private EntityManager entityManager;

    private final RoleService roleService;

    public UserDaoImpl(RoleService roleService) {
        this.roleService = roleService;
    }

    @Override
    public List<User> getAllUsers() {
        return entityManager.createQuery("from User", User.class).getResultList();
    }


    @Override
    public void saveUser(User user) {
        entityManager.persist(user);
    }

    @Override
    public void updateUser(User user) {
        entityManager.merge(user);
    }


    @Override
    public User getUser(long id) {
        return entityManager.find(User.class, id);
    }


    @Override
    public void deleteUserById(long id) {
        if (id != 0) {
            entityManager.remove(entityManager.find(User.class, id));
        }
    }

    @Override
    public UserDetails findUserByUsername(String username) {

        TypedQuery<User> query = (entityManager.createQuery("select user from User user " +
                "join fetch user.role where user.email = :username",User.class));
        query.setParameter("username",username);
        return query.getResultList().stream().findFirst().orElse(null);
    }


}
