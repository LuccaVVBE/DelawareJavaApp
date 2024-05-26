package service;

import domain.Box;
import repository.GenericDao;
import repository.GenericDaoJpa;

import java.util.List;

public class BoxService implements GenericDao<Box> {
    private final GenericDao<Box> boxRepo;

    public BoxService() {
        this.boxRepo = new GenericDaoJpa<>(Box.class);
    }

    @Override
    public List<Box> findAll() {
        return boxRepo.findAll();
    }

    @Override
    public <U> Box get(U id) {
        return boxRepo.get(id);
    }

    @Override
    public Box update(Box box) {
        return boxRepo.update(box);
    }

    @Override
    public void delete(Box object) {
        boxRepo.delete(object);
    }

    @Override
    public void insert(Box object) {
        boxRepo.insert(object);
    }

    @Override
    public <U> boolean exists(U id) {
        return boxRepo.exists(id);
    }

}
