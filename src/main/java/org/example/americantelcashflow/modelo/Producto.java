package org.example.americantelcashflow.modelo;

import lombok.Getter;
import lombok.Setter;
import org.openxava.annotations.Required;
import org.openxava.annotations.Stereotype;
import org.openxava.annotations.View;
import org.openxava.annotations.Views;
import org.openxava.model.Identifiable;

import javax.persistence.Entity;
import java.math.BigDecimal;

@Entity
@Getter @Setter
@Views({
        @View(name="DEFAULT",
                members="nombre, descripcion, precioUnitario"
        ),
        @View(name="SoloNombre",
                members="nombre"
        )
})
public class Producto extends Identifiable {

    @Required
    private String nombre;

    @Stereotype("MEMO")
    private String descripcion;

    @Required
    @Stereotype("MONEY")
    private BigDecimal precioUnitario;
}
