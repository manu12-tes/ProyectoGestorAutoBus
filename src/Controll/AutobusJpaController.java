/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controll;

import Controll.exceptions.NonexistentEntityException;
import Modelo.Autobus;
import java.io.Serializable;
import javax.persistence.Query;
import javax.persistence.EntityNotFoundException;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import Modelo.Corridas;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

/**
 *
 * @author manel
 */
public class AutobusJpaController implements Serializable {

    public AutobusJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Autobus autobus) {
        if (autobus.getCorridasCollection() == null) {
            autobus.setCorridasCollection(new ArrayList<Corridas>());
        }
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Collection<Corridas> attachedCorridasCollection = new ArrayList<Corridas>();
            for (Corridas corridasCollectionCorridasToAttach : autobus.getCorridasCollection()) {
                corridasCollectionCorridasToAttach = em.getReference(corridasCollectionCorridasToAttach.getClass(), corridasCollectionCorridasToAttach.getIdCorrida());
                attachedCorridasCollection.add(corridasCollectionCorridasToAttach);
            }
            autobus.setCorridasCollection(attachedCorridasCollection);
            em.persist(autobus);
            for (Corridas corridasCollectionCorridas : autobus.getCorridasCollection()) {
                Autobus oldAutobusOfCorridasCollectionCorridas = corridasCollectionCorridas.getAutobus();
                corridasCollectionCorridas.setAutobus(autobus);
                corridasCollectionCorridas = em.merge(corridasCollectionCorridas);
                if (oldAutobusOfCorridasCollectionCorridas != null) {
                    oldAutobusOfCorridasCollectionCorridas.getCorridasCollection().remove(corridasCollectionCorridas);
                    oldAutobusOfCorridasCollectionCorridas = em.merge(oldAutobusOfCorridasCollectionCorridas);
                }
            }
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Autobus autobus) throws NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Autobus persistentAutobus = em.find(Autobus.class, autobus.getIdClase());
            Collection<Corridas> corridasCollectionOld = persistentAutobus.getCorridasCollection();
            Collection<Corridas> corridasCollectionNew = autobus.getCorridasCollection();
            Collection<Corridas> attachedCorridasCollectionNew = new ArrayList<Corridas>();
            for (Corridas corridasCollectionNewCorridasToAttach : corridasCollectionNew) {
                corridasCollectionNewCorridasToAttach = em.getReference(corridasCollectionNewCorridasToAttach.getClass(), corridasCollectionNewCorridasToAttach.getIdCorrida());
                attachedCorridasCollectionNew.add(corridasCollectionNewCorridasToAttach);
            }
            corridasCollectionNew = attachedCorridasCollectionNew;
            autobus.setCorridasCollection(corridasCollectionNew);
            autobus = em.merge(autobus);
            for (Corridas corridasCollectionOldCorridas : corridasCollectionOld) {
                if (!corridasCollectionNew.contains(corridasCollectionOldCorridas)) {
                    corridasCollectionOldCorridas.setAutobus(null);
                    corridasCollectionOldCorridas = em.merge(corridasCollectionOldCorridas);
                }
            }
            for (Corridas corridasCollectionNewCorridas : corridasCollectionNew) {
                if (!corridasCollectionOld.contains(corridasCollectionNewCorridas)) {
                    Autobus oldAutobusOfCorridasCollectionNewCorridas = corridasCollectionNewCorridas.getAutobus();
                    corridasCollectionNewCorridas.setAutobus(autobus);
                    corridasCollectionNewCorridas = em.merge(corridasCollectionNewCorridas);
                    if (oldAutobusOfCorridasCollectionNewCorridas != null && !oldAutobusOfCorridasCollectionNewCorridas.equals(autobus)) {
                        oldAutobusOfCorridasCollectionNewCorridas.getCorridasCollection().remove(corridasCollectionNewCorridas);
                        oldAutobusOfCorridasCollectionNewCorridas = em.merge(oldAutobusOfCorridasCollectionNewCorridas);
                    }
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = autobus.getIdClase();
                if (findAutobus(id) == null) {
                    throw new NonexistentEntityException("The autobus with id " + id + " no longer exists.");
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
            Autobus autobus;
            try {
                autobus = em.getReference(Autobus.class, id);
                autobus.getIdClase();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The autobus with id " + id + " no longer exists.", enfe);
            }
            Collection<Corridas> corridasCollection = autobus.getCorridasCollection();
            for (Corridas corridasCollectionCorridas : corridasCollection) {
                corridasCollectionCorridas.setAutobus(null);
                corridasCollectionCorridas = em.merge(corridasCollectionCorridas);
            }
            em.remove(autobus);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<Autobus> findAutobusEntities() {
        return findAutobusEntities(true, -1, -1);
    }

    public List<Autobus> findAutobusEntities(int maxResults, int firstResult) {
        return findAutobusEntities(false, maxResults, firstResult);
    }

    private List<Autobus> findAutobusEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Autobus.class));
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

    public Autobus findAutobus(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Autobus.class, id);
        } finally {
            em.close();
        }
    }

    public int getAutobusCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Autobus> rt = cq.from(Autobus.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
