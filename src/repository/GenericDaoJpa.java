package repository;

import io.github.cdimascio.dotenv.Dotenv;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class GenericDaoJpa<T> implements GenericDao<T> {
    private static final String PU_NAME = "default";
    private static final Map<String, Object> configOverrides = new HashMap<String, Object>();

//    private static final EntityManagerFactory emf = Persistence.createEntityManagerFactory(PU_NAME, configOverrides);
//    protected static final EntityManager em = emf.createEntityManager();
    protected static EntityManager em;
    private static EntityManagerFactory emf;
    private final Class<T> type;

    public GenericDaoJpa(Class<T> type) {
        Dotenv dotenv = Dotenv.load();
        configOverrides.put("javax.persistence.jdbc.password", dotenv.get("DB_PASSWORD"));
        configOverrides.put("javax.persistence.jdbc.user", dotenv.get("DB_USER"));
        configOverrides.put("javax.persistence.jdbc.url", dotenv.get("DB_URL"));
        emf = Persistence.createEntityManagerFactory(PU_NAME, configOverrides);
        em = emf.createEntityManager();
        this.type = type;
    }

    public static void closePersistency() {
        em.close();
        emf.close();
    }

    public static void startTransaction() {
        em.getTransaction().begin();
    }

    public static void commitTransaction() {
        em.getTransaction().commit();
    }

    public static void rollbackTransaction() {
        em.getTransaction().rollback();
    }

    @Override
    public List<T> findAll() {
        //return em.createNamedQuery(type.getName()+".findAll", type).getResultList();
        em.clear();
        return em.createQuery("select entity from " + type.getName() + " entity", type).getResultList();
    }

    @Override
    public <U> T get(U id) {
        T entity = em.find(type, id);
        return entity;
    }

    @Override
    public T update(T object) {
        startTransaction();
        var e = em.merge(object);
        commitTransaction();
        return e;

//        em.getTransaction().begin();
//        T updatedObject = em.merge(object);
//        em.getTransaction().commit();
//        return updatedObject;
    }

    @Override
    public void delete(T object) {
        em.remove(em.merge(object));
    }

    @Override
    public void insert(T object) {
//        startTransaction();
        em.persist(object);
//        commitTransaction();
    }

    @Override
    public <U> boolean exists(U id) {
        T entity = em.find(type, id);
        return entity != null;
    }


}
