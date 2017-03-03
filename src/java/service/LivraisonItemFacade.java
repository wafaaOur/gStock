/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package service;

import bean.LivraisonItem;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

/**
 *
 * @author the joker
 */
@Stateless
public class LivraisonItemFacade extends AbstractFacade<LivraisonItem> {

    @PersistenceContext(unitName = "gSinventairePU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public LivraisonItemFacade() {
        super(LivraisonItem.class);
    }
    
}
