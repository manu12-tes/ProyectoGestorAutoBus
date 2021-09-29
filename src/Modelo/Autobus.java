/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Modelo;

import java.io.Serializable;
import java.util.Collection;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author manel
 */
@Entity
@Table(name = "AUTOBUS")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Autobus.findAll", query = "SELECT a FROM Autobus a"),
    @NamedQuery(name = "Autobus.findByIdClase", query = "SELECT a FROM Autobus a WHERE a.idClase = :idClase"),
    @NamedQuery(name = "Autobus.findByNombre", query = "SELECT a FROM Autobus a WHERE a.nombre = :nombre"),
    @NamedQuery(name = "Autobus.findByNumAisentos", query = "SELECT a FROM Autobus a WHERE a.numAisentos = :numAisentos")})
public class Autobus implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "ID_CLASE")
    private Integer idClase;
    @Column(name = "NOMBRE")
    private String nombre;
    @Column(name = "NUM_AISENTOS")
    private Integer numAisentos;
    @OneToMany(mappedBy = "autobus")
    private Collection<Corridas> corridasCollection;

    public Autobus() {
    }

    public Autobus(Integer idClase) {
        this.idClase = idClase;
    }

    public Integer getIdClase() {
        return idClase;
    }

    public void setIdClase(Integer idClase) {
        this.idClase = idClase;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public Integer getNumAisentos() {
        return numAisentos;
    }

    public void setNumAisentos(Integer numAisentos) {
        this.numAisentos = numAisentos;
    }

    @XmlTransient
    public Collection<Corridas> getCorridasCollection() {
        return corridasCollection;
    }

    public void setCorridasCollection(Collection<Corridas> corridasCollection) {
        this.corridasCollection = corridasCollection;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idClase != null ? idClase.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Autobus)) {
            return false;
        }
        Autobus other = (Autobus) object;
        if ((this.idClase == null && other.idClase != null) || (this.idClase != null && !this.idClase.equals(other.idClase))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Modelo.Autobus[ idClase=" + idClase + " ]";
    }
    
}
