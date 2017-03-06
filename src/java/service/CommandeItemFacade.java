/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package service;

import bean.Commande;
import bean.CommandeItem;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

/**
 *
 * @author the joker
 */
@Stateless
public class CommandeItemFacade extends AbstractFacade<CommandeItem> {

    @PersistenceContext(unitName = "gSinventairePU")
    private EntityManager em;
    private List<CommandeItem>commandeItems;
    
    
    
    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public CommandeItemFacade() {
        super(CommandeItem.class);
    }
    public List<CommandeItem> findById(Long id) {
        return em.createQuery("SELECT ci FROM CommandeItem ci WHERE ci.id= '" + id + "'").getResultList();
    }
    
    public List<CommandeItem> findByProduit(Long id) {
        return em.createQuery("SELECT ci FROM CommandeItem ci WHERE ci.produit.id= '" + id + "'").getResultList();
    }
    
    public List<CommandeItem> findByCommande(Long id) {
        return em.createQuery("SELECT ci FROM CommandeItem ci WHERE ci.commande.id= '" + id + "'").getResultList();
    }

    public List<CommandeItem> getCommandeItems() {
        return commandeItems;
    }

    public void setCommandeItems(List<CommandeItem> commandeItems) {
        this.commandeItems = commandeItems;
    }

    
}
