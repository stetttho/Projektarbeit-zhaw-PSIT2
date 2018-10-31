package ch.zhaw.ps16_gruppe08.cars;

import java.util.HashMap;

/** 
 * Repräsentiert einen aktuellen Benutzer
 *
 */
public class Benutzer {
    private String name;
    private String passwort;
    private boolean hatSchreibrechte;
    private int anzahlVersuche;
    private HashMap<String, String> gueltigeBenutzer = new HashMap<>();

    public Benutzer ()
    {
        gueltigeBenutzer();
    }
    
    /**
     * Methode erstellt eine HashMap mit Benutzer, die akzeptiert werden.
     */
    public void gueltigeBenutzer()
    {
        gueltigeBenutzer.put("admin", "admin");
        gueltigeBenutzer.put("leser", "leser");
    }
    
    /**
     * Setzt den aktuellen Benutzername
     * @param name
     */
    public void setName(String name)
    {
        this.name = name;
    }
    
    /**
     * Setzt das aktuelle Passwort
     * @param passwort
     */
    public void setPasswort(String passwort)
    {
        this.passwort = passwort;
    }
    
    /**
     * Setzt Schreibrechte für den entsprechenden Benutzer
     * 
     */
    public void setSchreibrechte()
    {
        if(this.name.equals("admin"))
        {
            hatSchreibrechte = true;
        }
        else if(this.name.equals("leser"))
        {
        hatSchreibrechte = false;
        }
    }
    
    /**
     * Setter-Methode, um die Anzahl der Anmeldeversuche zu setzen.
     * @param anzahl
     */
    public void setAnzahlVersuche(int anzahl)
    {
        anzahlVersuche = anzahl;
    }
    
    /**
     * Getter-Methode für Benutzername
     * @return this.name
     */
    public String getName()
    {
        return this.name;
    }

    /**
     * Getter-Methode für Passwort
     * @return this.passwort
     */
    public String getPasswort()
    {
        return this.passwort;
    }
    
    /**
     * Getter-Methode zur Abfrage der Schreibrechte
     * @return hatSchreibrechte
     */
    public boolean getSchreibrechte()
    {
        return hatSchreibrechte;
    }
    
    /**
     * Getter-Methode zur Abfrage der Anzahl der Anmeldeversuche    
     * @return anzahlVersuche
     */
    public int getAnzahlVersuche()
    {
        return anzahlVersuche;
    }
    
    /**
     * Methode überprüft, ob Input-Benutzer einem 
     * gültigen Benutzer aus der HashMap entspricht.
     * @return boolean
     */
    public boolean ueberpruefeBenutzer()
    {
        anzahlVersuche++;
        if(gueltigeBenutzer.containsKey(name))
        {
            return true;
        }

        return false;
    }
    
    /**
     * Methode überprüft, ob das Passwort des Input-Benutzers korrekt ist.
     * Wenn ja, werden Schreib- oder Leserechte werden gesetzt.
     * @return boolean
     */
    public boolean ueberpruefePasswort()
    {
        String passwortGueltigerBenutzer;
        passwortGueltigerBenutzer = gueltigeBenutzer.get(name);
        if(passwortGueltigerBenutzer.equals(passwort))
        {
            setSchreibrechte();
            return true;
        }
        return false;
    }
    
}
