package service;

import domain.Company;
import repository.GenericDao;
import repository.GenericDaoJpa;

import java.util.List;

//public class CompanyService implements CompanyDao {
public class CompanyService implements GenericDao<Company> {

    //    private CompanyDao companyRepo;
    private GenericDao<Company> companyRepo;

    public CompanyService() {
//        this.companyRepo = new CompanyRepo();
        this.companyRepo = new GenericDaoJpa<>(Company.class);
    }

    @Override
    public List<Company> findAll() {
        return companyRepo.findAll();
    }

    @Override
    public <U> Company get(U id) {
        return companyRepo.get(id);
    }

    @Override
    public Company update(Company company) {
        return companyRepo.update(company);
    }

    @Override
    public void delete(Company company) {
        companyRepo.delete(company);
    }

    @Override
    public void insert(Company company) {
        companyRepo.insert(company);
    }

    @Override
    public <U> boolean exists(U id) {
        return companyRepo.exists(id);
    }

}

