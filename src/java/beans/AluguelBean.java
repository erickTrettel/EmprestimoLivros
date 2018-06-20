package beans;

import entidades.Aluguel;
import entidades.Cliente;
import entidades.Livro;
import java.io.Serializable;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import servico.AluguelService;

@ManagedBean
@ViewScoped
public class AluguelBean implements Serializable {

    private Aluguel aluguel = new Aluguel();
    private final AluguelService aluguelService = new AluguelService();
    private List<Aluguel> alugueis = null;

    public void salvar() {

        //Verifica se codigo do cliente e codigo do livro são válidos
        if (verificarCodigoCliente(aluguel.getCodigoCliente())
                && verificarCodigoLivro(aluguel.getCodigoLivro())) {

            //Pega a data atual e insere no campo dataAluguel
            aluguel.setDataAluguel(getPegaDataAtual());

            //Seta o status do aluguel como pendente
            aluguel.setStatus(Boolean.FALSE);

            //Seta a status do livro como indisponivel
            setLivroIndisponivel(aluguel.getCodigoLivro());

            aluguelService.salvar(aluguel);
            alugueis.add(aluguel);
            aluguel = new Aluguel();

            FacesMessage msg = new FacesMessage("Aluguel registrado");
            FacesContext.getCurrentInstance().addMessage(null, msg);
        } else {
            FacesMessage msg = new FacesMessage("Código(s) do cliente e/ou Livro inválido(s)");
            FacesContext.getCurrentInstance().addMessage(null, msg);
        }
    }

    public void devolver() {

        List<Aluguel> todosAlugueis = getTodosAlugueis();
        
        ClienteBean cb = new ClienteBean();
        List<Cliente> todosClientes = cb.getTodosClientes();

        for (Aluguel alu : todosAlugueis) {
            if (aluguel.getCodigo().equals(alu.getCodigo())) {
                
                if(alu.getDataDevolucao().before(getPegaDataAtual())){
                    
                    for(Cliente c : todosClientes){
                        if(alu.getCodigoCliente() == c.getCodigo()){
                            Double multa = c.getMulta();
                            multa += 15.00;
                            c.setMulta(multa);
                            cb.editar(c);
                        }
                    }
                }

                if (alu.getStatus().equals(Boolean.FALSE)) {
                    alu.setStatus(Boolean.TRUE);

                    aluguelService.editar(alu);
                    
                    setLivroDisponivel(alu.getCodigoLivro());

                    FacesMessage msg = new FacesMessage("Devolução registrada");
                    FacesContext.getCurrentInstance().addMessage(null, msg);
                } else {
                    FacesMessage msg = new FacesMessage("Algo deu errado");
                    FacesContext.getCurrentInstance().addMessage(null, msg);
                }
            }
        }
    }

    public void setLivroIndisponivel(Integer codigo) {
        LivroBean l = new LivroBean();
        List<Livro> livros = l.getTodosLivros();

        for (Livro li : livros) {
            if (li.getCodigo().equals(codigo)) {
                li.setDisponibilidade(Boolean.FALSE);
                l.editar(li);
            }
        }
    }
    
    public void setLivroDisponivel(Integer codigo) {
        LivroBean l = new LivroBean();
        List<Livro> livros = l.getTodosLivros();

        for (Livro li : livros) {
            if (li.getCodigo().equals(codigo)) {
                li.setDisponibilidade(Boolean.TRUE);
                l.editar(li);
            }
        }
    }

    public boolean verificarCodigoCliente(int codigo) {

        ClienteBean c = new ClienteBean();
        List<Cliente> clientes = c.getTodosClientes();

        for (Cliente cli : clientes) {
            if (cli.getCodigo() == codigo) {
                return true;
            }
        }

        return false;
    }

    public boolean verificarCodigoLivro(Integer codigo) {

        LivroBean l = new LivroBean();
        List<Livro> livros = l.getTodosLivros();

        for (Livro li : livros) {
            if (li.getCodigo().equals(codigo) && li.getDisponibilidade().equals(Boolean.TRUE)) {
                return true;
            }
        }

        return false;
    }

    public Date getPegaDataAtual() {
        Calendar calendar = new GregorianCalendar();
        Date date = new Date();
        calendar.setTime(date);
        return calendar.getTime();
    }

    public Date getCalculaDataDevolucao() {
        Date date = getPegaDataAtual();
        Date dataDev;

        GregorianCalendar gc = new GregorianCalendar();
        gc.add(gc.MONTH, 2);

        dataDev = gc.getTime();

        return dataDev;
    }

    public List<Aluguel> getTodosAlugueis() {
        if (alugueis == null) {
            alugueis = aluguelService.getAlugueis();
        }

        return alugueis;
    }

    public Aluguel getAluguel() {
        return aluguel;
    }

    public void setAluguel(Aluguel aluguel) {
        this.aluguel = aluguel;
    }
}
