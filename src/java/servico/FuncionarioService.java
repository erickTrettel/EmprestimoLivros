package servico;

import entidades.Funcionario;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

public class FuncionarioService {
    
    private final EntityManagerFactory emf;
    
    public FuncionarioService(){
        emf = Persistence.createEntityManagerFactory("ProjetoAluguelProvaPU");
    }
    
    public void salvar(Funcionario funcionario) {
        EntityManager em = emf.createEntityManager();
        em.getTransaction().begin();
        em.persist(funcionario);
        em.getTransaction().commit();
        em.close();
    }
    
    public void editar(Funcionario funcionario){
        EntityManager em = emf.createEntityManager();
        em.getTransaction().begin();
        em.merge(funcionario);
        em.getTransaction().commit();
        em.close();
    }

    public List<Funcionario> getFuncionarios() {
        EntityManager em = emf.createEntityManager();
        List lista = em.createQuery("SELECT a FROM Funcionario a").getResultList();
        em.close();
        
        return lista;
    }
}
