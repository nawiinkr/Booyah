package com.neo.booyah.server.utils;
 
import org.hibernate.HibernateException;
import org.hibernate.SessionFactory;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;
import org.hibernate.service.ServiceRegistry;
 
//import com.neo.booyah.server.entity.Student;
 
public class HibernateUtil {
     
    private static SessionFactory sessionFactory;
    
    public static SessionFactory getSessionFactory() {
        if (null != sessionFactory) {
        	return sessionFactory;
        }
            
        
        Configuration configuration = new Configuration();
        
        String  prodScenario = System.getenv("BINGED_PROD");
        
        if(prodScenario != null && prodScenario.equals("false")) {
        	configuration.configure("hibernate_local.cfg.xml");
        }else {
        	configuration.configure("hibernate.cfg.xml");
        }
        
        ServiceRegistry serviceRegistry = new StandardServiceRegistryBuilder().applySettings(configuration.getProperties()).build();
        try {
            sessionFactory = configuration.buildSessionFactory(serviceRegistry);
        } catch (HibernateException e) {
            System.err.println("Initial SessionFactory creation failed." + e);
            throw new ExceptionInInitializerError(e);
        }
        return sessionFactory;
    }
     
}