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
@Table(name = "POBLACION")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Poblacion.findAll", query = "SELECT p FROM Poblacion p"),
    @NamedQuery(name = "Poblacion.findByIdPoblacion", query = "SELECT p FROM Poblacion p WHERE p.idPoblacion = :idPoblacion"),
    @NamedQuery(name = "Poblacion.findByNombre", query = "SELECT p FROM Poblacion p WHERE p.nombre = :nombre"),
    @NamedQuery(name = "Poblacion.findByEstado", query = "SELECT p FROM Poblacion p WHERE p.estado = :estado")})
public class Poblacion implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "ID_POBLACION")
    private Integer idPoblacion;
    @Column(name = "NOMBRE")
    private String nombre;
    @Column(name = "ESTADO")
    private String estado;
    @OneToMany(mappedBy = "destino")
    private Collection<Corridas> corridasCollection;
    @OneToMany(mappedBy = "origen")
    private Collection<Corridas> corridasCollection1;

    public Poblacion() {
    }

    public Poblacion(Integer idPoblacion) {
        this.idPoblacion = idPoblacion;
    }

    public Integer getIdPoblacion() {
        return idPoblacion;
    }

    public void setIdPoblacion(Integer idPoblacion) {
        this.idPoblacion = idPoblacion;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    @XmlTransient
    public Collection<Corridas> getCorridasCollection() {
        return corridasCollection;
    }

    public void setCorridasCollection(Collection<Corridas> corridasCollection) {
        this.corridasCollection = corridasCollection;
    }

    @XmlTransient
    public Collection<Corridas> getCorridasCollection1() {
        return corridasCollection1;
    }

    public void setCorridasCollection1(Collection<Corridas> corridasCollection1) {
        this.corridasCollection1 = corridasCollection1;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idPoblacion != null ? idPoblacion.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Poblacion)) {
            return false;
        }
        Poblacion other = (Poblacion) object;
        if ((this.idPoblacion == null && other.idPoblacion != null) || (this.idPoblacion != null && !this.idPoblacion.equals(other.idPoblacion))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Modelo.Poblacion[ idPoblacion=" + idPoblacion + " ]";
    }
    
}
