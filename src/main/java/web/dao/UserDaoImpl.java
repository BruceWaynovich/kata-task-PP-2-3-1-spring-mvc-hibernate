package web.dao;

import org.springframework.stereotype.Repository;
import web.model.User;

import javax.persistence.EntityManager;
import javax.persistence.EntityNotFoundException;
import javax.persistence.PersistenceContext;

import java.util.List;

@Repository
public class UserDaoImpl implements UserDao {
    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public List<User> getAllUsers() {
        return entityManager.createQuery("SELECT u FROM User u", User.class).getResultList();
    }

    @Override
    public void save(User user) {
        entityManager.persist(user);
    }

    @Override
    public User getUserById(int id) {
        User user = entityManager.find(User.class, id);
        if (user == null) {
            throw new EntityNotFoundException("Пользователь с ID " + id + " не найден");
        }
        return user;
    }

    @Override
    public void update(User user) {
        getUserById(user.getId());
        entityManager.merge(user);
    }

    @Override
    public void delete(int id) {
        User userToDelete = getUserById(id);
        entityManager.remove(userToDelete);
    }
}
