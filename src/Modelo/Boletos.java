/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Modelo;

import java.io.Serializable;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author manel
 */
@Entity
@Table(name = "BOLETOS")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Boletos.findAll", query = "SELECT b FROM Boletos b"),
    @NamedQuery(name = "Boletos.findByFolio", query = "SELECT b FROM Boletos b WHERE b.folio = :folio"),
    @NamedQuery(name = "Boletos.findByNumasineto", query = "SELECT b FROM Boletos b WHERE b.numasineto = :numasineto"),
    @NamedQuery(name = "Boletos.findByPasajero", query = "SELECT b FROM Boletos b WHERE b.pasajero = :pasajero"),
    @NamedQuery(name = "Boletos.encuentrarepetido", query = "SELECT b FROM Boletos b WHERE b.pasajero = ?1"),
    @NamedQuery(name = "Boletos.encuentracorrida", query = "SELECT b.corrida FROM Boletos b WHERE b.corrida = ?1")
})
public class Boletos implements Serializable {
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
    @JoinColumn(name = "CORRIDA", referencedColumnName = "ID_CORRIDA")
    @ManyToOne
    private Corridas corrida;

    public Boletos() {
    }

    public Boletos(Integer folio) {
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

    public Corridas getCorrida() {
        return corrida;
    }

    public void setCorrida(Corridas corrida) {
        this.corrida = corrida;
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
        if (!(object instanceof Boletos)) {
            return false;
        }
        Boletos other = (Boletos) object;
        if ((this.folio == null && other.folio != null) || (this.folio != null && !this.folio.equals(other.folio))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Modelo.Boletos[ folio=" + folio + " ]";
    }
    
}
