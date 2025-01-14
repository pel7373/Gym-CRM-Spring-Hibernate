package org.gym.dao.impl;

import lombok.AllArgsConstructor;
import org.gym.dao.TraineeDao;
import org.gym.entity.Trainee;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
@AllArgsConstructor
public class TraineeDaoImpl implements TraineeDao {
    private final SessionFactory sessionFactory;

    @Override
    public List<Trainee> findAll() {
        return null;// traineeStorage.findAll();
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED)
    public Trainee findById(Long id) {
        Query<Trainee> query = getSession()
                .createQuery("from Trainee t where t.id = :ID", Trainee.class);
        query.setParameter("ID", id);
        return query.uniqueResult();
    }

//    @Override
//    public Trainee save(Trainee trainee) {
//        return traineeStorage.save(trainee);
//    }

    @Transactional(propagation = Propagation.REQUIRED)
    public Trainee save(Trainee trainee) {
        getSession().persist(trainee);
        return trainee;
    }

    @Override
    public Trainee update(Long id, Trainee trainee) {
        return trainee;//traineeStorage.update(id, trainee);
    }

    @Override
    public void deleteById(Long id) {
        //traineeStorage.deleteById(id);
    }

    @Override
    public boolean isUserNameExists(String userName) {
        return false;// traineeStorage.isUserNameExist(userName);
    }

    private Session getSession() {
        return sessionFactory.getCurrentSession();
    }
}
