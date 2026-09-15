/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package rs.ac.bg.fon.ai.client.logic;

import java.time.LocalDate;
import java.util.List;

import rs.ac.bg.fon.ai.client.communication.Communication;
import rs.ac.bg.fon.ai.communication.communication.Operations;
import rs.ac.bg.fon.ai.communication.communication.Request;
import rs.ac.bg.fon.ai.communication.communication.Response;
import rs.ac.bg.fon.ai.communication.communication.ResponseType;
import rs.ac.bg.fon.ai.communication.model.Bill;
import rs.ac.bg.fon.ai.communication.model.Employee;
import rs.ac.bg.fon.ai.communication.model.Film;
import rs.ac.bg.fon.ai.communication.model.Genre;
import rs.ac.bg.fon.ai.communication.model.Hall;
import rs.ac.bg.fon.ai.communication.model.Projection;
import rs.ac.bg.fon.ai.communication.model.Ticket;

/**
 *
 * @author nkala
 */
public class Controller {

    private static Controller instance;
    private Employee currentUser;

    private Controller() {
    }

    public static Controller getInstance() {
        if (instance == null) {
            instance = new Controller();
        }
        return instance;
    }

    public void setCurrentUser(Employee employee) {
        this.currentUser = employee;
    }

    public Employee getCurrentUser() {
        return currentUser;
    }

    public Employee login(String username, String password) throws Exception {
        // Kreiraj niz sa username i password
        Object[] loginParams = new Object[]{username, password};

        Request request = new Request(Operations.LOGIN, loginParams);
        Response response = Communication.getInstance().sendRequest(request);

        if (response.getResponseType().equals(ResponseType.SUCCESS)) {
            return (Employee) response.getResult();
        } else {
            throw response.getException();
        }
    }

    // Hall operations
    public Hall saveHall(Hall hall) throws Exception {
        Request request = new Request(Operations.SAVE_HALL, hall);
        Response response = Communication.getInstance().sendRequest(request);

        if (response.getResponseType().equals(ResponseType.SUCCESS)) {
            return (Hall) response.getResult(); // ← VRATI Hall SA ID-JEM
        } else {
            throw response.getException();
        }
    }

    public List<Hall> getHalls() throws Exception {
        Request request = new Request(Operations.GET_HALLS, null);
        Response response = Communication.getInstance().sendRequest(request);

        if (response.getResponseType().equals(ResponseType.SUCCESS)) {
            return (List<Hall>) response.getResult();
        } else {
            throw response.getException();
        }
    }

    public void updateHall(Hall hall) throws Exception {
        Request request = new Request(Operations.UPDATE_HALL, hall);
        Response response = Communication.getInstance().sendRequest(request);

        if (response.getResponseType().equals(ResponseType.SUCCESS)) {
            return;
        } else {
            throw response.getException();
        }
    }

    public void deleteHall(Hall hall) throws Exception {
        Request request = new Request(Operations.DELETE_HALL, hall);
        Response response = Communication.getInstance().sendRequest(request);

        if (response.getResponseType().equals(ResponseType.SUCCESS)) {
            return;
        } else {
            throw response.getException();
        }
    }

    public boolean hasUpcomingProjectionsForHall(Hall hall) throws Exception {
        Request request = new Request(Operations.HAS_UPCOMING_PROJECTIONS_FOR_HALL, hall);
        Response response = Communication.getInstance().sendRequest(request);

        if (response.getResponseType().equals(ResponseType.SUCCESS)) {
            return (Boolean) response.getResult();
        } else {
            throw response.getException();
        }
    }

    // Projection operations
    public Projection saveProjection(Projection projection) throws Exception {
        Request request = new Request(Operations.SAVE_PROJECTION, projection);
        Response response = Communication.getInstance().sendRequest(request);

        if (response.getResponseType().equals(ResponseType.SUCCESS)) {
            return (Projection) response.getResult();
        } else {
            throw response.getException();
        }
    }

    public List<Projection> getAllProjections() throws Exception {
        Request request = new Request(Operations.GET_ALL_PROJECTIONS, null);
        Response response = Communication.getInstance().sendRequest(request);

        if (response.getResponseType().equals(ResponseType.SUCCESS)) {
            return (List<Projection>) response.getResult();
        } else {
            throw response.getException();
        }
    }

    public Projection getProjectionById(Long projectionId) throws Exception {
        Request request = new Request(Operations.GET_PROJECTION_BY_ID, projectionId);
        Response response = Communication.getInstance().sendRequest(request);

        if (response.getResponseType().equals(ResponseType.SUCCESS)) {
            return (Projection) response.getResult();
        } else {
            throw response.getException();
        }
    }

    public List<Projection> getProjectionsByDate(LocalDate date) throws Exception {
        Request request = new Request(Operations.GET_PROJECTIONS_BY_DATE, date);
        Response response = Communication.getInstance().sendRequest(request);

        if (response.getResponseType().equals(ResponseType.SUCCESS)) {
            return (List<Projection>) response.getResult();
        } else {
            throw response.getException();
        }
    }

    public void updateProjectionSoldTickets(Projection projection) throws Exception {
        Request request = new Request(Operations.UPDATE_PROJECTION, projection);
        Response response = Communication.getInstance().sendRequest(request);

        if (response.getResponseType().equals(ResponseType.SUCCESS)) {
            return;
        } else {
            throw response.getException();
        }
    }

    public void editProjection(Projection projection) throws Exception {
        Request request = new Request(Operations.EDIT_PROJECTION, projection);
        Response response = Communication.getInstance().sendRequest(request);

        if (response.getResponseType().equals(ResponseType.SUCCESS)) {
            return;
        } else {
            throw response.getException();
        }
    }

    public void deleteProjection(Projection projection) throws Exception {
        Request request = new Request(Operations.DELETE_PROJECTION, projection);
        Response response = Communication.getInstance().sendRequest(request);

        if (response.getResponseType().equals(ResponseType.SUCCESS)) {
            return;
        } else {
            throw response.getException();
        }
    }

    // Film operations
    public Film addMovie(Film film) throws Exception {
        Request request = new Request(Operations.ADD_MOVIE, film);
        Response response = Communication.getInstance().sendRequest(request);

        if (response.getResponseType().equals(ResponseType.SUCCESS)) {
            return (Film) response.getResult();
        } else {
            throw response.getException();
        }
    }

    public List<Film> getAllFilms() throws Exception {
        Request request = new Request(Operations.GET_ALL_FILMS, null);
        Response response = Communication.getInstance().sendRequest(request);

        if (response.getResponseType().equals(ResponseType.SUCCESS)) {
            return (List<Film>) response.getResult();
        } else {
            throw response.getException();
        }
    }

    public List<Genre> getGenresByID(Long filmId) throws Exception {
        Request request = new Request(Operations.GET_GENRES_BY_ID, filmId);
       Response response = Communication.getInstance().sendRequest(request);

       if (response.getResponseType().equals(ResponseType.SUCCESS)) {
           return (List<Genre>) response.getResult();
       } else {
           throw response.getException();
       }
   }
    // Genre operations
    public List<Genre> getAllGenres() throws Exception {
        Request request = new Request(Operations.GET_ALL_GENRES, null);
        Response response = Communication.getInstance().sendRequest(request);

        if (response.getResponseType().equals(ResponseType.SUCCESS)) {
            return (List<Genre>) response.getResult();
        } else {
            throw response.getException();
        }
    }

    // Ticket operations
    public void saveTickets(List<Ticket> tickets) throws Exception {
        Request request = new Request(Operations.SAVE_TICKETS, tickets);
        Response response = Communication.getInstance().sendRequest(request);

        if (response.getResponseType().equals(ResponseType.SUCCESS)) {
            return;
        } else {
            throw response.getException();
        }
    }

    public List<Ticket> getTicketsByBillId(Long billId) throws Exception {
       Request request = new Request(Operations.GET_TICKETS_BY_BILL_ID, billId);
        Response response = Communication.getInstance().sendRequest(request);

        if (response.getResponseType().equals(ResponseType.SUCCESS)) {
            return (List<Ticket>) response.getResult();
        } else {
            throw response.getException();
        }
    }
    // Bill operations
    public Bill saveBill(Bill bill) throws Exception {
        Request request = new Request(Operations.SAVE_BILL, bill);
        Response response = Communication.getInstance().sendRequest(request);

        if (response.getResponseType().equals(ResponseType.SUCCESS)) {
            return (Bill) response.getResult();
        } else {
            throw response.getException();
        }
    }

    public void editBill(Bill bill) throws Exception {
        Request request = new Request(Operations.EDIT_BILL, bill);
        Response response = Communication.getInstance().sendRequest(request);
        if (response.getResponseType().equals(ResponseType.SUCCESS)) {
            return;
        } else {
            throw response.getException();
        }
    }

    public List<Bill> getAllBills() throws Exception {
        Request request = new Request(Operations.GET_ALL_BILLS, null);
        Response response = Communication.getInstance().sendRequest(request);

        if (response.getResponseType().equals(ResponseType.SUCCESS)) {
            return (List<Bill>) response.getResult();
        } else {
            throw response.getException();
        }
    }
}
