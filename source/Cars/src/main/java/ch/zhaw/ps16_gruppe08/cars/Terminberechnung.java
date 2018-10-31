package ch.zhaw.ps16_gruppe08.cars;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;

/**
 * Zentrale Klasse für die Berechnung der Routen
 * 
 * @version 1.0
 * 
 */
public class Terminberechnung {
    private ArrayList<Verfugbarkeiten> verfuegbarkeiten = new ArrayList<>();
    private final Calendar vonDatum;
    private final Calendar bisDatum;
    private final int tage;
    private ArrayList<Variante> variante = new ArrayList<>();
    private static final int MILLISEKUNDEN_PRO_TAG = 1000 * 60 * 60 * 24;

    public Terminberechnung(final Calendar vonDatum, final Calendar bisDatum) 
    {
        this.vonDatum = (Calendar)vonDatum.clone();
        this.bisDatum = (Calendar)bisDatum.clone();
 
        if (this.vonDatum.after(this.bisDatum)) 
        {
            throw new IllegalArgumentException(
                    "Das von-Datum muss vor dem bis-Datum liegen.");
        }
        
        this.tage = zuBerechnendeAnzahlTage();
        Calendar datum = (Calendar) this.vonDatum.clone();
        
        for (int index = 0; index < tage; index++) 
        {
            verfuegbarkeiten.add(
                    new Verfugbarkeiten((Calendar)datum.clone()));
           
            if (verfuegbarkeiten.get(index).getAnzahlFahrzeuge() <= 0 ||
                verfuegbarkeiten.get(index).getAnzahlKundentermine() <= 0 ||
                verfuegbarkeiten.get(index).getAnzahlMitarbeiter() <= 0) 
                {
                    throw new IllegalArgumentException(
                            "Es sind keine Fahrzeuge, Mitarbeiter " +
                            "oder Kundentermine verfügbar.");
                }
            datum.add(Calendar.DAY_OF_MONTH, 1);
        }
    }

    /**
     * Erstellt eine Anzahl Varianten aufgrund des Parameters Anzahl.
     * 
     * @param planId
     * @param anzahl
     */
    public Variante berechneTermine(int planId, int anzahl) 
    {
        if(anzahl <= 0)
        {
            throw new IllegalArgumentException("Die Anzahl Varianten " +
                                               "muss grösser 0 sein");
        }

        if(planId <= 0)
        {
            throw new IllegalArgumentException("Die PlanId " +
                                               "muss grösser 0 sein");
        }        
        HashMap<Kundentermin, Integer> statistikNichtVergebeneKundentermine = 
                new HashMap<>();
        for (int index = 0; index < anzahl; index++) 
        {
            for(Verfugbarkeiten element : verfuegbarkeiten)
            {
                element.resetIndexe();
            }
            variante.add(new Variante(planId,verfuegbarkeiten,
                                      statistikNichtVergebeneKundentermine));
        }
        
        return bewerteVarianten();

    }
    
    /**
     * Rechnet die Anzahl zu berechnende Tage aufgrund der vorgegebenen
     * Datumsbereich aus.
     * 
     * @return anzahlTage
     */
    private int zuBerechnendeAnzahlTage() 
    {
        long differenz = bisDatum.getTimeInMillis() 
                - vonDatum.getTimeInMillis();
        int tage = (int) (differenz / MILLISEKUNDEN_PRO_TAG) + 1;
        return tage;
    }
    
    /**
     * Bewertet die einzelnen Varianten und vergibt der besten den Status
     * definitiv, gibt die beste Variante zurück.
     * 
     * @return definitiveVariante
     */
    private Variante bewerteVarianten()
    {
        Variante definitiveVariante = null;
        for(Variante element : variante)
        {
            if(definitiveVariante == null ||
                    element.getAnzahlVerpassteKunde() > 
                    definitiveVariante.getAnzahlVerpassteKunde())
            {
                definitiveVariante = element; 
            }
        }
        
        definitiveVariante.setStatusDefinitiv(true);
        return definitiveVariante;
    }
        
    /**
     * Gibt die erstellten Varianten als ArrayList zurück.
     */
    public ArrayList<Variante> getVarianten()
    {
        return this.variante;
    }
    
    /**
     * gibt das Verfügbarkeiten-Objekt das der Berechnung zugrunde liegt
     * zurück.
     */
    public ArrayList<Verfugbarkeiten> getVerfuegbarkeiten()
    {
        return this.verfuegbarkeiten;
    }
}
