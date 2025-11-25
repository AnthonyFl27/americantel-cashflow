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

    // -------- RELACIONES --------

    @ManyToOne
    @Required
    private Caja caja;

    @ManyToOne
    private Cliente cliente;

    // -------- CAMPOS --------

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

    /**
     * Fecha de la venta = fecha de apertura de la caja.
     * Solo lectura, el usuario no la cambia.
     */
    @ReadOnly
    @Stereotype("DATETIME")
    @Depends("caja.fechaApertura")
    public Date getFecha() {
        return caja != null ? caja.getFechaApertura() : null;
    }
}
