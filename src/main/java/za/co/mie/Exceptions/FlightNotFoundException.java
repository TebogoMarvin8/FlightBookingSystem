/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package za.co.mie.Exceptions;

/**
 *
 * @author Train
 */
public class FlightNotFoundException extends RuntimeException {
    public FlightNotFoundException(String msg){
        super(msg);
    }
}
