package beans;

import entidades.Funcionario;
import java.io.Serializable;
import java.util.List;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import org.primefaces.event.RowEditEvent;
import servico.FuncionarioService;

@ManagedBean
@ViewScoped
public class FuncionarioBean implements Serializable {

    private Funcionario funcionario = new Funcionario();
    private final FuncionarioService funcionarioService = new FuncionarioService();
    private List<Funcionario> funcionarios = null;

    public void salvar() {

        if (verificarCodigo(funcionario.getCodigo())) {
            funcionarioService.salvar(funcionario);
            funcionarios.add(funcionario);
            funcionario = new Funcionario();

            FacesMessage msg = new FacesMessage("Funcionario salvo");
            FacesContext.getCurrentInstance().addMessage(null, msg);
        }else{
            FacesMessage msg = new FacesMessage("Código inválido");
            FacesContext.getCurrentInstance().addMessage(null, msg);
        }
    }

    public void editar(Funcionario f) {
        funcionarioService.editar(f);
    }

    public boolean verificarCodigo(Integer codigo) {

        List<Funcionario> todosFuncionarios = getTodosFuncionarios();

        for (Funcionario f : todosFuncionarios) {
            if (codigo.equals(f.getCodigo())) {
                return false;
            }
        }

        return true;
    }
    
    public void onRowEdit(RowEditEvent event) {
        Funcionario f = (Funcionario) event.getObject();
        FuncionarioService us = new FuncionarioService();
        us.editar(f);

        FacesMessage msg = new FacesMessage("Funcionario editado");
        FacesContext.getCurrentInstance().addMessage(null, msg);
    }
    
    public List<Funcionario> getTodosFuncionarios(){
        if(funcionarios == null){
            funcionarios = funcionarioService.getFuncionarios();
        }
        
        return funcionarios;
    }

    public Funcionario getFuncionario() {
        return funcionario;
    }

    public void setFuncionario(Funcionario funcionario) {
        this.funcionario = funcionario;
    }
}
