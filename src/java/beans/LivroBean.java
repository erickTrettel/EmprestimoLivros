package beans;

import entidades.Livro;
import java.io.Serializable;
import java.util.List;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import org.primefaces.event.RowEditEvent;
import servico.LivroService;

@ViewScoped
@ManagedBean
public class LivroBean implements Serializable {

    private Livro livro = new Livro();
    private final LivroService livroService = new LivroService();
    private List<Livro> livros = null;
    
    private List<Livro> livrosFiltrados;

    public void salvar() {

        if (verificarCodigo(livro.getCodigo())) {
            livro.setDisponibilidade(Boolean.TRUE);
            livroService.salvar(livro);
            livros.add(livro);
            livro = new Livro();

            FacesMessage msg = new FacesMessage("Livro salvo");
            FacesContext.getCurrentInstance().addMessage(null, msg);
        } else {
            FacesMessage msg = new FacesMessage("Código já cadastrado");
            FacesContext.getCurrentInstance().addMessage(null, msg);
        }
    }

    public boolean verificarCodigo(Integer codigo) {

        List<Livro> todosLivros = getTodosLivros();

        for (Livro todosLivro : todosLivros) {
            if (codigo.equals(todosLivro.getCodigo())) {
                return false;
            }
        }

        return true;
    }

    public boolean verificaCategoria(String categoria) {
        switch (categoria.toLowerCase()) {
            case "romance":
                return true;
            case "drama":
                return true;
            case "comédia":
                return true;
            case "suspense":
                return true;
            case "policial":
                return true;
            case "aventura":
                return true;
            case "ficção":
                return true;
            case "outro":
                return true;
            default:
                return false;
        }
    }
    
    public void editar(Livro l){
        LivroService ls = new LivroService();
        ls.editar(l);
    }

    public void onRowEdit(RowEditEvent event) {

        Livro l = (Livro) event.getObject();

        if (verificaCategoria(l.getCategoria())) {
            LivroService ls = new LivroService();
            ls.editar(l);

            FacesMessage msg = new FacesMessage("Livro editado");
            FacesContext.getCurrentInstance().addMessage(null, msg);
        } else {
            FacesMessage msg = new FacesMessage("Categoria inválida");
            FacesContext.getCurrentInstance().addMessage(null, msg);
        }
    }
    
    public Livro getLivroCodigo(Integer codigo){
        List<Livro> todosLivros = getTodosLivros();
        
        for (Livro l : todosLivros) {
            if(l.getCodigo().equals(codigo)){
                return l;
            }
        }
        
        FacesMessage msg = new FacesMessage("Código inválido");
        FacesContext.getCurrentInstance().addMessage(null, msg);
        return null;
    }

    public List<Livro> getTodosLivros() {

        if (livros == null) {
            livros = livroService.getLivros();
        }

        return livros;
    }

    public Livro getLivro() {
        return livro;
    }

    public void setLivro(Livro livro) {
        this.livro = livro;
    }

    public List<Livro> getLivrosFiltrados() {
        return livrosFiltrados;
    }

    public void setLivrosFiltrados(List<Livro> livrosFiltrados) {
        this.livrosFiltrados = livrosFiltrados;
    }
}
