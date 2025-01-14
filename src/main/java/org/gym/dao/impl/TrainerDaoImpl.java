package org.gym.dao.impl;

import lombok.AllArgsConstructor;
import org.gym.dao.TrainerDao;
import org.gym.entity.Trainee;
import org.gym.entity.Trainer;
import org.gym.exception.EntityNotFoundException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
@AllArgsConstructor
public class TrainerDaoImpl implements TrainerDao {
    private final SessionFactory sessionFactory;


    @Override
    public List<Trainer> findAll() {
        return null;// trainerStorage.findAll();
    }

    @Override
    public Trainer findById(Long id) throws EntityNotFoundException {
        return new Trainer();// trainerStorage.findById(id);
    }

    @Transactional(propagation = Propagation.REQUIRED)
    public Trainer save(Trainer trainer) {
        getSession().persist(trainer);
        return trainer;
    }

    @Override
    public Trainer update(Long id, Trainer trainer) {
        return trainer;// trainerStorage.update(id, trainer);
    }

    @Override
    public boolean isUserNameExists(String userName) {
        return false;// trainerStorage.isUserNameExist(userName);
    }

    private Session getSession() {
        return sessionFactory.getCurrentSession();
    }
}
