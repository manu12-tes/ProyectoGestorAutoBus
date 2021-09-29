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
import Modelo.Autobus;
import Modelo.Poblacion;
import Modelo.Boleto;
import Modelo.Boletos;
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
public class CorridasJpaController implements Serializable {

    public CorridasJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Corridas corridas) {
        if (corridas.getBoletosCollection() == null) {
            corridas.setBoletosCollection(new ArrayList<Boletos>());
        }
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Autobus autobus = corridas.getAutobus();
            if (autobus != null) {
                autobus = em.getReference(autobus.getClass(), autobus.getIdClase());
                corridas.setAutobus(autobus);
            }
            Poblacion destino = corridas.getDestino();
            if (destino != null) {
                destino = em.getReference(destino.getClass(), destino.getIdPoblacion());
                corridas.setDestino(destino);
            }
            Poblacion origen = corridas.getOrigen();
            if (origen != null) {
                origen = em.getReference(origen.getClass(), origen.getIdPoblacion());
                corridas.setOrigen(origen);
            }
            Boleto boleto = corridas.getBoleto();
            if (boleto != null) {
                boleto = em.getReference(boleto.getClass(), boleto.getFolio());
                corridas.setBoleto(boleto);
            }
            Collection<Boletos> attachedBoletosCollection = new ArrayList<Boletos>();
            for (Boletos boletosCollectionBoletosToAttach : corridas.getBoletosCollection()) {
                boletosCollectionBoletosToAttach = em.getReference(boletosCollectionBoletosToAttach.getClass(), boletosCollectionBoletosToAttach.getFolio());
                attachedBoletosCollection.add(boletosCollectionBoletosToAttach);
            }
            corridas.setBoletosCollection(attachedBoletosCollection);
            em.persist(corridas);
            if (autobus != null) {
                autobus.getCorridasCollection().add(corridas);
                autobus = em.merge(autobus);
            }
            if (destino != null) {
                destino.getCorridasCollection().add(corridas);
                destino = em.merge(destino);
            }
            if (origen != null) {
                origen.getCorridasCollection().add(corridas);
                origen = em.merge(origen);
            }
            if (boleto != null) {
                boleto.getCorridasCollection().add(corridas);
                boleto = em.merge(boleto);
            }
            for (Boletos boletosCollectionBoletos : corridas.getBoletosCollection()) {
                Corridas oldCorridaOfBoletosCollectionBoletos = boletosCollectionBoletos.getCorrida();
                boletosCollectionBoletos.setCorrida(corridas);
                boletosCollectionBoletos = em.merge(boletosCollectionBoletos);
                if (oldCorridaOfBoletosCollectionBoletos != null) {
                    oldCorridaOfBoletosCollectionBoletos.getBoletosCollection().remove(boletosCollectionBoletos);
                    oldCorridaOfBoletosCollectionBoletos = em.merge(oldCorridaOfBoletosCollectionBoletos);
                }
            }
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Corridas corridas) throws NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Corridas persistentCorridas = em.find(Corridas.class, corridas.getIdCorrida());
            Autobus autobusOld = persistentCorridas.getAutobus();
            Autobus autobusNew = corridas.getAutobus();
            Poblacion destinoOld = persistentCorridas.getDestino();
            Poblacion destinoNew = corridas.getDestino();
            Poblacion origenOld = persistentCorridas.getOrigen();
            Poblacion origenNew = corridas.getOrigen();
            Boleto boletoOld = persistentCorridas.getBoleto();
            Boleto boletoNew = corridas.getBoleto();
            Collection<Boletos> boletosCollectionOld = persistentCorridas.getBoletosCollection();
            Collection<Boletos> boletosCollectionNew = corridas.getBoletosCollection();
            if (autobusNew != null) {
                autobusNew = em.getReference(autobusNew.getClass(), autobusNew.getIdClase());
                corridas.setAutobus(autobusNew);
            }
            if (destinoNew != null) {
                destinoNew = em.getReference(destinoNew.getClass(), destinoNew.getIdPoblacion());
                corridas.setDestino(destinoNew);
            }
            if (origenNew != null) {
                origenNew = em.getReference(origenNew.getClass(), origenNew.getIdPoblacion());
                corridas.setOrigen(origenNew);
            }
            if (boletoNew != null) {
                boletoNew = em.getReference(boletoNew.getClass(), boletoNew.getFolio());
                corridas.setBoleto(boletoNew);
            }
            Collection<Boletos> attachedBoletosCollectionNew = new ArrayList<Boletos>();
            for (Boletos boletosCollectionNewBoletosToAttach : boletosCollectionNew) {
                boletosCollectionNewBoletosToAttach = em.getReference(boletosCollectionNewBoletosToAttach.getClass(), boletosCollectionNewBoletosToAttach.getFolio());
                attachedBoletosCollectionNew.add(boletosCollectionNewBoletosToAttach);
            }
            boletosCollectionNew = attachedBoletosCollectionNew;
            corridas.setBoletosCollection(boletosCollectionNew);
            corridas = em.merge(corridas);
            if (autobusOld != null && !autobusOld.equals(autobusNew)) {
                autobusOld.getCorridasCollection().remove(corridas);
                autobusOld = em.merge(autobusOld);
            }
            if (autobusNew != null && !autobusNew.equals(autobusOld)) {
                autobusNew.getCorridasCollection().add(corridas);
                autobusNew = em.merge(autobusNew);
            }
            if (destinoOld != null && !destinoOld.equals(destinoNew)) {
                destinoOld.getCorridasCollection().remove(corridas);
                destinoOld = em.merge(destinoOld);
            }
            if (destinoNew != null && !destinoNew.equals(destinoOld)) {
                destinoNew.getCorridasCollection().add(corridas);
                destinoNew = em.merge(destinoNew);
            }
            if (origenOld != null && !origenOld.equals(origenNew)) {
                origenOld.getCorridasCollection().remove(corridas);
                origenOld = em.merge(origenOld);
            }
            if (origenNew != null && !origenNew.equals(origenOld)) {
                origenNew.getCorridasCollection().add(corridas);
                origenNew = em.merge(origenNew);
            }
            if (boletoOld != null && !boletoOld.equals(boletoNew)) {
                boletoOld.getCorridasCollection().remove(corridas);
                boletoOld = em.merge(boletoOld);
            }
            if (boletoNew != null && !boletoNew.equals(boletoOld)) {
                boletoNew.getCorridasCollection().add(corridas);
                boletoNew = em.merge(boletoNew);
            }
            for (Boletos boletosCollectionOldBoletos : boletosCollectionOld) {
                if (!boletosCollectionNew.contains(boletosCollectionOldBoletos)) {
                    boletosCollectionOldBoletos.setCorrida(null);
                    boletosCollectionOldBoletos = em.merge(boletosCollectionOldBoletos);
                }
            }
            for (Boletos boletosCollectionNewBoletos : boletosCollectionNew) {
                if (!boletosCollectionOld.contains(boletosCollectionNewBoletos)) {
                    Corridas oldCorridaOfBoletosCollectionNewBoletos = boletosCollectionNewBoletos.getCorrida();
                    boletosCollectionNewBoletos.setCorrida(corridas);
                    boletosCollectionNewBoletos = em.merge(boletosCollectionNewBoletos);
                    if (oldCorridaOfBoletosCollectionNewBoletos != null && !oldCorridaOfBoletosCollectionNewBoletos.equals(corridas)) {
                        oldCorridaOfBoletosCollectionNewBoletos.getBoletosCollection().remove(boletosCollectionNewBoletos);
                        oldCorridaOfBoletosCollectionNewBoletos = em.merge(oldCorridaOfBoletosCollectionNewBoletos);
                    }
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = corridas.getIdCorrida();
                if (findCorridas(id) == null) {
                    throw new NonexistentEntityException("The corridas with id " + id + " no longer exists.");
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
            Corridas corridas;
            try {
                corridas = em.getReference(Corridas.class, id);
                corridas.getIdCorrida();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The corridas with id " + id + " no longer exists.", enfe);
            }
            Autobus autobus = corridas.getAutobus();
            if (autobus != null) {
                autobus.getCorridasCollection().remove(corridas);
                autobus = em.merge(autobus);
            }
            Poblacion destino = corridas.getDestino();
            if (destino != null) {
                destino.getCorridasCollection().remove(corridas);
                destino = em.merge(destino);
            }
            Poblacion origen = corridas.getOrigen();
            if (origen != null) {
                origen.getCorridasCollection().remove(corridas);
                origen = em.merge(origen);
            }
            Boleto boleto = corridas.getBoleto();
            if (boleto != null) {
                boleto.getCorridasCollection().remove(corridas);
                boleto = em.merge(boleto);
            }
            Collection<Boletos> boletosCollection = corridas.getBoletosCollection();
            for (Boletos boletosCollectionBoletos : boletosCollection) {
                boletosCollectionBoletos.setCorrida(null);
                boletosCollectionBoletos = em.merge(boletosCollectionBoletos);
            }
            em.remove(corridas);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<Corridas> findCorridasEntities() {
        return findCorridasEntities(true, -1, -1);
    }

    public List<Corridas> findCorridasEntities(int maxResults, int firstResult) {
        return findCorridasEntities(false, maxResults, firstResult);
    }

    private List<Corridas> findCorridasEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Corridas.class));
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

    public Corridas findCorridas(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Corridas.class, id);
        } finally {
            em.close();
        }
    }

    public int getCorridasCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Corridas> rt = cq.from(Corridas.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
