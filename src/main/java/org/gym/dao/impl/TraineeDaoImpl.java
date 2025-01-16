package org.gym.dao.impl;

import jakarta.annotation.Resource;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.gym.dao.TraineeDao;
import org.gym.entity.Trainee;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
@RequiredArgsConstructor
public class TraineeDaoImpl implements TraineeDao {
    //private final SessionFactory sessionFactory;
    @PersistenceContext(unitName="entityManager")
    //@Resource("entityManager")
    //@Qualifier("entityManager")
    private final EntityManager entityManager;

    @Override
    public List<Trainee> findAll() {
        return null;// traineeStorage.findAll();
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED)
    public Trainee findById(Long id) {
//        Query<Trainee> query = getSession()
//                .createQuery("from Trainee t where t.id = :ID", Trainee.class);
//        query.setParameter("ID", id);
        return null;// query.uniqueResult();
    }

//    @Override
//    public Trainee save(Trainee trainee) {
//        return traineeStorage.save(trainee);
//    }

    @Transactional//(propagation = Propagation.REQUIRED)
    public Trainee save(Trainee trainee) {
//        getSession().persist(trainee);
//        return trainee;
        if (trainee == null || trainee.getUser() == null) {
            throw new IllegalArgumentException("Entity cannot be null");
        }
        if (trainee.getUser().getUserName() == null || trainee.getUser().getUserName().isEmpty()) {
            throw new IllegalArgumentException("Username cannot be null or empty");
        }
        if (trainee.getId() == null) {
            entityManager.persist(trainee);
        } else {
            trainee = entityManager.merge(trainee);
        }
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

//    private Session getSession() {
//        return sessionFactory.getCurrentSession();
//    }
}
