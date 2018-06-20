package servico;

import entidades.Cliente;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

public class ClienteService {

    private final EntityManagerFactory emf;

    public ClienteService() {
        emf = Persistence.createEntityManagerFactory("ProjetoAluguelProvaPU");
    }

    public void salvar(Cliente cliente) {
        EntityManager em = emf.createEntityManager();
        em.getTransaction().begin();
        em.persist(cliente);
        em.getTransaction().commit();
        em.close();
    }

    public void editar(Cliente cliente) {
        EntityManager em = emf.createEntityManager();
        em.getTransaction().begin();
        em.merge(cliente);
        em.getTransaction().commit();
        em.close();
    }

    public List<Cliente> getClientes() {
        EntityManager em = emf.createEntityManager();
        List lista = em.createQuery("SELECT a FROM Cliente a").getResultList();
        em.close();

        return lista;
    }
}
