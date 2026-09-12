/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.proyecto.biblioteca.entity;

import jakarta.persistence.Basic;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.NamedQueries;
import jakarta.persistence.NamedQuery;
import jakarta.persistence.Table;
import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;
import java.io.Serializable;
import java.util.Date;

/**
 *
 * @author benny
 */
@Entity
@Table(name = "detalle_prestamo")
@NamedQueries({
    @NamedQuery(name = "DetallePrestamo.findAll", query = "SELECT d FROM DetallePrestamo d")})
public class DetallePrestamo implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id_detalle_prestamo")
    private Integer idDetallePrestamo;
    @Column(name = "fecha_devolucion_real")
    @Temporal(TemporalType.DATE)
    private Date fechaDevolucionReal;
    @JoinColumn(name = "id_libro", referencedColumnName = "id_libro")
    @ManyToOne(optional = false)
    private Libros idLibro;
    @JoinColumn(name = "id_prestamo", referencedColumnName = "id_prestamo")
    @ManyToOne(optional = false)
    private Prestamos idPrestamo;

    public DetallePrestamo() {
    }

    public DetallePrestamo(Integer idDetallePrestamo) {
        this.idDetallePrestamo = idDetallePrestamo;
    }

    public Integer getIdDetallePrestamo() {
        return idDetallePrestamo;
    }

    public void setIdDetallePrestamo(Integer idDetallePrestamo) {
        this.idDetallePrestamo = idDetallePrestamo;
    }

    public Date getFechaDevolucionReal() {
        return fechaDevolucionReal;
    }

    public void setFechaDevolucionReal(Date fechaDevolucionReal) {
        this.fechaDevolucionReal = fechaDevolucionReal;
    }

    public Libros getIdLibro() {
        return idLibro;
    }

    public void setIdLibro(Libros idLibro) {
        this.idLibro = idLibro;
    }

    public Prestamos getIdPrestamo() {
        return idPrestamo;
    }

    public void setIdPrestamo(Prestamos idPrestamo) {
        this.idPrestamo = idPrestamo;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idDetallePrestamo != null ? idDetallePrestamo.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof DetallePrestamo)) {
            return false;
        }
        DetallePrestamo other = (DetallePrestamo) object;
        if ((this.idDetallePrestamo == null && other.idDetallePrestamo != null) || (this.idDetallePrestamo != null && !this.idDetallePrestamo.equals(other.idDetallePrestamo))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.proyecto.biblioteca.entity.DetallePrestamo[ idDetallePrestamo=" + idDetallePrestamo + " ]";
    }
    
}
