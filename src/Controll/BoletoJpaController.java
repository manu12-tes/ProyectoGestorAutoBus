/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controll;

import Controll.exceptions.NonexistentEntityException;
import Modelo.Boleto;
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
public class BoletoJpaController implements Serializable {

    public BoletoJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Boleto boleto) {
        if (boleto.getCorridasCollection() == null) {
            boleto.setCorridasCollection(new ArrayList<Corridas>());
        }
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Collection<Corridas> attachedCorridasCollection = new ArrayList<Corridas>();
            for (Corridas corridasCollectionCorridasToAttach : boleto.getCorridasCollection()) {
                corridasCollectionCorridasToAttach = em.getReference(corridasCollectionCorridasToAttach.getClass(), corridasCollectionCorridasToAttach.getIdCorrida());
                attachedCorridasCollection.add(corridasCollectionCorridasToAttach);
            }
            boleto.setCorridasCollection(attachedCorridasCollection);
            em.persist(boleto);
            for (Corridas corridasCollectionCorridas : boleto.getCorridasCollection()) {
                Boleto oldBoletoOfCorridasCollectionCorridas = corridasCollectionCorridas.getBoleto();
                corridasCollectionCorridas.setBoleto(boleto);
                corridasCollectionCorridas = em.merge(corridasCollectionCorridas);
                if (oldBoletoOfCorridasCollectionCorridas != null) {
                    oldBoletoOfCorridasCollectionCorridas.getCorridasCollection().remove(corridasCollectionCorridas);
                    oldBoletoOfCorridasCollectionCorridas = em.merge(oldBoletoOfCorridasCollectionCorridas);
                }
            }
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Boleto boleto) throws NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Boleto persistentBoleto = em.find(Boleto.class, boleto.getFolio());
            Collection<Corridas> corridasCollectionOld = persistentBoleto.getCorridasCollection();
            Collection<Corridas> corridasCollectionNew = boleto.getCorridasCollection();
            Collection<Corridas> attachedCorridasCollectionNew = new ArrayList<Corridas>();
            for (Corridas corridasCollectionNewCorridasToAttach : corridasCollectionNew) {
                corridasCollectionNewCorridasToAttach = em.getReference(corridasCollectionNewCorridasToAttach.getClass(), corridasCollectionNewCorridasToAttach.getIdCorrida());
                attachedCorridasCollectionNew.add(corridasCollectionNewCorridasToAttach);
            }
            corridasCollectionNew = attachedCorridasCollectionNew;
            boleto.setCorridasCollection(corridasCollectionNew);
            boleto = em.merge(boleto);
            for (Corridas corridasCollectionOldCorridas : corridasCollectionOld) {
                if (!corridasCollectionNew.contains(corridasCollectionOldCorridas)) {
                    corridasCollectionOldCorridas.setBoleto(null);
                    corridasCollectionOldCorridas = em.merge(corridasCollectionOldCorridas);
                }
            }
            for (Corridas corridasCollectionNewCorridas : corridasCollectionNew) {
                if (!corridasCollectionOld.contains(corridasCollectionNewCorridas)) {
                    Boleto oldBoletoOfCorridasCollectionNewCorridas = corridasCollectionNewCorridas.getBoleto();
                    corridasCollectionNewCorridas.setBoleto(boleto);
                    corridasCollectionNewCorridas = em.merge(corridasCollectionNewCorridas);
                    if (oldBoletoOfCorridasCollectionNewCorridas != null && !oldBoletoOfCorridasCollectionNewCorridas.equals(boleto)) {
                        oldBoletoOfCorridasCollectionNewCorridas.getCorridasCollection().remove(corridasCollectionNewCorridas);
                        oldBoletoOfCorridasCollectionNewCorridas = em.merge(oldBoletoOfCorridasCollectionNewCorridas);
                    }
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = boleto.getFolio();
                if (findBoleto(id) == null) {
                    throw new NonexistentEntityException("The boleto with id " + id + " no longer exists.");
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
            Boleto boleto;
            try {
                boleto = em.getReference(Boleto.class, id);
                boleto.getFolio();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The boleto with id " + id + " no longer exists.", enfe);
            }
            Collection<Corridas> corridasCollection = boleto.getCorridasCollection();
            for (Corridas corridasCollectionCorridas : corridasCollection) {
                corridasCollectionCorridas.setBoleto(null);
                corridasCollectionCorridas = em.merge(corridasCollectionCorridas);
            }
            em.remove(boleto);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<Boleto> findBoletoEntities() {
        return findBoletoEntities(true, -1, -1);
    }

    public List<Boleto> findBoletoEntities(int maxResults, int firstResult) {
        return findBoletoEntities(false, maxResults, firstResult);
    }

    private List<Boleto> findBoletoEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Boleto.class));
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

    public Boleto findBoleto(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Boleto.class, id);
        } finally {
            em.close();
        }
    }

    public int getBoletoCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Boleto> rt = cq.from(Boleto.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
