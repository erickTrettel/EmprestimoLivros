package entidades;

import beans.ClienteBean;
import java.util.List;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;

@FacesConverter("clienteConverter")
public class ClienteConverter implements Converter {

    @Override
    public Object getAsObject(FacesContext fc, UIComponent uic, String string) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public String getAsString(FacesContext fc, UIComponent uic, Object value) {

        if (value == null) {
            return null;
        }

        String nomeCliente = "";
        
        Integer codCliente = (Integer) value;
        
        ClienteBean cb = new ClienteBean();
        
        List<Cliente> clientes = cb.getTodosClientes();
        
        for(Cliente c : clientes){
            if(c.getCodigo() == codCliente){
                nomeCliente = c.getNome();
            }
        }

        return nomeCliente;
    }

}
