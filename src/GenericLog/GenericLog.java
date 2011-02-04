/* DataReader
 *
 * Copyright 2011
 *
 * This file is part of the DataReader project.
 *
 * DataReader software is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * DataReader software is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with any DataReader software.  If not, see http://www.gnu.org/licenses/
 *
 * I ask that if you make any changes to this file you fork the code on github.com!
 *
 */
package GenericLog;

import datareader.DataLogReaderApp;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.util.HashMap;

/**
 *
 * @author Bryan
 */
public class GenericLog extends HashMap<String, GenericDataElement>  {

    final public static int LOG_NOT_LOADED = -1;
    final public static int LOG_LOADING = 0;
    final public static int LOG_LOADED = 1;
    final private static String LOG_STATUS = "LogStatus";
    private String metaData;
    protected final PropertyChangeSupport PCS;
    private int logLoaded;

    public GenericLog() {
        super();
        logLoaded = -1;
        PCS = new PropertyChangeSupport(this);
        addPropertyChangeListener("LogLoaded", new PropertyChangeListener() {
            public void propertyChange( final PropertyChangeEvent propertyChangeEvent) {
                if((Integer)propertyChangeEvent.getNewValue() == 0){
                    DataLogReaderApp.getInstance().setLog((GenericLog) propertyChangeEvent.getSource());
                }
                else if((Integer)propertyChangeEvent.getNewValue() == 1) {
                    DataLogReaderApp.getInstance().getDrawnGraph().reInitGraph();
                }
            }
        });
        metaData = "";
        
    }
    /**
     * provide a <code>String</code> array of headers<br>
     * each header will be used as a HashMap key, the data related to each header will be added to an <code>ArrayList</code>.
     * @param headers - of the data to be converted
     */
    public GenericLog(String[] headers) {
        super();

        logLoaded = -1;
        PCS = new PropertyChangeSupport(this);
        addPropertyChangeListener("LogLoaded", new PropertyChangeListener() {
            public void propertyChange( final PropertyChangeEvent propertyChangeEvent) {
                if((Integer)propertyChangeEvent.getNewValue() == 0){
                    DataLogReaderApp.getInstance().setLog((GenericLog) propertyChangeEvent.getSource());
                }
                else if((Integer)propertyChangeEvent.getNewValue() == 1) {
                    DataLogReaderApp.getInstance().getDrawnGraph().reInitGraph();
                }
            }
        });

        metaData = "";
        
        this.setHeaders(headers);

    }
    /**
     * Add a piece of data to the <code>ArrayList</code> associated with the <code>key</code>
     * @param key - header
     * @param value - data to be added
     * @return true or false if it was successfully added
     */
    public boolean addValue(String key, double value) {
        GenericDataElement logElement = (GenericDataElement) this.get(key);
        return logElement.add(value);
    }
    /**
     * Set the state of the log
     * @param logLoaded GenericLog.LOG_NOT_LOADED / GenericLog.LOG_LOADING / GenericLog.LOG_LOADED
     */
    public void setLogStatus(int logLoaded) {
        int isLogLoaded = this.logLoaded;
         this.logLoaded = logLoaded;
        PCS.firePropertyChange("LogLoaded", isLogLoaded, logLoaded);
    }
    /**
     *
     * @return -1 if log not loaded 0 if loading or 1 if log is loaded
     */
    public int getLogStatus() {
        return this.logLoaded;
    }

    public void setHeaders(String[] headers) {
        for (int x = 0; x < headers.length; x++) {
            this.put(headers[x], new GenericDataElement());
        }
    }


    /**
     * Add metadata This is information about the log being converted such as the location it was from or the date<br>
     * This method does not add to its self so in order to add more info you must VAR.addMetaData(VAR.getMetaData() + NEWINFO)
     * @param md meta data to be added
     */
    public void setMetaData(String md) {
        metaData = md;
    }
    /**
     *
     * @return String containing the current meta data
     */
    public String getMetadata() {
        return metaData;
    }
    /*
     * Test the log, this will output data to the console only
     * code kept incase it was needed at some point
     */
   /* public void testLog() {
        Iterator i = this.keySet().iterator();
        ArrayList al;
        String head = "";
        while (i.hasNext()) {
            head = (String) i.next();
            al = (ArrayList) this.get(head);
            System.out.printf("%10s",head);
            for (int x = 0; x < al.size() - 1; x++) {
                System.out.printf("%10.3f ", al.get(x));
            }
            System.out.println();
        }
        System.out.print(this.metaData);
    }*/

   /**
    * Add a property change listener to the generic log, REQUIRED!!
    * GenericLog.LOG_STATUS is the name of the status property
    * @param name
    * @param listener
    * <code>new OBJECT.addPropertyChangeListener("LogLoaded", new PropertyChangeListener() {<br>
    *       public void propertyChange( final PropertyChangeEvent propertyChangeEvent) {<br>
    *           DataLogReaderApp.getInstance().setLog((GenericLog) propertyChangeEvent.getSource());
    *           ...Insert code here...<br>
    *
    *       }<br>
    *   });</code>
    */

    public void addPropertyChangeListener(final String name, final PropertyChangeListener listener) {
        PCS.addPropertyChangeListener(name, listener);
    }
    /**
     * Remove a PropertyChangeListener
     * @param propertyName name of listener
     * @param listener listener
     */
    public void removePropertyChangeListener(final String propertyName, final PropertyChangeListener listener) {
        PCS.removePropertyChangeListener(propertyName, listener);
    }



}
