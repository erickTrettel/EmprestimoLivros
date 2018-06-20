package entidades;

import beans.FuncionarioBean;
import java.util.List;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;

@FacesConverter("funcionarioConverter")
public class FuncionarioConverter implements Converter{

    @Override
    public Object getAsObject(FacesContext fc, UIComponent uic, String string) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public String getAsString(FacesContext fc, UIComponent uic, Object value) {
        
        if (value == null) {
            return null;
        }
        
        Integer codigoFuncionario = (Integer) value;
        String nomeFuncionario = "";
        
        FuncionarioBean fb = new FuncionarioBean();
        
        List<Funcionario> todosFuncionarios = fb.getTodosFuncionarios();
        
        for(Funcionario f : todosFuncionarios){
            if(f.getCodigo().equals(codigoFuncionario)){
                nomeFuncionario = f.getNome();
            }
        }
        
        return nomeFuncionario;
    }
    
}
