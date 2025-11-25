package org.example.americantelcashflow.modelo;

import lombok.Getter;
import lombok.Setter;
import org.openxava.annotations.Depends;
import org.openxava.annotations.ReadOnly;
import org.openxava.annotations.Required;
import org.openxava.annotations.Stereotype;
import org.openxava.annotations.View;
import org.openxava.model.Identifiable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.OneToMany;
import java.math.BigDecimal;
import java.util.Collection;
import java.util.Date;
import java.util.Objects;

@Entity
@Getter @Setter
@View(members =
        "fechaApertura, saldoInicial, saldoFinal;" +
                "descripcion"
)
public class Caja extends Identifiable {

    @Required
    @Stereotype("DATETIME")
    @Column(name = "fecha_apertura")
    private Date fechaApertura;

    @Required
    @Stereotype("MONEY")
    @Column(name = "saldo_inicial")
    private BigDecimal saldoInicial;

    /** NO poner en la vista para evitar subpestañas */
    @OneToMany(mappedBy = "caja")
    private Collection<Venta> ventas;

    @ReadOnly
    @Stereotype("MONEY")
    @Depends("ventas.total, saldoInicial")
    public BigDecimal getSaldoFinal() {
        if (ventas == null) return saldoInicial;

        BigDecimal totalVentas = ventas.stream()
                .map(Venta::getTotal)
                .filter(Objects::nonNull)
                .reduce(BigDecimal.ZERO, BigDecimal::add);

        return saldoInicial.add(totalVentas);
    }

    @Stereotype("TEXT_AREA")
    private String descripcion;
}
