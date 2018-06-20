package servico;

import entidades.Aluguel;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

public class AluguelService {
    
    private final EntityManagerFactory emf;
    
    public AluguelService(){
        emf = Persistence.createEntityManagerFactory("ProjetoAluguelProvaPU");
    }
    
    public void salvar(Aluguel aluguel){
        EntityManager em = emf.createEntityManager();
        em.getTransaction().begin();
        em.persist(aluguel);
        em.getTransaction().commit();
        em.close();
    }
    
    //Talvez não seja útil
    public void editar(Aluguel aluguel){
        EntityManager em = emf.createEntityManager();
        em.getTransaction().begin();
        em.merge(aluguel);
        em.getTransaction().commit();
        em.close();
    }
    
    public List<Aluguel> getAlugueis(){
        EntityManager em = emf.createEntityManager();
        List lista = em.createQuery("SELECT a FROM Aluguel a").getResultList();
        em.close();
        
        return lista;
    }
}
