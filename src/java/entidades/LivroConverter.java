package entidades;

import beans.LivroBean;
import java.util.List;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;

@FacesConverter("livroConverter")
public class LivroConverter implements Converter{

    @Override
    public Object getAsObject(FacesContext fc, UIComponent uic, String string) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public String getAsString(FacesContext fc, UIComponent uic, Object value) {
        
        if (value == null) {
            return null;
        }
        
        String nomeLivro = "";
        Integer codLivro = (Integer) value;
        
        LivroBean lb = new LivroBean();
        
        List<Livro> livros = lb.getTodosLivros();
        
        for(Livro l : livros){
            if(l.getCodigo().equals(codLivro)){
                nomeLivro = l.getTitulo();
            }
        }
        
        return nomeLivro;
    }
    
}
