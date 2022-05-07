package hiber.dao;

import hiber.model.Car;
import hiber.model.User;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import javax.persistence.TypedQuery;
import java.util.List;

@Repository
public class UserDaoImp implements UserDao {

    @Autowired
    private SessionFactory sessionFactory;

    @Override
    public void add(User user) {
        sessionFactory.getCurrentSession().save(user);
    }

    @Override
    @SuppressWarnings("unchecked")
    public List<User> listUsers() {
        TypedQuery<User> query = sessionFactory.getCurrentSession().createQuery("from User");
        return query.getResultList();
    }

    @Override
    public User getUserByCar(String model, int series) {
        TypedQuery<Car> carTypedQuery = sessionFactory.getCurrentSession().createQuery("FROM Car c WHERE c.model =:model AND c.series =:series")
                .setParameter("model", model)
                .setParameter("series", series);
        Car car = carTypedQuery.getSingleResult();
        TypedQuery<User> typedQuery = sessionFactory.getCurrentSession().createQuery("FROM User u WHERE u.car =:car")
                .setParameter("car", car);
        return typedQuery.getSingleResult();
    }
}
