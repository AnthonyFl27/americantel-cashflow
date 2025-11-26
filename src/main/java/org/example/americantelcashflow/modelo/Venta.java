package org.example.americantelcashflow.modelo;

import lombok.Getter;
import lombok.Setter;
import org.openxava.annotations.*;
import org.openxava.model.Identifiable;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.Collection;
import java.util.Date;
import java.util.Objects;

@Entity
@Getter @Setter
@View( // Vista por defecto
        members =
                "fecha, caja, cliente;" +   // fila 1
                        "descripcion;" +            // fila 2
                        "detalles;" +               // colección
                        "total"                     // total abajo
)
public class Venta extends Identifiable {

    // -------- CAMPOS --------

    // ? YA NO lleva @Required
    @ReadOnly                 // El usuario la ve pero no la puede editar
    @Stereotype("DATETIME")
    private Date fecha;

    // -------- RELACIONES --------

    @ManyToOne
    @Required                 // La caja sí es obligatoria
    private Caja caja;

    @ManyToOne
    private Cliente cliente;

    @Stereotype("TEXT_AREA")
    private String descripcion;

    @OneToMany(mappedBy = "venta", cascade = CascadeType.ALL)
    @ListProperties("producto.nombre, cantidad, precioUnitario, subtotal")
    private Collection<DetalleVenta> detalles;

    // -------- CÁLCULOS --------

    @ReadOnly
    @Stereotype("MONEY")
    @Depends("detalles.subtotal")
    public BigDecimal getTotal() {
        if (detalles == null) return BigDecimal.ZERO;

        return detalles.stream()
                .map(DetalleVenta::getSubtotal)
                .filter(Objects::nonNull)
                .reduce(BigDecimal.ZERO, BigDecimal::add);
    }

    // -------- LÓGICA FECHA --------

    /**
     * Cuando seleccionas una caja:
     * - Copiamos su fechaApertura a fecha de la venta (si existe).
     */
    public void setCaja(Caja caja) {
        this.caja = caja;
        if (caja != null && caja.getFechaApertura() != null) {
            this.fecha = caja.getFechaApertura();
        }
    }

    /**
     * Antes de insertar en BD nos aseguramos de que fecha tenga valor.
     */
    @PrePersist
    public void onCreate() {
        if (fecha == null) {
            if (caja != null && caja.getFechaApertura() != null) {
                fecha = caja.getFechaApertura();
            } else {
                fecha = new Date(); // respaldo: fecha actual
            }
        }
    }
}
