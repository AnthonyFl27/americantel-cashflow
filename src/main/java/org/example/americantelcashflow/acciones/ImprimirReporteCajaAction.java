package org.example.americantelcashflow.acciones;

import java.util.Map;

import net.sf.jasperreports.engine.JRDataSource;
import org.openxava.actions.JasperReportBaseAction;

public class ImprimirReporteCajaAction extends JasperReportBaseAction {

    @Override
    protected String getJRXML() throws Exception {
        // Nombre del archivo dentro de la carpeta /reports
        return "ReporteCajaAmericantel.jrxml";
    }

    @Override
    protected JRDataSource getDataSource() throws Exception {
        // IMPORTANTE: para que Jasper use el SQL del jrxml,
        // debemos devolver null y OpenXava le pasa la conexión JDBC.
        return null;
    }

    @Override
    @SuppressWarnings("rawtypes")
    public Map getParameters() throws Exception {
        // No usamos parámetros por ahora
        return null;
    }
}
