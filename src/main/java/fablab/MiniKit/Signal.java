/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package fablab.MiniKit;

/**
 *
 * @author toto
 */
class Signal {

    private String message;
    public String getMessage() {
         return message;
    };
    public void notifyThread(String message){
        synchronized(this){
            this.message = message; 
            this.notify(); 
        }
    }
    
    
}
