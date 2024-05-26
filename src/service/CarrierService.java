package service;

import domain.Carrier;
import repository.GenericDao;
import repository.GenericDaoJpa;

import java.util.List;

public class CarrierService implements GenericDao<Carrier> {

    private final GenericDao<Carrier> carrierRepo;

    public CarrierService() {
        this.carrierRepo = new GenericDaoJpa<>(Carrier.class);
    }

    @Override
    public List<Carrier> findAll() {
        return carrierRepo.findAll();
    }

    @Override
    public <U> Carrier get(U id) {
        return carrierRepo.get(id);
    }

    @Override
    public Carrier update(Carrier carrier) {
        return carrierRepo.update(carrier);
    }

    @Override
    public void delete(Carrier carrier) {
        carrierRepo.delete(carrier);
    }

    @Override
    public void insert(Carrier carrier) {
        carrierRepo.insert(carrier);
    }

    @Override
    public <U> boolean exists(U id) {
        return carrierRepo.exists(id);
    }


}
