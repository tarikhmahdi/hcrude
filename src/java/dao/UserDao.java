/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dao;

import entity.User;
import java.util.List;
import javax.servlet.http.HttpSession;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import util.HibernateUtil;
import util.SessionUtil;

/**
 *
 * @author araf0
 */
public class UserDao {

    private User user;
    private List< User> DaoAllUsers;

    public List< User> AllUsers() {
        Session session = HibernateUtil.getSessionFactory().openSession();
        try {
            session.beginTransaction();
            DaoAllUsers = session.createCriteria(User.class).list();
            int count = DaoAllUsers.size();
            session.getTransaction().commit();
        } catch (Exception e) {
            e.printStackTrace();
            session.getTransaction().rollback();
        }
        session.close();
        return DaoAllUsers;
    }

    public boolean login(User user) {
        try {

            SessionFactory factory = HibernateUtil.getSessionFactory();
            Session session = factory.openSession();
            session.beginTransaction();
            
            Query query = session.createQuery("SELECT u FROM User u WHERE u.name=:name AND u.pass =:pass");
            query.setString("name", user.getName());
            query.setString("pass", user.getPass());
            
            List<User> cList= query.list();
            cList.toString();
            session.getTransaction().commit();
            session.close();
            
            if(cList.size()>0){
                HttpSession session1 = SessionUtil.getSession();
                session1.setAttribute("name", cList.get(0).getName());
                session1.setAttribute("id", cList.get(0).getId());              
                return true;
            } else{
                return false;                
            }
            

        } catch (Exception e) {
            //e.printStackTrace();
            return false;
        }
    }

    public boolean addUser(User user) {
        try {
            SessionFactory factory = HibernateUtil.getSessionFactory();
            Session session = factory.openSession();
            session.beginTransaction();
            session.save(user);
            session.getTransaction().commit();
            session.close();
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    public void update(User user) {
        Session session = HibernateUtil.getSessionFactory().openSession();
        try {
            session.beginTransaction();
            session.update(user);
            session.flush();
            session.getTransaction().commit();
        } catch (Exception e) {
            e.printStackTrace();
            session.getTransaction().rollback();
        }
        session.close();
    }

    public void delete(User user) {
        Session session = HibernateUtil.getSessionFactory().openSession();
        try {
            String name = user.getName();
            session.beginTransaction();
            session.delete(user);
            session.getTransaction().commit();
        } catch (Exception e) {
            e.printStackTrace();
            session.getTransaction().rollback();
        }
        session.close();
    }
}
