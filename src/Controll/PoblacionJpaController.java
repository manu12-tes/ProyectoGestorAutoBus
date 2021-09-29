/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controll;

import Controll.exceptions.NonexistentEntityException;
import java.io.Serializable;
import javax.persistence.Query;
import javax.persistence.EntityNotFoundException;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import Modelo.Corridas;
import Modelo.Poblacion;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

/**
 *
 * @author manel
 */
public class PoblacionJpaController implements Serializable {

    public PoblacionJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Poblacion poblacion) {
        if (poblacion.getCorridasCollection() == null) {
            poblacion.setCorridasCollection(new ArrayList<Corridas>());
        }
        if (poblacion.getCorridasCollection1() == null) {
            poblacion.setCorridasCollection1(new ArrayList<Corridas>());
        }
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Collection<Corridas> attachedCorridasCollection = new ArrayList<Corridas>();
            for (Corridas corridasCollectionCorridasToAttach : poblacion.getCorridasCollection()) {
                corridasCollectionCorridasToAttach = em.getReference(corridasCollectionCorridasToAttach.getClass(), corridasCollectionCorridasToAttach.getIdCorrida());
                attachedCorridasCollection.add(corridasCollectionCorridasToAttach);
            }
            poblacion.setCorridasCollection(attachedCorridasCollection);
            Collection<Corridas> attachedCorridasCollection1 = new ArrayList<Corridas>();
            for (Corridas corridasCollection1CorridasToAttach : poblacion.getCorridasCollection1()) {
                corridasCollection1CorridasToAttach = em.getReference(corridasCollection1CorridasToAttach.getClass(), corridasCollection1CorridasToAttach.getIdCorrida());
                attachedCorridasCollection1.add(corridasCollection1CorridasToAttach);
            }
            poblacion.setCorridasCollection1(attachedCorridasCollection1);
            em.persist(poblacion);
            for (Corridas corridasCollectionCorridas : poblacion.getCorridasCollection()) {
                Poblacion oldDestinoOfCorridasCollectionCorridas = corridasCollectionCorridas.getDestino();
                corridasCollectionCorridas.setDestino(poblacion);
                corridasCollectionCorridas = em.merge(corridasCollectionCorridas);
                if (oldDestinoOfCorridasCollectionCorridas != null) {
                    oldDestinoOfCorridasCollectionCorridas.getCorridasCollection().remove(corridasCollectionCorridas);
                    oldDestinoOfCorridasCollectionCorridas = em.merge(oldDestinoOfCorridasCollectionCorridas);
                }
            }
            for (Corridas corridasCollection1Corridas : poblacion.getCorridasCollection1()) {
                Poblacion oldOrigenOfCorridasCollection1Corridas = corridasCollection1Corridas.getOrigen();
                corridasCollection1Corridas.setOrigen(poblacion);
                corridasCollection1Corridas = em.merge(corridasCollection1Corridas);
                if (oldOrigenOfCorridasCollection1Corridas != null) {
                    oldOrigenOfCorridasCollection1Corridas.getCorridasCollection1().remove(corridasCollection1Corridas);
                    oldOrigenOfCorridasCollection1Corridas = em.merge(oldOrigenOfCorridasCollection1Corridas);
                }
            }
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Poblacion poblacion) throws NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Poblacion persistentPoblacion = em.find(Poblacion.class, poblacion.getIdPoblacion());
            Collection<Corridas> corridasCollectionOld = persistentPoblacion.getCorridasCollection();
            Collection<Corridas> corridasCollectionNew = poblacion.getCorridasCollection();
            Collection<Corridas> corridasCollection1Old = persistentPoblacion.getCorridasCollection1();
            Collection<Corridas> corridasCollection1New = poblacion.getCorridasCollection1();
            Collection<Corridas> attachedCorridasCollectionNew = new ArrayList<Corridas>();
            for (Corridas corridasCollectionNewCorridasToAttach : corridasCollectionNew) {
                corridasCollectionNewCorridasToAttach = em.getReference(corridasCollectionNewCorridasToAttach.getClass(), corridasCollectionNewCorridasToAttach.getIdCorrida());
                attachedCorridasCollectionNew.add(corridasCollectionNewCorridasToAttach);
            }
            corridasCollectionNew = attachedCorridasCollectionNew;
            poblacion.setCorridasCollection(corridasCollectionNew);
            Collection<Corridas> attachedCorridasCollection1New = new ArrayList<Corridas>();
            for (Corridas corridasCollection1NewCorridasToAttach : corridasCollection1New) {
                corridasCollection1NewCorridasToAttach = em.getReference(corridasCollection1NewCorridasToAttach.getClass(), corridasCollection1NewCorridasToAttach.getIdCorrida());
                attachedCorridasCollection1New.add(corridasCollection1NewCorridasToAttach);
            }
            corridasCollection1New = attachedCorridasCollection1New;
            poblacion.setCorridasCollection1(corridasCollection1New);
            poblacion = em.merge(poblacion);
            for (Corridas corridasCollectionOldCorridas : corridasCollectionOld) {
                if (!corridasCollectionNew.contains(corridasCollectionOldCorridas)) {
                    corridasCollectionOldCorridas.setDestino(null);
                    corridasCollectionOldCorridas = em.merge(corridasCollectionOldCorridas);
                }
            }
            for (Corridas corridasCollectionNewCorridas : corridasCollectionNew) {
                if (!corridasCollectionOld.contains(corridasCollectionNewCorridas)) {
                    Poblacion oldDestinoOfCorridasCollectionNewCorridas = corridasCollectionNewCorridas.getDestino();
                    corridasCollectionNewCorridas.setDestino(poblacion);
                    corridasCollectionNewCorridas = em.merge(corridasCollectionNewCorridas);
                    if (oldDestinoOfCorridasCollectionNewCorridas != null && !oldDestinoOfCorridasCollectionNewCorridas.equals(poblacion)) {
                        oldDestinoOfCorridasCollectionNewCorridas.getCorridasCollection().remove(corridasCollectionNewCorridas);
                        oldDestinoOfCorridasCollectionNewCorridas = em.merge(oldDestinoOfCorridasCollectionNewCorridas);
                    }
                }
            }
            for (Corridas corridasCollection1OldCorridas : corridasCollection1Old) {
                if (!corridasCollection1New.contains(corridasCollection1OldCorridas)) {
                    corridasCollection1OldCorridas.setOrigen(null);
                    corridasCollection1OldCorridas = em.merge(corridasCollection1OldCorridas);
                }
            }
            for (Corridas corridasCollection1NewCorridas : corridasCollection1New) {
                if (!corridasCollection1Old.contains(corridasCollection1NewCorridas)) {
                    Poblacion oldOrigenOfCorridasCollection1NewCorridas = corridasCollection1NewCorridas.getOrigen();
                    corridasCollection1NewCorridas.setOrigen(poblacion);
                    corridasCollection1NewCorridas = em.merge(corridasCollection1NewCorridas);
                    if (oldOrigenOfCorridasCollection1NewCorridas != null && !oldOrigenOfCorridasCollection1NewCorridas.equals(poblacion)) {
                        oldOrigenOfCorridasCollection1NewCorridas.getCorridasCollection1().remove(corridasCollection1NewCorridas);
                        oldOrigenOfCorridasCollection1NewCorridas = em.merge(oldOrigenOfCorridasCollection1NewCorridas);
                    }
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = poblacion.getIdPoblacion();
                if (findPoblacion(id) == null) {
                    throw new NonexistentEntityException("The poblacion with id " + id + " no longer exists.");
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
            Poblacion poblacion;
            try {
                poblacion = em.getReference(Poblacion.class, id);
                poblacion.getIdPoblacion();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The poblacion with id " + id + " no longer exists.", enfe);
            }
            Collection<Corridas> corridasCollection = poblacion.getCorridasCollection();
            for (Corridas corridasCollectionCorridas : corridasCollection) {
                corridasCollectionCorridas.setDestino(null);
                corridasCollectionCorridas = em.merge(corridasCollectionCorridas);
            }
            Collection<Corridas> corridasCollection1 = poblacion.getCorridasCollection1();
            for (Corridas corridasCollection1Corridas : corridasCollection1) {
                corridasCollection1Corridas.setOrigen(null);
                corridasCollection1Corridas = em.merge(corridasCollection1Corridas);
            }
            em.remove(poblacion);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<Poblacion> findPoblacionEntities() {
        return findPoblacionEntities(true, -1, -1);
    }

    public List<Poblacion> findPoblacionEntities(int maxResults, int firstResult) {
        return findPoblacionEntities(false, maxResults, firstResult);
    }

    private List<Poblacion> findPoblacionEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Poblacion.class));
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

    public Poblacion findPoblacion(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Poblacion.class, id);
        } finally {
            em.close();
        }
    }

    public int getPoblacionCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Poblacion> rt = cq.from(Poblacion.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
