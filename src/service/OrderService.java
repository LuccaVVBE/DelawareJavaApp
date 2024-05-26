package service;

import domain.Order;
import repository.GenericDao;
import repository.GenericDaoJpa;

import java.util.List;

public class OrderService implements GenericDao<Order> {

    private final GenericDao<Order> orderRepo;

    public OrderService() {
        this.orderRepo = new GenericDaoJpa<>(Order.class);
    }

    @Override
    public List<Order> findAll() {
        return orderRepo.findAll();
    }

    @Override
    public <U> Order get(U id) {
        return orderRepo.get(id);
    }

    @Override
    public Order update(Order order) {
        return orderRepo.update(order);
    }

    @Override
    public void delete(Order order) {
        orderRepo.delete(order);
    }

    @Override
    public void insert(Order object) {
        orderRepo.insert(object);
    }

    @Override
    public <U> boolean exists(U id) {
        return orderRepo.exists(id);
    }
}
