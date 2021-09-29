/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controll;

import Controll.exceptions.NonexistentEntityException;
import Modelo.Boletos;
import java.io.Serializable;
import javax.persistence.Query;
import javax.persistence.EntityNotFoundException;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import Modelo.Corridas;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

/**
 *
 * @author manel
 */
public class BoletosJpaController implements Serializable {

    public BoletosJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Boletos boletos) {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Corridas corrida = boletos.getCorrida();
            if (corrida != null) {
                corrida = em.getReference(corrida.getClass(), corrida.getIdCorrida());
                boletos.setCorrida(corrida);
            }
            em.persist(boletos);
            if (corrida != null) {
                corrida.getBoletosCollection().add(boletos);
                corrida = em.merge(corrida);
            }
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Boletos boletos) throws NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Boletos persistentBoletos = em.find(Boletos.class, boletos.getFolio());
            Corridas corridaOld = persistentBoletos.getCorrida();
            Corridas corridaNew = boletos.getCorrida();
            if (corridaNew != null) {
                corridaNew = em.getReference(corridaNew.getClass(), corridaNew.getIdCorrida());
                boletos.setCorrida(corridaNew);
            }
            boletos = em.merge(boletos);
            if (corridaOld != null && !corridaOld.equals(corridaNew)) {
                corridaOld.getBoletosCollection().remove(boletos);
                corridaOld = em.merge(corridaOld);
            }
            if (corridaNew != null && !corridaNew.equals(corridaOld)) {
                corridaNew.getBoletosCollection().add(boletos);
                corridaNew = em.merge(corridaNew);
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = boletos.getFolio();
                if (findBoletos(id) == null) {
                    throw new NonexistentEntityException("The boletos with id " + id + " no longer exists.");
                }
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void destroy(Integer id) throws NonexistentEntityException {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Boletos boletos;
            try {
                boletos = em.getReference(Boletos.class, id);
                boletos.getFolio();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The boletos with id " + id + " no longer exists.", enfe);
            }
            Corridas corrida = boletos.getCorrida();
            if (corrida != null) {
                corrida.getBoletosCollection().remove(boletos);
                corrida = em.merge(corrida);
            }
            em.remove(boletos);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<Boletos> findBoletosEntities() {
        return findBoletosEntities(true, -1, -1);
    }

    public List<Boletos> findBoletosEntities(int maxResults, int firstResult) {
        return findBoletosEntities(false, maxResults, firstResult);
    }

    private List<Boletos> findBoletosEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Boletos.class));
            Query q = em.createQuery(cq);
            if (!all) {
                q.setMaxResults(maxResults);
                q.setFirstResult(firstResult);
            }
            return q.getResultList();
        } finally {
            em.close();
        }
    }

    public Boletos findBoletos(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Boletos.class, id);
        } finally {
            em.close();
        }
    }

    public int getBoletosCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Boletos> rt = cq.from(Boletos.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
