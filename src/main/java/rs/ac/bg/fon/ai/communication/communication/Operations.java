/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package rs.ac.bg.fon.ai.communication.communication;

/**
 *
 * @author korisnik
 */
public class Operations {
    // Hall operations

    public static final int SAVE_HALL = 1;
    public static final int GET_HALLS = 2;
    public static final int UPDATE_HALL = 3;
    public static final int DELETE_HALL = 4;
    public static final int HAS_UPCOMING_PROJECTIONS_FOR_HALL = 5;

    // Projection operations
    public static final int SAVE_PROJECTION = 10;
    public static final int GET_ALL_PROJECTIONS = 11;
    public static final int GET_PROJECTION_BY_ID = 12;
    public static final int GET_PROJECTIONS_BY_DATE = 13;
    public static final int UPDATE_PROJECTION = 14;
    public static final int EDIT_PROJECTION = 15;
    public static final int DELETE_PROJECTION = 16;

    // Film operations
    public static final int ADD_MOVIE = 20;
    public static final int GET_ALL_FILMS = 21;
    public static final int GET_GENRES_BY_ID = 22;

    // Genre operations
    public static final int GET_ALL_GENRES = 30;

    // Ticket operations
    public static final int SAVE_TICKETS = 40;
    public static final int GET_TICKETS_BY_BILL_ID = 41;

    // Bill operations
    public static final int SAVE_BILL = 50;
    public static final int GET_ALL_BILLS = 51;
    public static final int EDIT_BILL = 100;

    // Employee operations
    public static final int LOGIN = 60;
}
