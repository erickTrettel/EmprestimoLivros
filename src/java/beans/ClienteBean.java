package beans;

import entidades.Aluguel;
import entidades.Cliente;
import java.io.Serializable;
import java.util.List;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import org.primefaces.event.RowEditEvent;
import servico.ClienteService;

@ManagedBean
@ViewScoped
public class ClienteBean implements Serializable {

    private Cliente cliente = new Cliente();
    private final ClienteService clienteService = new ClienteService();
    private List<Cliente> clientes = null;

    private List<Cliente> clientesFiltrados;

    public void salvar() {

        if (verificarCodigo(cliente.getCodigo())) {
            cliente.setMulta(0.0);
            clienteService.salvar(cliente);
            clientes.add(cliente);
            cliente = new Cliente();

            FacesMessage msg = new FacesMessage("Cliente salvo");
            FacesContext.getCurrentInstance().addMessage(null, msg);
        } else {
            FacesMessage msg = new FacesMessage("Código inválido");
            FacesContext.getCurrentInstance().addMessage(null, msg);
        }
    }

    public void editar(Cliente c) {
        ClienteService ls = new ClienteService();
        ls.editar(c);
    }

    public void pagarMulta() {
        clientes = getTodosClientes();

        for (Cliente c : clientes) {
            if (c.getCodigo() == cliente.getCodigo() && c.getMulta() > 0) {
                c.setMulta(0.0);
                editar(c);

                FacesMessage msg = new FacesMessage("Pagamento concluído");
                FacesContext.getCurrentInstance().addMessage(null, msg);
            }
        }
    }

    public boolean verificarCodigo(Integer codigo) {

        List<Cliente> todosClientes = getTodosClientes();

        for (Cliente cli : todosClientes) {
            if (codigo.equals(cli.getCodigo())) {
                return false;
            }
        }

        return true;
    }

    public void onRowEdit(RowEditEvent event) {
        Cliente u = (Cliente) event.getObject();
        ClienteService us = new ClienteService();
        us.editar(u);

        FacesMessage msg = new FacesMessage("Cliente editado");
        FacesContext.getCurrentInstance().addMessage(null, msg);
    }

    public Double getMulta(int codigo) {
        Double multa = 0.00;

        AluguelBean ab = new AluguelBean();

        List<Aluguel> alugueis = ab.getTodosAlugueis();

        for (Aluguel a : alugueis) {
            if (a.getCodigoCliente() == codigo) {
                multa += 15.00;
            }
        }

        return multa;
    }

    public List<Cliente> getTodosClientes() {

        if (clientes == null) {
            clientes = clienteService.getClientes();
        }

        return clientes;
    }

    public Cliente getCliente() {
        return cliente;
    }

    public void setCliente(Cliente cliente) {
        this.cliente = cliente;
    }

    public List<Cliente> getClientesFiltrados() {
        return clientesFiltrados;
    }

    public void setClientesFiltrados(List<Cliente> clientesFiltrados) {
        this.clientesFiltrados = clientesFiltrados;
    }
}
