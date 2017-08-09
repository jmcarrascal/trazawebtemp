package ar.com.cipres.rest.config;

import javax.annotation.PostConstruct;
import javax.persistence.EntityManagerFactory;

import org.hibernate.ejb.HibernateEntityManagerFactory;
import org.hibernate.event.service.spi.EventListenerRegistry;
import org.hibernate.event.spi.EventType;
import org.hibernate.event.spi.PreLoadEvent;
import org.hibernate.event.spi.PostLoadEvent;
import org.hibernate.event.spi.PostLoadEventListener;
import org.hibernate.event.spi.PreUpdateEvent;
import org.hibernate.event.spi.PreUpdateEventListener;
import org.hibernate.internal.SessionFactoryImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;




public class UiDateListener implements PostLoadEventListener, PreUpdateEventListener {
    
	@Autowired 
    private EntityManagerFactory entityManagerFactory;

    @PostConstruct
    private void init() {
        HibernateEntityManagerFactory hibernateEntityManagerFactory = (HibernateEntityManagerFactory) this.entityManagerFactory;
        SessionFactoryImpl sessionFactoryImpl = (SessionFactoryImpl) hibernateEntityManagerFactory.getSessionFactory();
        EventListenerRegistry registry = sessionFactoryImpl.getServiceRegistry().getService(EventListenerRegistry.class);
        registry.appendListeners(EventType.POST_LOAD, this);
        registry.appendListeners(EventType.PRE_UPDATE, this);
    }

    public String onPrepareStatement(String sql) {
    	System.out.println("");
        return sql;
    
    }
    @Override
    public void onPostLoad(PostLoadEvent event) {
        final Object entity = event.getEntity();
        if (entity == null) return;

        // some logic after entity loaded
    }

    @Override
    public boolean onPreUpdate(PreUpdateEvent event) {
        final Object entity = event.getEntity();
        if (entity == null) return false;

        // some logic before entity persist

        return false;
    }
}