package org.example.americantelcashflow.modelo;

import lombok.Getter;
import lombok.Setter;
import org.openxava.annotations.*;
import org.openxava.model.Identifiable;

import javax.persistence.*;
import java.math.BigDecimal;

@Entity
@Getter @Setter
@View(name="DEFAULT",
        members =
                "venta;" +
                        "producto, cantidad, precioUnitario, subtotal"   // mostramos los 4
)
public class DetalleVenta extends Identifiable {

    // -------- RELACIONES --------

    @ManyToOne
    @Required
    private Venta venta;

    @ManyToOne
    @Required
    @ReferenceView("SoloNombre")          // usa solo el nombre del producto
    private Producto producto;

    // -------- CAMPOS --------

    @Required
    private BigDecimal cantidad;

    // ? Ya NO es @Required, solo de lectura
    @Stereotype("MONEY")
    @ReadOnly
    private BigDecimal precioUnitario;

    // -------- LÓGICA --------

    /**
     * Al seleccionar un producto:
     * - Copia el precioUnitario del producto al detalle.
     */
    public void setProducto(Producto producto) {
        this.producto = producto;

        if (producto != null) {
            this.precioUnitario = producto.getPrecioUnitario();
        } else {
            this.precioUnitario = null;
        }
    }

    /**
     * Subtotal calculado: cantidad * precioUnitario
     * Solo lectura, no se guarda en DB, pero se usa en la lista y en la vista.
     */
    @ReadOnly
    @Stereotype("MONEY")
    @Depends("cantidad, precioUnitario")
    public BigDecimal getSubtotal() {
        if (cantidad == null || precioUnitario == null) return BigDecimal.ZERO;
        return cantidad.multiply(precioUnitario);
    }
}
