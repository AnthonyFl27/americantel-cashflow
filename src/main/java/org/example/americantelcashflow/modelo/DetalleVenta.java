package org.example.americantelcashflow.modelo;

import lombok.Getter;
import lombok.Setter;
import org.openxava.annotations.Required;

import javax.persistence.*;

@Entity
@Table(name = "detalle_venta")
@Getter
@Setter
public class DetalleVenta {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)  // PostgreSQL identity/autoincrement
    @Column(name = "id_detalle")
    private Long idDetalle;

    @Required
    @Column(name = "descripcion_item", length = 200, nullable = false)
    private String descripcionItem;

    @Required
    @Column(nullable = false)
    private int cantidad;

    @Required
    @Column(name = "precio_unitario", nullable = false)
    private double precioUnitario;

    @Column(nullable = false)
    private double subtotal;

    // -------- RELACIÓN MUCHOS ? UNO CON VENTA --------
    @ManyToOne
    @JoinColumn(name = "id_venta", nullable = false)
    private Venta venta;

    // -------- LÓGICA AUTOMÁTICA --------
    @PrePersist
    @PreUpdate
    public void calcularSubtotal() {
        this.subtotal = this.cantidad * this.precioUnitario;
    }
}
