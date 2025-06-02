package fr.diginamic.hello.dao;


import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.TypedQuery;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Classe abstraite parent pour tous les DAO
 * Contient les opérations CRUD communes
 * @param <T> Type de l'entité
 * @param <ID> Type de l'identifiant
 */
public abstract class AbstractDao<T, ID> {
    @PersistenceContext
    protected EntityManager entityManager;

    protected abstract Class<T> getEntityClass();

    protected String getEntityName() {
        return getEntityClass().getSimpleName();
    }

    @Transactional
    public List<T> findAll() {
        TypedQuery<T> query = entityManager.createQuery("SELECT e FROM " + getEntityName() + " e", getEntityClass());
        return query.getResultList();
    }

    /**
     * Trouve toutes les entités
     */
    @Transactional
    public T findById(ID id) {
        return entityManager.find(getEntityClass(), id);
    }

    /**
     * Trouve une entité par son ID
     */
    @Transactional
    public T findByNom(String nom) {
        TypedQuery<T> query = entityManager.createQuery("SELECT e FROM " + getEntityName() + " e WHERE LOWER(e.nom) = LOWER(:nom)", getEntityClass());
        query.setParameter("nom", nom);
        return query.getSingleResult();
    }

    /**
     * Sauvegarde ou met à jour une entité
     */
    @Transactional
    public T save(T entity) {
        try {
            java.lang.reflect.Method getIdMethod = getEntityClass().getMethod("getId");
            Object id = getIdMethod.invoke(entity);

            if (id == null || (id instanceof Number && ((Number) id).intValue() == 0)) {
                entityManager.persist(entity);
                return entity;
            } else {
                return entityManager.merge(entity);
            }
        } catch (Exception e) {
            throw new RuntimeException("Erreur lors de la sauvegarde", e);
        }
    }

    /**
     * Supprime une entité par son ID
     */
    @Transactional
    public boolean deleteById(ID id) {
        T entity = findById(id);
        if (entity != null) {
            entityManager.remove(entity);
            return true;
        }
        return false;
    }

    /**
     * Vérifie si une entité existe par son nom
     */
    @Transactional
    public boolean existsByNom(String nom) {
        TypedQuery<Long> query = entityManager.createQuery("SELECT COUNT(e) FROM " + getEntityName() + " e WHERE LOWER(e.nom) = LOWER(:nom)", Long.class);
        query.setParameter("nom", nom);
        return query.getSingleResult() > 0;
    }

    /**
     * Vérifie si une entité existe par son ID
     */
    @Transactional
    public boolean existsById(ID id) {
        TypedQuery<Long> query = entityManager.createQuery("SELECT COUNT(e) FROM " + getEntityName() + " e WHERE e.id = :id", Long.class);
        query.setParameter("id", id);
        return query.getSingleResult() > 0;
    }
}
