/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Modelo;

import java.io.Serializable;
import java.util.Collection;
import java.util.Date;
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
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author manel
 */
@Entity
@Table(name = "CORRIDAS")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Corridas.findAll", query = "SELECT c FROM Corridas c"),
    @NamedQuery(name = "Corridas.findByIdCorrida", query = "SELECT c FROM Corridas c WHERE c.idCorrida = :idCorrida"),
    @NamedQuery(name = "Corridas.findByFecha", query = "SELECT c FROM Corridas c WHERE c.fecha = :fecha"),
    @NamedQuery(name = "Corridas.findByHora", query = "SELECT c FROM Corridas c WHERE c.hora = :hora"),
    @NamedQuery(name = "Corridas.findByCosto", query = "SELECT c FROM Corridas c WHERE c.costo = :costo"),
    @NamedQuery(name = "Corridas.findfechajorigenjdestino", query = "SELECT c FROM Corridas c WHERE c.fecha = ?1 and c.origen=?2 and c.destino=?3")})
public class Corridas implements Serializable {
    @JoinColumn(name = "BOLETO", referencedColumnName = "FOLIO")
    @ManyToOne
    private Boleto boleto;
    @OneToMany(mappedBy = "corrida")
    private Collection<Boletos> boletosCollection;
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "ID_CORRIDA")
    private Integer idCorrida;
    @Column(name = "FECHA")
    @Temporal(TemporalType.DATE)
    private Date fecha;
    @Column(name = "HORA")
    @Temporal(TemporalType.TIME)
    private Date hora;
    @Column(name = "COSTO")
    private Integer costo;
    
    
    @JoinColumn(name = "AUTOBUS", referencedColumnName = "ID_CLASE")
    @ManyToOne
    private Autobus autobus;
    @JoinColumn(name = "DESTINO", referencedColumnName = "ID_POBLACION")
    @ManyToOne
    private Poblacion destino;
    @JoinColumn(name = "ORIGEN", referencedColumnName = "ID_POBLACION")
    @ManyToOne
    private Poblacion origen;

    public Corridas() {
    }

    public Corridas(Integer idCorrida) {
        this.idCorrida = idCorrida;
    }

    public Integer getIdCorrida() {
        return idCorrida;
    }

    public void setIdCorrida(Integer idCorrida) {
        this.idCorrida = idCorrida;
    }

    public Date getFecha() {
        return fecha;
    }

    public void setFecha(Date fecha) {
        this.fecha = fecha;
    }

    public Date getHora() {
        return hora;
    }

    public void setHora(Date hora) {
        this.hora = hora;
    }

    public Integer getCosto() {
        return costo;
    }

    public void setCosto(Integer costo) {
        this.costo = costo;
    }

   

    public Autobus getAutobus() {
        return autobus;
    }

    public void setAutobus(Autobus autobus) {
        this.autobus = autobus;
    }

    public Poblacion getDestino() {
        return destino;
    }

    public void setDestino(Poblacion destino) {
        this.destino = destino;
    }

    public Poblacion getOrigen() {
        return origen;
    }

    public void setOrigen(Poblacion origen) {
        this.origen = origen;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idCorrida != null ? idCorrida.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Corridas)) {
            return false;
        }
        Corridas other = (Corridas) object;
        if ((this.idCorrida == null && other.idCorrida != null) || (this.idCorrida != null && !this.idCorrida.equals(other.idCorrida))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Modelo.Corridas[ idCorrida=" + idCorrida + " ]";
    }

    public Boleto getBoleto() {
        return boleto;
    }

    public void setBoleto(Boleto boleto) {
        this.boleto = boleto;
    }

    @XmlTransient
    public Collection<Boletos> getBoletosCollection() {
        return boletosCollection;
    }

    public void setBoletosCollection(Collection<Boletos> boletosCollection) {
        this.boletosCollection = boletosCollection;
    }
    
}
