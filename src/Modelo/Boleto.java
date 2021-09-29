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
@Table(name = "BOLETO")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Boleto.findAll", query = "SELECT b FROM Boleto b"),
    @NamedQuery(name = "Boleto.findByFolio", query = "SELECT b FROM Boleto b WHERE b.folio = :folio"),
    @NamedQuery(name = "Boleto.findByNumasineto", query = "SELECT b FROM Boleto b WHERE b.numasineto = :numasineto"),
    @NamedQuery(name = "Boleto.findByPasajero", query = "SELECT b FROM Boleto b WHERE b.pasajero = :pasajero")})
public class Boleto implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "FOLIO")
    private Integer folio;
    @Column(name = "NUMASINETO")
    private Integer numasineto;
    @Column(name = "PASAJERO")
    private String pasajero;
    @OneToMany(mappedBy = "boleto")
    private Collection<Corridas> corridasCollection;

    public Boleto() {
    }

    public Boleto(Integer folio) {
        this.folio = folio;
    }

    public Integer getFolio() {
        return folio;
    }

    public void setFolio(Integer folio) {
        this.folio = folio;
    }

    public Integer getNumasineto() {
        return numasineto;
    }

    public void setNumasineto(Integer numasineto) {
        this.numasineto = numasineto;
    }

    public String getPasajero() {
        return pasajero;
    }

    public void setPasajero(String pasajero) {
        this.pasajero = pasajero;
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
        hash += (folio != null ? folio.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Boleto)) {
            return false;
        }
        Boleto other = (Boleto) object;
        if ((this.folio == null && other.folio != null) || (this.folio != null && !this.folio.equals(other.folio))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Modelo.Boleto[ folio=" + folio + " ]";
    }
    
}
