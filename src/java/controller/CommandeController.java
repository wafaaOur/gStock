/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import bean.Commande;
import bean.CommandeItem;
import bean.Fournisseur;
import bean.Produit;
import com.sun.javafx.scene.control.skin.VirtualFlow;
import controller.util.JsfUtil;
import javax.inject.Named;
import javax.enterprise.context.SessionScoped;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ejb.EJB;
import javax.ejb.EJBException;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;
import service.CommandeFacade;
import service.CommandeItemFacade;

/**
 *
 * @author WAFA
 */
@Named(value = "commandeController")
@SessionScoped
public class CommandeController implements Serializable {

    private List<CommandeItem>commandeItems;
    private Date dateCommande;
    private Double montantTotal;
    private Fournisseur fournisseur;
    private Double quantite;
    private Produit produit;
    private Double prixCommandeItem;
    private CommandeItemController commandeItemController;
    @EJB
    private service.CommandeFacade ejbFacade;
    private CommandeItemFacade commandeItemFacade;
    private Commande selected;
    private List<Commande>items;
    
    public void saveCommande(){
        Commande selected=getParams();
        ejbFacade.save(selected, commandeItemFacade.getCommandeItems());
        
    }
    public void saveItem(){
        CommandeItem commandeItem=getParamItem();
        commandeItemFacade.getCommandeItems().add(commandeItem);
        
    }
    
    private CommandeItem getParamItem(){
        CommandeItem commandeItem=new CommandeItem();
        commandeItem.setProduit(produit);
        commandeItem.setQuantite(quantite);
        return commandeItem;
    }
    private Commande getParams(){
        Commande selected=new Commande();
        selected.setDateCommande(getDateCommande());
        selected.setMontantTotal(getMontantTotal());
        selected.setFournisseur(getFournisseur());
        return selected;
    }
   
     /**
     * Creates a new instance of CommandeController
     */
     public CommandeController() {
    }

    public Commande getSelected() {
        return selected;
    }

    public void setSelected(Commande selected) {
        this.selected = selected;
    }

    public List<CommandeItem> getCommandeItems() {
        return commandeItems;
    }

    public void setCommandeItems(List<CommandeItem> commandeItems) {
        this.commandeItems = commandeItems;
    }

    public Date getDateCommande() {
        return dateCommande;
    }

    public void setDateCommande(Date dateCommande) {
        this.dateCommande = dateCommande;
    }

    public Double getMontantTotal() {
        return montantTotal;
    }

    public void setMontantTotal(Double montantTotal) {
        this.montantTotal = montantTotal;
    }

    public Fournisseur getFournisseur() {
        return fournisseur;
    }

    public void setFournisseur(Fournisseur fournisseur) {
        this.fournisseur = fournisseur;
    }

    public Double getQuantite() {
        return quantite;
    }

    public void setQuantite(Double quantite) {
        this.quantite = quantite;
    }

    public Produit getProduit() {
        return produit;
    }

    public void setProduit(Produit produit) {
        this.produit = produit;
    }

    public Double getPrixCommandeItem() {
        return prixCommandeItem;
    }

    public void setPrixCommandeItem(Double prixCommandeItem) {
        this.prixCommandeItem = prixCommandeItem;
    }

    public CommandeItemController getCommandeItemController() {
        return commandeItemController;
    }

    public void setCommandeItemController(CommandeItemController commandeItemController) {
        this.commandeItemController = commandeItemController;
    }

    private CommandeFacade getFacade() {
        return ejbFacade;
    }

    public CommandeItemFacade getCommandeItemFacade() {
        return commandeItemFacade;
    }

    public void setCommandeItemFacade(CommandeItemFacade commandeItemFacade) {
        this.commandeItemFacade = commandeItemFacade;
    }

    public void setItems(List<Commande> items) {
        this.items = items;
    }

    
    
    protected void setEmbeddableKeys() {
    }

    protected void initializeEmbeddableKey() {
    }

   
    public Commande prepareCreate() {
        selected = new Commande();
        initializeEmbeddableKey();
        return selected;
    }

    public void create() {
        persist(JsfUtil.PersistAction.CREATE, ResourceBundle.getBundle("/Bundle").getString("CommandeCreated"));
        if (!JsfUtil.isValidationFailed()) {
            items = null;    // Invalidate list of items to trigger re-query.
        }
    }

    public void update() {
        persist(JsfUtil.PersistAction.UPDATE, ResourceBundle.getBundle("/Bundle").getString("CommandeUpdated"));
    }

    public void destroy() {
        persist(JsfUtil.PersistAction.DELETE, ResourceBundle.getBundle("/Bundle").getString("CommandeDeleted"));
        if (!JsfUtil.isValidationFailed()) {
            selected = null; // Remove selection
            items = null;    // Invalidate list of items to trigger re-query.
        }
    }

    public List<Commande> getItems() {
        if (items == null) {
            items = getFacade().findAll();
        }
        return items;
    }

    private void persist(JsfUtil.PersistAction persistAction, String successMessage) {
        if (selected != null) {
            setEmbeddableKeys();
            try {
                if (persistAction != JsfUtil.PersistAction.DELETE) {
                    getFacade().edit(selected);
                } else {
                    getFacade().remove(selected);
                }
                JsfUtil.addSuccessMessage(successMessage);
            } catch (EJBException ex) {
                String msg = "";
                Throwable cause = ex.getCause();
                if (cause != null) {
                    msg = cause.getLocalizedMessage();
                }
                if (msg.length() > 0) {
                    JsfUtil.addErrorMessage(msg);
                } else {
                    JsfUtil.addErrorMessage(ex, ResourceBundle.getBundle("/Bundle").getString("PersistenceErrorOccured"));
                }
            } catch (Exception ex) {
                Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, null, ex);
                JsfUtil.addErrorMessage(ex, ResourceBundle.getBundle("/Bundle").getString("PersistenceErrorOccured"));
            }
        }
    }

    public Commande getCommande(java.lang.Long id) {
        return getFacade().find(id);
    }

    public List<Commande> getItemsAvailableSelectMany() {
        return getFacade().findAll();
    }

    public List<Commande> getItemsAvailableSelectOne() {
        return getFacade().findAll();
    }

    @FacesConverter(forClass = Commande.class)
    public static class CommandeControllerConverter implements Converter {

        @Override
        public Object getAsObject(FacesContext facesContext, UIComponent component, String value) {
            if (value == null || value.length() == 0) {
                return null;
            }
            CommandeController controller = (CommandeController) facesContext.getApplication().getELResolver().
                    getValue(facesContext.getELContext(), null, "commandeController");
            return controller.getCommande(getKey(value));
        }

        java.lang.Long getKey(String value) {
            java.lang.Long key;
            key = Long.valueOf(value);
            return key;
        }

        String getStringKey(java.lang.Long value) {
            StringBuilder sb = new StringBuilder();
            sb.append(value);
            return sb.toString();
        }

        @Override
        public String getAsString(FacesContext facesContext, UIComponent component, Object object) {
            if (object == null) {
                return null;
            }
            if (object instanceof Commande) {
                Commande o = (Commande) object;
                return getStringKey(o.getId());
            } else {
                Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, "object {0} is of type {1}; expected type: {2}", new Object[]{object, object.getClass().getName(), Commande.class.getName()});
                return null;
            }
        }

    }
}

