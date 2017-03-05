/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

import dao.UserDao;
import entity.User;
import java.util.List;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import org.primefaces.context.RequestContext;

/**
 *
 * @author araf0
 */
@ManagedBean
@SessionScoped
public class UserMB {
    User user = new User();
    UserDao userDao = new UserDao();
    private List< User> usersList;

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
    
    public String login(){
        user.setName(user.getName());
        user.setPass(user.getPass());
        if(new UserDao().login(user)){
            System.out.println("Login success");
            return "view";
        } else{
            System.out.println("Login failed");
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Something went wrong!", ""));
        }
        return null;
    }
    
    public String changeUser(User user) {
        this.user = user;
        return "edit";
    }
    
    public String addUser(){
        boolean status = new UserDao().addUser(user);
        if (status) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Data Saved", ""));
            return "view";
        } else {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN, "Data not Saved", ""));
        }
        return null;
    }
    
    public List< User> getUsers() {
        usersList = userDao.AllUsers();
        int count = usersList.size();
        return usersList;
    }
    
    public String UpdateUser(User user) {
        String Name = user.getName();
        FacesMessage message1 = new FacesMessage(FacesMessage.SEVERITY_INFO, "Name", Name);
        RequestContext.getCurrentInstance().showMessageInDialog(message1);
        userDao.update(user);
        System.out.println("User Info successfully saved.");
        FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_INFO, "Save Information", "User updated successfully .");
        RequestContext.getCurrentInstance().showMessageInDialog(message);
        user = new User();
        return "view";
    }
    
    public void deleteUser(User user) {
        String Name = user.getName();  
        userDao.delete(user);
        FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_INFO, "Delete", "Record deleted successfully");
        RequestContext.getCurrentInstance().showMessageInDialog(message);
    }
}
