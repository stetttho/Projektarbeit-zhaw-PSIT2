package ch.zhaw.ps16_gruppe08.cars;
 
import java.util.Enumeration;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpSessionContext;

public class HttpSessionStub implements javax.servlet.http.HttpSession {

    private Benutzer anmeldung;
    @Override
    public long getCreationTime() {
        // TODO Auto-generated method stub
        return 0;
    }

    @Override
    public String getId() {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public long getLastAccessedTime() {
        // TODO Auto-generated method stub
        return 0;
    }

    @Override
    public ServletContext getServletContext() {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public void setMaxInactiveInterval(int interval) {
        // TODO Auto-generated method stub
        
    }

    @Override
    public int getMaxInactiveInterval() {
        // TODO Auto-generated method stub
        return 0;
    }

    @Override
    public HttpSessionContext getSessionContext() {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public Object getAttribute(String name) {
        
        return this.anmeldung;
    }
    
    public void setBenutzer(Benutzer anmeldung)
    {
        this.anmeldung = anmeldung;
    }

    @Override
    public Object getValue(String name) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public Enumeration<String> getAttributeNames() {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public String[] getValueNames() {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public void setAttribute(String name, Object value) {
        // TODO Auto-generated method stub
        
    }

    @Override
    public void putValue(String name, Object value) {
        // TODO Auto-generated method stub
        
    }

    @Override
    public void removeAttribute(String name) {
        // TODO Auto-generated method stub
        
    }

    @Override
    public void removeValue(String name) {
        // TODO Auto-generated method stub
        
    }

    @Override
    public void invalidate() {
        // TODO Auto-generated method stub
        
    }

    @Override
    public boolean isNew() {
        // TODO Auto-generated method stub
        return false;
    }

}