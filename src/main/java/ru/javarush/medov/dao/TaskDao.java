package ru.javarush.medov.dao;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import ru.javarush.medov.domain.Status;
import ru.javarush.medov.domain.Task;

import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.Queue;

@Repository
@Transactional(readOnly = true)
public class TaskDao {

    private final SessionFactory sessionFactory;

    public TaskDao(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    public List<Task> getAll(int offset, int limit) {
        Query<Task> query = getSession().createQuery("from Task", Task.class);
        query.setFirstResult(offset);
        query.setMaxResults(limit);
        return query.list();
    }


    public int getAllCount(){
        Query<Long> query = getSession().createQuery("select count(t) from Task t", Long.class);
        return Math.toIntExact(query.getSingleResult());
    }

    public Task getById(Integer id){
        Query<Task> query = getSession().createQuery("from Task t where t.id = :ID", Task.class);
        query.setParameter("ID", id);
        return query.getSingleResult();
    }


    public void saveOrUpdate(Task task){
        getSession().persist(task);
    }


    public void delete(Integer id){
        getSession().remove(getById(id));
    }

    private Session getSession(){
        return sessionFactory.getCurrentSession();
    }

    public List<String> getStatusList(){
        return Arrays.stream(Status.values()).map(String::valueOf).toList();
    }

}
