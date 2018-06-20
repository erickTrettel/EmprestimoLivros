package servico;

import entidades.Livro;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

public class LivroService {
    
    private final EntityManagerFactory emf;
    
    public LivroService(){
        emf = Persistence.createEntityManagerFactory("ProjetoAluguelProvaPU");
    }
    
    public void salvar(Livro livro) {
        EntityManager em = emf.createEntityManager();
        em.getTransaction().begin();
        em.persist(livro);
        em.getTransaction().commit();
        em.close();
    }
    
    public void editar(Livro livro){
        EntityManager em = emf.createEntityManager();
        em.getTransaction().begin();
        em.merge(livro);
        em.getTransaction().commit();
        em.close();
    }

    public List<Livro> getLivros() {
        EntityManager em = emf.createEntityManager();
        List lista = em.createQuery("SELECT a FROM Livro a").getResultList();
        em.close();
        
        return lista;
    }
}
