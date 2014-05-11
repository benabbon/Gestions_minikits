/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */


package fablab.config.service;

/**
 *
 * @author cordieth
 */
public interface Config {
    
    
    /**
     * Get IP of the server.
     * This information is given by reading the config.txt.
     *
     * @return IP of the server the minikit should connect to
     */
    public String getIPServer();
    
    /**
     *  Get the lastname of the Minikit user
     * @return Lastname
     */
    
    public String getNomParticulier();
    
    
    /**
     *  Get the first name of the minikit user
     * @return firstname
     */
    public String getPrenomParticulier();
    
    /**
     * Get the id of the Minikit if already defined -1 else.
     * @return idMinikit if defined
     */
    public int getIdMinikit();
    
    /**
     *  Get the number of Captors
     * @return number of Captors
     */
    public int getNbCapteurs();
    /**
     *  get functionality of a capto ( one word of what the captors does)
     * @param captor number
     * @return Captor functionality
     */
    public String getFoncCapteur(int numeroCapteur);
}
